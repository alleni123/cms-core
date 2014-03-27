package com.lj.cms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.core.dao.ICmsLinkDao;
import com.lj.core.model.CmsLink;


@Service("cmsLinkService")
public class CmsLinkService implements ICmsLinkService
{
	@Autowired
	private ICmsLinkDao cmsLinkDao;
	
	@Override
	public void add(CmsLink cl)
	{
		cmsLinkDao.add(cl);
	}

	@Override
	public void delete(int id)
	{
		cmsLinkDao.delete(id);
	}

	@Override
	public void update(CmsLink cl)
	{
		cmsLinkDao.update(cl);
	}

	@Override
	public CmsLink load(int id)
	{
		
		return cmsLinkDao.load(id);
	}

	@Override
	public Pager<CmsLink> findByType(String type)
	{
		
		return cmsLinkDao.findByType(type);
	}

	@Override
	public List<CmsLink> listByType(String type)
	{
		
		return cmsLinkDao.listByType(type);
	}

	@Override
	public List<String> ListAllType()
	{
		
		return cmsLinkDao.ListAllType();
	}

	@Override
	public Map<String, Integer> getMinAndMaxPos()
	{
		
		return cmsLinkDao.getMinAndMaxPos();
	}

	@Override
	public int updatePos(int id, int oldPos, int newPos)
	{
		
		return cmsLinkDao.updatePos(id, oldPos, newPos);
	}

}
