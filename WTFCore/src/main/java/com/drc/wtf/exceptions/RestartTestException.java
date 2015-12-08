package com.drc.wtf.exceptions;

public class RestartTestException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8956957388719005481L;
	public Boolean restartTestCase = false;

	public RestartTestException(String message) {
		super(message);
		restartTestCase = true;
	}

	public RestartTestException(Exception ex) {
		super(ex.getMessage());

		String message = ex.getMessage();// ex.getLocalizedMessage();

		try {
			if (message.contains("FORWARDING_TO_NODE_FAILED")
					|| message.contains("not reachable")
					|| message
							.contains("no such window: target window already closed")
					|| message
							.contains("Error communicating with the remote browser. It may have died.")
					|| message.contains("Connection timed out")

			) {

				restartTestCase = true;
			}

		} catch (Exception e) {
			restartTestCase = false;
		}
	}

}
