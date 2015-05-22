package com.dc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





public class OperateCopy
{     
	   private String[ ] PriArray= new String[20];
	   private int PriNumber=0;
	   private int ColNumber;
	   private int JsonSize;
	   private JSONArray JsonStore;
	   private Connection conn = null;
	  
	   public OperateCopy() {
				String url = "jdbc:mysql://localhost:3306/B8";
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
	   public void AddIt() throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   stmt.executeUpdate("insert into userinfo values('deng','ch')");
	   }
	   
	   public void ModifyIt() throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   stmt.executeUpdate("update userinfo set name='fqyang'where pwd ='vostro0821'");
	   }
	   public void DeleteIt() throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   stmt.executeUpdate("delete from userinfo where name ='qq'");
	   }
	   
   
	   //在数据库上建立数据表  1016版本
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
	      
	       Statement stmt = conn.createStatement();
		//   String sql="insert into user_db_tb values('"+UserName+"','"+DbName+"','"+TableName+"')";
		 //  stmt.executeUpdate(sql);
		   
		   //插入一行，以便于后来调出到前台时，可以编辑
		   //String Sql_Insert="insert into "+TableName+"(1";
		   //String Sql_Insert="insert into "+TableName+" (aaadkdc) values (1)";	   
		   
		   //sql2+="aaadkdc INTEGER(11) NOT NULL AUTO_INCREMENT,PRIMARY KEY (aaadkdc),";
		   String sql2="create table "+TableName+"(";
		   sql2+="aaadkdc INTEGER(11),";
		   ColNum=ColNum-3;
		   for(int i=0;i<=ColNum;i++)
	       {
	           JSONObject jsonObject3 = JSONObject.fromObject(jsonArray.getString(i));
	           String colname = jsonObject3.getString("label");
	           String coltype = jsonObject3.getString("type");
	           String NNull=jsonObject3.getString("NNull");
	           String Pri=jsonObject3.getString("Pri");
	           if(Pri=="true")
	           {
	        	   sql2=sql2+colname+" "+coltype+" NOT NULL"+",";
	        	   PriArray[PriNumber++]=Pri;
	        	   continue;
	           }
	           else
	           {
	        	   if(NNull=="true")
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
				  SqlPri+="'"+PriArray[i]+"',";
			  
			  SqlPri=SqlPri.substring(0,(SqlPri.length()-1));
			  SqlPri+="))"; 
			  sql2+=SqlPri; 
		   }
		   else
		   {
			   sql2=sql2.substring(0,(sql2.length()-1));
			   sql2+=")";  
		   }
				 		   
	       System.out.println("建立数据表的SQL为："+sql2);
	       stmt.executeUpdate(sql2);
	       
	       //System.out.println("S额外增加行的SQL为："+Sql_Insert);
	       //stmt.executeUpdate(Sql_Insert);
	      
	      //创建用户—库-表的纪录应该放在创建表的后面，以防止现表没有建成功，反而继续添加了这条记录
	       String sql="insert into user_db_tb values('"+UserName+"','"+DbName+"','"+TableName+"')";
		   stmt.executeUpdate(sql);
			   
		
		 } 
	 //在数据表上建立数据表
	   public void TB_CREAT_TB(String Uname,String DbN,String Tname) throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   String sql="insert into user_db_tb values('"+Uname+"','"+DbN+"','"+Tname+"')";
		   stmt.executeUpdate(sql);
		   
		   String sql2="create table "+Tname+"(sex varchar(1), age int)";
		   stmt.executeUpdate(sql2);
	   }
	   
	   //重命名数据表REN_TB
	   public void REN_TB(String Uname,String DbN,String OldName,String NewName) throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   
		   String sql="update user_db_tb set TableName='"+NewName+"'where UserName ='"+Uname+"'and DbName='"+DbN+"' and TableName='"+OldName+"'";
		   stmt.executeUpdate(sql);	
		   //方法一
		   //String sql3="ALTER TABLE "+OldName+" RENAME "+NewName; 
		   
		 //方法二
		   String sql2="rename table "+OldName+" to "+NewName;
		   stmt.executeUpdate(sql2);
	   }
	   
	   //删除数据表
	   public void DeleteTb(String Uname,String DbN,String Tname) throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   stmt.executeUpdate("drop table "+Tname);
		   stmt.executeUpdate("delete from user_db_tb where UserName ='"+Uname+"'and DbName='"+DbN+"' and TableName='"+Tname+"'");
	   }
	   
	   
	   
	   //建立数据库
	   public void CreateDb(String Uname,String DbN) throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   stmt.executeUpdate("create database "+DbN); 
		   String s="insert into user_db_tb(UserName,DbName) values('"+Uname+"','"+DbN+"')";
		   stmt.executeUpdate(s); 
		   
	   }
	   
	   //删除数据库
	  public boolean DropDb(String Uname,String DbN) throws Exception 
	   {
		   Statement stmt = conn.createStatement();
		   stmt.executeUpdate("delete from User_Db_Tb where UserName ='"+Uname+"'and DbName='"+DbN+"'");
		   stmt.executeUpdate("drop database "+DbN); 
		   
		   //不用删除数据库上的表  删除库就删除了里面的表吧
		   return true;
	   }
	  
	  //用来更新数据表
	  public void sendJsonArray(String jsonData) throws SQLException
	  {    
		  Statement stmt = conn.createStatement();
		  String TableName;
		//  String sql="update MyTest set ";
		
		  
		   JSONArray jsonArray = JSONArray.fromObject(jsonData);
	       JsonSize=jsonArray.size();
	       JsonStore=jsonArray;
	       
	       JSONObject TableNameObject = JSONObject.fromObject(jsonArray.getString(JsonSize-1));
	       TableName=TableNameObject.getString("key1");
	       System.out.println("数据表的表名为："+TableName);
	       
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
				                    sql=sql+ColName2+"="+ColVal2+" where dk17dc21="+MyId;
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
					    		        	sql="update "+TableName+" set ";
					    		        	sql=sql+ColName2+"='"+ColVal2+"',"+ColName3+"='"+ColVal3+"' where aaadkdc="+MyId;
					    		        }
				    	                else if(operation.equals("delete"))
				    	                {
				    	                	sql="delete from "+TableName+" where aaadkdc="+MyId;
				    	                	
				    	                }
				    	                else
				    	                {
				    	                	
				    	                	sql="insert into "+TableName+" values";
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
			    		        	sql="update "+TableName+" set ";
			    		        	sql=sql+ColName2+"='"+ColVal2+"',"+ColName3+"='"+ColVal3+"',"+ColName4+"='"+ColVal4+"' where aaadkdc="+MyId;
			    		        }
		    	                else if(operation.equals("delete"))
		    	                {
		    	                	sql="delete from "+TableName+" where aaadkdc="+MyId;
		    	                	
		    	                }
		    	                else
		    	                {
		    	                	
		    	                	sql="insert into "+TableName+" values";
		    	                	sql=sql+"('"+MyId+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"')";
		    	                	
		    	                }
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break;
		       } 
			   
	       } 
	
	  }
	  
	  public void Handle(int num) throws SQLException 
	  {
	    	Statement stmt = conn.createStatement();
		    String sql="update MyTest set ";
	    	
		    switch(num)
	     	{
			          case 1: 
			        	       { 
			        	    	    
			        	       }
				              
				               break;
			    	        
			    	   case 2: {
			    		        JSONObject jsonObject1 = JSONObject.fromObject(JsonStore.getString(0));
			    		        int MyId= Integer.parseInt(jsonObject1.getString("key1"));
			    		        String ColVal2 = jsonObject1.getString("key2");
			    		        
			                    JSONObject jsonObject2 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber)));
			                    String ColName2 = jsonObject2.getString("key1");
			                    
			                    
			                    sql=sql+ColName2+"="+ColVal2+" where dk17dc21="+MyId;
			                    }
			    	            break;
			    	            
			    	   case 3: 
			    	            {
			    	            	JSONObject jsonObject1 = JSONObject.fromObject(JsonStore.getString(0));
				    		        int MyId= Integer.parseInt(jsonObject1.getString("key1"));
				    		        String ColVal2 = jsonObject1.getString("key2");
				                    String ColVal3 = jsonObject1.getString("key3");
				                    
				                    JSONObject jsonObject2 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber)));
				                    String ColName2 = jsonObject2.getString("key1");
				                    
				                    JSONObject jsonObject3 = JSONObject.fromObject(JsonStore.getString((JsonSize-ColNumber+1)));
				                    String ColName3 = jsonObject3.getString("key1");
				                    sql=sql+ColName2+"='"+ColVal2+"',"+ColName3+"='"+ColVal3+"' where dk17dc21="+MyId;
				                
					            }
			    	            break;
			    	    
			    	   case 4: 
			           {
			             
			           }
	       } 
		  System.out.println("SQL语句的内容为："+sql);
		  stmt.executeUpdate(sql);  
		    
	   }
	  
	  
}