package com.aboo.vbbs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	
	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(Application.class);
		app.setAddCommandLineProperties(false);
		app.setBannerMode(Banner.Mode.OFF);
		ApplicationContext context = app.run(args);
		logger.error("【{}】 started at {}", context.getId(), new Date(context.getStartupDate()));
	}

}
