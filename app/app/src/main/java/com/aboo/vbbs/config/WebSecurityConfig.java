package com.aboo.vbbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.aboo.vbbs.security.AppSecurityInterceptor;
import com.aboo.vbbs.security.UserDetailService;
import com.aboo.vbbs.security.ValidateCodeAuthenticationFilter;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	  @Autowired
	  private UserDetailService userDetailService;
	  @Autowired
	  private AppSecurityInterceptor appSecurityInterceptor;
	  @Autowired
	  private WebSupportConfig webAuthConfig;
	  @Autowired
	  private ValidateCodeAuthenticationFilter validateCodeAuthenticationFilter;
	  
   @Override
   public void configure(HttpSecurity http) throws Exception {
       http.antMatcher("/**")
         .authorizeRequests()
         .antMatchers("/", "/register", "/login**","/error**", "/common/code", "/static/**")
         .permitAll()
         .anyRequest()
         .authenticated()
         .and()
		 .csrf().disable();
       
       http.formLogin()
       .loginPage("/login")
       .loginProcessingUrl("/login")
       .failureUrl("/login?error")
       .defaultSuccessUrl("/")
       .permitAll();
       //remember-me
       http.rememberMe().key("remember-me").rememberMeServices(webAuthConfig.persistentTokenBasedRememberMeServices());

       http.logout()
           .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
           .logoutSuccessUrl("/").invalidateHttpSession(true)
           .deleteCookies("JSESSIONID", "remember-me");	
       
       http.addFilterBefore(appSecurityInterceptor, FilterSecurityInterceptor.class);
       http.addFilterBefore(validateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

       http.csrf().ignoringAntMatchers("/common/upload", "/user/space/deleteFile", "/favicon.ico");
   }
   
   @Override
   public void configure(WebSecurity web) {
     web.ignoring().antMatchers("/static/**");
   }
   
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
   }
  
   @Bean
   public AuthenticationManager authenticationManager() throws Exception {
     return super.authenticationManagerBean();
   }

//	@Bean
//	public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//		FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
//		registration.setFilter(filter);
//		registration.setOrder(-100);
//		return registration;
//	}

//	private Filter ssoFilter() {
//		CompositeFilter filter = new CompositeFilter();
//		List<Filter> filters = new ArrayList<>();
//		filters.add(ssoFilter(qq(), "/login/qq"));
//		filters.add(ssoFilter(github(), "/login/github"));
//		filter.setFilters(filters);
//		return filter;
//	}
//
//	private Filter ssoFilter(ClientResources client, String path) {
//		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
//		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient() );
//		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
//		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
//				client.getClient().getClientId());
//		tokenServices.setRestTemplate(oAuth2RestTemplate);
//		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
//		return oAuth2ClientAuthenticationFilter;
//	}
//	
//	@Bean
//	@ConfigurationProperties("security.oauth2.github")
//	public ClientResources github() {
//		return new ClientResources();
//	}
//
//	@Bean
//	@ConfigurationProperties("security.oauth2.qq")
//	public ClientResources qq() {
//		return new ClientResources();
//	}
//
//}
//
//class ClientResources {
//
//	@NestedConfigurationProperty
//	private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
//
//	@NestedConfigurationProperty
//	private ResourceServerProperties resource = new ResourceServerProperties();
//
//	public AuthorizationCodeResourceDetails getClient() {
//		return client;
//	}
//
//	public ResourceServerProperties getResource() {
//		return resource;
//	}
}
   