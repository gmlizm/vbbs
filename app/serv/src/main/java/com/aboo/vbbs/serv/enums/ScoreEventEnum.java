package com.aboo.vbbs.serv.enums;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
public enum ScoreEventEnum {
  DAILY_SIGN("每日签到", "dailySign"),
  CREATE_TOPIC("发布主题", "createTopic"),
  COMMENT_TOPIC("评论主题", "commentTopic"),
  REGISTER("注册", "register");

  private String event;
  private String name;

  ScoreEventEnum(String event, String name) {
    this.event = event;
    this.name = name;
  }

  ScoreEventEnum(String event) {
    this.event = event;
  }

  public String getEvent() {
    return event;
  }

  public String getName() {
    return name;
  }
}
