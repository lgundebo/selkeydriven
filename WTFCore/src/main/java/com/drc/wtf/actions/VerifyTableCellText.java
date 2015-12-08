package com.drc.wtf.actions;



import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.VerifyBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class VerifyTableCellText extends VerifyBase
{
	


public VerifyTableCellText(TestCase testCase)
{
	super(testCase);
	
}

public void Perform() throws TestStepFailureException, InterruptedException {
	
	if(elementExist)
	{
	Verify.TableCellTextVerifyOrClick(testCase);
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
                     + testCase.action.actionName + ">> Table '"
                     + testCase.action.fieldName
                     + " does not exist in the page '"
                     + testCase.action.pageName + "'";


     TestStepFailureException failure = new TestStepFailureException(message);
     throw failure;
}
	 
}


