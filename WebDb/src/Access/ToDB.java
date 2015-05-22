package Access;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;


public class ToDB {
	 private Connection conn = null; 
	 private String[][] TbStruct = new String[50][50];
	 private Statement stmt;
	
	 public void Connection(String DbName) {
	        String url = "jdbc:mysql://localhost:3306/"+DbName;
			String user = "root";
			String password = "root";
			String s = "com.mysql.jdbc.Driver";
			try {
				Class.forName(s).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    }
	 
	 public void CreateTb(String UserName,String DbName,String TableName,String[][] TableStruct,int RowNum,int ColNum)
	 {
			 String sql="create table "+TableName+"(";			
			 for(int ColIndex=0;ColIndex<ColNum;ColIndex++)
	     	 {
	     		String ColName=TableStruct[0][ColIndex];        		
	     		String ColType=TableStruct[1][ColIndex]; 
	     		sql+=ColName+" "+ColType+","; 
	     	}  
			sql=sql.substring(0,(sql.length()-1));
			sql+=")";
			System.out.println("建立数据表的SQL为："+sql);
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//插入行数据
			String Sql2="insert into "+TableName+"(";
			for(int j=0;j<ColNum;j++)
			  Sql2=Sql2+TableStruct[0][j]+",";
			Sql2=Sql2.substring(0,(Sql2.length()-1)); 
			Sql2+=") values(";	
			
			for(int i=2;i<RowNum;i++)
			{
			  String SqlInsert=Sql2;				  
			  for(int j=0;j<ColNum;j++)
				  SqlInsert=SqlInsert+"'"+TableStruct[i][j]+"',";
			  SqlInsert=SqlInsert.substring(0,(SqlInsert.length()-1)); 
			  SqlInsert+=")";
			  System.out.println("插入记录的SQL为："+SqlInsert);
			  try {
				stmt.executeUpdate(SqlInsert);
			  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
			} 
			
			String sql3="insert into B8.user_db_tb values('"+UserName+"','"+DbName+"','"+TableName+"')";
			try {
				stmt.executeUpdate(sql3);				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   	   
	 }
	 
	 public void ToDB(String UserStr) throws IOException, SQLException
	 {				 
	       int location=UserStr.lastIndexOf("$");	 
	       String AccessAddr=UserStr.substring(location+1,UserStr.length());
	       
	       String User_DbName=UserStr.substring(0,location);	       
	       int loc=User_DbName.lastIndexOf("$");
	       String UserName=User_DbName.substring(0,loc);
	       String DbName=User_DbName.substring(loc+1,User_DbName.length()); 
	       
	       System.out.println("用户名为："+UserName);
	       System.out.println("Access地址为："+AccessAddr);
	       System.out.println("数据库名为："+DbName);
		 
	    	int TableNum=0;
	    	String[] Array =new String[100];//用于存储全部表名    	
	        Database db=Database.open(new File(AccessAddr));       
	        //读取Access库中的全部表名
	        Set<String> TableSet = db.getTableNames();               
	        Iterator iterator = TableSet.iterator();       
	        while (iterator.hasNext()){
	        	Array[TableNum++]=iterator.next().toString();        	
	        }
	        
	        //建立Mysql的数据库	       
			Connection("B8"); 	
		    stmt = conn.createStatement();
			stmt.executeUpdate("create database "+User_DbName);
			String s="insert into B8.user_db_tb(UserName,DbName) values('"+UserName+"','"+DbName+"')";
			stmt.executeUpdate(s); 
			stmt.executeUpdate("use "+User_DbName);		
			   
	        //转换成Mysql的数据表
	        for(int TBIndex=0;TBIndex<TableNum;TBIndex++)
	        {
	        	String TableName=Array[TBIndex];
	        	Table TB=db.getTable(TableName);
	        	List<Column> cols = TB.getColumns();
	        	int TBColNum=TB.getColumnCount();
	        	int TBRowNum=TB.getRowCount();        	
	        	for (int ColIndex=0;ColIndex<TBColNum;ColIndex++)
	        	{
	        		Column c = (Column)cols.get(ColIndex);        		
	        		String type=c.getType().toString();
					//System.out.println("列类型为："+type+"  "+"列名为："+c.getName());
					TbStruct[0][ColIndex]=c.getName(); //TbStruct第一行保存列名
					TbStruct[1][ColIndex]=type;		   //TbStruct第二行保存列类型
	        	}            	
	        	
	        	//TbStruct从第三行开始保存Access的表的行的数据
	        	int DataRowIndex=2;
	        	Map row;        	
	        	while((row =TB.getNextRow())!= null) 
	        	{
	        	   int ColIndex=0;
	        	   Iterator iter= row.values().iterator();
	        	   while (iter.hasNext()) {
	        		  Object obj = iter.next();
	        		  TbStruct[DataRowIndex][ColIndex++]=String.valueOf(obj);        		        		 
	        	   }
	        	   DataRowIndex++;        	 
	        	} 
	        	
	        	//打印TbStruct从第三行开始保存Access的表的行的数据
	        	for(int num=0;num<DataRowIndex;num++)
	        	{
	        		for(int colnum=0;colnum<TBColNum;colnum++)
	        			 System.out.print(TbStruct[num][colnum]+" "); 
	        		System.out.println();
	        	}
	        	
	        	TBRowNum=TBRowNum+2;
	        	CreateTb(UserName,DbName,TableName,TbStruct,TBRowNum,TBColNum);        	
	        }        
	        conn.close();       	
	    }
	 

}
