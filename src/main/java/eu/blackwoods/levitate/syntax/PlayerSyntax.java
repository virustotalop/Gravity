package eu.blackwoods.levitate.syntax;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.exception.CommandSyntaxException;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class PlayerSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException, CommandSyntaxException {
		if(parameter.equalsIgnoreCase("online") == false && parameter.equalsIgnoreCase("offline") == false && parameter.equals("") == false) throw new CommandSyntaxException("Method 'player' doesn't supports parameter '"+parameter+"'!");
		OfflinePlayer p = Bukkit.getOfflinePlayer(passed);
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%player%", p.getName());
		if(parameter.equalsIgnoreCase("online")) {
			if(!p.isOnline()) throw new SyntaxResponseException(Message.PLAYERSYNTAX_PLAYER_OFFLINE.get(TextMode.COLOR, replaces));
		} else if(parameter.equalsIgnoreCase("offline")) {
			if(p.isOnline()) throw new SyntaxResponseException(Message.PLAYERSYNTAX_PLAYER_ONLINE.get(TextMode.COLOR, replaces));
		}
	}

}
