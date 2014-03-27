package com.lj.core.dao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

 
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
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

 
import com.lj.basic.model.SystemContext;
import com.lj.basic.test.util.AbstractDbUnitTestCase;
import com.lj.core.model.Channel;
import com.lj.core.model.Topic;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestChannelDao extends AbstractDbUnitTestCase{
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Inject
	private IChannelDao channelDao;
	
	@Before
	public void setUp() throws IOException, DatabaseUnitException, SQLException{
		System.out.println(1);
		Session s=sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		// this.backupCustomTable(new String[]{"t_channel"});
		 IDataSet ds=createDataSet("topic");
		 IDataSet t_user=createDataSet("t_user");
		 
		 
	 	 ReplacementDataSet rds=new ReplacementDataSet(ds);
	 	 rds.addReplacementObject("null", null);
		 DatabaseOperation.DELETE_ALL.execute(dbunitCon, ds);
		 DatabaseOperation.DELETE_ALL.execute(dbunitCon, t_user);
		 
		 DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,rds);
		 SystemContext.removeAll();
	}
	
	
	@Test
	public void myTest(){
	//	Topic t=topicDao.get(111);
		Channel c=channelDao.load(111);
		System.out.println(c==null);
	}
	
	@Test
	public void testListByParent(){
		//获取所有父节点为根节点的栏目。（0和null都表示根节点）
		List<Channel> cs=channelDao.listByParent(0);
		Assert.assertNotNull(cs);
		Assert.assertEquals(cs.size(), 4);
		
		cs=channelDao.listByParent(null);
		Assert.assertNotNull(cs);
		Assert.assertEquals(cs.size(), 4);
		
		
		//获取所有父节点为系统管理模块的栏目。 会有4个结果，id12~15
		List<Channel> systemManage_channels=channelDao.listByParent(11);
		 Assert.assertNotNull(systemManage_channels);
		 Assert.assertEquals(systemManage_channels.size(), 4);
		 Assert.assertEquals(systemManage_channels.get(0).getCustomLink(), 0);
	}
	
	@Test
	public void testGetMaxOrderByParent(){
		 
		 
		 Integer max=channelDao.getMaxOrderByParent(1);
		 Assert.assertEquals(max, new Integer(2));
		 
		 max=channelDao.getMaxOrderByParent(4);
		 Assert.assertEquals(max, new Integer(3));
		 
		 max=channelDao.getMaxOrderByParent(8);
		 Assert.assertEquals(max, new Integer(2));
		 
		 max=channelDao.getMaxOrderByParent(3);
		Assert.assertEquals(max, new Integer(0));
		 
		Assert.fail("没有完成测试");

		// Integer Max=channelDao.getMaxOrderByParent(2);
	}
	
	@Test
	public void testGenerateTree(){
		System.out.println(channelDao.generateTree());
		
		fail("没有完成测试");
	}
	
	@Test
	public void testGenerateTreeByParent(){
		System.out.println(channelDao.generateByParent(null));
	}
	
	@Test
	public void testListEnabledChannel(){
		System.out.println(channelDao.listEnabledChannel());
		System.out.println(channelDao.listEnabledChannel().size());
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
		SessionHolder sh=(SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s=sh.getSession();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	// 	this.resumeTable();
	
	
	
	}
	
	
	
	
	
	
	
}
