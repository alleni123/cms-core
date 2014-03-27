package com.lj.core.dao.topic;

import java.util.List;

import com.lj.basic.dao.IBaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Keyword;
import com.lj.core.model.Topic;

public interface ITopicDao extends IBaseDao<Topic>
{		
	
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
		 * @return
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
