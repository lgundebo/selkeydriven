package com.drc.wtf.actions;



import java.io.File;












import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.SkipException;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.VerifyBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.SetElement;
import com.drc.wtf.test_management.TestCase;


public class VerifyField extends VerifyBase
{
	
private String sExistNotExist;

public VerifyField(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform() throws TestStepFailureException{
	
	
	

	 sExistNotExist = testCase.action.fieldValue.trim().toUpperCase();
    if (sExistNotExist.toUpperCase().equals("EXIST"))
    {
    	
    	if(elementExist)
    	{
    	
        testCase.logging().writeLogAndConsole(
                "Step # " + testCase.action.testStep + "-->"
                        + testCase.action.actionName
                        + ">> Field exists in the field in the page '"
                        + testCase.action.pageName + "'");
    	}
    	 else
    	    {
    	        
    	        String message =
    	                "Step # " + testCase.action.testStep + "-->"
    	                        + testCase.action.actionName
    	                        + ">> Field does not exist in the field in the page '"
    	                        + testCase.action.pageName + "'";
    	        
    	        TestStepFailureException failure = new TestStepFailureException(message);
    	        throw failure;
    	        
    	       
    	    }
        
    }
    else if (sExistNotExist.toUpperCase().equals("NOTEXIST"))
    {
    	
    	if(!elementExist)
    	{
    	
        testCase.logging().writeLogAndConsole(
                "Step # " + testCase.action.testStep + "-->"
                        + testCase.action.actionName
                        + ">> Field does not exists in the field in the page '"
                        + testCase.action.pageName + "'");
    	}
    	 else
    	    {
    	        
    	        String message =
    	                "Step # " + testCase.action.testStep + "-->"
    	                        + testCase.action.actionName
    	                        + ">> Field exists in the field in the page '"
    	                        + testCase.action.pageName + "'";
    	        
    	        TestStepFailureException failure = new TestStepFailureException(message);
    	        throw failure;
    	        
    	       
    	    }
        
    }
          
                   
    
    testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> Link '"
            + testCase.action.fieldValue
            + "'" + sExistNotExist +" in the field in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}




private void failure() throws TestStepFailureException
{
	  String message = StepSuccessMessage();
              
      
      TestStepFailureException failure = new TestStepFailureException(message);
      throw failure;
}

private static boolean IsItemDisplayed(WebElement sWebelement, String Text)
{

    boolean bLinkExist = false;
    String sText;
    try
    {
        // bLinkExist = sWebelement.isDisplayed();
        sText = sWebelement.getText().trim();
        if (sText.contains(Text.trim()) == true)
        {
            bLinkExist = true;
        }
        return bLinkExist;
    }
    catch (NoSuchElementException e)
    {
        return false;
    }
}


	
	 
}
