package com.drc.wtf.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class CaptureWebTextInToInteger extends ActionBase
{
	


public CaptureWebTextInToInteger(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	String captureWebText = testCase.browser.currentElement.getText().trim();


    testCase.captureData(captureWebText);

    testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = 
            "Step # " + testCase.action.testStep + "-->"
                    + testCase.action.actionName
                    + ">> Total number items for the '"
                    + testCase.action.fieldName + "' is :"
                    + testCase.getCapturedData() + "  in the page '"
                    + testCase.action.pageName + "'";
	
	return message;
}


	private int iCountTableRowCount;
	 
}
