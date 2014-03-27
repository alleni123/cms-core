package com.lj.cms.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.protocol.http.servlet.UploadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lj.basic.model.Pager;
import com.lj.basic.model.SystemContext;
import com.lj.core.dao.topic.IAttachmentDao;
import com.lj.core.model.Attachment;

@Service("attachmentService")
public class AttachmentService implements IAttachmentService
{
	@Autowired
	private IAttachmentDao attachmentDao;

	public final static String UPLOAD_PATH = "/upload/";
	public final static int IMG_WIDTH = 900;
	public final static int THUMBNAIL_WIDTH = 100;
	public final static int THUMBNAIL_HEIGHT = 80;

	@Override
	public void add(Attachment a, InputStream is) throws IOException
	{
		// 将信息存储到数据库
		attachmentDao.add(a);
		
		// 进行文件的存储
		this.saveFile(a, is);
	}

	/**
	 * 视频67 25分钟</br> 上传文件被存储到resources/upload文件夹中</br>
	 * 缩略图会被存到upload/thumbnail中。
	 * 
	 * @param is
	 * @throws IOException
	 *             有异常就不会进行操作
	 */
	private void saveFile(Attachment attachment, InputStream is)
			throws IOException
	{
		String realPath = SystemContext.getRealPath();
		System.out.println("realPath= "+realPath);
		String path = realPath + "/resources/upload/";
		System.out.println("path= "+path);
		String thumbPath = realPath + "/resources/upload/thumbnail/";
		
		File fp = newFile(path);
		File tfp = newFile(thumbPath);

		path=path+attachment.getNewName();
		System.out.println("path2= "+path);
		thumbPath = thumbPath + attachment.getNewName();

		if (attachment.getIsImg() == 1)
		{	
			System.out.println("isimg");
			BufferedImage oldBi = ImageIO.read(is);
			int width = oldBi.getWidth();
			BufferedImage bi = null;
			
			Builder<BufferedImage> builder = Thumbnails.of(oldBi);

			if (width > IMG_WIDTH)
			{
				// Thumbnails.of(oldBi).scale(THUMBNAIL_WIDTH/width);
				System.out.println("width>img_width");
				builder.scale((double)IMG_WIDTH / (double)width);
			} else{
				builder.scale(1.0f);
			}
			System.out.println("thumbnail finished1");
			builder.toFile(path);
			System.out.println("thumbnail finished1.1");
			// 缩略图的处理
			// 1. 将原图进行压缩
			//这里原本写的是Thumbnails.of(is),但是由于上面is已经被读过， 此时读不出任何内容了。 所以会导致读不出内容。
			//并且不会报错， 代码执行到这里不会继续执行了。 详情见视频68->20分钟
			BufferedImage thumb = Thumbnails.of(oldBi)
					.scale(THUMBNAIL_WIDTH * 1.2 / width).asBufferedImage();
			System.out.println("thumbnail finished2");
			// 2. 进行切割并且保存
			Thumbnails
					.of(thumb)
					.scale(1.0f)
					.sourceRegion(Positions.CENTER, THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT)
					.toFile(new File(thumbPath));
			System.out.println("thumbnail finished3");
		}
		else
		{
			FileUtils.copyInputStreamToFile(is, new File(path));
		}
	}

	/**
	 * File f=new File(path), 如果f不存在， 则调用f.mkdir();
	 * 
	 * @param path
	 * @return
	 */
	private File newFile(String path)
	{
		File f = new File(path);
		if (!f.exists())
		{
			f.mkdirs();
			System.out.println(f.getAbsolutePath()+" is created");
		}
		return f;
	}

	@Override
	public void delete(int id)
	{
		Attachment attach = attachmentDao.load(id);
		attachmentDao.delete(id);
		deleteAttachFiles(attach);
	}

	@Override
	public Attachment load(int id)
	{
		// XXX Auto-generated method stub
		return attachmentDao.load(id);
	}

	@Override
	public Pager<Attachment> findNoUseAttachment()
	{
		return attachmentDao.findNoUseAttachment();
	}

	@Override
	public void clearNoUseAttachment()
	{
		attachmentDao.clearNoUseAttachment();
	}

	@Override
	public List<Attachment> listByTopic(int tid)
	{
		return attachmentDao.listByTopic(tid);
	}

	@Override
	public List<Attachment> listIndexPic(int num)
	{
		return attachmentDao.listIndexPic(num);
	}

	@Override
	public Pager<Attachment> findChannelPic(int cid)
	{
		return attachmentDao.findChannelPic(cid);
	}

	@Override
	public List<Attachment> listAttachByTopic(int tid)
	{
		// XXX Auto-generated method stub
		return attachmentDao.listAttachByTopic(tid);
	}

	@Override
	public void updateIndexPic(int aid)
	{
		Attachment attachment=attachmentDao.load(aid);
		attachment.setIsIndexPic(attachment.getIsIndexPic()==0?1:0);
		System.out.println("service update index pic"+attachment.getIsIndexPic());
		attachmentDao.update(attachment);
	}

	@Override
	public void updateAttachInfo(int aid)
	{	
		System.out.println("service updaeattachinfo");
		Attachment attachment = attachmentDao.load(aid);
		attachment.setIsAttach(attachment.getIsAttach()==0?1:0);
		
		attachmentDao.update(attachment);
	}

	public static void deleteAttachFiles(Attachment attach)
	{
		String realPath = SystemContext.getRealPath();
		realPath += UPLOAD_PATH;
		new File(realPath + attach.getNewName()).delete();
	}

	@Override
	public Pager<Attachment> listAllPic()
	{
		
		return attachmentDao.listAllIndexPic();
	}

	@Override
	public long findNoUseAttachmentNum()
	{
		
		return attachmentDao.findNoUseAttachmentNum();
	}

}
