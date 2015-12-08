package com.drc.wtf.test_management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.SkipException;

import com.drc.wtf.core.CaptureData;
import com.drc.wtf.core.RandomData;
import com.drc.wtf.core.ScreenShot;
import com.drc.wtf.exceptions.TestCaseFailureException;
import com.drc.wtf.execution.SetElement;
import com.drc.wtf.object_repository.Element;
import com.drc.wtf.object_repository.ObjectRepository;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import DRC.AutomationFramework.WebDriver.DriverSetup;
import DRC.AutomationFramework.WebDriver.RemoteDriver;

public class TestCase implements Cloneable
{
	private Boolean           testPass      = true;
    private Boolean           stepPass      = true;
    public SkipException 	skipException = null;
    public String            testCaseName;
    public String            testSuiteName;

   

    public int               testNumber;

    public Boolean           runTest;
    
    public Boolean			runParallel;

  //  private RandomData       randomData    = new RandomData();
    private CaptureData      captureData   = new CaptureData();

    public TestConfigInfo    configInfo;
    public RemoteDriver      browser;
    public StringBuffer      failedMessage = new StringBuffer();

    private ScreenShot       screenShot;
    private Log              logging;
    private ObjectRepository repository;

    // Track test current step
    public int               currentStep   = 0;                 // will change to private
    private int 			 nextStep      =0;
    public List<Action>      testSteps;
    public Action            action        = null;
    public Element           element       = null;
    public SetElement 		 setElement;
    public DriverSetup      driverSetup;

    private Action           newAction;

    public TestCase(List<String> testCaseRow, int rowIndex, ObjectRepository objectRepository,
            DriverSetup setup)
    {
    	this.createTestCase(testCaseRow, rowIndex, objectRepository, setup, true);
       
    }

    public TestCase(List<String> testCaseRow, int rowIndex, ObjectRepository objectRepository,
            DriverSetup setup, Boolean populateTestSteps)
    {
    	this.createTestCase(testCaseRow, rowIndex, objectRepository, setup, populateTestSteps);
       
    }
    
    
    public TestCase(TestCase testCase, DriverSetup setup)
    {
    	driverSetup = setup;

        testCaseName = testCase.testCaseName; // testCaseRow.get(1).trim();

        testSuiteName = testCase.testSuiteName; //testCaseRow.get(0).trim();

        runTest = testCase.runTest; //testCaseRow.get(2).trim().toUpperCase().equals("YES");
 
        this.repository = new ObjectRepository(testCase.repository);
        
        
        
        configInfo = new TestConfigInfo(setup, this);

        screenShot = new ScreenShot(configInfo.screenShotDirectroy);
        logging = new Log(configInfo.testLogFile);
        
       
        //Clone test steps
        Iterator itr = testCase.testSteps.iterator();
        this.testSteps = new ArrayList<Action>();
        while (itr.hasNext())
        {
            Action act = (Action) itr.next();
            try {
				this.testSteps.add((Action) act.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        
        
        
        
    }
    
    public void startBrowser() throws TestCaseFailureException
    {
    	if(testSteps.size() >0)
    	{
    		
    	setElement = new SetElement(this);
    		
        this.browser = new RemoteDriver(driverSetup);
       // this.browser.Navigate(this.configInfo.url);
    	}
    	else
    	{
    		logging().writeLogAndConsole("There are no steps for test case: " + this.testCaseName);
    		TestCaseFailureException failure = new TestCaseFailureException("There are no steps for test case: " + this.testCaseName);
    		throw failure;
    		
    	}
    }

    
    public void navigateToStartURL() throws TestCaseFailureException
    {
    	
        this.browser.Navigate(this.configInfo.url);
    	
    }
    
    
    public void initalizeTestCase(ObjectRepository objectRepository,  DriverSetup setup)
    {
    	 // Make a copy of the object repository just for this test case
        repository = new ObjectRepository(objectRepository);

        configInfo = new TestConfigInfo(setup, this);

        screenShot = new ScreenShot(configInfo.screenShotDirectroy);
        logging = new Log(configInfo.testLogFile);

        // Create the test steps
     
        this.createTestSteps();
    }
    
    
    public void refreshTestCase(ObjectRepository objectRepository)
    {
    	repository = new ObjectRepository(objectRepository);
    	this.createTestSteps();
    }
    

    private void createTestSteps()
    {

        String fileName = this.configInfo.testCaseRepository + this.testSuiteName + ".xls";

        File inputWorkbook = new File(fileName);
        Workbook w = null;
        testSteps = new ArrayList<Action>();



        try
        {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(testCaseName);
            System.out.print("testCaseName::" + testCaseName + '\n');
           
            int iTotalRows = sheet.getRows();

            int i;

            // change it to while loop
            for (i = 1; i < iTotalRows; i++)
            {

                newAction = new Action();
                newAction.testStep = i;

                Cell cell = sheet.getCell(0, i);

                if (!cell.getContents().toString().equals(""))
                {
                    newAction.actionName = cell.getContents().toString().trim();

                    cell = sheet.getCell(1, i);
                    newAction.fieldName = cell.getContents().toString().trim();

                    cell = sheet.getCell(2, i);
                    newAction.fieldValue = cell.getContents().toString().trim();

                    cell = sheet.getCell(3, i);
                    newAction.pageName = cell.getContents().toString().trim();

                    cell = null;

                    testSteps.add(newAction);
                }
            }
        }
        catch (BiffException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(NullPointerException e)
        {
        	e.printStackTrace();
        	
        }
        finally
        {

            w.close();
            inputWorkbook = null;
        }
    }

    public boolean CheckForMoreSteps()
    {
    	  boolean moreSteps = false;
         
         
          if (testSteps.size() > currentStep +1)
          {
        	  moreSteps =true;
          }
          
          return moreSteps;
    }
    
    public void RestartTestCase()
    {
    	currentStep =0;
    	nextStep = 0;
    	
    }
    
    public boolean moveToNextStep()
    {

        boolean moreSteps = false;
        stepPass = true;
        currentStep = nextStep;
        if (testSteps.size() > currentStep)
        {

            action = testSteps.get(currentStep);
            // if the action is Dynamic update a hashmap entry for it
            if (this.action.fieldName.toUpperCase().contains("DYNAMIC") == true)
            {
                this.repository.updateDynamicValues(action);
            }

            element = this.repository.get(this.action.fieldName);

            nextStep++;
            moreSteps = true;
        }

        return moreSteps;
    }
    
    public String GetNextActionName()
    {
    	String nextAction = null;
    	
    	if (testSteps.size() > currentStep +1)
        {

           nextAction = testSteps.get(currentStep+1).actionName;
        }
    	
    	return nextAction;
    }
    
    

    public Element getElement(String key)
    {
        return this.repository.get(key);
    }

    public void updateActionRandomData()
    {
    	action = repository.updateActionRandomData(this.action);// updateActionRandom(this.action);
       // action = this.randomData.updateActionRandom(this.action);
    }

    public void updateDynamicData()
    {
        repository.updateDynamicValues(this.action);
    }

    public void updateActionCaptureData()
    {
    	action = captureData.updateActionCapture(this.action);
    	
    }
    
    public void captureData(String capturedWebText)
    {
        captureData.captureData(this.action, capturedWebText);

    }

    public String getCapturedData()
    {
        return captureData.sCaptureWebText;

    }
    
    

    public CaptureData GetCapturedDataObject()
    {
        return captureData;

    }
    
    public void takeErrorScreenShot()
    {
        String screenShotResult = screenShot.takeErrorScreenShot(this);
        logging.writeLogAndConsole("Screenshot saved at: "
        		+ System.getProperty("line.separator")
        		+ screenShotResult);
    }

    public Log logging()
    {
        if (action != null)
        {
            if (testSteps.size() > currentStep)
            {
            	// logging.setAction(action);
                logging.setAction(testSteps.get(currentStep));
            }
        }
        return logging;
    }

    public void SetStepStatusFailed(Exception e)
    {
    	testPass = false;
    	stepPass = false;
    	
    	
    	
    	failedMessage.append(e.getMessage());
    	
    	 
    	
    	this.logging().stepFailed(e);
    	
    	
		this.takeErrorScreenShot();
		
    }
    
    public boolean GetTestStatus()
    {
    	return testPass;
    }
    
    public boolean GetStepStatus()
    {
    	return stepPass;
    	
    }
    
    public void failMessage(String sText)
    {
        this.failedMessage.append(sText + '\n');
    }

    @Override
    public String toString()
    {

        StringBuilder builder = new StringBuilder();
        builder.append("TestSuite=").append(this.testSuiteName).append(", TestCase=")
        .append(this.testCaseName);
        return builder.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        TestCase cloned = (TestCase) super.clone();

        Iterator itr = this.testSteps.iterator();
        cloned.testSteps = new ArrayList<Action>();
        while (itr.hasNext())
        {
            Action act = (Action) itr.next();
            cloned.testSteps.add((Action) act.clone());
        }

        
        
        
        return cloned;

    }

    private void createTestCase(List<String> testCaseRow, int rowIndex, ObjectRepository objectRepository,
            DriverSetup setup, Boolean populateTestSteps)
    {
    	  driverSetup = setup;

          testCaseName = testCaseRow.get(1).trim();

          testSuiteName = testCaseRow.get(0).trim();

          runTest = testCaseRow.get(2).trim().toUpperCase().equals("YES");

         try
         {
        	 runParallel =  testCaseRow.get(3).trim().toUpperCase().equals("TRUE");
         }
         catch(Exception e)
         {
        	 runParallel = true;
         }
         

          testNumber = rowIndex;

          
          // Create the test steps
         if(populateTestSteps)
         {
          this.initalizeTestCase(objectRepository, setup);
         }
    }

}
