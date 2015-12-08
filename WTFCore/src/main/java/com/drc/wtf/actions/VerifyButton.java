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


public class VerifyButton extends VerifyBase
{
	
private String sExistNotExist;

public VerifyButton(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform() throws TestStepFailureException{
	

	
          if(elementExist)
          {
        	  elementExists();
        	  
          }
          else
          {
        	  elementNotExists();
        	  
          }
                   
    
    testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> Button '"
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



private void elementExists() throws TestStepFailureException
{
	int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
    sExistNotExist =
            testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
            .toUpperCase();
    testCase.action.fieldValue =
            testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
    if (sExistNotExist.toUpperCase().equals("EXIST"))
    {
    	
    	testCase.logging().writeLogAndConsole(StepSuccessMessage());
    	
       
    }
    else if (sExistNotExist.toUpperCase().equals("NOTEXIST"))
    {
       failure();
        
    }
    else
    {
        
        String message =
                "Step # "
                        + testCase.action.testStep
                        + "-->"
                        + testCase.action.actionName
                        + ">> Button '"
                        + testCase.action.fieldValue
                        + "' needs to be either EXIST or NOTEXIST in the field in the page '"
                        + testCase.action.pageName + "'";
        
        
        TestStepFailureException failure = new TestStepFailureException(message);
        throw failure;
        
        
    }	
}


private void elementNotExists() throws TestStepFailureException
{
	int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
    
	sExistNotExist =
            testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
            .toUpperCase();
    
    testCase.action.fieldValue =
            testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
    
    if (sExistNotExist.toUpperCase().equals("NOTEXIST"))
    {
    	testCase.logging().writeLogAndConsole(StepSuccessMessage());
    }
    else if (sExistNotExist.toUpperCase().equals("EXIST"))
    {
       failure();
        
    }
    else
    {
        
        String message =
                "Step # "
                        + testCase.action.testStep
                        + "-->"
                        + testCase.action.actionName
                        + ">> Button '"
                        + testCase.action.fieldValue
                        + "' needs to be either EXIST or NOTEXIST in the field in the page '"
                        + testCase.action.pageName + "'";


        TestStepFailureException failure = new TestStepFailureException(message);
        throw failure;
        
    }
}

	
	 
}
