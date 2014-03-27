package com.lj.cms.service;

import java.util.List;

import com.lj.core.model.Role;
import com.lj.core.model.User;

public interface IRoleService {
	public void add(Role role);
	
	/**
	 * 删除该角色
	 * @param id
	 */
	public void delete(int id);
	public void update(Role role);
	public Role load(int id);
	
	public List<Role> listRole();
	
	/**
	 * 删除该角色组下的所有用户
	 * @param id
	 */
	public void deleteRoleUsers(int id);
	 
}
