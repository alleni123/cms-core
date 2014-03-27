package com.lj.core.dao.topic;

import java.util.List;

import com.lj.basic.dao.IBaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Keyword;

public interface IKeywordDao extends IBaseDao<Keyword>
{
	
	
	/**
	 * 如果该关键字已经有了，则update该关键字（次数+1）<br/>
	 * 否则添加该关键字到数据库
	 * @param name 关键字名称
	 */
	public void addOrUpdate(String name);
	
	/**
	 * 根据引用次数获取关键字, 获取大于等于num次的关键字
	 */
	public List<Keyword> listChiefKeyword(int num);
	
	
	/**
	 * 查找没有使用的关键字<br/>
	 * 也就是在t_keyword表中有，并关联到t_topic.但是t_topic的content里面并没有<br/>
	 * 实际应该是 findKeywordNotInUse或者是findUnusedKeyword.
	 * @return
	 */
	public Pager<Keyword> findNoUseKeyword();
	
	
	/**
	 * 清空没有使用的关键字,应该是clearUnusedKeywords
	 */
	public void clearNoUseKeyword();
	
	
	/**
	 * 查找正在被文章引用的关键字<br/>
	 * 里面会调用构造函数 Keyword(String name,int times),将关键字的名称以及出现次数赋值<br/>
	 * 实际应该是findKeywordInUse()
	 * @return 返回的List对象是经过降序排序的。被使用最多的关键字在最前
	 */
	public List<Keyword> findUseKeyword();
	
	
	
	/**
	 * 根据某个条件从t_keyword表中查询关键字
	 * @return
	 */
	public List<Keyword> listKeywordByCon(String con);
	
	
	public List<String> listKeywordStringByCon(String con);
	
	
	
	
}
