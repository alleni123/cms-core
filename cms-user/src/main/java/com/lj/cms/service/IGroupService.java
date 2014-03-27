package com.lj.cms.service;

import java.util.List;

import com.lj.basic.model.Pager;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.Group;
import com.lj.core.model.GroupChannel;
import com.lj.core.model.User;

public interface IGroupService {
	public void add(Group group);

	public void delete(int id);

	public Group load(int id);

	public void update(Group group);

	public List<Group> listGroup();

	public Pager<Group> findGroup();

	public void deleteGroupUsers(int rid);

	public List<User> listGroupUsers(int gid);

	/**
	 * 添加GroupChannel对象
	 * 
	 * @param gid
	 * @param cid
	 */
	public void addGroupChannel(int gid, int cid);

	/**
	 * 加载GroupChannel对象
	 * 
	 * @param gid
	 * @param cid
	 * @return
	 */
	public GroupChannel loadGroupChannel(int gid, int cid);

	/**
	 * 清空gid该组所管理的所有栏目
	 * 
	 * @param gid
	 */
	public void clearGroupChannel(int gid);

	/**
	 * 获取某个组的所有管理栏目的id
	 * 
	 * @param gid
	 * @return 一组Integer数组，包含了所有gid关联的Channel的id
	 */
	public List<Integer> listGroupChannelIds(int gid);

	/**
	 * 获取某个组的栏目树 例如用户组id是2->秘书组, 则会获取秘书组所管理的所有栏目的list，通过ChannelTree的形式储存在list中.
	 * 
	 * @param gid
	 *            用户组的id
	 * @return
	 */
	public List<ChannelTree> generateGroupChannelTree(int gid);

	/**
	 * 获取某个用户的栏目树
	 * 
	 * @return
	 */
	public List<ChannelTree> generateUserChannelTree(int uid);

	/**
	 * 删除组栏目
	 */
	public void deleteGroupChannel(int gid, int cid);

}
