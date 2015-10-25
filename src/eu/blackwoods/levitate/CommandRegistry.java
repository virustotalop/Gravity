package eu.blackwoods.levitate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import eu.blackwoods.levitate.CommandInformation.CommandExecutor;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.exception.CommandSyntaxException;
import eu.blackwoods.levitate.exception.ExecutorIncompatibleException;
import eu.blackwoods.levitate.exception.NoPermissionException;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class CommandRegistry {
	
	private HashMap<CommandInformation, CommandHandler> commands = new HashMap<CommandInformation, CommandHandler>();
	private List<String> aliases = new ArrayList<String>();
	private PermissionHandler permissionHandler = null;
	private HelpMap helpMap = null;
	private Environment environment = null;
	private Plugin plugin = null;
	
	/**
	 * This class registers your commands and handels them.
	 * This constructor is for Java-Standalone applications
	 */
	public CommandRegistry() {
		detectEnvironment();
		setEnvironment(Environment.STANDALONE);
	}
	
	/**
	 * This class registers your commands and handels them.
	 * This constructor is for Bukkit/Spigot plugins
	 * @param plugin The JavaPlugin instance
	 */
	public CommandRegistry(Plugin plugin) { 
		detectEnvironment();
		if(plugin == null) return;
		this.plugin = plugin;
		if(getEnvironment() == Environment.STANDALONE) setEnvironment(Environment.BUKKIT);
	}
	
	/**
	 * Register new command
	 * @param info CommandInformation with syntax, permission etc
	 * @param handler The CommandHandler wich handels the execution of the command
	 */
	public void register(CommandInformation info, CommandHandler handler) {
		registerFakeCommand(info, null);
		commands.put(info, handler);
	}
	
	public void registerAlias(String alias) {
		if(aliases.contains(alias.toLowerCase())) return;
		aliases.add(alias.toLowerCase());
	}
	
	/**
	 * Register new command with aliases
	 * @param info CommandInformation with syntax, permission etc
	 * @param aliases Array with aliases
	 * @param handler The CommandHandler wich handels the execution of the command
	 */
	public void register(CommandInformation info, String[] aliases, CommandHandler handler) {
		
		for(String alias : aliases) {
			String ns = "";
			String syntax = info.getSyntax();
			if(syntax.startsWith("?") || syntax.startsWith("/") || syntax.startsWith("$")) {
				ns = syntax.substring(0, 1);
				syntax = syntax.substring(1);
			}
			ns += alias + " ";
			if(syntax.contains(" ")) {
				String[] split = syntax.split(" ");
				for (int i = 0; i < split.length; i++) {
					if(i != 0) {
						ns += split[i] + " ";
					}
				}
			}
			if(ns.endsWith(" ")) ns = ns.substring(0, ns.length()-1);
			CommandInformation cinfo = new CommandInformation(ns, info.getPermission());
			registerAlias(alias);
			commands.put(cinfo, handler);
		}
		registerFakeCommand(info, aliases);
		commands.put(info, handler);
	}
	
	private void registerFakeCommand(final CommandInformation info, String[] aliases) {
		if(aliases == null) aliases = new String[] {};
		if(environment == Environment.SPIGOT || environment == Environment.BUKKIT) {
			try {
				final Field f = getPlugin().getServer().getClass().getDeclaredField("commandMap");
				f.setAccessible(true);
	            CommandMap cmap = (CommandMap)f.get(getPlugin().getServer());
	            cmap.register(info.getCommand(), new Command(info.getCommand(), info.getDescription(), info.getSyntax(), new ArrayList<String>(Arrays.asList(aliases))) {
					
	            	@Override
	            	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
	            		List<String> complete = handleTabComplete(sender, alias, args);
	            		if(complete == null) return super.tabComplete(sender, alias, args);
	            		return complete;
	            	}
	            	
					@Override
					public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
						try {
							
							return playerPassCommand(arg0, arg1, arg2);
						} catch (CommandSyntaxException | NoPermissionException | SyntaxResponseException e) {
							if(e instanceof NoPermissionException) {
								arg0.sendMessage(Message.NO_PERMISSION.get(TextMode.COLOR));
								return true;
							}
							if(e instanceof SyntaxResponseException) {
								arg0.sendMessage(e.getMessage());
								return true;
							}
							e.printStackTrace();
						}
						return false;
					}
				});
			} catch (IllegalArgumentException | SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Get strings which fit the entered argument
	 * @param sender CommandSender
	 * @param command Base-Command or alias
	 * @param args
	 * @return List of strings which could fit the entered argument
	 */
	private List<String> handleTabComplete(CommandSender sender, String command, String[] args) {
		if(args.length == 0) return null;
		List<String> complete = new ArrayList<String>();
		int lastArg = args.length-1;
		String arg = args[lastArg];
		CommandExecutor ce = CommandExecutor.PLAYER;
		if(!(sender instanceof Player)) ce = CommandExecutor.CONSOLE;
		
		for(CommandInformation info : commands.keySet()) {
			if(!info.getCommand().equalsIgnoreCase(command)) continue;
			if(permissionHandler != null && info.getPermission() != null) {
				if(!permissionHandler.hasPermission(sender, info.getPermission())) {
					continue;
				}
			}
			CommandExecutor cmdExec = null;
			if(sender instanceof Player) {
				cmdExec = CommandExecutor.PLAYER;
			} else {
				cmdExec = CommandExecutor.CONSOLE;
			}
			
			switch(info.getCommandExecutor()) {
			case CONSOLE:
				if(cmdExec != CommandExecutor.CONSOLE) continue;
				break;
			case PLAYER:
				if(cmdExec != CommandExecutor.PLAYER) continue;
				break;
			}
			Argument exArg = info.getArgs().get(lastArg);
			if(exArg == null) continue;
			complete.addAll(exArg.getHandler().getTabComplete(exArg.getParameter(), arg));
		}
		Iterator<String> iComplete = complete.iterator();
		while(iComplete.hasNext()) {
			String str = iComplete.next();
			if(!str.toLowerCase().startsWith(arg.toLowerCase())) iComplete.remove();
		}
		Collections.sort(complete, new Comparator<String>() {
	        @Override
	        public int compare(String s1, String s2) {
	            return s1.compareToIgnoreCase(s2);
	        }
	    });	
		return complete;
	}
	
	/**
	 * Register default Bukkit/Spigot PermissionHandler.
	 * It uses the hasPermission() method of Bukkit/Spigot to check permissions.
	 */
	public void registerBukkitPermissionHandler() {
		permissionHandler = new PermissionHandler() {
			
			@Override
			public boolean hasPermission(Object sender, String permission) {
				if(sender instanceof CommandSender) {
					if(!((CommandSender)sender).hasPermission(permission)) return false;
				}
				return true;
			}
		};
	}
	
	/**
	 * Register your own PermissionHandler
	 * @param permissionHandler PermissionHandler wich checks whether the sender has permission to execute the command
	 */
	public void registerPermissionHandler(PermissionHandler permissionHandler) {
		this.permissionHandler = permissionHandler;
	}
	
	/**
	 * Register default HelpMap
	 */
	public void registerDefaultHelpMap() {
		this.helpMap = new DefaultHelpMap(this);
	}
	
	/**
	 * Register own HelpMaoo
	 * @param helpMap Handles the help-message
	 */
	public void registerHelpMap(HelpMap helpMap) {
		this.helpMap = helpMap;
	}
	
	/**
	 * Execute a command. This method is for Java-Standalone applications.
	 * This feature doesn't work at the moment.
	 * @param command The base-command
	 * @param args Arguments
	 * @return
	 * @throws CommandSyntaxException
	 * @throws SyntaxResponseException
	 * @throws NoPermissionException
	 * @throws ExecutorIncompatibleException
	 */
	@Deprecated
	public boolean userPassCommand(String command, String[] args) throws CommandSyntaxException, SyntaxResponseException, NoPermissionException, ExecutorIncompatibleException {
		for(CommandInformation i : commands.keySet()) {
			if(i.matches(CommandExecutor.PLAYER, command, args)) {
				if(permissionHandler != null && i.getPermission() != null) {
					if(!permissionHandler.hasPermission(null, i.getPermission())) {
						throw new NoPermissionException(Message.NO_PERMISSION.get(TextMode.COLOR));
					}
				}
				commands.get(i).execute(null, command, new ParameterSet(args));
				return true;
			}
		}
		if(helpMap != null) helpMap.onHelp(null, command, args);
		return false;
	}
	
	/**
	 * Executes a command as a Bukkit/Spigot player or console.
	 * You don't need to call it.
	 * @param sender The sender of the command
	 * @param command The base-command
	 * @param args The arguments
	 * @return
	 * @throws CommandSyntaxException
	 * @throws SyntaxResponseException
	 * @throws NoPermissionException
	 */
	public boolean playerPassCommand(CommandSender sender, String command, String[]args) throws CommandSyntaxException, NoPermissionException, SyntaxResponseException {
		CommandExecutor ce = CommandExecutor.PLAYER;
		if(!(sender instanceof Player)) ce = CommandExecutor.CONSOLE;
		boolean found = false;
		SyntaxResponseException exeption = null;
		for(CommandInformation i : commands.keySet()) {
			if(found == true) continue;
			try {
				if(i.matches(ce, command, args)) {
					if(permissionHandler != null && i.getPermission() != null) {
						if(!permissionHandler.hasPermission(sender, i.getPermission())) {
							throw new NoPermissionException(Message.NO_PERMISSION.get(TextMode.COLOR));
						}
					}
					commands.get(i).execute(sender, command, new ParameterSet(args));
					found = true;
				}
			} catch (ExecutorIncompatibleException | SyntaxResponseException e) {
				if(e instanceof ExecutorIncompatibleException) {
					sender.sendMessage(e.getMessage());
					found = true;
				} else {
					exeption = (SyntaxResponseException) e;
				}
			}
		}
		if(helpMap != null && found == false) {
			helpMap.onHelp(sender, command, args);
		} else if(helpMap == null && found == false) {
			throw exeption;
		}
		return found;
	}
	
	public static boolean existClass(String clazz) {
		try {
			Class.forName(clazz);
			return true;
		} catch (Exception e) { }
		return false;
	}

	public HashMap<CommandInformation, CommandHandler> getCommands() {
		return commands;
	}

	public void setCommands(HashMap<CommandInformation, CommandHandler> commands) {
		this.commands = commands;
	}

	public PermissionHandler getPermissionHandler() {
		return permissionHandler;
	}

	public void setPermissionHandler(PermissionHandler permissionHandler) {
		this.permissionHandler = permissionHandler;
	}
	
	/**
	 * Auto-Detect the Environment
	 */
	public void detectEnvironment() {
		try {
			Class.forName("org.bukkit.Server.Spigot");
			environment = Environment.SPIGOT;
		} catch (Exception e) {
			try {
				Class.forName("org.Bukkit.Bukkit");
				environment = Environment.BUKKIT;
			} catch (Exception ee) {
				environment = Environment.STANDALONE;
			}
		}
		
		
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	
	
}
