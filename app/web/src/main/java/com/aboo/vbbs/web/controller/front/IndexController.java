package com.aboo.vbbs.web.controller.front;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.base.exception.ApiException;
import com.aboo.vbbs.base.model.Result;
import com.aboo.vbbs.base.util.StrUtil;
import com.aboo.vbbs.data.model.bbs.Role;
import com.aboo.vbbs.data.model.bbs.ScoreLog;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.CodeService;
import com.aboo.vbbs.serv.RoleService;
import com.aboo.vbbs.serv.ScoreLogService;
import com.aboo.vbbs.serv.UserService;
import com.aboo.vbbs.serv.enums.CodeEnum;
import com.aboo.vbbs.serv.enums.ScoreEventEnum;
import com.aboo.vbbs.web.controller.BaseController;
import com.aboo.vbbs.web.util.FreemarkerUtil;
import com.aboo.vbbs.web.util.identicon.Identicon;
import com.google.common.collect.Maps;

@Controller
public class IndexController extends BaseController {

//  @Autowired
//  private TopicSearch topicSearch;
  @Autowired
  private UserService userService;
  @Autowired
  private Identicon identicon;
  @Autowired
  private CodeService codeService;
  @Autowired
  private RoleService roleService;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  ScoreLogService scoreLogService;

  /**
   * 首页
   *
   * @return
   */
  @GetMapping("/")
  public String index(String tab, Integer p, Model model) {
    model.addAttribute("p", p);
    model.addAttribute("tab", tab);
    return "front/index";
  }

  /**
   * top 100 user score
   *
   * @return
   */
  @GetMapping("/top100")
  public String top100() {
    return "front/top100";
  }

  /**
   * 搜索
   *
   * @param p
   * @param q
   * @param model
   * @return
   */
  @GetMapping("/search")
  public String search(Integer p, String q, Model model) {
//    Page<Topic> page = topicSearch.search(p == null ? 1 : p, siteConfig.getPageSize(), q);
//    model.addAttribute("page", page);
    model.addAttribute("q", q);
    return "front/search";
  }

  /**
   * 进入登录页
   *
   * @return
   */
  @GetMapping("/login")
  public String toLogin(String s, Model model, HttpServletResponse response) {
    if (getUser() != null) {
      return redirect(response, "/");
    }
    model.addAttribute("s", s);
    return "front/login";
  }

  /**
   * 进入注册页面
   *
   * @return
   */
  @GetMapping("/register")
  public String toRegister(HttpServletResponse response) {
    if (getUser() != null) {
      return redirect(response, "/");
    }
    return "front/register";
  }

  /**
   * 注册验证
   *
   * @param username
   * @param password
   * @return
   */
  @PostMapping("/register")
  @ResponseBody
  public Result register(String username, String password, String email, String emailCode, String code,
                         HttpSession session) throws ApiException {

    String genCaptcha = (String) session.getAttribute("index_code");
    if (StringUtils.isEmpty(code)) throw new ApiException("验证码不能为空");

    if (!genCaptcha.toLowerCase().equals(code.toLowerCase())) throw new ApiException("验证码错误");
    if (StringUtils.isEmpty(username)) throw new ApiException("用户名不能为空");
    if (StringUtils.isEmpty(password)) throw new ApiException("密码不能为空");
    if (StringUtils.isEmpty(email)) throw new ApiException("邮箱不能为空");

    if (AppSite.me().getIllegalUsername().contains(username)
        || !StrUtil.check(username, StrUtil.userNameCheck)) throw new ApiException("用户名不合法");

    User user = userService.findByUsername(username);
    if (user != null) throw new ApiException("用户名已经被注册");

    User user_email = userService.findByEmail(email);
    if (user_email != null) throw new ApiException("邮箱已经被使用");

    int validateResult = codeService.validateCode(email, emailCode, CodeEnum.EMAIL);
    if (validateResult == 1) throw new ApiException("邮箱验证码不正确");
    if (validateResult == 2) throw new ApiException("邮箱验证码已过期");
    if (validateResult == 3) throw new ApiException("邮箱验证码已经被使用");

    Date now = new Date();
    // generator avatar
    String avatar = identicon.generator(username);

    user = new User();
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
    user.setInTime(now);
    user.setBlock(false);
    user.setToken(UUID.randomUUID().toString());
    user.setAvatar(avatar);
    user.setAttempts(0);
    user.setScore(AppSite.me().getScore());
    user.setSpaceSize(AppSite.me().getUserUploadSpaceSize());

    // set user's role
    Role role = roleService.findByName(AppSite.me().getNewUserRole());
    userService.save(user, role.getId());

    //region 记录积分log
    if (AppSite.me().getScore() != 0) {
      ScoreLog scoreLog = new ScoreLog();

      scoreLog.setInTime(new Date());
      scoreLog.setEvent(ScoreEventEnum.REGISTER.getEvent());
      scoreLog.setChangeScore(user.getScore());
      scoreLog.setScore(user.getScore());
      scoreLog.setUserId(user.getId());

      Map<String, Object> params = Maps.newHashMap();
      params.put("scoreLog", scoreLog);
      params.put("user", user);
      String des = freemarkerUtil.format(AppSite.me().getScoreTemplate().get(ScoreEventEnum.REGISTER.getName()), params);
      scoreLog.setEventDescription(des);
      scoreLogService.save(scoreLog);
    }
    //endregion 记录积分log
    return Result.success();
  }

}
