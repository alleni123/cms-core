package com.lj.core.model;




import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
 

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="t_user")
@SequenceGenerator(name="TestSEQ",sequenceName="user_Seq",allocationSize=1,initialValue=100)
public class User {
	private int id;
	/**
	 * 用户登录名称
	 */
	private String username;
	
	/**
	 * 用户登录密码
	 */
 
	private String password;
	
	/**
	 * 用户昵称
	 */
	private String	nickname;
	
	/**
	 * 用户邮箱地址
	 */
	private String	email;
	
	/**
	 * 用户手机
	 */
	private String	phone;
	
	/**
	 * 用户状态: 0表示停用， 1表示启用
	 */
	private int	status;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
 
 

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TestSEQ")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@NotNull(message="用户名不能为空")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotNull(message="用户密码不能为空")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Email(message="邮件格式不正确")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	@Column(name="create_date")
	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public User(int id, String username, String password, String nickname,
			String email, String phone, int status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.status = status;
		
	}

	public User() {
		super();
	}
	
	
	
	
	
	
	
}
