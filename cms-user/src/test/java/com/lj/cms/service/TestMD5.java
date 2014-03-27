package com.lj.cms.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestMD5 {
	
	public static void main(String[] args) {
		String password="23315";
		System.out.println(password.length());
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(password.getBytes(), 0, password.length());
			BigInteger bi=new BigInteger(1,md.digest());
			System.out.println(bi);
			System.out.println(bi.toString(16));
			System.out.println(password);
			
		} catch (NoSuchAlgorithmException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
	}
}
