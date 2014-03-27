package com.lj.cms.service;

import java.util.List;

import com.lj.basic.model.Pager;
import com.lj.core.model.Group;
import com.lj.core.model.Role;
import com.lj.core.model.User;

public interface IUserService {
	/**
	 * 添加用户， 需要判断用户名是否存在， 如果存在抛出异常
	 * @param user	用户
	 * @param rids 用户角色id
	 * @param gids	用户组id
	 */
	public void add(User user, Integer[] rids, Integer[]gids);
	
	
	/**
	 * 删除用户， 注意需要把用户和角色和组的对应关系删除
	 * 如果用户存在相应的文章不能删除
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 用户的更新， 如果rids中的角色在用户中已经存在， 就不做操作
	 * 如果rids中的角色在用户中不存在，就要添加
	 * 如果用户中的角色不存在rid中， 则要删除
	 * 对于group同样要做这样的操作
	 * @param user
	 */
	public void update(User user,Integer[] rids, Integer[] gids);
	
	
	/**
	 * 更新用户的状态
	 * @param id
	 */
	public void updateStatus(int id);
	
	/**
	 * 列表用户
	 */
	public Pager<User> findUser();
	
 
	
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	public User load(int id);
	
	/**
	 * 获取用户所有角色信息
	 * @param id 用户的id
	 * @return 根据id查询到的该用户的所有角色信息
	 */
	public List<Role> listUserRoles(int uid);
	
	/**
	 * 获取用户所有组信息
	 * @param uid
	 * @return
	 */
	public List<Group> listUserGroups(int uid);
	
	public List<Integer> listUserRoleIds(int id);
	
	public List<Integer> listUserGroupIds(int id);
	
	public List<User> listRoleUsers(int rid);
	
	public User login(String username, String password);
	
	public void update(User user);
	
	public void updatePwd(int uid, String oldPwd, String newPwd);
	
	

	
	
}
