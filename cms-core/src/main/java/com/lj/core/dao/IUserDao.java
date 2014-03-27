package com.lj.core.dao;

import java.util.List;

import com.lj.basic.dao.IBaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Group;
import com.lj.core.model.Role;
import com.lj.core.model.RoleType;
import com.lj.core.model.User;
import com.lj.core.model.UserGroup;
import com.lj.core.model.UserRole;

public interface IUserDao extends IBaseDao<User>{
	
	/**
	 * 获取用户的所有角色信息
	 * @param userId
	 * @return
	 */
	public List<Role> listUserRoles(int userId);
	
	
	/**
	 * 获取用户的所有角色的id
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserRolesId(int userId);
	
	
	/**
	 * 获取用户的所有组信息
	 * @param userId  需要获取对象的用户的id
	 * @return
	 */
	public List<Group> listUserGroups(int userId);

	
	/**
	 * 获取用户的所有组ID
	 * @param userId
	 * @return
	 */
	public List<Integer> listUserGroupId(int userId);
	
	/**
	 * 根据用户和角色获取用户角色的关联对象
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserRole loadUserRole(int userId, int roleId);
	
	/**
	 * 根据用户和分组获取用户分组的关联对象
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public UserGroup loadUserGroup(int userId, int groupId);
	
	
	/**
	 * 根据用户名获取用户对象
	 * @param username
	 * @return
	 */
	public User loadByUsername(String username);
	
	
	/**
	 * 根据角色id获取角色用户关联对象
	 * @param roleId
	 * @return
	 */
	public List<User> listRoleUsers(int roleId);
	
	/**
	 * 根据
	 * @param roleType
	 * @return
	 */
	public List<User> listRoleUsers(RoleType roleType);
	
	
	/**
	 * 添加用户角色对象
	 */
	public void addUserRole(User user, Role role);
	
	
	/**
	 * 添加用户组对象
	 * @param user
	 * @param group
	 */
	public void addUserGroup(User user, Group group);
	
	/**
	 * 删除用户的角色信息<br/>
	 * @param uid
	 */
	public void deleteUserRoles(int uid);
	
	
	/**
	 * 删除用户组
	 * @param uid
	 */
	public void deleteUserGroups(int uid);
	
	
	public Pager<User> findUser();
	
	 
	
	/**
	 * 删除用户角色对象 (关联信息)<br/>
	 * delete UserRole ur where ur.user.id=? and ur.role.id=?<br/>
	 * 这里根据用户的id以及角色的id，删除T_USER_ROLE表中的信息<br/>
	 * @param uid
	 * @param rid
	 */
	public void deleteUserRole(int uid, int rid);
	
	
	/**
	 * 删除用户组对象（关联信息）<br/>
	 * delete UserGroup ug where ug.user.id=? and ug.group.id=?<br/>
	 * 这里根据用户的id以及用户组的id，删除T_USER_GROUP中的关联信息<br/>
	 * @param uid
	 * @param gid
	 */
	public void deleteUserGroup(int uid, int gid);
	
	
	
	
	/**
	 * 获取某个组中的用户对象
	 */
	public List<User> listGroupUsers(int gid);
	
	
	 
}

