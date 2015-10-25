package eu.blackwoods.levitate;

import java.util.List;

import eu.blackwoods.levitate.exception.CommandSyntaxException;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public interface SyntaxHandler {
	
	/**
	 * Handles whether a value is vaild
	 * @param parameter The parameters of the syntax
	 * @param passed The value passed by the user
	 * @throws CommandSyntaxException
	 * @throws SyntaxResponseException
	 */
	public void check(String parameter, String passed) throws CommandSyntaxException, SyntaxResponseException;
	
	public List<String> getTabComplete(String parameter, String passed);
}
