package com.aboo.vbbs.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

/**
 * 数据库连接配置类
 * 
 * @author lizm
 *
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages= {"com.aboo.vbbs.data.mapper.sys"}, sqlSessionTemplateRef = "sqlSessionTemplateSys")
public class Db2Config {

	/** 日志记录工具 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* ########数据源配置-start######## */
	@ConfigurationProperties(prefix = "spring.datasource.sys")
	@Bean
	public DataSource dataSourceSys() {
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.info("系统应用的MySQL数据库-数据源为【{}】", dataSource.getClass().getSimpleName());
		return dataSource;
	}
    @Bean
    public SqlSessionFactory sqlSessionFactorySys(@Qualifier("dataSourceSys") DataSource dataSource) throws Exception{
    	MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource); 
        return sqlSessionFactory.getObject();
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplateSys(@Qualifier("sqlSessionFactorySys") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
	/* ########数据源配置-end######## */

	/* ########事务管理配置-start######## */
	@Bean(name = "transManagerSys")
	public PlatformTransactionManager transManager(@Qualifier("dataSourceSys") DataSource dataSource) {
		AbstractPlatformTransactionManager transManager = new DataSourceTransactionManager(dataSource);
		transManager.setDefaultTimeout(20000);
		logger.debug("系统应用的数据库事务管理器为【{}】", transManager.getClass().getSimpleName());
		return transManager;
	}
	/* ########事务管理配置-end######## */

}
