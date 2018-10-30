package com.aboo.vbbs.web.controller.admin;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aboo.vbbs.data.model.bbs.Role;
import com.aboo.vbbs.serv.PermissionService;
import com.aboo.vbbs.serv.RoleService;
import com.aboo.vbbs.web.controller.BaseController;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/role")
public class RoleAdminController extends BaseController {

  @Autowired
  private RoleService roleService;
  @Autowired
  private PermissionService permissionService;

  /**
   * 角色列表
   *
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("roles", roleService.findAll());
    return "admin/role/list";
  }

  /**
   * 添加角色
   *
   * @param model
   * @return
   */
  @GetMapping("/add")
  public String add(Model model) {
    model.addAttribute("list", permissionService.findAll(false));
    return "admin/role/add";
  }

  /**
   * 保存配置的权限
   *
   * @param permissionIds
   * @param response
   * @return
   */
  @PostMapping("/add")
  public String save(String name, String description, Integer[] permissionIds, HttpServletResponse response) {
    Role role = new Role();
    role.setName(name);
    role.setDescription(description);
    roleService.save(role, Arrays.asList(permissionIds));
    return redirect(response, "/admin/role/list");
  }

  /**
   * 编辑角色
   *
   * @param model
   * @return
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("role", roleService.selectById(id));
    model.addAttribute("list", permissionService.findAll(false));
    return "/admin/role/edit";
  }

  /**
   * 更新配置的权限
   *
   * @param permissionIds
   * @param response
   * @return
   */
  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id, String name, String description, Integer[] permissionIds, HttpServletResponse response) {
    Role role = roleService.selectById(id);
    role.setName(name);
    role.setDescription(description);
    roleService.save(role,Arrays.asList(permissionIds));
    return redirect(response, "/admin/role/list");
  }

  /**
   * 删除角色
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Integer id, HttpServletResponse response) {
    roleService.deleteById(id);
    return redirect(response, "/admin/role/list");
  }
}
