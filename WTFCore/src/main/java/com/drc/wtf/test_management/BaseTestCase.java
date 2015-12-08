/*
 *
 */
package com.drc.wtf.test_management;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.drc.wtf.exceptions.ObjectRepositoryException;
import com.drc.wtf.exceptions.RestartTestException;
import com.drc.wtf.exceptions.TestCaseFailureException;
import com.drc.wtf.execution.BrowserLaunch;
import com.drc.wtf.execution.NavigateBrowser;
import com.drc.wtf.execution.StepExecution;
import com.drc.wtf.execution.StepExecutor;

import DRC.AutomationFramework.WebDriver.DriverSetup;

public class BaseTestCase

{
	private TestCase testCase;

	private DriverSetup driverSetup;

	private Boolean testResultsReported = false;

	/**
	 * Before class.
	 * 
	 * @param browser
	 *            the browser
	 * @param platform
	 *            the platform
	 * @param version
	 *            the version
	 * @param profile
	 *            the profile
	 */
	@Parameters({ "browser", "platform", "version", "profile", "hubURL" })
	@BeforeClass
	public void beforeClass(String browser, String platform, String version,
			String profile, String hubURL) {

		driverSetup = new DriverSetup(platform, browser, version, profile,
				hubURL, "NOTAPPLICABLE");

	}

	@AfterMethod
	public void afterTest() {
		try {
			testCase.browser.Quit();
		} catch (Exception e) {
		}
	}

	private boolean launchBrowser() {
		Boolean successful = false;
		try {
			testCase.startBrowser();

			if (testCase.browser.driver() != null) {
				successful = true;
			}

		} catch (TestCaseFailureException e) {

			Assert.fail("TestCase failed because there were no test steps", e);

		} catch (Exception e) {
			successful = false;
		}

		return successful;
	}

	public void executeTest(String testname) {
		int index = 0;
		testResultsReported = false;
		for (int i = 0; i < BuildTestSuite.testCaseListAll.size(); i++) {
			if (BuildTestSuite.testCaseListAll.get(i).testCaseName
					.equals(testname)) {
				index = i;
				break;
			}
		}

		testCase = null;

		try {

			testCase = new TestCase(BuildTestSuite.testCaseListAll.get(index),
					driverSetup);
		} catch (Exception e1) {

			Assert.fail("Failed to build the test case " + testname, e1);
		}

		testCase.logging().writeLogAndConsole(
				"Start executing Test Case : " + testCase.testCaseName
						+ " from Test Suite : " + testCase.testSuiteName);

		/*
		 * Try to launch the browser retry on failure
		 * 
		 * Executing in it's own thread
		 * after ten minutes the thread will 
		 * be interrupted by the main thread and the test will exit
		 * 
		 */

		BrowserLaunch browserLauncher = new BrowserLaunch(testCase);
		Thread browserThread = new Thread(browserLauncher);
		browserThread.start();
		try {
			browserThread.join(1000*60*10); //10 Minutes
		
		
		if(browserLauncher.failTestCase)
		{
			browserThread.interrupt();
			browserThread.sleep(1000);
			browserThread.join(10000);
			
			Assert.fail("TestCase execution failed.", browserLauncher.testCaseFailure);
		}
		}
		catch (InterruptedException e1) {
			Assert.fail("TestCase execution failed: Launching the Browser", e1);
		} 
				
				

		
		

		/*
		 * 
		 * Navigate the browser to the startup URL from the Config script
		 */

		
		
		NavigateBrowser browserNavigator = new NavigateBrowser(testCase);
		Thread navThread = new Thread(browserNavigator);
		navThread.start();
		try {
			navThread.join(1000*60*5); //10 Minutes
		
		
		if(browserNavigator.failTestCase)
		{
			navThread.interrupt();
			navThread.sleep(1000);
			navThread.join(10000);
			
			Assert.fail("TestCase execution failed.", browserLauncher.testCaseFailure);
		}
		}
		catch (InterruptedException e1) {
			Assert.fail("TestCase execution failed: Navigating to the URL", e1);
		} 
				
				
		
		
		
		
		try {
			testCase.navigateToStartURL();
		} catch (Exception e) {
			String message = "Failed to navigate to the Startup URL";
			testCase.logging().TestFailed(e, message);

			testCase.logging().writeLogAndConsole(
					"TestCase Failed: " + testCase.testCaseName);
			testCase.logging().writeLogAndConsole(
					"End executing Test Case : " + testCase.testCaseName
							+ " from Test Suite : " + testCase.testSuiteName);

			TestCaseFailureException ex = new TestCaseFailureException(
					testCase.logging());
			Assert.fail("TestCase execution failed.", ex);
		}

		
		
		
		/*
		 * Start Executing the test case
		 */
		testCase.currentStep = 0;

		boolean bMamkeTestFailforNotCallingMethodFromCommon = false;

		StringBuffer failedSteps = new StringBuffer("The Failed Steps are ");

		int fStepCnt = 0;

		try {
			StepExecutor executor = new StepExecutor(testCase);
			while (testCase.CheckForMoreSteps()) {

				testCase.moveToNextStep();

				
				Thread stepThread = new Thread(executor);
				stepThread.start();
				stepThread.join(1000*60*30); //30 Minutes
				
				
				if(executor.getRestartTestException() != null)
				{
					throw executor.getRestartTestException();
				}
				else if(!executor.stepFinished)
				{
					
						stepThread.interrupt();
					    stepThread.join(10000);
						stepThread = null;
					    
						throw new Exception("Step execution timed out after 30 minutes");
						
				}
					
				//StepExecution.ExecuteStep(testCase);

				if (testCase.GetStepStatus() == false) {
					fStepCnt++;
					failedSteps.append(testCase.action.testStep + ", " + '\n');
					if (fStepCnt > 3) {
						failedSteps.insert(0,
								"More Than 3 steps failed. Aborting the test ");
						testCase.logging().writeLogAndConsole(
								("***Aborting Test***" + System
										.getProperty("line.separator"))
										.toString().concat(
												failedSteps.toString()));
						break;
					}
				}

				bMamkeTestFailforNotCallingMethodFromCommon = true;
			}

		} 
		catch(RestartTestException ex)
		{
			try{
				testCase.browser.driver().quit();
			}
			catch(Exception e)
			{}
			
			testCase.logging().writeLogAndConsole(
					"Restarting Test Case : " + testCase.testCaseName
					+ " from Test Suite : " + testCase.testSuiteName);
			
			executeTest(testname);
			return;
		}
		catch (Exception ex) {

				testCase.SetStepStatusFailed(ex);
				ex.printStackTrace();
			

		} finally {
			if (!testResultsReported) {
				testResultsReported = true;
				if (testCase.skipException != null) {

					throw testCase.skipException;
				} else if (testCase.GetTestStatus() == false) {
					BuildTestSuite.testSuite.updateTestResults("Failed",
							testCase);
				} else {
					BuildTestSuite.testSuite.updateTestResults("Passed",
							testCase);
				}

				if (bMamkeTestFailforNotCallingMethodFromCommon == false) {

					testCase.logging()
							.writeLogAndConsole(
									"Script was unable to call any method from the common file");

				}

				if (testCase.GetTestStatus() == false) {
					testCase.logging().writeLogAndConsole(
							"TestCase Failed: " + testCase.testCaseName);

					try {
						TestCaseFailureException e = new TestCaseFailureException(
								testCase.logging());
						throw e;
					} catch (TestCaseFailureException b) {
						Assert.fail("TestCase execution failed." + failedSteps,
								b);
					}

				}
				if (testCase.GetTestStatus() == true) {
					testCase.logging().writeLogAndConsole(
							"TestCase Passed: " + testCase.testCaseName);
				}

				// Log End of Test case
				testCase.logging().writeLogAndConsole(
						"End executing Test Case : " + testCase.testCaseName

						+ " from Test Suite : " + testCase.testSuiteName);
			}

		}

	}

}
