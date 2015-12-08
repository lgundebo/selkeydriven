package com.drc.wtf.actions;



import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class EnterText extends ActionBase
{
	


public EnterText(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	 // Updates the action with random data if necessary

    testCase.updateActionRandomData();
    testCase.updateActionCaptureData();

    testCase.browser.currentElement.clear();
    
    testCase.browser.currentElement.sendKeys(testCase.action.fieldValue);
    
    if(testCase.driverSetup.browser.getValue().toUpperCase().equals("SAFARI") )
	{
    
    WebElement body = testCase.browser.driver().findElementByXPath("/html/body");
    body.click();
	}
 
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
    
    }

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> '"
            + testCase.action.fieldValue
            + "' was entered in the field '"
            + testCase.action.fieldName + "' in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}


	
	 
}
