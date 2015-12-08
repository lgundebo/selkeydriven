package com.drc.wtf.actions;




import org.openqa.selenium.Keys;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;

public class SendKeys extends ActionBase
{
	


public SendKeys(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	
	
	Keys keyToSend = Keys.valueOf(testCase.action.fieldValue.toUpperCase());
    testCase.browser.currentElement.sendKeys(keyToSend);
    
   
    
 
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
    
    }

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> '"
            + testCase.action.fieldValue
            + "' was sent to the field '"
            + testCase.action.fieldName + "' in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}




	
	 
}
