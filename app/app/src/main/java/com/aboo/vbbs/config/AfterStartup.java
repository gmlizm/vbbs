package com.aboo.vbbs.config;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

import com.aboo.vbbs.base.AppContextHolder;
import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.data.model.bbs.Permission;
import com.aboo.vbbs.data.model.bbs.Role;
import com.aboo.vbbs.data.model.bbs.SysInfo;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.PermissionService;
import com.aboo.vbbs.serv.RoleService;
import com.aboo.vbbs.serv.SysInfoService;
import com.aboo.vbbs.serv.UserService;
import com.aboo.vbbs.web.controller.tag.CurrentUserDirective;
import com.aboo.vbbs.web.controller.tag.NodeTopicsDirective;
import com.aboo.vbbs.web.controller.tag.NodesDirective;
import com.aboo.vbbs.web.controller.tag.NotificationsDirective;
import com.aboo.vbbs.web.controller.tag.RepliesDirective;
import com.aboo.vbbs.web.controller.tag.ScoreDirective;
import com.aboo.vbbs.web.controller.tag.SpringSecurityTag;
import com.aboo.vbbs.web.controller.tag.TopicsDirective;
import com.aboo.vbbs.web.controller.tag.UserCollectDirective;
import com.aboo.vbbs.web.controller.tag.UserCommentDirective;
import com.aboo.vbbs.web.controller.tag.UserDirective;
import com.aboo.vbbs.web.controller.tag.UserTopicDirective;

import lombok.Data;

@Configuration
@Service
@Transactional("transManager")
public class AfterStartup implements ApplicationListener<ContextRefreshedEvent> {

	/** 日志记录工具 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysInfoService sysInfoService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			//初始化环境上下文
			initAppContextHolder(event);
			//加载站点配置信息
			loadAppConfig();
			//添加freemarker共享变量
			addFreemarkerSharedVariable();
			
			SysInfo sysInfo = sysInfoService.findByName("init");
			if (sysInfo == null) {
				insert();
				sysInfo = new SysInfo().setName("init").setValue("1");
				sysInfoService.save(sysInfo);
			} else {
				if (sysInfo.getValue().equalsIgnoreCase("0")) {
					// 系统已经初始化过了，这里有人为修改过的
					// 添加你想要的处理
				}
			}
		} catch (Exception e) {
			logger.error("----init system error----", e);
			System.exit(0);
		}
	}

	private void initAppContextHolder(ContextRefreshedEvent event) throws Exception {
		Field[] fields = AppContextHolder.class.getDeclaredFields();
		for(Field field:fields) {
			if(field.getType()==ApplicationContext.class) {
				field.setAccessible(true);
				field.set(AppContextHolder.class, event.getApplicationContext());
				field.setAccessible(false);
				break;
			}
		}
	}
	
	private void loadAppConfig() throws IOException {
		Yaml yam = new Yaml();
		AppSite.set(yam.loadAs(new ClassPathResource("config/website.yml").getInputStream(), AppSite.class));
	}

	private void addFreemarkerSharedVariable() throws Exception {
		freemarker.template.Configuration configuration = AppContextHolder.getBean(freemarker.template.Configuration.class);

	    configuration.setSharedVariable("sec", AppContextHolder.getBean(SpringSecurityTag.class));
	    // 注入全局配置至freemarker
	    configuration.setSharedVariable("site", AppSite.me());
//	    configuration.setSharedVariable("model", baseEntity);

	    configuration.setSharedVariable("user_topics_tag", AppContextHolder.getBean(UserTopicDirective.class));
	    configuration.setSharedVariable("user_replies_tag", AppContextHolder.getBean(UserCommentDirective.class));
	    configuration.setSharedVariable("user_collects_tag", AppContextHolder.getBean(UserCollectDirective.class));
	    configuration.setSharedVariable("topics_tag", AppContextHolder.getBean(TopicsDirective.class));
	    configuration.setSharedVariable("user_tag", AppContextHolder.getBean(UserDirective.class));
	    configuration.setSharedVariable("current_user_tag", AppContextHolder.getBean(CurrentUserDirective.class));
	    configuration.setSharedVariable("notifications_tag", AppContextHolder.getBean(NotificationsDirective.class));
	    configuration.setSharedVariable("replies_tag", AppContextHolder.getBean(RepliesDirective.class));
	    configuration.setSharedVariable("score_tag", AppContextHolder.getBean(ScoreDirective.class));
	    configuration.setSharedVariable("nodes_tag", AppContextHolder.getBean(NodesDirective.class));
	    configuration.setSharedVariable("node_topics_tag", AppContextHolder.getBean(NodeTopicsDirective.class));

	    logger.info("init freemarker sharedVariables {site} success...");
		
	}

	public void destory() {
		// TODO
	}

	public void insert() throws Exception {
		Yaml yam = new Yaml();
		DataConfig dataConfig = yam.loadAs(new ClassPathResource("config/data.yml").getInputStream(), DataConfig.class);

		// save permissions
		for (DataPermission dataPerm : dataConfig.getPermissions()) {
			Permission permission = new Permission();
			permission.setName(dataPerm.getName());
			permission.setDescription(dataPerm.getDescription());
			permission.setPid(0);
			permissionService.save(permission);
			// save child permission
			dataPerm.getChilds().forEach(childPermission -> {
				Permission permission1 = new Permission();
				permission1.setPid(permission.getId());
				permission1.setName(childPermission.getName());
				permission1.setDescription(childPermission.getDescription());
				permission1.setUrl(childPermission.getUrl());
				permissionService.save(permission1);
			});
		}

		// save role
		dataConfig.getRoles().forEach(dataRole -> {
			Role role = new Role();
			role.setName(dataRole.getName());
			role.setDescription(dataRole.getDescription());
			List<Integer> permIds = new ArrayList<>();
			// associate permission
			if (dataRole.getPermissions() != null) {
				dataRole.getPermissions().forEach(permissionName -> {
					Permission permission = permissionService.findByName(permissionName);
					permIds.add(permission.getId());
				});
			}
			roleService.save(role, permIds);
		});

		// save user
		DataUser dataUser = dataConfig.getUser();
		Role role = roleService.findByName(dataUser.getRole());
		User user = new User();
		user.setUsername(dataUser.getUsername());
		user.setPassword(dataUser.getPassword());
		user.setEmail(dataUser.getEmail());
		user.setBio(dataUser.getBio());
		user.setUrl(dataUser.getUrl());
		user.setInTime(new Date());
		user.setBlock(false);
		user.setToken(UUID.randomUUID().toString());
		user.setAvatar(dataUser.getAvatar());
		user.setAttempts(0);
		user.setScore(AppSite.me().getScore());
		user.setSpaceSize(AppSite.me().getUserUploadSpaceSize());
		// associate role
		userService.save(user, role.getId());

	}

	@Data
	static public class DataConfig {
		private DataUser user;
		private List<DataRole> roles;
		private List<DataPermission> permissions;
	}

	@Data
	static public class DataUser {
		private String username;
		private String password;
		private String avatar;
		private String email;
		private String bio;
		private String url;
		private String role;
	}

	@Data
	static public class DataRole {
		private String name;
		private String description;
		private List<String> permissions;
	}

	@Data
	static public class DataPermission {
		private String name;
		private String description;
		private String url;
		private List<DataChildPermission> childs;
	}

	@Data
	static public class DataChildPermission {
		private String name;
		private String description;
		private String url;
	}

}
