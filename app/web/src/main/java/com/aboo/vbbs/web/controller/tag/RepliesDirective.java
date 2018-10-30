package com.aboo.vbbs.web.controller.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.aboo.vbbs.data.model.bbs.Comment;
import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.CommentService;
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
public class RepliesDirective implements TemplateDirectiveModel {

  @Autowired
  private CommentService commentService;
  @Autowired
  private UserService userService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    int topicId = Integer.parseInt(map.get("id").toString());
    Topic topic = new Topic();
    topic.setId(topicId);

    List<Comment> replies = commentService.findByTopic(topic);
    environment.setVariable("replies", builder.build().wrap(replies));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
      User user = userService.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
      environment.setVariable("user", builder.build().wrap(user));
    }

    templateDirectiveBody.render(environment.getOut());
  }
}