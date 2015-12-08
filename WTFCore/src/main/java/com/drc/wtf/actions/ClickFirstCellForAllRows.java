package com.drc.wtf.actions;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.interactions.WTFWebElement;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.exceptions.TestTimeOutException;
import com.drc.wtf.execution.Verify;
import com.drc.wtf.test_management.TestCase;

public class ClickFirstCellForAllRows extends ActionBase
{
	


public ClickFirstCellForAllRows(TestCase testCase)
{
	super(testCase);
	
}

public void Perform() throws TestStepFailureException, TestTimeOutException {
	
	 try
     {

         int row_num;
         int col_num;
         List<WebElement> tr_collection;
         WebElement table_elements =
                 testCase.browser.driver().findElement(By.id(testCase.element.fieldProperty1));
         tr_collection =
                 table_elements.findElements(By.xpath("id('" + testCase.element.fieldProperty1
                         + "')/tbody/tr"));
         // testCase.logging().writeLogAndConsole("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
         row_num = 1;
         for (WebElement trElement : tr_collection)
         {
             List<WebElement> td_collection = trElement.findElements(By.xpath("td"));

             // testCase.logging().writeLogAndConsole("NUMBER OF COLUMNS="+td_collection.size());
             col_num = 1;
             for (WebElement tdElement : td_collection)
             {
                 if ((testCase.action.fieldValue.toUpperCase().equals("P")) && (col_num == 1))
                 {
                	 /*
                	 testCase.browser
                     .driver()
                     .findElement(
                             By.xpath(".//*[@id='itemsTable']/tbody/tr["
                                     + Integer.toString(row_num) + "]/td[1]")).click();
                	 */
                    WebElement rowElement = testCase.browser
                     .driver()
                     .findElement(
                             By.xpath(".//*[@id='itemsTable']/tbody/tr["
                                     + Integer.toString(row_num) + "]/td[1]"));
                    
                    WTFWebElement elementToClick = new WTFWebElement(rowElement, testCase);
        			elementToClick.Click();
                    
                    try
                    {
                    	Thread.sleep(3000);
                    }
                    catch (InterruptedException e)
                    {
                    	throw new TestTimeOutException(e);
                    }
                    
                    
                     break;
                 }
                 col_num++;
             }
             row_num++;
         }

         testCase.logging().writeLogAndConsole(
                 "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                 + ">> Script was able to click first cell of all rows for '"
                 + testCase.action.fieldValue + "' in the page '"
                 + testCase.action.pageName + "'");
     }
     catch (Exception e)
     {
     	
         
           String message =      "Step # " + testCase.action.testStep + "-->" + testCase.action.actionName
                 + ">> Script was not able to click first cell of all rows for '"
                 + testCase.action.fieldValue + "' in the page '"
                 + testCase.action.pageName + "'";
           
         TestStepFailureException failure = new TestStepFailureException(message);
         throw failure;
     }
	
	 
}

@Override
protected String StepSuccessMessage() {
	
	return null;
}


	
	 
}
