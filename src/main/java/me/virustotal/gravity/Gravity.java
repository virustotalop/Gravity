package me.virustotal.gravity;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class Gravity extends JavaPlugin {
	
	@Override
	public void onEnable()
	{
		Snooper.loadListeners(this);
		this.getLogger().log(Level.INFO, "Gravity has been applied to Levitate!");
	}
	
	@Override
	public void onDisable()
	{
		Snooper.cleanupStaticVars(this);
	}
}
