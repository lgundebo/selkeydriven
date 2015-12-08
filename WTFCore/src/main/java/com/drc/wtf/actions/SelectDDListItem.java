package com.drc.wtf.actions;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;

public class SelectDDListItem extends ActionBase {

	public SelectDDListItem(TestCase testCase) {
		super(testCase);

	}

	@Override
	public void Perform() throws TestStepFailureException {

		Boolean bFoundItemInList = false;

		super.waitForElementDispalyed(testCase.browser.currentElement);
		super.waitForElementClickable(testCase.browser.currentElement);

		Select select = new Select(testCase.browser.currentElement);

try{
		select.selectByVisibleText(testCase.action.fieldValue);
}
catch(Exception e)
{
	String message = "Step # " + testCase.action.testStep + "-->"
			+ testCase.action.actionName + ">> '"
			+ testCase.action.fieldValue
			+ "' does not exist in the list for '"
			+ testCase.action.fieldName + "' in the page '"
			+ testCase.action.pageName + "'";

	TestStepFailureException failure = new TestStepFailureException(
			message);
	throw failure;	
}
		/*
		String firstSelected = select.getFirstSelectedOption().getText().trim();

		
		if (firstSelected.equals(testCase.action.fieldValue)) {
			bFoundItemInList = true;

		}
		*/
		
		/*
		List<WebElement> sDDList = select.getOptions();
		for (WebElement option : sDDList) {
			String SDDListSelectedValue = option.getText().toString().trim();
			if (SDDListSelectedValue.equals(testCase.action.fieldValue)) {
				bFoundItemInList = true;

				option.click();

				break;
			}
		}
		
		*/
		/*
		if (bFoundItemInList == false) {

			String message = "Step # " + testCase.action.testStep + "-->"
					+ testCase.action.actionName + ">> '"
					+ testCase.action.fieldValue
					+ "' does not exist in the list for '"
					+ testCase.action.fieldName + "' in the page '"
					+ testCase.action.pageName + "'";

			TestStepFailureException failure = new TestStepFailureException(
					message);
			throw failure;

		}
*/
		testCase.logging().writeLogAndConsole(StepSuccessMessage());
	}

	@Override
	protected String StepSuccessMessage() {
		String message = "Step # " + testCase.action.testStep + "-->"
				+ testCase.action.actionName + ">> '"
				+ testCase.action.fieldValue + "' was selected in the field '"
				+ testCase.action.fieldName + "' in the page '"
				+ testCase.action.pageName + "'";

		return message;
	}

}
