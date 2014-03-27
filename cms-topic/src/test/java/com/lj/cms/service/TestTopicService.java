package com.lj.cms.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.lj.basic.model.SystemContext;
import com.lj.basic.test.util.AbstractDbUnitTestCase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestTopicService extends AbstractDbUnitTestCase
{	
	@Autowired
	private ITopicService topicService;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Before
	public void setUp() throws IOException, DatabaseUnitException, SQLException{
		System.out.println(1);
		Session s=sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		 //this.backupCustomTable(new String[]{"t_channel"});
		// IDataSet ds=createDataSet("topic");
		 
		// ReplacementDataSet rds=new ReplacementDataSet(ds);
		 //rds.addReplacementObject("null", null);
		 
		// DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,rds);
	}
	
	
	
	@Test
	public void test01(){
		topicService.add(null, 111, 1, null);
	}
}
