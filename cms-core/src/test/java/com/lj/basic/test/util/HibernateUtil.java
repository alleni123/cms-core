package com.lj.basic.test.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;



public class HibernateUtil {
	
	private final static SessionFactory FACTORY=buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		// TODO Auto-generated method stub
		Configuration cfg=new Configuration().configure();
		ServiceRegistry sr= new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory=cfg.buildSessionFactory(sr);
		
		return factory;
	}
	
	public static SessionFactory getSessionFactory(){
		return FACTORY;
	}
	
	public static Session openSession(){
		return FACTORY.openSession();
	}
	
	public  static void close(Session session){
		if(session!=null)
			session.close();
	}
	
	
	
	
}
