package com.aboo.vbbs.web.controller.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.data.model.bbs.Topic;
import com.aboo.vbbs.serv.TopicService;
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
public class TopicsDirective implements TemplateDirectiveModel {

  @Autowired
  private TopicService topicService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    String tab = StringUtils.isEmpty(map.get("tab")) ? "default" : map.get("tab").toString();
    if (StringUtils.isEmpty(tab)) tab = "default";

    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());
    Page<Topic> page = topicService.page(p, AppSite.me().getPageSize(), tab);

    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}