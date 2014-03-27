package com.lj.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.core.dao.IChannelDao;
import com.lj.core.dao.IGroupDao;
import com.lj.core.dao.IUserDao;
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.CmsException;
import com.lj.core.model.Group;
import com.lj.core.model.GroupChannel;
import com.lj.core.model.User;

@Service("groupService")
public class GroupService implements IGroupService {
	private IGroupDao groupDao;
	private IUserDao userDao;
	private IChannelDao channelDao;
	
	
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	public IGroupDao getGroupDao() {
		return groupDao;
	}
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	public IUserDao getUserDao() {
		return userDao;
	}
	
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(Group group) {
		groupDao.add(group);
	}

	@Override
	public void delete(int id) {
		List<User> users=userDao.listGroupUsers(id);
		if(users!=null&&users.size()>0) 
		throw new CmsException("删除的组中还有用户，不能删除") ;
		
		groupDao.delete(id);
		 
	}

	@Override
	public Group load(int id) {
		return groupDao.load(id);
	}

	@Override
	public void update(Group group) {
		groupDao.update(group);
	}

	@Override
	public List<Group> listGroup() {
		return groupDao.listGroup();
	}

	@Override
	public Pager<Group> findGroup() {
		Pager<Group>datas=groupDao.findPager();
		return datas;
	}

	@Override
	public void deleteGroupUsers(int gid) {
		groupDao.deleteGroupUsers(gid);
	}
	
	@Override
	public List<User> listGroupUsers(int gid) {
		return userDao.listGroupUsers(gid);
	}
	@Override
	public void addGroupChannel(int gid, int cid) {
		Group g=groupDao.load(gid);
		Channel c=channelDao.load(cid);
		if(c==null||g==null) throw new CmsException("要添加的组频道关联对象不存在");
		groupDao.addGroupChannel(groupDao.load(gid),channelDao.load(cid));
	}
	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
		return groupDao.loadGroupChannel(gid, cid);
	}
	@Override
	public void clearGroupChannel(int gid) {
		
		groupDao.clearGroupChannel(gid);
	}
	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		return groupDao.listGroupChannelIds(gid);
	}
	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
		return groupDao.generateGroupChannelTree(gid);
	}
	@Override
	public List<ChannelTree> generateUserChannelTree(int uid) {
		return  groupDao.generateUserChannelTree(uid);
	}
	@Override
	public void deleteGroupChannel(int gid, int cid) {
		groupDao.deleteGroupChannel(gid, cid);
	}

}
