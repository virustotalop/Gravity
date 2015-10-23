package eu.blackwoods.levitate;


public interface PermissionHandler {
	
	/**
	 * Check if sender has the given permission
	 * @param sender The sender, in Bukkit/Spigot plugins it's CommandSender
	 * @param permission The permission
	 * @return Return true if the sender has permission
	 */
	public boolean hasPermission(Object sender, String permission);
	
}
