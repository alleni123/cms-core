package com.lj.core.dao;

import java.util.List;

import com.lj.basic.dao.IBaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.ChannelType;

public interface IChannelDao extends IBaseDao<Channel> {
	/**
	 * 输入父id查询该父栏目下的所有子栏目
	 * @param pid
	 * @return
	 */
	public List<Channel> listByParent(Integer pid);
	
	
	/**
	 * 获取子栏目的最大的排序号
	 * @param pid
	 * @return
	 */
	public int getMaxOrderByParent(Integer  pid);
	 
	/**
	 * 把所有的栏目获取并生成一棵完整的树
	 * @return
	 */
	public List<ChannelTree> generateTree();
	
	
	/**
	 * 根据父类对象获取子类栏目，并且生成树列表
	 * @param pid
	 * @return
	 */
	public List<ChannelTree> generateByParent(Integer pid);
	
	
	/**
	 * 更新栏目的排列orders
	 */
	public void updateSort(Integer[] ids);

	
	public List<Channel> listEnabledChannel();
	
	/**
	 * 视频122 22分钟。 获取所有首页导航栏目，栏目的状态必须为已经启用
	 * @return
	 */
	public List<Channel> listTopNavChannel();
	
	/**
	 * 视频125 2分钟</br>
	 * 获取所有的首页栏目
	 * @return
	 */
	public List<Channel> listAllIndexChannel(ChannelType[] channelType);
	
	
	/**
	 * 所有的可以发布文章的栏目，栏目的状态必须为启用状态
	 * @return
	 */
	public List<Channel> listPublishChannel();
	
	public Channel loadFirstChannelByNav(int pid);
	
	/**
	 * 根据父栏目id获取所有正被使用的Channel. 这个方法被应用于前台显示
	 * @param pid
	 * @return
	 */
	public List<Channel> ListChannelInUseByParent(Integer pid);
	
	
	public void deleteChannelGroups(int channel_id);
	
	public List<ChannelTree> generateTreeEnabled();
	
	public List<Channel> listChannelByType(ChannelType ct);
}
