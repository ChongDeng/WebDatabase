package sql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.healthmarketscience.jackcess.Database;
import com.mysql.jdbc.ResultSetMetaData;

public class Export {
	   
	   //DbStore用来存储最后一次连接的数据库名
	   String DbStore=null;
	   private Connection conn = null;
	   
	   public void Connection(String DbName) {
		        DbStore=DbName;
		        String url = "jdbc:mysql://localhost:3306/"+DbStore;
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
	  
	  
   public String creatTempSql(String UserName,String DbName,String TbName) throws SQLException, IOException
   {	         
	     String RealDbName=UserName+"$"+DbName;
	     String result = null;   
	     ArrayList List = new ArrayList();  
	     String StrOutput="";
	    
	     if(TbName==null)  //用户选择了导出数据库
	     {   	        	 
	    	 String filePath="F:\\Flex_File\\Tomcat\\webapps\\WebDb\\space\\"+RealDbName+".txt"; //将代表数据库SQL语句的StrOutput写到服务器目录		
			 File file = new File(filePath);			
			 if (file.exists()){					
			   file.delete();
			 }				
			 file.createNewFile();
			 BufferedWriter output = new BufferedWriter(new FileWriter(file));
			 
	    	 Connection(RealDbName);	   		   
		     Statement stmt = conn.createStatement();	
		        	  
		     //获得该数据库下的数据表名
		     stmt.executeQuery("use "+RealDbName);
		     ResultSet rs=stmt.executeQuery("show tables");
		   	 int j=1;
		   	 List.clear();
		   	 while(rs.next())
		   	 {
		   	      System.out.println(rs.getString(j));
		   	      List.add(rs.getString(j));			         
	         }
		      	      
		     //获得每个表的结构的SQL语句
			 for(int i=0;i<List.size();++i)
			 {
					 StrOutput="-- ----------------------------";
					 output.write(StrOutput);
					 output.write("\r\n");
					 StrOutput="-- Table structure for "+List.get(i);
					 output.write(StrOutput);
					 output.write("\r\n");
			         StrOutput="-- ----------------------------";	
			         output.write(StrOutput);
					 output.write("\r\n");
			         
					 ResultSet rs2=stmt.executeQuery("select * from "+List.get(i));
					 ResultSetMetaData rsmd = (ResultSetMetaData) rs2.getMetaData();
				     int colNum=rsmd.getColumnCount();					     
							     
					 String[] ColName =new String[100];
					 String[] ColType =new String[100];
					 String[] IsNull =new String[100];
							    
					 //获得当前表的列名字、列类型以及是否为非空
					 for(int n=0;n<colNum;n++)
					 {
					  	ColName[n]=rsmd.getColumnName(n+1);
					   	ColType[n]=rsmd.getColumnTypeName(n+1); 
					  	if(rsmd.isNullable(n+1)==0)
					  	  IsNull[n]="NOT NULL";						    
				     }					     
							    
				     //获得创建表的SQL语句
					 StrOutput="DROP TABLE IF EXISTS "+List.get(i)+";";
					 output.write(StrOutput);
					 output.write("\r\n");
					 StrOutput="CREATE TABLE "+List.get(i)+" (";
					 for(int n=0;n<colNum;n++)
					 {
						 System.out.println("列类型为："+ColName[n]);
						 if(ColType[n].equals("VARCHAR"))
							 ColType[n]+="(20)";
						 if(IsNull[n]=="NOT NULL")
						    StrOutput+=ColName[n]+" "+ColType[n]+" NOT NULL,";
				    	 else
							StrOutput+=ColName[n]+" "+ColType[n]+","; 
					 }
					
					 StrOutput=StrOutput.substring(0,(StrOutput.length()-1));
					 StrOutput+=");";				
					 output.write(StrOutput);
					 output.write("\r\n");
					 output.write("\r\n");
		     } 
		   	
			 StrOutput="-- ----------------------------";
			 output.write(StrOutput);
			 output.write("\r\n");
		     StrOutput="-- Records";
		     output.write(StrOutput);
			 output.write("\r\n");
		     StrOutput="-- ----------------------------";	
		     output.write(StrOutput);
			 output.write("\r\n");
			 
		     //获得每个表的插入记录的SQL语句
		    for(int i=0;i<List.size();i++)
		    {
			     ResultSet rs2=stmt.executeQuery("select * from "+List.get(i));	
			     ResultSetMetaData rsmd = (ResultSetMetaData) rs2.getMetaData();
			     int colNum=rsmd.getColumnCount();					     
						    			     
	       	     //获得插入记录的SQL语句
				 while(rs2.next())
				  {
				 	  StrOutput="INSERT INTO "+List.get(i)+" VALUES ("; 
				 	  StrOutput+=rs2.getString(1)+","; 
				 	  for(int c=2;c<=colNum;++c)
				   		  StrOutput+="'"+rs2.getString(c)+"',"; 
				   	  StrOutput=StrOutput.substring(0,(StrOutput.length()-1));
				   	  StrOutput+=");";
				      
				   	  output.write(StrOutput);
					  output.write("\r\n");
				  }					     
			 }
		    
		   conn.close();		  
		   output.close();		
	  }
	    
	  else     //用户选择了导出数据表
      {
		     String filePath="F:\\Flex_File\\Tomcat\\webapps\\WebDb\\space\\"+RealDbName+"$"+TbName+".txt"; //将代表数据库SQL语句的StrOutput写到服务器目录		
			 File file = new File(filePath);			
			 if (file.exists()){					
			   file.delete();
			 }				
			 file.createNewFile();
			 BufferedWriter output = new BufferedWriter(new FileWriter(file));	 
			 
			 StrOutput="-- ----------------------------";
			 output.write(StrOutput);
			 output.write("\r\n");
			 StrOutput="-- Table structure for "+TbName;
			 output.write(StrOutput);
			 output.write("\r\n");
	         StrOutput="-- ----------------------------";	
	         output.write(StrOutput);
			 output.write("\r\n");			 
			 
			 Connection(RealDbName);	   		   
		     Statement stmt = conn.createStatement();
		     ResultSet rs=stmt.executeQuery("select * from "+TbName);	
		     ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		     int colNum=rsmd.getColumnCount();					     
					     
			 String[] ColName =new String[100];
			 String[] ColType =new String[100];
			 String[] IsNull =new String[100];
					    
			 //获得当前表的列名字、列类型以及是否为非空
			 for(int n=0;n<colNum;n++)
			 {
			  	ColName[n]=rsmd.getColumnName(n+1);
			   	ColType[n]=rsmd.getColumnTypeName(n+1); 
			  	if(rsmd.isNullable(n+1)==0)
			  	  IsNull[n]="NOT NULL";						    
		     }					     
					    
		     //获得创建表的SQL语句
			 StrOutput="DROP TABLE IF EXISTS "+TbName+";";
			 output.write(StrOutput);
			 output.write("\r\n");
			 StrOutput="CREATE TABLE "+TbName+" (";
			 for(int n=0;n<colNum;n++)
			 {
				 System.out.println("列类型为："+ColName[n]);
				 if(ColType[n].equals("VARCHAR"))
					 ColType[n]+="(20)";				 
				 if(IsNull[n]=="NOT NULL")
				    StrOutput+=ColName[n]+" "+ColType[n]+" NOT NULL,";
		    	 else
					StrOutput+=ColName[n]+" "+ColType[n]+","; 
			 }
			
			 StrOutput=StrOutput.substring(0,(StrOutput.length()-1));
			 StrOutput+=");";				
			 output.write(StrOutput);
			 output.write("\r\n");
			 output.write("\r\n");
			 
			//获得插入记录的SQL语句
			 while(rs.next())
			  {
			 	  StrOutput="INSERT INTO "+TbName+" VALUES ("; 
			 	  StrOutput+=rs.getString(1)+","; 
			 	  for(int c=2;c<=colNum;++c)
			   		  StrOutput+="'"+rs.getString(c)+"',"; 
			   	  StrOutput=StrOutput.substring(0,(StrOutput.length()-1));
			   	  StrOutput+=");";
			      
			   	  output.write(StrOutput);
				  output.write("\r\n");
			  }				
			 
			 conn.close();		  
			 output.close();	
		     
	  }
	          
    return result;
	
   }
}
