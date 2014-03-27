package com.lj.core.dao.topic;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Attachment;


@Repository("attachmentDao")
public class AttachmentDao extends BaseDao<Attachment> implements
		IAttachmentDao
{

	@Override
	public Pager<Attachment> findNoUseAttachment()
	{
		String hql=" select a from Attachment a where a.topic is null";
		return this.find(hql);
	}

	@Override
	public void clearNoUseAttachment()
	{
		String hql="delete  Attachment a where a.topic is null";
		// this.getSession().createQuery(hql).executeUpdate();
		this.updateByHql(hql);
	}

	@Override
	public void deleteByTopic(int topic_id)
	{
		String hql="delete Attachment a where a.topic.id = ?";
		//this.getSession().createQuery(hql).setParameter(0, topic_id).executeUpdate();
		this.updateByHql(hql, topic_id);
	}

	@Override
	public List<Attachment> listByTopic(int topic_id)
	{
		String hql="select a from Attachment a where a.topic.id= ?";
		return this.list(hql, topic_id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> listIndexPic(int num)
	{
		String hql="select a from Attachment a where a.isIndexPic=1 and a.topic.status=1";
		return this.getSession().createQuery(hql).setMaxResults(num).list();
	}

	@Override
	public Pager<Attachment> findChannelPic(int channel_id)
	{
		String hql="select a from Attachment a where a.topic.status=1 and "+
	               " a.topic.channel.id=? and a.id=a.topic.channelPicId";
		return this.find(hql, channel_id);
	}

	@Override
	public List<Attachment> listAttachByTopic(int topic_id)
	{
		String hql="select a from Attachment a where a.topic.id=? and a.isAttach = 1";
		return this.list(hql, topic_id);
	}

	@Override
	public Pager<Attachment> listAllIndexPic()
	{
		String hql="select a from Attachment a where a.topic.status=1 and a.isImg=1";
		return this.find(hql);
	}

	@Override
	public long findNoUseAttachmentNum()
	{
		String hql="select count(*) from Attachment a where a.topic is null";
		return (Long) this.getSession().createQuery(hql).uniqueResult();
	}

	
}
