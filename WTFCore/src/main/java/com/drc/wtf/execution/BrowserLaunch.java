package com.drc.wtf.execution;

import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import com.drc.wtf.exceptions.TestCaseFailureException;
import com.drc.wtf.test_management.TestCase;

public class BrowserLaunch implements Runnable {

	private TestCase testCase;
	
	public TestCaseFailureException testCaseFailure =null;
	public Boolean failTestCase = true;
	
	public BrowserLaunch(TestCase _testCase)
	{
		testCase = _testCase;
	}
	
	
	@Override
	public void run() {
		
		testCaseFailure =null;
		failTestCase = true;
		
		/*
		 * Try to launch the browser retry on failure
		 */
		
		Boolean launchOk = false;
		int launchAttempts = 0;
		do {
			launchOk = this.launchBrowser();
			launchAttempts++;
			if (!launchOk) {
				
				this.terminateBrowser();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					this.terminateBrowser();
					e.printStackTrace();
					break;
				}

			}

		} while (!launchOk && launchAttempts <= 5);

		/*
		 * If the Browser failed to launch after five attempts fail the test
		 */

		if (!launchOk) {

			String message;
			if (testCase.browser != null) {
				message = testCase.browser.GetDriverInfo();
			} else {
				message = "Driver is null";
			}

			TestCaseFailureException ex = new TestCaseFailureException(
					testCase.logging());

			testCase.logging().TestFailed(ex, message);
			testCase.logging().writeLogAndConsole(
					"TestCase Failed: " + testCase.testCaseName);
			testCase.logging().writeLogAndConsole(
					"End executing Test Case : " + testCase.testCaseName
							+ " from Test Suite : " + testCase.testSuiteName);

			
			failTestCase= true;
			testCaseFailure = ex;
			//Assert.fail("TestCase execution failed.", ex);
		}
		else
		{
			failTestCase = false;
		}
		
	}
	
	
	private void terminateBrowser() {
	try{
		testCase.browser.driver().quit();
	}
	catch(Exception e)
	{}
	
	}
	
	
	
	private boolean launchBrowser() {
		Boolean successful = false;
		try {	
			testCase.startBrowser();
			if (testCase.browser.driver() != null) {
				successful = true;
			}

		} catch (TestCaseFailureException e) {

			successful =false;
			testCaseFailure = e;
			
		} 
		catch (WebDriverException e)
		{
			testCase.logging().writeLogAndConsole(e.getLocalizedMessage());
		}
		catch (Exception e) {
			
			
			
			successful = false;
		}

		return successful;
	}

}
