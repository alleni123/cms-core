package com.lj.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 发布的文章信息
 * @author Administrator
 *
 */
@Entity
@Table(name="t_topic")
@SequenceGenerator(name="TestSEQ",sequenceName="topic_Seq",allocationSize=1,initialValue=100)
public class Topic {
		
	private int id;
	private String title;
	
	
	/**
	 * 关键字： 通过|来分割不同的关键字
	 */
	private String keyword;
	
	
	/**
	 * 文章的状态，默认为0表示未发布，1表示已发布。
	 */
	private int status;
	
	/**
	 * 是否为推荐文章
	 */
	private int recommend;
	
	/**
	 * 文章的内容
	 */
	private String content;
	
	/**
	 * 发布该文章的用户名称
	 */
	private String publishName;
	
	/**
	 * 文章的发布时间,用来进行排序
	 */
	private Date publishDate;
	
	/**
	 * 文章的创建日期
	 */
 	private Date createDate;
	
	/**
	 * 栏目图片id， 如果该栏目是图片类型的栏目，就会显示这个id的图片
	 * 这个id对应Attachment的id
	 */
	private int channelPicId;
	
	
	
	/**
	 * 文章的发布者
	 */
	private User user;
	
	/**
	 * 文章的摘要
	 */
	private String summary;
	
	/**
	 * 栏目名称 冗余字段  视频：54 17分钟
	 */
	private String channel_name;
	
	
	/**
	 * 文章所在的频道，多对一
	 */
	private Channel channel;
	
	/**
	 * 文章的作者名称，用来显示用户的昵称，冗余字段
	 */
	private String author;
	
	
	public String getChannel_name()
	{
		return channel_name;
	}

	public void setChannel_name(String channel_name)
	{
		this.channel_name = channel_name;
	}

	
	
	
	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getRecommend()
	{
		return recommend;
	}

	public void setRecommend(int recommend)
	{
		this.recommend = recommend;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getPublishName()
	{
		return publishName;
	}

	public void setPublishName(String publishName)
	{
		this.publishName = publishName;
	}
	
	@Column(name="publish_date")
	public Date getPublishDate()
	{
		return publishDate;
	}

	public void setPublishDate(Date publishDate)
	{
		this.publishDate = publishDate;
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
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="channel_id")
	public Channel getChannel()
	{
		return channel;
	}

	public void setChannel(Channel channel)
	{
		this.channel = channel;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser()
	{ 
		// 看起来明显一点的颜色
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
	
	@Column(name="channel_pic_id")
	public int getChannelPicId()
	{
		return channelPicId;
	}

	public void setChannelPicId(int channelPicId)
	{
		this.channelPicId = channelPicId;
	}
	
	
	/**
	 * 用于实现find方法的构造函数。
	 * 视频54 18分钟
	 */
	public Topic(int id, String title, String keyword, int status,
			int recommend, String publishName, Date publishDate,
			String channel_name, String author)
	{
		super();
		this.id = id;
		this.title = title;
		this.keyword = keyword;
		this.status = status;
		this.recommend = recommend;
		this.publishName = publishName;
		this.publishDate = publishDate;
		this.channel_name = channel_name;
		this.author = author;
	}
	
	public Topic()
	{
	}
	
	
	

	public Topic(int id, String title, String keyword, int status, int recommend, Date publishDate, String summary, String channel_name, String author)
	{
		super();
		this.id = id;
		this.title = title;
		this.keyword = keyword;
		this.status = status;
		this.recommend = recommend;
		this.publishDate = publishDate;
		this.summary = summary;
		this.channel_name = channel_name;
		this.author = author;
	}

	@Override
	public String toString()
	{
		return "Topic [id=" + id + ", title=" + title + "]";
	}
	
	
	
	
	
	
	

}
