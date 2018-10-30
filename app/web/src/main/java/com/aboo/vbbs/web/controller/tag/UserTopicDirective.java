package com.aboo.vbbs.web.controller.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.TopicService;
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
public class UserTopicDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;
  @Autowired
  private TopicService topicService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    String username = map.get("username").toString();
    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());
    int limit = Integer.parseInt(map.get("limit").toString());

    User currentUser = userService.findByUsername(username);
    Page<Topic> page = topicService.findByUser(p, limit, currentUser);

    environment.setVariable("page", builder.build().wrap(page));
    environment.setVariable("currentUser", builder.build().wrap(currentUser));
    templateDirectiveBody.render(environment.getOut());
  }
}
