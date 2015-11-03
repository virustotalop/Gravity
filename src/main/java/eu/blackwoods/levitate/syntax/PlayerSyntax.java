package eu.blackwoods.levitate.syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.exception.CommandSyntaxException;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class PlayerSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException 
	{
		if(parameter.equalsIgnoreCase("online") == false  && parameter.equals("") == false) 
			throw new CommandSyntaxException("Method 'player' doesn't supports parameter '"+parameter+"'!");
		Player p = Bukkit.getPlayer(passed);
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%player%", p.getName());
		if(parameter.equalsIgnoreCase("online")) 
		{
			if(!p.isOnline()) 
				throw new SyntaxResponseException(Message.PLAYERSYNTAX_PLAYER_OFFLINE.get(TextMode.COLOR, replaces));
		} 
	}
	
	@Override
	public List<String> getTabComplete(String parameter, String passed) 
	{
		List<String> playerList = new ArrayList<String>();
		for(Player p : Bukkit.getOnlinePlayers())
			playerList.add(p.getName());
		return playerList;
	}

}
