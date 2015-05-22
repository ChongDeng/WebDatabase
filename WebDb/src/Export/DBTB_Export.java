package Export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import net.sf.json.JSONObject;

import org.rjb.User;

import com.healthmarketscience.jackcess.Database;
import com.mysql.jdbc.ResultSetMetaData;


public class DBTB_Export {
	 WritableSheet sheet;
     WritableWorkbook book; 
	
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
	 
	 
	public List getDbName(String UName) throws SQLException
   {
		User u; 
		ArrayList userList=new ArrayList(); 
		String ResultStr="";
		Connection("B8");
		Statement stmt = conn.createStatement();
		String sql="select distinct DbName from User_Db_Tb where UserName='"+UName+"'";
		ResultSet rs=stmt.executeQuery(sql);		
		userList.clear();
		while(rs.next())
	    {
	    	   u=new User(); 
	    	   u.setLabel(rs.getString("DbName"));
		       userList.add(u);   
	    }	
		conn.close();
		return userList;
   }
	
	public List getTbName(String UserName,String DbName) throws SQLException
	   {			
		    User u; String TbName;
			ArrayList userList=new ArrayList(); 
			String ResultStr="";
			Connection("B8");
			Statement stmt = conn.createStatement();
			String sql="select TableName from user_db_tb where UserName='"+UserName+"' and DbName='"+DbName+"'";
			System.out.println("SQL语句为为"+sql);
			ResultSet rs=stmt.executeQuery(sql);		
			userList.clear();
			while(rs.next())
		    {	    	  
				TbName=rs.getString("TableName");				
				if(!rs.wasNull())
				{
				   u=new User(); 
		    	   u.setLabel(TbName);
		    	   userList.add(u);
				}						          
		    }	
			conn.close();
			return userList;
	   }
	
	public void creatTempExcel(String UserName,String DbName,String TbName) throws IOException, SQLException, RowsExceededException, WriteException
	{
	    String[][] TbData = new String[50][50];	   
	    int ColNum,Row=0;
		String ExcelPath="F:\\Flex_File\\Tomcat\\webapps\\WebDb\\space\\"+TbName+".xls";		
		File myFilePath=new File(ExcelPath);
		if(!myFilePath.exists())
		    myFilePath.createNewFile();  //创建Excel空文件
				
		
		String RealDb=UserName+"$"+DbName;
		Connection(RealDb);
		Statement stmt = conn.createStatement();
		String sql="select * from "+TbName;
		ResultSet rs=stmt.executeQuery(sql);
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		ColNum=rsmd.getColumnCount();
		
		for(int i=0;i<ColNum;++i)//读取表的列名 
		{
		   TbData[0][i]=rsmd.getColumnName(i+1);
		   System.out.print("列名："+TbData[0][i]+" ");		   
		}
		Row=1;
		System.out.println();
		
		while(rs.next())//读取表的列值
		 {   
			for(int i=0;i<ColNum;++i)
			  {
				TbData[Row][i]=rs.getString(TbData[0][i]);
				System.out.print("列值："+TbData[Row][i]+" ");	
			  }
			System.out.println();
			Row++;
	     } 
   
		
		book=Workbook.createWorkbook(new File(ExcelPath)); 		
		sheet=book.createSheet("Sheet1",0); //生成名为“第一页”的工作表，参数0表示这是第一页 
		for(int r=0;r<Row;r++)     //往Excel空文件里写入表的数据
		  for(int i=0;i<ColNum;++i)
		  {
			 String ColVal=TbData[r][i];
			 Label label=new Label(i,r,ColVal);
			 sheet.addCell(label);
		  }	
		book.write(); 
		book.close();
		conn.close();	 
	}
	
	public void creatTempTxt(String FileName,String TextStr) //将由前台传过来的数据表的名字—TbForSent,作为临时文件的名字-FileName
	{
		try{			
			String TxtPath="F:\\Flex_File\\Tomcat\\webapps\\WebDb\\space\\"+FileName+".txt";
			
			File file = new File(TxtPath);
			if (file.exists()){					
				file.delete();
			}				
			file.createNewFile(); //创建空Txt文件
			
			BufferedWriter output = new BufferedWriter(new FileWriter(file));	
			int index=TextStr.indexOf("Replace_Flg");
			while(index!=-1)
			{
			  String str=TextStr.substring(0,index);			 
			  TextStr=TextStr.substring(index+11,TextStr.length());			  
			  output.write(str);	
			  output.write("\r\n");			  
			  index=TextStr.indexOf("Replace_Flg");
			}						
		    output.close();
	} catch (Exception ex) {
		System.out.println(ex);
	}
	}
	
	public void creatTempAccess(String UserName,String DbName) throws IOException, SQLException
	{
		    ArrayList List = new ArrayList();    	
		    String Source=UserName+"$"+DbName;
			String AccesccPath="F:\\Flex_File\\Tomcat\\webapps\\WebDb\\space\\"+DbName+".accdb";
			//创建数据库
	        File file = new File(AccesccPath);
			if (file.exists()) {			
				file.delete();
			}		
			file.createNewFile();
			Database db = Database.create(file);
			
			Connection(Source);
			Statement stmt = conn.createStatement();		   
		   //查询源数据库的所有数据表
		   stmt.executeQuery("use "+Source);
		   ResultSet rs=stmt.executeQuery("show tables");
		   int j=1;
		   List.clear();
		   while(rs.next())
		   {
		         //System.out.print(rs.getString(j));
				List.add(rs.getString(j));			         
		   }
		
		   for(int i=0;i<List.size();i++)
		   {
			     ResultSet rs2=stmt.executeQuery("select * from "+List.get(i));
			     Database.open(new File(AccesccPath)).copyTable(List.get(i).toString(), rs2); 
			     
		   }
		   System.out.println("成功");		
	}
	
}
