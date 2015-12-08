package com.drc.wtf.actions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.actions.interactions.Mouse;
import com.drc.wtf.exceptions.TestStepFailureException;
import com.drc.wtf.test_management.TestCase;

public class SelectDDTableItem extends ActionBase {

	public SelectDDTableItem(TestCase testCase) {
		super(testCase);

	}

	@Override
	public void Perform() throws TestStepFailureException {

		

		super.waitForElementDispalyed(testCase.browser.currentElement);
		super.waitForElementClickable(testCase.browser.currentElement);

		
		//Click the initial element which opens up the drop down table
		Mouse mouse = new Mouse(testCase);
		mouse.Click();
		
		
		//Send backspace to the element to clear out the original values
		//these element types have some interesting behavior and try to backfill the value
		//this seems to mitigate that
		int initalTextLength = testCase.browser.currentElement.getAttribute("value").length();
		
		for(int i =0; i < initalTextLength; i++)
		{
			testCase.browser.currentElement.sendKeys(Keys.BACK_SPACE);
		}
		
		
		
		/*
		 * The element does not appear in the DOM right away
		 * waiting briefly and then sending the letters of the expected value
		 * one letter at a time seems to work best with the control
		 * 
		 * the control trys to autocomplete after every keystroke this seems to work the best
		 */
		int letterCount = testCase.action.fieldValue.length();
		int i =0;
		WebElement element;
		do{
		element = testCase.browser.wait.WaitElementPresent(By.xpath( "//*[contains(text(),'"+testCase.action.fieldValue+"')]"), 3);
		
		testCase.browser.currentElement.sendKeys(String.valueOf(testCase.action.fieldValue.toCharArray()[i]));
		i++;
		}
		while(element == null  &&i < letterCount);
		
		
		
		
		//Once the element is visible click it
		testCase.setElement.SettingWebelement(testCase, "xpath", "//*[contains(text(),'"+testCase.action.fieldValue+"')]");
		testCase.browser.currentElement.click();
	//	mouse.Click();
		
		
		
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
