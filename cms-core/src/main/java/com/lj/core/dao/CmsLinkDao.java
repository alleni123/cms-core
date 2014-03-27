package com.lj.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.CmsLink;


@Repository("cmsLinkDao")
public class CmsLinkDao extends BaseDao<CmsLink> implements ICmsLinkDao
{

	@Override
	public CmsLink add(CmsLink cl)
	{
		Map<String,Integer>m=this.getMinAndMaxPos();
		if(m.get("max")==null){
			cl.setPos(1);
		}else{
			cl.setPos(m.get("max")+1);
		}
		super.add(cl);
		return cl;
	}

	 

	@Override
	public void delete(int id)
	{	
		CmsLink cl=this.load(id);
		String hql="update CmsLink set pos=pos-1 where pos>=?";
		this.updateByHql(hql, cl.getPos());
		super.delete(id);
	}

 
  

	@Override
	public Pager<CmsLink> findByType(String type)
	{
		String hql;
		
		if(type==null||"".equals(type.trim())){
			hql="from CmsLink";
		}else{
			hql="from CmsLink cl where cl.type='"+type+"'";
		}
		hql+=" order by pos";
		
		return this.find(hql);
	}

	@Override
	public List<CmsLink> listByType(String type)
	{
		return this.list("from CmsLink where type=? order by pos",type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> ListAllType()
	{	
		String hql="select type from CmsLink group by type";
		return (List<String>)this.getSession().createQuery(hql).list();
	}

	@Override
	public Map<String, Integer> getMinAndMaxPos()
	{
		String hql="select max(pos),min(pos) from CmsLink";
		Object[] objs=(Object[]) this.getSession().createQuery(hql).uniqueResult();
		Map<String,Integer> mm=new HashMap<String,Integer>();
		mm.put("max", (Integer)objs[0]);
		mm.put("min", (Integer)objs[1]);
		return mm;
	}

	@Override
	public int updatePos(int id, int oldPos, int newPos)
	{	
		CmsLink cl=this.load(id);
		if(oldPos==newPos){
			return 0;
		}
		String hql=null;
		if(oldPos>newPos){
			hql="update CmsLink cl set cl.pos=cl.pos+1 where cl.pos>=? and cl.pos<?";
		}
		else if(oldPos<newPos){
			hql="update CmsLink cl set cl.pos= cl.pos-1 where cl.pos<=? and cl.pos>?";
		}
		int updatedRows=this.updateByHql(hql,new Object[]{newPos,oldPos});
		cl.setPos(newPos);
		super.update(cl);
		return updatedRows;
	}

}
