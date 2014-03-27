package com.lj.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.IndexPic;

@Repository("indexPicDao")
public class IndexPicDao extends BaseDao<IndexPic> implements IIndexPicDao
{

	@Override
	public IndexPic add(IndexPic ip)
	{
		// 首先让pos大于等于1的地方加1，最后把要添加的位置设置为1，并加入到数据库中
		String hql = "update IndexPic ip set ip.pos=ip.pos+1 where pos>=1";
		this.getSession().createQuery(hql).executeUpdate();
		ip.setPos(1);
		super.add(ip);
		return ip;
	}

	@Override
	public void delete(int id)
	{
		IndexPic ip = this.load(id);
		int pos = ip.getPos();
		String hql = "update IndexPic ip set ip.pos=ip.pos-1 where pos>=?";
		this.getSession().createQuery(hql).setParameter(0, pos);
		super.delete(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexPic> listIndexPicByNum(int num)
	{
		String hql = "from IndexPic order by pos";

		return this.getSession().createQuery(hql).setFirstResult(0).setMaxResults(num).list();
	}

	@Override
	public Pager<IndexPic> findIndexPic()
	{ // 视频84开头被从order by createDate改为order by pos
		return this.find("from IndexPic order by pos");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listAllIndexPicName()
	{
		String hql = "select ip.newName from IndexPic ip";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public Map<String, Integer> getMaxAndMinPos()
	{
		String hql = "select max(pos),min(pos) from IndexPic";
		Object[] obj = (Object[]) this.getSession().createQuery(hql).uniqueResult(); // 这里的uniqueResult获取一个Object[]->[4,1]

		Map<String, Integer> mm = new HashMap<String, Integer>();
		mm.put("max", (Integer) obj[0]);
		mm.put("min", (Integer) obj[1]);
		return mm;

	}

	@Override
	public int updatePos(int id, int oldPos, int newPos)
	{
		IndexPic ip = this.load(id);
		
		if (oldPos == newPos)
		{
			return 0;
		}
		String hql = "";
		if (oldPos > newPos)
		{
			hql = "update IndexPic ip set ip.pos=ip.pos+1 where ip.pos>=? and ip.pos<?";

			// int
			// executeResult=this.getSession().createQuery(hql).setParameter(0,
			// newPos).setParameter(1, oldPos).executeUpdate();
		}
		else if (oldPos < newPos)
		{
			hql = "update IndexPic ip set ip.pos=ip.pos-1 where ip.pos<=? and ip.pos>?";
			// int
			// executeResult=this.getSession().createQuery(hql).setParameter(0,
			// newPos).setParameter(1, oldPos).executeUpdate();
		}
		int updatedRows = this.updateByHql(hql, new Object[] { newPos, oldPos });
		ip.setPos(newPos); //貌似这个set不能放在上面。否则这个会比上面的updateByHql先执行。
		this.update(ip);
		return updatedRows; //这里返回的数值应该是新位置和旧位置之差， 因为被移动的对象本身的位置不被updateByHql所更新。
	}

}
