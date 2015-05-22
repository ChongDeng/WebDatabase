package Excel;


import java.io.*; 

import jxl.*; 
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TableToExcel 
{ 
	private int ColNum;
	private int RowNum;
    private int JsonSize;
    private JSONArray jsonArray;
    WritableSheet sheet;
    WritableWorkbook book;
	
	public void CreateExcel(String jsonData ) throws IOException, RowsExceededException, WriteException 
		{ 
			       jsonArray = JSONArray.fromObject(jsonData);
				   //获得行数
				   RowNum=jsonArray.size();
				   System.out.println("行数为："+RowNum); 
			       //获得保存Excel的地址
			       JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(0));
			       String ExcelPath=jsonObject.getString("Path");
			       System.out.println("地址为："+ExcelPath); 
			       
			        File myFilePath=new File(ExcelPath);
				    if(!myFilePath.exists())
				      myFilePath.createNewFile();
					
			       //获得列数
			       JSONObject jsonObject2 = JSONObject.fromObject(jsonArray.getString(1));
			       String ColNumStr=jsonObject2.getString("Path");
			       ColNum=Integer.parseInt(ColNumStr);
			       System.out.println("列数为："+ColNum);
			
				    book=Workbook.createWorkbook(new File(ExcelPath)); 
					//生成名为“第一页”的工作表，参数0表示这是第一页 
					sheet=book.createSheet("Sheet1",0); 
					 int count=0;
					for(int r=0;r<RowNum;r++)
					{
						JSONObject RowObj = JSONObject.fromObject(jsonArray.getString(r));
						switch(ColNum)
				     	{
						          case 1: 
						        	       { 
						        	    	   String Col1=RowObj.getString("key0");
						        	    	   Label label=new Label(0,r,Col1);
						        	    	   sheet.addCell(label); 
						        	       }
							              
							               break;
						    	        
						    	   case 2: {   String Col1=RowObj.getString("key0");
						    	               System.out.print(Col1+" ");
			        	    	               Label label=new Label(0,r,Col1);
			        	    	               String Col2=RowObj.getString("key1");
			        	    	               System.out.println(Col2+" ");
			        	    	               Label label2=new Label(1,r,Col2);
			        	    	               sheet.addCell(label);sheet.addCell(label2); 
			        	    	               
						                    }
						    	            break;
						    	            
						    	   case 3: 
						    	            {
						    	            	   String Col1=RowObj.getString("key0");
				        	    	               Label label=new Label(0,r,Col1);
				        	    	               String Col2=RowObj.getString("key1");
				        	    	               Label label2=new Label(1,r,Col2);
				        	    	               String Col3=RowObj.getString("key2");
				        	    	               Label label3=new Label(2,r,Col3);
				        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3); 	  	  
								            }
						    	            break;
						    	    
						    	   case 4: 
				    	            {
				    	            	   String Col1=RowObj.getString("key0");
				    	            	   System.out.print(Col1+" ");
				    	            	   Label label=new Label(0,r,Col1);
		        	    	               String Col2=RowObj.getString("key1");
		        	    	               System.out.print(Col2+" ");
		        	    	               Label label2=new Label(1,r,Col2);
		        	    	               String Col3=RowObj.getString("key2");
		        	    	               System.out.print(Col3+" ");
		        	    	               Label label3=new Label(2,r,Col3);
		        	    	               String Col4=RowObj.getString("key3");
		        	    	               Label label4=new Label(3,r,Col4);
		        	    	               System.out.println(Col4+" ");
		        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3); sheet.addCell(label4); 	  	  
						            }
				    	            break;  
				    	            
						    	   case 5: 
				    	            {
				    	            	   String Col1=RowObj.getString("key0");
		        	    	               Label label=new Label(0,r,Col1);
		        	    	               String Col2=RowObj.getString("key1");
		        	    	               Label label2=new Label(1,r,Col2);
		        	    	               String Col3=RowObj.getString("key2");
		        	    	               Label label3=new Label(2,r,Col3);
		        	    	               String Col4=RowObj.getString("key3");
		        	    	               Label label4=new Label(3,r,Col4);
		        	    	               String Col5=RowObj.getString("key4");
		        	    	               Label label5=new Label(4,r,Col5);
		        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3); 
		        	    	               sheet.addCell(label4); sheet.addCell(label5); 	 	  	  
						            }
				    	            break;   
				    	            
						    	   case 6: 
				    	            {
				    	            	   String Col1=RowObj.getString("key0");
		        	    	               Label label=new Label(0,r,Col1);
		        	    	               String Col2=RowObj.getString("key1");
		        	    	               Label label2=new Label(1,r,Col2);
		        	    	               String Col3=RowObj.getString("key2");
		        	    	               Label label3=new Label(2,r,Col3);
		        	    	               String Col4=RowObj.getString("key3");
		        	    	               Label label4=new Label(3,r,Col4);
		        	    	               String Col5=RowObj.getString("key4");
		        	    	               Label label5=new Label(4,r,Col5);
		        	    	               String Col6=RowObj.getString("key5");
		        	    	               Label label6=new Label(5,r,Col6);
		        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3); 
		        	    	               sheet.addCell(label4); sheet.addCell(label5);sheet.addCell(label6); 	 	  	  
						            }
				    	            break;   
				    	            
						    	   case 7: 
				    	            {
				    	            	   String Col1=RowObj.getString("key0");
				    	            	   System.out.print(Col1+" ");
				    	            	   Label label=new Label(0,r,Col1);		        	    	                
		        	    	               String Col2=RowObj.getString("key1");
		        	    	               System.out.print(Col2+" ");
		        	    	               Label label2=new Label(1,r,Col2);		        	    	               
		        	    	               String Col3=RowObj.getString("key2");
		        	    	               System.out.print(Col3+" ");
		        	    	               Label label3=new Label(2,r,Col3);
		        	    	               String Col4=RowObj.getString("key3");
		        	    	               System.out.print(Col4+" ");
		        	    	               Label label4=new Label(3,r,Col4);
		        	    	               String Col5=RowObj.getString("key4");
		        	    	               System.out.print(Col5+" ");;
		        	    	               Label label5=new Label(4,r,Col5);
		        	    	               String Col6=RowObj.getString("key5");
		        	    	               System.out.print(Col6+" ");
		        	    	               Label label6=new Label(5,r,Col6);
		        	    	               String Col7=RowObj.getString("key6");
		        	    	               System.out.println(Col7);
		        	    	               Label label7=new Label(6,r,Col7);
		        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3);sheet.addCell(label4); 
		        	    	               sheet.addCell(label5);sheet.addCell(label6); sheet.addCell(label7); 	 	  	  
						            }
				    	            break;   
				    	            
						    	   case 8: 
				    	            {
				    	            	   String Col1=RowObj.getString("key0");
		        	    	               Label label=new Label(0,r,Col1);
		        	    	               String Col2=RowObj.getString("key1");
		        	    	               Label label2=new Label(1,r,Col2);
		        	    	               String Col3=RowObj.getString("key2");
		        	    	               Label label3=new Label(2,r,Col3);
		        	    	               String Col4=RowObj.getString("key3");
		        	    	               Label label4=new Label(3,r,Col4);
		        	    	               String Col5=RowObj.getString("key4");
		        	    	               Label label5=new Label(4,r,Col5);
		        	    	               String Col6=RowObj.getString("key5");
		        	    	               Label label6=new Label(5,r,Col6);
		        	    	               String Col7=RowObj.getString("key6");
		        	    	               Label label7=new Label(6,r,Col7);
		        	    	               String Col8=RowObj.getString("key7");
		        	    	               Label label8=new Label(7,r,Col8);
		        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3);sheet.addCell(label4); 
		        	    	               sheet.addCell(label5);sheet.addCell(label6); sheet.addCell(label7);sheet.addCell(label8); 	 	  	  
						            }
				    	            break;   
				    	            
						    	   case 9: 
				    	            {
				    	            	   String Col1=RowObj.getString("key0");
		        	    	               Label label=new Label(0,r,Col1);
		        	    	               String Col2=RowObj.getString("key1");
		        	    	               Label label2=new Label(1,r,Col2);
		        	    	               String Col3=RowObj.getString("key2");
		        	    	               Label label3=new Label(2,r,Col3);
		        	    	               String Col4=RowObj.getString("key3");
		        	    	               Label label4=new Label(3,r,Col4);
		        	    	               String Col5=RowObj.getString("key4");
		        	    	               Label label5=new Label(4,r,Col5);
		        	    	               String Col6=RowObj.getString("key5");
		        	    	               Label label6=new Label(5,r,Col6);
		        	    	               String Col7=RowObj.getString("key6");
		        	    	               Label label7=new Label(6,r,Col7);
		        	    	               String Col8=RowObj.getString("key7");
		        	    	               Label label8=new Label(7,r,Col8);
		        	    	               String Col9=RowObj.getString("key8");
		        	    	               Label label9=new Label(8,r,Col9);
		        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3);sheet.addCell(label4);sheet.addCell(label5); 
		        	    	               sheet.addCell(label6); sheet.addCell(label7);sheet.addCell(label8);sheet.addCell(label9); 	 	  	  
						            }
				    	            break;   
				    	            
						    	   case 10: 
				    	            {
				    	            	   String Col1=RowObj.getString("key0");
		        	    	               Label label=new Label(0,r,Col1);
		        	    	               String Col2=RowObj.getString("key1");
		        	    	               Label label2=new Label(1,r,Col2);
		        	    	               String Col3=RowObj.getString("key2");
		        	    	               Label label3=new Label(2,r,Col3);
		        	    	               String Col4=RowObj.getString("key3");
		        	    	               Label label4=new Label(3,r,Col4);
		        	    	               String Col5=RowObj.getString("key4");
		        	    	               Label label5=new Label(4,r,Col5);
		        	    	               String Col6=RowObj.getString("key5");
		        	    	               Label label6=new Label(5,r,Col6);
		        	    	               String Col7=RowObj.getString("key6");
		        	    	               Label label7=new Label(6,r,Col7);
		        	    	               String Col8=RowObj.getString("key7");
		        	    	               Label label8=new Label(7,r,Col8);
		        	    	               String Col9=RowObj.getString("key8");
		        	    	               Label label9=new Label(8,r,Col9);
		        	    	               String Col10=RowObj.getString("key9");
		        	    	               Label label10=new Label(9,r,Col10);
		        	    	               sheet.addCell(label);sheet.addCell(label2);sheet.addCell(label3);sheet.addCell(label4);sheet.addCell(label5); 
		        	    	               sheet.addCell(label6); sheet.addCell(label7);sheet.addCell(label8);sheet.addCell(label9);sheet.addCell(label10); 	 	  	  
						            }
				    	            break; 				  
				     	 }							 
					}
					book.write(); 
					book.close();
					System.out.println("计数："+count);
		}
} 