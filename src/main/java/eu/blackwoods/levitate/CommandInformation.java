package eu.blackwoods.levitate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.exception.CommandSyntaxException;
import eu.blackwoods.levitate.exception.ExecutorIncompatibleException;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class CommandInformation {

	private String syntax;
	private String command;
	private String permission;
	private String description = "";
	private CommandExecutor commandExecutor;
	private List<Argument> args = new ArrayList<Argument>();
	
	/**
	 * Create a CommandInformation for a new command
	 * @param syntax Your syntax
	 */
	public CommandInformation(String syntax) {
		this.syntax = syntax;
		try {
			processSyntax();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a CommandInformation with permission for a new command
	 * @param syntax Your syntax
	 * @param permission Your new permission
	 */
	public CommandInformation(String syntax, String permission) {
		this.permission = permission;
		this.syntax = syntax;
		try {
			processSyntax();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a CommandInformation with permission and description for a new command
	 * @param syntax Your syntax
	 * @param permission Your new permission
	 * @param description Your description of your command
	 */
	public CommandInformation(String syntax, String permission, String description) {
		this.permission = permission;
		this.syntax = syntax;
		this.description = description;
		try {
			processSyntax();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void processSyntax() throws CommandSyntaxException {
		
		if(!syntax.contains(" ")) {
			processCommandBase(syntax);
			return;
		}
		
		for(MatchResult mr : SyntaxValidations.allMatches(Pattern.compile("<([^>]*)>|([\\/|\\?|\\$][.^\\w]*)"), syntax)) {
			String arg = mr.group();
			if(arg.startsWith("$")|arg.startsWith("?")|arg.startsWith("/")) {
				processCommandBase(arg);
				continue;
			}
			HashMap<String,String> replaces = new HashMap<String, String>();
			replaces.put("%arg%", arg);
			
			if(!arg.startsWith("<")) throw new CommandSyntaxException(Message.CI_ARG_HAS_TO_START_WITH_CHAR.get(TextMode.COLOR, replaces));
			if(!arg.endsWith(">")) throw new CommandSyntaxException(Message.CI_ARG_HAS_TO_END_WITH_CHAR.get(TextMode.COLOR, replaces));
			arg = arg.substring(1, arg.length()-1);
			String method = parseArgument(arg).get(0);
			if(!SyntaxValidations.existHandler(method)) {
				replaces.clear();
				replaces.put("%method%", method);
				throw new CommandSyntaxException(Message.CI_NO_SYNTAX.get(TextMode.COLOR, replaces));
			}
			this.args.add(new Argument(method, parseArgument(arg).get(1), SyntaxValidations.syntaxes.get(method)));
		}
	}
	
	public boolean matchArgument(String input, String syntaxArg) throws CommandSyntaxException {
		List<String> i = parseArgument(syntaxArg);
		for(SyntaxHandler h : SyntaxValidations.syntaxes.values()) {
			try {
				h.check(i.get(1), input);
				return true;
			} catch (Exception e) { }
		}
		return false;
	}
			
	private List<String> parseArgument(String arg) throws CommandSyntaxException {
		List<String> i = new ArrayList<String>();
		String method = "";
		String parameters = "";
		if(arg.contains("[") && arg.contains("]")) {
			boolean start = false;
			boolean end = false;
			for(char c : arg.toCharArray()) {
				String ch = String.valueOf(c);	
				HashMap<String,String> replaces = new HashMap<String, String>();
				replaces.put("%char%", "[");
				replaces.put("%arg%", arg);
				if(ch.equals("[")) {
					if(start) throw new CommandSyntaxException(Message.CI_ERROR_AT_CHAR_IN_ARG.get(TextMode.COLOR, replaces));
					if(end) throw new CommandSyntaxException(Message.CI_ERROR_AT_CHAR_IN_ARG.get(TextMode.COLOR, replaces));
					start = true;
					continue;
				}
				if(ch.equals("]")) {
					replaces.put("%char%", "]");
					if(!start) throw new CommandSyntaxException(Message.CI_ERROR_AT_CHAR_IN_ARG.get(TextMode.COLOR, replaces));
					if(end) throw new CommandSyntaxException(Message.CI_ERROR_AT_CHAR_IN_ARG.get(TextMode.COLOR, replaces));
					end = true;
					continue;
				}
				if(!start && !end) {
					method += ch;
				}else if(start == true && end == false) {
					parameters += ch;
				} else {
					replaces.clear();
					replaces.put("%char%", ch);
					throw new CommandSyntaxException(Message.CI_ERROR_AT_CHAR.get(TextMode.COLOR, replaces));
				}
			}
		} else {
			method = arg;
		}
		i.add(method);
		i.add(parameters);
		return i;
	}
	
	private void processCommandBase(String base) throws CommandSyntaxException {
		if(base.toLowerCase().startsWith("?")) {
			commandExecutor = CommandExecutor.ALL;
		}
		if(base.toLowerCase().startsWith("/")) {
			commandExecutor = CommandExecutor.PLAYER;
		}
		if(base.toLowerCase().startsWith("$")) {
			commandExecutor = CommandExecutor.CONSOLE;
		}
		base = base.substring(1);
		if(base.startsWith("<")) throw new CommandSyntaxException(Message.CI_CMD_CANNOT_START_WITH.get(TextMode.COLOR));
		this.command = base;
	}
	
	
	public boolean matches(CommandExecutor sender, String command, String[] args) throws CommandSyntaxException, SyntaxResponseException, ExecutorIncompatibleException {
		if(this.args.size() != args.length) return false;
		if(!this.command.equalsIgnoreCase(command)) return false;
		
		switch(commandExecutor) {
		case CONSOLE:
			if(sender != CommandExecutor.CONSOLE) throw new ExecutorIncompatibleException(Message.ONLY_CONSOLE.get(TextMode.COLOR));
			break;
		case PLAYER:
			if(sender != CommandExecutor.PLAYER) throw new ExecutorIncompatibleException(Message.ONLY_INGAME.get(TextMode.COLOR));
			break;
		}
		
		if(args == null || args.length == 0) {
			if(this.args.size() != args.length) return false;
		}
		
		int i = 0; 
		while(i < args.length) {
			Argument exArg = this.args.get(i);
			String arg = args[i];
			try {
				exArg.getHandler().check(exArg.getParameter(), arg);
			} catch (SyntaxResponseException e) {
				throw e;
			}
			i++;
		}
		
		if(this.args.size() == i) return true;
		return false;
	}
	
	enum CommandExecutor {
		CONSOLE(),
		PLAYER(),
		ALL();
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	public List<Argument> getArgs() {
		return args;
	}

	public void setArgs(List<Argument> args) {
		this.args = args;
	}
	
	
}
