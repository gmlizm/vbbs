package com.aboo.vbbs.config;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.base.util.StrUtil;
import com.aboo.vbbs.data.model.bbs.GithubUser;
import com.aboo.vbbs.data.model.bbs.RememberMeToken;
import com.aboo.vbbs.data.model.bbs.Role;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.security.UserDetailService;
import com.aboo.vbbs.serv.GithubUserService;
import com.aboo.vbbs.serv.RememberMeTokenService;
import com.aboo.vbbs.serv.RoleService;
import com.aboo.vbbs.serv.UserService;

@Configuration
public class WebSupportConfig {

  @Autowired
  private UserDetailService userDetailService;
  @Autowired
  private GithubUserService githubUserService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private UserService userService;

  @Bean
  public PrincipalExtractor principalExtractor() {
    return map -> {
      String login = map.get("login").toString();//github的登录名
      GithubUser githubUser = githubUserService.findByLogin(login);
      User user;
      if (githubUser == null) {
        githubUser = new GithubUser();
        githubUser = githubUserService.convert(map, githubUser);
        githubUserService.save(githubUser);
        //创建一个本地用户
        user = userService.findByUsername(login);
        if (user == null) {
          user = new User();
          user.setUsername(login);
        } else {
          user.setUsername(login + "_" + githubUser.getGithubId());
        }
        user.setEmail(githubUser.getEmail());
        user.setBio(githubUser.getBio());
        user.setUrl(githubUser.getHtmlUrl());
        user.setPassword(new BCryptPasswordEncoder().encode(StrUtil.randomString(16)));
        user.setInTime(new Date());
        user.setBlock(false);
        user.setToken(UUID.randomUUID().toString());
        user.setAvatar(githubUser.getAvatarUrl());
        user.setAttempts(0);
        user.setScore(AppSite.me().getScore());
        user.setSpaceSize(AppSite.me().getUserUploadSpaceSize());
        user.setGithubUserId(githubUser.getId());
        // set user's role
        Role role = roleService.findByName("user"); // normal user
        userService.save(user, role.getId());
      } else {
        githubUser = githubUserService.convert(map, githubUser);
        user = userService.findByGithubId(githubUser.getId());
      }
      //加载用户的权限信息
      return userDetailService.loadUserByUsername(user.getUsername());
    };
  }

  @Bean
  public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
    PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices("remember-me"
        , userDetailService, persistentTokenRepository());
    services.setAlwaysRemember(true);
    return services;
  }
  
  @Bean 
  @Transactional("transManager")
  public PersistentTokenRepository persistentTokenRepository() {
	  return new PersistentTokenRepository() {
		  @Autowired
		    private RememberMeTokenService rememberMeTokenService;
		    @Override
		    public void createNewToken(PersistentRememberMeToken token) {
		      List<RememberMeToken> tokens = rememberMeTokenService.getAllByUsernameOrderByDate(token.getUsername());
		      if (tokens != null && tokens.size() >= AppSite.me().getLoginPoints()) {
		        int end = tokens.size() - AppSite.me().getLoginPoints() + 1;
		        for (int i = 0; i < end; i++) {
		      	  rememberMeTokenService.deleteById(tokens.get(i).getId());
		        }
		      }
		      RememberMeToken rememberMeToken = new RememberMeToken();
		      BeanUtils.copyProperties(token, rememberMeToken);
		      rememberMeTokenService.insert(rememberMeToken);
		    }

		    @Override
		    public void updateToken(String series, String tokenValue, Date lastUsed) {
		      RememberMeToken rememberMeToken = rememberMeTokenService.getBySeries(series);
		      if (rememberMeToken != null) {
		        rememberMeToken.setTokenValue(tokenValue);
		        rememberMeToken.setDate(lastUsed);
		      }
		    }

		    @Override
		    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		      RememberMeToken rememberMeToken = rememberMeTokenService.getBySeries(seriesId);
		      if (rememberMeToken != null) {
		        return new PersistentRememberMeToken(rememberMeToken.getUsername(),
		            rememberMeToken.getSeries(), rememberMeToken.getTokenValue(), rememberMeToken.getDate());
		      }
		      return null;
		    }

		    @Override
		    public void removeUserTokens(String username) {
		  	  rememberMeTokenService.deleteByUsername(username);
		    }
	  };
  }

  //TODO 
//  @Bean
//  public EmbeddedServletContainerFactory servletContainerFactory() {
//    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//    tomcat.addConnectorCustomizers(connector -> {
//      connector.setScheme("http");
//      connector.setPort(Integer.parseInt(env.getProperty("server.port")));
//      if (siteConfig.isSsl()) {
//        connector.setRedirectPort(8443);
//        connector.setSecure(true);
//      }
//    });
//    return tomcat;
//  }
  
}
