package Txt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.rjb.User;

import java.util.List; 

public class TextToTable 
{
	    int ColNum;
	    static User u;
	    static ArrayList filecon=new ArrayList();
	    static ArrayList userList = new ArrayList();
	  
		public  List RTextContent(String TextPath,String DividerCode)
		{
		  try{
			   userList.clear(); filecon.clear();
			   String m = "";   
			   BufferedReader file = new BufferedReader(new FileReader(TextPath));   			   
			   //将文本文件内容读入到filecon
			   while ((m = file.readLine()) != null)   
			   { 
				 if(!m.equals("0")) //文本结束的标志   
				  {   
				    if (!m.equals("")) //不需要读取空行   
				     {   
				        filecon.add(m);   
				      }   
				   }   
		        }   
				file.close(); 
	
	    	    //从filecon中解析出每行每列的数据
				for(int i=0;i<filecon.size();i++)
				{
				   String str=(String) filecon.get(i);
				   StringTokenizer st = new StringTokenizer(str,DividerCode);
				   String[] Store =new String[100];
				   int j=0;
				   while (st.hasMoreTokens()) 
					 {
						 Store[j++]=st.nextToken();
					 }
		
				   for(int k=0;k<j;k++)
				    {
			    		  Store[k]=Store[k].replaceAll("'","");
				  	      System.out.print("列值为"+Store[k]+" ");
				    }
						  
				    u=new User(); 
					Handle(Store,j);
					userList.add(u); 
			      }
				
				
		     } catch (FileNotFoundException e) {
		          e.printStackTrace();
		     } catch (IOException e) {
		        e.printStackTrace();
		     }
		     return userList;   
		}
		
		public  List ReadTextCont(String TextPath)
		{
		  try{
			   userList.clear(); filecon.clear();
			   String m = "";   
			   String DividerFlag="Other";
			   int ColNum=0;
			   BufferedReader file = new BufferedReader(new FileReader(TextPath));   			   
			   //将文本文件内容读入到filecon
			   while ((m = file.readLine()) != null)   
			   { 
				 if(!m.equals("0")) //文本结束的标志   
				  {   
				    if (!m.equals("")) //不需要读取空行   
				     {   
				        filecon.add(m);   
				      }   
				   }   
		        }   
				file.close(); 
				
				String Str=(String) filecon.get(0);
				int SpacerIndex=Str.indexOf(" ");
				int TabIndex=Str.indexOf("	");
				int FenhaoIndex=Str.indexOf(";");
				int DouhaoIndex=Str.indexOf(",");
				System.out.println("各索引依次为："+SpacerIndex+" "+TabIndex+" "+FenhaoIndex+" "+DouhaoIndex);  
				
				if(SpacerIndex!=-1)
					DividerFlag=" ";
				if(TabIndex!=-1)
					DividerFlag="	";
				if(FenhaoIndex!=-1)
					DividerFlag=";";
				if(DouhaoIndex!=-1)
					DividerFlag=",";				
				System.out.println("分隔符为："+DividerFlag);  
				
				//从filecon中解析出每行每列的数据
				if(!DividerFlag.equals("Other"))
				{
					for(int i=0;i<filecon.size();i++)
					{
					   String str=(String) filecon.get(i);
					   StringTokenizer st = new StringTokenizer(str,DividerFlag);
					   String[] Store =new String[100];
					   int j=0;
					   while (st.hasMoreTokens()) 
						 {
							 Store[j++]=st.nextToken();
						 }
					   ColNum=j;
					   for(int k=0;k<j;k++)
					    {
				    		  Store[k]=Store[k].replaceAll("'","");
					  	      System.out.print("列值为"+Store[k]+" ");
					    }
							  
					    u=new User(); 
						Handle(Store,ColNum);
						userList.add(u); 
				      }
					 	
				}
				else
				{
					for(int i=0;i<filecon.size();i++)
					{
					   String str=(String) filecon.get(i);
					   int j=0;
					   String[] Store =new String[100];
					   while(str.length()>0)
						 {
							 int index1=str.indexOf("'");
							 str=str.substring(index1+1, str.length());							 
							 int index2=str.indexOf("'");
							 String StrInfo=str.substring(0,index2);
							 System.out.print("有效信息为："+StrInfo+" ");
							 Store[j++]=StrInfo;							 
							 str=str.substring(index2+1, str.length());
						 }					  
					   ColNum=j;
					   
					    u=new User(); 
						Handle(Store,ColNum);
						userList.add(u); 
				      }
					
					userList.add(ColNum);
				}
					
	
	    	    
				
				
				
		     } catch (FileNotFoundException e) {
		          e.printStackTrace();
		     } catch (IOException e) {
		        e.printStackTrace();
		     }
		     return userList;   
		}
		
		
	    public static void Handle(String[] arr,int num)
	    {   	    	
	    	switch(num)
	     	{
			          case 1: 
			        	         u.setkey1(arr[0]);    	         
			    	              break;
			    	        
			    	   case 2: {
			    		           u.setkey1(arr[0]);
					               u.setkey2(arr[1]);					              
			                    }
			    	            break;
			    	   case 3: 
			    	            {
			    	            	u.setkey1(arr[0]);
							        u.setkey2(arr[1]);
							        u.setkey3(arr[2]);
							        System.out.println("第一列："+u.getkey1());				    		       
				    		        System.out.println("第二列："+u.getkey2());
				    		        System.out.println("第三列："+u.getkey3());
							       
					            }
			    	            break;
			    	    
			    	   case 4: 
					           {
					        	   u.setkey1(arr[0]);
							       u.setkey2(arr[1]);
							       u.setkey3(arr[2]);
							       u.setkey4(arr[3]);
							    
					           }
					           break;
			    	   case 5: 
					           {
					        	   u.setkey1(arr[0]);
							       u.setkey2(arr[1]);
							       u.setkey3(arr[2]);
							       u.setkey4(arr[3]);
							       u.setkey5(arr[4]);
							         
					           }
					           break;
			    	   case 6: 
					           {
					        	   u.setkey1(arr[0]);
							       u.setkey2(arr[1]);
							       u.setkey3(arr[2]);
							       u.setkey4(arr[3]);
							       u.setkey5(arr[4]);
							       u.setkey6(arr[5]);
							        
					           }
					           break;
			    	   case 7: 
					           {
					        	   u.setkey1(arr[0]);
							       u.setkey2(arr[1]);
							       u.setkey3(arr[2]);
							       u.setkey4(arr[3]);
							       u.setkey5(arr[4]);
							       u.setkey6(arr[5]);
							       u.setkey7(arr[6]);
							    
					           }
					           break;
			    	   case 8: 
					           {
					        	   u.setkey1(arr[0]);
							       u.setkey2(arr[1]);
							       u.setkey3(arr[2]);
							       u.setkey4(arr[3]);
							       u.setkey5(arr[4]);
							       u.setkey6(arr[5]);
							       u.setkey7(arr[6]);
							       u.setkey8(arr[7]);
							         
					           }
			                   break;
			    	   case 9: 
					           {
					        	  u.setkey1(arr[0]);
						          u.setkey2(arr[1]);
						          u.setkey3(arr[2]);
						          u.setkey4(arr[3]);
						          u.setkey5(arr[4]);
						          u.setkey6(arr[5]);
						          u.setkey7(arr[6]);
						          u.setkey8(arr[7]);
						          u.setkey9(arr[8]);
						             
					           }
					           break;
			    	   case 10: 
					           {
					        	  u.setkey1(arr[0]);
					              u.setkey2(arr[1]);
					              u.setkey3(arr[2]);
					              u.setkey4(arr[3]);
					              u.setkey5(arr[4]);
					              u.setkey6(arr[5]);
					              u.setkey7(arr[6]);
					              u.setkey8(arr[7]);
					              u.setkey9(arr[8]);
					              u.setkey10(arr[9]);
					           }
					           break;
			           
	       } 
	    }		
		
	    
	    public  String TextCont_Resolve(String TextPath)
		{
		   String DividerFlag=""; 
	    	try{
				   userList.clear(); filecon.clear();
				   String m = "";				   
				   BufferedReader file = new BufferedReader(new FileReader(TextPath));   			   
				   
				   //将文本文件内容读入到filecon
				   while ((m = file.readLine()) != null)   
				   { 
					 if(!m.equals("0")) //文本结束的标志   
					  {   
					    if (!m.equals("")) //不需要读取空行   
					     {   
					        filecon.add(m);   
					      }   
					   }   
			        }   
					file.close(); 
		
					String Str=(String) filecon.get(0);
					int SpacerIndex=Str.indexOf(" ");
					int TabIndex=Str.indexOf("	");
					int FenhaoIndex=Str.indexOf(";");
					int DouhaoIndex=Str.indexOf(",");
					System.out.println("各索引依次为："+SpacerIndex+" "+TabIndex+" "+FenhaoIndex+" "+DouhaoIndex);  
					
					if(SpacerIndex!=-1)
						DividerFlag=" ";
					else if(TabIndex!=-1)
						DividerFlag="	";
					else if(FenhaoIndex!=-1)
						DividerFlag=";";
					else if(DouhaoIndex!=-1)
						DividerFlag=",";
					else
						DividerFlag="Other";
					
					System.out.println("返回值为："+DividerFlag);    	    
				
		     } catch (FileNotFoundException e) {
		          e.printStackTrace();
		     } catch (IOException e) {
		        e.printStackTrace();
		     }
		  return DividerFlag;   
	  }
		
}
