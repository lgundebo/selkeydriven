package com.drc.wtf.execution;

import org.testng.Assert;

import com.drc.wtf.exceptions.TestCaseFailureException;
import com.drc.wtf.test_management.TestCase;

public class NavigateBrowser implements Runnable {

	private TestCase testCase;
	
	public TestCaseFailureException testCaseFailure =null;
	public Boolean failTestCase = true;
	
	public NavigateBrowser(TestCase _testCase)
	{
		testCase = _testCase;
	}
	
	
	@Override
	public void run() {
		
		failTestCase = true;
		testCaseFailure = null;
		try 
		{
			testCase.navigateToStartURL();
		} 
		catch (Exception e) 
		{
			String message = "Failed to navigate to the Startup URL";
			testCase.logging().TestFailed(e, message);

			testCase.logging().writeLogAndConsole(
					"TestCase Failed: " + testCase.testCaseName);
			testCase.logging().writeLogAndConsole(
					"End executing Test Case : " + testCase.testCaseName
							+ " from Test Suite : " + testCase.testSuiteName);

			TestCaseFailureException ex = new TestCaseFailureException(
					testCase.logging());
			
			testCaseFailure = ex;
			failTestCase = true;
			Assert.fail("TestCase execution failed.", ex);
		}
		if(testCaseFailure == null)
		{
		failTestCase = false;
		}
	}
	
	
	
	

}
