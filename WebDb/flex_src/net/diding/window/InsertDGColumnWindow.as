
package net.diding.window
{
	import flash.events.MouseEvent;
	
	import mx.containers.HBox;
	import mx.containers.TitleWindow;
	import mx.containers.VBox;
	import mx.controls.RadioButton;
	import mx.controls.RadioButtonGroup;
	import mx.core.Application;
	import mx.events.CloseEvent;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
	
	import net.diding.manager.EditDataGridManager;

	public class InsertDGColumnWindow  extends TitleWindow
	{
		private var rbg:RadioButtonGroup;
		private var before_Rbn:RadioButton;
		private var end_Rbn:RadioButton;
		
		public function InsertDGColumnWindow ()
		{
			super();
		    this.width=250;
			this.height=180;
			this.showCloseButton=true;
			this.addEventListener(CloseEvent.CLOSE,removeOwnerwindow);
			this.addEventListener(FlexEvent.CREATION_COMPLETE,setViewPosition)	
			createUIFace()
		}
		 private function setViewPosition(event:FlexEvent):void{
			this.x=Application.application.width/2-250/2;
			this.y=Application.application.height/2-180/2
		}
		
		private function removeOwnerwindow(event:CloseEvent):void {
		    PopUpManager.removePopUp(this);
        }  
		private function createUIFace():void{
			rbg = new RadioButtonGroup();
			  
			var MainVBox:VBox=new VBox();
			MainVBox.setStyle("horizontalAlign","center")
			MainVBox.setStyle("paddingTop",20)
			MainVBox.setStyle("verticalGap",15)
			MainVBox.percentHeight=100;
			MainVBox.percentWidth=100;
			this.addChild(MainVBox)
			//------------------------------------------------
			var FirstHBox:HBox=new HBox();
			FirstHBox.setStyle("horizontalAlign","center")
			FirstHBox.setStyle("verticalAlign","middle")
			FirstHBox.percentWidth=100;
			MainVBox.addChild(FirstHBox)

			before_Rbn=new RadioButton();
			before_Rbn.group= rbg;
			before_Rbn.width=100;
			before_Rbn.label="当前列前面"
			FirstHBox.addChild(before_Rbn);
			before_Rbn.addEventListener(MouseEvent.CLICK,doSetBefore_Handler)
			//----------------------------------------------
			
			var SecondHBox:HBox=new HBox();
			SecondHBox.setStyle("horizontalAlign","center")
			SecondHBox.setStyle("verticalAlign","middle")
			SecondHBox.percentWidth=100;
			MainVBox.addChild(SecondHBox)

			end_Rbn=new RadioButton();
			end_Rbn.group= rbg;
			end_Rbn.width=100;
			end_Rbn.label="当前列后面"
			SecondHBox.addChild(end_Rbn);
			end_Rbn.addEventListener(MouseEvent.CLICK,doSetEnd_Handler)
			//----------------------------------------------
		}
		
		private function doSetBefore_Handler(event:MouseEvent):void{
              editor.AddColumn("AddColumn_B")
               PopUpManager.removePopUp(this);
		}
		private function doSetEnd_Handler(event:MouseEvent):void{
			  editor.AddColumn("AddColumn_E");
			   PopUpManager.removePopUp(this);
		}
		
		private var editor:EditDataGridManager;
		public function getWinArg(geditor:EditDataGridManager):void{
        	editor=geditor;
        }
	
	}
}