package com.aboo.vbbs.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.model.bbs.Permission;
import com.aboo.vbbs.serv.PermissionService;

@Service
public class AppInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  Logger log = LoggerFactory.getLogger(AppInvocationSecurityMetadataSource.class);

  @Autowired
  private PermissionService permissionService;

  private HashMap<String, Collection<ConfigAttribute>> map = null;

  /**
   * 加载资源，初始化资源变量
   */
  public void loadResourceDefine() {
    map = new HashMap<>();
    List<Permission> permissions = permissionService.findAll(true);
    for (Permission permission : permissions) {
      map.put(permission.getUrl(), Arrays.asList(new SecurityConfig(permission.getName())));
    }
    log.info("security info load success!!");
  }


  /**
   * 根据路径获取访问权限的集合接口
   *
   * @param object
   * @return
   * @throws IllegalArgumentException
   */
  @Override
  public Collection<ConfigAttribute> getAttributes(Object object)
      throws IllegalArgumentException {
    if (map == null) loadResourceDefine();
    HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
    AntPathRequestMatcher matcher;
    String resUrl;
    for (String s : map.keySet()) {
      resUrl = s;
      matcher = new AntPathRequestMatcher(resUrl);
      if (matcher.matches(request)) {
        return map.get(resUrl);
      }
    }
    return null;
  }

  /**
   * @return
   */
  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return Collections.emptyList();
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
