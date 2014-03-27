package com.lj.cms.service;

import java.util.List;
import java.util.Map;

import com.lj.basic.model.Pager;
import com.lj.core.model.CmsLink;

public interface ICmsLinkService
{
	public void add(CmsLink cl);
	public void delete(int id);
	public void update(CmsLink cl);
	public CmsLink load(int id);
	
	
	/**
	 * 根据类型获取超链接。如果type为空，就获取所有的超链接，排序方法根据pos
	 */
	public Pager<CmsLink> findByType(String type);
	
	/**
	 * 获取某个类型中的所有链接，不进行分页
	 * @param type
	 * @return
	 */
	public List<CmsLink> listByType(String type);
	
	
	/**
	 * 获取所有的超链接类型
	 * @return
	 */
	public List<String> ListAllType();
	
	public Map<String,Integer> getMinAndMaxPos();
	
	public int updatePos(int id, int oldPos, int newPos);
	
}
