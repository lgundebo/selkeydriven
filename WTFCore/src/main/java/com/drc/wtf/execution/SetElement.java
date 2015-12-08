package com.drc.wtf.execution;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;




import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.drc.wtf.core.CodeTimer;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class SetElement 
{
	private TestCase testCase;
	
	
	public SetElement(TestCase _testCase)
	{
		testCase = _testCase;
	}
	

	private  int defaultWaitTime = 15;
	
	public  boolean SettingWebelement(TestCase testCase)
	{
		return SettingWebelement (testCase, testCase.element.fieldProperty,
                testCase.element.fieldProperty1, defaultWaitTime);
		
		
	}

	public  boolean SettingWebelement(TestCase testCase, int secondsToWait)
	{
		return SettingWebelement (testCase, testCase.element.fieldProperty,
                testCase.element.fieldProperty1, secondsToWait);
		
		
	}
	
	public  boolean SettingWebelement (TestCase testCase, String ObjectNameProperty, String ObjectPropertyValue) {
		
		return SettingWebelement(testCase, ObjectNameProperty, ObjectPropertyValue, defaultWaitTime);
	}
	
	public  boolean SettingWebelement (TestCase testCase, String ObjectNameProperty, String ObjectPropertyValue, int secondsToWait) {
	    
		
		testCase.browser.currentElement = GetWebelement(testCase, ObjectNameProperty, ObjectPropertyValue, secondsToWait);
		
	    	//return true if web element exists
	    return (testCase.browser.currentElement != null);   		        
	}    
	
	
	
	public  WebElement GetWebelement(TestCase testCase)
	{
		return GetWebelement(testCase, testCase.element.fieldProperty,
                testCase.element.fieldProperty1, defaultWaitTime);
	}
	
	public  WebElement GetWebelement(TestCase testCase, String ObjectNameProperty, String ObjectPropertyValue) {
		return GetWebelement(testCase, ObjectNameProperty,
				ObjectPropertyValue, defaultWaitTime);
	}
	
	public  WebElement GetWebelement(TestCase testCase, String ObjectNameProperty, String ObjectPropertyValue, int secondsToWait) {
	
			
				testCase.browser.wait.WaitElementNotPresent(By.className("blockUI")); //PageLoad();
			
		
			
		
				testCase.browser.wait.WaitElementNotDisplayed(By.cssSelector("#ctl00_DefaultContent_UpdateProgressWait1_popUpPleaseWait_DXPWMB-1"));
				
				
				
				
				WebElement element =null;
				
				
				
				try{
					
			    	By by = createBy(testCase, ObjectNameProperty, ObjectPropertyValue);
			
			    	testCase.browser.currentElement = null;
			    	
			    	testCase.browser.wait.WaitElementPresent(by, secondsToWait);
			    	element = testCase.browser.driver().findElement(by);
			    	
			    
			    	
					
			    } catch (NoSuchElementException e) {
			    element = null;
			    
			        
			    }
			    finally
			    {
			    	if(element != null)
			    	{
			    	
			    	scrollToElement(element, testCase);
			    	
			    
					
			    	}
			    }
				
				
				/*
				 * Adding this block to hopefully eliminate stale element exceptions
				 * It is possible the state of the elment is changing as 
				 * it is scrolled into view?????
				 */
				
				
				
				try{
					
			    	By by = createBy(testCase, ObjectNameProperty, ObjectPropertyValue);
			
			    	testCase.browser.currentElement = null;
			    	
			    	testCase.browser.wait.WaitElementPresent(by, secondsToWait);
			    	element = testCase.browser.driver().findElement(by);
			    	
					
			    } catch (NoSuchElementException e) {
			    element = null;
			    
			        
			    }
				
				
				
			    return element; 
	
	}
	
	
	
	

	public  void scrollToElement(WebElement element, TestCase testCase)
	{
	
		
		
		
		//By locator = createBy(testCase, testCase.element.fieldName, testCase.element.fieldProperty);
		WebElement rootElement = testCase.browser.driver().findElementByXPath("/html/body");
		//moveToElement(testCase.browser.driver(), rootElement, By.xpath("/html/body"));
		
		//onMouseOver(rootElement, testCase);
		
		/*
		Actions hover = new Actions(testCase.browser.driver());
		hover.moveByOffset(-1000,-1000);
		hover.perform();
		*/
		WebElement original = element;
		 try
		  {
			  while(!element.isDisplayed())
			  {
				  
				 element = element.findElement(By.xpath(".."));
				  
				 // element = (WebElement) ((JavascriptExecutor) testCase.browser.driver()).executeScript(
                        //  "return arguments[0].parentNode;", element);
			  }
		  }
		 catch(Exception ex)
		 {
			// testCase.takeErrorScreenShot();
			// ex.printStackTrace(); 
			 
		 }
		
	  try
	  {
		 
		
	   
	   
	   ((JavascriptExecutor) testCase.browser.driver()).executeScript("arguments[0].scrollIntoView(true);", element);
	   
	 
	  }
	  catch(Exception e)
	  {
		//  testCase.takeErrorScreenShot();
		// e.printStackTrace();
	  }
	  
	try{
		
		
		onMouseOver(element, testCase);
		
		
	}
	catch(Exception e)
	{
		testCase.takeErrorScreenShot();
		e.printStackTrace();
		
	}
	WebDriverWait wait = new WebDriverWait (testCase.browser.driver(), 15 );
	
	try{
	element = wait.until(ExpectedConditions.visibilityOf(original));
	}
	catch(Exception ex){
		//testCase.takeErrorScreenShot();
		//ex.printStackTrace();
	}
	 
	
	}
	
	
	private  By createBy(TestCase testCase, String ObjectNameProperty, String ObjectPropertyValue)
	{
		By by = null;
				
	    	
					switch  (ObjectNameProperty){
					  		case "id":  
					  			by = new By.ById(ObjectPropertyValue);
					  						
					  			
					  			break;
					  		case "name":  
					  			
					  			by = new By.ByName(ObjectPropertyValue);
					  			
					  			
					  			break;
					  		case "xpath":  
					  			
					  			by = new By.ByXPath(ObjectPropertyValue);
					  			
								
					  			break;	
					  		case "cssSelector":  
					  			
					  			by = new By.ByCssSelector(ObjectPropertyValue);
					  		
								break;			
					  		case "className":  
					  			
					  			by = new By.ByClassName(ObjectPropertyValue);
					  			
								break;						
					  		case "linkText":  
					  			
					  			by = new By.ByLinkText(ObjectPropertyValue);
					  			
								break;	
					  		case "partialLinkText":  
					  			
					  			by = new By.ByPartialLinkText(ObjectPropertyValue);
					  			
								break;		
					  		case "tagName":  
					  			
					  			by = new By.ByTagName(ObjectPropertyValue);
					  			
								break;	
							default:
								TestStepFailureException failure = new TestStepFailureException("Step # "+testCase.action.testStep +"-->"+ObjectNameProperty+">> is not a identification property in the Testing Framework" );
								testCase.SetStepStatusFailed(failure);
										
								//testCase.logging().writeLogAndConsole("Step # "+testCase.action.testStep +"-->"+ObjectNameProperty+">> is not a identification property in the Testing Framework" );
								break;		
					}
								
					return by;
		
	}
	
	
	public void moveToElement(WebDriver driver, WebElement element, By locator) {
		
		
		
		
		
		if (!(testCase.browser.driver() instanceof SafariDriver)) {
			Actions builder = new Actions(testCase.browser.driver());
			builder.moveToElement(element).perform();
		}
		else {
			JavascriptExecutor js = (JavascriptExecutor) testCase.browser.driver();
			String locatorType = locator.toString().substring(3);
			String elem = "var elem = document;";
			if (locatorType.startsWith("id")) {
				elem = "var elem = document.getElementById(\""+locatorType.substring(4)+"\");";
			}
			else if (locatorType.startsWith("xpath")) {
				String snippet = "document.getElementByXPath = function(sValue) { var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); if (a.snapshotLength > 0) { return a.snapshotItem(0); } }; ";
				js.executeScript(snippet);
				elem = "var elem = document.getElementByXPath(\""+locatorType.substring(7)+"\");";
			}
			else if (locatorType.startsWith("className")) {
				elem = "var elem = document.getElementsByClassName(\""+locatorType.substring(14)+"\")[0];";
			}
			String mouseOverScript = elem + " if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false);" +
					" elem.dispatchEvent(evObj);} else if(document.createEventObject) { elem.fireEvent('onmouseover');}";
			js.executeScript(mouseOverScript);
		}
	}
	
	public boolean onMouseOver(WebElement element, TestCase testCase)
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
	
	
}
