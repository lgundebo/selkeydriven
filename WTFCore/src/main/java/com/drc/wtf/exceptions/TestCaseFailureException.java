package com.drc.wtf.exceptions;

import com.drc.wtf.test_management.Log;


public class TestCaseFailureException extends Exception
{
	public TestCaseFailureException(Log testCaseLog)
	{
		
		super("TestCase execution failed." +
				System.getProperty("line.separator") +
				testCaseLog.LogHistory() + 
				System.getProperty("line.separator") +
				System.getProperty("line.separator") +  
				testCaseLog.ExceptionHistory());
	}
	
	public TestCaseFailureException(String message) {
        super(message);
    }
}
