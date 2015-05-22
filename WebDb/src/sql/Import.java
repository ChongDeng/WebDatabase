package sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Import {
     
	
	public String getContent(String fileName) throws IOException
	{
           String rs="";		  
		   ArrayList filecon=new ArrayList();
		   filecon.clear();
		   String m = ""; 
		   String FilePath="F:\\Flex_File\\Tomcat\\webapps\\WebDb\\space\\"+fileName;		 
		   BufferedReader file = new BufferedReader(new FileReader(FilePath));   			   
		   
		   //将文本文件内容读入到filecon
		   while ((m = file.readLine()) != null)   
		   { 
			 if(!m.equals("0")) //文本结束的标志   
			  {   
			    if (!m.equals("")&&!m.contains("--")) //不需要读取空行   
			     {   
			        filecon.add(m);   
			      }   
			   }   
	        }   
			file.close(); 

	    //从filecon中解析出每行每列的数据
			for(int i=0;i<filecon.size();i++)
			{
				rs+=(String) filecon.get(i);			 
		    }
			System.out.println("内容为："+rs);	
	   	  return rs;
		  
		
	   }
	  
}
