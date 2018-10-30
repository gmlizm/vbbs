package com.aboo.vbbs.web.controller.front;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.base.exception.ApiException;
import com.aboo.vbbs.base.model.Result;
import com.aboo.vbbs.base.util.StrUtil;
import com.aboo.vbbs.data.model.bbs.Comment;
import com.aboo.vbbs.data.model.bbs.ScoreLog;
import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.CommentService;
import com.aboo.vbbs.serv.NotificationService;
import com.aboo.vbbs.serv.ScoreLogService;
import com.aboo.vbbs.serv.TopicService;
import com.aboo.vbbs.serv.UserService;
import com.aboo.vbbs.serv.enums.NotificationEnum;
import com.aboo.vbbs.serv.enums.ScoreEventEnum;
import com.aboo.vbbs.web.controller.BaseController;
import com.aboo.vbbs.web.util.FreemarkerUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private UserService userService;

  @Autowired
  FreemarkerUtil freemarkerUtil;

  @Autowired
  ScoreLogService scoreLogService;

  /**
   * 保存评论
   *
   * @param topicId
   * @param content
   * @return
   */
  @PostMapping("/save")
  public String save(Integer topicId, String content, HttpServletResponse response) throws Exception {
    User user = getUser();
    if (user.getBlock()) throw new Exception("你的帐户已经被禁用，不能进行此项操作");
    if (user.getScore() + AppSite.me().getCreateCommentScore() < 0) throw new Exception("你的积分不足，不能评论");
    if (StringUtils.isEmpty(content)) throw new Exception("评论内容不能为空");

    if (topicId != null) {
      Topic topic = topicService.findById(topicId);
      if (topic != null) {
        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setTopicId(topicId);
        comment.setInTime(new Date());
        comment.setUp(0);
        comment.setContent(content);
        commentService.save(comment);

        // update score
        user.setScore(user.getScore() + AppSite.me().getCreateCommentScore());
        userService.save(user);

        //评论+1
        topic.setCommentCount(topic.getCommentCount() + 1);
        topic.setLastCommentTime(new Date());
        topicService.save(topic);


        //region 记录积分log
        ScoreLog scoreLog = new ScoreLog();

        scoreLog.setInTime(new Date());
        scoreLog.setEvent(ScoreEventEnum.COMMENT_TOPIC.getEvent());
        scoreLog.setChangeScore(AppSite.me().getCreateCommentScore());
        scoreLog.setScore(user.getScore());
        scoreLog.setUserId(user.getId());

        Map<String, Object> params = Maps.newHashMap();
        params.put("scoreLog", scoreLog);
        params.put("user", user);
        params.put("topic", topic);
        String des = freemarkerUtil.format(AppSite.me().getScoreTemplate().get(ScoreEventEnum.COMMENT_TOPIC.getName()), params);
        scoreLog.setEventDescription(des);
        scoreLogService.save(scoreLog);
        //endregion 记录积分log

        //给话题作者发送通知
        if (user.getId() != topic.getUserId()) {
          notificationService.sendNotification(getUser(), userService.findById(topic.getUserId()), NotificationEnum.COMMENT.name(), topic, content);
        }
        //给At用户发送通知
        List<String> atUsers = StrUtil.fetchAt(null, content);
        for (String u : atUsers) {
          u = u.replace("@", "").trim();
          if (!u.equals(user.getUsername())) {
            User _user = userService.findByUsername(u);
            if (_user != null) {
              notificationService.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content);
            }
          }
        }
        return redirect(response, "/topic/" + topicId);
      }
    }
    return redirect(response, "/");
  }

  /**
   * 点赞
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/up")
  @ResponseBody
  public Result up(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment _comment = commentService.findById(id);

    if (_comment == null) throw new ApiException("评论不存在");
    if (user.getId() == _comment.getUserId()) throw new ApiException("不能给自己的评论点赞");

    Comment comment = commentService.up(user.getId(), _comment);
    return Result.success(comment.getUpDown());
  }

  /**
   * 取消点赞
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/cancelUp")
  @ResponseBody
  public Result cancelUp(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment comment = commentService.cancelUp(user.getId(), id);
    if (comment == null) throw new ApiException("评论不存在");
    return Result.success(comment.getUpDown());
  }

  /**
   * 踩
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/down")
  @ResponseBody
  public Result down(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment _comment = commentService.findById(id);

    if (_comment == null) throw new ApiException("评论不存在");
    if (user.getId() == _comment.getUserId()) throw new ApiException("不能给自己的评论点赞");

    Comment comment = commentService.down(user.getId(), _comment);
    return Result.success(comment.getUpDown());
  }

  /**
   * 取消踩
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/cancelDown")
  @ResponseBody
  public Result cancelDown(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment comment = commentService.cancelDown(user.getId(), id);
    if (comment == null) throw new ApiException("评论不存在");
    return Result.success(comment.getUpDown());
  }
}
