package com.drc.wtf.actions;


import com.drc.wtf.actions.base.ConditionBase;
import com.drc.wtf.execution.SetElement;
import com.drc.wtf.test_management.TestCase;


public class If extends ConditionBase
{
	


public If(TestCase testCase)
{
	super(testCase);
	
}

@Override
public Boolean InitStep()
{
	elementExist = true;
	return elementExist;
}

@Override
public void Perform(){
	
	
	testCase.setElement.SettingWebelement(testCase);
	
	Boolean enterForLoop =false;
	
	if(testCase.action.fieldValue.toLowerCase().equals("displayed") && super.ElementDispalyed())
	{
		
		enterForLoop = true;
		
	}
	
	if(enterForLoop)
	{
		testCase.logging().writeLogAndConsole(StepSuccessMessage());
	}
	else
	{
		testCase.logging().writeLogAndConsole("Step # " + testCase.action.testStep + "-->"
            + ">> If Statement evaluated to FALSE Skipping the steps until EndIf is encountered");
		
		while(!testCase.GetNextActionName().toLowerCase().equals("endif") && testCase.GetNextActionName() != null)
		{
			testCase.moveToNextStep();
			testCase.logging().writeLogAndConsole(StepSkippedMessage());
		}
		
	}
     
    
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName
            + ">> If Statement evaluated to TRUE entering the If statment for field name '"
            + testCase.action.fieldName + "' with the condition applied '"
            + testCase.action.fieldValue + "'";
            
	
	return message;
}


protected String StepSkippedMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + ">> If Statement evaluated to FALSE Skipping (this is okay) '"
            + testCase.action.fieldName + "' with the condition applied '"
            + testCase.action.fieldValue + "'"
			+ testCase.action.actionName + "'";
            
	
	return message;
}

	 
}
