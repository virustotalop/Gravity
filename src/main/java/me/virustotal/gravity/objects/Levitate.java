package me.virustotal.gravity.objects;

import org.bukkit.plugin.Plugin;

import eu.blackwoods.levitate.CommandHandler;
import eu.blackwoods.levitate.CommandInformation;
import eu.blackwoods.levitate.CommandRegistry;
import eu.blackwoods.levitate.SyntaxValidations;

public class Levitate {
	
	private Plugin plugin;
	private static boolean syntaxRegistered = false;
	
	public Levitate(Plugin plugin)
	{
		this.plugin = plugin;
		if(!syntaxRegistered)
		{
			syntaxRegistered = true;
			SyntaxValidations.registerDefaultSyntax();
		}
	}
	
	public void registerCommand(String usage, String permission, String description, CommandHandler commandHandler)
	{
		this.registerCommand(new String[] {usage, permission, description}, commandHandler); 
	}
	
	public void registerCommand(String[] objs, CommandHandler commandHandler)
	{
		CommandRegistry commandRegistry = new CommandRegistry(this.plugin);
		commandRegistry.registerBukkitPermissionHandler();
		commandRegistry.registerDefaultHelpMap();
		commandRegistry.register(new CommandInformation(objs[0], objs[1], objs[2]), commandHandler);
	}
}
