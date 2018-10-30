package com.aboo.vbbs.security;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.model.bbs.Permission;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.serv.PermissionService;
import com.aboo.vbbs.serv.UserService;

@Service
public class UserDetailService implements UserDetailsService {

  private Logger log = LoggerFactory.getLogger(UserDetailService.class);

  @Autowired
  private UserService userService;
  @Autowired
  private PermissionService permissionService;

  public UserDetails loadUserByUsername(String username) {
    User user = userService.findByUsername(username);
    if (user != null) {
      List<Permission> permissions = permissionService.findByAdminUserId(user.getId());
      List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
      for (Permission permission : permissions) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
        grantedAuthorities.add(grantedAuthority);
      }
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
          true, true, true, !user.getBlock(), grantedAuthorities);
    } else {
      log.info("用户" + username + " 不存在");
      throw new UsernameNotFoundException("用户名或密码不正确");
    }
  }

}
