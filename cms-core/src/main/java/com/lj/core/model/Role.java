package com.lj.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * 角色对象，用来对应可以访问的功能， 系统中为了简单只定义了管理员,发布人员和审核人员
 * 
 * @author Administrator
 *
 */

@Entity
@Table(name="t_role")
@SequenceGenerator(name="RoleSEQ",sequenceName="Role_Seq",allocationSize=1,initialValue=100)
public class Role {
	
	/**
	 * 角色id
	 */
	private int id;
	
	/**
	 * 角色名称,中文
	 */
	private String name;
	
	/**
	 * 角色的编号， 枚举类型
	 */
	private RoleType roleType;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@NotEmpty(message="角色名称不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	 
	@Enumerated(EnumType.ORDINAL)
	@Column(name="role_type")
	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Role(int id, String name, RoleType roleType) {
		super();
		this.id = id;
		this.name = name;
		this.roleType=roleType;
	}

	public Role() {
		super();
	}
	
	
	
	
	
	
}
