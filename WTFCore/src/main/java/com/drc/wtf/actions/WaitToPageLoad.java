package com.drc.wtf.actions;



import java.util.concurrent.TimeUnit;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.BrowserInteractionBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class WaitToPageLoad extends BrowserInteractionBase
{
	


public WaitToPageLoad(TestCase testCase)
{
	super(testCase);
	
}

public void Perform() throws TestStepFailureException, InterruptedException {
	 
	testCase.browser.driver().manage().timeouts()
    .pageLoadTimeout(20, TimeUnit.SECONDS);
    testCase.logging().writeLogAndConsole(
            "Step # " + testCase.action.testStep + "-->"
                    + testCase.action.actionName + ">> Script is waiting for "
                    + testCase.action.pageName + " to be load.");
}



	
	 
}
