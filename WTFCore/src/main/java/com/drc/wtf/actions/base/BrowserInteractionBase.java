package com.drc.wtf.actions.base;

import org.openqa.selenium.WebElement;

import com.drc.wtf.test_management.TestCase;


public abstract class BrowserInteractionBase extends ActionBase
{

	
	public BrowserInteractionBase(TestCase testCase)
	{
		super(testCase);
	}
	
	@Override
	public Boolean InitStep()
	{
		elementExist = true;
		return elementExist;
	}
	
	
	
	
	
	@Override
	protected String StepSuccessMessage() {
		String actionString = "Step # " + testCase.action.testStep + "-->"
                + testCase.action.actionName + ">> '"
                + testCase.action.fieldName + "' was performed in the browser'"
                + testCase.action.pageName + "'";
		
		return actionString;
	}
	
	
}
