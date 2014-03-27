package com.lj.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 该类定义了用户组和栏目之间的对应关系， 哪些用户组可以管理哪些栏目，
 * 以实现对用户职责的统一管理
 * @author Administrator
 *
 */
@Entity
@Table(name="t_group_channel")
@SequenceGenerator(name="TestSEQ",sequenceName="group_channel_Seq",allocationSize=1,initialValue=100)
public class GroupChannel {
	private int id;
	private Group group;
	private Channel channel;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TestSEQ")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@ManyToOne
	@JoinColumn(name="g_id")
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@ManyToOne
	@JoinColumn(name="c_id")
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	
}
