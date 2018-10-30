package com.aboo.vbbs.serv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aboo.vbbs.data.mapper.bbs.PermissionMapper;
import com.aboo.vbbs.data.model.bbs.Permission;
import com.aboo.vbbs.data.model.bbs.RolePermission;
import com.aboo.vbbs.data.model.bbs.User;
import com.aboo.vbbs.data.model.bbs.UserRole;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RolePermissionService rolePermissionService;

	public Permission findByName(String name) {
		return super.selectOne(new EntityWrapper<>(new Permission().setName(name)));
	}

	/**
	 * 根据pid查询权限
	 *
	 * @param pid
	 * @return
	 */
	@Cacheable
	public List<Permission> findByPid(int pid) {
		return super.selectList(new EntityWrapper<>(new Permission().setPid(pid)));
	}

	/**
	 * 查询权限列表
	 *
	 * @return
	 */
	@Cacheable
	public List findAll(boolean child) {
		if (child) {
			Wrapper<Permission> wrapper = Condition.wrapper();
			return super.selectList(wrapper.gt("pid", 0));
		} else {
			List<Map<String,Object>> list = new ArrayList<>();
			List<Permission> permissions = this.findByPid(0);
			for (Permission permission : permissions) {
				Map<String,Object> map = new HashMap<>();
				map.put("permission", permission);
				map.put("childPermissions", this.findByPid(permission.getId()));
				list.add(map);
			}
			return list;
		}
	}

	/**
	 * 根据用户的id查询用户的所有权限
	 *
	 * @param adminUserId
	 * @return
	 */
	@Cacheable
	public List<Permission> findByAdminUserId(int adminUserId) {
		User user = userService.findById(adminUserId);
		List<UserRole> roles = userRoleService.selectList(new EntityWrapper<>(new UserRole().setUserId(user.getId())));
		if (roles == null) {
			return Collections.emptyList();
		}
		List<Integer> permIds = new ArrayList<>();
		roles.forEach(role -> {
			List<RolePermission> rolePerms = rolePermissionService
					.selectList(new EntityWrapper<>(new RolePermission().setRoleId(role.getRoleId())));
			if (CollectionUtils.isEmpty(rolePerms)) {
				return;
			}
			permIds.addAll(rolePerms.stream().map(it -> it.getPermissionId()).collect(Collectors.toList()));
		});

		if(CollectionUtils.isEmpty(permIds)) {
			return Collections.emptyList();
		}
		
		List<Permission> permissions = super.selectBatchIds(permIds);
		if (permissions == null) {
			return Collections.emptyList();
		}
		return permissions.stream().filter(it -> it.getPid() > 0).collect(Collectors.toList());
	}

	@CacheEvict(allEntries = true)
	public void save(Permission permission) {
		super.insert(permission);
	}

	/**
	 * 删除权限 判断权限的pid是不是0，是的话，就删除其下所有的权限
	 *
	 * @param id
	 */
	@CacheEvict(allEntries = true)
	public void deleteById(Integer id) {
		Permission permission = findById(id);
		if (permission.getPid() == 0) {
			super.delete(new EntityWrapper<>(new Permission().setPid(permission.getId())));
		}
		super.deleteById(permission.getId());
	}

	@Cacheable
	public Permission findById(int id) {
		return super.selectById(id);
	}
}
