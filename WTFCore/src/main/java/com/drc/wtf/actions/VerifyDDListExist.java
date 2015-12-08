package com.drc.wtf.actions;



import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.VerifyBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class VerifyDDListExist extends VerifyBase
{
	


public VerifyDDListExist(TestCase testCase)
{
	super(testCase);
	
}

public void Perform() throws TestStepFailureException, InterruptedException {
	
	if(elementExist)
	{
	int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
     String sExistNotExist =
             testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
             .toUpperCase();
     testCase.action.fieldValue =
             testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
     if (sExistNotExist.toUpperCase().equals("EXIST"))
     {
         testCase.logging().writeLogAndConsole(
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' exists in the field in the page '"
                         + testCase.action.pageName + "'");
     }
    
     else
     {
         
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' does not exist in the field in the page '"
                         + testCase.action.pageName + "'";
         
         TestStepFailureException failure = new TestStepFailureException(message);
         throw failure;
         
         
         
     }
	}
	else
	{
		elementNotExist();
	}
}

@Override
protected String StepSuccessMessage() {
	
	return null;
}

private void elementNotExist() throws TestStepFailureException
{
	int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
    String sExistNotExist =
            testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
            .toUpperCase();
    testCase.action.fieldValue =
            testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
    if (sExistNotExist.toUpperCase().equals("NOTEXIST"))
    {
        testCase.logging().writeLogAndConsole(
                "Step # " + testCase.action.testStep + "-->"
                        + testCase.action.actionName + ">> Dropdown list '"
                        + testCase.action.fieldValue
                        + "' does not exist in the field in the page '"
                        + testCase.action.pageName + "'");
    }
    // (sExistNotExist.toUpperCase().equals("EXIST"))
    else
    {
        
        String message=        "Step # " + testCase.action.testStep + "-->"
                        + testCase.action.actionName + ">> Drop down list '"
                        + testCase.action.fieldValue
                        + "' exists in the field in the page '"
                        + testCase.action.pageName + "'";
        
        TestStepFailureException failure = new TestStepFailureException(message);
        throw failure;

    }
}
	
	 
}
