package com.aboo.vbbs.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.UserService;

/**
 * Created by tomoya. Copyright (c) 2016, All Rights Reserved. https://yiiu.co
 */
public class BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 带参重定向
	 *
	 * @param path
	 * @return
	 */
	protected String redirect(String path) {
		return "redirect:" + path;
	}

	/**
	 * 不带参重定向
	 *
	 * @param response
	 * @param path
	 * @return
	 */
	protected String redirect(HttpServletResponse response, String path) {
		try {
			response.sendRedirect(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取Security用户
	 *
	 * @return
	 */
	protected UserDetails getSecurityUser() {
		Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean b = o instanceof UserDetails;
		if (b) {
			return (UserDetails) o;
		}
		return null;
	}

	/**
	 * 获取用户信息
	 *
	 * @return
	 */
	protected User getUser() {
		UserDetails userDetails = getSecurityUser();
		if (userDetails != null) {
			return userService.findByUsername(userDetails.getUsername());
		}
		return null;
	}

	/**
	 * 获取用户信息
	 *
	 * @param token
	 * @return
	 */
	protected User getUser(String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		} else {
			return userService.findByToken(token);
		}
	}

	protected String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
	}

}
