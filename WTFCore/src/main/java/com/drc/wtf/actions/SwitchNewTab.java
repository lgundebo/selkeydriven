package com.drc.wtf.actions;







import java.util.ArrayList;






import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class SwitchNewTab extends ActionBase
{
	


public SwitchNewTab(TestCase testCase)
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
	
	  ArrayList<String> newTab =
              new ArrayList<String>(testCase.browser.driver().getWindowHandles());
              // newTab.remove(oldTab);
              testCase.browser.driver().switchTo().window(newTab.get(1));
             
                      
     
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> Tab '"
            + testCase.action.fieldValue
            + "' is selected in the page '" + testCase.action.pageName
            + "'";
	
	return message;
}


	
	 
}
