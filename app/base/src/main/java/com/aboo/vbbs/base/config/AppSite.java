package com.aboo.vbbs.base.config;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class AppSite {

	/** 唯一实例，保存站点配置 */
	private static AppSite me = new AppSite();
	
	private AppSite() {
	}
	
	public static AppSite me() {
		return me;
	}
	
	public static void set(AppSite siteConfig) {
		AppSite.me = siteConfig;
	}

	private String name;
	private String intro;
	private String baseUrl;
	private String staticUrl;
	private int pageSize;
	private String uploadPath;
	private String theme;
	private boolean search;
	private int attempts;
	private long attemptsWaitTime;
	private int maxCreateTopic;
	private int createTopicScore;
	private int createCommentScore;
	private long userUploadSpaceSize;
	private int score;
	private int loginPoints;
	private String GA;
	private String googleZZ;
	private String baiduTJ;
	private String baiduZZ;
	private boolean ssl;
	private String newUserRole;
	private CookieConfig cookie;
	private List<String> illegalUsername;
	private MailTemplateConfig mail;
	private Map<String,String> scoreTemplate;

}
