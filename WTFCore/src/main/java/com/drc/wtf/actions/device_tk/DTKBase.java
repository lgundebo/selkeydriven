package com.drc.wtf.actions.device_tk;

import org.openqa.selenium.WebElement;

import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;

public abstract class DTKBase 
{

	
	public DTKBase(TestCase _testCase)
	{
		testCase = _testCase;
	}
	
	
	
	public abstract void Perform() throws TestStepFailureException, InterruptedException;//(TestCase testCase);
	protected abstract String StepSuccessMessage();
	
	
	public void waitForElementDispalyed(WebElement element)
	{
		testCase.browser.wait.WaitElementDisplayed(element);
		
	}
	
	protected TestCase testCase;
}
