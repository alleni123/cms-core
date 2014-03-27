package com.lj.cms.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lj.basic.model.Pager;
import com.lj.core.dao.*;
import com.lj.core.model.CmsException;
import com.lj.core.model.Group;
import com.lj.core.model.Role;
import com.lj.core.model.RoleType;
import com.lj.core.model.User;

import static org.easymock.EasyMock.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class TestUserService {
		
	 @Inject
	 private IUserService userService;
	 
	 @Inject
	 private IRoleDao roleDao;
	 
	 @Inject
	 private IUserDao userDao;
	 
	 @Inject
	 private IGroupDao groupDao;
	 
	 private User baseUser=new User(1,"admin1","123","li","all@gmail.com","2323",0);
	 
	 @Test
	 public void testDelete()
	 {	
		 reset(userDao);
		 int uid=2;
		 
		 userDao.deleteUserRoles(uid);
		 expectLastCall();
		 
		 userDao.deleteUserGroups(uid);
		 expectLastCall();
		 
		
		 
		 userDao.delete(uid);
		 expectLastCall();
		 
		 replay(userDao);
		 userService.delete(uid);
		 
		 verify(userDao);
	 }
	 
	 
	 @Test
	 public void testUpdateStatus(){
		 reset(userDao);
		 int uid=1;
		 
		  expect(userDao.load(uid)).andReturn(baseUser);
		 
 		  userDao.update(baseUser);
 		  expectLastCall();
 		  replay(userDao);
 		  
 		  
 		  userService.updateStatus(uid);
 		 
 	  
 		  
 		  
 		  Assert.assertEquals(baseUser.getStatus(), 1);
 	  
 		  verify(userDao);
		 
	 }
	 
	 
	 @Test
	 public void testFindUser(){
		 reset(userDao);
		  
		 expect(userDao.findUser()).andReturn(new Pager<User>());
		 
		 
		 replay(userDao);
		 
		 
		 
		 userService.findUser();
		 
		 verify(userDao);
	 }
	 
	 @Test
	 public void testAdd(){
		 reset(userDao, groupDao, roleDao);
		 
		 Integer[] rids={1,2};
		 Integer[] gids={2,3};
		 Role r=new Role(1,"管理员",RoleType.ROLE_ADMIN);
		 Group g=new Group(2,"admin_group","hello");
		 expect(userDao.loadByUsername("admin1")).andReturn(null);
		 expect(userDao.add(baseUser)).andReturn(baseUser);
		 expect(roleDao.load(rids[0])).andReturn(r);
		 userDao.addUserRole(baseUser, r);
		 r.setId(2);
		 expect(roleDao.load(rids[1])).andReturn(r);
		 userDao.addUserRole(baseUser, r);
		 
		 expect(groupDao.load(gids[0])).andReturn(g);
		 userDao.addUserGroup(baseUser, g);
		 g.setId(3);
		 expect(groupDao.load(gids[1])).andReturn(g);
		 userDao.addUserGroup(baseUser, g);
		 
		 
		 replay(userDao,groupDao,roleDao);
		 
		 
		 userService.add(baseUser, rids, gids);
		 
		 verify(userDao,roleDao,groupDao);
		 
		 
		 
	 }
	 
	 
	 
	 
	 
	 
	 @Test(expected=CmsException.class)
	 public void testAddNoRole(){
		 reset(userDao, groupDao, roleDao);
		 
		 Integer[] rids={1,2};
		 Integer[] gids={2,3};
		 Role r=new Role(1,"管理员",RoleType.ROLE_ADMIN);
		 Group g=new Group(2,"admin_group","hello");
		 expect(userDao.loadByUsername("admin1")).andReturn(null);
		 expect(userDao.add(baseUser)).andReturn(baseUser);
		 expect(roleDao.load(rids[0])).andReturn(null);
		 userDao.addUserRole(baseUser, r);
		 r.setId(2);
		 expect(roleDao.load(rids[1])).andReturn(r);
		 userDao.addUserRole(baseUser, r);
		 
		 expect(groupDao.load(gids[0])).andReturn(g);
		 userDao.addUserGroup(baseUser, g);
		 g.setId(3);
		 expect(groupDao.load(gids[1])).andReturn(g);
		 userDao.addUserGroup(baseUser, g);
		 
		 
		 replay(userDao,groupDao,roleDao);
		 
		 
		 userService.add(baseUser, rids, gids);
		 
		 verify(userDao,roleDao,groupDao);
		 
		 
		 
	 }
	 
	 
	 public void testUpdateUser(){
		 reset(userDao, groupDao, roleDao);
		 
		 Integer[] nids={1,2};
		 List<Integer>eids=Arrays.asList(2,3);
		 
		 User u=userService.load(1);
		 
	//	 expect(userDao.listUserRoles(baseUser.getId())).andReturn(value)
	 
		 
		 
	//	 verify(userDao,roleDao,groupDao);
	 }
	 
	 
	 
	 
	 
	 
	 
	 

}
