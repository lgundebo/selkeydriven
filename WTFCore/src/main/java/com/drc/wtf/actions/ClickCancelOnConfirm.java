package com.drc.wtf.actions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.drc.wtf.actions.base.ClickBase;
import com.drc.wtf.actions.interactions.WTFWebElement;
import com.drc.wtf.test_management.TestCase;

public class ClickCancelOnConfirm extends ClickBase
{
	


public ClickCancelOnConfirm(TestCase testCase)
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
	WebElement confirmElement = testCase.browser.driver()
			.findElement(By.xpath("//input[@value = 'confirm']"));
	
	super.waitForElement(confirmElement);
	
	WTFWebElement elementToClick = new WTFWebElement(confirmElement, testCase);
	elementToClick.Click();
	//confirmElement.click();
	
	//Handle and click the alert
	Alert confirm = testCase.browser.driver().switchTo().alert();
	confirm.dismiss();
    
	
	
	 testCase.logging().writeLogAndConsole(this.StepSuccessMessage());
}


	
	 
}
