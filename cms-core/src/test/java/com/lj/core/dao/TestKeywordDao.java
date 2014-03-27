package com.lj.core.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


import org.dbunit.DatabaseUnitException;
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
import org.wicketstuff.jsr303.util.Assert;

import static junit.framework.Assert.*;

import com.lj.basic.model.Pager;
import com.lj.basic.test.util.AbstractDbUnitTestCase;
import com.lj.basic.test.util.Junit4ClassRunner;
import com.lj.basic.util.MyLog4jLogger;
import com.lj.core.dao.topic.IKeywordDao;
import com.lj.core.model.Keyword;

//@RunWith(Junit4ClassRunner.class)

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestKeywordDao extends AbstractDbUnitTestCase
{

	@Inject
	private SessionFactory sessionFactory;

	@Inject
	private IKeywordDao keywordDao;

	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException
	{
		// 此时最好不要使用Spring的Transactional来管理，因为dbunit是通过jdbc来处理connection，再使用spring在一些编辑操作中造成问题
		Session session = sessionFactory.openSession();

		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));

		IDataSet ds = createDataSet("topic");
		System.out.println(ds);
		
		 DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	}

	@Test
	public void testClearNoUseKeyword()
	{	
		List<Keyword>keywordsBeforeclear=keywordDao.findNoUseKeyword().getDatas();
		assertEquals(keywordsBeforeclear.size(), 3);
		
		
		keywordDao.clearNoUseKeyword();
		List<Keyword>keywords=keywordDao.findNoUseKeyword().getDatas();
		assertEquals(keywords.size(), 0);
	}
	
	@Test
	public void testFindNoUseKeyword(){
		Pager<Keyword> keys=keywordDao.findNoUseKeyword();
		MyLog4jLogger.debug("hello debug");
		MyLog4jLogger.debug("Keyword not userd ="+keys.getTotal());
		 assertEquals(keys.getTotal(), 3);
	}
	
	
	/**
	 * 所有被文章引用的关键字。
	 * method-> List<Keyword> findUserKeyword()
	 */
	@Test
	public void testFindUseKeyword(){
		List<Keyword> keywords=keywordDao.findUseKeyword();
		System.out.println(keywords.size());
		for(Keyword k:keywords){
			System.out.println(k.getName()+" "+k.getTimes());
	
			if(k.getName().equals("aaa")){
				assertEquals(k.getTimes(),2);
			}
		}
		assertEquals(keywords.size(), 8);
	}
	
	@Test
	public void testListKeywordByCon(){
		List<Keyword> keywords=keywordDao.listKeywordByCon("bc");
		for(Keyword keyword:keywords){
			System.out.println(keyword);
			
			//2,5,6,7这几个id的包含了bc关键字在name和pinyin简写里面。
			List<Integer> ids=Arrays.asList(2,5,6,7);
			assertTrue(ids.contains(keyword.getId()));
		}
	}
	
	@Test
	public void testAddOrUpdate(){
		keywordDao.addOrUpdate("bb");
		
		//加入精灵论坛， 然后验证首字母缩写是否正确加入
		keywordDao.addOrUpdate("精灵论坛");
		
		//？load语句放这里上面的addOrUpdate语句才会生效？
		Keyword k=keywordDao.load(1);
	 	List<Keyword>keywords=keywordDao.listKeywordByCon("精灵");
	 	assertEquals(keywords.get(0).getNameshortPY(),"jllt");
		
		//update id和times为1的“ab”， 然后验证次数是否为2
		keywordDao.addOrUpdate("ab");
		//Keyword k=keywordDao.load(1); ?放这里不会生效？
		assertEquals(k.getTimes(),2);
	}
 

	@After
	public void tearDown()
	{
		MyLog4jLogger.debug("tearDown");
		SessionHolder sh=(SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s=sh.getSession();
		s.flush();
		
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		
	}

}
