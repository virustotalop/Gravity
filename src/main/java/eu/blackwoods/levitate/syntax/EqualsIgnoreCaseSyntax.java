package eu.blackwoods.levitate.syntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class EqualsIgnoreCaseSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException 
	{
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%arg%", passed);
		replaces.put("%value%", parameter);
		if(!parameter.equalsIgnoreCase(passed)) throw new SyntaxResponseException(Message.EQUALSIGNORECASESYNTAX_DOESNT_EQUAL.get(TextMode.COLOR, replaces));
	}

	@Override
	public List<String> getTabComplete(String parameter, String passed) 
	{
		return new ArrayList<String>(Arrays.asList(parameter));
	}

}
