package com.lj.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.core.dao.topic.IKeywordDao;
import com.lj.core.model.Keyword;

@Service("keywordService")
public class KeywordService implements IKeywordService
{

	@Autowired
	private IKeywordDao keywordDao;

	@Override
	public void addOrUpdate(String name)
	{
		keywordDao.addOrUpdate(name);

	}

	@Override
	public List<Keyword> getMaxTimesKeyword(int num)
	{
		List<Keyword> result = new ArrayList<Keyword>();

		List<Keyword> keywords = keywordDao.findUseKeyword();
		
		if(keywords.size()<=num) return keywords;
		
		for (int i = 0; i <= num; i++)
		{
			result.add(keywords.get(i));
		}
		return result;
	}

	@Override
	public List<Keyword> getKeywordByTimes(int num)
	{
		List<Keyword> result = new ArrayList<Keyword>();

		List<Keyword> keywords = keywordDao.findUseKeyword();

		for (Keyword k : keywords)
		{
			if (k.getTimes() >= num)
			{
				result.add(k);
			}
		}

		return result;
	}

	@Override
	public Pager<Keyword> findNoUseKeyword()
	{
		return keywordDao.findNoUseKeyword();
	}

	@Override
	public void clearNoUseKeyword()
	{
		keywordDao.clearNoUseKeyword();
	}

	@Override
	public List<Keyword> findUseKeyword()
	{
		return keywordDao.findUseKeyword();
	}

	@Override
	public List<Keyword> listKeywordByCon(String con)
	{
		return keywordDao.listKeywordByCon(con);
	}

	@Override
	public List<String> listKeywordStringByCon(String con)
	{
		// XXX Auto-generated method stub
		return keywordDao.listKeywordStringByCon(con);
	}

}
