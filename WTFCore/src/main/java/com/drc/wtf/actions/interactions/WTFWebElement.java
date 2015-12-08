package com.drc.wtf.actions.interactions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.MouseAction;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.drc.wtf.test_management.TestCase;

public class WTFWebElement
{
	
	private WebElement element;
	private TestCase testCase;
	
	
	public WTFWebElement(WebElement _element, TestCase _testCase)
	{
		element = _element;
		testCase = _testCase;
		
	}
	
	
	
	

	public void Click()
	{
		
		if(testCase.driverSetup.browser.getValue().toUpperCase().equals("SAFARI") )
		{
			onMouseOver();
			testCase.browser.wait.WaitElementClickable(element);
			
			onClick();
						
			
		}
		else
		{
			Actions click = new Actions(testCase.browser.driver());
			click.click(element);
			click.perform();
			
			
		}
		
	}
	
	
	private boolean onMouseOver()
	{
		boolean result = false;
		try
		{
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) testCase.browser.driver();
			js.executeScript(mouseOverScript, element);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	
	//Only works with no extra events tied to the click event
	private boolean onClick()
	{
		boolean result = false;
		
		String onClickText = element.getAttribute("onclick");
		try
		{
			if(onClickText == null)
			{
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('click');}";
			JavascriptExecutor js = (JavascriptExecutor) testCase.browser.driver();
		
			js.executeScript(mouseOverScript, element);
			}
			else
			{
				WebElement elementLocal;
				int loopCount =0; 
				do
				{
					elementLocal =null;
					loopCount++;
					
					try
					{
					WebDriverWait wait = new WebDriverWait (testCase.browser.driver(), 1);
					element = wait.until(ExpectedConditions.elementToBeClickable(element));
					element.click();
					
					}
					catch(Exception e)
					{
						elementLocal =null;
					}
				}while(elementLocal != null && loopCount < 5);
			}
			result = true;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		
		testCase.browser.wait.PageLoad();
		
		return result;
	}
	
}
