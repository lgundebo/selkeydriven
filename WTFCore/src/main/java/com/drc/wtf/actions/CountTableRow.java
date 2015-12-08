package com.drc.wtf.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class CountTableRow extends ActionBase
{
	


public CountTableRow(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	 List<WebElement> tr_collection;
     WebElement table_elements =
             testCase.browser.driver().findElement(
                     By.id(testCase.element.fieldProperty1));
     tr_collection =
             table_elements.findElements(By.xpath("id('"
                     + testCase.element.fieldProperty1 + "')/tbody/tr"));
    
     iCountTableRowCount = tr_collection.size();

     if (testCase.action.fieldValue.contains("TableRowCountNum1"))
     {
         testCase.GetCapturedDataObject().iCountTableRowCount1 = iCountTableRowCount;
     }
     else if (testCase.action.fieldValue.contains("TableRowCountNum2"))
     {
         testCase.GetCapturedDataObject().iCountTableRowCount2 = iCountTableRowCount;
     }
     else if (testCase.action.fieldValue.contains("TableRowCountNum3"))
     {
         testCase.GetCapturedDataObject().iCountTableRowCount3 = iCountTableRowCount;
     }
     else if (testCase.action.fieldValue.contains("TableRowCountNum4"))
     {
         testCase.GetCapturedDataObject().iCountTableRowCount4 = iCountTableRowCount;
     }
     
     
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName
            + ">> Total number rows for the table '"
            + testCase.action.fieldName + "' is :"
            + iCountTableRowCount + "  in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}


	private int iCountTableRowCount;
	 
}
