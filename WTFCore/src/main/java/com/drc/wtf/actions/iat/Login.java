package com.drc.wtf.actions.iat;

import org.openqa.selenium.By;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.ClickBase;
import com.drc.wtf.exceptions.RestartTestException;
import com.drc.wtf.exceptions.TestCaseFailureException;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.StepExecution;
import com.drc.wtf.test_management.TestCase;

public class Login extends IATBase {

	public Login(TestCase testCase) {
		super(testCase);

	}

	@Override
	public void Perform() throws TestStepFailureException,
			InterruptedException, RestartTestException {

		Boolean loggingIn = true;
		ActionBase action;

		testCase.browser.wait.PageLoad();
		try {
		while (loggingIn) {
			Thread.sleep(10);
			
				if (!testCase.action.actionName.equals("Wait")) {
					action = null;
					action = StepExecution.getAction(testCase);
					action.InitStep();
					action.Perform();
				}
				Thread.sleep(10);
				if (testCase.action.actionName.equals("Click")
						&& testCase.action.fieldName.equals("Button_Logon")) {

					if (testCase.browser.wait.WaitElementPresent(By
							.id("selectProfileForm")) != null) {

						loggingIn = false;
						break;
					} else {
						
						RestartTestException ex = new RestartTestException("Login failure");
						throw ex;
					}
					
					
				}
				testCase.moveToNextStep();
			}
		
		}
		catch (Exception e) {

				RestartTestException ex = new RestartTestException("Login failure");

				
					throw ex;
					
				
			
			
		}

		Thread.sleep(3000);

	}

	@Override
	protected String StepSuccessMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
