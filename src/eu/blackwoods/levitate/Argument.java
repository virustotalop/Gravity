package eu.blackwoods.levitate;

public class Argument {
	
	private String method;
	private String parameter;
	private SyntaxHandler handler;
	
	/**
	 * Only used internal
	 * @param method
	 * @param parameter
	 * @param handler
	 */
	public Argument(String method, String parameter, SyntaxHandler handler) {
		this.method = method;
		this.parameter = parameter;
		this.handler = handler;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public SyntaxHandler getHandler() {
		return handler;
	}

	public void setHandler(SyntaxHandler handler) {
		this.handler = handler;
	}
	
	
	
	
	
}
