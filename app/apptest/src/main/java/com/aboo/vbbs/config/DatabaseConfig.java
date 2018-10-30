package com.aboo.vbbs.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据库连接配置类
 * 
 * @author lizm
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.aboo.vbbs.data.mapper")
public class DatabaseConfig {
	
	/** 日志记录工具 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*########数据源配置-start########*/
	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.info("系统应用的MySQL数据库-数据源为【{}】", dataSource.getClass().getSimpleName());
		return dataSource;
	}
	
	/*########数据源配置-end########*/
	
	
	/*########事务管理配置-start########*/
	@Primary
	@Bean(name = "tManager")
	public PlatformTransactionManager msqlTManager(DataSource dataSource) {
		PlatformTransactionManager tManager = new DataSourceTransactionManager(dataSource);
		//tManager.setDefaultTimeout(30000);
		logger.debug("系统应用的数据库事务管理器为【{}】", tManager.getClass().getSimpleName());
		return tManager;
	}
	
	/*########事务管理配置-end########*/
	

}
