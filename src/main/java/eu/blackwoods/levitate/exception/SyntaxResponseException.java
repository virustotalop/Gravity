package eu.blackwoods.levitate.exception;

public class SyntaxResponseException extends Exception {

	private static final long serialVersionUID = -1738985143779536725L;

	public SyntaxResponseException() 
	{
		super();
	}

	public SyntaxResponseException(String message) 
	{
		super(message);
	}

	public SyntaxResponseException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public SyntaxResponseException(Throwable cause) 
	{
		super(cause);
	}
}