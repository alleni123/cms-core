package com.lj.core.dao.topic;

import java.util.List;

import com.lj.basic.dao.IBaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Attachment;

/**
 * 53_文章管理_02 文章功能dao接口创建 ---  18分钟
 * @author Administrator
 *
 */
public interface IAttachmentDao extends IBaseDao<Attachment>
{
	
	/**
	 * 获取没有被引用(使用)的附件。<br/>
	 * 在数据库中，topic_id为null的就是没有被使用的附件
	 * @return
	 */
	public Pager<Attachment> findNoUseAttachment();
	
	
	/**
	 * 获取没有被引用的附件的数量。<br/>
	 * 在数据库中，topic_id为null的就是没有被使用的附件
	 * @return
	 */
	public long findNoUseAttachmentNum();
	
	
	/**
	 * 清空没有被引用的附件
	 */
	public void clearNoUseAttachment();
	
	/**
	 * 删除某个文章的所有附件
	 * @param topic_id 文章id
	 */
	public void deleteByTopic(int topic_id);
	
	/**
	 * 获取某个文章的附件
	 * @param topic_id
	 * @return
	 */
	public List<Attachment> listByTopic(int topic_id);
	
	/**
	 * 根据一个数量获取首页图片的附件信息<br/>
	 * @param num 表示要查询多少个附件的信息
	 * @return 返回List对象，包含了num个附件
	 */
	public List<Attachment> listIndexPic(int num);
	
	/**
	 * 获取某个栏目中的附件图片信息
	 * @param channel_id
	 * @return
	 */
	public Pager<Attachment> findChannelPic(int channel_id);
	
	
	/**
	 * 获取所有的新闻图片信息
	 * @return
	 */
	public Pager<Attachment> listAllIndexPic();
	
	
	/**
	 * 获取某篇文章的属于附件类型的附件对象<br/>
	 * 和listByTopic的区别是该方法加入了is_attachment<br/>
	 * 如果为1， 则表示该方法在文章中使用。
	 * @param topic_id
	 * @return
	 */
	public List<Attachment> listAttachByTopic(int topic_id);
	
}
