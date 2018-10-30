package com.aboo.vbbs.web.controller.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.CollectService;
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
public class CurrentUserDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;
  @Autowired
  private CollectService collectService;
 

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    String username = map.get("username").toString();
    User currentUser = userService.findByUsername(username);
    long collectCount = collectService.countByUser(currentUser);

    environment.setVariable("currentUser", builder.build().wrap(currentUser));
    environment.setVariable("collectCount", builder.build().wrap(collectCount));
    templateDirectiveBody.render(environment.getOut());
  }
}