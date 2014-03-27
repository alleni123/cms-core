package com.lj.cms.service;

import java.io.File;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.basic.model.SystemContext;
import com.lj.core.dao.IChannelDao;
import com.lj.core.dao.IUserDao;
import com.lj.core.dao.topic.IAttachmentDao;
import com.lj.core.dao.topic.ITopicDao;
import com.lj.core.model.Attachment;
import com.lj.core.model.Channel;
import com.lj.core.model.CmsException;
import com.lj.core.model.Topic;
import com.lj.core.model.User;


@Service("topicService")
public class TopicService extends BaseDao<Topic> implements ITopicService
{	
	
	@Autowired
	private ITopicDao topicDao;
	@Autowired
	private IAttachmentDao attachmentDao;
	@Autowired
	private IChannelDao channelDao;
	@Autowired
	private IUserDao userDao;
	
	 
	 
	
	
	
	@Override
	public void add(Topic topic, int channel_id, int user_id,
			Integer[] attachIds)
	{
		Channel channel=channelDao.get(channel_id);
		User user=userDao.get(user_id);
		if(channel==null||user==null){
			throw new CmsException("要添加的文章必须有用户和栏目");
		}
		
		topic.setAuthor(user.getNickname());
		topic.setChannel_name(channel.getName());
		topic.setCreateDate(new Date());
		topic.setChannel(channel);
		topic.setUser(user);
		topicDao.add(topic);
		
		addAttach2Topic(topic, attachIds);
	}

	@Override
	public void add(Topic topic, int channel_id, int user_id)
	{
		add(topic, channel_id, user_id, null);
	}

	@Override
	public void delete(int id)
	{
		Topic topic=topicDao.get(id);
		
		//获取文件对象，目的是为了删除磁盘上的文件对象
		List<Attachment>attachs=attachmentDao.listByTopic(id);
		
		
		if(topic==null){
			throw new CmsException("文章不存在");
		}
		attachmentDao.deleteByTopic(id);
		topicDao.delete(id);
		
		//删除硬盘上面的文件
		for(Attachment a:attachs){
			AttachmentService.deleteAttachFiles(a);
		}
		
		
	}

	@Override
	public void update(Topic topic, int channel_id, Integer[] attachIds)
	{
		Channel channel=channelDao.get(channel_id);
		if(channel==null) throw new CmsException("要更新的文章不存在");
		topic.setChannel_name(channel.getName());
		topic.setChannel(channel);
		topicDao.update(topic);
		addAttach2Topic(topic, attachIds);
	}

	@Override
	public void update(Topic topic, int channel_id)
	{
		update(topic, channel_id, null);
	}
	
	@Override
	public void updateStatus(int tid)
	{

		Topic topic=topicDao.load(tid);
		topic.setStatus(topic.getStatus()==0?1:0);
		topicDao.update(topic);
	}

	@Override
	public Pager<Topic> find(Integer channel_id, String title, Integer status)
	{
		return topicDao.find(channel_id, title, status);
	}

	@Override
	public Pager<Topic> find(Integer user_id, Integer channel_id, String title,
			Integer status)
	{
		return topicDao.find(user_id, channel_id, title, status);
	}

	@Override
	public Pager<Topic> searchTopicByKeyword(String keyword)
	{
		return topicDao.searchTopicByKeyword(keyword);
	}

	@Override
	public Pager<Topic> searchTopic(String con)
	{
		return topicDao.searchTopic(con);
	}

	@Override
	public Pager<Topic> findRecommendTopic(Integer channel_id)
	{
		return topicDao.findRecommendTopic(channel_id);
	}
	
	
	private void addAttach2Topic(Topic topic, Integer[] attachIds){
		if(attachIds!=null){
			for(Integer aid:attachIds){
				Attachment attach=attachmentDao.get(aid);
				if(attach==null) continue;
				attach.setTopic(topic);
				//!!这里我觉得挺有意思。 这里只是执行了setTopic, 并没有任何update或者add语句。但是结果这个attach还是被更新了。
				//以后再研究一下为什么
			}
		}
	}

	@Override
	public List<Topic> listTopicByChannelAndNumber(int channel_id, int num)
	{
		return topicDao.listTopicByChannelAndNumber(channel_id, num);
	}

	@Override
	public boolean isUpdateIndex(int channel_id)
	{
		
		return topicDao.isUpdateIndex(channel_id);
	}

	@Override
	public Topic loadLatestTopicByColumn(int channel_id)
	{
		return topicDao.loadLatestTopicByColumn(channel_id);
	}

	@Override
	public Pager<Topic> findTopics()
	{
		
		return topicDao.findTopics();
	}

	@Override
	public Pager<Topic> findTopicsByStatus(Integer status)
	{
		
		return topicDao.findTopicsByStatus(status);
	}

	@Override
	public List<Topic> listTopicsByChannel(int channel_id)
	{
		return topicDao.listTopicsByChannel(channel_id);
	}

	

	

}
