package exception;

public class StructureException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4187679158109073384L;

	public StructureException() {
		super();
	}

	public StructureException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StructureException(String message, Throwable cause) {
		super(message, cause);
	}

	public StructureException(String message) {
		super(message);
	}

	public StructureException(Throwable cause) {
		super(cause);
	}

}
