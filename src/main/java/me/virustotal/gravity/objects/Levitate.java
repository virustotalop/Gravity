package me.virustotal.gravity.objects;

import org.bukkit.plugin.Plugin;

import eu.blackwoods.levitate.CommandHandler;
import eu.blackwoods.levitate.CommandInformation;
import eu.blackwoods.levitate.CommandRegistry;
import eu.blackwoods.levitate.SyntaxValidations;

public class Levitate {
	
	private Plugin plugin;
	private SyntaxValidations syntaxValidations;
	
	public Levitate(Plugin plugin)
	{
		this.plugin = plugin;
		this.syntaxValidations = new SyntaxValidations(plugin);
		this.syntaxValidations.registerDefaultSyntax();
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
