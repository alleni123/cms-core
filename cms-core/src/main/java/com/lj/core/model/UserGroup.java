package com.lj.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="t_user_group")
@SequenceGenerator(name="TestSEQ",sequenceName="Test_Seq",allocationSize=1,initialValue=100)
public class UserGroup {
	private int id;
	private User user;
	private Group group;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="TestSEQ")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="u_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@ManyToOne
	@JoinColumn(name="g_id")
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public UserGroup( User user, Group group) {
		super();
		
		this.user = user;
		this.group = group;
	}
	
	public UserGroup( ) {
		
	}
	
	
}
