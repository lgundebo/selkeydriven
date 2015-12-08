package com.drc.wtf.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import com.drc.wtf.test_management.TestCase;
import com.drc.wtf.test_management.TestSuite;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.Browser;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.BrowserTargetBits;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.HubURL;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Platform;
import DRC.AutomationFramework.WebDriver.DriverEnumerators.Version;
import DRC.AutomationFramework.WebDriver.DriverSetup;

public class ConsoleExecution extends XmlSuite
{

	/**
	 *
	 */
	private static final long	serialVersionUID	= 244124010558163667L;
	private TestSuite			testSuite;

	DriverSetup					info				= new DriverSetup();

	public ConsoleExecution()
	{
	}

	public ConsoleExecution(DriverSetup setup)
	{
		info = setup;

		System.out.println("Thread Constructor: "
				+ Thread.currentThread().getId());
	}

	// Harness for testing plug-in factory later
	private DriverSetup getDriverObject() {
		DriverSetup a = new DriverSetup();
		a.browser = Browser.CHROME;
		// a.hubURL = HubURL.LOCALHOST;
		a.hubURL = HubURL.LOCALHOST.toString();
		a.platform = Platform.WINDOWS;
		a.targetBits = BrowserTargetBits.BIT_32;
		a.version = Version.NOTSPECIFIED.toString();
		// a.version = Version.NOTSPECIFIED;
		return a;
	}

	@DataProvider(name = "TestCases")
	public Object[][] initialize() {

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

		int stepToRunUntil = 0;

		// Set TestCase Name
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

					try
					{

						StepExecution.ExecuteStep(testCase);
					} catch (Exception e)
					{
						stepToRunUntil = 0;
					} finally
					{
						if (stepToRunUntil < testCase.currentStep)
						{
							int selection = menu();
							// stepToRunUntil = handleUserInput(testCase,
							// selection);

							if (selection == 1)
							{
							} else if (selection == 2)
							{
								int currentStep = testCase.currentStep - 1;
								testSuite.RefreshObjectRepository();

								testCase.refreshTestCase(testSuite
										.getObjectRepository());

								testCase.currentStep = currentStep;
							} else if (selection == 3)
							{
								stepToRunUntil = getStepToRunUntil();
							} else if (selection == 4)
							{
								stepToRunUntil = testCase.testSteps.size() + 1;
							} else if (selection == 5)
							{
								testSuite.RefreshObjectRepository();

								testCase.refreshTestCase(testSuite
										.getObjectRepository());
								selection = menu();
							} else if (selection == 6)
							{
								testCase.browser.Quit();
								Close();
								System.exit(0);
							}

						}
					}

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
			}

			finally
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

			if (testCase.GetTestStatus() == false)
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

	private static int menu() {
		System.out.println("Options are (enter the number):");
		System.out.println("1. Next Step");
		System.out.println("2. Rerun Step");
		System.out.println("3. Run until a particular step");
		System.out.println("4. Run until the end");
		System.out.println("5. Refresh TestCase & ObjectRepository");
		System.out.println("6. exit");

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		String input = null;

		try
		{
			input = bufferedReader.readLine();
		} catch (IOException e1)
		{

			e1.printStackTrace();
		}

		// String input = System.console().readLine();
		int inputValue = 0;
		try
		{
			inputValue = Integer.parseInt(input);
		} catch (Exception e)
		{
			inputValue = menu();
		} finally
		{
			if (inputValue > 6 || inputValue < 1)
			{
				System.out
						.println(input
								+ " is not a valid input.  Please choose from the following options:");
				inputValue = menu();
			}
		}

		return inputValue;

	}

	/*
	 * System.out.println("1. Next Step"); System.out.println("2. Rerun Step");
	 * System.out.println("3. Run until a particular step");
	 * System.out.println("4. Run until the end");
	 * System.out.println("5. exit");
	 */

	private int handleUserInput(TestCase testCase, int selection) {
		int stepToRun = 0;

		if (selection == 1)
		{
		} else if (selection == 2)
		{
			int currentStep = testCase.currentStep - 1;
			testSuite.RefreshObjectRepository();

			testCase.refreshTestCase(testSuite.getObjectRepository());

			testCase.currentStep = currentStep;
		} else if (selection == 3)
		{
			stepToRun = getStepToRunUntil();
		} else if (selection == 4)
		{
			stepToRun = testCase.testSteps.size() + 1;
		} else if (selection == 5)
		{
			testSuite.RefreshObjectRepository();

			testCase.refreshTestCase(testSuite.getObjectRepository());
		} else if (selection == 6)
		{
			System.exit(0);
		}

		return stepToRun;

	}

	private int getStepToRunUntil() {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		String input = null;

		System.out.println("What step would you like to run until:");

		try
		{
			input = bufferedReader.readLine();
		} catch (IOException e1)
		{

			e1.printStackTrace();
		}

		// String input = System.console().readLine();
		int inputValue = 0;
		try
		{
			inputValue = Integer.parseInt(input);
		} catch (Exception e)
		{
			inputValue = getStepToRunUntil();
		}

		return inputValue;
	}

}
