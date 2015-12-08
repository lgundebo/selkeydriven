package com.drc.wtf.actions;




import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class EndIf extends ActionBase
{
	


public EndIf(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	
	
     
     
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName
            + ">> EndIf encountered normal execution will resume'";
            
	
	return message;
}

	
	 
}
