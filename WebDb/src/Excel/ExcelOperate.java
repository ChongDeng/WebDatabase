package Excel;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

import jxl.*;
import org.rjb.User;
public class ExcelOperate {
	 User u;
	 Cell[] CellRow;
	String[] store=new String[30];

		
	 ArrayList<Object> userList = new ArrayList();
	
	 public  List SheetCont(String Path,String SheetNumber){
				
		try{        
			        //如果不这样做，上回的表中的数据就会在下回的表中出现
			        userList.clear(); 
			       
			        int Number=Integer.parseInt(SheetNumber);
			        System.out.println("号码为："+Number);
			        
				    Workbook book=Workbook.getWorkbook(new File(Path)); 
			        Sheet sheet=book.getSheet(Number);
			        int ColNum=sheet.getColumns();
			        int RowNum=sheet.getRows();
			        //Cell cell1=sheet.getCell(0,0); 
				    //result=cell1.getContents();
			        //result=Integer.toString(ColNum)+" "+Integer.toString(RowNum);
			        for(int r=0;r<RowNum;r++)
			        {
			        	 Cell[] CellRow=sheet.getRow(r);
			        	 System.out.println("一行的长度为："+CellRow.length);
			        	 for(int n=0;n<CellRow.length;n++)
			        	 { 
			        		 store[n]=CellRow[n].getContents();
			        		 //System.out.println("的元素为："+store[n]);
			        	 }
			        		
			        	 u=new User(); 
				         Handle(CellRow.length);
				         userList.add(u); 
     		        }
			        System.out.println("333");
			        userList.add(ColNum); 
			        
			        book.close();
			
		    }catch(Exception e)	{ 
			      System.out.println(e); 
			    }
		    return userList;   
	}
	
	public String SheetNum(String Path){
		String num=null;
		
		try{
			      Workbook book=Workbook.getWorkbook(new File(Path)); 
			      num=Integer.toString(book.getNumberOfSheets());
			     
			      System.out.println("表单的数目为："+num.toString());
			      book.close();
				  
			
			
		    }catch(Exception e)	{ 
			      System.out.println(e); 
			    }
		 return num;   
	}
	
	
	  public void Handle(int num)
	    {
		  System.out.println("111");
	
	    	switch(num)
	     	{
			          case 1:   u.setkey1(store[0]);
		        	            break;
			    	        
			    	   case 2: {
			    		           u.setkey1(store[0]);
			        	           u.setkey2(store[1]);
			        	           break;
			                    }
			    	            
			    	   case 3: 
			    	            {
			    	            	u.setkey1(store[0]);
					        	    u.setkey2(store[1]);
					        	    u.setkey3(store[2]);
					        	    break;
					            }
			    	            
			    	    
			    	   case 4: 
			           {
			        	    u.setkey1(store[0]);
			        	    u.setkey2(store[1]);
			        	    u.setkey3(store[2]);
			        	    u.setkey4(store[3]);
		    		        break;
			           }
			           
			    	   case 5: 
			           {
			        	    u.setkey1(store[0]);
			        	    u.setkey2(store[1]);
			        	    u.setkey3(store[2]);
			        	    u.setkey4(store[3]);
		    		        u.setkey5(store[4]);
		    		        break;
			           }
			           
			    	   case 6: 
			           {
			        	    u.setkey1(store[0]);
			        	    u.setkey2(store[1]);
			        	    u.setkey3(store[2]);
			        	    u.setkey4(store[3]);
		    		        u.setkey5(store[4]);
		    		        u.setkey6(store[5]);
		    		        break;
			           }
			           
			    	   case 7: 
			           {
			        	    u.setkey1(store[0]);
			        	    u.setkey2(store[1]);
			        	    u.setkey3(store[2]);
			        	    u.setkey4(store[3]);
		    		        u.setkey5(store[4]);
		    		        u.setkey6(store[5]);
		    		        u.setkey7(store[6]);
		    		        break;
			           }
			           
			    	   case 8: 
			           {
			        	    u.setkey1(store[0]);
			        	    u.setkey2(store[1]);
			        	    u.setkey3(store[2]);
			        	    u.setkey4(store[3]);
		    		        u.setkey5(store[4]);
		    		        u.setkey6(store[5]);
		    		        u.setkey7(store[6]);
		    		        u.setkey8(store[7]);
		    		        break;
			           }
			           
			    	   case 9: 
			           {
			        	    u.setkey1(store[0]);
			        	    u.setkey2(store[1]);
			        	    u.setkey3(store[2]);
			        	    u.setkey4(store[3]);
		    		        u.setkey5(store[4]);
		    		        u.setkey6(store[5]);
		    		        u.setkey7(store[6]);
		    		        u.setkey8(store[7]);
		    		        u.setkey9(store[8]);
		    		        break;
			           }
			           
			    	   case 10: 
			           {
			        	    u.setkey1(store[0]);
			        	    u.setkey2(store[1]);
			        	    u.setkey3(store[2]);
			        	    u.setkey4(store[3]);
		    		        u.setkey5(store[4]);
		    		        u.setkey6(store[5]);
		    		        u.setkey7(store[6]);
		    		        u.setkey8(store[7]);
		    		        u.setkey9(store[8]);
		    		        u.setkey10(store[9]);
		    		        break;
			           }
			           
	       }         
	    }
}
