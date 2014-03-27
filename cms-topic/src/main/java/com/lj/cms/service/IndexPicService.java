package com.lj.cms.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.basic.model.SystemContext;
import com.lj.core.dao.IIndexPicDao;
import com.lj.core.model.IndexPic;


@Service("indexPicService")
public class IndexPicService implements IIndexPicService
{
	
	@Autowired
	private IIndexPicDao indexPicDao;

	@Override
	public void add(IndexPic indexPic)
	{	
		indexPic.setCreateDate(new Date());
		indexPicDao.add(indexPic);
	}

	@Override
	public void update(IndexPic indexPic)
	{	
		indexPicDao.update(indexPic);
	}

	@Override
	public void delete(int id)
	{	IndexPic pic=indexPicDao.load(id);
	String realpath=SystemContext.getRealPath();
	System.out.println("realpath= "+ realpath);
	String thumb_path=realpath+"/resources/indexPic/thumbnail/"+pic.getNewName();
	String pic_path=realpath+"/resources/indexPic/"+pic.getNewName();
	 new File(thumb_path).delete();
	 new File(pic_path).delete();
	
	
		indexPicDao.delete(id);
	}

	@Override
	public void updateStatus(int id)
	{
		IndexPic indexPic=indexPicDao.load(id);
		indexPic.setStatus(indexPic.getStatus()==0?1:0);
		indexPicDao.update(indexPic);
	}

	@Override
	public IndexPic load(int id)
	{
		
		return indexPicDao.load(id);
	}

	@Override
	public List<IndexPic> listIndexPicByNum(int num)
	{
		
		return indexPicDao.listIndexPicByNum(num);
	}

	@Override
	public Pager<IndexPic> findIndexPic()
	{
		
		return indexPicDao.findIndexPic();
	}

	@Override
	public List<String> listAllIndexPicName()
	{
		return indexPicDao.listAllIndexPicName();
	}

	@Override
	public void cleanNoUseIndexPic(List<String> pics)
	{
		String realpath=SystemContext.getRealPath();
		File temp=new File(realpath+"/resources/indexPic/temp");
		try
		{  //首先删除临时文件夹
			FileUtils.deleteDirectory(temp);
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		
		//然后删除缩略图
		for(String f:pics){
			new File(realpath+"/resources/indexPic/thumbnail/"+f).delete();
			new File(realpath+"/resources/indexPic/"+f).delete();
		}
		
		
	}

	@Override
	public Map<String, Integer> getMaxAndMinPos()
	{
		
		return indexPicDao.getMaxAndMinPos();
	}

	@Override
	public int updatePos(int id, int oldPos, int newPos)
	{
		
		return indexPicDao.updatePos(id, oldPos, newPos);
	}
	
	
 

}
