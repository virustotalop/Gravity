package eu.blackwoods.levitate;

import java.util.HashMap;

import org.bukkit.ChatColor;


public enum Message {
	NO_PERMISSION("You don't have permission!"),
	ONLY_INGAME("This command is for ingame players!"),
	ONLY_CONSOLE("This command is only for the console!"),
	COMMAND_DOESNT_EXIST("This command doesn't exist!"),
	CI_ARG_HAS_TO_START_WITH_CHAR("The argument \"%arg%\" has to start with \"<\"!"),
	CI_ARG_HAS_TO_END_WITH_CHAR("The argument \"%arg%\" has to start with \">\"!"),
	CI_NO_SYNTAX("There is no syntax for \"%method%\"!"),
	CI_ERROR_AT_CHAR_IN_ARG("Error at character \"%char%\" in \"%arg%\"!"),
	CI_ERROR_AT_CHAR("Error at character \"%char%\"!"),
	CI_CMD_CANNOT_START_WITH("Command cannot start with \"<\"!"),
	BOOLEANSYNTAX_HAS_TO_BE_BOOLEAN("Argument \"%arg%\" has to be a boolean!"),
	CHOICESYNTAX_NOT_LIST("Parameter \"%arg%\" has to be a list of choices!"),
	CHOICESYNTAX_NOT_COMMA_SEPARATED("Parameter \"%arg%\" has to be a comma-separated list!"),
	CHOICESYNTAX_NOT_IN_LIST("Argument \"%arg%\" has to be a value of theese \"%list%\"!"),
	ENUMSYNTAX_NEEDS_CLASSPATH("Method \"enum\" needs the classpath to an enum!"),
	ENUMSYNTAX_CLASS_DOESNT_EXIST("Class \"%class%\" in method \"enum\" doesn\"t exist!"),
	ENUMSYNTAX_ARG_NOT_IN_ENUM("Argument \"%arg%\" has to be a value of theese \"%list%\"! It\"s case-insensitive."),
	EQUALSIGNORECASESYNTAX_DOESNT_EQUAL("Argument \"%arg%\" has to equal \"%value%\"!"),
	EQUALSSYNTAX_DOESNT_EQUAL("Argument \"%arg%\" has to equal \"%value%\"!"),
	INTEGERSYNTAX_HAS_NO_INTEGER("Argument \"%arg%\" has to be a number!"),
	INTEGERSYNTAX_HAS_TO_BE_POSITIVE_ZERO("Argument \"%arg%\" has to be a positive number or zero!"),
	INTEGERSYNTAX_HAS_TO_BE_NEGATIVE_ZERO("Argument \"%arg%\" has to be a negative number or zero!"),
	INTEGERSYNTAX_PARAMETER_MALFORMED("Parameter \"%parameter%\" is malformed!"),
	INTEGERSYNTAX_PARAMETER_MALFORMED_2("Parameter \"%parameter%\" is malformed! The left number has to be smaller then the right one."),
	INTEGERSYNTAX_HAS_TO_BE_INBETWEEN("Argument \"%arg%\" has to be inbetween \"%min%\" and \"%max%\"!"),
	DOUBLESYNTAX_HAS_NO_DOUBLE("Argument \"%arg%\" has to be a decimal number!"),
	DOUBLESYNTAX_HAS_TO_BE_POSITIVE_ZERO("Argument \"%arg%\" has to be a positive decimal number or zero!"),
	DOUBLESYNTAX_HAS_TO_BE_NEGATIVE_ZERO("Argument \"%arg%\" has to be a negative decimal number or zero!"),
	DOUBLESYNTAX_PARAMETER_MALFORMED("Parameter \"%parameter%\" is malformed!"),
	DOUBLESYNTAX_PARAMETER_MALFORMED_2("Parameter \"%parameter%\" is malformed! The left decimal number has to be smaller then the right one."),
	DOUBLESYNTAX_HAS_TO_BE_INBETWEEN("Argument \"%arg%\" has to be inbetween \"%min%\" and \"%max%\"!"),
	NOTEQUALSIGNORECASESYNTAX_CANNOT_EQUAL("Argument \"%arg%\" cannot equal \"%value%\"!"),
	NOTEQUALSSYNTAX_CANNOT_EQUAL("Argument \"%arg%\" cannot equal \"%value%\"!"),
	PLAYERSYNTAX_PLAYER_OFFLINE("The player \"%player%\" has to be online!"),
	PLAYERSYNTAX_PLAYER_ONLINE("The player \"%player%\" has to be offline!"),
	STRINGSYNTAX_CANNOT_BE_INT("Argmuent \"%arg%\" cannot be a number!"),
	STRINGSYNTAX_ONLY_LOWERCASE("Argument \"%arg%\" has to contain only lower-case letters!"),
	STRINGSYNTAX_ONLY_UPPERCASE("Argument \"%arg%\" has to contain only upper-case letters!"),
	ITEMSTACKSYNTAX_NO_INTEGER("Argument \"%arg%\" has to be an item separated by \":\"!"),
	ITEMSTACKSYNTAX_POSITIVE_INTEGER("Number \"%int%\" has to be positive or zero!"),
	ITEMSTACKSYNTAX_ITEM_NOT_FOUND("Item \"%arg%\" doesn't exist!");
	
	String message;
	
	Message(String message) {
		this.message = message;
	}
	
	public String get(TextMode mode) {
		String raw = message;
		switch(mode) {
		case COLOR:
			return ChatColor.translateAlternateColorCodes('&', raw);
		case PLAIN:
			return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', raw));
		case RAW:
			return raw;
		}
		return raw;
	}
	
	public String get(TextMode mode, HashMap<String, String> replaces) {
		String message = get(mode);
		for(String key : replaces.keySet()) 
			message = message.replace(key, replaces.get(key));
		return message;
	}
	
	public enum TextMode {
		RAW, PLAIN, COLOR
	}
}
