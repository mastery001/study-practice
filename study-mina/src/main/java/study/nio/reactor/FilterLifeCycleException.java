package study.nio.reactor;

public class FilterLifeCycleException extends RuntimeException {
	private static final long serialVersionUID = -5542098881633506449L;

	public FilterLifeCycleException() {
	}

	public FilterLifeCycleException(String message) {
		super(message);
	}

	public FilterLifeCycleException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilterLifeCycleException(Throwable cause) {
		super(cause);
	}
}
