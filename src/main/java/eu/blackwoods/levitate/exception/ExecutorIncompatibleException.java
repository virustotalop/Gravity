package eu.blackwoods.levitate.exception;

public class ExecutorIncompatibleException extends Exception {
	public ExecutorIncompatibleException() {
		super();
	}

	public ExecutorIncompatibleException(String message) {
		super(message);
	}

	public ExecutorIncompatibleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExecutorIncompatibleException(Throwable cause) {
		super(cause);
	}
}