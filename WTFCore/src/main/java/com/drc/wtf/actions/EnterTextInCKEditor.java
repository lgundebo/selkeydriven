package com.drc.wtf.actions;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;






import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class EnterTextInCKEditor extends ActionBase
{
	


public EnterTextInCKEditor(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	

	
	testCase.browser.driver().switchTo().frame(testCase.browser.currentElement);
    WebElement elems = testCase.browser.driver().switchTo().activeElement();
    JavascriptExecutor js = (JavascriptExecutor) testCase.browser.driver();
    elems.clear();
    js.executeScript("document.body.innerHTML = '<br>'");
    
    if(testCase.driverSetup.browser.getValue().toUpperCase().equals("SAFARI") )     

    {

               js.executeScript( "var div = document.getElementsByTagName (\"body\")[0];if (div.contentEditable == \"true\") {div.contentEditable = \"false\";var text=div.innerHTML;div.innerHTML = text+arguments[0];}",testCase.action.fieldValue);

           } 
    
    else
    {
   
   
	
    		
    		elems.sendKeys(testCase.action.fieldValue);	
    }
	
            
    
    
	String winHandle = testCase.browser.driver().getWindowHandle();
	testCase.browser.driver().switchTo().window(winHandle);
     
     
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> '"
            + testCase.action.fieldValue
            + "' was entered in the field '"
            + testCase.action.fieldName + "' in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}


	
	 
}
