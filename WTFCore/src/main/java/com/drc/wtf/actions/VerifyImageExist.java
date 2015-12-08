package com.drc.wtf.actions;



import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.VerifyBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class VerifyImageExist extends VerifyBase
{
	


public VerifyImageExist(TestCase testCase)
{
	super(testCase);
	
}

public void Perform() throws TestStepFailureException, InterruptedException {
	
	 String sExistNotExist = testCase.action.fieldValue.trim().toUpperCase();
     
     if (sExistNotExist.toUpperCase().equals("EXIST") && testCase.browser.currentElement != null)
     {
         testCase.logging().writeLogAndConsole(
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldName
                         + "' exists in the field in the page '"
                         + testCase.action.pageName + "'");
     }
     else
     {
         //testCase.SetStepStatusFailed();
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldName
                         + "' does not exist in the field in the page '"
                         + testCase.action.pageName + "'";
         
         
         TestStepFailureException failure = new TestStepFailureException(message);
         throw failure;
         
     }
}

@Override
protected String StepSuccessMessage() {
	
	return null;
}


	
	 
}
