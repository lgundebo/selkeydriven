package com.drc.wtf.execution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.drc.wtf.exceptions.ObjectRepositoryException;
import com.drc.wtf.test_management.TestCase;
import com.drc.wtf.test_management.TestSuite;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.HubURL;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Version;
import DRC.AutomationFramework.WebDriver.DriverSetup;

public class TestCaseExecution extends XmlSuite
{

	/**
	 *
	 */
	private static final long	serialVersionUID	= 244124010558163667L;
	private TestSuite			testSuite;

	DriverSetup					info				= new DriverSetup();

	public TestCaseExecution()
	{
	}

	public TestCaseExecution(DriverSetup setup)
	{
		info = setup;

		System.out.println("Thread Constructor: "
				+ Thread.currentThread().getId());
	}

	// Harness for testing plug-in factory later
	private DriverSetup getDriverObject() {
		DriverSetup a = new DriverSetup();
		a.browser = Browser.SAFARI;
		a.hubURL = HubURL.LOCALHOST.toString();
		a.platform = Platform.WINDOWS;
		a.targetBits = BrowserTargetBits.BIT_32;
		a.version = Version.NOTSPECIFIED.toString();
		return a;
	}

	@DataProvider(name = "TestCases")
	public Object[][] initialize() {
		System.out.println("Thread A " + Thread.currentThread().getId());
		// Values from config.properties
		Properties configFile = new Properties();
		try
		{
			configFile.load(WebTestFactory.class.getClassLoader()
					.getResourceAsStream("config.properties"));

		} catch (IOException e)
		{

			e.printStackTrace();
		}

		testSuite = new TestSuite("TestExecutionSheet", "ObjectReporsitory",
				getDriverObject());

		List<TestCase> testCaseList = new ArrayList();

		TestCase testCase;
		while ((testCase = testSuite.GetNextTestCaseToRun()) != null)
		{
			testCaseList.add(testCase);
		}

		Object[][] testCaseArray = new Object[testCaseList.size()][1];

		for (int i = 0; i < testCaseList.size(); i++)
		{
			testCaseArray[i][0] = testCaseList.get(i);

		}

		return testCaseArray;
	}

	@Test(dataProvider = "TestCases")
	public void TestCaseExecution(TestCase testCase, ITestContext context)
			throws Exception {

		// System.out.println("Thread B " + Thread.currentThread().getId() +
		// " Browser: " + info.browser);
		XmlTest xmlTest = context.getCurrentXmlTest();

		xmlTest.setName(testCase.testCaseName);

		boolean bRunningScriptwithVBSdriver = false;
		boolean bMamkeTestFailforNotCallingMethodFromCommon = false;

		bMamkeTestFailforNotCallingMethodFromCommon = false;

		try
		{

			testCase.logging().writeLogAndConsole(
					"Start executing Test Case : " + testCase.testCaseName
					+ " from Test Suite : " + testCase.testSuiteName);

			// Execute the test
			testCase.startBrowser();

			try
			{

				while (testCase.moveToNextStep())
				{

					StepExecution.ExecuteStep(testCase);
				}
			} catch (NoSuchElementException e)
			{
				testCase.SetStepStatusFailed(e);
				return;
			} catch (RuntimeException e)
			{

				testCase.SetStepStatusFailed(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally
			{
				if (testCase.GetTestStatus() == false)
				{
					Assert.fail("TestCase execution failed.");
				}

			}

			// Log End of Test case
			testCase.logging().writeLogAndConsole(
					"End executing Test Case : " + testCase.testCaseName
					+ " from Test Suite : " + testCase.testSuiteName);

			bMamkeTestFailforNotCallingMethodFromCommon = true;
		} catch (Exception e)
		{
			e.printStackTrace();

		} finally
		{

			testCase.browser.Quit();
			if (testCase.skipException != null)
			{

				throw testCase.skipException;
			} else if (testCase.GetTestStatus() == false)
			{
				testSuite.updateTestResults("Failed", testCase);
			} else
			{
				testSuite.updateTestResults("Passed", testCase);
			}

			if (bMamkeTestFailforNotCallingMethodFromCommon == false)
			{

				testCase.logging()
				.writeLogAndConsole(
						"Script was unable to call any method from the common file");
			}

			if (testCase.GetTestStatus() == false)
			{
				Assert.fail("TestCase execution failed.");
			}

		}
		System.out.println("Ending Thread B " + Thread.currentThread().getId()
				+ " Browser: " + info.browser);
	}

	@AfterClass
	public void Close() {
		testSuite.finished();

	}

}
