package com.drc.wtf.test_management;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITest;

import com.drc.wtf.object_repository.ObjectRepository;

import DRC.AutomationFramework.Excel.WorkBook;
import DRC.AutomationFramework.FileUtilities.FileUtilities;
import DRC.AutomationFramework.WebDriver.DriverSetup;

public class TestSuite implements ITest
{
    private WorkBook         workBook;
    private String           currentTestSuiteName;

    private String           testCaseWorkSheet;
    private String           repositoryWorkSheet;



    private String           testResultsFolderPath;

    private ObjectRepository repository;

    private TestConfigInfo   suiteConfig;
    private DriverSetup      driverSetup;

    // private TestCase testCase; // = new TestCase();

    private String repoSheetName;
 
    public TestSuite(String testSheetName, String repositorySheetName, DriverSetup browserSetup)
    {
        driverSetup = browserSetup;
        repoSheetName = repositorySheetName;
        suiteConfig = new TestConfigInfo(browserSetup);
        String workingFile =
                FileUtilities.copyFile(suiteConfig.configurationFile,
                        suiteConfig.testResultsFolderPath);

        /*
         * Opens the workbook and reads in the object repository Closes it once it is done Leave
         * above the code for opening the TestSuite Or the file won't be able to be opened
         */

        repository = new ObjectRepository(workingFile, repoSheetName);

        workBook = new WorkBook(workingFile);
        testCaseWorkSheet = testSheetName;



    }



    public void finished()
    {


        workBook.close();
    }



    public void updateTestResults(String status, TestCase testCase)
    {
        List<String> testCaseRow = workBook.activeSheet.getRowByIndex(testCase.testNumber);
        testCaseRow.set(2, status);
        workBook.activeSheet.updateRow(testCase.testNumber, testCaseRow); // updateCurrentRow(testCaseRow);

    }


    public void updateTestResults(String status)
    {

        workBook.SetActiveSheet(testCaseWorkSheet);

        List<String> testCaseRow = workBook.activeSheet.getCurrentRow();
        testCaseRow.set(2, status);
        workBook.activeSheet.updateCurrentRow(testCaseRow);

    }


    // Returns next test case to excecute
    public TestCase GetNextTestCaseToRun()
    {
        workBook.SetActiveSheet(testCaseWorkSheet);

        TestCase testCase = null;

        // Loop while there are still test case rows to iterate and the current test should not be
        // run
        while (workBook.activeSheet.moveToNextRow())
        {

            testCase = null;
            List<String> testCaseRow = workBook.activeSheet.getCurrentRow();

            if (testCaseRow.size() > 2)
            {
                if (testCaseRow.get(2).trim().toUpperCase().equals("YES"))
                {
                    testCase =
                            new TestCase(testCaseRow, workBook.activeSheet.getRowIndex(),
                                    repository, driverSetup);
                }
            }

            if (testCase != null && testCase.runTest)
            {

                break;
            }

        }

        // If there are no more test cases to run return null



        return testCase;
    }

    public ArrayList<TestCase> GetAllTestCase(boolean loadTestSteps)
    {
        if (!workBook.SetActiveSheet(testCaseWorkSheet))
        {
            return null;
        }

        TestCase testCase = null;
        ArrayList<TestCase> testList = new ArrayList();

        // Loop while there are still test case rows to iterate and the current test should not be
        // run
        while (workBook.activeSheet.moveToNextRow())
        {

            testCase = null;
            List<String> testCaseRow = workBook.activeSheet.getCurrentRow();

            if (testCaseRow.size() > 3)
            {

                testCase =
                        new TestCase(testCaseRow, workBook.activeSheet.getRowIndex(), repository,
                                driverSetup, loadTestSteps);
               
               
                testList.add(testCase);
                
            }
            //
            // if (testCase != null && testCase.runTest)
            // {
            //
            // break;
            // }

        }

        // If there are no more test cases to run return null



        return testList;
    }

    //This method is only intended to be used when walking through a test case
    public void RefreshObjectRepository()
    {
    	 String workingFile =
                 FileUtilities.copyFile(suiteConfig.configurationFile,
                         suiteConfig.testResultsFolderPath, "updateRepo.xls");
    	repository = new ObjectRepository(workingFile, repoSheetName);
    }
    
    
    public ObjectRepository getObjectRepository()
    {
    	return repository;
    }
    
    @Override
    public String toString()
    {

        StringBuilder builder = new StringBuilder();
        builder.append("TestSuite=").append(this.currentTestSuiteName);
        return builder.toString();
    }


    @Override
    public String getTestName()
    {
        return this.toString();
    }

}
