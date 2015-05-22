package net.diding.dgRender
{
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	import mx.containers.HBox;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.DataGridEvent;
	import mx.events.FlexEvent;

	public class DgHeaderTextRender extends HBox
	{
		private var headerLabel:Label;
		private var textInput:TextInput;
		private var dgColumn:DataGridColumn;

		public function DgHeaderTextRender()
		{
			super();
			this.setStyle("horizontalAlign","center");
			this.doubleClickEnabled=true;
			this.addEventListener(MouseEvent.DOUBLE_CLICK, editHeaderTxt);
			this.addEventListener(FlexEvent.CREATION_COMPLETE,init);
			this.addEventListener(MouseEvent.ROLL_OVER,setColor);
			this.addEventListener(MouseEvent.ROLL_OUT,clearColor);
		}
		private function setColor(event:MouseEvent):void{
			//trace(this.owner)
			//var d:DataGrid=this.owner as DataGrid;
			this.setFocus();
			this.setStyle("backgroundColor","#CCCCCC")
		}
		
		private function clearColor(event:MouseEvent):void{
			this.clearStyle("backgroundColor")	
		}
		
		override public function get data():Object
		{
			return dgColumn;
		}


		override public function set data(value:Object):void
		{
			dgColumn=value as DataGridColumn;
			dgColumn.sortable=false;
			dispatchEvent(new FlexEvent(FlexEvent.DATA_CHANGE));
		}


		private function init(event:FlexEvent):void
		{
			headerLabel=new Label();
			headerLabel.percentWidth=100;
			this.addChild(headerLabel);
			headerLabel.text=dgColumn.headerText;
			
		}
		private function changeToInput():void{
			this.removeAllChildren();
			
			textInput=new TextInput();
			textInput.percentWidth=100
			this.addChild(textInput);
			textInput.text=dgColumn.headerText;

			textInput.setFocus(); 
			textInput.addEventListener(FocusEvent.FOCUS_OUT,setHeaderTxt)
		}
		private function changeToView():void{
			dgColumn.headerText=textInput.text;
			this.removeAllChildren();
			headerLabel=new Label();
			headerLabel.percentWidth=100;
			this.addChild(headerLabel);
			headerLabel.text=dgColumn.headerText;
		}

		
		private function editHeaderTxt(event:MouseEvent):void
		{
			changeToInput()
		}

		private function setHeaderTxt(event:FocusEvent):void
		{
			changeToView()
		}
		//------------------------------------------
	}
}