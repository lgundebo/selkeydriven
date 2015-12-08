package com.drc.wtf.actions;




import java.util.List;






import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;






import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;


public class SelectItemFromList extends ActionBase
{
	


public SelectItemFromList(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform() throws TestStepFailureException{
	
	Boolean bFoundItemInList = false;
	String SDDListSelectedValue;
	
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	 Select selectBox = new Select(testCase.browser.currentElement);
     selectBox.deselectAll();
     List<WebElement> sListItem = selectBox.getOptions();
    
     for (WebElement option : sListItem)
     {
         SDDListSelectedValue = option.getText().toString().trim();
         if (SDDListSelectedValue.equals(testCase.action.fieldValue))
         {
             bFoundItemInList = true;
             selectBox.selectByVisibleText(testCase.action.fieldValue);
            
                     
             break;
         }
     }
     if (bFoundItemInList == false)
     {
       
         String message =
                 "Step # " + testCase.action.testStep + "-->"
                         + testCase.action.actionName + ">> '"
                         + testCase.action.fieldValue
                         + "' does not exist in the list for '"
                         + testCase.action.fieldName + "' in the page '"
                         + testCase.action.pageName + "'";
         
         TestStepFailureException failure = new TestStepFailureException(message);
         throw failure;
         
         
     }
                      
     
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> '"
            + testCase.action.fieldValue
            + "' was selected in the list '"
            + testCase.action.fieldName + "' in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}


	
	 
}
