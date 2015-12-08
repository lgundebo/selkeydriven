package com.drc.wtf.exceptions;

import com.drc.wtf.test_management.TestCase;

public class ObjectRepositoryException extends Exception
{
	public ObjectRepositoryException(TestCase testCase)
	{
		
		super("For Step# "+ testCase.action.testStep + " Unable to find: " +  testCase.action.fieldName);
		
	}
	
	public ObjectRepositoryException(String message) {
        super(message);
    }
}
