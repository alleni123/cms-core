package com.lj.core.dao;

import java.util.List;

import com.lj.basic.dao.IBaseDao;
import com.lj.core.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
	public List<Role> listRole();
	
	/**
	 * 删除该角色组下的所有用户
	 * @param rid
	 */
	public void deleteRoleUser(int rid);
	
}
