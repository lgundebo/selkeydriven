package com.drc.wtf.actions;



import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.VerifyBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class VerifySelectedCheckBoxRadioButton extends VerifyBase
{
	


public VerifySelectedCheckBoxRadioButton(TestCase testCase)
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
    Boolean bCheckUnchecked = testCase.browser.currentElement.isSelected();
     if ((bCheckUnchecked == true)
             && (sExistNotExist.toUpperCase().equals("EXIST")))
     {
         testCase.logging().writeLogAndConsole(
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' was checked in the field '"
                         + testCase.action.fieldName + "' in the page '"
                         + testCase.action.pageName + "'");
     }
     else if ((bCheckUnchecked == false)
             && (sExistNotExist.toUpperCase().equals("NOTEXIST")))
     {
         testCase.logging().writeLogAndConsole(
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' was not checked in the field '"
                         + testCase.action.fieldName + "' in the page '"
                         + testCase.action.pageName + "'");
     }
     else if ((bCheckUnchecked == false)
             && (sExistNotExist.toUpperCase().equals("EXIST")))
     {
        
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' was not checked in the field '"
                         + testCase.action.fieldName + "' in the page '"
                         + testCase.action.pageName + "'";
        
         TestStepFailureException failure = new TestStepFailureException(message);
         throw failure;
         
         
     }
    
     else
     {
         
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' was checked in the field '"
                         + testCase.action.fieldName + "' in the page '"
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
	 String message =       
			 "Step # " + testCase.action.testStep + "-->"
             + testCase.action.actionName + ">> '"
             + testCase.action.fieldName
             + " does not exist in the page '"
             + testCase.action.pageName + "'";


     TestStepFailureException failure = new TestStepFailureException(message);
     throw failure;
}
	 
}
