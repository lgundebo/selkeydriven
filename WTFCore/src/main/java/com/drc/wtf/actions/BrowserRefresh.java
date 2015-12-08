package com.drc.wtf.actions;



import com.drc.wtf.actions.base.BrowserInteractionBase;
import com.drc.wtf.actions.base.ClickBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.exceptions.TestTimeOutException;
import com.drc.wtf.test_management.TestCase;

public class BrowserRefresh extends BrowserInteractionBase
{
	


public BrowserRefresh(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform() throws TestStepFailureException, TestTimeOutException {
	
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		
		throw new TestTimeOutException(e);
		
	}
	 testCase.browser.driver().navigate().refresh();
	 
	 try {
		 Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new TestTimeOutException(e);
		}
	 
	 testCase.logging().writeLogAndConsole(this.StepSuccessMessage());
}


	
	 
}
