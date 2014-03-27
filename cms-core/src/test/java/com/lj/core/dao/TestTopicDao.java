package com.lj.core.dao;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.lj.basic.model.Pager;
import com.lj.basic.test.util.AbstractDbUnitTestCase;
import com.lj.basic.util.MyLog4jLogger;
import com.lj.core.dao.topic.ITopicDao;
import com.lj.core.model.Topic;


/**
 *  54 6分钟
 * @author Administrator
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestTopicDao extends AbstractDbUnitTestCase
{	
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Inject
	private ITopicDao topicDao;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException{
		//此时最好不要使用Spring的Transactional来管理，因为dbunit是通过jdbc来处理connection，再使用spring在一些编辑操作中造成问题
		Session session=sessionFactory.openSession();
	
	   TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		//this.backupAllTable();
	 
	 
	
	
	 
	 	IDataSet ds=createDataSet("topic");
	 	System.out.println(ds);
		
	    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	//	DatabaseOperation.UPDATE.execute(dbunitCon, ds);
	}
		
	
	
	
	/**
	 * 通过栏目id和状态来获取文章的分页信息。
	 */
	@Test
	public void testFindByChannelAndStatus() throws DatabaseUnitException, SQLException{
//		IDataSet ds=createDataSet("topic");
//		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
		Pager<Topic>topics=topicDao.find(7, null, 1);
		 Assert.assertEquals(topics.getTotal(),2);
		 
	}
	@Test
	public void testSearchTopicByKeyword(){
		Pager<Topic> topics=topicDao.searchTopicByKeyword("aa");
		System.out.println(topics);
		for(Topic topic:topics.getDatas()){
			System.out.println(topic);
			System.out.println(topic.getKeyword());
		}
		
		System.out.println(topics.getSize());
		Assert.assertEquals(topics.getTotal(), 3);
	}
	
	/**
	 * 根据某个条件来检索，该条件会在标题(title)，内容(content)和摘要(summary)中检索。
	 */
	@Test
	public void testSearchTopic(){
		Pager<Topic> topics=topicDao.searchTopic("b");
		for(Topic topic:topics.getDatas()){
			Assert.assertTrue(topic.getId()==3||topic.getId()==1);
		}
		Assert.assertEquals(topics.getTotal(), 2);
	}
	
	/**
	 * 检索某个栏目的推荐文章，如果channel_id为空，表示检索全部的文章
	 */
	@Test
	public void testFindRecommendTopic(){
		Pager<Topic> topics=topicDao.findRecommendTopic(null);
		for(Topic topic:topics.getDatas()){
			//System.out.println(topic);
			Assert.assertTrue(topic.getId()==1||topic.getId()==4);
		}
	}
	
	
	
	@After
	public void tearDown(){
		MyLog4jLogger.debug("tearDown");
		
		SessionHolder sh = (SessionHolder) TransactionSynchronizationManager
				.getResource(sessionFactory);
		Session s = sh.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	   	
	}
	
}
