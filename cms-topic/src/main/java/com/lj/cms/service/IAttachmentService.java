package com.lj.cms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.lj.basic.model.Pager;
import com.lj.core.model.Attachment;

public interface IAttachmentService 
{
	public void add(Attachment a,InputStream is)throws IOException;
	
	
	public void delete(int id);
	
	
	
	public Attachment load(int id);
	
	
	/**
	 * 获取没有被引用的附件
	 * @return
	 */
	public Pager<Attachment> findNoUseAttachment();
	
	
	/**
	 * 获取没有被引用的附件的数量。<br/>
	 * 在数据库中，topic_id为null的就是没有被使用的附件
	 * @return
	 */
	public long findNoUseAttachmentNum();

	
	/**
	 * 清空没有被引用的附件
	 */
	public void clearNoUseAttachment();
	
	
	
	/**
	 * 获取某个文章的附件
	 * @param tid
	 * @return
	 */
	public List<Attachment> listByTopic(int tid);
	
	
	
	/**
	 * 根据一个数量获取首页图片的附件信息
	 * @param num
	 * @return
	 */
	public List<Attachment> listIndexPic(int num);
	
	
	/**
	 * 获取某个栏目中的附件图片信息
	 * @param cid
	 * @return
	 */
	public Pager<Attachment> findChannelPic(int cid);
	
	
	/**
	 * 获取某篇文章的属于附件类型的附件对象
	 * @param tid
	 * @return
	 */
	public List<Attachment> listAttachByTopic(int tid);
	
	/**
	 * 更新图片的主页面状态。 如果不是主页图片，就更改为主页图片</br>
	 * 主页图片为0的话，就改为1， 为1的话就改为0
	 * @param aid
	 */
	public void updateIndexPic(int aid);
	
	public void updateAttachInfo(int aid);
	
	public Pager<Attachment> listAllPic();
	
}
