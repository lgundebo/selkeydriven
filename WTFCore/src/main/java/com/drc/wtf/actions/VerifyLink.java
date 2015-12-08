package com.drc.wtf.actions;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.VerifyBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;


public class VerifyLink extends VerifyBase
{
	
private String sExistNotExist;

public VerifyLink(TestCase testCase)
{
	super(testCase);
	
}

@Override
public Boolean InitStep()
{
return false;	
}

@Override
public void Perform() throws TestStepFailureException{
	
	//Boolean elementExist =false;
	
	int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
     sExistNotExist =
            testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
            .toUpperCase();


    testCase.action.fieldValue =
            testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
   
  
   //Checking if the link exists
   if(sExistNotExist.toUpperCase().equals("EXIST"))
   {
	   //set the element
	   elementExist =
				testCase.setElement.SettingWebelement(testCase, testCase.element.fieldProperty,
                   testCase.element.fieldProperty1);
	 
	   //if it is displayed success
	   if(!IsItemDisplayed(testCase.browser.currentElement,
                    testCase.action.fieldValue))
	   {
		
		   failure();
	   }
	   
   }
   else if(sExistNotExist.toUpperCase().equals("NOTEXIST"))
   {
	   elementExist =
				testCase.setElement.SettingWebelement(testCase, testCase.element.fieldProperty,
                   testCase.element.fieldProperty1, 3);
	   
	   /*
	    * if the element exists
	    * and has the displayed text
	    * failure
	   */
	   
	   
	   if(elementExist && 
			   IsItemDisplayed(testCase.browser.currentElement,
               testCase.action.fieldValue))
	   {
		   failure();
	   }
	   
	   
   }
   
   
    else
    {
       
        failure();
       
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
