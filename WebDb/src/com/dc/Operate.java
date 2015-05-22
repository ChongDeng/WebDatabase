package com.dc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.rjb.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

 
import java.util.List; 



public class Operate
{     
	   private String[ ] PriArray= new String[20];
	   private int PriNumber=0;
	   private int ColNumber;
	   private int JsonSize;
	   private JSONArray JsonStore;
	   private Connection conn = null;
	   
	   //DbStore用来存储最后一次连接的数据库名
	   String DbStore=null;
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
	   
   
	   //建立数据表  1016版本
	   public void CreateTb(String jsonData) throws SQLException
	   {
		   JSONArray jsonArray = JSONArray.fromObject(jsonData);
	       int ColNum=jsonArray.size();
	       
	       //获得表名
	       JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(ColNum-1));
	       String TableName=jsonObject.getString("label");
	       
	       //获得用户名和库名
	       JSONObject jsonObject2 = JSONObject.fromObject(jsonArray.getString(ColNum-2));
	       String UserName=jsonObject2.getString("label");
	       String DbName=jsonObject2.getString("type");
	       
	    
	       //真实的数据库名
	       String RealDbName=UserName+"$"+DbName;
	       
	       Connection(RealDbName);
		   Statement stmt = conn.createStatement();
	       
		   String sql2="create table "+TableName+"(";
		   sql2+="ID INTEGER(11),";
		   
		   ColNum=ColNum-3;
		   for(int i=0;i<=ColNum;i++)
	       {
		           JSONObject jsonObject3 = JSONObject.fromObject(jsonArray.getString(i));
		           String colname = jsonObject3.getString("label");
		           String coltype = jsonObject3.getString("type");
		           String NNull=jsonObject3.getString("NNull");
		           String Pri=jsonObject3.getString("Pri");
		           
		           System.out.println("主键："+Pri);
		           System.out.println("非空："+NNull);
		           	           
		           if(Pri.equals("true"))
		           {
		        	   sql2=sql2+colname+" "+coltype+" NOT NULL"+",";
		        	   PriArray[PriNumber++]=colname;
		        	   continue;
		           }
		           else
		           {
		        	   if(NNull.equals("true"))
		        		   {
		        		     sql2=sql2+colname+" "+coltype+" NOT NULL"+",";
		        		     continue;
		        		   }
		        	   else
		        		   sql2=sql2+colname+" "+coltype+","; 
		           }
		           
		           
	        } 
			   
		   if(PriNumber>0)
		   {
			  String SqlPri="PRIMARY KEY (";
			  for(int i=0;i<PriNumber;i++)
				 {
				   SqlPri+=PriArray[i]+",";
				 }
			  
			  SqlPri=SqlPri.substring(0,(SqlPri.length()-1));
			  SqlPri+="))"; 
			  sql2+=SqlPri; 
		   }
		   else
		   {
			   sql2=sql2.substring(0,(sql2.length()-1));
			   sql2+=")";  
		   }
		  
		   //写这一步的原因是因为创建了一个表的主键后，再创建另一个表的时候，这个主键会自动插在里一个表中，很诡异。
		   //所以，通过PriNumber置0来阻止添加	 
		   PriNumber=0;
		   
	       System.out.println("建立数据表的SQL为："+sql2);
	       stmt.executeUpdate(sql2);
	       
	       //System.out.println("S额外增加行的SQL为："+Sql_Insert);
	       //stmt.executeUpdate(Sql_Insert);
	      
	      //创建用户—库-表的纪录应该放在创建表的后面，以防止现表没有建成功，然而继续添加了这条记录
	       String sql="insert into B8.user_db_tb values('"+UserName+"','"+DbName+"','"+TableName+"')";
		   stmt.executeUpdate(sql);
		   conn.close();	   
		
		 } 
	 

	   
	   //重命名数据表REN_TB
	   public void REN_TB(String Uname,String DbN,String OldName,String NewName) throws Exception 
	   {
           String RealDbName=Uname+"$"+DbN;
	       Connection(RealDbName);
		   Statement stmt = conn.createStatement();
		   
	       //String OldTableName=Uname+"$"+DbN+"$"+OldName;
	       //String NewTableName=Uname+"$"+DbN+"$"+NewName;
	     
		   String sql="update B8.user_db_tb set TableName='"+NewName+"'where UserName ='"+Uname+"'and DbName='"+DbN+"' and TableName='"+OldName+"'";
		   stmt.executeUpdate(sql);	
		   //方法一
		   //String sql3="ALTER TABLE "+OldName+" RENAME "+NewName; 
		   
		 //方法二
		   String sql2="rename table "+OldName+" to "+NewName;
		   stmt.executeUpdate(sql2);
		   conn.close();
	   }
	   
	   //删除数据表
	   public void DeleteTb(String Uname,String DbN,String Tname) throws Exception 
	   {
		   String RealDbName=Uname+"$"+DbN;
	       Connection(RealDbName);
		   Statement stmt = conn.createStatement();
		   
		   //数据库中的真实的表名
	       //String RealTbName=Uname+"$"+DbN+"$"+Tname;
	      
		   stmt.executeUpdate("drop table "+Tname);
		   stmt.executeUpdate("delete from B8.user_db_tb where UserName ='"+Uname+"'and DbName='"+DbN+"' and TableName='"+Tname+"'");
	       conn.close();
	   }
	   
	   
	   
	   //建立数据库
	   public void CreateDb(String Uname,String DbN) throws Exception 
	   {   
		   String Db="B8";
		   Connection(Db);
		   
		   String UserAndDB=Uname+"$"+DbN;
		   Statement stmt = conn.createStatement();
		   stmt.executeUpdate("create database "+UserAndDB); 
		   String s="insert into user_db_tb(UserName,DbName) values('"+Uname+"','"+DbN+"')";
		   stmt.executeUpdate(s); 
		   conn.close();
	   }
	   
	   //删除数据库
	  public boolean DropDb(String Uname,String DbN) throws Exception 
	   {
		   String Db="B8";
		   Connection(Db);
		   //数据库中的真实的表名
	       String RealDbName=Uname+"$"+DbN;
	       Statement stmt = conn.createStatement();
		   stmt.executeUpdate("delete from User_Db_Tb where UserName ='"+Uname+"'and DbName='"+DbN+"'");
		   stmt.executeUpdate("drop database "+RealDbName); 
		   
		   conn.close();
		   //不用删除数据库上的表  删除库就删除了里面的表吧
		   return true;
	   }
	  
	  //用来更新数据表的行和列的值
	  public void sendJsonArray(String jsonData) throws SQLException
	  {    
		 	  
		   JSONArray jsonArray = JSONArray.fromObject(jsonData);
	       JsonSize=jsonArray.size();
	       JsonStore=jsonArray;
	       
	       JSONObject TableNameObject = JSONObject.fromObject(jsonArray.getString(JsonSize-1));
	       String UserDBTb=TableNameObject.getString("key1");
	       int location=UserDBTb.lastIndexOf("$");
	       String DbName=UserDBTb.substring(0,location);
		   String TbName=UserDBTb.substring(location+1,UserDBTb.length());
		   Connection(DbName);   
	       Statement stmt=conn.createStatement();  
	       
	       System.out.println("华南理工库名为："+DbName);
	       System.out.println("华南理工表名为："+TbName);
	       
	       JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(JsonSize-2));
	       ColNumber=Integer.parseInt(jsonObject.getString("key1"));
	       System.out.println("数据表的列数为："+ColNumber);
	       
	       int ModifyRow=JsonSize-ColNumber-1;
	       System.out.println("修改的行数为："+ModifyRow);
	       for(int i=0;i<ModifyRow;i++)
	       {
	    	   switch(ColNumber)
		     	{
				          case 1: 
				        	       { 
				        	    	    
				        	       }
					              
					               break;
				    	        
				    	   case 2: {
				    		        JSONObject jsonObject1 = JSONObject.fromObject(JsonStore.getString(i));
				    		        int MyId= Integer.parseInt(jsonObject1.getString("key1"));
				    		        String ColVal2 = jsonObject1.getString("key2");
				    		        
				                    JSONObject jsonObject2 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber)));
				                    String ColName2 = jsonObject2.getString("key1");
				                    
				                    String sql="update MyTest set ";
				                    sql=sql+ColName2+"="+ColVal2+" where ID="+MyId;
				                    }
				    	            break;
				    	            
				    	   case 3: 
				    	            {
				    	            	//获得识别键
				    	            	JSONObject jsonObject1 = JSONObject.fromObject(JsonStore.getString(i));
					    		        int MyId= Integer.parseInt(jsonObject1.getString("key1"));
					    		        
					    		        //取列值
					    		        JSONObject ColValObject = JSONObject.fromObject(JsonStore.getString(i));
					    		        String ColVal2 = ColValObject.getString("key2");
					                    String ColVal3 = ColValObject.getString("key3");
					                    
					                    //取列二的列名
					                    JSONObject jsonObject2 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber-1)));
					                    String ColName2 = jsonObject2.getString("key1");
					                    //取列三的列名
					                    JSONObject jsonObject3 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber)));
					                    String ColName3 = jsonObject3.getString("key1");
					                    
					                    //判断操作码
					                    
					                    JSONObject OperateObj = JSONObject.fromObject(JsonStore.getString(i));
					    		        String operation = OperateObj.getString("OperateKey");
					                    
					    		        String sql=null;;
					    		        if(operation.equals("update"))
					    		        {
					    		        	sql="update "+TbName+" set ";
					    		        	sql=sql+ColName2+"='"+ColVal2+"',"+ColName3+"='"+ColVal3+"' where ID="+MyId;
					    		        }
				    	                else if(operation.equals("delete"))
				    	                {
				    	                	sql="delete from "+TbName+" where ID="+MyId;
				    	                	
				    	                }
				    	                else
				    	                {
				    	                	
				    	                	sql="insert into "+TbName+" values";
				    	                	sql=sql+"('"+MyId+"','"+ColVal2+"','"+ColVal3+"')";
				    	                	
				    	                }
					                    System.out.println("SQL语句的内容为："+sql);
					             	    stmt.executeUpdate(sql);  
						            }
				    	            break;
				    	   case 4: 
		    	            {
		    	            	//获得识别键
		    	            	JSONObject jsonObject1 = JSONObject.fromObject(JsonStore.getString(i));
			    		        int MyId= Integer.parseInt(jsonObject1.getString("key1"));
			    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(JsonStore.getString(i));
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    
			                    if(ColVal2.equals(" "))
			                    	ColVal2=null;
			                    
			                    if(ColVal3.equals(" "))
			                    	ColVal3=null;
			                    
			                    if(ColVal4.equals(" "))
			                    	ColVal4=null;
			                   
			                    //取列二的列名
			                    JSONObject jsonObject2 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber-1)));
			                    String ColName2 = jsonObject2.getString("key1");
			                    //取列三的列名
			                    JSONObject jsonObject3 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber)));
			                    String ColName3 = jsonObject3.getString("key1");
			                    //取列四的列名
			                    JSONObject jsonObject4 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber+1)));
			                    String ColName4 = jsonObject4.getString("key1");
			                    
			                    //判断操作码
			                    
			                    JSONObject OperateObj = JSONObject.fromObject(JsonStore.getString(i));
			    		        String operation = OperateObj.getString("OperateKey");
			                    
			    		        String sql=null;;
			    		        if(operation.equals("update"))
			    		        {
			    		        	sql="update "+TbName+" set ";
			    		        	sql=sql+ColName2+"='"+ColVal2+"',"+ColName3+"='"+ColVal3+"',"+ColName4+"='"+ColVal4+"' where ID="+MyId;
			    		        }
		    	                else if(operation.equals("delete"))
		    	                {
		    	                	sql="delete from "+TbName+" where ID="+MyId;
		    	                	
		    	                }
		    	                else
		    	                {
		    	                	
		    	                	sql="insert into "+TbName+" values";
		    	                	sql=sql+"('"+MyId+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"')";
		    	                	
		    	                }
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break;
		       } 
			   
	       } 
	      conn.close();
	  }
	  
	  //查询分析器中的数据库的源
	  public List SQLDbData(String Uname) throws SQLException
	  {    
		   User u; 
		   ArrayList<User> userList=new ArrayList<User>(); 
		   String Db="B8";
		   Connection(Db);
		   PreparedStatement stmt = conn.prepareStatement("select distinct DbName from User_Db_Tb where UserName='"+Uname+"'");
	       ResultSet rs = stmt.executeQuery();
		
	       while(rs.next())
	       {
	    	   u=new User(); 
	    	   u.setLabel(rs.getString("DbName"));
		       userList.add(u);   
	       }	
	       conn.close();
		  return userList;
	 }
	  
	  //SQL查询
	  public String SqlOperation(String SQLStr,String Uname) throws Exception 
	   {
			  String RealDName=null; int index=0;
			  String VirDName=null;
			  String Result="操作失败";
			  
			  System.out.println("您输入的SQL语句为："+SQLStr); 
					  
			  StringTokenizer st = new StringTokenizer(SQLStr,";");
			  String[] Store =new String[100];
			  int i=0;
			  while (st.hasMoreTokens()) 
			  {
				 Store[i++]=st.nextToken();
			  }
			  System.out.println("有几条语句："+i); 
			 
			  if(i==1){
				  Result=DeepSqlOperation(SQLStr,Uname);
			  }
			  else
				  for(int num=0;num<i;num++)
					  {
					     Result=DeepSqlOperation(Store[num],Uname);
					     if(Result.equals("操作失败")||Result.equals("请先连接数据库"))
					    	 return Result;
					  }			  
			  return Result;			 
	  }
	  
	  
	  //SQL查询
	  public String DeepSqlOperation(String SQLStr,String Uname) throws Exception 
	   {
		     
		      String RealDName=null; int index=0;
			  String VirDName=null;
			  String Result="操作失败";
			  
			  System.out.println("您输入的SQL语句为："+SQLStr); 
			  			  
			  StringTokenizer st = new StringTokenizer(SQLStr);
			  String[] Store =new String[100];
			  int i=0;
			  while (st.hasMoreTokens()) 
			  {
				 Store[i++]=st.nextToken();
			  }
			  
			  String First_Two=Store[0]+" "+Store[1];
			  String FirstLow=First_Two.toLowerCase();
			  System.out.println("前两个字母："+FirstLow);
			  
			  //创建数据库
			  if(FirstLow.equals("create database")) 
			  {
				  VirDName=Store[2];
				  RealDName=Uname+"$"+Store[2];
				  Store[2]=RealDName;
				  
				  String ExecStr="";
				  for(int k=0;Store[k]!=null;k++) 
					  ExecStr+=Store[k]+" ";
				  ExecStr=ExecStr.substring(0,(ExecStr.length()-1)); 
	              System.out.println("修改后的SQL语句为："+ExecStr);
				 
	              String Db="B8";
	   		      Connection(Db);
				  Statement stmt = conn.createStatement();
				  stmt.executeUpdate(ExecStr);
				  
				  String s="insert into B8.user_db_tb(UserName,DbName) values('"+Uname+"','"+VirDName+"')";
				  stmt.executeUpdate(s); 				  
				  conn.close();				  
				  Result="操作成功";
			  }
			 
			//删除数据库
			  else if(FirstLow.equals("drop database")) 
			  {
				  VirDName=Store[2];
				  RealDName=Uname+"$"+Store[2];
				  Store[2]=RealDName;
				  
				  String ExecStr="";
				  for(int k=0;Store[k]!=null;k++) 
					  ExecStr+=Store[k]+" ";
				  ExecStr=ExecStr.substring(0,(ExecStr.length()-1)); 
	              System.out.println("修改后的SQL语句为："+ExecStr);
				 
	              String Db="B8";
	   		      Connection(Db);
				  Statement stmt = conn.createStatement();
				  stmt.executeUpdate(ExecStr);
				  
				  stmt.executeUpdate("delete from B8.User_Db_Tb where UserName ='"+Uname+"'and DbName='"+VirDName+"'");
				  conn.close();				  
				  Result="操作成功";
			  }
			  
			//新建数据表
			  else if(FirstLow.equals("create table")) 
			  {
				  if(DbStore.equals("B8"))
				  {
					  Result="请先连接数据库";
					  return Result;
				  }
				  else
				  {
					  System.out.println("此时数据库为："+DbStore);
					  Connection(DbStore);
					  Statement stmt = conn.createStatement();					  
					/*  
					  StringTokenizer FenGe = new StringTokenizer(SQLStr,",");
					  String[] SqlStore =new String[50];
					  int num=0;
					  while (FenGe.hasMoreTokens()) 
			  		   {
							SqlStore[num++]=FenGe.nextToken();							
					   }
						  
					  for(int n=0;SqlStore[n]!=null;n++) 
						 System.out.println("分割后的一条语句为："+SqlStore[n]);						  
						 
					  StringBuffer First=new StringBuffer(SqlStore[0]);
					  StringBuffer ModifyFirst=new StringBuffer( );
						  
					  int place=SqlStore[0].indexOf("(");
					  place++;
					  System.out.println("定位为："+place);
					  String NeededCol="ID int(11) default NULL, ";
					  ModifyFirst=First.insert(place,NeededCol);
						  
					  SqlStore[0]=ModifyFirst.toString();
					  System.out.println("修改后的第一条语句为："+ SqlStore[0]);
						  
					  String ExecStr="";
				      for(int k=0;SqlStore[k]!=null;k++) 
						ExecStr=ExecStr+SqlStore[k]+",";
					  ExecStr=ExecStr.substring(0,(ExecStr.length()-1)); 
			              
					  System.out.println("终结版语句为："+ExecStr);					  
					  stmt.executeUpdate(ExecStr);				  
					 */
					  System.out.println("修改0：");
					  stmt.executeUpdate(SQLStr);	 
					  int location=DbStore.lastIndexOf("$");
					  String VirDBase=DbStore.substring(location+1,DbStore.length());					  
					  System.out.println("修改1：");
					  String sql="insert into B8.user_db_tb values('"+Uname+"','"+VirDBase+"','"+Store[2]+"')";
					  stmt.executeUpdate(sql);
					  conn.close();
				  }
		     	  Result="操作成功";
			  }
			  
			//删除数据表
			  else if(FirstLow.equals("drop table")) 
			  {				  
					  if(DbStore.equals("B8"))
					  {
						     Result="请先连接数据库";
						     return Result;
					  }
					  else
					  {	
							  Connection(DbStore);
							  Statement stmt = conn.createStatement();
							  int location=DbStore.lastIndexOf("$");
							  String VirDBase=DbStore.substring(location+1,DbStore.length());
							  
							  if(i==3)  //以"drop table fqyang"的形式删除
							  { 
								  //这一步是为了去掉 'fqyang'的引号
								  if(Store[2].substring(0,1).equals("'"))
								      Store[2]=Store[2].substring(1,Store[2].length()-1);
								  System.out.println("表名为："+Store[2]);		
								  stmt.executeUpdate("delete from B8.user_db_tb where UserName ='"+Uname+"'and DbName='"+VirDBase+"' and TableName='"+Store[2]+"'");						  
								  stmt.executeUpdate(SQLStr);
								  
							  }
							  else  //以"drop table if  exists fqyang"的形式删除
							  {
								   System.out.println("这个时候的表名为："+Store[4]);
								   
								   //查询数据表是否在数据库中
								   ResultSet Res=stmt.executeQuery("show tables");					 
								   while(Res.next())
								   {
								     if(Store[4]==Res.getString(1))
										{
								    	   stmt.executeUpdate("delete from B8.user_db_tb where UserName ='"+Uname+"'and DbName='"+VirDBase+"' and TableName='"+Store[4]+"'");  
								    	   break;		         
										}
								   }
								   stmt.executeUpdate(SQLStr);						  
							  }					  
						  conn.close();
					  }
				  Result="操作成功";
			  }
			 
			//重命名数据表，或者，对表的列操作   SQL语句是以ALTER TABLE开头的
			  else if(FirstLow.equals("alter table")) 
			  {
				  int num = 0;
				  if(DbStore.equals("B8"))
				  {
					  Result="请先连接数据库";
					  return Result;
				  }
				  else
				  {
					  Connection(DbStore);
					  Statement stmt = conn.createStatement();
					  stmt.executeUpdate(SQLStr);
					  
						  //重命名数据表的SQL
						  if(Store[3].toLowerCase().equals("rename"))
						  {
								  int location=DbStore.lastIndexOf("$");
								  String VirDBase=DbStore.substring(location+1,DbStore.length());
								  
								  //这一步是为了去掉 'fqyang'的引号
								  if(Store[2].substring(0,1).equals("'"))
								      Store[2]=Store[2].substring(1,Store[2].length()-1);
								  System.out.println("表名为："+Store[2]);		
								  
								  for(int n=0;Store[n]!=null;n++)
									  num++;
								  String NewTbName=Store[--num];
								  System.out.print("SQL中的新表名为："+NewTbName);
								  
								  String sql="update B8.user_db_tb set TableName='"+NewTbName+"'where UserName ='"+Uname+"'and DbName='"+VirDBase+"' and TableName='"+Store[2]+"'";
								  stmt.executeUpdate(sql);
								  conn.close();
								  Result="操作成功";
						  }
						  //表的列操作  
						  else
						  {
							  Result="操作成功";
						  }
				   }
	         }
			
			 //插入、修改、删除记录
			  else if(Store[0].toLowerCase().equals("insert")||Store[0].toLowerCase().equals("delete")||Store[0].toLowerCase().equals("update"))
			  {
				  if(DbStore.equals("B8"))
				  {
					  Result="请先连接数据库";
					  return Result;
				  }
				  else
				  { 
    		    	  Connection(DbStore);
					  Statement stmt = conn.createStatement();
					  stmt.executeUpdate(SQLStr);
					  Result="操作成功";
				  }  
			   }
			  
			//重命名数据表 以rename table开头
			  else if(FirstLow.equals("rename table")) 
			  {
					  int num = 0;
					  if(DbStore.equals("B8"))
					  {
						  Result="请先连接数据库";
						  return Result;
					  }
					  else
					  {
						  Connection(DbStore);
						  Statement stmt = conn.createStatement();
						  stmt.executeUpdate(SQLStr);
						  
						  int location=DbStore.lastIndexOf("$");
						  String VirDBase=DbStore.substring(location+1,DbStore.length());
						  
						//这一步是为了去掉 'fqyang'的引号
						  if(Store[2].substring(0,1).equals("'"))
						      Store[2]=Store[2].substring(1,Store[2].length()-1);
						  System.out.println("表名为："+Store[2]);		
						  
						  for(int n=0;Store[n]!=null;n++)
							  num++;
						  String NewTbName=Store[--num];
						  System.out.print("SQL中的新表名为："+NewTbName);
						  
						  String sql="update B8.user_db_tb set TableName='"+NewTbName+"'where UserName ='"+Uname+"'and DbName='"+VirDBase+"' and TableName='"+Store[2]+"'";
						  stmt.executeUpdate(sql);	
						  conn.close();
					  }
					  Result="操作成功";
			  }
			  
			//查询数据表 以Select开头
			  else if(Store[0].toLowerCase().equals("select")) 
			  {
				      if(DbStore.equals("B8"))
					  {
						  Result="请先连接数据库";
						  return Result;
					  }
					  else
					  {
						  Result="Select";
						  return Result;
					  }
			  }			
			//连接数据库，以use开头
			  else if(Store[0].toLowerCase().equals("use"))
			  {    		       	
					  String Db=Uname+"$"+Store[1];
					  Connection(Db);
					  conn.close();
					  Result=Store[0]+Store[1];
			   }
			  else{
					  Connection(DbStore);
					  Statement stmt = conn.createStatement();
					  stmt.executeUpdate(SQLStr);
					  Result="操作成功";
			  }
			  return Result;
	  }
	  
}