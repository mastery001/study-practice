package spider;

public class FetchException extends RuntimeException{

	/**
	 * 2016年5月17日 下午3:52:31
	 */
	private static final long serialVersionUID = 4795286284999973738L;

	public FetchException() {
		super();
	}

	public FetchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FetchException(String message, Throwable cause) {
		super(message, cause);
	}

	public FetchException(String message) {
		super(message);
	}

	public FetchException(Throwable cause) {
		super(cause);
	}


}
