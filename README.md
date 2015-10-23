# Levitate
Levitate is an awesome and easy to use Command-Library for Bukkit/Spigot-Plugins.<br>
It allowes you to register commands with arguments, permissions and description in seconds. There also is no need to register it in the plugin.yml.

#Example
This is a simple kill command:
```Java
SyntaxValidations.registerDefaultSyntax(pluginInstance);
CommandRegistry registry = new CommandRegistry(pluginInstance);
registry.registerBukkitPermissionHandler();
		
registry.register(new CommandInformation("/kill <player[online]>", "kill.player", "Kill a player"), new CommandHandler() {
			
	@Override
	public void execute(CommandSender sender, String command, ParameterSet args) {
		Player p = args.getPlayer(0);
		p.setHealth(0);
		sender.sendMessage("Player " + p.getName() + " has been killed!");
	}
			
});
```

#Features
* Checks arguments to be vaild
* Checks permission
* Registers command directly to Bukkit/Spigot without plugin.yml
* Supports command aliases
* Bukkit/Spigot-Version independent
* Extendable

#Getting started
Please check [the wiki](https://github.com/KennethWussmann/Levitate/wiki) to get started!

#Planned features
* HelpMap with detailed Command-List
* Commands with undefined amount of arguments
* TabComplete

#License
Levitate is licensed under [GNU General Public License Version 2](https://github.com/KennethWussmann/Levitate/blob/master/LICENSE).
