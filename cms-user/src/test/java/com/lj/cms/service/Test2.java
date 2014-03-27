package com.lj.cms.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lj.core.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_beans.xml")
public class Test2 {
	
	 @Inject
	 private IUserService userService;
	
	@Test
	public void test1(){
		User u=userService.load(1);
		System.out.println(u);
		System.out.println(1);
	}
}
