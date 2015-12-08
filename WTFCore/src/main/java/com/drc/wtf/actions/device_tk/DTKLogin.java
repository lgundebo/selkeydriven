package com.drc.wtf.actions.device_tk;



import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.drc.wtf.actions.*;
import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.base.ClickBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.execution.StepExecution;
import com.drc.wtf.test_management.BaseTestCase;
import com.drc.wtf.test_management.BuildTestSuite;
import com.drc.wtf.test_management.TestCase;

import DRC.AutomationFramework.WebDriver.DriverSetup;
public class DTKLogin extends DTKBase
{
	//private TestCase	LogintestCase;
	private BaseTestCase bTestCase;
	private DriverSetup	driverSetup;
	private By Login = By.linkText("Log On");
	private By username = By.cssSelector("#ctl00_LoginView1_LogOnPopUp1_popLogOn_ASPxPanel1_txtEmail");
	private By Pwd = By.cssSelector("#ctl00_LoginView1_LogOnPopUp1_popLogOn_ASPxPanel1_txtPassword");
	private By LogonButton = By.cssSelector("#ctl00_LoginView1_LogOnPopUp1_popLogOn_ASPxPanel1_btnLogon_CD");
	private By GeneralInfo = By.cssSelector("#ctl00_navPane_paneContent_SharedAjaxNavigationascx_AppNavigationMenu_GHE0");
	private By TestSetup = By.cssSelector("#ctl00_navPane_paneContent_SharedAjaxNavigationascx_AppNavigationMenu_GHC6 > table > tbody > tr > td.dxnb");
	private By DTK = By.linkText("Device Toolkit");

public DTKLogin(TestCase testCase)
{
	super(testCase);
	//LogintestCase = new TestCase(BuildTestSuite.testCaseList.get(1),bTestCase.driverSetup);
	
}

@Override
public void Perform() throws TestStepFailureException, InterruptedException {
	
	Boolean loggingIn = true;
	ActionBase action; 
	
	//Click Login
	  testCase.browser.driver().findElement(Login).click();
	//Enter Username
	 testCase.browser.driver().findElement(username).sendKeys("rilluri@datarecognitioncorp.com");
	//Enter Password
	  testCase.browser.driver().findElement(Pwd).sendKeys("Testing123");
	 //Click Signon
	  testCase.browser.driver().findElement(LogonButton).click();
	 //Click General Info
	  
	  testCase.browser.wait.WaitElementPresent(GeneralInfo);
	  testCase.browser.driver().findElement(GeneralInfo).click();
	  //Click TestSetup
	  testCase.browser.wait.WaitElementPresent(TestSetup);
	  testCase.browser.driver().findElement(TestSetup).click();
	  //Click DTK
	  testCase.browser.wait.WaitElementPresent(DTK);
	  testCase.browser.driver().findElement(DTK).click();
	  testCase.browser.driver().findElement(By.id("main-district-select"));
	  	
	/*while(loggingIn)
	{
		
	//	testCase.action.fieldName = "";
//		elementExist =testCase.setElement.SettingWebelement(testCase, testCase.element.fieldProperty,testCase.element.fieldProperty1);
		/*if(!LogintestCase.action.actionName.equals("Wait"))
		{
		action = null;
		action = StepExecution.getAction(LogintestCase);
    	action.InitStep();
		action.Perform();
		}
	
	
			if(testCase.browser.wait.WaitElementPresent(By.id("main-district-select")) != null)
			{
			
			loggingIn = false;
			break;
			}
			else
			{
			//	testCase.RestartTestCase();
			}
		
			
		
			testCase.moveToNextStep();
	
	
	
	}*/
	
	
}

@Override
protected String StepSuccessMessage() {
	// TODO Auto-generated method stub
	return null;
}


	
	 
}
