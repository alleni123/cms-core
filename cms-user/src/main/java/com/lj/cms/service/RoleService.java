package com.lj.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lj.core.dao.IRoleDao;
import com.lj.core.dao.IUserDao;
import com.lj.core.model.CmsException;
import com.lj.core.model.Role;
import com.lj.core.model.User;

@Service("roleService")
public class RoleService implements IRoleService {
	private IRoleDao roleDao;
	private IUserDao userDao;
	
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(Role role) {
		// TODO Auto-generated method stub
		roleDao.add(role);
	}

	@Override
	public void delete(int id) {
		
		List<User> us=userDao.listRoleUsers(id);
		//这里孔浩写的是if(us!=null||us.size()>0)  ，这样是不对的， 因为hibernate.query.list()方法返回的永远不会是null值。
		//if(us!=null||us.size()>0)
		if(us.size()>0)
			throw new CmsException("删除的角色对象中还有用户，不能删除");
	
		roleDao.delete(id);
	}

	@Override
	public void update(Role role) {
		// TODO Auto-generated method stub
		roleDao.update(role);
	}

	@Override
	public Role load(int id) {
		// TODO Auto-generated method stub
		return roleDao.load(id);
	}

	@Override
	public List<Role> listRole() {
		// TODO Auto-generated method stub
		return roleDao.listRole();
	}

	@Override
	public void deleteRoleUsers(int id) {
		// TODO Auto-generated method stub
		roleDao.deleteRoleUser(id);
	}
	
	 

}
