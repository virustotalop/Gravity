package eu.blackwoods.levitate;

import java.util.HashMap;




import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.blackwoods.levitate.CommandInformation.CommandExecutor;
import eu.blackwoods.levitate.Message.TextMode;

public class DefaultHelpMap implements HelpMap {
	
	CommandRegistry registry = null;
	
	public DefaultHelpMap(CommandRegistry commandRegistry) {
		registry = commandRegistry;
	}

	@Override
	public void onHelp(CommandSender sender, String command, String[] args) {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%plugin%", registry.getPlugin().getName());
		sender.sendMessage(Message.DEFAULTHELPMAP_HEADER.get(TextMode.COLOR, replaces));
		int i = 0;
		for(CommandInformation info : registry.getCommands().keySet()) {
			if(info.getPermission() != null)
				if(!registry.getPermissionHandler().hasPermission(sender, info.getPermission())) 
					continue;
			if(info.getCommand().equalsIgnoreCase(getCommandBase(command))) {
				String syntax = info.getSyntax();
				if(syntax.startsWith("?") || syntax.startsWith("$")) syntax = "/" + syntax.substring(1);
				replaces.put("%syntax%", syntax);
				String desc = info.getDescription();
				if(desc == null || desc.equals("")) desc = Message.DEFAULTHELPMAP_NO_DESCRIPTION.get(TextMode.COLOR);
				replaces.put("%description%", desc);
				if(sender instanceof Player && (info.getCommandExecutor() == CommandExecutor.PLAYER || info.getCommandExecutor() == CommandExecutor.ALL)) {
					sender.sendMessage(Message.DEFAULTHELPMAP_HELP_ELEMENT.get(TextMode.COLOR, replaces));
					i++;
				} else if(!(sender instanceof Player) && (info.getCommandExecutor() == CommandExecutor.CONSOLE || info.getCommandExecutor() == CommandExecutor.ALL)) {
					sender.sendMessage(Message.DEFAULTHELPMAP_HELP_ELEMENT.get(TextMode.COLOR, replaces));
					i++;
				}
			} 
		}
		if(i == 0) {
			replaces.put("%command%", command);
			sender.sendMessage(Message.DEFAULTHELPMAP_NO_COMMANDS.get(TextMode.COLOR, replaces));
		}
	}
	
	public String getCommandBase(String command) {
		if(command.startsWith("?") || command.startsWith("/") || command.startsWith("$"))
			command = command.substring(1);
		return command;
	}
	
}
