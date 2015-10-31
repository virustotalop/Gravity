package eu.blackwoods.levitate.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class WorldSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException 
	{
		World w = Bukkit.getWorld(passed);
		if(w == null) 
		{
			HashMap<String, String> replaces = new HashMap<String, String>();
			replaces.put("%world%", passed);
			throw new SyntaxResponseException(Message.WORLDSYNTAX_WORLD_DOES_NOT_EXIST.get(TextMode.COLOR, replaces));
		}
	}

	@Override
	public List<String> getTabComplete(String parameter, String passed) 
	{
		List<String> complete = new ArrayList<String>();
		for(World w : Bukkit.getWorlds())
			complete.add(w.getName());
		return complete;
	}
}
