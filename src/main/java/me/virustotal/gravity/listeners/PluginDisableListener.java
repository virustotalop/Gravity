package me.virustotal.gravity.listeners;

import me.virustotal.gravity.objects.Levitate;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class PluginDisableListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void pluginDisable(PluginDisableEvent e)
	{
		Levitate.unregisterCommands(e.getPlugin());
	}
}