package com.dexesttp.hkxpack.hkxwriter.exceptions;

/**
 * Throwed when an object content doesn't match with its template.
 */
public class WrongInputCastException extends Exception {
	private static final long serialVersionUID = 6483999356904172141L;

	/**
	 * Creates a {@link WrongInputCastException}.
	 * 
	 * @param message the error message
	 */
	public WrongInputCastException(final String message) {
		super(message);
	}

	/**
	 * Creates a {@link WrongInputCastException}.
	 * 
	 * @param previousException the exception that caused the
	 *                          {@link WrongInputCastException}.
	 */

	public WrongInputCastException(final Throwable previousException) {
		super(previousException);
	}

	/**
	 * Creates a {@link WrongInputCastException}.
	 * 
	 * @param message           the error message
	 * @param previousException the exception that caused the
	 *                          {@link WrongInputCastException}.
	 */
	public WrongInputCastException(final String message, final Throwable previousException) {
		super(message, previousException);
	}
}
