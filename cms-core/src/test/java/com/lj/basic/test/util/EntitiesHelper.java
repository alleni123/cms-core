package com.lj.basic.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.lj.core.model.Role;
import com.lj.core.model.User;

import junit.framework.Assert;



public class EntitiesHelper {
	private static User baseUser = new User(1, "admin1", "123", "admin1", "admin1@gmail.com", "11111", 1);

	public static void assertUser(User expected) {
		Assert.assertNotNull(expected);
	}
	
	public static void assertUser(User expected,User actual){
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getNickname(),actual.getNickname());
		Assert.assertEquals(expected.getEmail(),actual.getEmail());
		Assert.assertEquals(expected.getPhone(), actual.getPhone());
		Assert.assertEquals(expected.getStatus(), actual.getStatus());
		
	}
		

	public static void assertUsers(List<User> expected, List<User> actual) {
		
		for (int i = 0; i < expected.size(); i++) {
			User expectedUser=expected.get(i);
			User actualUser=actual.get(i);
			assertUser(expectedUser, actualUser);
			
		}
	}
	
	
	
	

	public static void assertRole(Role expected) {
		Assert.assertNotNull(expected);
	}
	
	public static void assertRole(Role expected,Role actual){
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getRoleType(), actual.getRoleType());
		 
		
	}
		

	public static void assertRoles(List<Role> expected, List<Role> actual) {
		for (int i = 0; i < expected.size(); i++) {
			Role expectedRole=expected.get(i);
			Role actualRole=actual.get(i);
			assertRole(expectedRole, actualRole);
			
		}
	}
	
	
	public static int getSequenceNextVal(String seqName){
//		String driverName = "oracle.jdbc.driver.OracleDriver"; // 加载JDBC驱动
//        String  URL = "jdbc:oracle:thin:@localhost:1521:ORCL"; // 连接服务器和数据库sample
//        Connection  con;
//        ResultSet rs;
//        int result=0;
//        try{
//        	 Class.forName(driverName);
//            con= DriverManager.getConnection(URL,"lingling","23315");
//            PreparedStatement ps=con.prepareStatement("select "+seqName+".NEXTVAL FROM DUAL");
//             rs=ps.executeQuery();
//             rs.next();
//              result= rs.getInt(1);
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
        
		 int result=0;
		try
		{ Connection con=DbUtil.getCon();
        PreparedStatement ps=con.prepareStatement("select "+seqName+".NEXTVAL FROM DUAL");
        ResultSet rs=ps.executeQuery();
        rs.next();
       
			result = rs.getInt(1);
		}
		catch (SQLException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
        
        return result;
       
	}
}
