package com.aboo.vbbs.web.controller.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.UserService;
import com.baomidou.mybatisplus.plugins.Page;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class ScoreDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());
    int limit = map.get("limit") == null ? AppSite.me().getPageSize() : Integer.parseInt(map.get("limit").toString());

    Page<User> page = userService.findByScore(p, limit);

    environment.setVariable("page", builder.build().wrap(page));

    templateDirectiveBody.render(environment.getOut());
  }
}