package com.drc.wtf.core;

import java.util.Random;

import com.drc.wtf.object_repository.Element;
import com.drc.wtf.test_management.Action;

public class RandomData 
{
	
	
	public Element updateDynamicRepositoryValues(Action action)
	{
	
 		   Element element = new Element();
	    		
	   			int ComaIndex = action.fieldValue.indexOf("&");
	   			element.fieldProperty = action.fieldValue.substring(0, ComaIndex).trim();
	   			
	   			element.fieldProperty1 = action.fieldValue.substring(ComaIndex+1).trim();
	   			
			
				if (element.fieldProperty1.contains("NewTestNameRandom")) { 
					
					element.fieldProperty1 = element.fieldProperty1.replace("NewTestNameRandom", sNewTestNameRandom);
						
					}
					else if (element.fieldProperty1 .contains("NewTestDescriptionRandom")) { 
						element.fieldProperty1 = element.fieldProperty1.replace("NewTestDescriptionRandom", sNewTestDescriptionRandom); }
						
					else if (element.fieldProperty1.contains("NewTestSessionRandom")) { 
						element.fieldProperty1 = element.fieldProperty1.replace("NewTestSessionRandom", sNewTestSessionRandom ); }
					else if (element.fieldProperty1.contains("NewStudentGroupRandom")) { 
						element.fieldProperty1 = element.fieldProperty1.replace("NewStudentGroupRandom", sNewStudentGroupRandom); }
					else if (element.fieldProperty1.contains("NewStudentGroupDescriptionRandom")) { 
						element.fieldProperty1 = element.fieldProperty1.replace("NewStudentGroupDescriptionRandom", sNewStudentGroupDescriptionRandom); }
					else if (element.fieldProperty1.contains("NewCommentRandom")) { 
						element.fieldProperty1 = element.fieldProperty1.replace("NewCommentRandom", sNewCommentRandom); }
					else{ }
				
				    if(action.actionName.toUpperCase().contains("VERIFYFIELD") == true  )
				    {
				    	String sText = element.fieldProperty;
						int UnderScoreIndex = sText.indexOf("_");
						action.fieldValue=  sText.substring(0, UnderScoreIndex).trim().toUpperCase();
						element.fieldProperty  =  sText.substring(UnderScoreIndex+1).trim();
				    	
				    }
					
	    	   
		return element;
	}
	
	
	public Action updateActionRandom(Action action)
	{
		if (action.fieldValue.contains("NewTestNameRandom")) { 
			action.fieldValue =  action.fieldValue.replace("NewTestNameRandom", sNewTestNameRandom); }
		else if (action.fieldValue.contains("NewTestDescriptionRandom")) { 
			action.fieldValue =  action.fieldValue.replace("NewTestDescriptionRandom", sNewTestDescriptionRandom); }
		else if (action.fieldValue.contains("NewTestSessionRandom")) { 
			action.fieldValue =  action.fieldValue.replace("NewTestSessionRandom", sNewTestSessionRandom ); }
		else if (action.fieldValue.contains("NewStudentGroupRandom")) { 
			action.fieldValue =  action.fieldValue.replace("NewStudentGroupRandom", sNewStudentGroupRandom ); }	
		else if (action.fieldValue.contains("NewStudentGroupDescriptionRandom")) { 
			action.fieldValue =  action.fieldValue.replace("NewStudentGroupDescriptionRandom", sNewStudentGroupDescriptionRandom ); }	
		else if (action.fieldValue.contains("NewCommentRandom")) { 
			action.fieldValue =  action.fieldValue.replace("NewCommentRandom", sNewCommentRandom ); }
		//else if (action.fieldValue.contains("CaptureWebTextIntoInteger1")) { 
		//	action.fieldValue =  action.fieldValue.replace("CaptureWebTextIntoInteger1", Integer.toString(iCaptureWebTextIntoInteger1) );}
		
		
		return action;
	}
	
	

	private String sNewTestNameRandom =  "NT"+ Random(1000,9999);
	private String sNewTestDescriptionRandom = "NTD"+ Random(1000,9999);
	private String sNewTestSessionRandom =  "TS"+ Random(1000,9999);
	private String sNewStudentGroupRandom = "SG"+ Random(1000,9999);
	private String sNewStudentGroupDescriptionRandom = "SGD" +Random(1000,9999);
	private String sNewCommentRandom = "Comment"+ Random(1000,9999);
	
	private int iCaptureWebTextIntoInteger1;
	
	 private String Random(int min, int max){
	    	Random rn = new Random();
	    	int n = max - min + 1;
	    	int i = rn.nextInt() % n;
	    	int randomNum =  min + i;
	    	String srandomNum =  String.valueOf(randomNum);
	    	srandomNum = srandomNum.replace("-", "");
	    	return srandomNum;
	    }
	
	
}
