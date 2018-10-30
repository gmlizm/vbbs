package com.aboo.vbbs.web.controller.admin;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.data.model.bbs.Role;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.data.model.bbs.UserRole;
import com.aboo.vbbs.serv.RoleService;
import com.aboo.vbbs.serv.UserRoleService;
import com.aboo.vbbs.serv.UserService;
import com.aboo.vbbs.web.controller.BaseController;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private UserRoleService userRoleService;
  @Autowired
  private RoleService roleService;

  /**
   * 用户列表
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    model.addAttribute("page", userService.pageByInTime(p == null ? 1 : p, AppSite.me().getPageSize()));
    return "admin/user/list";
  }

  /**
   * 禁用用户
   *
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/block")
  public String block(@PathVariable Integer id, HttpServletResponse response) {
    userService.blockUser(id);
    return redirect(response, "/admin/user/list");
  }

  /**
   * 解禁用户
   *
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/unblock")
  public String unblock(@PathVariable Integer id, HttpServletResponse response) {
    userService.unBlockUser(id);
    return redirect(response, "/admin/user/list");
  }

  /**
   * 配置用户的角色
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/role")
  public String role(@PathVariable Integer id, Model model) {
    model.addAttribute("user", userService.findById(id));
    model.addAttribute("roles", roleService.findAll());
    return "admin/user/role";
  }

  /**
   * 保存配置用户的角色
   *
   * @param id
   * @return
   */
  @PostMapping("/{id}/role")
  public String saveRole(@PathVariable Integer id, int score, Integer roleId, HttpServletResponse response) {
    User user = userService.findById(id);
    Set<Role> roles = new HashSet<>();
    Role role = roleService.selectById(roleId);
    roles.add(role);
    //TODO 记录日志
    user.setScore(score);
    userService.save(user);
    userRoleService.insert(new UserRole().setUserId(user.getId()).setRoleId(roleId));
    return redirect(response, "/admin/user/list");
  }

}
