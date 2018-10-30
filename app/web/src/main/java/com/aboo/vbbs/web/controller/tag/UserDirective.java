package com.aboo.vbbs.web.controller.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.UserService;

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
public class UserDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;
 

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    String username;
    if (map.containsKey("username") && !StringUtils.isEmpty(map.get("username").toString())) {
      username = map.get("username").toString();
    } else {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      username =  ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
    }

    User user = userService.findByUsername(username);

    environment.setVariable("user", builder.build().wrap(user));
    templateDirectiveBody.render(environment.getOut());
  }
}