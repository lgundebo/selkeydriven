/*
 *
 */
package com.drc.wtf.execution;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;



import org.testng.SkipException;

import DRC.AutomationFramework.WebDriver.DriverSetup;
import DRC.AutomationFramework.WebDriver.RemoteDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.iat.Login;
import com.drc.wtf.core.CaptureData;
import com.drc.wtf.core.CodeTimer;
import com.drc.wtf.core.ScreenShot;
import com.drc.wtf.exceptions.ObjectRepositoryException;
import com.drc.wtf.exceptions.RestartTestException;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.exceptions.TestTimeOutException;
import com.drc.wtf.object_repository.Element;
import com.drc.wtf.object_repository.ObjectRepository;
import com.drc.wtf.test_management.Action;
import com.drc.wtf.test_management.TestCase;
import com.drc.wtf.test_management.TestConfigInfo;


// TODO: Auto-generated Javadoc
/**
 * The Class StepExecution.
 */
public class StepExecution
{


	
    /**
     * Execute step.
     *
     * @param testCase the test case
     * @throws RestartTestException 
     * @throws ObjectRepositoryException the object repository exception
     */
    public static void ExecuteStep(TestCase testCase) throws RestartTestException //throws ObjectRepositoryException
    {
    	Boolean timeCode = false;
    	ActionBase action = null;
    	CodeTimer timer = new CodeTimer();
    	CodeTimer initTimer = new CodeTimer();
    	CodeTimer actionTimer = new CodeTimer();
    	timer.start();
    	
    	testCase.browser.wait.PageLoad();

        
        //System.out.print("THREAD" + Thread.currentThread().getId() + "::"
           //     + testCase.browser.driver().toString() + "::STEP" + testCase.currentStep + '\n');

        
        if (testCase.action.testStep == 6  )
        {
           
        	int a =5;
        	a+=5;
        }
        

        if(testCase.action.actionName.equals("Click") && testCase.action.fieldName.equals("Link_LogOn"))
        {
        	Login IATLogin = new Login(testCase);
        	try {
				IATLogin.Perform();
			} 
        	catch(RestartTestException e)
            {
        		 if(e.restartTestCase)
           	   {
           		   throw e;
           	   }
           	   else
           	   {
           		  testCase.SetStepStatusFailed(e);
           	   }
            }
        	catch (TestStepFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	catch (InterruptedException e)
        	{
        		return;
        	}
        	return;
        }
        try
        {
       
        	if(testCase.action.actionName.equals(""))
        	{
            String sExistNotExist = testCase.action.fieldValue.trim().toUpperCase();
            if (sExistNotExist.toUpperCase().equals("NOTEXIST"))
            {
                testCase.logging().writeLogAndConsole(
                        "Step # " + testCase.action.testStep + "-->"
                                + testCase.action.actionName
                                + ">> Field does not exist in the field in the page '"
                                + testCase.action.pageName + "'");
            }
            else
            {
               
                     String message = "Step # " + testCase.action.testStep + "-->"
                                + testCase.action.actionName
                                + ">> Field exists in the field in the page '"
                                + testCase.action.pageName + "'";


                    TestStepFailureException failure = new TestStepFailureException(message);
                    throw failure;
                                
            }
       }
            else
            {
            	
        	
        	try 
        	{
				action = getAction(testCase);
			} 
        	catch (TestStepFailureException e1) 
			{
				String message =
                        "Step # " + testCase.action.testStep + "-->"
                                + testCase.action.actionName
                                + ">> is not a keyword in the Testing Framework" + "\r\n"
                                + "Step # " + testCase.action.testStep + "--> Test failed";


                TestStepFailureException failure = new TestStepFailureException(message);
                throw failure;
				
			}
        	
        	
				initTimer.start();
				action.InitStep();
				initTimer.stop();
				
				if(timeCode)
				{
				testCase.logging().writeLogAndConsole("Init took: " + initTimer.getTime());
				}
				
				actionTimer.start();
				action.Perform();
				actionTimer.stop();
			
				if(timeCode)
				{
				testCase.logging().writeLogAndConsole("Action took: " + actionTimer.getTime());
				}
				
        	
        	return;
        }
        
            
                     
                    /*
                    default:
                        
                        message =      "Step # " + testCase.action.testStep + "-->Either "
                                        + testCase.action.actionName + ">> '"
                                        + testCase.action.fieldName
                                        + "'does not exist in the page '"
                                        + testCase.action.pageName + "'" + "\r\n Or "
                                        + testCase.action.actionName
                                        + " is not a key in the Testing Framework." + "\r\n"
                                        + "Step # " + testCase.action.testStep + "--> Test failed";
                                       
                                        failure = new TestStepFailureException(message);
                                        throw failure;
                                        */
             
        }
        
        catch(TestStepFailureException | WebDriverException e)
        {
     	   RestartTestException ex = new RestartTestException(e);
        	
     	   if(ex.restartTestCase)
     	   {
     		   throw ex;
     	   }
     	   else
     	   {
     		  testCase.SetStepStatusFailed(e);
     	   }
        }
        /*
        catch(NoSuchElementException e )
        {
        	testCase.SetStepStatusFailed(e);
        }
      */
        catch (RuntimeException e)
		{
			testCase.SetStepStatusFailed(e);
			
		}        
      
        catch (Exception e)
        {
        	testCase.SetStepStatusFailed(e);
        	
        }
   
    
       timer.stop();
	 
       if(timeCode)
       {
       testCase.logging().writeLogAndConsole("This step took " + timer.getTime() +" seconds inside step execution.");
       }
    }


   
   
    
    public static ActionBase getAction(TestCase testCase) throws TestStepFailureException
    {
    	String actionName = testCase.action.actionName;
    	ActionBase action = null;
    	
    	String className = "com.drc.wtf.actions.";
    	className += actionName;
    	
    	try {
    	Class cls = Class.forName(className);
    	Constructor constructor;
		
			constructor = cls.getConstructor(TestCase.class);
			action = (ActionBase)constructor.newInstance(testCase);
		} catch (NoSuchMethodException 
				| SecurityException 
				| ClassNotFoundException 
				| InstantiationException 
				| IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			
			TestStepFailureException failure = new TestStepFailureException("Failed to retrive base action." +
					System.getProperty("line.separator") +
					e.getMessage());
			throw failure;
		}
    	
    	return action;
    	
    }
    
}
