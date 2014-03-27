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
import com.lj.basic.test.util.EntitiesHelper;
 
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.Group;
import com.lj.core.model.GroupChannel;
 


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestGroupDao extends AbstractDbUnitTestCase{
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Inject
	private IGroupDao groupDao;
	
	@Inject
	private IChannelDao channelDao;
	
	@Before
	public void setUp() throws IOException, DatabaseUnitException, SQLException{
		System.out.println(1);
		Session s=sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		 this.backupCustomTable(new String[]{"t_group_channel","t_user","t_channel","t_group","t_user_group"});
		 IDataSet ds=createDataSet("groupChannel");
		 
		  
		 
		 DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
		 SystemContext.removeAll();
	}
	
	@Test
	public void testloadGroupChannel(){
		GroupChannel groupChannel= groupDao.loadGroupChannel(2, 400);
		Assert.assertEquals(groupChannel.getId(), 1);
	}
	
	@Test
	public void testAddGroupChannel(){
		Group group=groupDao.load(2);
		assertNotNull(group.getId()+group.getName());
		
		Channel channel=channelDao.load(3);
		
	 
		groupDao.addGroupChannel(group, channel);
		groupDao.clearGroupChannel(2);
	
		GroupChannel gc=groupDao.loadGroupChannel(2,3);
		
		int seqVal=EntitiesHelper.getSequenceNextVal("Test_Seq")-1;
		gc=(GroupChannel) this.sessionFactory.getCurrentSession().get(GroupChannel.class, seqVal);
	 
		
		assertNotNull(gc);
		assertNotNull(gc.getId());
		//System.out.println(gc.getId());
		
	}
	
	
	@Test
	public void testGenerateGroupChannelTree(){
		List<ChannelTree> channelTrees=groupDao.generateGroupChannelTree(1);
		System.out.println(channelTrees);
	}
	
	@Test
	public void testGenerateUserChannelTree(){
		List<ChannelTree> channelTrees=groupDao.generateUserChannelTree(2);
		System.out.println(channelTrees);
	}
	
	@Test
	public void testClearGroupChannel(){
		groupDao.clearGroupChannel(1);
		assertNull(groupDao.loadGroupChannel(1, 8));
		
		
	}
	
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
		SessionHolder sh=(SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s=sh.getSession();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	 //  	this.resumeTable();
	
	
	
	}
	
	
	
	
	
	
	
}
