package com.drc.wtf.actions.base;

import org.openqa.selenium.WebElement;

import com.drc.wtf.actions.interactions.Mouse;
import com.drc.wtf.exceptions.ObjectRepositoryException;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.exceptions.TestTimeOutException;
import com.drc.wtf.test_management.TestCase;

public abstract class ActionBase 
{

	
	public ActionBase(TestCase _testCase)
	{
		testCase = _testCase;
		mouse = new Mouse(testCase);
	}
	
	
	
	public abstract void Perform() throws TestStepFailureException, InterruptedException, TestTimeOutException;//(TestCase testCase);
	
	
	protected abstract String StepSuccessMessage();
	
	
	public Boolean InitStep()
	{
		elementExist = false;
		
		    
            if (testCase.action.fieldName != "")
            {

            	try{
            		if (testCase.element != null)
            		{
            			elementExist =
            					testCase.setElement.SettingWebelement(testCase, testCase.element.fieldProperty,
                                    testCase.element.fieldProperty1);
            		}
            		else
            		{
                	
                    throw new ObjectRepositoryException(testCase);
                    
            		}
            	}
				catch (ObjectRepositoryException e)
				{
					testCase.SetStepStatusFailed(e);
					
	
					
					elementExist = false;
				}
                
            }
            else
            {
                elementExist = true;
            }

        
		return elementExist;
	}
	
	public void waitForElementDispalyed(WebElement element)
	{
		testCase.browser.wait.WaitElementDisplayed(element);
		
	}
	
	public void waitForElementClickable(WebElement element)
	{
		
		testCase.browser.wait.WaitElementClickable(element);
	}
	
	protected TestCase testCase;
	protected Boolean elementExist = false;
	
	protected Mouse mouse;
}
