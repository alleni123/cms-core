package com.lj.core.model;

import java.util.List;


/**
 * 用来显示首页文章信息的。和freemarker结合使用。
 * 
 * 创建于视频125
 * @author Administrator
 *
 */
public class IndexTopic
{
	private int channel_id;
	private String channel_name;
	private List<Topic> topics;

	public int getChannel_id()
	{
		return channel_id;
	}
	public void setChannel_id(int channel_id)
	{
		this.channel_id = channel_id;
	}
	public String getChannel_name()
	{
		return channel_name;
	}
	public void setChannel_name(String channel_name)
	{
		this.channel_name = channel_name;
	}
	public List<Topic> getTopics()
	{
		return topics;
	}
	public void setTopics(List<Topic> topics)
	{
		this.topics = topics;
	}
	
	
	
}
