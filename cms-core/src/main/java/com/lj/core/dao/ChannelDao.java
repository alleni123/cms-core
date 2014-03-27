package com.lj.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lj.basic.dao.BaseDao;
import com.lj.basic.model.Pager;
import com.lj.core.model.Channel;
import com.lj.core.model.ChannelTree;
import com.lj.core.model.ChannelType;

/**
 * Channel被使用树形结构来存储并显示给客户端。<br/>
 * ChannelTree规定了ID,NAME,PID, 该对象的集合会在客户端被提取显示。<br/>
 * 
 * Channel的根元素是 id=0, name=网站系统栏目， pid=-1的ChannelTree对象。这个元素并不被存储到数据库中，但是每次加载树结构的时候，
 * 该对象就会被直接放入到集合当中，并作为根元素被显示出来。具体在本类中的initTreeNode方法。<br/>
 * 
 * 
 * 
 * @author Administrator
 *
 */
@Repository("channelDao")
public class ChannelDao extends BaseDao<Channel> implements IChannelDao {

	 

	@Override
	public List<Channel> listByParent(Integer pid) {
		String hql="select c from Channel c left join fetch c.parent cp where cp.id="+pid ;
		if(pid==null||pid==0) hql="select c from Channel c where c.parent is null";
		
		
		return this.list(hql);
	}

	@Override
	public int getMaxOrderByParent(Integer pid) {
		
		String hql="select max(c.orders) from Channel c where c.parent.id= "+pid;
		if(pid==null||pid==0) hql="select max(c.orders) from Channel c where c.parent is null";
		
		Object obj=this.queryObject(hql);
		if(obj==null){
			 
			return 0;
		}
		
		return (Integer)obj;
	}

	@Override
	public List<ChannelTree> generateTree() {
		String sql="select id,name,pid from t_channel  order by orders";
	 
		List<ChannelTree> tree=this.listBySql(sql,ChannelTree.class,false);
		 
		initTreeNode(tree);
		
		
	//	for(ChannelTree ct:tree){
	//		if(ct.getPid()==null) ct.setPid(0);
	//	}
		return tree;
	}
	
	
	/**
	 * 获取文章中要使用的栏目。
	 * 首先要得到所有非导航栏目，然后获取所有的导航栏目。
	 * 最后对比两者，看看有没有导航栏目。</br>
	 * 对比的重点是noNav_child.getPid()==nav_parent.getId(),如果满足这一点，则说明该parent下有非导航栏目，
	 * 则该parent必须被加入树结构中，以便让用户添加属于该栏目的文章。
	 */
	@Override
	public List<ChannelTree> generateTreeEnabled()
	{
		String sql="select id, name, pid from t_channel where status=1 and type!="+ChannelType.NAV_CHANNEL.ordinal()+" order by orders";
		List<ChannelTree> tree=this.listBySql(sql, ChannelTree.class, false);
		String navSql="select id , name, pid from t_channel where status=1 and type="+ChannelType.NAV_CHANNEL.ordinal()+" order by orders";
		List<ChannelTree> navTree=this.listBySql(navSql, ChannelTree.class, false);
		List<ChannelTree> result=new ArrayList<ChannelTree>();
		for(ChannelTree c:tree){
			for(ChannelTree nav:navTree){
				if(c.getPid()==nav.getId()){
					result.add(nav);
				}
			}
		}
		result.addAll(tree);
		initTreeNode(result);
		return  result;
	}
	
	
	
	public static void initTreeNode(List<ChannelTree> channelTrees){
	
		channelTrees.add(0, new ChannelTree(Channel.ROOT_ID, Channel.ROOT_NAME,-1));
		 
		 
		
		for(ChannelTree ct:channelTrees){
			if(ct.getPid()==null)
				ct.setPid(0);
		}
	}
	
	

	@Override
	public List<ChannelTree> generateByParent(Integer pid) {
		if(pid==null||pid==0){
			return this.listBySql("select t.id,t.name,t.pid from t_channel t where t.pid is null order by t.orders", ChannelTree.class, false);
		}else{
			return this.listBySql("select t.id,t.name,t.pid from t_channel t where t.pid= "+pid+" order by t.orders", ChannelTree.class, false);
		}
	 
	}

	@Override
	public void updateSort(Integer[] ids) {
		// XXX Auto-generated method stub
		int index=1;
		String hql="update Channel c set c.orders=? where c.id=?";
		for(Integer id:ids){
			this.updateByHql(hql,new Object[]{index,id});
			index++;
		}
	}

	@Override
	public List<Channel> listEnabledChannel()
	{
		String hql="select c from Channel c where c.status=1 and c.type!="+ChannelType.NAV_CHANNEL.ordinal();
		return this.list(hql);
	}

	@Override
	public List<Channel> listTopNavChannel()
	{
		String hql="select new Channel(c.id, c.name, c.customLink,c.customLinkUrl) from Channel c where c.status=1 and c.isTopNav=1 order by c.navOrder";
		return this.list(hql);
	}

	@Override
	public List<Channel> listAllIndexChannel( ChannelType[] channelType)
	{
		String hql="select new Channel(c.id, c.name) from Channel c where c.status=1 and c.isIndex=1";
		if(channelType!=null){
		String types="";
		for(int i=0;i<channelType.length;i++){
			types +=(Integer.toString(channelType[i].ordinal())+",");
			
		}
		
		if(!types.equals("")){
			//types.subSequence(types.length()-1, types.length());
			types=types.substring(0, types.length()-1);
		}
		
			hql+=" and c.type in ("+types+")";
		}
		
		List<Channel> channels=this.list(hql);
		
		return channels;
	}

	@Override
	public List<Channel> listPublishChannel()
	{
		
		return null;
	}

	@Override
	public Channel loadFirstChannelByNav(int pid)
	{
		String hql="select new Channel (c.id,c.name,c.type) from Channel c where c.parent.id=? order by c.orders";
		Channel c=(Channel) this.getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).setParameter(0, pid).uniqueResult();
		return c;
	}

	@Override
	public List<Channel> ListChannelInUseByParent(Integer pid)
	{
		String hql="select c from Channel c left join fetch c.parent cp where cp.id= "+
				pid+" and cp.status=1";
		if(pid==null||pid==0) hql="select c from Channel c where c.parent is null and c.status=1";
		List<Channel> channels=this.list(hql);
		return channels;
	}

	@Override
	public void deleteChannelGroups(int channel_id)
	{	
		String hql="delete GroupChannel gc where gc.channel.id=?";
		this.getSession().createQuery(hql).setParameter(0, channel_id).executeUpdate();
	}

	@Override
	public List<Channel> listChannelByType(ChannelType ct)
	{	
		String hql="select new Channel (c.id,c.name, c.type) from Channel c where c.status=1 and c.type="+ct.ordinal();
		List<Channel> channels=this.list(hql);
		return channels;
	}

	
	 
	
	
	
	
	
	
 
}
