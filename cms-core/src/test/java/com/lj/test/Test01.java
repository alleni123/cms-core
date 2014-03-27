package com.lj.test;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lj.basic.util.HibernateUtil;


public class Test01 extends HibernateDaoSupport
{	
	
	@Inject
	private static SessionFactory sessionFactory;
	
	
	 
	
	public static void main(String[] args)
	{	
		System.out.println(Test01.class.getClassLoader().getResource("t_user.xml").getFile());
		
	}
}
