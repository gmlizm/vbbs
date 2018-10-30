package com.aboo.vbbs.web.controller.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.data.model.bbs.Notification;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.NotificationService;
import com.aboo.vbbs.serv.UserService;
import com.baomidou.mybatisplus.plugins.Page;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class NotificationsDirective implements TemplateDirectiveModel {

  @Autowired
  private NotificationService notificationService;
  @Autowired
  private UserService userService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
    User user = userService.findByUsername(username);

    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());

    Page<Notification> page = notificationService.findByTargetUserAndIsRead(p, AppSite.me().getPageSize(), user, null);
    //将未读消息置为已读
    notificationService.updateByIsRead(user);

    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}