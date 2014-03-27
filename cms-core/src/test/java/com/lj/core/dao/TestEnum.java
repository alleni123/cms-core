package com.lj.core.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


import org.junit.Test;

 
import com.lj.basic.util.EnumUtils;
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelType;
import com.lj.core.model.RoleType;
 

public class TestEnum {
	
	@Test
	public void testList(){
		System.out.println(RoleType.values());
		for(RoleType rt:RoleType.values()){
			System.out.println(rt.name());
		}
	}
	
	@Test
	public void testList02(){
		Class<? extends Enum> clz=RoleType.class;
		System.out.println(clz.isEnum());
		
		Class clz2=RoleType.class;
		System.out.println(clz.isEnum());
		 System.out.println(EnumUtils.enum2Ordinal(RoleType.class));
		 System.out.println(EnumUtils.enum2Name(RoleType.class));
		 
	}
	
	@Test
	public void testMap(){
		List<String> actuals=Arrays.asList("ROLE_ADMIN","ROLE_PUBLISH","ROLE_AUDIT");
		List<String> expectes=EnumUtils.enum2Name(RoleType.class);
		System.out.println(actuals.size()==expectes.size()); 
		for(int i=0;i<actuals.size();i++){
			System.out.println(actuals.get(i).equals(expectes.get(i)));
		}
	}
	
	@Test
	public void testEnumProp(){
		String prop="getName";
		try {
			Method m=ChannelType.class.getMethod(prop, null);
			System.out.println(m.invoke(ChannelType.NAV_CHANNEL, null));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ChannelType.NAV_CHANNEL.getName());
	}
	
	@Test
	public void testEnumProp2(){
		System.out.println(EnumUtils.enum2BasicMap(ChannelType.class));
		System.out.println(EnumUtils.enumProp2List(ChannelType.class, "name"));
		System.out.println(EnumUtils.enumPropByOrdinalMap(ChannelType.class, "name"));
		System.out.println(EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
	}
}
