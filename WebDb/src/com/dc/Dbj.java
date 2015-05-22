package com.dc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.rjb.User;

public class Dbj {
	private Connection conn = null;
	 public void Connection( ) {
	       
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
	
	
	
	public String getDbTb(String Uname) throws SQLException{
		Connection( );
		PreparedStatement stmt = conn.prepareStatement("select distinct DbName from User_Db_Tb where UserName='"+Uname+"'");
        ResultSet rs = stmt.executeQuery();
		String head=null;
       
		//如果用户有库
		if (rs.next()) 
		  {
			head = "<?xml version='1.0' encoding='utf-8'?><folder item='您创建的数据库'>";
			  
			
			
		    do
		    {
					String Dname=rs.getString("DbName"); 
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("select TableName from User_Db_Tb where DbName='"+ Dname+ "'and UserName='"+Uname+"'"); 
				rs2.last();
				int i=rs2.getRow();
				rs2.first();
				
				
				// 如果库中有表  
				if (i>1)
				   {
						head =head+"<folder item='"+Dname+"'>";
				     	 do{
								String Tname=rs2.getString("TableName"); 
								if(Tname!=null)
								   head =head+"<folder item='"+Tname+"'/>";
				   		    }
						 while(rs2.next());
						 head = head +"</folder>" ;
				   }
				//如果库中没有表
				else
					head =head+"<folder item='"+Dname+"' isBranch='true'/>";
			 }
			while(rs.next());
			
			
			
			head = head +"</folder>" ;
		  }
	 		
		//如果用户没有库	
		else
			head = "<?xml version='1.0' encoding='utf-8'?><folder item='您创建的数据库' isBranch='true'/>";
		
		
		conn.close();
	    return head;
	}
	
	//添加联系人
	public String AddFriend(String Uname,String FName,String FnickName) throws SQLException
	{
		Connection( );
		String res="No Exist";
		Statement stmt = conn.createStatement();	
        String sql="select name from userinfo";       
        ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
	    {
		     System.out.println("用户名："+rs.getString("name")); 
			 if(FName.equals(rs.getString("name")))
		      {
		    	  String sql2="insert into friend_info values ('"+Uname+"','"+FName+"','"+FnickName+"')";
		  	      stmt.executeUpdate(sql2);
		  	      res="Exist";
		  	    break; 
		      }
	    }	      
		
	    conn.close();
	    return res;
		
	}
	
	
	//获取授权给您的用户表的真实表名
	public String getUTbName(String Uname,String TbName) throws SQLException
	{
		String Res=null;
		Connection( );		
		Statement stmt = conn.createStatement();	
        String sql="select UTbName,Editable from Share_Info where FName="+"'"+Uname+"'"+" and FTbName="+"'"+TbName+"'";
       
        System.out.println("sql语句为："+sql); 
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        Res=rs.getString("UTbName");  
        String Res2=rs.getString("Editable"); 
        Res=Res+"$"+Res2;
        System.out.println("真是表明名："+Res); 
        conn.close();        
        return Res;	
	}
	
	//共享数据表
	public void ShareTable(String Uname,String FName,String UTable,String FTable,String Note,String Editable) throws SQLException
	{	  	
		Connection( );
		Statement stmt = conn.createStatement();
		String RealTbName=Uname+"$"+FName+"$"+FTable;
		String sql="insert into Share_Info values ('"+Uname+"','"+FName+"','"+UTable+"','"+RealTbName+"','"+Note+"','"+Editable+"')";   
		stmt.executeUpdate(sql);		
	    conn.close();
	    
	    /*
		 System.out.println("用户名："+Uname); 
		 System.out.println("好友名："+FName); 
		 System.out.println("用户表："+UTable); 
		 System.out.println("好友表："+FTable); 
		 System.out.println("注释："+Note); 
		 System.out.println("可编辑："+Editable);
		*/  
		
	}
	
	//获得"与他人分享的数据表"中的Tree的数据源
	public List TbName_Shared(String Uname) throws SQLException
	{	  	
		   User u; 
		   ArrayList<User> userList=new ArrayList<User>(); 
		   Connection( );
		   PreparedStatement stmt = conn.prepareStatement("select distinct UTbName from Share_Info where UName='"+Uname+"'");
	       ResultSet rs = stmt.executeQuery();
	       userList.clear();
	       while(rs.next())
	       {
	    	   u=new User(); 
	    	   u.setLabel(rs.getString("UTbName"));
		       userList.add(u);   
	       }	
	       conn.close();
		  return userList;	
		
	}
	
	
	//获得"他人与您分享的数据表"中的Tree的数据源
	public List TbName_Sharing(String Uname) throws SQLException
	{	  	
		   User u; 
		   ArrayList<User> userList=new ArrayList<User>(); 
		   Connection( );
		   PreparedStatement stmt = conn.prepareStatement("select distinct FTbName from Share_Info where FName='"+Uname+"'");
	       ResultSet rs = stmt.executeQuery();
	       userList.clear();
	       while(rs.next())
	       {
	    	   u=new User(); 
	    	   u.setLabel(rs.getString("FTbName"));
		       userList.add(u);   
	       }	
	       conn.close();
		  return userList;			
	}
	
	//当弹出"与他人分享的数据表"中的表时，用来获取共享的所有用户的详细信息
	
	public List getSharedInfo(String TbName) throws SQLException
	{
		   User u; 
		   ArrayList<User> userList=new ArrayList<User>();
		   Connection( );
		   PreparedStatement stmt = conn.prepareStatement("select FName,Editable from share_info where UTbName='"+TbName+"'");
	       ResultSet rs = stmt.executeQuery();
	       userList.clear();
	       while(rs.next())
	       {
	    	   u=new User(); 
	    	   u.setLabel(rs.getString("FName"));
	    	   u.setkey1(rs.getString("Editable"));
		       userList.add(u);   
	       }	
	       conn.close();
		  return userList;	
	}
	
	
	
	//修改共享好友的共享情况
	public void modifyPri(String Uname,String fName,String TbName,String Edit) throws SQLException
	{ 
		
		   Connection( );
		   String se="update share_info set Editable='"+Edit+"'"+" where UName='"+Uname+"' and FName='"+fName+"'"+" and UTbName='"+TbName+"'";
		   System.out.print("查："+se);
		   PreparedStatement stmt = conn.prepareStatement("update share_info set Editable='"+Edit+"'"+" where UName='"+Uname+"' and FName='"+fName+"'"+" and UTbName='"+TbName+"'");
	       stmt.executeUpdate();
	       conn.close();
	}
	
	//删除该共享好友的共享权利
	public void deletePri(String Uname,String fName,String TbName) throws SQLException
	{ 
		
		   Connection( );
		   String se="delete from share_info where UName='"+Uname+"' and FName='"+fName+"'"+" and UTbName='"+TbName+"'";
		   System.out.print("查："+se);
		   PreparedStatement stmt = conn.prepareStatement("delete from share_info where UName='"+Uname+"' and FName='"+fName+"'"+" and UTbName='"+TbName+"'");
	       stmt.executeUpdate();
	       conn.close();
	}
	
	  //删除其他好友共享给自己的数据表	  
	  public void Del_ShareTb(String FTb) throws SQLException
		{ 
			
			   Connection( );			  
			   PreparedStatement stmt = conn.prepareStatement("delete from share_info where FTbName='"+FTb+"'");
		       stmt.executeUpdate();
		       conn.close();
		}
	  
	  //删除好友	  
	  public void DelFriend(String User,String Friend) throws SQLException
		{ 
			
			   Connection( );			  
			   PreparedStatement stmt = conn.prepareStatement("delete from friend_info where UserName='"+User+"' and FriendName='"+Friend+"'");
		       stmt.executeUpdate();
		       
		       stmt = conn.prepareStatement("delete from share_info where UName='"+User+"' and FName='"+Friend+"'");
		       stmt.executeUpdate();
		       
		       stmt = conn.prepareStatement("delete from share_info where UName='"+Friend+"' and FName='"+User+"'");
		       stmt.executeUpdate();
		       conn.close();
		}
	  
	  
	  
	  
	
	//获取通讯录
	public List getFName(String Uname) throws SQLException
	{
		   
		   User u; 
		   ArrayList<User> userList=new ArrayList<User>(); 
		   Connection( );
		   PreparedStatement stmt = conn.prepareStatement("select distinct FriendName from friend_info where UserName='"+Uname+"'");
	       ResultSet rs = stmt.executeQuery();
	       userList.clear();
	       while(rs.next())
	       {
	    	   u=new User(); 
	    	   u.setLabel(rs.getString("FriendName"));
		       userList.add(u);   
	       }	
	       conn.close();
		  return userList;		
	}
	
	
	
  }
