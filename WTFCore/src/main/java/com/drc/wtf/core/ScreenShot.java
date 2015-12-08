package com.drc.wtf.core;



import com.drc.wtf.test_management.TestCase;



public class ScreenShot 
{

	private String errorScreenFolderPath; 
	
	public ScreenShot(String folderPath)
	{
		errorScreenFolderPath = folderPath;
		
	}
	
	
	 public String takeErrorScreenShot(TestCase testCase)
	    {
		 //Return this only if the screencapture fails
	    	String returnString = "Error Saving the screenshot for step " + testCase.action.testStep;
	       
	    	
	    	
	    	
	    	try {
	        	
	    		

	    		 returnString =testCase.browser.screenShot.TakeAndSaveScreenShot(errorScreenFolderPath, "Step#" + testCase.action.testStep);
	 		   		
	             
	         } catch (Exception e) {
	             e.printStackTrace();
	         } 

	        return returnString;
	   
	    }
	
	
}
