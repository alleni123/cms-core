package com.lj.core.model.pic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="t_gallery_pic")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@SequenceGenerator(name="TestSEQ",sequenceName="gallery_pic_Seq",allocationSize=1,initialValue=100)
public class GalleryPic extends Pic
{
//	private int id;
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TestSEQ")
//	public int getId()
//	{
//		return id;
//	}
//
//	public void setId(int id)
//	{
//		this.id = id;
//	}
}	
