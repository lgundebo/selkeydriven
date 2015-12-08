package com.drc.wtf.core;

import com.drc.wtf.test_management.Action;

public class CaptureData 
{
	public int iCaptureWebTextIntoInteger1;
	public int iCaptureWebTextIntoInteger2;
	public int iCaptureWebTextIntoInteger3;
	public int iCaptureWebTextIntoInteger4;
	
	
	
	public String sCaptureWebTextIntoInteger1;
	public String sCaptureWebTextIntoInteger2;
	public String sCaptureWebTextIntoInteger3;
	public String sCaptureWebTextIntoInteger4;
	
	 public int    iCountTableRowCount1;
	    public int    iCountTableRowCount2;
	    public int    iCountTableRowCount3;
	    public int    iCountTableRowCount4;

	
	public String sCaptureWebText;
	
	
	public void captureData(Action action, String capturedWebText)
	{
		sCaptureWebText = capturedWebText;
		int iCaptureWebText = Integer.parseInt(capturedWebText);
		
		
		
		if (action.fieldValue.contains("CaptureWebTextIntoInteger1")) {
			iCaptureWebTextIntoInteger1 =  iCaptureWebText; 
			sCaptureWebTextIntoInteger1 = sCaptureWebText;
		}
		else if (action.fieldValue.contains("CaptureWebTextIntoInteger2")) {iCaptureWebTextIntoInteger2 =  iCaptureWebText; 
		sCaptureWebTextIntoInteger2 = sCaptureWebText;
		}
		else if (action.fieldValue.contains("CaptureWebTextIntoInteger3")) {iCaptureWebTextIntoInteger3 =  iCaptureWebText; 
		sCaptureWebTextIntoInteger3 = sCaptureWebText;
		}
		else if (action.fieldValue.contains("CaptureWebTextIntoInteger4")) {iCaptureWebTextIntoInteger4 =  iCaptureWebText; 
		sCaptureWebTextIntoInteger4 = sCaptureWebText;
		}			
		
	}
	
	
	public Action updateActionCapture(Action action)
	{
		if (action.fieldValue.contains("CaptureWebTextIntoInteger1")) { 
			action.fieldValue =  sCaptureWebTextIntoInteger1; }
		else if (action.fieldValue.contains("CaptureWebTextIntoInteger2")) { 
			action.fieldValue =  sCaptureWebTextIntoInteger2; }
		else if (action.fieldValue.contains("CaptureWebTextIntoInteger3")) { 
			action.fieldValue =  sCaptureWebTextIntoInteger3; }
		else if (action.fieldValue.contains("CaptureWebTextIntoInteger4")) { 
			action.fieldValue =  sCaptureWebTextIntoInteger4; }
		
			
		return action;
	}
	
	 @Override
	    public Object clone() throws CloneNotSupportedException
	    {
	        return super.clone();
	    }
	
}
