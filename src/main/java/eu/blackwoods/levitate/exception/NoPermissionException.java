package eu.blackwoods.levitate.exception;

public class NoPermissionException extends Exception {

	private static final long serialVersionUID = 2130857347420726232L;

	public NoPermissionException() 
	{
		super();
	}

	public NoPermissionException(String message) 
	{
		super(message);
	}

	public NoPermissionException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public NoPermissionException(Throwable cause) 
	{
		super(cause);
	}
}