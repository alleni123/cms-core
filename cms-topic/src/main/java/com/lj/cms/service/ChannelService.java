package com.lj.cms.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.basic.model.SystemContext;
import com.lj.basic.util.PropertiesUtil;
import com.lj.core.dao.IChannelDao;
import com.lj.core.model.BaseInfo;
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.ChannelType;
import com.lj.core.model.CmsException;
import com.lj.core.model.Topic;

@Service("channelService")
public class ChannelService implements IChannelService
{

	@Autowired
	private IChannelDao channelDao;

	@Autowired
	private ITopicService topicService;

	@Override
	public void add(Channel channel, Integer pid)
	{
		Integer orders = channelDao.getMaxOrderByParent(pid);

		if (pid != null && pid > 0)
		{
			Channel parentChannel = channelDao.load(pid);
			if (parentChannel == null)
				throw new CmsException("要添加栏目的父类对象不正确");
			System.out.println(parentChannel);
			channel.setParent(parentChannel);
		}
		channel.setOrders(orders + 1);
		channelDao.add(channel);
	}

	@Override
	public void update(Channel channel)
	{
		channelDao.update(channel);
	}

	@Override
	public void delete(int id)
	{
		// TODO 1.需要判断是否存在子栏目
		List<Channel> cs = channelDao.listByParent(id);
		if (cs != null && cs.size() > 0)
			throw new CmsException("要删除的栏目还有子栏目， 无法删除");

		// TODO 2, 需要判断是否存在文章
		List<Topic> topics = topicService.listTopicsByChannel(id);
		if (topics.size() > 0)
		{
			throw new CmsException("该栏目下还有文章，不能删除");
		}

		// TODO 3. 需要删除和组的关联关系
		channelDao.deleteChannelGroups(id);
		channelDao.delete(id);

	}

	@Override
	public void clearTopic(int id)
	{
		// TODO 实现了文章模块之后才实现该方法

	}

	@Override
	public Channel load(int id)
	{
		return channelDao.load(id);
	}

	@Override
	public List<Channel> listByParent(Integer pid)
	{

		String sort = SystemContext.getSort();
		if (sort == null || "".equals(sort.trim()))
		{
			SystemContext.setSort("c.orders");
			SystemContext.setOrder("asc");

		}

		return channelDao.listByParent(pid);
	}

	@Override
	public List<ChannelTree> generateTree()
	{
		// XXX Auto-generated method stub
		return channelDao.generateTree();
	}

	@Override
	public List<ChannelTree> generateTreeEnabled()
	{

		return channelDao.generateTreeEnabled();
	}

	@Override
	public List<ChannelTree> generateTreeByParent(Integer pid)
	{
		// XXX Auto-generated method stub
		return channelDao.generateByParent(pid);
	}

	@Override
	public void updateSort(Integer[] ids)
	{
		// XXX Auto-generated method stub
		channelDao.updateSort(ids);
	}

	@Override
	public List<Channel> listEnabledChannel()
	{
		// String
		// hql="select c from Channel c where c.status=1 and c.type!="+ChannelType.NAV_CHANNEL;
		return channelDao.listEnabledChannel();
	}

	@Override
	public List<Channel> listTopNavChannel()
	{

		return channelDao.listTopNavChannel();
	}

	@Override
	public List<Channel> listAllIndexChannel(ChannelType[] ct)
	{

		return channelDao.listAllIndexChannel(ct);
	}

	@Override
	public List<Channel> listAllIndexChannel(ChannelType ct)
	{

		return this.listAllIndexChannel(new ChannelType[] { ct });
	}

	@Override
	public Channel loadFirstChannelByNav(int pid)
	{

		return channelDao.loadFirstChannelByNav(pid);
	}

	@Override
	public List<Channel> ListChannelInUseByParent(Integer pid)
	{
		return channelDao.ListChannelInUseByParent(pid);
	}

	// public Pager<Channel> findChannels(){
	// Pager<Channel>pager=channelDao.find("from Channel");
	// return pager;
	// } 这里通过findPager就可以实现

	public Pager<Channel> findChannels()
	{
		return channelDao.findPager();
	}

	@Override
	public List<Channel> listChannelByType(ChannelType ct)
	{

		return channelDao.listChannelByType(ct);
	}

	@Override
	public void addIndexChannel(Channel channel, Integer pid, BaseInfo bi, URL url)
	{
		List<Channel> channels = this.listAllIndexChannel(ChannelType.TOPIC_LIST);
		channels.addAll(this.listAllIndexChannel(ChannelType.TOPIC_IMG));

		int propICN = bi.getIndexChannelNumber();
		if ((channels.size() + 1) <= propICN)
		{
			this.add(channel, pid);
			Properties props = PropertiesUtil.getInstance().load("indexChannel");
			props.setProperty(Integer.toString(channel.getId()), (channels.size() + 1) + "_" + 8);

			// url=ChannelService.class.getClassLoader().getResource("indexChannel.properties");

			WriteProps(props, url);

		}
		else
		{
			throw new CmsException("主页显示的栏目已经达到最大数量");
		}
	}

	@Override
	public void deleteIndexChannel(Integer id,  URL url)
	{
		Properties props = PropertiesUtil.getInstance().load("indexChannel");
		props.remove(id);
		WriteProps(props, url);
		this.delete(id);
	}

	private void WriteProps(Properties props, URL url)
	{

		OutputStream os = null;
		try
		{
			os = new FileOutputStream(url.getFile());
			props.store(os, "");
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (Exception e)
			{

			}
		}
		
		
	}

}
