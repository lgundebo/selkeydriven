package com.drc.wtf.actions;

import org.openqa.selenium.interactions.Actions;

import com.drc.wtf.actions.base.ClickBase;
import com.drc.wtf.test_management.TestCase;

public class DoubleClick extends ClickBase
{
	


public DoubleClick(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElement(testCase.browser.currentElement);
	
	Actions actionDobleClick = new Actions(testCase.browser.driver());
	actionDobleClick.doubleClick(testCase.browser.currentElement).perform();
	actionDobleClick = null;
    
            
	
	 testCase.logging().writeLogAndConsole(this.StepSuccessMessage());
}


	
	 
}
