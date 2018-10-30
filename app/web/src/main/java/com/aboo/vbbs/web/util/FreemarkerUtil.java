package com.aboo.vbbs.web.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Component
public class FreemarkerUtil {


  private final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);


  @Autowired
  FreeMarkerConfigurer freeMarkerConfigurer;

  private static final Map<String, Template> cachedTemplate = Maps.newConcurrentMap();

  public String format(String template, Map<String, Object> objectMap) {
    StringWriter writer = new StringWriter();
    try {
      getTemplateConfiguration(template).process(objectMap, writer);
    } catch (TemplateException | IOException e) {
      e.printStackTrace();
      logger.error("render template error", e);
    }
    return writer.toString();
  }

  private Template getTemplateConfiguration(String template) {

    if (cachedTemplate.containsKey(template)) {
      return cachedTemplate.get(template);
    }
    Configuration configuration = null;
    try {
      configuration = freeMarkerConfigurer.createConfiguration();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
      logger.error("get system configuration error", e);
    }
    configuration.setTemplateLoader(new StringTemplateLoader(template));
    configuration.setDefaultEncoding("UTF-8");


    try {
      Template stringTemplate = configuration.getTemplate("");
      cachedTemplate.put(template, stringTemplate);
      return stringTemplate;
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("get template error", e);
    }
    return null;
  }

}
