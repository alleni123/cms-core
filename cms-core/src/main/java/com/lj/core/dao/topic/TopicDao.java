package com.lj.core.dao.topic;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Keyword;
import com.lj.core.model.Topic;

@Repository("topicDao")
public class TopicDao extends BaseDao<Topic> implements ITopicDao
{

	@Override
	public Pager<Topic> find(Integer channel_id, String title, Integer status)
	{
		return find(null, channel_id, title, status);
	}

	/**
	 * 视频54 23分钟
	 * 
	 * @return
	 */
	private String getTopicSelect()
	{	
		//注意这里new Topic()里面的参数的顺序必须和Topic.java里面的构造函数的顺序一致，否则会出现赋值错误。
		return "select new Topic (t.id, t.title, t.keyword, t.status, t.recommend,t.publishName, t.publishDate, t.channel_name,t.author) ";

	}

	@Override
	public Pager<Topic> find(Integer user_id, Integer channel_id, String title,
			Integer status)
	{

		// 这里如果写成select t from Topic where 1=1,就会报错：
		// Unable to resolve path [t.status], unexpected token [t]
		// [select count(*) from com.lj.core.model.Topic where 1=1
		// and t.status=1]
		// 并且会发出多条sql语句。

		// 这个长hql语句写于视频54 20分钟
		//2/25修改。 如果status=2，则表示提取所有文章，不论是否发布。
		String hql = getTopicSelect() + " from Topic t   where 1=1";
		if (status != null)
		{	
			hql = (status==2? hql:hql+" and t.status="+status);
			//hql += " and t.status=" + status;
		}

		if (title != null && !title.equals(""))
		{
			hql += " and t.title like '%" + title + "%'";
		}

		if (user_id != null && user_id > 0)
		{
			hql += " and t.user.id=" + user_id;
		}
		if (channel_id != null && channel_id > 0)
		{
			hql += " and t.channel.id=" + channel_id;
		}

		return find(hql);
	}

	@Override
	public Pager<Topic> searchTopicByKeyword(String keyword)
	{
		String hql = getTopicSelect() + " from Topic t where t.keyword like '%"
				+ keyword + "%'";
		return this.find(hql);
	}

	@Override
	public Pager<Topic> searchTopic(String con)
	{
		// and后面的()里面的语句不加t也可以
		String hql = getTopicSelect()
				+ " from Topic t where t.status=1 and  (t.title like '%" + con
				+ "%' or t.content like '%" + con + "%'"
				+ " or summary like '%" + con + "%')";
		return this.find(hql);
	}

	@Override
	public Pager<Topic> findRecommendTopic(Integer channel_id)
	{
		String hql = getTopicSelect()
				+ " from Topic t where t.status=1 and t.recommend=1 ";
		if (channel_id == null || channel_id == 0)
		{
			return this.find(hql);
		}
		else
		{
			hql += "  and t.channel.id= ? ";
			return this.find(hql, channel_id);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> listTopicByChannelAndNumber(int channel_id, int num)
	{
		String hql=getTopicSelect()+" from Topic t where t.status=1 and t.channel.id=? order by t.publishDate desc";
		
		return (List<Topic>) this.getSession().createQuery(hql).setParameter(0, channel_id).setFirstResult(0).setMaxResults(num).list();
	}

	@Override
	public boolean isUpdateIndex(int channel_id)
	{
		String hql="select count(*) from Channel c where c.isIndex=1 and c.status=1 and c.id=?";
		Long count = (Long) this.getSession().createQuery(hql).setParameter(0, channel_id).uniqueResult();
		if(count==null||count<0) return false;
		return true;
	}

	@Override
	public Topic loadLatestTopicByColumn(int channel_id)
	{
		String s="select new Topic(t.id,t.title,t.keyword,t.status,t.recommend,t.publishDate,t.summary,t.channel_name,t.author)";
		String hql=s+" from Topic t where t.status =1 and t.channel.id=? order by t.publishDate desc";
		Topic topic=(Topic) this.getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).setParameter(0, channel_id).uniqueResult();
		return topic;
	}

	@Override
	public Pager<Topic> findTopics()
	{
		
		return this.find("from Topic");
	}
	
	/**
	 * 这里分为三种情况。
	 * 第一是要返回所有发布和未发布的。 此时status参数值为2.
	 * 后两种是根据发布情况来返回， 此时stauts为0和1
	 * 这里满足了分页浏览文章的需求。 既可以分页显示发布的文章，也可以分页显示未发布的文章。
	 */
	public Pager<Topic> findTopicsByStatus(Integer status){
		if(status==null||status==2){
			return this.findTopics();
		}
		return this.find("from Topic t where t.status=?", status);
	}

	@Override
	public List<Topic> listTopicsByChannel(int channel_id)
	{
		return this.list("from Topic t where t.channel.id=?",channel_id);
	}
	
	
	
	
	

}
