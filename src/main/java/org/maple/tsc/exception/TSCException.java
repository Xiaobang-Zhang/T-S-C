/**
 * 
 */
/**
 * @author Maple
 *
 */
package org.maple.tsc.exception;

public class TSCException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2154991171939164668L;
	
	private String errorMessage;
	

	public TSCException(String aErrorMessage) {
		setErrorMessage(aErrorMessage);
	}
	
	private void setErrorMessage(String aErrorMessage) {
		this.errorMessage = aErrorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}