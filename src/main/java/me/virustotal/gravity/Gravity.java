package me.virustotal.gravity;

import org.bukkit.plugin.java.JavaPlugin;

public class Gravity extends JavaPlugin {
	
	@Override
	public void onEnable()
	{
		
	}
	
	@Override
	public void onDisable()
	{
		Snooper.cleanupStaticVars(this);
	}
}
