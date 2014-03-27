package com.lj.basic.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import junit.framework.Assert;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

import com.lj.basic.test.util.AbstractDbUnitTestCase;
import com.lj.basic.test.util.DbUtil;
import com.lj.basic.util.MyLog4jLogger;


/**
 * 此处我们使用lingling这个数据库schema来专门进行测试
 * alleni用来存储我们的正式信息。
 * lingling->在dbutil中配置的  -jdbc connection
 * alleni-> 在beans.xml中配置的 -hibernate
 * @author Administrator
 *
 */
public class AbstractDbUnitTestCase {
	public static IDatabaseConnection dbunitCon;
	
	
	/**
	 * tempFile用来存放备份的数据信息<br/>
	 * 当测试数据时，会将数据信息存放到这个文件中。<br/>
	 * 测试完成之后，再将数据导回数据库。
	 */
	private File tempFile;
	
	/**
	 * Beforeclass是在创建这个类之前所做的操作<br/>
	 * 这个方法必须为static<br/>
	 * 该方法在junit测试中会被最先调用，并且只会调用一次。
	 */
	@BeforeClass
	public static void init() throws DatabaseUnitException{
		//System.out.println("BEFOREclass init");
		MyLog4jLogger.debug("AbstractDbUnitTestCase init() BeforeClass");
		//在测试运行之前，便给dbunitCon初始化并赋值。
		dbunitCon=new DatabaseConnection(DbUtil.getCon());
	}
	
	/**
	 * 测试完成之后，此方法便会被运行，关闭dbunitCon
	 */
	@AfterClass
	public static void destory(){
		try {
			if(dbunitCon!=null)dbunitCon.close();
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		MyLog4jLogger.debug("AbstractDbUnitTestCase destory() @AfterClass finished");
	}
	
	/**
	 * 通过项目中的xml文件获取测试数据<br/>
	 * @param table_name 数据表的名称
	 * @return 返回FlatXmlDataSet类型对象，包含了xml文件中的所有内容
	 */
	protected IDataSet createDataSet(String dataSource_name) throws DataSetException{
		
		if(!dataSource_name.endsWith(".xml")){
			dataSource_name+=".xml";
		}
		InputStream is=AbstractDbUnitTestCase.class.getClassLoader().getResourceAsStream(dataSource_name);
		
		Assert.assertNotNull("dbunit的基本数据文件不存在", is);
		
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
	}
	
	/**
	 * 这个方法用于备份指定的几个表名
	 * @param tname 要备份的数据库中的表名称
	 */
	protected void backupCustomTable(String[] tname) throws DataSetException, IOException{
		QueryDataSet backup=new QueryDataSet(dbunitCon);
		
		for(String name:tname){
			//这里将表名捆绑到QueryDataSet里面，这样在后面读取数据库的时候，就会产生对应的SQL语句。
			backup.addTable(name);
		}
		
		//将IDataSet的数据信息写入tempFile中。
		//里面调用了FlatXmlDataSet的write方法。
		writeBackupFile(backup);
	}
	
	
	/**
	 * 将IDataSet的数据信息写入tempFile中。
	 * @param backupDataSet
	 */
	private void writeBackupFile(IDataSet ds) throws DataSetException, IOException{
		if(tempFile==null||!tempFile.exists()){
		tempFile = File.createTempFile("backup", ".xml");}
		//System.out.println(tempFile.exists());
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}
	
	/**
	 * 不建议在Oracle数据库中使用。因为dbunitCon.createDataSet()会获取oracle里面的各种表，其中很多jvm是没有权限修改的。
	 */
	@Deprecated
	protected void backupAllTable() throws SQLException, DataSetException, IOException{
	 	IDataSet ds=dbunitCon.createDataSet();
	 //	System.out.println(ds.getTable("t_user").getRowCount());
		
	//	QueryDataSet qds=new QueryDataSet(dbunitCon);
		 
		writeBackupFile(ds);
		
	}
	

	
	protected void backupOneTable(String tname) throws DataSetException, IOException{
		this.backupCustomTable(new String[]{tname});
	}
	
	/**
	 *  读取tempFile里的数据，从而恢复数据库原始的信息。
	 */
	protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException{
		IDataSet ds=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	}
	
	
	
	
}
