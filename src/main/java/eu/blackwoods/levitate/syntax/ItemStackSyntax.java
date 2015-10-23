package eu.blackwoods.levitate.syntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import eu.blackwoods.levitate.Message;
import eu.blackwoods.levitate.SyntaxHandler;
import eu.blackwoods.levitate.Message.TextMode;
import eu.blackwoods.levitate.exception.SyntaxResponseException;

public class ItemStackSyntax implements SyntaxHandler {
	
	public static HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();  
	public static JavaPlugin plugin = null;
	
	public ItemStackSyntax(JavaPlugin plugin) {
		this.plugin = plugin;
		loadCSV();
	}

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%arg%", passed);
		ItemStack is = null;
		if(items.containsKey(passed.toLowerCase())) {
			is = items.get(passed.toLowerCase());
		} else if(passed.contains(":")) {
			String a = passed.split(":")[0];
			String b = passed.split(":")[1];
			
			if(!isInt(b)) throw new SyntaxResponseException(Message.ITEMSTACKSYNTAX_NO_INTEGER.get(TextMode.COLOR, replaces));
			
			int meta = Integer.parseInt(b);
			replaces.put("%int%", b);
			if(meta < 0)  throw new SyntaxResponseException(Message.ITEMSTACKSYNTAX_POSITIVE_INTEGER.get(TextMode.COLOR, replaces));
			
			if(!isInt(a)) {
				is = items.get(a);
				is.setDurability((short) meta);
				return;
			}
			
			int id = Integer.parseInt(a);
			replaces.put("%int%", a);
			if(id < 0)  throw new SyntaxResponseException(Message.ITEMSTACKSYNTAX_POSITIVE_INTEGER.get(TextMode.COLOR, replaces));
			
			is = new ItemStack(Material.getMaterial(id), 1, (short) meta);
		} else if(isInt(passed)) {
			int i = Integer.parseInt(passed);
			if(i < 0)  throw new SyntaxResponseException(Message.ITEMSTACKSYNTAX_POSITIVE_INTEGER.get(TextMode.COLOR, replaces));
			is = new ItemStack(i);
		}
		if(is == null) throw new SyntaxResponseException(Message.ITEMSTACKSYNTAX_ITEM_NOT_FOUND.get(TextMode.COLOR, replaces));
	}
	
	public static void loadCSV() {
		if(plugin == null) return;
		String csvFile = "plugins/" + plugin.getName() + "/items.csv";
		File f = new File(csvFile);
		if(!f.exists()) {
			csvFile = "plugins/Essentials/items.csv";
			f = new File(csvFile);
			if(!f.exists()) return;
		}
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			items.clear();
			while ((line = br.readLine()) != null) {
				if(line.startsWith("#")) continue;
				if(line.equals("")) continue;
				String[] item = line.split(cvsSplitBy);
				ItemStack is = new ItemStack(Material.getMaterial(Integer.parseInt(item[1])), 1, Short.parseShort(item[2]));
				items.put(item[0].toLowerCase(), is);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean isInt(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) { }
		return false;
	}
	
}
