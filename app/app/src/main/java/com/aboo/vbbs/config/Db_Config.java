package com.aboo.vbbs.config;

import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;

/**
 * 数据库相关配置类,ORM层配置
 * 
 * @author lizm
 *
 */
@Configuration
public class Db_Config {
	/* ########mybatis-plus-configuration######## */
	@Bean
    public MybatisConfiguration configuration(){
		MybatisConfiguration config = new MybatisConfiguration();
		config.setCacheEnabled(false);
		config.setMapUnderscoreToCamelCase(true);
		config.setJdbcTypeForNull(JdbcType.NULL);
		config.setAggressiveLazyLoading(true);
		return config;
    }
	
	/* ########mybatis-plus-globalConfig######## */
	@Bean
    public GlobalConfiguration globalConfig(){
		GlobalConfiguration globalConfig = new GlobalConfiguration();
		globalConfig.setIdType(0);//@See IdType(0-数据库自增,1-用户输入,2-全局唯一,3-全局唯一uuid,4-none,5-全局唯一字符串)
		globalConfig.setFieldStrategy(0);//0-忽略判断,1,非 NULL 判断,2-非空判断
		globalConfig.setDbColumnUnderline(true);//设置数据库字段驼峰下划线转换
		globalConfig.setCapitalMode(true);//开启大写命名
		globalConfig.setRefresh(true);//是否刷新mapper
		globalConfig.setSqlParserCache(true);//缓存sql 解析初始化
		globalConfig.setLogicDeleteValue("-1");//逻辑删除值
		globalConfig.setLogicNotDeleteValue("1");//非逻辑删除值
		globalConfig.setSqlInjector(new LogicSqlInjector());
		return globalConfig;
    }

}
