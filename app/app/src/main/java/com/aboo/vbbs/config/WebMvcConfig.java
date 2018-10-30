package com.aboo.vbbs.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.aboo.vbbs.security.interceptor.CommonInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${site.theme}")
	private String theme;

	@Autowired
	private CommonInterceptor commonInterceptor;

	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
//		 configurer.setUseSuffixPatternMatch(false);
	}

	/**
	 * Add intercepter
	 *
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(commonInterceptor).addPathPatterns("/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*").allowedMethods("POST", "GET").allowedOrigins("*");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/views/" + theme + "/static/");//, "file:./views/" + theme + "/static/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.freeMarker().cache(false);
		registry.freeMarker().prefix("");
		registry.freeMarker().suffix(".ftl");
	}

	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setDefaultEncoding(StandardCharsets.UTF_8.name());
		configurer.setTemplateLoaderPaths("classpath:/views/" + theme + "/templates/");//, "file:./views/" + theme + "/templates/");
		Properties settings = new Properties();
		settings.setProperty("template_exception_handler", "rethrow");
		configurer.setFreemarkerSettings(settings);
		return configurer;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		WebMvcConfigurer.super.configureDefaultServletHandling(configurer);
	}
	/*======================================================*/
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("10240KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }

}
