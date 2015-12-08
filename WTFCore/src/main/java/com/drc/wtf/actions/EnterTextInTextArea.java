package com.drc.wtf.actions;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.interactions.Actions;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class EnterTextInTextArea extends ActionBase
{
	


public EnterTextInTextArea(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	WebElement frame =
            testCase.browser
            .driver()
            .findElement(
                    By.xpath(".//*[@id='editPassageForm']/div/div/div/div/iframe"));
            testCase.browser.driver().switchTo().frame(frame);
            WebElement elem = testCase.browser.driver().findElement(By.tagName("body"));
            elem.click();
            elem.clear();
            elem.sendKeys("texting ckeditor");
            Actions act1 = new Actions(testCase.browser.driver());
            act1.sendKeys(elem, "Hello").build().perform();

            JavascriptExecutor js = null;
            if ((testCase.browser) instanceof JavascriptExecutor)
            {
                js = (JavascriptExecutor) (testCase.browser);

            }
            js.executeScript("CKEDITOR.instances['editorInstancesIE[i]'].insertHtml('Hi How are you.');");

            

           
                    
     
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
