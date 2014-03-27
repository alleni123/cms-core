package com.lj.core.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import static  org.junit.Assert.*;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;

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
import com.lj.core.dao.topic.IAttachmentDao;
import com.lj.core.model.Attachment;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestAttachmentDao extends AbstractDbUnitTestCase
{
	
 

		@Inject
		private SessionFactory sessionFactory;

		@Inject
		private IAttachmentDao attachmentDao;

		@Before
		public void setUp() throws SQLException, IOException,
				DatabaseUnitException
		{
			// 此时最好不要使用Spring的Transactional来管理，因为dbunit是通过jdbc来处理connection，再使用spring在一些编辑操作中造成问题
			Session session = sessionFactory.openSession();

			TransactionSynchronizationManager.bindResource(sessionFactory,
					new SessionHolder(session));

			IDataSet ds = createDataSet("topic");

			DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
			// DatabaseOperation.UPDATE.execute(dbunitCon, ds);
		}
		
		@Test
		public void testFindNoUseAttachment()
		{
			Pager<Attachment> attachments = attachmentDao.findNoUseAttachment();
			for(Attachment attachment:attachments.getDatas()){
				System.out.println(attachment.getId());
				assertTrue(attachment.getId()==5||attachment.getId()==8);
			}
			
		 
			
		}
		
		
		@Test
		public void testClearNoUseAttachment()
		{
			Pager<Attachment> attachments = attachmentDao.findNoUseAttachment();
			for(Attachment attachment:attachments.getDatas()){
				System.out.println(attachment.getId());
				assertTrue(attachment.getId()==5||attachment.getId()==8);
			}
			
			attachmentDao.clearNoUseAttachment();
			
			Pager<Attachment> attachmentsAfterClear=attachmentDao.findNoUseAttachment();
			assertTrue(attachmentsAfterClear.getTotal()==0);
			
		}
		
		
		@Test
		public void testDeleteByTopic(){
			//删除掉topic_id是3的。 也就是自己id为4的。
			attachmentDao.deleteByTopic(3);
			
			 //获取topic_id是3的。
			List<Attachment> attachments=attachmentDao.listByTopic(3);
			System.out.println(attachments.size());
			assertEquals(attachments.size(), 0);
			 
		}
		
		@Test
		public void testListAttachByTopic(){
			List<Attachment>attachments=attachmentDao.listAttachByTopic(1);
			
			for(Attachment attachment:attachments){
				assertTrue(attachment.getId()==1||attachment.getId()==6);
				assertTrue(attachment.getTopic().getId()==1);
				//System.out.println(attachment.getTopic().getTitle());
			}
		}
		
		@Test
		public void testListIndexPic(){
			//这里不管设置返回多少条，都会通过测试。貌似hibernate会将0和负数看做不限量查询->setMaxResults(0)
			List<Attachment>attachments=attachmentDao.listIndexPic(3);
			
			for(Attachment a:attachments){
				System.out.println(a.getId());
				//1 3 7满足了isIndexPic=1并且topic.status=1
				//9只满足了前者
				assertTrue(a.getId()==1||a.getId()==3||a.getId()==7);
			}
		}
		
		@Test
		public void testFindChannelPic(){
			//频道id为7的，包含了topic 1 4 5 ->a.topic.channel.id=7
			//而topic的status为1的， 只有topic1和topic4 ->a.topic.status=1
			//最后attachment满足a.id=a.topic.channelPicId的就只有1和7. ->a.id=a.topic.ChnnelPicId
			//此方法是不从t_channel表查询任何数据的
			Pager<Attachment>attachments=attachmentDao.findChannelPic(7);
			//System.out.println(attachments.size());
			for(Attachment a:attachments.getDatas()){
				System.out.println(a.getId());
				assertTrue(a.getId()==1||a.getId()==7);
			}
		}
		
		@Test
		public void testListAllIndexPic(){
			Pager<Attachment> atts=attachmentDao.listAllIndexPic();
			System.out.println(atts.getSize());
		}

		@After
		public void tearDown() throws DatabaseUnitException, SQLException
		{
			MyLog4jLogger.debug("tearDown");
			SessionHolder sh = (SessionHolder) TransactionSynchronizationManager
					.getResource(sessionFactory);
			Session s = sh.getSession();
			s.flush();
			TransactionSynchronizationManager.unbindResource(sessionFactory);
			IDataSet ds = createDataSet("topic");
			DatabaseOperation.DELETE_ALL.execute(dbunitCon, ds);
		}

	}

 
