package com.aboo.vbbs.config;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class EnvConfig {
	
	/** 日志记录工具 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String ENV_CONFIG_FILE = "config/env.properties";

	@PostConstruct
	private void initEnvironment() {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties(ENV_CONFIG_FILE);
			Set<Entry<Object,Object>> envProps = properties.entrySet();
			for(Entry<Object,Object> envEntry : envProps){
				 String envKey = String.valueOf(envEntry.getKey());
				 if(envKey==null||envKey.isEmpty()){
					 continue;
				 }
				 if(System.getProperty(envKey)==null){
					 System.setProperty(envKey, String.valueOf(envEntry.getValue()));
					 System.out.println(envKey+" ===>>> "+envEntry.getValue());
				 }
				 
			}
		} catch (IOException e) {
			logger.error("system environment variables load failured. [{}]", ENV_CONFIG_FILE);
			e.printStackTrace();
		}
	}

}
