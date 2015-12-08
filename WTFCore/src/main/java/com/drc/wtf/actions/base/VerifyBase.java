package com.drc.wtf.actions.base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.drc.wtf.test_management.TestCase;


public abstract class VerifyBase extends ActionBase
{

	
	public VerifyBase(TestCase testCase)
	{
		super(testCase);
	}
	
	
	@Override
	public Boolean InitStep()
	{
		/*
		 * On the Mac 10.9 under the Safari browser
		 * for test cases that involve large numbers of
		 * "Verify" steps in a row the browser thread seems to
		 * sleep until there is physical mouse movement on the 
		 * computer or the test case reaches actions that click on valid
		 * buttons.  This adjusts the browser every time there is a verify
		 * keeping the browser active without changing the state of the DOM
		 */
		 if(testCase.driverSetup.browser.getValue().toUpperCase().equals("SAFARI") )
			{
		Dimension initalSize = testCase.browser.driver().manage().window().getSize();
		Dimension adjustedSize = new Dimension(initalSize.getWidth() -1, initalSize.getHeight() -1);
		testCase.browser.driver().manage().window().setSize(adjustedSize);
		testCase.browser.driver().manage().window().setSize(initalSize );
			}
		
		Boolean initResult = super.InitStep();
		
		return initResult;
	}
	
	
	@Override
	protected String StepSuccessMessage() {
		String actionString = "Step # " + testCase.action.testStep + "-->"
                + testCase.action.actionName + ">> '"
                + testCase.action.fieldName + "' was clicked in the page '"
                + testCase.action.pageName + "'";
		
		return actionString;
	}
	
	
}
