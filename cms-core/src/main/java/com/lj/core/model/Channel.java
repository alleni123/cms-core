package com.lj.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="t_channel")
@SequenceGenerator(name="TestSEQ",sequenceName="channel_seq",allocationSize=1,initialValue=100)
public class Channel {
	
	public static final String ROOT_NAME = "网站系统栏目";
	public static final int ROOT_ID = 0;
	
	
	
	/**
	 * 栏目的主键
	 */
	private int id;
	
	
	/**
	 * 栏目的名称
	 */
	private String name;
	
	
	/**
	 * 栏目是否是自定义链接,0表示否，1表示是
	 */
	private int customLink;
	
	
	/**
	 * 栏目是否自定义链接，0表示否， 1表示是
	 */
	private String customLinkUrl;
	
	
	/**
	 * 栏目的类型， 枚举类型，该枚举中存在一个name属性用来标识中文的名称
	 */
	private ChannelType type;
	
	
	/**
	 * 是否是首页栏目，0表示否， 1表示是</br>
	 * 首页栏目下面包含一些首页文章， 和顶部导航栏目的区别是首页栏目在index页面中间的content中，而不在页面顶部。
	 */
	private int isIndex;
	
	
	/**
	 * 是否是首页的顶部导航栏目， 0表示否，1表示是
	 */
	private int isTopNav;
	
	
	/**
	 * 是否是推荐栏目， 0表示否， 1表示是
	 */
	private int recommend;
	
	
	/**
	 * 栏目的状态， 0表示停止， 1表示启用
	 */
	private int status;
	
	
	/**
	 * 栏目的序号
	 */
	private int orders;
	
	
	/**
	 * 父类栏目
	 */
	private Channel parent;
	
	/**
	 * 导航序号， 用于在导航网页上进行排序的
	 */
	private int navOrder;

	
	 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pid")
	public Channel getParent() {
		return parent;
	}


	public void setParent(Channel parent) {
		this.parent = parent;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TestSEQ")
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@NotEmpty(message="栏目名称不能为空")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
	@Column(name="custom_link")
	public int getCustomLink() {
		return customLink;
	}


	public void setCustomLink(int customLink) {
		this.customLink = customLink;
	}

	@Column(name="custom_link_url")
	public String getCustomLinkUrl() {
		return customLinkUrl;
	}


	public void setCustomLinkUrl(String customLinkUrl) {
		this.customLinkUrl = customLinkUrl;
	}

	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="type")
	public ChannelType getType() {
		return type;
	}


	public void setType(ChannelType type) {
		this.type = type;
	}

	
	@Column(name="is_index")
	public int getIsIndex() {
		return isIndex;
	}


	public void setIsIndex(int isIndex) {
		this.isIndex = isIndex;
	}

	
	@Column(name="is_top_nav")
	public int getIsTopNav() {
		return isTopNav;
	}


	public void setIsTopNav(int isTopNav) {
		this.isTopNav = isTopNav;
	}

	
	 
	public int getRecommend() {
		return recommend;
	}


	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	
	public int getOrders() {
		return orders;
	}


	public void setOrders(int orders) {
		this.orders = orders;
	}
	
	
	

	@Column(name="nav_order")
	public int getNavOrder()
	{
		return navOrder;
	}


	public void setNavOrder(int navOrder)
	{
		this.navOrder = navOrder;
	}


	@Override
	public String toString()
	{
		return "Channel [id=" + id + ", name=" + name + ", type=" + type
				+ ", status=" + status + "]";
	}


	public Channel()
	{
		// XXX Auto-generated constructor stub
	}


	public Channel(int id, String name, int customLink, String customLinkUrl)
	{
		this.id = id;
		this.name = name;
		this.customLink = customLink;
		this.customLinkUrl = customLinkUrl;
	}


	public Channel(int id, String name)
	{
		super();
		this.id = id;
		this.name = name;
	}


	public Channel(int id, String name, ChannelType type)
	{
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	
	
	
	
	
	 
	 
	
	
	
	
}
