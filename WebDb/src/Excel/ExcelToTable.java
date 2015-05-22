package Excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelToTable {
       
	   private String[ ] PriArray= new String[20];
	   private int PriNumber=0;
	   private int ColNumber;
	   private int JsonSize;
	  
	   private String RealDbName;
	   private String TbName;
	   
	   private ArrayList ColNameList = new ArrayList();
	   
	   private Connection conn = null;
	   
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
	   
	
	
	 //建立数据表  1203版本
	   public void CreaTable(String jsonData) throws SQLException
	   {
		   ColNameList.clear();
		   
		   JSONArray jsonArray = JSONArray.fromObject(jsonData);
		   
		   JsonSize=jsonArray.size();
		   JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(JsonSize-1));
	       //获得数据库名、表名
	       String UserDbTb=jsonObject.getString("key1");
	       int location=UserDbTb.lastIndexOf("$");
	       RealDbName=UserDbTb.substring(0,location);
	       TbName=UserDbTb.substring(location+1,UserDbTb.length());
	       
	       int loc=RealDbName.lastIndexOf("$");
	       String UserName=RealDbName.substring(0,loc);
	       String DbName=RealDbName.substring(loc+1,RealDbName.length());
		  
	       Connection(RealDbName);
		   Statement stmt = conn.createStatement();
	       
		   String sql2="create table "+TbName+"(";
		   sql2+="ID INTEGER(11) NOT NULL AUTO_INCREMENT,";
		   JsonSize=JsonSize-2;
		   for(int i=0;i<=JsonSize;i++)
	       {
		           JSONObject jsonObject3 = JSONObject.fromObject(jsonArray.getString(i));
		           String colname = jsonObject3.getString("key1");
		           ColNameList.add(colname);
		           String coltype = jsonObject3.getString("key2");
		           String Pri=jsonObject3.getString("key3");
		       	           
		           if(Pri.equals("true"))
		           {
		        	   sql2=sql2+colname+" "+coltype+" NOT NULL"+",";
		        	   PriArray[PriNumber++]=colname;
		        	   continue;
		           }
		           else
		           {
		        	   sql2=sql2+colname+" "+coltype+","; 
		           }
		           
		           
	        } 
		   
		   String SqlPri="PRIMARY KEY (ID,";   
		   if(PriNumber>0)
		   {
			 for(int i=0;i<PriNumber;i++)
				 SqlPri+=PriArray[i]+",";
		   }
		   SqlPri=SqlPri.substring(0,(SqlPri.length()-1));
		   SqlPri+="))"; 
		   sql2+=SqlPri; 
		   
		   //写这一步的原因是因为创建了一个表的主键后，再创建另一个表的时候，这个主键会自动插在里一个表中，很诡异。
		   //所以，通过PriNumber置0来阻止添加	 
		   PriNumber=0;
		   
	       System.out.println("建立数据表的SQL为："+sql2);
	       stmt.executeUpdate(sql2);
	      	      
	      //创建用户—库-表的纪录应该放在创建表的后面，以防止现表没有建成功，然而继续添加了这条记录
	       String sql="insert into B8.user_db_tb values('"+UserName+"','"+DbName+"','"+TbName+"')";
		   stmt.executeUpdate(sql);
		   conn.close();	   
		
		 } 
	   
	 //为数据表添加行数据  1203版本
	   public void InSertRow(String jsonData) throws SQLException
	   {
           Connection(RealDbName);
		   Statement stmt = conn.createStatement();
           
           JSONArray jsonArray = JSONArray.fromObject(jsonData);
	       JsonSize=jsonArray.size();	     
	       ColNumber=ColNameList.size();	     
	       
	       for(int i=0;i<=JsonSize-1;i++)
	       {
	    	   String sql="insert into "+TbName+"(";	    	   
	    	   for(int j=0;j<ColNameList.size();j++)
	    		   sql=sql+ColNameList.get(j)+",";
	    	   sql=sql.substring(0,(sql.length()-1)); 
	    	   sql+=") values(";
	    	   
	    	   switch(ColNumber)
		     	{
				          case 1: 
				        	       { 
				        	    	    //取列值
					    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
					    		        String ColVal1 = ColValObject.getString("key1");
					    		        sql+="'"+ColVal1+"')";
					                    System.out.println("SQL语句的内容为："+sql);
					             	    stmt.executeUpdate(sql);  
				        	       }
					              
					               break;
				    	        
				    	   case 2: {
						    		    //取列值
					    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
					    		        String ColVal1 = ColValObject.getString("key1");
					    		        String ColVal2 = ColValObject.getString("key2");
					                    sql+="'"+ColVal1+"','"+ColVal2+"')";
					                    System.out.println("SQL语句的内容为："+sql);
					             	    stmt.executeUpdate(sql); 
				                    }
				    	            break;
				    	            
				    	   case 3: {
						    		    //取列值
					    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
					    		        String ColVal1 = ColValObject.getString("key1");
					    		        String ColVal2 = ColValObject.getString("key2");
					                    String ColVal3 = ColValObject.getString("key3");
					                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"')";
					                    System.out.println("SQL语句的内容为："+sql);
					             	    stmt.executeUpdate(sql); 			    	            	
						            }
				    	            break;
				    	   case 4: 
		    	            {	    	            		    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
			    		        String ColVal1 = ColValObject.getString("key1");
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"')";			                 
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break;
		    	            
				    	   case 5: 
		    	            {	    	            		    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
			    		        String ColVal1 = ColValObject.getString("key1");
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    String ColVal5 = ColValObject.getString("key5");
			                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"','"+ColVal5+"')";			                 
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break;
		    	            
				    	   case 6: 
		    	            {	    	            		    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
			    		        String ColVal1 = ColValObject.getString("key1");
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    String ColVal5 = ColValObject.getString("key5");
			                    String ColVal6 = ColValObject.getString("key6");
			                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"','"+ColVal5+"','"+ColVal6+"')";			                 
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break; 
		    	            
				    	   case 7: 
		    	            {	    	            		    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
			    		        String ColVal1 = ColValObject.getString("key1");
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    String ColVal5 = ColValObject.getString("key5");
			                    String ColVal6 = ColValObject.getString("key6");
			                    String ColVal7 = ColValObject.getString("key7");
			                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"','"+ColVal5+"','"+ColVal6+"','"+ColVal7+"')";			                 
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break; 
		    	            
				    	   case 8: 
		    	            {	    	            		    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
			    		        String ColVal1 = ColValObject.getString("key1");
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    String ColVal5 = ColValObject.getString("key5");
			                    String ColVal6 = ColValObject.getString("key6");
			                    String ColVal7 = ColValObject.getString("key7");
			                    String ColVal8 = ColValObject.getString("key8");
			                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"','"+ColVal5+"','"+ColVal6+"','"+ColVal7+"','"+ColVal8+"')";			                 
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break;  
		    	            
				    	   case 9: 
		    	            {	    	            		    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
			    		        String ColVal1 = ColValObject.getString("key1");
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    String ColVal5 = ColValObject.getString("key5");
			                    String ColVal6 = ColValObject.getString("key6");
			                    String ColVal7 = ColValObject.getString("key7");
			                    String ColVal8 = ColValObject.getString("key8");
			                    String ColVal9 = ColValObject.getString("key9");
			                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"','"+ColVal5+"','"+ColVal6+"','"+ColVal7+"','"+ColVal8+"','"+ColVal9+"')";			                 
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break;  
		    	            
				    	   case 10: 
		    	            {	    	            		    		        
			    		        //取列值
			    		        JSONObject ColValObject = JSONObject.fromObject(jsonArray.getString(i));
			    		        String ColVal1 = ColValObject.getString("key1");
			    		        String ColVal2 = ColValObject.getString("key2");
			                    String ColVal3 = ColValObject.getString("key3");
			                    String ColVal4 = ColValObject.getString("key4");
			                    String ColVal5 = ColValObject.getString("key5");
			                    String ColVal6 = ColValObject.getString("key6");
			                    String ColVal7 = ColValObject.getString("key7");
			                    String ColVal8 = ColValObject.getString("key8");
			                    String ColVal9 = ColValObject.getString("key9");
			                    String ColVal10 = ColValObject.getString("key10");
			                    sql+="'"+ColVal1+"','"+ColVal2+"','"+ColVal3+"','"+ColVal4+"','"+ColVal5+"','"+ColVal6+"','"+ColVal7+"','"+ColVal8+"','"+ColVal9+"','"+ColVal10+"')";			                 
			                    System.out.println("SQL语句的内容为："+sql);
			             	    stmt.executeUpdate(sql);  
				            }
		    	            break;  
		    	          
		    	            
		    	            
		       } 
		    } 
	      conn.close(); 
	  } 
}
