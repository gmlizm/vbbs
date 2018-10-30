package com.aboo.vbbs.data;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * mybatis-plus 自动映射生成器
 * 
 * @author yylizm
 * @date 2018/6/1
 */
public class DataGenerator {

	public static void main(String[] args) {
		AutoGenerator generator = new AutoGenerator();
		generator.setGlobalConfig(getGlobalConfig());
		generator.setDataSource(getDataSourceConfig());
		generator.setTemplateEngine(new FreemarkerTemplateEngine());
		generator.setTemplate(getTemplateConfig());
		generator.setStrategy(getStrategyConfig());
		generator.setPackageInfo(getPackageConfig());
		generator.execute();
	}

	/**
	 * 配置创建模板
	 * 
	 * @return
	 */
	private static TemplateConfig getTemplateConfig() {
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setController(null);//.setService(null).setServiceImpl(null);
		return templateConfig;
	}

	/**
	 * 设置包路径
	 * 
	 * @return
	 */
	public static PackageConfig getPackageConfig() {
		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent("com.aboo.vbbs.data");
		packageConfig.setMapper("mapper");
		packageConfig.setEntity("model");
		packageConfig.setXml("mapper");
		packageConfig.setController("web.controller");
		//第一次初始化，生成service层，以后只剩成data层
		//packageConfig.setService("serv").setServiceImpl("serv.imp");
		return packageConfig;
	}

	/**
	 * 配置策略
	 * 
	 * @return
	 */
	private static StrategyConfig getStrategyConfig() {
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setCapitalMode(true);
		strategyConfig.setEntityLombokModel(true);
		strategyConfig.setDbColumnUnderline(true);
		strategyConfig.setNaming(NamingStrategy.underline_to_camel);
		strategyConfig.setDbColumnUnderline(true);
		strategyConfig.setTablePrefix("bbs_");
		strategyConfig.setSkipView(true);
		strategyConfig.entityTableFieldAnnotationEnable(true);
		strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
		strategyConfig.setEntityBuilderModel(true);
		return strategyConfig;
	}

	/**
	 * 全局配置
	 * 
	 * @return
	 */
	public static GlobalConfig getGlobalConfig() {
		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(false);
		config.setAuthor("yylizm");
		config.setOutputDir("src/main/java/");
		config.setFileOverride(true);
		config.setEnableCache(false);
		// config.setBaseColumnList(false);
		config.setControllerName("%sController");
		config.setServiceImplName("%sServiceImp");
		config.setServiceName("%sService");
		config.setOpen(false);
		return config;
	}

	/**
	 * 配置数据源
	 * 
	 * @return
	 */
	public static DataSourceConfig getDataSourceConfig() {
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/db_aboo?serverTimezone=UTC&characterEncoding=utf8&autoReconnect=true&useSSL=false";
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername("root").setPassword("root")
				.setDriverName("com.mysql.jdbc.Driver");
		return dataSourceConfig;
	}

}
