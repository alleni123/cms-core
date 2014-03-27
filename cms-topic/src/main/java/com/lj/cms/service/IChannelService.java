package com.lj.cms.service;

import java.net.URL;
import java.util.List;

import com.lj.core.model.BaseInfo;
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.ChannelType;

public interface IChannelService {
	
	/**
	 * 添加栏目
	 * @param channel
	 * @param pid
	 */
	public void add(Channel channel , Integer pid );
	
	
	/**
	 * 更新栏目
	 * @param channel
	 */
	public void update(Channel channel);
	
	
	/**
	 * 删除栏目
	 * @param id
	 */
	public void delete(int id);
	
	
	/**
	 * 清空栏目中的文章
	 * @param id
	 */
	public void clearTopic(int id);
	
	/**
	 * 
	 */
	public Channel load(int id);
	
	/**
	 * 根据父id加载栏目， 该方法首先检查SystemContext中是否存在排序
	 * 如果没有存在，则把orders加进去
	 * @param pid
	 * @return
	 */
	public List<Channel> listByParent(Integer pid);
	
	
	/**
	 * 把所有的栏目获取并生成一棵完整的树
	 */
	public List<ChannelTree> generateTree();
	
	/**
	 * 根据父类对象获取子类栏目，并且生成树列表
	 */
	public List<ChannelTree> generateTreeByParent(Integer pid);
	
	/**
	 * 获取所有的可以发布文章的栏目， 栏目的状态必须是启用状态</br>
	 * 该方法不会获取导航类型下的栏目。 也就是type不会为0
	 * @return
	 */
	public List<Channel> listEnabledChannel();
	
	public void updateSort(Integer[] ids);
	
	
	/**
	 * 视频122 22分钟。 获取所有首页导航栏目，栏目的状态必须为已经启用
	 * @return
	 */
	public List<Channel> listTopNavChannel();
	
	/**
	 * 视频125 15分钟。 获取所有首页栏目，不包括首页顶部导航栏目
	 * @return
	 */
	public List<Channel> listAllIndexChannel(ChannelType[] ct);
	
	public List<Channel> listAllIndexChannel(ChannelType ct);
	
	public Channel loadFirstChannelByNav(int pid);
	
	public List<Channel> ListChannelInUseByParent(Integer pid);
	
	/**
	 * 应用于channelController中的/treeEnabled.获取所有被启用的channel
	 * @return
	 */
	public List<ChannelTree> generateTreeEnabled();
	
	public List<Channel> listChannelByType(ChannelType ct);

	
	/**
	 * 3.16 如果是添加首页栏目， 则需要验证首页栏目的数量以及修改indexChannel.properties
	 * @param channel
	 * @param pid
	 * @param baseInfo 
	 * @param url 
	 */
	public void addIndexChannel(Channel channel, Integer pid, BaseInfo baseInfo, URL url);
	
	/**
	 * 3.16 删除首页栏目时， 要将indexChannel.properties中的相关内容一并删除
	 * @param channel
	 * @param pid
	 * @param baseInfo
	 * @param url
	 */
	public void deleteIndexChannel(Integer id, URL url);
}
