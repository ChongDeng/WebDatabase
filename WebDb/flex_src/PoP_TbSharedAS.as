// ActionScript file
    import com.adobe.serialization.json.JSON;
    
    import flash.events.ContextMenuEvent;
    import flash.ui.ContextMenu;
    import flash.ui.ContextMenuItem;
    
    import mx.collections.ArrayCollection;
    import mx.containers.HBox;
    import mx.containers.VBox;
    import mx.controls.Alert;
    import mx.controls.DataGrid;
    import mx.controls.dataGridClasses.DataGridColumn;
    import mx.events.CollectionEvent;
    import mx.events.CollectionEventKind;
    import mx.events.DataGridEvent;
    import mx.events.ListEvent;
    import mx.managers.PopUpManager;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    import net.diding.manager.EditDataGridManager;
    import net.diding.window.InsertDGColumnWindow;
    import net.diding.window.InsertDGRowWindow;
	    
	private var MyDataDG:DataGrid;
	private var mainVBox:VBox;
	private var editDgManager:EditDataGridManager;
	private var myContextMenu:ContextMenu=new ContextMenu();
	private var arDatas:ArrayCollection=new ArrayCollection();
	private var info:ArrayCollection=new ArrayCollection();
	
	var fCell:Array;
	
	//CurNode存储M由MultiDB_TO_XML传来的当前节点信息  她将将传递给子窗口
    public var CurNode:String=new String;
    
    public var FriendList:ArrayCollection;

    private var ColArray:ArrayCollection=new ArrayCollection();
    private var arr:Array=new Array;
    private var ColNum:int;
    var UpdateStr:String=new String;
    
    //用于存储共享该文件的所有好友的详细情况
    private var SharedInfoArr:ArrayCollection;
    //判断是否已经点击过了子面板2，若已经点击过了，就不用再次加载列表了
    private var HasClicked:Boolean=false;
    
    //用于增加行时，为新的行添加ID
    private var LargestID:int;
  
    //用于保存从后台调出数据表时的所有行中的最大记录
    private var LargestIDStore:int=0;
   
   //   var OperateStr:String ="update";
      
    //用来标记增加一行后，是否马上在这个新增的行上添加数据
    var MarkStr:String =null;
     
    //用来标记第一行的ID
    var FirstID:String;
     
    //用来标记所选的行的行号
    var NumOfRow:int; 
     
    //当表中只有一行时，如果删除了这一行，则标志量OneDeleteFlag将赋值为1
    var OneDeleteFlag:int=0; 

   //获取被选中的表格的行的信息
    private var RowObject:Object=new Object;
    
     //用来判断新建的数据表的第一行有没有数据
    private var FirstRow:Object=new Object;
    private var NotNewTable:Boolean;
    
     private var AddFlag:String="false";
    
    //保存表格中有更新的数据的行
    private var dataToUpdate:ArrayCollection=new ArrayCollection;

     import flash.ui.ContextMenu;
	 import flash.ui.ContextMenuItem;
	 import flash.events.ContextMenuEvent;
     private var menu1:ContextMenuItem;
     private var menu2:ContextMenuItem;
     private var menu3:ContextMenuItem;
     
     //好友的用户名
     private var FName:String;
     //该好友的编辑权限
     private var Editable:String;

     private function changeFunc():void
     {		       
        if( HasClicked==false&&myTab.selectedIndex==1)
		   {
		   	   SharedInfo.getSharedInfo(CurNode); 
		   	   HasClicked=true;
		   	   menu1= new ContextMenuItem("修改用户权限");
		       menu2 = new ContextMenuItem("增加共享用户");
		       menu3 = new ContextMenuItem("删除该共享用户");
		       menu1.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,modify);
		       menu2.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,add);
		       menu3.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,del);
		       var MyMenu:ContextMenu = new ContextMenu();
		       MyMenu.hideBuiltInItems();
		       MyMenu.customItems.push(menu1);
		       MyMenu.customItems.push(menu2);  
		       MyMenu.customItems.push(menu3);
		       SharedTree.contextMenu = MyMenu;
		   }
		 else
		   {
		 	
		   }   
	 }
	 
	 //单击事件
       private function quzhi(evt:ListEvent):void
       { 
	           FName=evt.target.selectedItem.label;
	           Editable=evt.target.selectedItem.key1;
        }

	 
	   //修改用户的共享权限      
     private function modify(evt:ContextMenuEvent):void 
     {
	      if(Editable=="true")
	          Editable="false";
	      else
	          Editable="true"; 
	              
	      modifyRPC.modifyPri(UserName.text,FName,CurNode,Editable); 
     }  
      //成功更改用户权限
      public function Sucmodify(event:ResultEvent):void
      {
      	  mx.controls.Alert.show("已成功修改！");
      	  SharedInfo.getSharedInfo(CurNode); 
      } 
     
        //增加新的用户       
     private function add(evt:ContextMenuEvent):void 
     {	   
	       var position:int=CurNode.lastIndexOf("$");  
           var TbName:String=CurNode.substring(position+1,CurNode.length);
	       
	       var shareFile:FileShare_Wind = new FileShare_Wind();
		   shareFile.TbNameStr=TbName;						  
		   shareFile.UserDbTbName=CurNode;
		   shareFile.FriendArray=FriendList;
		   shareFile.addEventListener("refresh",RefreshFunc);  
		   PopUpManager.addPopUp(shareFile,this,true);     
		   PopUpManager.centerPopUp(shareFile);			
     }
     
     
     
     //成功赋给某个好友共享权限后，刷新列表
      private function RefreshFunc(event:Event):void
	  {
		  mx.controls.Alert.show("已成功添加！");
		  SharedInfo.getSharedInfo(CurNode); 
	  } 
     
     //删除该用户      
     private function del(evt:ContextMenuEvent):void 
     {	 
	      delRPC.deletePri(UserName.text,FName,CurNode);	      
     }
     
     //成功更改用户权限
      public function SucDelete(event:ResultEvent):void
      {
      	  mx.controls.Alert.show("已成功删除！");
      	  SharedInfo.getSharedInfo(CurNode); 
      } 
     
	
	 private function SucSharedFriend(event:ResultEvent):void
     {
	     SharedInfoArr=new ArrayCollection;
	     SharedInfoArr=ArrayCollection(event.result);				
		 SharedTree.dataProvider=SharedInfoArr;	
     }
	
     private function mylabelfunc(item:Object):String
     {
     	 var str:String;
     	 if(item.key1=="true")
     	   str="编辑";
     	 else
     	   str="查看"; 
     	 return item.label+"   (用户权限为："+str+")";
     }					  
						  
 	
	private function init():void
	{
 
	    ud.getAllUser(CurNode); 
	}

	 public function onResult(event:ResultEvent):void{   
             
             arDatas=ArrayCollection(event.result); 
                        
             //mx.controls.Alert.show("有多少行数据："+arDatas.length.toString());
             //mx.controls.Alert.show("最大ID为："+arDatas[arDatas.length-1].key1);
             
             //LargestIDStore用来保存最大记录
             for(var i:int=0;i<arDatas.length;i++)
                if(LargestIDStore<arDatas[i].key1)
                   LargestIDStore=arDatas[i].key1;
             
             
             //FirstID用来保存第一行ID
             FirstID=arDatas[0].key1;
            
             //LargestID用来增加新行后，加1，再传给EditDataGridManager
             LargestID=LargestIDStore;
             //info=ArrayCollection(event.result);  
             
             
             //trace(arDatas);
             //arr=users.toArray();
                //trace(arr[0]); 
                //Object us=users.getItemAt(1);
            
             mainVBox=new VBox();
		     mainVBox.percentHeight=100;
		     mainVBox.percentWidth=100;
		     TbCanvas.addChild(mainVBox);
             
             ud.getColName.send(CurNode); 

     }
         
       public function ColResult(event:ResultEvent):void{
      	
      	 ColArray=ArrayCollection(event.result); 
      	 
      	 arr=ColArray.toArray();
      	  trace("传过来的值 "+ColArray);
      	 //trace("很关键 "+arr[0]);
      	 ColNum=arr.length;
         
         FirstRow=arDatas[0];
         NotNewTable=NotEmpty(FirstRow,ColNum);
         //mx.controls.Alert.show(NewTable.toString());
         
          	// trace(arr);
	     //trace(ColArray.length);
      	 handle2( );
      	 editDgManager.ColNumBer=ColNum;
	    	//trace("哥 这一部好关键： "+arDatas);
	    	
	    	//trace(arr[0]);
	    	//trace(arr.length);
	    	  
	    	 
      }    
     
    public function handle2( ):void{
    	createDataGrid(mainVBox)
	
		var controlBox:HBox=new HBox();
		controlBox.percentWidth=100;
		mainVBox.addChild(controlBox);
		editDgManager.arData=arDatas;
		    MyDataDG.dataProvider=arDatas;
		    //监测数据源是否改变
		    arDatas.addEventListener(CollectionEvent.COLLECTION_CHANGE,dataChanged);
		  
		    MyDataDG.addEventListener(DataGridEvent.ITEM_EDIT_BEGINNING,onEditBeginning);
		    
	   		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_OUT, clearMenuHandler);
     		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_IN, setMenuHandler);
    		 	
	 }  
     public function onFault(event:FaultEvent):void {      
         mx.controls.Alert.show(event.fault.faultString, "Error");               
            } 
                      
	 public function SaveFault(event:FaultEvent):void 
	 {      
        dataToUpdate.removeAll(); 
        var ProcessStr:String=event.fault.faultString;
        var myArray:Array=ProcessStr.split(":");
        var str:String="java.sql.SQLException ";
       // mx.controls.Alert.show(myArray[0]);  
       // if(myArray[0]==str||myArray[0]=="com.mysql.jdbc.MysqlDataTruncation ")
            //  mx.controls.Alert.show("新数据类型和该列的类型不一致!","请重新设置");   
        //else
               mx.controls.Alert.show(event.fault.faultString, "Error");
                       
     }
	
	
	private function createDataGrid(MainVBox:VBox):void
	{
		MyDataDG=new DataGrid();
		
		editDgManager=new EditDataGridManager();
		editDgManager.taget=MyDataDG;

		MyDataDG.editable=true;
		MyDataDG.sortableColumns=false
		MyDataDG.percentWidth=100;
		MyDataDG.percentHeight=80;
		MyDataDG.setStyle("verticalAlign", "middle")
		
		trace("createDataGrid  ");
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		MyDataDG.columns=myGridcolumns();
		MainVBox.addChild(MyDataDG);
//		ShowList();
	}
	
	
	
	
	
	private function addDataGridMenuItems():void
	{
		myContextMenu.hideBuiltInItems();
		var MenuList:Array=["删除行", "删除列", "插入行", "插入列"];
		var separatorBeforeArray:Array=[true, false, true, false];
	
		for (var j:int=0; j < MenuList.length; j++){
			var item:ContextMenuItem=new ContextMenuItem(MenuList[j], separatorBeforeArray[j]);
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, MenuItemSelectHandler);
			myContextMenu.customItems.push(item);
		}
	}
	
	private function MenuItemSelectHandler(event:ContextMenuEvent):void{
		var CaptionString:String=event.target.caption;
		trace(CaptionString);
		switch (CaptionString)
		{
			case "删除行":
			editDgManager.DelRow();
			break;
			
			case "删除列":
			editDgManager.DelColumn();
			break;
			
			case "插入行":
            
            LargestID++;
            editDgManager.AddID=LargestID;
			var InsertRowWindow:InsertDGRowWindow=InsertDGRowWindow(PopUpManager.createPopUp(this,InsertDGRowWindow, false));
			InsertRowWindow.title="-->插入行设置";
			InsertRowWindow.getWinArg(editDgManager);
		
			break;
			
			case "插入列":
			var InsertColWindow:InsertDGColumnWindow=InsertDGColumnWindow(PopUpManager.createPopUp(this,InsertDGColumnWindow, false));
			InsertColWindow.title="-->插入列设置";
			InsertColWindow.getWinArg(editDgManager)
			arDatas[2].key4="friend";
		
			break;
	
		}
	}
	//此项数据可以动态创建

	private var ValueArray:Array=["key1","key2","key3","key4","key5","key6","key7","key8","key9","key10"];
	private function myGridcolumns():Array
	{
		var cols:Array=MyDataDG.columns;
	
    	for (var m:int=1; m < ColNum; m++)
		 {
			var ShowID_DC:DataGridColumn=new DataGridColumn();
			ShowID_DC.editable=true;
			ShowID_DC.draggable=false;
			ShowID_DC.headerText=arr[m];
			ShowID_DC.dataField=ValueArray[m];
			//ShowID_DC.headerRenderer=new ClassFactory(DgHeaderTextRender);
				trace("列名 "+ShowID_DC.headerText);
			ShowID_DC.setStyle("textAlign", "center");
			cols.push(ShowID_DC)
		}
		trace("表格的列创建好了 ");
	 return cols;
	}
	

	
	private function setMenuHandler(event:DataGridEvent):void
	{
		editDgManager.SelColumnIndex=event.columnIndex;
		MyDataDG.contextMenu=myContextMenu;
		addDataGridMenuItems();
		
	}
	
	private function clearMenuHandler(event:DataGridEvent):void
	{
		MyDataDG.contextMenu=null;
	}

	 private function cancel():void
     {
             	  PopUpManager.removePopUp(this);
      }
	
	 private function onEditBeginning(event:DataGridEvent):void
		{
			   RowObject = MyDataDG.selectedItem; 
			   MarkStr=RowObject.key1;
			   
			   fCell=[event.rowIndex,event.columnIndex];
			   
			   //获得所选的行号
			   NumOfRow=GetNumOfRow(MarkStr);
			  
		}	

	
	 //	获得所选的行号
	 private function GetNumOfRow(Key1:String):int
	 {
	 	 var result:int;
	 	 for(var i:int=0; i<arDatas.length; i++)
    	      if(Key1==arDatas[i].key1)
    	          result=i;
    	 return result;  
    	         
	 }	
		
    private function dataChanged(event:CollectionEvent):void
	{
       
       if(event.kind == CollectionEventKind.UPDATE)
        {
           
             var OperateStr:String =null;
             var MarkInt:int=int(MarkStr);
 
              if(MarkInt<=LargestIDStore&&LargestIDStore!=1)
             {
             	   //mx.controls.Alert.show("新增后编辑了旧记录或者没有任何新增操作只是跟新"); 
             	   OperateStr="update";  
                   procces(ColNum,OperateStr); 
             }           
        }
        
        else if(event.kind == CollectionEventKind.REMOVE)
        { 
        	  if(MarkInt<=LargestIDStore)
        	   {
                  var DeleteStr:String ="delete";
                  procces(ColNum,DeleteStr);
                  if(arDatas.length!=0)
                  {
                  	 RowOperation(FirstID,NumOfRow); 
                     FirstID=arDatas[0].key1; 
                  } 
                  else
                  {
                 	 OneDeleteFlag=1;
                  }
                
               }
        }
        else 
        {
        	
        }
  
    }
    
    
    private function RowOperation(str:String,RowRank:int):void
    {
    	  if(RowObject.key1==str)
    	      RowObject=arDatas[0];
    	  else
    	  {
    	  	RowObject=arDatas[RowRank-1];
    	  	NumOfRow--;  	 
    	  }
    }
   private function procces(NumCol:int,OperateString:String):void
   {
   	        switch(NumCol)
   	          {
   	          	case 1:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;
   	          	case 2:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;
   	            case 3:
   	          	        
   	          	             var updatedObj:Object = new Object;
   	          	             updatedObj.OperateKey=OperateString;
	   	          	    	          
   	          	             updatedObj.key3= RowObject.key3;
   	          	             updatedObj.key2= RowObject.key2;
   	          	             updatedObj.key1= RowObject.key1;
   	          	             dataToUpdate.addItem(updatedObj);
   	          	        
   	          	        break;
   	            case 4:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.key3= RowObject.key3;
   	          	        updatedObj.key4= RowObject.key4;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;	        	       
   	            case 5:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.key3= RowObject.key3;
   	          	        updatedObj.key4= RowObject.key4;
   	          	        updatedObj.key5= RowObject.key5;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;
   	            case 6:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.key3= RowObject.key3;
   	          	        updatedObj.key4= RowObject.key4;
   	          	        updatedObj.key5= RowObject.key5;
   	          	        updatedObj.key6= RowObject.key6;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;  
   	          	 case 7:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.key3= RowObject.key3;
   	          	        updatedObj.key4= RowObject.key4;
   	          	        updatedObj.key5= RowObject.key5;
   	          	        updatedObj.key6= RowObject.key6;
   	          	        updatedObj.key7= RowObject.key7;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;  
   	          	 case 8:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.key3= RowObject.key3;
   	          	        updatedObj.key4= RowObject.key4;
   	          	        updatedObj.key5= RowObject.key5;
   	          	        updatedObj.key6= RowObject.key6;
   	          	        updatedObj.key7= RowObject.key7;
   	          	        updatedObj.key8= RowObject.key8;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;      
   	          	  case 9:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.key3= RowObject.key3;
   	          	        updatedObj.key4= RowObject.key4;
   	          	        updatedObj.key5= RowObject.key5;
   	          	        updatedObj.key6= RowObject.key6;
   	          	        updatedObj.key7= RowObject.key7;
   	          	        updatedObj.key8= RowObject.key8;
   	          	        updatedObj.key9= RowObject.key8;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;      
   	          	  case 10:
   	          	        var updatedObj:Object = new Object;
   	          	        updatedObj.key1= RowObject.key1;
   	          	        updatedObj.key2= RowObject.key2;
   	          	        updatedObj.key3= RowObject.key3;
   	          	        updatedObj.key4= RowObject.key4;
   	          	        updatedObj.key5= RowObject.key5;
   	          	        updatedObj.key6= RowObject.key6;
   	          	        updatedObj.key7= RowObject.key7;
   	          	        updatedObj.key8= RowObject.key8;
   	          	        updatedObj.key9= RowObject.key9;
   	          	        updatedObj.key10= RowObject.key10;
   	          	        updatedObj.OperateKey=OperateString;
   	          	        dataToUpdate.addItem(updatedObj);
   	          	        break;   	   	   
   	           }
    
   } 
   
     //将增加的行的数据加入到dataToUpdate
     private function AddProc( ):void
     {
     	 LargestIDStore++;
    	      for(var i:int=LargestIDStore; i<=LargestID; i++)
    	        for(var j:int=0; j<arDatas.length; j++)
    	            if(i==arDatas[j].key1)
    	               {
    	               	  RowObject=arDatas[j];
    	               	  if(NotEmpty(RowObject,ColNum))
    	               	    {
    	               	       var AddStr:String ="add";
                               procces(ColNum,AddStr);
    	               	    }
    	              }
    	 LargestIDStore--;
     }
      
     //判断新增行后，是否没有添加数据
     private function NotEmpty(Obj:Object,NumBer:int):Boolean
     {
     	 var Result:Boolean=true;
     	 switch(NumBer)
   	          {
   	          	case 1:
   	          	            break;
   	          	case 2:
   	          	            if(Obj.key2==" ")
   	          	               Result=false;
   	          	            break;
   	            case 3:
   	          	            if(Obj.key2==" "&&Obj.key3==" ")
   	          	               Result=false;
   	          	            break;
   	            case 4:
   	          	            if(Obj.key2==" "&&Obj.key3==" "&& Obj.key4==" ")
   	          	               Result=false;
   	          	            break;
   	          	                  	       
   	            case 5:
   	          	           if(Obj.key2==" "&&Obj.key3==" "&& Obj.key4==" "&& Obj.key5==" ")
   	          	               Result=false;
   	          	           break;
   	            case 6:
   	          	           if(Obj.key2==" "&&Obj.key3==" "&& Obj.key4==" "&& Obj.key5==" "&& Obj.key6==" ")
   	          	               Result=false;
   	          	           break;
   	          	case 7:
   	          	           if(Obj.key2==" "&&Obj.key3==" "&& Obj.key4==" "&& Obj.key5==" "&& Obj.key6==" "&& Obj.key7==" ")
   	          	               Result=false;
   	          	           break;
   	            case 8:
   	          	           if(Obj.key2==" "&&Obj.key3==" "&& Obj.key4==" "&& Obj.key5==" "&& Obj.key6==" "&& Obj.key7==" "&& Obj.key8==" ")
   	          	               Result=false;
   	          	           break;
   	          	case 9:
   	          	           if(Obj.key2==" "&&Obj.key3==" "&& Obj.key4==" "&& Obj.key5==" "&& Obj.key6==" "&& Obj.key7==" "&& Obj.key8==" "&& Obj.key9==" ")
   	          	               Result=false;
   	          	           break;
   	          	case 10:
   	          	           if(Obj.key2==" "&&Obj.key3==" "&& Obj.key4==" "&& Obj.key5==" "&& Obj.key6==" "&& Obj.key7==" "&& Obj.key8==" "&& Obj.key9==" "&& Obj.key10==" ")
   	          	               Result=false;
   	          	           break;
   	           }
        return Result;
    
     }
     
  
    private function SaveSend( ):void
    {
    	       if(!OneDeleteFlag)
    	         RowObject=arDatas[0]; 
    	     
    	       //表只有一行的情况下，判断第一行是否有数据，没有就是add，有就是update或者delete
    	       if((LargestIDStore==1)&&!NotNewTable&&NotEmpty(RowObject,ColNum))
    	         {
    	           // RowObject=arDatas[0]; 
    	            var AddString:String ="add";
                    procces(ColNum,AddString); 
    	         }
    	       else if( LargestIDStore==1&&NotNewTable&&NotEmpty(FirstRow,ColNum)&&LargestIDStore==LargestID&&!OneDeleteFlag)
    	        {
    	        	var UpString:String ="update";
                    procces(ColNum,UpString); 
    	        }
    	 
    	        
    	        //将增加的行的数据加入到dataToUpdate 
    	       if(LargestID>LargestIDStore)
    	         AddProc( );
    	        
    	       for (var m:int=1; m < ColNum; m++)
		        {     
		        	var ColName:Object = new Object;
		        	ColName.key1=arr[m];
		        	dataToUpdate.addItem(ColName);
		        }
    	       
    	       var NumStore:Object = new Object;
   	           NumStore.key1=ColNum;
   	           dataToUpdate.addItem(NumStore);
   	           
   	           //添加表名
   	           var TableNameObj:Object = new Object;
   	           TableNameObj.key1=CurNode;
   	           dataToUpdate.addItem(TableNameObj);
   	           
   	           UpdateStr= JSON.encode(dataToUpdate.toArray());
   	           // mx.controls.Alert.show(dataToUpdate.toString()); 
   	            mx.controls.Alert.show(UpdateStr);  
   	                 
      	     SendUpDate.sendJsonArray(UpdateStr);
    	
    }
    
     public function UpDateSuccess(event:ResultEvent):void
     {
     	    init();
     	    mx.controls.Alert.show("更新成功");
          	dataToUpdate.removeAll();
          	
     }   
		
