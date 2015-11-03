package eu.blackwoods.levitate;

public class Argument implements Cloneable {

	private String method;
	private String parameter;
	private SyntaxHandler handler;
	private boolean unlimited = false;

	/**
	 * Only used internal
	 * 
	 * @param method
	 * @param parameter
	 * @param handler
	 */
	public Argument(String method, String parameter, SyntaxHandler handler) 
	{
		this.method = method;
		this.parameter = parameter;
		this.handler = handler;
	}

	/**
	 * Only used internal
	 * 
	 * @param method
	 * @param parameter
	 * @param handler
	 */
	public Argument(String method, String parameter, SyntaxHandler handler,boolean unlimited) 
	{
		this.method = method;
		this.parameter = parameter;
		this.handler = handler;
		this.unlimited = unlimited;
	}

	public String getMethod() 
	{
		return this.method;
	}

	public void setMethod(String method) 
	{
		this.method = method;
	}

	public String getParameter() 
	{
		return parameter;
	}

	public void setParameter(String parameter) 
	{
		this.parameter = parameter;
	}

	public SyntaxHandler getHandler() 
	{
		return this.handler;
	}

	public void setHandler(SyntaxHandler handler) 
	{
		this.handler = handler;
	}

	public boolean isUnlimited() 
	{
		return this.unlimited;
	}

	public void setUnlimited(boolean unlimited) 
	{
		this.unlimited = unlimited;
	}

	protected Object clone() 
	{
		Argument clone = null;
		try 
		{
			clone = (Argument) super.clone();
		} 
		catch (CloneNotSupportedException e) 
		{
			e.printStackTrace();
		}
		return clone;
	}
}