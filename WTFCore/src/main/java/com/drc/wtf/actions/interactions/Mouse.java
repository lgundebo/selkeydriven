package com.drc.wtf.actions.interactions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.MouseAction;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.drc.wtf.core.CodeTimer;
import com.drc.wtf.test_management.TestCase;

public class Mouse 
{
	
	private TestCase testCase;
	
	public Mouse(TestCase _testCase)
	{
		testCase = _testCase;
		
	}
	
	
	private boolean onMouseOver()
	{
		boolean result = false;
		try
		{
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) testCase.browser.driver();
			js.executeScript(mouseOverScript, testCase.browser.currentElement);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	

	public void Click()
	{
		
		if(testCase.driverSetup.browser.getValue().toUpperCase().equals("SAFARI") )
		{
			onMouseOver();
			testCase.browser.wait.WaitElementClickable(testCase.browser.currentElement);
			
			onClick();
						
			
		}
		else
		{
			Actions click = new Actions(testCase.browser.driver());
			click.click(testCase.browser.currentElement);
			click.perform();
			
			
		}
		
	}
	
	
	
	
	
	//Only works with no extra events tied to the click event
	public boolean onClick()
	{
		boolean result = false;
		
		//String onClickText = testCase.browser.currentElement.getAttribute("onclick");
		try
		{
			if(!parentHasOnClick(testCase.browser.currentElement))
			{
			String mouseOverScript = 
					"if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('click');}";
			
			JavascriptExecutor js = (JavascriptExecutor) testCase.browser.driver();
			
			js.executeScript(mouseOverScript, testCase.browser.currentElement);
			}
			else
			{
				WebElement element;
				int loopCount =0; 
				do
				{
					element =null;
					loopCount++;
					
					try
					{
					WebDriverWait wait = new WebDriverWait(testCase.browser.driver(), 1);
					element = wait.until(ExpectedConditions.elementToBeClickable(testCase.browser.currentElement));
					testCase.browser.currentElement.click();
					
					}
					catch(Exception e)
					{
						element =null;
					}
				}while(element != null && loopCount < 5);
				
				
				if(element != null)
				{
					element.sendKeys(Keys.ENTER);
					
				}
				
			}
			result = true;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		
		/*
		
		CodeTimer timer = new CodeTimer();
		timer.start();
		testCase.browser.wait.PageLoad();
		timer.stop();
		testCase.logging().writeLogAndConsole("Waited after click for: " + timer.getTimeMillis());
		*/
		
		testCase.browser.wait.PageLoad();
		
		return result;
	}
	
	private Boolean parentHasOnClick(WebElement childElement)
	{
		Boolean onClickExists = false;
		WebElement parentElement = childElement;
		do{
			String onClickText = parentElement.getAttribute("onclick");
			
			
			
			try
			{
				if(onClickText != null)
				{
					onClickExists = true;
					break;
				}
				parentElement = parentElement.findElement(By.xpath(".."));
			}
			catch(Exception e)
			{
				parentElement = null;
			}
		}while(parentElement != null && parentElement.getTagName() != "body");
		
		
		return onClickExists;
	}
	
	
	private void onClick2()
	{
		//org.openqa.selenium.interactions.Mouse a = testCase.browser.driver().getMouse();
		//Coordinates where;// = (Coordinates) 
		
		RemoteWebElement c =(RemoteWebElement)testCase.browser.currentElement;
		c.click();
		//a.click(c.getCoordinates());
		
	}
	
}
