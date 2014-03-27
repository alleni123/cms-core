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
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

import com.lj.basic.test.util.AbstractDbUnitTestCase;
import com.lj.basic.test.util.DbUtil;

public class AbstractDbUnitTestCase {
	public static IDatabaseConnection dbunitCon;
	private File tempFile;
	
	@BeforeClass
	public static void init() throws DatabaseUnitException{
		dbunitCon=new DatabaseConnection(DbUtil.getCon());
	}
	
	
	@AfterClass
	public static void destory(){
		try {
			if(dbunitCon!=null)dbunitCon.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	protected IDataSet createDataSet(String tname) throws DataSetException{
		InputStream is=AbstractDbUnitTestCase.class.getClassLoader().getResourceAsStream(tname+".xml");
		
		Assert.assertNotNull("dbunit的基本数据文件不存在", is);
		
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
	}
	
	
	private void writeBackupFile(IDataSet ds) throws DataSetException, IOException{
		tempFile = File.createTempFile("backup", ".xml");
		System.out.println(tempFile.exists());
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}
	
	protected void backupAllTable() throws SQLException, DataSetException, IOException{
	 	IDataSet ds=dbunitCon.createDataSet();
	 	System.out.println(ds.getTable("t_user").getRowCount());
		
	//	QueryDataSet qds=new QueryDataSet(dbunitCon);
		 
		writeBackupFile(ds);
		
	}
	
	protected void backupCustomTable(String[] tname) throws DataSetException, IOException{
		QueryDataSet ds=new QueryDataSet(dbunitCon);
		
		for(String str:tname){
			ds.addTable(str);
		}
		writeBackupFile(ds);
	}
	
	protected void backupOneTable(String tname) throws DataSetException, IOException{
		this.backupCustomTable(new String[]{tname});
	}
	
	
	protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException{
		IDataSet ds=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	}
	
	
	
	
}
