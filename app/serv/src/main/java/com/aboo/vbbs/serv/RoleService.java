package com.aboo.vbbs.serv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aboo.vbbs.data.mapper.bbs.RoleMapper;
import com.aboo.vbbs.data.model.bbs.Role;
import com.aboo.vbbs.data.model.bbs.RolePermission;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yylizm
 * @since 2018-06-04
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

	@Autowired
	private RolePermissionService rolePermissionService;

	public Role findByName(String name) {
		return super.selectOne(new EntityWrapper<Role>().eq("name", name));
	}

	/**
	 * 查询所有的角色
	 *
	 * @return
	 */
	@Cacheable
	public List<Role> findAll() {
		return super.selectList(Condition.empty());
	}

	/**
	 * 删除角色
	 *
	 * @param id
	 */
	@CacheEvict(allEntries = true)
	public void deleteById(Integer id) {
		super.deleteById(id);
	}

	/**
	 * 根据id查找角色
	 *
	 * @param id
	 * @return
	 */
	@Cacheable
	public Role selectById(Serializable id) {
		return super.selectById(id);
	}

	@CacheEvict(allEntries = true)
	public void save(Role role, List<Integer> permissionIds) {
		super.insert(role);
		if (permissionIds == null) {
			return;
		}
		if (!CollectionUtils.isEmpty(permissionIds)) {
			List<RolePermission> rolePerms = new ArrayList<>();
			for (Integer permId : permissionIds) {
				rolePerms.add(new RolePermission().setRoleId(role.getId()).setPermissionId(permId));
			}
			rolePermissionService.insertBatch(rolePerms);
		}
	}

}
