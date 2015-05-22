package org.rjb;   
  
import java.sql.Connection;   
import java.sql.DriverManager;   
import java.sql.ResultSet;   
import java.sql.SQLException;   
import java.sql.Statement;   
import java.util.ArrayList;   
import java.util.List;   
import java.util.StringTokenizer;

import com.mysql.jdbc.ResultSetMetaData;
  
public class UserDao {   
     
	 private String NaMe="name"; 
	 User u; 
	 User u2; 
	 static String[] ColName=new String[100];
	 ResultSet rs;
	 ResultSet rs2;
	 int ColNum;
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
	   
    
    //获得所有行的数据
    public List getAllUser(String UserDbTb)throws SQLException
    {  
    	int location=UserDbTb.lastIndexOf("$");
    	String DbName=UserDbTb.substring(0,location);
	    String TbName=UserDbTb.substring(location+1,UserDbTb.length());
	    
	    System.out.println("数据库名字："+DbName);
	    System.out.println("数据表名字："+TbName);
    	Connection(DbName);   
        Statement st=conn.createStatement();   
        //String TableNameStr="select * from "+
        //rs=st.executeQuery("select * from MyTest"); 
        rs=st.executeQuery("select * from "+TbName); 
        ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
        int CountRow=0;
        
        ArrayList<User> userList=new ArrayList<User>();  
        
         ColNum=rsmd.getColumnCount();
		// String ColName[100];
		

    	       
		for(int i=1;i<=ColNum;++i) 
			{
			  ColName[i]=rsmd.getColumnName(i);
			  //System.out.println("我想打印"+ColName[i]);
			}
	   
		while(rs.next())
		 {   
			 u=new User(); 
	         Handle(ColNum);
	         userList.add(u); 
	         CountRow++;
	     }   
	
		
		if(CountRow==0)
		 {
			u2=new User(); 
			NewRow(ColNum);
	        userList.add(u2);
		 }
	    conn.close();  
		return userList;   
    }
    
    
   		
    public void Handle(int num) throws SQLException
    {    
    	System.out.println("你大爷第一列为："+ColName[1]);
    	switch(num)
     	{
		          case 1: 
		        	      u.setkey1(rs.getString(ColName[1]));
		    	         
		    	           break;
		    	        
		    	   case 2: {
			    		        u.setkey1(rs.getString(ColName[1]));
			    		        System.out.println("姓名"+u.getkey1());
			    		        u.setkey2(rs.getString(ColName[2]));
			    		        System.out.println("密码"+u.getkey2());
		                    }
		    	            break;
		    	   case 3: 
		    	            {
				               u.setkey1(rs.getString(ColName[1]));
				               System.out.println("妈的第一列为："+u.getkey1());
				               u.setkey2(rs.getString(ColName[2]));
				               System.out.println("妈的第二列为："+u.getkey2());
				               u.setkey3(rs.getString(ColName[3]));
				               System.out.println("妈的第三列为："+u.getkey3());
				            }
		    	            break;
		    	    
		    	   case 4: 
				           {
				              u.setkey1(rs.getString(ColName[1]));
				              u.setkey2(rs.getString(ColName[2]));
				              u.setkey3(rs.getString(ColName[3]));
				              u.setkey4(rs.getString(ColName[4]));
				           }
				           break;
		    	   case 5: 
				           {
				              u.setkey1(rs.getString(ColName[1]));
				              u.setkey2(rs.getString(ColName[2]));
				              u.setkey3(rs.getString(ColName[3]));
				              u.setkey4(rs.getString(ColName[4]));
				              u.setkey5(rs.getString(ColName[5]));
				           }
				           break;
		    	   case 6: 
				           {
				              u.setkey1(rs.getString(ColName[1]));
				              u.setkey2(rs.getString(ColName[2]));
				              u.setkey3(rs.getString(ColName[3]));
				              u.setkey4(rs.getString(ColName[4]));
				              u.setkey5(rs.getString(ColName[5]));
				              u.setkey6(rs.getString(ColName[6]));
				           }
				           break;
		    	   case 7: 
				           {
				              u.setkey1(rs.getString(ColName[1]));
				              u.setkey2(rs.getString(ColName[2]));
				              u.setkey3(rs.getString(ColName[3]));
				              u.setkey4(rs.getString(ColName[4]));
				              u.setkey5(rs.getString(ColName[5]));
				              u.setkey6(rs.getString(ColName[6]));
				              u.setkey7(rs.getString(ColName[7]));
				           }
				           break;
		    	   case 8: 
				           {
				              u.setkey1(rs.getString(ColName[1]));
				              u.setkey2(rs.getString(ColName[2]));
				              u.setkey3(rs.getString(ColName[3]));
				              u.setkey4(rs.getString(ColName[4]));
				              u.setkey5(rs.getString(ColName[5]));
				              u.setkey6(rs.getString(ColName[6]));
				              u.setkey7(rs.getString(ColName[7]));
				              u.setkey8(rs.getString(ColName[8]));
				           }
		                   break;
		    	   case 9: 
				           {
				              u.setkey1(rs.getString(ColName[1]));
				              u.setkey2(rs.getString(ColName[2]));
				              u.setkey3(rs.getString(ColName[3]));
				              u.setkey4(rs.getString(ColName[4]));
				              u.setkey5(rs.getString(ColName[5]));
				              u.setkey6(rs.getString(ColName[6]));
				              u.setkey7(rs.getString(ColName[7]));
				              u.setkey8(rs.getString(ColName[8]));
				              u.setkey9(rs.getString(ColName[9]));
				           }
				           break;
		    	   case 10: 
				           {
				              u.setkey1(rs.getString(ColName[1]));
				              u.setkey2(rs.getString(ColName[2]));
				              u.setkey3(rs.getString(ColName[3]));
				              u.setkey4(rs.getString(ColName[4]));
				              u.setkey5(rs.getString(ColName[5]));
				              u.setkey6(rs.getString(ColName[6]));
				              u.setkey7(rs.getString(ColName[7]));
				              u.setkey8(rs.getString(ColName[8]));
				              u.setkey9(rs.getString(ColName[9]));
				              u.setkey10(rs.getString(ColName[10]));
				           }
				           break;
		           
       } 
    }
    
 
    //添加一行，便于编辑 
    public void NewRow(int num)
    {
    	
    	switch(num)
     	{
		          case 1: 
		        	         u2.setkey1(" ");
		    	             break;
		    	        
		    	   case 2: {
		    		           u2.setkey1("1");
		    		           u2.setkey2(" ");
		    	            }
		    	            break;
		    	   case 3: 
		    	            {
		    	            	u2.setkey1("1");
			    		        u2.setkey2(" ");
			    		        u2.setkey3(" ");
			    		     }
		    	            break;
		    	    
		    	   case 4: 
				           {
				        	    u2.setkey1("1");
			    		        u2.setkey2(" ");
			    		        u2.setkey3(" ");
				                u2.setkey4(" ");
				           }
		    	   case 5: 
				           {
				        	    u2.setkey1("1");
			    		        u2.setkey2(" ");
			    		        u2.setkey3(" ");
				                u2.setkey4(" ");
				                u2.setkey5(" ");
				           }
		    	   case 6: 
		           {
		        	    u2.setkey1("1");
	    		        u2.setkey2(" ");
	    		        u2.setkey3(" ");
		                u2.setkey4(" ");
		                u2.setkey5(" ");
		                u2.setkey6(" ");   
		           }   
		    	   case 7: 
		           {
		        	    u2.setkey1("1");
	    		        u2.setkey2(" ");
	    		        u2.setkey3(" ");
		                u2.setkey4(" ");
		                u2.setkey5(" ");
		                u2.setkey6(" ");
		                u2.setkey7(" ");
		           }  
		    	   case 8: 
		           {
		        	    u2.setkey1("1");
	    		        u2.setkey2(" ");
	    		        u2.setkey3(" ");
		                u2.setkey4(" ");
		                u2.setkey5(" ");
		                u2.setkey6(" ");
		                u2.setkey7(" ");
		                u2.setkey8(" ");
		           } 
		    	   case 9: 
		           {
		        	    u2.setkey1("1");
	    		        u2.setkey2(" ");
	    		        u2.setkey3(" ");
		                u2.setkey4(" ");
		                u2.setkey5(" ");
		                u2.setkey6(" ");
		                u2.setkey7(" ");
		                u2.setkey8(" ");
		                u2.setkey9(" ");
		           }  
		    	   case 10: 
		           {
		        	    u2.setkey1("1");
	    		        u2.setkey2(" ");
	    		        u2.setkey3(" ");
		                u2.setkey4(" ");
		                u2.setkey5(" ");
		                u2.setkey6(" ");
		                u2.setkey7(" ");
		                u2.setkey8(" ");
		                u2.setkey9(" ");
		                u2.setkey10(" ");
		           }  
       } 
    }
    
    
    
    public List getColName(String UserDbTb) throws SQLException{
    	
    	int location=UserDbTb.lastIndexOf("$");
    	String DbName=UserDbTb.substring(0,location);
	    String TbName=UserDbTb.substring(location+1,UserDbTb.length());
			
    	Connection(DbName);   
        Statement stt=conn.createStatement();  
       
        //rs2=stt.executeQuery("select * from MyTest"); 
        rs2=stt.executeQuery("select * from "+TbName); 
        ResultSetMetaData rsmd2 = (ResultSetMetaData) rs2.getMetaData();
        
       
         ColNum=rsmd2.getColumnCount();
		// String ColName[100];
		

    	       
		for(int i=1;i<=ColNum;++i) 
			{
			  ColName[i]=rsmd2.getColumnName(i);
			  System.out.println("我想打印"+ColName[i]);
			}

    	ArrayList List = new ArrayList();
    	 for(int i=1;i<=ColNum;++i) 
    		 System.out.println("老子要读列名数组： "+ColName[i]);
    	 
    	 for(int i=0;i<ColNum;++i) 
			 List.add(ColName[i+1]);
		conn.close();	
    	return List;
    }
 
    
   //SQL查询，获得所有行的数据
    public List SQLArrData(String SqlStr,String UserDb)throws SQLException
    {  
    	  ArrayList<Object> userList=new ArrayList();
    	  
    	  Connection(UserDb);   
          Statement st=conn.createStatement(); 
    	
    	  StringTokenizer st1 = new StringTokenizer(SqlStr);
		  String[] Store =new String[100];
		  int i=0;
		 //Store保存的是传到后台的查询字符串中的各个子串
		  while (st1.hasMoreTokens()) 
		  {
			 Store[i++]=st1.nextToken();
		  }
		  
		  if(Store[1].equals("*"))
		  {
			    rs=st.executeQuery(SqlStr);
			    ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		        int CountRow=0;
		        
		         ColNum=rsmd.getColumnCount();
				// String ColName[100];
				       
				for(int n=1;n<=ColNum;++n) 
					{
					  ColName[n]=rsmd.getColumnName(n);
					  System.out.println("全部列打印名"+ColName[n]);
					}
			   
				while(rs.next())
				 {   
					 System.out.println("全部列打印2");
					 u=new User(); 
			         Handle(ColNum);
			         userList.add(u); 
			         CountRow++;
			     }   
			
				if(CountRow==0)
				 {
					u2=new User(); 
					NewRow(ColNum);
			        userList.add(u2);
				 }
				conn.close();
		  }
		  
		  else
		  {
			  
			  int CountRow=0;
			  int k=1;
			  
			  Store[1]="ID"+","+Store[1];			  
			  String ExecStr="";
			  for(int mem=0;Store[mem]!=null;mem++) 
				  ExecStr+=Store[mem]+" ";
			  ExecStr=ExecStr.substring(0,(ExecStr.length()-1)); 
              System.out.println("修改后的SQL语句为："+ExecStr);
			  
              StringTokenizer StrProc = new StringTokenizer(Store[1],",");			  
			  while (StrProc.hasMoreTokens()) 
			  {
				  ColName[k++]=StrProc.nextToken();
				  System.out.println("列名："+ColName[k]);
			  }
			  
			  ColNum=k-1;
			  System.out.println("有多少列："+ColNum);
			  
			  rs=st.executeQuery(ExecStr);
			  

				while(rs.next())
				 {   
					 u=new User(); 
			         Handle(ColNum);
			         userList.add(u); 
			         CountRow++;
			     }   
				System.out.println("记录行数为："+CountRow);
				
				if(CountRow==0)
				 {
					u2=new User(); 
					NewRow(ColNum);
			        userList.add(u2);
				 }
				conn.close();
			
				  
		  }
			  
			return userList;
		  
    }
   
     //SQL查询，获得列名
     public List SQLColName(String SqlStr,String UserDb) throws SQLException
     {
    	  ArrayList userList=new ArrayList();
    	  
    	  StringTokenizer st1 = new StringTokenizer(SqlStr);
		  String[] Store =new String[100];
		  int i=0;
		  while (st1.hasMoreTokens()) 
		  {
			 Store[i++]=st1.nextToken();
		  }
    	 
		  if(Store[1].equals("*"))
		  {
			    Connection(UserDb);   
	            Statement stt=conn.createStatement(); 
			    rs2=stt.executeQuery(SqlStr); 
		        ResultSetMetaData rsmd2 = (ResultSetMetaData) rs2.getMetaData();
		        
		        ColNum=rsmd2.getColumnCount();
			       
				for(int k=1;k<=ColNum;++k) 
					{
					  ColName[k]=rsmd2.getColumnName(k);
					  System.out.println("我想打印"+ColName[k]);
					}

		    	 
		    	 for(int j=0;j<ColNum;++j) 
		    		 userList.add(ColName[j+1]);
		  }
    	 
    	 else
		  {
			  StringTokenizer StrProc = new StringTokenizer(Store[1],",");
			  String[] ColArr =new String[50];
			  int k=0;
			  ColArr[k++]="ID";
			  while (StrProc.hasMoreTokens()) 
			  {
				  ColArr[k++]=StrProc.nextToken();
			  }
			  
			  for(int num=0;ColArr[num]!=null;num++)
			  {
				  System.out.println("加了第一列的列名为："+ColArr[num]);
			  	  userList.add(ColArr[num]);
			  }
				  
		  }
		  return userList;
      }
   
  
}  
