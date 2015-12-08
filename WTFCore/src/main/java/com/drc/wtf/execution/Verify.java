package com.drc.wtf.execution;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.drc.wtf.actions.interactions.WTFWebElement;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;

public class Verify
{


    public static void CompareEqualOrNot(TestCase testCase) throws InterruptedException, TestStepFailureException
    {
        int UnderScoreIndex = testCase.action.fieldValue.indexOf(":");
        int Field1Actualvalue = 0, Field2Actualvalue = 0;
        String sFieldName;
        boolean EqualTrueFalse;

        sFieldName = testCase.action.fieldValue.substring(0, UnderScoreIndex).trim();
        testCase.action.fieldValue =
                testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();

        UnderScoreIndex = testCase.action.fieldValue.indexOf("&");
        String Field1Value = testCase.action.fieldValue.substring(0, UnderScoreIndex).trim();
        String Field2Value = testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();


        if (Field1Value.contains("TableRowCountNum1"))
        {
            Field1Actualvalue = testCase.GetCapturedDataObject().iCountTableRowCount1;
        }
        else if (Field1Value.contains("TableRowCountNum2"))
        {
            Field1Actualvalue = testCase.GetCapturedDataObject().iCountTableRowCount2;
        }
        else if (Field1Value.contains("TableRowCountNum3"))
        {
            Field1Actualvalue = testCase.GetCapturedDataObject().iCountTableRowCount3;
        }
        else if (Field1Value.contains("TableRowCountNum4"))
        {
            Field1Actualvalue = testCase.GetCapturedDataObject().iCountTableRowCount4;
        }

        if (Field2Value.contains("CaptureWebTextIntoInteger1"))
        {
            Field2Actualvalue = testCase.GetCapturedDataObject().iCaptureWebTextIntoInteger1;
        }
        else if (Field2Value.contains("CaptureWebTextIntoInteger2"))
        {
            Field2Actualvalue = testCase.GetCapturedDataObject().iCaptureWebTextIntoInteger2;
        }
        else if (Field2Value.contains("CaptureWebTextIntoInteger3"))
        {
            Field2Actualvalue = testCase.GetCapturedDataObject().iCaptureWebTextIntoInteger3;
        }
        else if (Field2Value.contains("CaptureWebTextIntoInteger4"))
        {
            Field2Actualvalue = testCase.GetCapturedDataObject().iCaptureWebTextIntoInteger4;
        }


        if (Field1Actualvalue == Field2Actualvalue)
        {
            EqualTrueFalse = true;
        }
        else
        {
            EqualTrueFalse = false;
        }

        if (testCase.action.actionName.equals("CompareEqual"))
        {
            if (EqualTrueFalse == true)
            {
                testCase.logging().writeLogAndConsole(
                        "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                                + ">> Total number rows and items for the fiels '" + sFieldName
                                + "' are :" + "equal" + "  in the page '"
                                + testCase.action.pageName + "'");
            }
            else
            {


                
                 String message = "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                                + ">> Total number rows and items for the fiels '" + sFieldName
                                + "' are not :" + "equal" + "  in the page '"
                                + testCase.action.pageName + "'"
                                + " Field1Value = " +Field1Actualvalue
                                + " Field2Value = " + Field2Actualvalue;
                                
                  TestStepFailureException failure = new TestStepFailureException(message);
                  throw failure;
            }
        }

        if (testCase.action.actionName.equals("CompareNotEqual"))
        {
            if (EqualTrueFalse == false)
            {
                testCase.logging().writeLogAndConsole(
                        "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                                + ">> Total number rows and items for the fiels '" + sFieldName
                                + "' are not :" + "equal" + "  in the page '"
                                + testCase.action.pageName + "'");
            }
            else
            {
               
                  String message =      "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                                + ">> Total number rows and items for the fiels '" + sFieldName
                                + "' are :" + "equal" + "  in the page '"
                                + testCase.action.pageName + "'"
                                + " Field1Value = " +Field1Actualvalue
                                + " Field2Value = " + Field2Actualvalue;
                
                  TestStepFailureException failure = new TestStepFailureException(message);
                  throw failure;
            }
        }
    }


    public static void VerifyOrClickRowText(TestCase testCase) throws InterruptedException, TestStepFailureException
    {
        try
        {
            String sExistNotExist = null;
            if (testCase.action.actionName.equals("VerifyRowText"))
            {
                int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
                if (UnderScoreIndex != -1)
                {
                    sExistNotExist =
                            testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
                                    .toUpperCase();
                }
                else
                {
                    sExistNotExist = "";
                }
                testCase.action.fieldValue =
                        testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
            }

            if (testCase.action.fieldValue.contains("NewCommentRandom"))
            {
                testCase.updateActionRandomData();
            }

            boolean sFieldExists = false;

            // Verify if the web text exist on first page.
            sFieldExists = VerifyOrClickRowTextForSinglePage(testCase);

            // Verify if the web text exist on the other pages.
            while (!sFieldExists && testCase.browser.element.Exists(By.className("nextLink")) == true)
            {
            	
               
                	WTFWebElement link = new WTFWebElement(testCase.setElement.GetWebelement(testCase,"className", "nextLink"), testCase);
                	link.Click();
                   
                    sFieldExists = VerifyOrClickRowTextForSinglePage(testCase);
                
            }

            if (testCase.action.actionName.equals("ClickRowText"))
            {
                if (sFieldExists == false)
                {
                
                    String message =        "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' does not exist in the page '"
                                    + testCase.action.pageName + "'";
                    

                    TestStepFailureException failure = new TestStepFailureException(message);
                    throw failure;
                    
                }
                else
                {
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' was clicked in the page '"
                                    + testCase.action.pageName + "'");
                }
            }
            else if (testCase.action.actionName.equals("VerifyRowText"))
            {
                if ((sFieldExists == false) && sExistNotExist.toUpperCase().equals("EXIST"))
                {

                   
                      String message =      "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' does not exist in the page '"
                                    + testCase.action.pageName + "'";
                    
                      
                      TestStepFailureException failure = new TestStepFailureException(message);
                      throw failure;
                }
                else if ((sFieldExists == false) && sExistNotExist.toUpperCase().equals("NOTEXIST"))
                {
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' does not exist in the page '"
                                    + testCase.action.pageName + "'");
                }
                else if ((sFieldExists == true) && sExistNotExist.toUpperCase().equals("EXIST"))
                {
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' exists in the page '"
                                    + testCase.action.pageName + "'");
                }
                // For (sFieldExists == true) && sExistNotExist.toUpperCase().equals("NOTEXIST")
                else
                {
                   
                      String message =      "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' existd in the page '"
                                    + testCase.action.pageName + "'";
                  
                      TestStepFailureException failure = new TestStepFailureException(message);
                      throw failure;
                }
            }
        }
        catch (InterruptedException e)
        {
        }
    }


    public static void VerifyOrClickRowText_ForEachColumn(TestCase testCase)
            throws InterruptedException, TestStepFailureException
    {
        try
        {
            String sExistNotExist = null;
            if (testCase.action.actionName.equals("VerifyRowTextForEachColumn"))
            {
                int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
                sExistNotExist =
                        testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
                                .toUpperCase();
                testCase.action.fieldValue =
                        testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
            }

            testCase.updateActionRandomData();
            testCase.updateActionCaptureData();


            boolean sFieldExists = false;

            // Verify if the web text exist on first page.
            sFieldExists = VerifyOrClickRowTextForSinglePage_ForEachColumn(testCase);

            // Verify if the web text exist on the other pages.
            while (!sFieldExists && testCase.setElement.SettingWebelement(testCase, "className", "nextLink") == true)
            {
                
                	WTFWebElement link = new WTFWebElement(testCase.setElement.GetWebelement(testCase, "className", "nextLink"), testCase);
                	link.Click();
                   
                    sFieldExists = VerifyOrClickRowTextForSinglePage_ForEachColumn(testCase);
                
            }

            if (testCase.action.actionName.equals("ClickRowText"))
            {
                if (sFieldExists == false)
                {
                   
                     String message =   "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' does not exist in the page '"
                                    + testCase.action.pageName + "'";
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "--> Test failed");
                    
                    TestStepFailureException failure = new TestStepFailureException(message);
                    throw failure;
                    
                    
                

                }
                else
                {
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' was clicked in the page '"
                                    + testCase.action.pageName + "'");
                }
            }
            else if (testCase.action.actionName.equals("VerifyRowTextForEachColumn"))
            {
                if ((sFieldExists == false) && sExistNotExist.toUpperCase().equals("EXIST"))
                {
                   
                       String message =  "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' does not exist in the page '"
                                    + testCase.action.pageName + "'";
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "--> Test failed");
                   
                    TestStepFailureException failure = new TestStepFailureException(message);
                    throw failure;
                   
                }
                else if ((sFieldExists == false) && sExistNotExist.toUpperCase().equals("NOTEXIST"))
                {
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' does not exist in the page '"
                                    + testCase.action.pageName + "'");
                }
                else if ((sFieldExists == true) && sExistNotExist.toUpperCase().equals("EXIST"))
                {
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' exists in the page '"
                                    + testCase.action.pageName + "'");
                }
                
                else
                {
                  
                      String message =      "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebRowText'"
                                    + testCase.action.fieldValue + "' existd in the page '"
                                    + testCase.action.pageName + "'";
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "--> Test failed");
                   
                    
                    TestStepFailureException failure = new TestStepFailureException(message);
                    throw failure;
                }
            }
        }
        catch (InterruptedException e)
        {
        }
    }


    public static boolean VerifyOrClickRowTextForSinglePage(TestCase testCase)
            throws InterruptedException, TestStepFailureException
    {

      //  Thread.sleep(1000);
        int row_num = 0;
        int col_num;
        boolean sFieldExists = false;
        List<WebElement> tr_collection;
        String[] VrifyRowTexts = testCase.action.fieldValue.split("&");
        int iNumberCellText = VrifyRowTexts.length;

    
        
        WebElement table_elements = testCase.setElement.GetWebelement(testCase, "id", testCase.element.fieldProperty1);
        
              //  testCase.browser.driver().findElement(By.id(testCase.element.fieldProperty1));
        
        tr_collection = testCase.browser.FindElements(By.xpath("id('" + testCase.element.fieldProperty1
                        + "')/tbody/tr"));
                
        for (WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection = trElement.findElements(By.xpath("td"));

            if (td_collection.size() != iNumberCellText)
            {
             
                  String message =  "Step # "
                                        + testCase.action.testStep
                                        + "-->"
                                        + testCase.action.actionName
                                        + ">> Total number of for verifying text cells are not equal to number of columns in table in the page '"
                                        + testCase.action.pageName + "'";
              
                
                  TestStepFailureException failure = new TestStepFailureException(message);
                  throw failure;
                  
                  
            }

            // System.out.println("NUMBER OF COLUMNS="+td_collection.size());
            col_num = 0;
            for (WebElement tdElement : td_collection)
            {

                if (VrifyRowTexts[col_num].toUpperCase().trim().equals("SKIP"))
                {
                }
                else if (VrifyRowTexts[col_num].toUpperCase().trim()
                        .equals("sCaptureWebText".toUpperCase()))
                {
                    if (tdElement.getText().toString().trim().equals(testCase.getCapturedData()))
                    {
                        sFieldExists = true;
                    }
                    else
                    {
                        sFieldExists = false;
                        // testCase.SetStepStatusFailed();
                        break;
                    }
                }
                else if (tdElement.getText().toString().trim()
                        .equals(VrifyRowTexts[col_num].trim()))
                {
                    sFieldExists = true;
                    if (testCase.action.actionName.equals("ClickRowText"))
                    {
                    	WTFWebElement rowText = new WTFWebElement(tdElement, testCase);
                    	
                        rowText.Click();
                    }
                }
                else
                {
                    sFieldExists = false;
                    // testCase.SetStepStatusFailed();
                    break;
                }
                col_num++;
            }
            if (sFieldExists == true)
            {
                break;
            }
            row_num++;
        }
        table_elements = null;
        tr_collection = null;
        return sFieldExists;
    }

    public static boolean VerifyOrClickRowTextForSinglePage_ForEachColumn(TestCase testCase)
            throws InterruptedException
    {

       // Thread.sleep(1000);
        int row_num = 0;
        int col_num;
        boolean sFieldExists = false;
        List<WebElement> tr_collection;
        List<WebElement> tr_collection_ColumnCount;
        String[] VrifyRowTexts = testCase.action.fieldValue.split("&");
        int iNumberCellText = VrifyRowTexts.length;
        int iTotalNumberOfCoumnFromWebTable;
        
        //By fieldPropertyBy = By.id(testCase.element.fieldProperty1);
        //testCase.browser.wait.WaitElementPresent(fieldPropertyBy);
         

        WebElement table_elements = testCase.setElement.GetWebelement(testCase, "id", testCase.element.fieldProperty1);
              //  testCase.browser.driver().findElement(fieldPropertyBy);
        tr_collection =
                table_elements.findElements(By.xpath("id('" + testCase.element.fieldProperty1
                        + "')/tbody/tr"));
        tr_collection_ColumnCount =
                table_elements.findElements(By.xpath("id('" + testCase.element.fieldProperty1
                        + "')/thead/tr"));

        iTotalNumberOfCoumnFromWebTable = tr_collection_ColumnCount.size();

        // System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
        // for(int i= 0; i++; i < iTotalNumberOfCoumnFromWebTable)
        for (WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection = trElement.findElements(By.xpath("td"));

            // if (td_collection.size() != iNumberCellText)
            // {
            // testCase.SetStepStatusFailed();
            // testCase.logging().writeLogAndConsole("Step # "+ testCase.action.testStep +"-->"+
            // testCase.action.actionName +
            // ">> Total number of for verifying text cells are not equal to number of columns in table in the page '"+
            // testCase.action.pageName +"'" );
            // testCase.logging().writeLogAndConsole("Step # "+ testCase.action.testStep
            // +"--> Test failed" );
            // testCase.takeErrorScreenShot();
            // break;
            // }

            // System.out.println("NUMBER OF COLUMNS="+td_collection.size());
            col_num = 0;
            for (WebElement tdElement : td_collection)
            {

                if (VrifyRowTexts[col_num].toUpperCase().trim().equals("SKIP"))
                {
                }
                else if (VrifyRowTexts[col_num].toUpperCase().trim()
                        .equals("sCaptureWebText".toUpperCase()))
                {
                    if (tdElement.getText().toString().trim().equals(testCase.getCapturedData()))
                    {
                        sFieldExists = true;
                    }
                    else
                    {
                        sFieldExists = false;
                        // testCase.SetStepStatusFailed();
                        break;
                    }
                }
                else if (tdElement.getText().toString().trim()
                        .equals(VrifyRowTexts[col_num].trim()))
                {
                    sFieldExists = true;
                    if (testCase.action.actionName.equals("ClickRowTextForEachColumn"))
                    {
                    	WTFWebElement elementToClick = new WTFWebElement(tdElement, testCase);
                    	elementToClick.Click();
                    	
                        //tdElement.click();
                    }
                }
                else
                {
                    sFieldExists = false;
                    // testCase.SetStepStatusFailed();
                    break;
                }
                col_num++;
            }
            if (sFieldExists == true)
            {
                break;
            }
            row_num++;
        }
        table_elements = null;
        tr_collection = null;
        return sFieldExists;
    }


    public static void assertLinkNotPresent(WebDriver driver, String text)
    {
        try
        {
            driver.findElement(By.linkText(text));
            fail("Link with text <" + text + "> is present");
        }
        catch (NoSuchElementException ex)
        {
            /* do nothing, link is not present, assert is passed */
        }
    }



    private static void fail(String string)
    {
        // TODO Auto-generated method stub

    }


    public static void TableCellTextVerifyOrClick(TestCase testCase) throws InterruptedException, TestStepFailureException

    {
        try
        {
            boolean sFieldExists = false;
            boolean bLinkExists = false;
            int row_numHeader, col_numHeader = 0;
            
            row_numHeader = 0;
            
          
/*
            if(testCase.browser.currentElement.isDisplayed())
            {
            testCase.browser.wait.WaitElementPresent(By.linkText("1"));
            bLinkExists = testCase.browser.element.Exists(By.linkText("1"));
            //bLinkExists = testCase.setElement.SettingWebelement(testCase, "linkText", "1");

            //Moves to the first page of the table
            if (bLinkExists == true)
            {
            	testCase.setElement.GetWebelement(testCase, "linkText", "1").click();
               
                
              
            }
            }
            */

            String sExistNotExist = null;
            if (testCase.action.actionName.equals("VerifyTableCellText"))
            {
                int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
                sExistNotExist =
                        testCase.action.fieldValue.substring(0, UnderScoreIndex).trim()
                                .toUpperCase();
                testCase.action.fieldValue =
                        testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();
            }



            int ComaIndex = testCase.action.fieldValue.indexOf("&");
            String sColumnHeaderName = null;
            if (ComaIndex != -1)
            {
                sColumnHeaderName = testCase.action.fieldValue.substring(0, ComaIndex).trim();
            }
            testCase.action.fieldValue = testCase.action.fieldValue.substring(ComaIndex + 1).trim();

            // Update the current actions data with the random values
            testCase.updateActionRandomData();
            testCase.updateActionCaptureData();


            // Finding the column position

           col_numHeader = getColumnIndex(testCase, sColumnHeaderName);
           

            // Verify if the web text exist on first page.
            sFieldExists = Verify.WebCellTextFoundOrNot(testCase, col_numHeader);

            int iCounter = 1;

            
           
           if(!testCase.browser.element.Exists(By.linkText("1")))
           {
        	iCounter++;   
        	   
           }
            
          
                while (!sFieldExists && testCase.browser.element.Exists(By.linkText(String.valueOf(iCounter))) == true)
                {
                	testCase.setElement.SettingWebelement(testCase, "linkText", String.valueOf(iCounter));
                   
                        WebElement linkElement = testCase.browser.wait.WaitElementPresent(By.linkText(String.valueOf(iCounter)));
                        
                        testCase.setElement.scrollToElement(linkElement, testCase);
                        linkElement.click();
                                          
                      
                        sFieldExists = Verify.WebCellTextFoundOrNot(testCase, col_numHeader);
                   
                        iCounter++;
                }

          

            // if Web Cell Text does not exists.
            if ((sFieldExists == false))
            {
            	String message = "";
                if (testCase.action.actionName.equals("ClickTableCellText"))
                {
              
                      message =      "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebCell'"
                                    + testCase.action.fieldValue + "' does not exist in the page '"
                                    + testCase.action.pageName + "'";
                    
                      
                   
                }
                else if (testCase.action.actionName.equals("VerifyTableCellText"))
                {
                    if (sExistNotExist.toUpperCase().equals("EXIST"))
                    {
                     
                      message =          "Step # " + testCase.action.testStep + "-->"
                                        + testCase.action.actionName + ">> WebCell'"
                                        + testCase.action.fieldValue
                                        + "' does not exist in the page '"
                                        + testCase.action.pageName + "'";
                       
                    }
                    else
                    {
                        
                          message =      "Step # " + testCase.action.testStep + "-->"
                                        + testCase.action.actionName + ">> WebCell'"
                                        + testCase.action.fieldValue
                                        + "' does not exist in the page '"
                                        + testCase.action.pageName + "'";
                    }
                    TestStepFailureException failure = new TestStepFailureException(message);
                    throw failure;
                }
                else
                {
                }
            }

            // if Web Cell Text does not exists.
            if ((sFieldExists == true))
            {
                if (testCase.action.actionName.equals("ClickTableCellText"))
                {
                    testCase.logging().writeLogAndConsole(
                            "Step # " + testCase.action.testStep + "-->"
                                    + testCase.action.actionName + ">> WebCell'"
                                    + testCase.action.fieldValue + "' was clicked in the page '"
                                    + testCase.action.pageName + "'");
                }
                else if (testCase.action.actionName.equals("VerifyTableCellText"))
                {
                    if (sExistNotExist.toUpperCase().equals("EXIST"))
                    {
                        testCase.logging().writeLogAndConsole(
                                "Step # " + testCase.action.testStep + "-->"
                                        + testCase.action.actionName + ">> WebCell'"
                                        + testCase.action.fieldValue + "' exists in the page '"
                                        + testCase.action.pageName + "'");
                    }
                    else
                    {
                        testCase.logging().writeLogAndConsole(
                                "Step # " + testCase.action.testStep + "-->"
                                        + testCase.action.actionName + ">> WebCell'"
                                        + testCase.action.fieldValue + "' exists in the page '"
                                        + testCase.action.pageName + "'");
                        testCase.logging().writeLogAndConsole(
                                "Step # " + testCase.action.testStep + "--> Test failed");
                    }
                }
                else
                {
                }
            }
        }
        catch (InterruptedException e)
        {
        }
    }

    
    private static int getColumnIndex(TestCase testCase, String sColumnHeaderName) throws TestStepFailureException
    {
    	 int row_numHeader=0, col_numHeader = 0;
    	 String CellValue;
    	 boolean ColumnExists = false;
    	 
    	 WebElement table_element = testCase.setElement.GetWebelement(testCase, "id", testCase.element.fieldProperty1);
 		
         
         List<WebElement> tr_collectionHeader =
                 table_element.findElements(By.xpath("id('" + testCase.element.fieldProperty1
                         + "')/thead/tr"));
         
         for (WebElement trElement : tr_collectionHeader)
         {
             List<WebElement> td_collectionHeader = trElement.findElements(By.xpath("th"));
            
             col_numHeader = 0;
             for (WebElement tdElement : td_collectionHeader)
             {
                 CellValue = tdElement.getText().toString().trim();
                 if (CellValue.equals(sColumnHeaderName))
                 {
                     ColumnExists = true;
                     break;
                 }
                 
                 col_numHeader++;
             }
             row_numHeader++;
         }

         if (ColumnExists == false)
         {
           
           String message="";
         	if ((testCase.action.actionName == "VerifyTableCellText")
                     || (testCase.action.actionName == "ClickTableCellText"))
             {
                
                   message =      "Step # " + testCase.action.testStep + "-->"
                                 + testCase.action.actionName + ">> Column'" + sColumnHeaderName
                                 + "' does not exist in the page '" + testCase.action.pageName
                                 + "'";
             }
             else
             {
             }
         	TestStepFailureException failure = new TestStepFailureException(message);
             throw failure;
         }
    	
         return col_numHeader;
    }
    
    
    
    
    public static boolean WebCellTextFoundOrNot(TestCase testCase, int iColumnHeader)
            throws InterruptedException
    {
        int col_num;
        boolean sFieldExists = false;
        List<WebElement> tr_collection;

        
        WebElement table_elements = testCase.setElement.GetWebelement(testCase, "id", testCase.element.fieldProperty1);
        
       
        
        tr_collection =
        		   table_elements.findElements(By.xpath("id('" + testCase.element.fieldProperty1
                        + "')/tbody/tr"));
        
        
        for (WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
           
            col_num = 0;
            for (WebElement tdElement : td_collection)
            {
                if (col_num == iColumnHeader)
                {
                    if (tdElement.getText().toString().trim().equals(testCase.action.fieldValue))
                    {
                        sFieldExists = true;
                        if (testCase.action.actionName.equals("VerifyTableCellText"))
                        {

                            break;
                        }
                        else if (testCase.action.actionName.equals("ClickTableCellText"))
                        {
                        	testCase.setElement.scrollToElement(tdElement, testCase);
                        	
                        	//tdElement.click();
                        	
                        	WTFWebElement elementToClick = new WTFWebElement(tdElement, testCase);
                        	elementToClick.Click();
                            break;
                        }
                        
                    }
                }
                col_num++;
            }
            if (sFieldExists == true)
            {
                break;
            }
            
        }
        table_elements = null;
        tr_collection = null;
        return sFieldExists;
    }

    public static void VerifyRowText(TestCase testCase) throws InterruptedException, TestStepFailureException

    {
        try
        {

            String sExistNotExist = null;
            int UnderScoreIndex = testCase.action.fieldValue.indexOf("_");
            sExistNotExist =
                    testCase.action.fieldValue.substring(0, UnderScoreIndex).trim().toUpperCase();
            testCase.action.fieldValue =
                    testCase.action.fieldValue.substring(UnderScoreIndex + 1).trim();

            boolean sFieldExists = false;

            // Verify if the web text exist on first page.
            sFieldExists = VerifyRowTextForSinglePage(testCase);

            // Verify if the web text exist on the other pages.
            while (testCase.setElement.SettingWebelement(testCase, "className", "nextLink") == true)
            {
                if (sFieldExists == true)
                {
                    break;
                }
                else
                {
                	WTFWebElement link = new WTFWebElement(testCase.setElement.GetWebelement(testCase,"className", "nextLink"), testCase);
                	link.Click();
                   //testCase.setElement.GetWebelement(testCase, "className", "nextLink").click();
                 //   Thread.sleep(3000);
                    sFieldExists = VerifyRowTextForSinglePage(testCase);
                }
            }

            // if Web Cell Text does not exists.
            if ((sFieldExists == false) && sExistNotExist.toUpperCase().equals("EXIST"))
            {
                //testCase.SetStepStatusFailed();
                //testCase.logging()
                  //      .writeLogAndConsole(
                    String message =    "Step # " + testCase.action.testStep + "-->"
                                        + testCase.action.actionName + ">> WebRowText'"
                                        + testCase.action.fieldValue
                                        + "' does not exist in the page '"
                                        + testCase.action.pageName + "'";
              //  testCase.logging().writeLogAndConsole(
                      //  "Step # " + testCase.action.testStep + "--> Test failed");
               // testCase.takeErrorScreenShot();
               // testCase.logging().stepFailed();
                    TestStepFailureException failure = new TestStepFailureException(message);
                    throw failure;
            }
            else if ((sFieldExists == false) && sExistNotExist.toUpperCase().equals("NOTEXIST"))
            {
                testCase.logging()
                        .writeLogAndConsole(
                                "Step # " + testCase.action.testStep + "-->"
                                        + testCase.action.actionName + ">> WebRowText'"
                                        + testCase.action.fieldValue
                                        + "' does not exist in the page '"
                                        + testCase.action.pageName + "'");
            }
            else if ((sFieldExists == true) && sExistNotExist.toUpperCase().equals("EXIST"))
            {
                testCase.logging().writeLogAndConsole(
                        "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                                + ">> WebRowText'" + testCase.action.fieldValue
                                + "' exists in the page '" + testCase.action.pageName + "'");
            }
            // For (sFieldExists == true) && sExistNotExist.toUpperCase().equals("NOTEXIST")
            else
            {
               // testCase.SetStepStatusFailed();
               // testCase.logging().writeLogAndConsole(
                 String message =    "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                                + ">> WebRowText'" + testCase.action.fieldValue
                                + "' existd in the page '" + testCase.action.pageName + "'";
                //testCase.logging().writeLogAndConsole(
                 //       "Step # " + testCase.action.testStep + "--> Test failed");
               // testCase.takeErrorScreenShot();
               // testCase.logging().stepFailed();
                
                TestStepFailureException failure = new TestStepFailureException(message);
                throw failure;
            }

        }
        catch (InterruptedException e)
        {
        }
    }

    public static boolean VerifyRowTextForSinglePage(TestCase testCase) throws InterruptedException
    {

       // Thread.sleep(1000);
        int row_num = 0;
        int col_num;
        boolean sFieldExists = false;
        List<WebElement> tr_collection;
        String[] VrifyRowTexts = testCase.action.fieldValue.split("&");
        int iNumberCellText = VrifyRowTexts.length;

        By fieldPropertyBy = By.id(testCase.element.fieldProperty1);
        testCase.browser.wait.WaitElementPresent(fieldPropertyBy);
        
        WebElement table_elements =
                testCase.browser.driver().findElement(fieldPropertyBy);
        tr_collection =
                table_elements.findElements(By.xpath("id('" + testCase.element.fieldProperty1
                        + "')/tbody/tr"));

        // testCase.logging().writeLogAndConsole("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
        for (WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection = trElement.findElements(By.xpath("td"));

            if (td_collection.size() != iNumberCellText)
            {
                testCase.logging()
                        .writeLogAndConsole(
                                "Step # "
                                        + testCase.action.testStep
                                        + "-->"
                                        + testCase.action.actionName
                                        + ">> Total number of for verifying texts  cell are same the page '"
                                        + testCase.action.pageName + "'");
                break;
            }

            // testCase.logging().writeLogAndConsole("NUMBER OF COLUMNS="+td_collection.size());
            col_num = 0;
            for (WebElement tdElement : td_collection)
            {
                if (VrifyRowTexts[col_num].toUpperCase().trim().equals("SKIP"))
                {
                }
                else if (tdElement.getText().toString().trim()
                        .equals(VrifyRowTexts[col_num].trim()))
                {
                    sFieldExists = true;
                }
                else
                {
                    sFieldExists = false;
                    break;
                }
                col_num++;
            }
            if (sFieldExists == true)
            {
                break;
            }
            row_num++;
        }
        table_elements = null;
        tr_collection = null;
        return sFieldExists;
    }

}
