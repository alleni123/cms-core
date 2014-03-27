package com.lj.cms.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.basic.util.MyLog4jLogger;
import com.lj.basic.util.SecurityUtil;
import com.lj.core.dao.IGroupDao;
import com.lj.core.dao.IRoleDao;
import com.lj.core.dao.IUserDao;
import com.lj.core.model.CmsException;
import com.lj.core.model.Group;
import com.lj.core.model.Role;
import com.lj.core.model.User;

@Service("userService")
public class UserService implements IUserService {
	
	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;
	
	
	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}
	
	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}

	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public void add(User user, Integer[] rids, Integer[] gids) {
		// TODO Auto-generated method stub
		System.out.println(user);
		User tu=userDao.loadByUsername(user.getUsername());
		if(tu!=null) throw new CmsException("添加的用户对象已经存在");
		
	 	user.setPassword(SecurityUtil.md5(user.getUsername()+user.getPassword()));
		MyLog4jLogger.debug("UserService: After md5(user.getUserName()+user.getPassword())," +
				" password="+user.getPassword());
		userDao.add(user);
		
		//添加角色对象
		if(rids!=null){
		for(Integer rid:rids){
			this.addUserRole(user, rid);
		}}
		
		//添加用户组对象
		if(gids!=null){
		for(Integer gid:gids){
			this.addUserGroup(user, gid);
		}}
	}
	
	private void addUserRole(User user, int rid){
		//1.检查角色对象是否存在， 如果不存在则抛出异常
		Role role=roleDao.load(rid);
		if(role==null) throw new CmsException("添加用户的角色不存在");
		//2.检查用户角色对象是否已经存在,如果存在则不添加
		userDao.addUserRole(user, role);
	}
	
	private void addUserGroup(User user, int gid){
		Group group=groupDao.load(gid);
		if(group==null) throw new CmsException("添加用户的组不存在");
		userDao.addUserGroup(user, group);
	}

	@Override
	public void delete(int id) {
		//TODO 需要进行用户是否有文章的判断
		 //1.删除用户管理的角色对象
		userDao.deleteUserRoles(id);
		//2.删除用户管理的组对象
		userDao.deleteUserGroups(id);
		userDao.delete(id);
	}	

	@Override
	public void update(User user, Integer[] rids, Integer[] gids) {
		// TODO Auto-generated method stub
		//1.获取用户已经存在的组id和角色id=== erids->exist role ids.
		List<Integer> erids=userDao.listUserRolesId(user.getId());
		List<Integer> egids=userDao.listUserGroupId(user.getId());
		//2.判断, 如果erids中不存在rids， 就添加
		for(Integer rid:rids){
			if(!erids.contains(rid)){
				this.addUserRole(user, rid);
			}
		}
		
		for(Integer gid:gids){
			if(!egids.contains(gid)){
				this.addUserGroup(user, gid);
			}
		}
		
		//3. 进行删除
		for(Integer erid:erids){
			if(!ArrayUtils.contains(rids,erid)){
				userDao.deleteUserRole(user.getId(),erid);
			}
		}
		for(Integer egid:egids){
			if(!ArrayUtils.contains(gids,egid)){
				userDao.deleteUserGroup(user.getId(),egid);
			}
		}
	}

	@Override
	public void updateStatus(int id) {
		 User u=userDao.load(id);
		 if(u==null) throw new CmsException("修改状态的用户不存在");
		 u.setStatus(u.getStatus()==0?1:0);
		 userDao.update(u);

	}

	@Override
	public Pager<User> findUser() {
		 
		//return userDao.find("from User");
		return userDao.findUser();
	}

	@Override
	public User load(int id) {
		// TODO Auto-generated method stub
		return userDao.load(id);
		 
	}

	@Override
	public List<Role> listUserRoles(int uid) {
		// TODO Auto-generated method stub
		return userDao.listUserRoles(uid);
	}

	@Override
	public List<Group> listUserGroups(int uid) {
		// TODO Auto-generated method stub
		return userDao.listUserGroups(uid);
	}
	
	@Override
	public List<Integer> listUserRoleIds(int id) {
		// TODO Auto-generated method stub
		return userDao.listUserRolesId(id);
	}
	
	@Override
	public List<Integer> listUserGroupIds(int id) {
		// TODO Auto-generated method stub
		return userDao.listUserGroupId(id);
	}
	
	@Override
	public List<User> listRoleUsers(int rid) {
		// TODO Auto-generated method stub
		return userDao.listRoleUsers(rid);
	}
	
	@Override
	public User login(String username, String password) {
		// XXX Auto-generated method stub
		User user=userDao.loadByUsername(username);
		if(user==null) throw new CmsException("用户名或密码不正确");
		
		if(!(SecurityUtil.md5(username,password).equals(user.getPassword()))){
			throw new CmsException("用户名或密码不正确");
		}
		
		int status=user.getStatus();
		if(status==0)
			throw new CmsException("用户已经停用， 请于管理员联系");
		
		
		
		return user;
	}
	
	@Override
	public void update(User user){
		userDao.update(user);
	}
	
	@Override
	public void updatePwd(int uid, String oldPwd, String newPwd) {
		// XXX Auto-generated method stub
		User u=userDao.load(uid);
		if(!(SecurityUtil.md5(u.getUsername(), oldPwd)).equals(u.getPassword())){
			throw new CmsException("原始密码输入不正确");
		}
		
		String _newPwd=(SecurityUtil.md5(u.getUsername(), newPwd));
		u.setPassword(_newPwd);
		userDao.update(u);
	}
 

}
