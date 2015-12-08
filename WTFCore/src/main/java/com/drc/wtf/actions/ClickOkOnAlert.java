package com.drc.wtf.actions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.drc.wtf.actions.base.ClickBase;
import com.drc.wtf.actions.interactions.WTFWebElement;
import com.drc.wtf.test_management.TestCase;

public class ClickOkOnAlert extends ClickBase
{
	


public ClickOkOnAlert(TestCase testCase)
{
	super(testCase);
	
}

@Override
public Boolean InitStep()
{
	elementExist = true;
	return elementExist;
}

@Override
public void Perform(){
	
	
	//Find the alert element 
	//wait for it to be clickable
	//and click it
	WebElement alertElement = testCase.browser.driver()
    .findElement(By.xpath("//input[@value = 'alert']"));
	
	super.waitForElement(alertElement);
	
	WTFWebElement elementToClick = new WTFWebElement(alertElement, testCase);
	elementToClick.Click();
	//alertElement.click();
	
	//Handle and click the alert
    Alert javascriptAlertOK = testCase.browser.driver().switchTo().alert();
    javascriptAlertOK.accept();
    
	
	
	 testCase.logging().writeLogAndConsole(this.StepSuccessMessage());
}


	
	 
}
