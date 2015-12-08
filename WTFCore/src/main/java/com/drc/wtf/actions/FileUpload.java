package com.drc.wtf.actions;



import java.io.File;




import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.SkipException;

import com.drc.wtf.actions.base.ActionBase;
import com.drc.wtf.test_management.TestCase;


public class FileUpload extends ActionBase
{
	


public FileUpload(TestCase testCase)
{
	super(testCase);
	
}

@Override
public void Perform(){
	super.waitForElementDispalyed(testCase.browser.currentElement);
	
	if(testCase.driverSetup.browser.getValue().toUpperCase().equals("SAFARI") )
	{
		testCase.skipException = new SkipException("File Upload is unsupported in Safari browser.  Test Case was Skipped.");
	}
	
	LocalFileDetector detector = new LocalFileDetector();
	String path = testCase.configInfo.fileLocationFileUpload.trim();
	path = path + testCase.action.fieldValue.trim();
	File uploadFile = detector.getLocalFile(path);
	
	//Export to remote node
	((RemoteWebElement)testCase.browser.currentElement).setFileDetector(detector);
	String a = uploadFile.getAbsolutePath();
	System.out.println(a); 
	//testCase.browser.driver().setFileDetector(detector);
	testCase.browser.currentElement.sendKeys(uploadFile.getAbsolutePath());
    
            

           
                    
     
     testCase.logging().writeLogAndConsole(StepSuccessMessage());
}

@Override
protected String StepSuccessMessage() {
	String message = "Step # " + testCase.action.testStep + "-->"
            + testCase.action.actionName + ">> '"
            + testCase.action.fieldValue
            + "' was inserted next to the button '"
            + testCase.action.fieldName + "' in the page '"
            + testCase.action.pageName + "'";
	
	return message;
}


	
	 
}
