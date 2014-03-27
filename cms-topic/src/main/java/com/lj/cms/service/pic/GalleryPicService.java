package com.lj.cms.service.pic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.core.dao.pic.IGalleryPicDao;
import com.lj.core.model.pic.GalleryPic;

@Service("galleryPicService")
public class GalleryPicService implements IGalleryPicService
{
	
	@Autowired
	private IGalleryPicDao galleryPicDao;
	
	@Override
	public void add(GalleryPic galleryPic)
	{	
		
		galleryPicDao.add(galleryPic);
	}

 

	@Override
	public void update(GalleryPic galleryPic)
	{
		galleryPicDao.update(galleryPic);
	}



	@Override
	public void updateTitle(int id, String title)
	{
		GalleryPic gp=galleryPicDao.load(id);
		gp.setTitle(title);
		this.update(gp);
	}



	@Override
	public Pager<GalleryPic> find()
	{
		
		return galleryPicDao.findPager();
	}



	@Override
	public void delete(int id)
	{
		galleryPicDao.delete(id);
	}



	@Override
	public void updateStatus(int id)
	{
		GalleryPic gp=galleryPicDao.load(id);
		gp.setStatus(gp.getStatus()==0?1:0);
		galleryPicDao.update(gp);
	}



}
