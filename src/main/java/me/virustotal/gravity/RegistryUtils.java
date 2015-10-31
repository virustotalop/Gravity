package me.virustotal.gravity;

import me.virustotal.gravity.objects.Levitate;

import org.bukkit.plugin.Plugin;


public class RegistryUtils {
	
	public static Levitate newLevitate(Plugin plugin)
	{
		return new Levitate(plugin);
	}
}
