package com.lj.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.Group;
import com.lj.core.model.GroupChannel;
import com.lj.core.model.User;

@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {

	@Override
	public List<Group> listGroup() {
		// TODO Auto-generated method stub
		return  this.list("from Group");
	}

	@Override
	public Pager<Group> findGroup() {
		// TODO Auto-generated method stub
		
		return this.find("from Group");
	}

	@Override
	public void deleteGroupUsers(int gid) {
		// TODO Auto-generated method stub
		System.out.println("gid= "+gid);
		 this.updateByHql("delete UserGroup ug where ug.group.id=?",gid);
		 
	}

	@Override
	public void addGroupChannel(Group group, Channel channel) {
		// XXX Auto-generated method stub
		GroupChannel groupChannel=this.loadGroupChannel(group.getId(), channel.getId());
		System.out.println("groupChannel= "+groupChannel);
		if(groupChannel!=null) return;
		groupChannel=new GroupChannel();
		
		System.out.println("group!="+group.getId()+group.getName());
		System.out.println("channel!="+channel.getId()+channel.getName());
		groupChannel.setGroup(group);
		groupChannel.setChannel(channel);
		
		 try{
			 this.getSession().save(groupChannel);}
				 catch(Exception e){
					 e.printStackTrace();
				 }
		
		
			 
	}

	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
	
		return  (GroupChannel) this.queryObject("from GroupChannel gc where gc.group.id=? and gc.channel.id=?" , new Object[]{gid,cid});
	 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		String hql="select gc.channel.id from GroupChannel gc where gc.group.id=?";
		return  this.getSession().createQuery(hql).setParameter(0, gid).list();
	}

	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
//		select c.id as id,c.name as name, c.pid as pid from t_group_channel gc left join t_channel c on (gc.c_id=c.id) where gc.g_id=400;
		
		String sql = "select c.id as id, c.name as name , c.pid as pid" +
				" from t_group_channel gc left join t_channel c on (gc.c_id=c.id)"+ 
				" where gc.g_id=?";
		
		List<ChannelTree> tree=this.listBySql(sql,gid,ChannelTree.class,false);
		 
		ChannelDao.initTreeNode(tree);
		
		return tree;
	}

	@Override
	public List<ChannelTree> generateUserChannelTree(int uid) {
		// XXX Auto-generated method stub
		String sql="select distinct c.id as id, c.name as name, c.pid as pid"+
		   " from t_group_channel gc left join t_channel c on (gc.c_id=c.id) " +
		   " left join t_user_group ug on (ug.g_id=gc.g_id) "+
		   " where ug.u_id=?";
		List<ChannelTree> tree=this.listBySql(sql,uid,ChannelTree.class,false);
		ChannelDao.initTreeNode(tree);
		return tree;
	}

	@Override
	public void deleteGroupChannel(int gid, int cid) {
		
		this.updateByHql("delete GroupChannel gc where gc.group.id=? and gc.channel.id=?",new Object[]{gid,cid});
	}

	@Override
	public void clearGroupChannel(int gid) {

		this.updateByHql("delete GroupChannel gc where gc.group.id=?", gid);
	}
	
	 

	 

}
