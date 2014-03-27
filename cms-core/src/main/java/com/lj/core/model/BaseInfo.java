package com.lj.core.model;

public class BaseInfo
{
	private String name;
	private String address; //公司地址
	private String zipCode; //邮政编码
	private String recordCode; //备份编码
	private String phone; 
	private String email;
	private String domainName;   //网址
	private int indexPicWidth;
	private int indexPicHeight;
	private int indexPicNumber;  //主页动态图片的数量
	
	/**
	 *  3/16 
	 */
	private int indexChannelNumber;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getZipCode()
	{
		return zipCode;
	}
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}
	public String getRecordCode()
	{
		return recordCode;
	}
	public void setRecordCode(String recordCode)
	{
		this.recordCode = recordCode;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public int getIndexPicWidth()
	{
		return indexPicWidth;
	}
	public void setIndexPicWidth(int indexPicWidth)
	{
		this.indexPicWidth = indexPicWidth;
	}
	public int getIndexPicHeight()
	{
		return indexPicHeight;
	}
	public void setIndexPicHeight(int indexPicHeight)
	{
		this.indexPicHeight = indexPicHeight;
	}
	public String getDomainName()
	{
		return domainName;
	}
	public void setDomainName(String domainName)
	{
		this.domainName = domainName;
	}
	public int getIndexPicNumber()
	{
		return indexPicNumber;
	}
	public void setIndexPicNumber(int indexPicNumber)
	{
		this.indexPicNumber = indexPicNumber;
	}
	public int getIndexChannelNumber()
	{
		return indexChannelNumber;
	}
	public void setIndexChannelNumber(int indexChannelNumber)
	{
		this.indexChannelNumber = indexChannelNumber;
	}
	
	
	
	
	
}
