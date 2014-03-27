package com.lj.cms.service;

import java.util.List;

import com.lj.basic.dao.IBaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Topic;

public interface ITopicService extends IBaseDao<Topic>
{
	
	//添加文章开始
	/**
	 * 添加带附件的文章
	 * @param topic 文章
	 * @param channel_id 栏目id
	 * @param user_id 发布者id
	 * @param attachment_ids 文章的附件id数组
	 */
	public void add(Topic topic, int channel_id, int user_id, Integer[] attachIds);
	
	
	/**
	 *  添加不带附件的文章
	 * @param topic 文章
	 * @param channel_id 栏目id
	 * @param user_id 发布者id
	 */
	public void add(Topic topic , int channel_id, int user_id);
	//添加文章结束
	
	
	
	
	/**
	 * 删除文章，要先删除文章的附件信息，再删除附件的文件对象 <br/>
	 * ->先删除数据库里的信息， 最后再删除磁盘里的文件对象。<br/>
	 * 这样如果删除出错，数据库可以回滚。而文件对象也还在磁盘中。
	 * @param id
	 */
	public void delete(int id);
	
	
	
	
	//更新文章开始
	/**
	 * 更新文章，带附件信息
	 * @param topic 文章
	 * @param channel_id 栏目id
	 * @param attachment_ids 文章的附件id数组
	 */
	public void update(Topic topic, int channel_id, Integer[] attachIds);
	
	/**
	 * 更新没有附件信息的文章
	 * @param topic
	 * @param channel_id
	 */
	public void update(Topic topic, int channel_id);
	
	/**
	 * 更新文章的状态
	 * @param tid
	 */
	public void updateStatus(int tid);
	//更新文章结束
	
	
	
	//从ITopicDao开始
	/**
	 * 根据栏目和标题来进行文件的检索
	 * @param channel_id 栏目的id
	 * @param title 文章标题
	 * @return
	 */
	public Pager<Topic> find(Integer channel_id, String title, Integer status);
	
	/**
	 * 根据用户id，栏目id和文章标题进行检索
	 * @param user_id
	 * @param channel_id
	 * @param title
	 * @return 返回用户id所发布的文章
	 */
	public Pager<Topic> find(Integer user_id, Integer channel_id, String title, Integer status);
	
	/**
	 * 根据关键字来进行文件的检索，仅仅只是检索关键字类似的。
	 * @param keyword
	 * @return
	 */
	public Pager<Topic> searchTopicByKeyword(String keyword);
	
	
	/**
	 * 根据某个条件来检索，该条件会在标题，内容和摘要中检索。
	 * @param con
	 * @return
	 */
	public Pager<Topic> searchTopic(String con);
	
	/**
	 * 检索某个栏目的推荐文章，如果channel_id为空，表示检索全部的文章
	 * @param channel_id
	 * @return
	 */
	public Pager<Topic> findRecommendTopic(Integer channel_id);
	
	//ITopicDao结束
	
	
	/**
	 * 视频125 42分钟
	 * 根据栏目和文章的数量获取该栏目中的文章。
	 * @param channel_id
	 * @param num 这个值用于确定取出多少篇文章
	 * @return
	 */
	public List<Topic> listTopicByChannelAndNumber(int channel_id, int num);
	
	public List<Topic> listTopicsByChannel(int channel_id);
	
	public boolean isUpdateIndex(int channel_id);
	
	public Topic loadLatestTopicByColumn(int channel_id);
	
	public Pager<Topic> findTopics();
	
	public Pager<Topic> findTopicsByStatus(Integer status);
}
