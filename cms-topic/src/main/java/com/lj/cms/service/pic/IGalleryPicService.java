package com.lj.cms.service.pic;

import com.lj.basic.model.Pager;
import com.lj.core.model.pic.GalleryPic;

public interface IGalleryPicService 
{
	public void add(GalleryPic galleryPic);
	public void update(GalleryPic galleryPic);
	public void updateStatus(int id);
	public void updateTitle(int id, String title);
	public void delete(int id);
	
	public Pager<GalleryPic> find();
	
}
