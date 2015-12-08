package com.drc.wtf.exceptions;



public class TestTimeOutException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7492426430943790625L;
	public Boolean restartTestCase = false;

	public TestTimeOutException(String message) {
		super(message);
	}

	public TestTimeOutException(Exception ex) {
		super(ex.getMessage());

		
	}

}
