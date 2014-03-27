package com.lj.core.dao.topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.basic.util.MyLog4jLogger;
import com.lj.basic.util.PinyinUtil;
import com.lj.core.model.Keyword;

@Repository("keywordDao")
public class KeywordDao extends BaseDao<Keyword> implements IKeywordDao
{

	@Override
	public List<Keyword> listChiefKeyword(int num)
	{
		String hql = " from Keyword where times >= ?";
		return this.list(hql, num);
	}
	
	
	/**
	 * 获取所有被文章所使用的关键字。
	 * 其实是getKeywordInUse2Map
	 * @return Map对象，key是String类型的keyword，value是Integer类型的该关键字出现的次数
	 */
	private Map<String, Integer> getExistKeyword2Map()
	{
		String hql = "select t.keyword from Topic t";

		// 这里得到的是包含|符号的结果。 因为我们的关键字是通过|来作为一个String存储的。
		// 例如java|php
		@SuppressWarnings("unchecked")
		List<String> allKeys = this.getSession().createQuery(hql).list();
		
		
		MyLog4jLogger.debug("Before allKeys.remove(null),allKeys" + allKeys);
		// 上面的list()结果会有null值。个别文章在数据库中没有关键字
		
		 
//		while(allKeys.contains(null)){
//			allKeys.remove(null);
//		}
		
		allKeys.removeAll(Collections.singleton(null));
		
		MyLog4jLogger.debug("After allKeys.remove(null),allKeys" + allKeys);

		

		// 返回对象， 包含了所有单个关键字，不再包含|符号。
		Map<String, Integer> result_keys = new HashMap<String, Integer>();
		
		//key在这里是aa|bb|cc这种结构的， 在后面会被split方法分割成单个String
		for (String key : allKeys)
		{
			// 这里必须加上转义字符\\，不然|会被当做‘或’来处理
			String[] keys = key.split("\\|");
			//这里的k就是单个关键字。
			for (String k : keys)
			{	
				// 再次验证将空值去掉
				if ("".equals(k.trim()))
					{continue;}
				
				if (result_keys.containsKey(k))
				{	
					//当关键字k已经
					result_keys.put(k, result_keys.get(k) + 1);
				}
				else
				{
					result_keys.put(k, 1);
				}

			}
		}
		return result_keys;
	}

	/**
	 * 通过select t.keyword from t_topic t, 获取所有文章的关键字
	 * 
	 * @return 所有文章的关键字
	 */
	private Set<String> getExistKeywords()
	{
		 return   getExistKeyword2Map().keySet();

	}

	@Override
	public Pager<Keyword> findNoUseKeyword()
	{
		String hql = " from Keyword where name not in (:key_names)";

		// 得到一组存在的关键字的名称
		Set<String> key_names = getExistKeywords();
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("key_names", key_names);
		return this.findByAlias(hql, alias);
	}

	@Override
	public void clearNoUseKeyword()
	{
		String hql="delete Keyword k where k.name not in (:key_names)";
		Set<String> key_names=getExistKeywords();
		 
		this.getSession().createQuery(hql).setParameterList("key_names", key_names).executeUpdate();
	}
	
	
	 
	@Override
	public List<Keyword> findUseKeyword()
	{
		Map<String,Integer> allKeys=getExistKeyword2Map();
		Set<String>keys=allKeys.keySet();
		List<Keyword> keywords=new ArrayList<Keyword>();
		for(String k:keys){
			
			keywords.add(new Keyword(k,allKeys.get(k)));
		}
		//对ks进行排序。
		Collections.sort(keywords);
		
		return keywords;
	}


	@Override
	public List<Keyword> listKeywordByCon(String con)
	{	
		String hql="from Keyword k where k.name like '%"+con+"%' or k.nameFullPY like '%"+con+"%'" +
				  " or nameshortPY like '%"+con+"%'";
		return this.list(hql);
	}


	@Override
	public void addOrUpdate(String name)
	{
		Keyword k=(Keyword) this.queryObject(" from Keyword where name=?",name);
		 if(k==null){
			 k=new Keyword();
			 k.setName(name);
			 //将汉字转换成pinyin
			 k.setNameFullPY(PinyinUtil.str2Pinyin(name, null));
			 k.setNameshortPY(PinyinUtil.getInitials(name));
			 k.setTimes(1);
			 this.add(k);
		 }else{
			 k.setTimes(k.getTimes()+1);
		 }
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> listKeywordStringByCon(String con)
	{	
		//String hql="from  Keyword k where k.name like '%"+con+"%' or k.nameFullPY like '%"+con+"%'"+
		//		" or k.nameshortPY like '%"+con+"%'";
		String hql="select name from  Keyword  where name like '%"+con+"%' or nameFullPY like '%"+con+"%'"+
				" or nameshortPY like '%"+con+"%'";
		
		return (List<String>) this.getSession().createQuery(hql).list();
	}

}
