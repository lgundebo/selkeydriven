package com.drc.wtf.actions;



import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.BrowserInteractionBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class Wait extends BrowserInteractionBase
{
	


public Wait(TestCase testCase)
{
	super(testCase);
	
}

public void Perform() throws TestStepFailureException, InterruptedException {
	 
	testCase.logging().writeLogAndConsole(
             "Step # " + testCase.action.testStep + "-->"
                     + testCase.action.actionName
                     + ">> Script is SKIPPING waiting for '"
                     + testCase.action.fieldValue + "' seconds.");
}




	
	 
}
