package com.drc.wtf.actions;



import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class CompareNotEqual extends ActionBase
{
	


public CompareNotEqual(TestCase testCase)
{
	super(testCase);
	
}

public void Perform() throws TestStepFailureException, InterruptedException {
	
	Verify.CompareEqualOrNot(testCase);
	
	 
}

@Override
protected String StepSuccessMessage() {
	
	return null;
}


	
	 
}
