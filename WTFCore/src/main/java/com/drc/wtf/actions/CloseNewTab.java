package com.drc.wtf.actions;



import java.io.File;





import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.SkipException;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class CloseNewTab extends ActionBase
{
	


public CloseNewTab(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	
	testCase.browser.driver().findElement(By.cssSelector("body"))
    .sendKeys(Keys.CONTROL + "w");// close the tab
           
                    
     
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> '"
            + testCase.action.fieldValue
            + "' a tab was closed '"
            + testCase.action.fieldName + "' in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}


	
	 
}
