package eu.blackwoods.levitate.exception;

public class CommandSyntaxException extends Exception {

	private static final long serialVersionUID = -8433119402423624668L;

	public CommandSyntaxException() 
	{
		super();
	}

	public CommandSyntaxException(String message) 
	{
		super(message);
	}

	public CommandSyntaxException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public CommandSyntaxException(Throwable cause) 
	{
		super(cause);
	}
}