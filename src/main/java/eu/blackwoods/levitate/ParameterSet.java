package eu.blackwoods.levitate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.blackwoods.levitate.syntax.ItemStackSyntax;

public class ParameterSet {
	
	List<String> parameter = new ArrayList<String>();
	
	/**
	 * The collection of parameters
	 * @param args
	 */
	public ParameterSet(String[] args) {
		for(String arg : args)
			parameter.add(arg);
	}
	
	/**
	 * Get argument as String
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public String getString(int i) {
		return parameter.get(i);
	}

	/**
	 * Get argument as Integer
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public int getInt(int i) {
		return Integer.parseInt(parameter.get(i));
	}
	
	/**
	 * Get argument as Double
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public double getDouble(int i) {
		return Double.parseDouble(parameter.get(i));
	}

	/**
	 * Get argument as Boolean
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public boolean getBoolean(int i) {
		String arg = parameter.get(i);
		if(arg.equalsIgnoreCase("true")) return true;
		if(arg.equalsIgnoreCase("false")) return false;
		if(arg.equalsIgnoreCase("0")) return false;
		if(arg.equalsIgnoreCase("1")) return true;
		if(arg.equalsIgnoreCase("on")) return true;
		if(arg.equalsIgnoreCase("off")) return false;
		if(arg.equalsIgnoreCase("an")) return true;
		if(arg.equalsIgnoreCase("aus")) return false;
		if(arg.equalsIgnoreCase("ja")) return true;
		if(arg.equalsIgnoreCase("nein")) return false;
		if(arg.equalsIgnoreCase("yes")) return true;
		if(arg.equalsIgnoreCase("no")) return false;
		if(arg.equalsIgnoreCase("enable")) return true;
		if(arg.equalsIgnoreCase("disable")) return false;
		return false;
	}

	/**
	 * Get argument as Player. Player is null if he is offline
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public Player getPlayer(int i) {
		return Bukkit.getPlayer(parameter.get(i));
	}

	/**
	 * Get argument as OfflinePlayer
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public OfflinePlayer getOfflinePlayer(int i) {
		return Bukkit.getOfflinePlayer(parameter.get(i));
	}
	
	/**
	 * Get argument as ItemStack
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public ItemStack getItemStack(int i) {
		ItemStack is = null;
		String arg = parameter.get(i);
		if(ItemStackSyntax.items.containsKey(arg.toLowerCase())) {
			is = ItemStackSyntax.items.get(arg.toLowerCase());
		} else if(arg.contains(":")) {
			String a = arg.split(":")[0];
			String b = arg.split(":")[1];
			int id = 0;
			int meta = Integer.parseInt(b);
			if(!isInt(a)) {
				is = ItemStackSyntax.items.get(a);
				is.setDurability((short) meta);
				return is;
			}
			is = new ItemStack(Material.getMaterial(id), 1, (short) meta);
		} else if(isInt(arg)) {
			is = new ItemStack(Integer.parseInt(arg));
		}
		return is;
	}
	
	/**
	 * Get arguments from a to b as String delimited by a space
	 * @param start Start index 
	 * @param end End index
	 * @return
	 */
	public String getString(int start, int end) {
		return getString(start, end, " ");
	}
	
	/**
	 * Get arguments from a to b as String delimited by entered String
	 * @param start Start index 
	 * @param end End index
	 * @param delimiter Combine Strings with delimiter
	 * @return
	 */
	public String getString(int start, int end, String delimiter) {
		try {
			String str = "";
			for(String s : parameter.subList(start, end)) {
				str += s + delimiter;
			}
			if(str.endsWith(delimiter)) str.substring(0, str.length()-2);
			return str;
		} catch (IndexOutOfBoundsException e) { }
		return null;
	}
	
	/**
	 * Get arguments from a to the end of all arguments delimited by space
	 * @param start Start index 
	 * @return
	 */
	public String getMessage(int start) {
		return getString(start, parameter.size());
	}

	/**
	 * Get argument as world
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public World getWorld(int i) {
		return Bukkit.getWorld(parameter.get(i));
	}
	
	/**
	 * Get argument as URL
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public URL getURL(int i) {
		try {
			return new URL(parameter.get(i));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get argument as Object
	 * @param i The index in your command, starts at 0
	 * @return
	 */
	public Object get(int i) {
		return (Object) parameter.get(i);
	}
	
	
	private boolean isInt(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) { }
		return false;
	}
}
