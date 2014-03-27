package com.lj.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 文章的关键字
 * @author Administrator
 *
 */
@Entity
@Table(name="t_keyword")
@SequenceGenerator(name="TestSEQ",sequenceName="keyword_Seq",allocationSize=1,initialValue=100)
public class Keyword implements Comparable<Keyword>
{
	private int id;
	
	/**
	 * 关键字的名称
	 */
	private String name;
	
	/**
	 * 被引用的次数
	 */
	private int times;
	
	
	/**
	 * 关键字的全拼
	 * 比如关键字='李三',这里就是'lisan'
	 */
	private String nameFullPY;
	
	
	/**
	 * 关键字的简拼   
	 * 也就是acronym
	 */
	private String nameshortPY;

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TestSEQ")
	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public int getTimes()
	{
		return times;
	}


	public void setTimes(int times)
	{
		this.times = times;
	}

	@Column(name="name_full_py")
	public String getNameFullPY()
	{
		return nameFullPY;
	}

	

	public void setNameFullPY(String nameFullPY)
	{
		this.nameFullPY = nameFullPY;
	}

	
	@Column(name="name_short_py")
	public String getNameshortPY()
	{
		return nameshortPY;
	}


	public void setNameshortPY(String nameshortPY)
	{
		this.nameshortPY = nameshortPY;
	}
	
	
	public Keyword()
	{
	}


	public Keyword(String name, int times)
	{
		super();
		this.name = name;
		this.times = times;
	}


	@Override
	public int compareTo(Keyword o)
	{	
		//先看this.times是否大于o.times,是则返回-1。 
		//如果不大于，则看this.times是否等于o.times，等于则返回0，否则返回1
		return this.times>o.times?-1:(this.times==o.times?0:1);
		

	}


	@Override
	public String toString()
	{
		return "Keyword [id=" + id + ", name=" + name + ", times=" + times
				+ "]";
	}
	
	
	
	
	
}
