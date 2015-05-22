package com.dc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Process {
	private Connection conn = null;

	public Process() {
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

	// 获得密码
	public String getPass(String userName) {
		String pass = "";

		try {
			PreparedStatement stmt = conn
					.prepareStatement("select pwd from userinfo where name='"
							+ userName + "'");
			ResultSet results = stmt.executeQuery();
			byte[] buff = new byte[4096];
			boolean more = results.next();
			while (more) {
				for (int i = 1; i <= results.getRow(); i++) {
					String s1 = results.getString(i);
					pass = pass + s1;
					 System.out.print(results.getString(i)+" \n ");
				}
				more = results.next();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return pass;
	}

	//检查注册的用户名是否可用
	public String check(String userName) {
		String pass = "0";

		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from userinfo where name='"
							+ userName + "'");
			ResultSet results = stmt.executeQuery();
			byte[] buff = new byte[4096];
			
			if (results.next()) {
				pass="1";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return pass;
	}
	
	
	//注册
	public  boolean Regist(String Name,String Pwd){
		int result = 1;
		try {
			    Statement stmt = conn.createStatement();
				String s="insert into userinfo values('"+Name+"','"+Pwd+"')";
				stmt.executeUpdate(s);
		     } catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();

		                                try {
				                              conn.close();
			                                 } catch (SQLException e1) 
			                                     {
															// TODO Auto-generated catch block
															e1.printStackTrace();
										          }
		                            }
		
		   if (result == 1)
				return true;
		   else
			    return false;    
	 }

	
	
	// 修改密码
	public boolean modifyPass(String newPass, String user) {
		int result = 0;
		String upString = "update login set password='" + newPass
				+ "'where name='" + user + "'";
		try {
			PreparedStatement stmt = conn.prepareStatement(upString);
			int i = stmt.executeUpdate();
			//
			System.out.print(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (result == 1) {
			return true;
		} else
			return false;
	}
  
}
