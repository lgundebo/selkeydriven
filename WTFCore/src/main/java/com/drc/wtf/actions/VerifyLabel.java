package com.drc.wtf.actions;



import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.VerifyBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class VerifyLabel extends VerifyBase
{
	


public VerifyLabel(TestCase testCase)
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


     // Update the current action with the random data
     testCase.updateActionRandomData();
     testCase.updateActionCaptureData();

     testCase.browser.currentElement = testCase.setElement.GetWebelement(testCase);
     String sTestingText = testCase.browser.currentElement.getText().toLowerCase();
     Boolean bTextCheck = sTestingText.contains(testCase.action.fieldValue.trim().toLowerCase());

     if ((bTextCheck == true) && (sExistNotExist.toUpperCase().equals("EXIST")))
     {
         testCase.logging().writeLogAndConsole(
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' exists in the field: " + testCase.action.fieldName
                         + " in the page '" + testCase.action.pageName + "'");
     }

     else if ((bTextCheck == false)
             && (sExistNotExist.toUpperCase().equals("NOTEXIST")))
     {
         testCase.logging().writeLogAndConsole(
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' does not exist in the field: "
                         + testCase.action.fieldName + " in the page '"
                         + testCase.action.pageName + "'");
     }

     else if ((bTextCheck == true)
             && (sExistNotExist.toUpperCase().equals("NOTEXIST")))
     {
         
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' exists in the field: " + testCase.action.fieldName
                         + " in the page '" + testCase.action.pageName + "'";
         
         TestStepFailureException failure = new TestStepFailureException(message);
         throw failure;
         
         
     }

     else if ((bTextCheck == false)
             && (sExistNotExist.toUpperCase().equals("EXIST")))
     {
        
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' does not exist in the field: "
                         + testCase.action.fieldName + " in the page '"
                         + testCase.action.pageName + "'"
                         + "Actual Text is: " +testCase.browser.currentElement.getText();
         
         TestStepFailureException failure = new TestStepFailureException(message);
         throw failure;
         
         
     }

     else
     {
         
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> Please review '"
                         + testCase.action.fieldValue + "' in field "
                         + testCase.action.fieldName + " in the page '"
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
                        + testCase.action.actionName + ">> Label '"
                        + testCase.action.fieldName
                        + " does not exist in the field in the page '"
                        + testCase.action.pageName + "'");
    }
    else
    {
        
        String message =
                "Step # " + testCase.action.testStep + "-->"
                        + testCase.action.actionName + ">> Label '"
                        + testCase.action.fieldName
                        + " does not exist in the page '"
                        + testCase.action.pageName + "'";


        TestStepFailureException failure = new TestStepFailureException(message);
        throw failure;
        
    }
}	
	 
}
