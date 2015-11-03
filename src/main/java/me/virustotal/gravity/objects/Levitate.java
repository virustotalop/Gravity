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
	private static ArrayList<Levitate> levitateInstances = new ArrayList<Levitate>();
	
	public Levitate(Plugin plugin)
	{
		this.plugin = plugin;
		this.setupInstance();
	}
	
	private void setupInstance()
	{
		if(!syntaxRegistered)
		{
			syntaxRegistered = true;
			SyntaxValidations.registerDefaultSyntax();
		}
		for(Levitate levitate : levitateInstances)
		{
			if(levitate.plugin.getName().equals(this.plugin.getName()))
				return;
		}
		
		levitateInstances.add(this);
	}
	
	public void registerCommand(String usage, String permission, String description, CommandHandler commandHandler, String... aliases)
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
		
		if(aliases != null)
			commandRegistry.register(new CommandInformation(usage, permission, description), aliases, commandHandler);
		else
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
				Levitate.levitateInstances.remove(Levitate.getInstance(plugin));
				break; 
			}
		}
	}
	
	public static Levitate getInstance(Plugin plugin)
	{
		for(Levitate levitate : Levitate.levitateInstances)
		{
			if(levitate.plugin.getName().equals(plugin.getName()))
				return levitate;
		}
		
		return null;
	}
}