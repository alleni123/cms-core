package com.lj.core.dao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import java.util.Arrays;

import java.util.List;


import javax.inject.Inject;

import junit.framework.Assert;

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


import com.lj.basic.model.Pager;
import com.lj.basic.model.SystemContext;
import com.lj.basic.test.util.AbstractDbUnitTestCase;
import com.lj.basic.test.util.EntitiesHelper;
import com.lj.core.model.Group;
import com.lj.core.model.Role;
import com.lj.core.model.RoleType;
import com.lj.core.model.User;
import com.lj.core.model.UserGroup;
import com.lj.core.model.UserRole;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase {
	@Inject
	private SessionFactory sessionFactory;

	@Inject
	private IUserDao userDao;
	
	@Inject
	private IGroupDao groupDao;
	
	@Inject
	private IRoleDao roleDao;
	
	 

	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		System.out.println("setUp process");

 		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(s));
 
	//	this.backupCustomTable(new String[]{"t_user","t_group","t_role","t_user_group","t_user_role"});

		IDataSet ds=createDataSet("t_user");
		IDataSet topic=createDataSet("topic");
		
		DatabaseOperation.DELETE_ALL.execute(dbunitCon, topic);
		
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		
	//	SystemContext.removeAll();
		
	}
	
	@Test
	public void testListUserRoles() throws DatabaseUnitException, SQLException{
		
		List<Role> actual=Arrays.asList(new Role( 2,"文章发布人员",RoleType.ROLE_PUBLISH),new Role(3,"文章审核人员",RoleType.ROLE_AUDIT));
		List<Role> roles=userDao.listUserRoles(2);
		System.out.println(roles.get(0).getId());
		System.out.println(roles.size());
	 	EntitiesHelper.assertRoles(roles, actual);
		
	}
	
	@Test
	public void testSequence(){
		
	}
	
	
	@Test
	public void testAdd(){
	
		
	
		//User u1=new User(,"lingling","233","ling","alleni@gmail.com","fd",1);
		User u1=new User();
		u1.setUsername("lingling52");
		u1.setEmail("alleni13@gmail.com");
		u1.setPassword("123");
		u1.setNickname("lli");
		u1.setPhone("2323");
		u1.setStatus(1);
		u1.setId(4);
		userDao.add(u1);
		
		int seqVal=EntitiesHelper.getSequenceNextVal("Test_Seq")-1;
		System.out.println("seqVal="+seqVal);
		System.out.println(userDao.load(seqVal).getUsername());
//		System.out.println(userDao.load(id));
	//	System.out.println(userDao.load(1).getUsername());
	}
	
	
	@Test 
	public void testLoadUserGroup(){
		UserGroup ug=userDao.loadUserGroup(2,1);
		User u=userDao.load(2);
		Group g=groupDao.load(1);
		UserGroup actual=new UserGroup(u,g);
		 
		System.out.println(ug);
		assertEquals(ug.getGroup(), actual.getGroup());
		assertEquals(ug.getUser(), actual.getUser());
		
	//	EntitiesHelper.assertUser(, actual)
	}
	
	@Test
	public void testAddUserGroup() throws DatabaseUnitException, SQLException{
	   
	
	//	UserRole ur=userDao.loadUserRole(user.getId(), role.getId());
		
	  	User u=userDao.load(1);
		Group group=groupDao.load(1);
//		Group group1=new Group(15,"hello","hello");
	//	groupDao.add(group1);
	//	System.out.println(groupDao.load(15).getName());
//		User user=userDao.load(1);
//	 	
	 	userDao.addUserGroup(u, group);
//		
 		UserGroup ug=userDao.loadUserGroup(1,1);
 		int seqVal=EntitiesHelper.getSequenceNextVal("Test_Seq")-1;
 	  ug=(UserGroup) this.sessionFactory.getCurrentSession().get(UserGroup.class, seqVal);
 

 		System.out.println(ug.getId());
 		System.out.println(ug.getGroup().getId());
 		System.out.println(ug.getUser().getId());
 		System.out.println(ug.getId());
 		Assert.assertNotNull(ug);
//		
		
	}
	
	@Test
	public void testAddUserRole() throws DatabaseUnitException, SQLException{
	   
	
	//	UserRole ur=userDao.loadUserRole(user.getId(), role.getId());
		
		UserRole userRole=userDao.loadUserRole(1,3);
		assertNull(userRole);
		
	  	User u=userDao.load(1);
		Role role=roleDao.load(3);
//		Group group1=new Group(15,"hello","hello");
	//	groupDao.add(group1);
	//	System.out.println(groupDao.load(15).getName());
//		User user=userDao.load(1);
//	 	
	 	userDao.addUserRole(u, role);
//		
	 	UserRole ur=userDao.loadUserRole(1,3);
 	 	int seqVal=EntitiesHelper.getSequenceNextVal("Test_Seq")-1;
 	 // ug=(UserGroup) this.sessionFactory.getCurrentSession().get(UserGroup.class, seqVal);
 	 	 ur=(UserRole) this.sessionFactory.getCurrentSession().get(UserRole.class, seqVal);
 	 	 
 	 	 
 		 assertNotNull(ur);
 		 assertEquals(ur.getRole().getId(), 3);
 		 assertEquals(ur.getUser().getId(), 1);
//		
		
	}
	
	@Test
	public void testDeleteUserRoles(){
		 UserRole ur=userDao.loadUserRole(2, 3);
		 assertNotNull(ur);
		userDao.deleteUserRoles(2);
		 ur=userDao.loadUserRole(2, 3);
		 assertNull(ur);
	}
	
	@Test
	public void testDeleteUserGroups(){
		 UserGroup ug=userDao.loadUserGroup(3, 1);
		 assertNotNull(ug);
		userDao.deleteUserGroups(3);
		 ug=userDao.loadUserGroup(3, 1);
		 assertNull(ug);
	}
	
	@Test
	public void testFindUser(){
		 
		SystemContext.setSort("id");
		SystemContext.setOrder("asc");
	 	 
		User u1=new User(1,"admin1","123","admin1","alleni1@gmail.com","111111111",1);
		User u2=new User(2,"admin2","123","admin2","alleni2@gmail.com","222222222",1);
		User u3=new User(3,"admin3","123","admin3","alleni3@gmail.com","3333333333",1);
		User u4=new User(4,"admin4","123","admin4","alleni4@gmail.com","4444444444",1);
		List<User>actual=Arrays.asList(u1,u2,u3,u4);
		Pager<User> pages=userDao.findUser();
		 
		assertNotNull(pages);
		assertEquals(pages.getTotal(), 3);
		EntitiesHelper.assertUsers(pages.getDatas(), actual);
		
		}
	 

	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException,
			SQLException {
		System.out.println("tearDown process");
		SessionHolder sh = (SessionHolder) TransactionSynchronizationManager
				.getResource(sessionFactory);
		Session s = sh.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		IDataSet ds=createDataSet("t_user");
	//	DatabaseOperation.DELETE_ALL.execute(dbunitCon, ds);
	//   	this.resumeTable();
	}
}
