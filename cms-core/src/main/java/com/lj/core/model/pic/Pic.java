package com.lj.core.model.pic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



/**
 * 通过继承来实现table。
 * 摘自 http://www.360doc.com/content/11/0906/22/1542811_146326974.shtml
 * @author Administrator
 *
 */
@Entity
@Table(name="t_pic")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@SequenceGenerator(name="TestSEQ",sequenceName="pic_Seq",allocationSize=1,initialValue=100)
public class Pic
{
	private int id;
	
	private String title;
	
	private String subTitle;
	
	private String newName;
	
	private String originalName;
	
	private int status;
	
	private Date createDate;
	
	private long imgSize;

	public long getImgSize()
	{
		return imgSize;
	}

	public void setImgSize(long imgSize)
	{
		this.imgSize = imgSize;
	}

	@Id
	@GeneratedValue//(strategy=GenerationType.SEQUENCE, generator="TestSEQ")
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSubTitle()
	{
		return subTitle;
	}

	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}
	
	@Column(name="new_name")
	public String getNewName()
	{
		return newName;
	}

	public void setNewName(String newName)
	{
		this.newName = newName;
	}
	
	@Column(name="original_name")
	public String getOriginalName()
	{
		return originalName;
	}

	public void setOriginalName(String originalName)
	{
		this.originalName = originalName;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
	
	@Column(name="create_date")
	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	
	
}
