package com.lj.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="t_group")
@SequenceGenerator(name="TestSEQ",sequenceName="Group_Seq",allocationSize=1,initialValue=100)
public class Group {
	
	
	/**
	 * 组id
	 */
	private int id;
	
	/**
	 * 组名称
	 */
	private String name;
	
	/**
	 * 组描述信息
	 */
	private String desc;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TestSEQ")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@NotEmpty(message="用户组名不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="group_desc")
	public String getDesc() {
		return desc;
	}
	@Column(name="group_desc")
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Group() {
		super();
	}

	public Group(int id, String name, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	
	
	
	
	
}
