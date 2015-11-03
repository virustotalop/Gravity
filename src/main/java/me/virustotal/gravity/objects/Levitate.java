package me.virustotal.gravity.objects;

import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import eu.blackwoods.levitate.CommandHandler;
import eu.blackwoods.levitate.CommandInformation;
import eu.blackwoods.levitate.CommandRegistry;
import eu.blackwoods.levitate.SyntaxValidations;

public class Levitate {
	
	private Plugin plugin;
	private static boolean syntaxRegistered = false;
	private static ArrayList<CommandRegistry> registries = new ArrayList<CommandRegistry>();
	
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
		boolean added = false;
		for(CommandRegistry reg : registries)
		{
			if(reg.getPlugin().getName().equals(this.plugin.getName()))
			{
				commandRegistry = reg;
				added = true;
				break;
			}
		}
		
		commandRegistry.registerBukkitPermissionHandler();
		commandRegistry.registerDefaultHelpMap();
		String usage = objs[0];
		String permission = objs[1];
		String description = objs[2];
		commandRegistry.register(new CommandInformation(usage, permission, description), commandHandler);
		
		if(!added)
			Levitate.registries.add(commandRegistry);
	}
	
	public void registerAlias(String command, String alias)
	{
		for(CommandRegistry reg : registries)
		{
			for(CommandInformation info : reg.getCommands().keySet())
			{
				if(info.getCommand().equals(command))
				{	reg.registerAlias(command, alias);
					return;
				}
			}
		}
	}
	
	public static void unregisterCommands(Plugin plugin)
	{
		for(CommandRegistry reg : registries)
		{
			if(reg.getPlugin().getName().equals(plugin.getName()))
			{
				reg.unregister(plugin);
				registries.remove(reg);
				reg = null; //Set reg to null, will need to check later if other objects are not dereferenced
				break;
			}
		}
	}
}