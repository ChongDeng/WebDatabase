/*************************************************
 *@四川资中二中 帝鼎 2009年3月6日
 * ************************************************/
package net.diding.manager
{
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ClassFactory;
	
	import net.diding.dgRender.DgHeaderTextRender;
 
	public class EditDataGridManager extends EventDispatcher
	{
		private var MyDataDG:DataGrid;
		private var arDatas:ArrayCollection;
		private var nowColumnIndex:Number;
		private var Col_Num:int;
		private var NewID:int;
		

		public function EditDataGridManager(target:IEventDispatcher=null)
		{
			super(target);
		}
		/***********控制目标对象DataGrid**********/
		public function set taget(arg:DataGrid):void
		{
			MyDataDG=arg;
		}
		/***********ArrayCollection结构**********/
		public function set arData(arg:ArrayCollection):void{
			arDatas=arg
		}
		
		/***********传入的selectedColumnIndex**********/
		public function set SelColumnIndex(arg:Number):void{
			nowColumnIndex=arg
		}
		
		public function set ColNumBer(arg:int):void{
		
			Col_Num=arg;
			//mx.controls.Alert.show(Col_Num.toString());
		}
		
		
		
		public function set AddID(arg:int):void{
		
			NewID=arg;
			//mx.controls.Alert.show(NewID.toString());
		}

		/*************************************************
		 * 添加列
		 * ***********************************************/
		public function AddColumn(bntLablel:String):void
		{
			var dgColumns:Array=MyDataDG.columns;
			
			var aColumn:DataGridColumn=new DataGridColumn();
			aColumn.editable=true;
			aColumn.draggable=false;
			aColumn.dataField=String(dgColumns.length+1);
			aColumn.headerRenderer=new ClassFactory(DgHeaderTextRender);
			aColumn.headerText="新增列";
			aColumn.setStyle("textAlign", "center");

			if (bntLablel == "AddColumn_B")
			{
				dgColumns.splice(nowColumnIndex, 0, aColumn);
				
			}
			else
			{
				dgColumns.splice(nowColumnIndex + 1, 0, aColumn);
			}
			MyDataDG.columns=dgColumns;
			arDatas.refresh();
			MyDataDG.dataProvider=arDatas;
			
		}
		
		/*************************************************
		 * 删除列
		 * ***********************************************/
		public function DelColumn():void
		{
			var dgColumns:Array=MyDataDG.columns;
			dgColumns.splice(nowColumnIndex,1);
			MyDataDG.columns=dgColumns;
		}
		/*************************************************
		 * 添加行
		 * ***********************************************/
		public function AddRow(bntLablel:String):void
		{
			var nowSelObj:Object=MyDataDG.selectedItem;
			var idx:int=arDatas.getItemIndex(nowSelObj)
			var obj:Object=new Object();
            var NewIDstr:String=String(NewID);
            switch(Col_Num)
   	          {
   	          	case 1:
   	          	        obj.key1=NewIDstr;
                        break;
   	          	case 2:
   	          	        obj.key1=NewIDstr;
                        obj.key2=" ";
                        break;
   	            case 3:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";   	          	        
   	          	         break;
   	            case 4:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";  
                         obj.key4=" ";
                         break;	        	       
   	            case 5:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";  
                         obj.key4=" ";
                         obj.key5=" ";
   	          	        break;
   	            case 6:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";  
                         obj.key4=" ";
                         obj.key5=" ";
                         obj.key6=" ";
   	          	        break; 
   	           case 7:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";  
                         obj.key4=" ";
                         obj.key5=" ";
                         obj.key6=" ";
                         obj.key7=" ";
   	          	        break; 
   	           case 8:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";  
                         obj.key4=" ";
                         obj.key5=" ";
                         obj.key6=" ";
                         obj.key7=" ";
                         obj.key8=" ";
   	          	        break; 
   	          case 9:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";  
                         obj.key4=" ";
                         obj.key5=" ";
                         obj.key6=" ";
                         obj.key7=" ";
                         obj.key8=" ";
                         obj.key9=" ";
                        break; 
   	         case 10:
   	          	         obj.key1=NewIDstr;
                         obj.key2=" ";
                         obj.key3=" ";  
                         obj.key4=" ";
                         obj.key5=" ";
                         obj.key6=" ";
                         obj.key7=" ";
                         obj.key8=" ";
                         obj.key9=" ";
                         obj.key10=" ";
   	          	        break; 
   	          	         	   
   	        }
            
            
		
			
			if (bntLablel == "AddRow_B")
			{
				arDatas.addItemAt(obj, idx)
			}
			else
			{
				arDatas.addItemAt(obj, idx + 1)
			}



			MyDataDG.dataProvider=null;
		    arDatas.refresh()
			MyDataDG.dataProvider=arDatas;
		}
		/*************************************************
		 * 删除行
		 * ***********************************************/
		public function DelRow():void
		{
			var nowSelObj:Object=MyDataDG.selectedItem;
			var idx:int=arDatas.getItemIndex(nowSelObj)
			arDatas.removeItemAt(idx);
			for (var j:int=0; j < arDatas.length; j++)
			{
				var show_num:Number=Number(j + 1);
				if (show_num < 10)
				{
					arDatas[j].ID="No.0" + show_num;
				}
				else
				{
					arDatas[j].ID="No." + show_num;
				}
			}
			MyDataDG.dataProvider=null;
			arDatas.refresh()
			MyDataDG.dataProvider=arDatas;
		}
		
		
		
	}
}