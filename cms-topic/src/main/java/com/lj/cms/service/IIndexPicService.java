package com.lj.cms.service;

import java.util.List;
import java.util.Map;

import com.lj.basic.model.Pager;
import com.lj.core.model.IndexPic;

public interface IIndexPicService
{
	public void add(IndexPic indexPic);
	
	public void update(IndexPic indexPic);
	public void delete(int id);
	public void updateStatus(int id);
	public IndexPic load(int id);
	
	/**
	 * 根据数量来获取首页图片信息
	 * @param num
	 * @return
	 */
	public List<IndexPic> listIndexPicByNum(int num);
	
	
	public Pager<IndexPic> findIndexPic();
	
	/**
	 * 获取所有的首页图片名称
	 * @return
	 */
	public List<String> listAllIndexPicName();
	
	public void cleanNoUseIndexPic(List<String>pics);
	
	public Map<String,Integer> getMaxAndMinPos();
	
	/**
	 * 更新位置，如果新位置比原始位置大->n:5, o:2 -> 将该图片pos换成5.并将3,4,5统统减1</br>
	 * 如果新位置比原始位置小->n:4,o:8 ->将该图片pos换成4，并将4,5,6,7统统加1
	 * @param id 调整位置的图像的id
	 * @param oldPos 原始位置
	 * @param newPos 新位置
	 * @return 返回被更新的row数量
	 */
	public int updatePos(int id, int oldPos, int newPos);
	
}
