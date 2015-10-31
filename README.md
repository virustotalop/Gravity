#Gravity
Gravity is a command framework that is based on [Levitate](https://github.com/KennethWussmann/Levitate). Gravity allows each plugin to create their own instance Levitate. Levitate allows you to register commands easily along with tab completion for arguments. Documentation pending for Gravity, below is how a command was registered with Levitate.

#Example
This is a simple kill command:
```Java
SyntaxValidations.registerDefaultSyntax(pluginInstance);
CommandRegistry registry = new CommandRegistry(pluginInstance);
registry.registerBukkitPermissionHandler();
registry.registerDefaultHelpMap();

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
* HelpMap with detailed Command-List
* TabComplete
* Undefined amount of arguments
* Extendable

#Planned features
* Further abstract command registering.
* Build Gravity into a larger framework for other uses.

#License
Gravity & Levitate are licensed under [GNU General Public License Version 2](https://github.com/virustotalop/Gravity/blob/master/LICENSE).
