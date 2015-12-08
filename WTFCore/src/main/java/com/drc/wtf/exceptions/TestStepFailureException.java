package com.drc.wtf.exceptions;

import com.drc.wtf.test_management.Log;


public class TestStepFailureException extends Exception
{
	
	public TestStepFailureException(String message) {
        super(message);
    }
}
