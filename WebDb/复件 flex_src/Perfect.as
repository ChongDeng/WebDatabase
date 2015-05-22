// ActionScript file
// ActionScript file

	// ActionScript file
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	
	import mx.collections.ArrayCollection;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ClassFactory;
	import mx.events.DataGridEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import net.diding.dgRender.DgHeaderTextRender;
	import net.diding.manager.EditDataGridManager;
	import net.diding.window.InsertDGColumnWindow;
	import net.diding.window.InsertDGRowWindow;
	
	
	
	private var MyDataDG:DataGrid;
	private var mainVBox:VBox;
	private var editDgManager:EditDataGridManager;
	private var myContextMenu:ContextMenu=new ContextMenu();
	private var arDatas:ArrayCollection=new ArrayCollection();
	private var info:ArrayCollection=new ArrayCollection();

    private var ColArray:ArrayCollection=new ArrayCollection();
    private var arr:Array=new Array;
    private var ColNum:int;
    
	private function init():void
	{
//		mainVBox=new VBox();
//		mainVBox.percentHeight=100;
//		mainVBox.percentWidth=100;
//		this.addChild(mainVBox);
	  
	    ud.getAllUser.send(); 
	}


    public function handle2( ):void{
    	createDataGrid(mainVBox)
	
		var controlBox:HBox=new HBox();
		controlBox.percentWidth=100;
		mainVBox.addChild(controlBox);
		editDgManager.arData=arDatas;
		    MyDataDG.dataProvider=arDatas;
	   		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_OUT, clearMenuHandler);
     		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_IN, setMenuHandler);
    		 	
	 }  
	
	 public function onResult(event:ResultEvent):void{   
             
             arDatas=ArrayCollection(event.result); 
             //info=ArrayCollection(event.result);  
             
             
             //trace(arDatas);
             //arr=users.toArray();
                //trace(arr[0]); 
                //Object us=users.getItemAt(1);
            
             mainVBox=new VBox();
		     mainVBox.percentHeight=100;
		     mainVBox.percentWidth=100;
		     this.addChild(mainVBox);
             
             ud.getColName.send(); 
             
            
            //使ArrayCollection变量arData设为表格的数据源  
//            editDgManager.arData=arDatas;
//		    MyDataDG.dataProvider=arDatas;
//	   		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_OUT, clearMenuHandler);
//     		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_IN, setMenuHandler);
//            trace("数据源绑定成功了");
            
             trace("妈的"+arDatas.getItemAt(0).key1);
             trace(arDatas.getItemAt(0).key2);
             trace(arDatas.getItemAt(0).key3);
           
     }
         
       public function ColResult(event:ResultEvent):void{
      	
      	 ColArray=ArrayCollection(event.result); 
      	 
      	 arr=ColArray.toArray();
      	  trace("传过来的值 "+ColArray);
      	 //trace("很关键 "+arr[0]);
      	 ColNum=arr.length;
      	// trace(arr);
	     //trace(ColArray.length);
      	 handle2( );
      	 
	    	//trace("哥 这一部好关键： "+arDatas);
	    	
	    	//trace(arr[0]);
	    	//trace(arr.length);
	    	  
	    	 
      }   
     public function onFault(event:FaultEvent):void {      
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
			var InsertRowWindow:InsertDGRowWindow=InsertDGRowWindow(PopUpManager.createPopUp(this,InsertDGRowWindow, false));
			InsertRowWindow.title="-->插入行设置";
			InsertRowWindow.getWinArg(editDgManager)
			break;
			
			case "插入列":
			var InsertColWindow:InsertDGColumnWindow=InsertDGColumnWindow(PopUpManager.createPopUp(this,InsertDGColumnWindow, false));
			InsertColWindow.title="-->插入列设置";
			InsertColWindow.getWinArg(editDgManager)
			break;
	
		}
	}
	//此项数据可以动态创建
//	private var HeadArray:Array=["Name", "Password","Sex"];
	private var ValueArray:Array=["key1","key2","key3","key4","key5","key6"];
	private function myGridcolumns():Array
	{
		var cols:Array=MyDataDG.columns;
	
    	for (var m:int=0; m < ColNum; m++)
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
	
//	private function ShowList():void
//	{
		
		   
		//	var obj:Object=new Object();
		//	obj.key1="Hello";
		//	obj.key2="Hello";
		//	obj.key3="Hello";
		  
		//	arDatas.addItem(obj);
		
//		editDgManager.arData=arDatas;
		
//		trace("绑定数据源");
//		MyDataDG.dataProvider=arDatas;
	
//		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_OUT, clearMenuHandler);
//		MyDataDG.addEventListener(DataGridEvent.ITEM_FOCUS_IN, setMenuHandler);
//	}
	
	
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

	
	
		
