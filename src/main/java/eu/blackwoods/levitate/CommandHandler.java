package eu.blackwoods.levitate;

import org.bukkit.command.CommandSender;

public interface CommandHandler {
	
	/**
	 * Handles the registered command.
	 * This method only get's called when the user-input matches the syntax and he has permission.
	 * @param sender The CommandSender
	 * @param command The base-command
	 * @param args A set of arguments. This is used to get vaild parsed values of the user-input
	 */
	public void execute(CommandSender sender, String command, ParameterSet args);
	
}
