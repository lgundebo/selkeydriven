package com.drc.wtf.actions;



import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;

import com.drc.wtf.actions.base.ClickBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;

public class Click extends ClickBase
{
	


public Click(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform() throws TestStepFailureException {
	
	try
	{
		super.waitForElementDispalyed(testCase.browser.currentElement);
	}
	catch(Exception e)
	{}
	
	try{
	
		super.waitForElementDispalyed(testCase.browser.currentElement);
		
		
		this.mouse.Click();
	
	}
catch(Exception ex)
{
	
TestStepFailureException failure = new	TestStepFailureException(ex.getMessage());
throw failure;
}
	
	 testCase.logging().writeLogAndConsole(this.StepSuccessMessage());
}


	
	 
}
