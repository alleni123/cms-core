package com.lj.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 这是主页的图片的实体类，目前是主页上自动切换的图片框里的图片信息
 * @author Administrator
 *
 */
@Entity
@Table(name="t_index_pic")
@SequenceGenerator(name="TestSEQ",sequenceName="index_pic_Seq",allocationSize=1,initialValue=100)
public class IndexPic
{
	private int id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 位置. 视频84加入
	 */
	private int pos;
	
	
	/**
	 * 子标题
	 */
	private String subTitle;
	
	/**
	 * 首页的链接类型</br>
	 * 0 -表示网内链接，只链接到一个文章节点中。 /topic/xx
	 * 1 -表示外部链接， 需要制定完整的url路径
	 */
	private int linkType;
	
	/**
	 * 链接的地址， 如果是站内链接就用一个文章节点来表示
	 */
	private String linkUrl; 
	
	/**
	 * 图片的新名称，使用当前时间的毫秒数
	 */
	private String newName;
	
	/**
	 * 图片的原始名称
	 */
	private String originalName;
	
	/**
	 * 图片的状态 0为停用，1为启用
	 */
	private int status;
	
	/**
	 * 图片创建日期， 用来排序
	 */
	private Date createDate;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TestSEQ")
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
	
	@Column(name="link_type")
	public int getLinkType()
	{
		return linkType;
	}
	public void setLinkType(int linkType)
	{
		this.linkType = linkType;
	}
	
	@Column(name="link_url")
	public String getLinkUrl()
	{
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl)
	{
		this.linkUrl = linkUrl;
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
	public int getPos()
	{
		return pos;
	}
	public void setPos(int pos)
	{
		this.pos = pos;
	}
	
	
	
	
}
