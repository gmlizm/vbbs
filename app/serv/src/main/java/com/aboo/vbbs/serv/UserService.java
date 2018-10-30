package com.aboo.vbbs.serv;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.UserMapper;
import com.aboo.vbbs.data.model.bbs.Role;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.data.model.bbs.UserRole;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yylizm
 * @since 2018-06-04
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

	@Autowired
	private UserRoleService userRoleService ;
	
	@Autowired
	private RoleService roleService ;
	
	/**
	 * 获取用户对应的角色列表
	 * @param userId
	 * @return
	 */
	public List<Role> getRoles(Integer userId){
		List<UserRole> userRoles = userRoleService.selectList(new EntityWrapper<>(new UserRole().setUserId(userId)));
		if(userRoles==null) {
			return Collections.emptyList();
		}
		return roleService.selectBatchIds(userRoles.stream().map(it->it.getRoleId()).collect(Collectors.toList()));
	}
	/**
	 * search user by score desc
	 *
	 * @param p
	 * @param size
	 * @return
	 */
	@Cacheable
	public Page<User> findByScore(int p, int size) {
		return super.selectPage(new Page<User>(p,size,"score", false));
	}
	
	/**
	 * 分页查询用户列表
	 *
	 * @param p
	 * @param size
	 * @return
	 */
	@Cacheable
	public Page<User> pageByInTime(int p, int size) {
		return super.selectPage(new Page<User>(p,size,"in_time", false));
	}

	@Cacheable
	public User findById(int id) {
		return super.selectById(id);
	}

	@Cacheable
	public User findByUsername(String username) {
		return super.selectOne(new EntityWrapper<User>().eq("username", username));
	}
	
	@Cacheable
	public User findByGithubId(Integer githubId) {
		return super.selectOne(new EntityWrapper<>(new User().setGithubUserId(githubId)));
	}
	
	@Cacheable
	public User findByEmail(String email) {
		return super.selectOne(new EntityWrapper<User>().eq("email", email));
	}
	@Cacheable
	public User findByToken(String token) {
		return super.selectOne(new EntityWrapper<User>().eq("token", token));
	}

	@CacheEvict(allEntries = true)
	public void save(User user, Integer roleId) {
		super.insert(user);
		userRoleService.insert(new UserRole().setUserId(user.getId()).setRoleId(roleId));
	}
	
	@CacheEvict(allEntries = true)
	public void save(User user) {
		super.insert(user);
	}

	/**
	 * 禁用用户
	 *
	 * @param id
	 */
	@CacheEvict(allEntries = true)
	public void blockUser(Integer id) {
		User user = findById(id);
		user.setBlock(true);
		save(user);
	}

	/**
	 * 用户解禁
	 *
	 * @param id
	 */
	@CacheEvict(allEntries = true)
	public void unBlockUser(Integer id) {
		User user = this.findById(id);
		user.setBlock(false);
		save(user);
	}



}
