package eu.blackwoods.levitate.syntax;

import java.util.HashMap;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class BooleanSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, final String passed) throws SyntaxResponseException {
		if(passed.equalsIgnoreCase("true")) return;
		if(passed.equalsIgnoreCase("false")) return;
		if(passed.equalsIgnoreCase("0")) return;
		if(passed.equalsIgnoreCase("1")) return;
		if(passed.equalsIgnoreCase("on")) return;
		if(passed.equalsIgnoreCase("off")) return;
		if(passed.equalsIgnoreCase("an")) return;
		if(passed.equalsIgnoreCase("aus")) return;
		if(passed.equalsIgnoreCase("ja")) return;
		if(passed.equalsIgnoreCase("nein")) return;
		if(passed.equalsIgnoreCase("yes")) return;
		if(passed.equalsIgnoreCase("no")) return;
		if(passed.equalsIgnoreCase("enable")) return;
		if(passed.equalsIgnoreCase("disable")) return;
		throw new SyntaxResponseException(Message.BOOLEANSYNTAX_HAS_TO_BE_BOOLEAN.get(TextMode.COLOR, new HashMap<String, String>(){{put("%arg%", passed);}}));
	}

}
