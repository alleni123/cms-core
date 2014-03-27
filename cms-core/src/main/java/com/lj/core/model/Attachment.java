package com.lj.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//这里的original全被我拼写成了orginal...

/**
 * 附件
 * 这里孔浩在解决延迟加载的时候用了一个构造函数
 * 	private String getAttachmentSelect() {
 *	return "select new Attachment(a.id,a.newName,a.oldName,a.type," +
 *				"a.suffix,a.size,a.isIndexPic,a.isImg,a.isAttach,a.topic.id)";
 *	}
 * 他是在构造函数里面调用了setTopicId，通过这种方法避免了发起查询topic的sql语句。
 * 
 * -- 其实只要在多对一的getter上加入一个@ManyToOne(fetch=FetchType.LAZY),开启延迟加载就行了。。
 *   
 * @author Administrator
 *
 */

@Entity
@Table(name="t_attachment")
@SequenceGenerator(name="TestSEQ",sequenceName="Att_Seq",allocationSize=1,initialValue=100)
public class Attachment
{	
	private int id;
	
	/**
	 * 附件上传之后的名称
	 */
	private String newName;
	
	/**
	 * 附件的原始名称
	 */
	private String originalName;
	
	
	/**
	 * 附件的类型，这个类型和contentType类型一直
	 */
	private String type;
	
	/**
	 * 附件的后缀
	 */
	private String filenameExtension;
	
	/**
	 * 附件的大小
	 */
	private long fileSize;
	
	/**
	 * 该附件是否是主页图片
	 */
	private int isIndexPic;
	
	/**
	 * 该图片是否是图片类型. 0表示不是，1表示是。
	 */
	private int isImg;
	
	/**
	 * 附件所属文章
	 */
	private Topic topic;
	
	
	/**
	 * 是否是附件信息，0表示不是，1表示是。 <br/>
	 * 如果是附件信息，就在文章的附件栏进行显示
	 */
	private int isAttach;
	
	
	@Column(name="is_attach")
	public int getIsAttach()
	{
		return isAttach;
	}

	public void setIsAttach(int isAttach)
	{
		this.isAttach = isAttach;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TestSEQ")
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	
	@Column(name="new_name")
	public String getNewName()
	{
		return newName;
	}

	public void setNewName(String newName)
	{
		this.newName = newName;
	}
	
	@Column(name="original_name")
	public String getOriginalName()
	{
		return originalName;
	}

	public void setOriginalName(String originalName)
	{
		this.originalName = originalName;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
	@Column(name="filename_extension")
	public String getFilenameExtension()
	{
		return filenameExtension;
	}

	public void setFilenameExtension(String filenameExtension)
	{
		this.filenameExtension = filenameExtension;
	}

 
	
	public long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(long fileSize)
	{
		this.fileSize = fileSize;
	}

	@Column(name="is_index_pic")
	public int getIsIndexPic()
	{
		return isIndexPic;
	}

	public void setIsIndexPic(int isIndexPic)
	{
		this.isIndexPic = isIndexPic;
	}
	
	@Column(name="is_img")
	public int getIsImg()
	{
		return isImg;
	}
	
	/**
	 * 设置是否是图片附件
	 * @param isImg 0表示不是，1表示是
	 */
	public void setIsImg(int isImg)
	{
		this.isImg = isImg;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="topic_id")
	public Topic getTopic()
	{
		return topic;
	}

	public void setTopic(Topic topic)
	{
		this.topic = topic;
	}
	
	
	
}
