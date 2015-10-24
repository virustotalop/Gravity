package eu.blackwoods.levitate.syntax;

import java.util.HashMap;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.exception.CommandSyntaxException;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class IntegerSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws CommandSyntaxException, SyntaxResponseException {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%arg%", passed);
		if(!isInt(passed)) throw new SyntaxResponseException(Message.INTEGERSYNTAX_HAS_NO_INTEGER.get(TextMode.COLOR, replaces));
		int passedInt = Integer.parseInt(passed);
		if(parameter.equals("+")) {
			if(passedInt < 0 && passedInt != 0) throw new SyntaxResponseException(Message.INTEGERSYNTAX_HAS_TO_BE_POSITIVE_ZERO.get(TextMode.COLOR, replaces));
		} else if(parameter.equals("-")) {
			if(passedInt > 0 && passedInt != 0) throw new SyntaxResponseException(Message.INTEGERSYNTAX_HAS_TO_BE_NEGATIVE_ZERO.get(TextMode.COLOR, replaces));
		} else if(parameter.contains("-")) {
			replaces.put("%parameter%", parameter);
			if(!parameter.matches("(-?[0-9]+)-(-?[0-9]+)")) throw new CommandSyntaxException(Message.INTEGERSYNTAX_PARAMETER_MALFORMED.get(TextMode.COLOR, replaces));
			if(parameter.startsWith("-")) {
				int a = Integer.parseInt("-"+parameter.split("-", 3)[1]);
				int b = Integer.parseInt(parameter.split("-", 3)[2]);
				replaces.put("%min%", String.valueOf(a));
				replaces.put("%max%", String.valueOf(b));
				
				if(a > b) throw new SyntaxResponseException(Message.INTEGERSYNTAX_PARAMETER_MALFORMED_2.get(TextMode.COLOR, replaces));
				if(passedInt < a || passedInt > b) throw new SyntaxResponseException(Message.INTEGERSYNTAX_HAS_TO_BE_INBETWEEN.get(TextMode.COLOR, replaces));
				return;
			} else {
				int a = Integer.parseInt(parameter.split("-", 2)[0]);
				int b = Integer.parseInt(parameter.split("-", 2)[1]);
				replaces.put("%min%", String.valueOf(a));
				replaces.put("%max%", String.valueOf(b));
				if(a > b) throw new SyntaxResponseException(Message.INTEGERSYNTAX_PARAMETER_MALFORMED_2.get(TextMode.COLOR, replaces));
				if(passedInt < a || passedInt > b) throw new SyntaxResponseException(Message.INTEGERSYNTAX_HAS_TO_BE_INBETWEEN.get(TextMode.COLOR, replaces));
			}		
		}
	}
	
	public boolean isInt(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) { }
		return false;
	}
	
	public int countOccurrences(String input, String search) {
		int i = 0;
		int lastIndex = 0;

	    while(lastIndex != -1){
	    	lastIndex = input.indexOf(search,lastIndex);
			if( lastIndex != -1) {
				i++;
			}
			lastIndex+=search.length();
	    }
		return i;
	}

}
