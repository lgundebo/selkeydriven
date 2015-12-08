package com.drc.wtf.actions.base;

import org.openqa.selenium.WebElement;

import com.drc.wtf.test_management.TestCase;


public abstract class ClickBase extends ActionBase
{

	/*
	public ClickBase()
	{}
	*/
	
	public ClickBase(TestCase testCase)
	{
		super(testCase);
	}
	
	
	public void waitForElement(WebElement element)
	{
		testCase.browser.wait.WaitElementClickable(element);
		
	}
	
	
	@Override
	public String StepSuccessMessage() {
		String actionString = "Step # " + testCase.action.testStep + "-->"
                + testCase.action.actionName + ">> '"
                + testCase.action.fieldName + "' was clicked in the page '"
                + testCase.action.pageName + "'";
		
		return actionString;
	}
	
	
}
