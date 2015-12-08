package com.drc.wtf.actions.iat;

import org.openqa.selenium.WebElement;

import com.drc.wtf.exceptions.RestartTestException;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;

public abstract class IATBase 
{

	
	public IATBase(TestCase _testCase)
	{
		testCase = _testCase;
	}
	
	
	
	public abstract void Perform() throws TestStepFailureException, InterruptedException, RestartTestException;//(TestCase testCase);
	protected abstract String StepSuccessMessage();
	
	
	public void waitForElementDispalyed(WebElement element)
	{
		testCase.browser.wait.WaitElementDisplayed(element);
		
	}
	
	protected TestCase testCase;
}
