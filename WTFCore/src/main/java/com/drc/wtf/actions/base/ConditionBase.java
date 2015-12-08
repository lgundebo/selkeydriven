package com.drc.wtf.actions.base;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.SetElement;
import com.drc.wtf.test_management.TestCase;

public abstract class ConditionBase extends ActionBase
{

	
	public ConditionBase(TestCase _testCase)
	{
		super(_testCase);
		
	}
	
	
	/*
	public abstract void Perform() throws TestStepFailureException;//(TestCase testCase);
	protected abstract String StepSuccessMessage();
	*/
	
	protected Boolean ElementDispalyed()
	{
		Boolean elementDisplayed= false;
		if(testCase.browser.currentElement != null)
		{
			try{
		WebDriverWait wait = new WebDriverWait(testCase.browser.driver(), 15 );
		wait.until(ExpectedConditions.visibilityOf(testCase.browser.currentElement));
		elementDisplayed = true;
			}
			catch(Exception ex)
			{
				
				elementDisplayed = false;
			}
		}
		
		return elementDisplayed;
	}
	
	
}
