package eu.blackwoods.levitate.syntax;

import java.util.HashMap;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class NotEqualsSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%arg%", passed);
		replaces.put("%value%", parameter);
		if(parameter.equals(passed)) throw new SyntaxResponseException(Message.NOTEQUALSSYNTAX_CANNOT_EQUAL.get(TextMode.COLOR, replaces));
	}

}
