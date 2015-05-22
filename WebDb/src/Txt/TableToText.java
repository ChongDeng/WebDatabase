package Txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TableToText {
	public void CreateTxt(String Path,String TextStr)
	{
		try{
				System.out.println("内容为："+TextStr);
			    
				//int location=TextStr.lastIndexOf("\n");
				//System.out.println("索引为："+location); 
			    
				//String TbName=UserDBTb.substring(location+1,UserDBTb.length());
				   
				File file = new File(Path);
				if (file.exists()){					
					file.delete();
				}				
				file.createNewFile();				
				BufferedWriter output = new BufferedWriter(new FileWriter(file));	
				int index=TextStr.indexOf("Replace_Flg");
				while(index!=-1)
				{
				  String str=TextStr.substring(0,index);
				  System.out.println("截取的为："+str);
				  TextStr=TextStr.substring(index+11,TextStr.length());
				  System.out.println("剩余的为："+TextStr);
				  output.write(str);	
				  output.write("\r\n");
				  
				  index=TextStr.indexOf("Replace_Flg");
				}						
			    output.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
