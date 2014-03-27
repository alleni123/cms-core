package com.lj.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Group;
import com.lj.core.model.Role;
import com.lj.core.model.RoleType;
import com.lj.core.model.User;
import com.lj.core.model.UserGroup;
import com.lj.core.model.UserRole;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> listUserRoles(int userId)
	{
		// TODO Auto-generated method stub
		String hql = "select ur.role from UserRole ur where ur.user.id=? order by ur.role.id";

		return this.getSession().createQuery(hql).setParameter(0, userId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> listUserRolesId(int userId)
	{
		// TODO Auto-generated method stub
		String hql = "select ur.role.id from UserRole ur where ur.user.id=?";

		return this.getSession().createQuery(hql).setParameter(0, userId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> listUserGroups(int userId)
	{
		String hql = "select ug.group from UserGroup ug where ug.user.id=? ";
		return this.getSession().createQuery(hql).setParameter(0, userId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> listUserGroupId(int userId)
	{

		String hql = "select ug.group.id from UserGroup ug where ug.user.id=?";
		return this.getSession().createQuery(hql).setParameter(0, userId)
				.list();
	}

	@Override
	public UserRole loadUserRole(int userId, int roleId)
	{
		String hql = "select ur from UserRole ur where ur.user.id=? and ur.role.id=?";
		return (UserRole) this.getSession().createQuery(hql)
				.setParameter(0, userId).setParameter(1, roleId).uniqueResult();
	}

	@Override
	public UserGroup loadUserGroup(int userId, int groupId)
	{
		// TODO Auto-generated method stub
		String hql = "select ug from UserGroup ug where ug.user.id=? and ug.group.id=?";
		// String hql =
		// "select ug from UserGroup ug left join fetch ug.user u left join fetch ug.group g where u.id=? and g.id=?";
		return (UserGroup) this.getSession().createQuery(hql)
				.setParameter(0, userId).setParameter(1, groupId)
				.uniqueResult();
	}

	@Override
	public User loadByUsername(String username)
	{
		// TODO Auto-generated method stub
		// 这里如果这样写会出现错误， 找不到node data String
		// hql="select u from User where username=?";
		String hql = "select user from User user where user.username=?";
		// return (User) this.getSession().createQuery(hql).setParameter(0,
		// username).uniqueResult();
		return (User) this.queryObject(hql, username);
	}

	@Override
	public List<User> listRoleUsers(int roleId)
	{
		String hql = "select ur.user from UserRole ur where ur.role.id=?";
		return this.list(hql, roleId);

	}

	@Override
	public List<User> listRoleUsers(RoleType roleType)
	{
		// TODO Auto-generated method stub
		String hql = "select ur.user from UserRole  ur where ur.role.id=?";
		return this.list(hql, roleType);
	}

	@Override
	public void addUserRole(User user, Role role)
	{
		// TODO Auto-generated method stub
		UserRole ur = this.loadUserRole(user.getId(), role.getId());
		if (ur != null)
			return;
		ur = new UserRole();
		ur.setRole(role);
		ur.setUser(user);
		System.out.println(1);
		this.getSession().save(ur);
		System.out.println(1);
	}

	@Override
	public void addUserGroup(User user, Group group)
	{
		// TODO Auto-generated method stub
		UserGroup ug = this.loadUserGroup(user.getId(), group.getId());
		if (ug != null)
		{
			System.out.println("ug!=null");
			return;
		}
		UserGroup userGroup = new UserGroup();

		userGroup.setGroup(group);
		userGroup.setUser(user);
		try
		{
			this.getSession().save(userGroup);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("saved");
	}

	@Override
	public void deleteUserRoles(int uid)
	{
		// TODO Auto-generated method stub
		String hql = "delete UserRole ur    where ur.user.id=?";
		this.updateByHql(hql, uid);
	}

	@Override
	public void deleteUserGroups(int uid)
	{
		// TODO Auto-generated method stub
		String hql = "delete UserGroup ug where ug.user.id=?";
		this.updateByHql(hql, uid);
	}

	@Override
	public Pager<User> findUser()
	{
		return this.find("from User");

	}

	@Override
	public void deleteUserRole(int uid, int rid)
	{
		// TODO Auto-generated method stub
		String hql = "delete UserRole ur where ur.user.id=? and ur.role.id=?";
		this.updateByHql(hql, new Object[] { uid, rid });
	}

	@Override
	public List<User> listGroupUsers(int gid)
	{
		// TODO Auto-generated method stub
		String hql = "select ug.user from UserGroup ug where ug.group.id=?";
		return this.list(hql, gid);
	}

	@Override
	public void deleteUserGroup(int uid, int gid)
	{
		String hql="delete UserGroup ug where ug.user.id=? and ug.group.id=?";
		this.updateByHql(hql, new Object[]{uid,gid});
	}

 

}
