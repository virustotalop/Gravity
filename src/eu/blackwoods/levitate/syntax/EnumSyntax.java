package eu.blackwoods.levitate.syntax;

import java.lang.reflect.Field;
import java.util.HashMap;

import eu.blackwoods.levitate.CommandRegistry;
import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.exception.CommandSyntaxException;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class EnumSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException {
		if(parameter.equals("")) throw new CommandSyntaxException(Message.ENUMSYNTAX_NEEDS_CLASSPATH.get(TextMode.COLOR));
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%class%", parameter);
		if(!CommandRegistry.existClass(parameter)) throw new CommandSyntaxException(Message.ENUMSYNTAX_CLASS_DOESNT_EXIST.get(TextMode.COLOR, replaces));
		try {
			Class<?> cls = Class.forName(parameter);
			cls.getDeclaredField(passed.toUpperCase());
		} catch (NoSuchFieldException | ClassNotFoundException e) {
			if(e instanceof NoSuchFieldException) {
				try {
					Class<?> cls = Class.forName(parameter);
					String fields = "";
					for(Field f : cls.getFields()) {
						fields += correctCase(f.getName()) + ", ";
					}
					fields = fields.substring(0, fields.length()-2);
					replaces.clear();
					replaces.put("%arg%", correctCase(passed));
					replaces.put("%list%", fields);
					throw new SyntaxResponseException(Message.ENUMSYNTAX_ARG_NOT_IN_ENUM.get(TextMode.COLOR, replaces));
				} catch (ClassNotFoundException e2) { }
			}
			e.printStackTrace();
		}
	}
	
	public String correctCase(String input) {
		String i = "";
		int count = 0;
		for(char ch : input.toCharArray()) {
			String c = String.valueOf(ch).toLowerCase();
			if(count == 0) c = c.toUpperCase();
			i += c;
			count++;
		}
		return i;
	}

}
