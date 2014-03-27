package com.lj.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Role;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {

	@Override
	public List<Role> listRole() {
		// TODO Auto-generated method stub
		return this.list("from Role");
		
	}

	@Override
	public void deleteRoleUser(int rid) {
		// TODO Auto-generated method stub
		this.updateByHql("delete UserRole ur where ur.role.id=?",rid);
	}

	 

}
