package eu.blackwoods.levitate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import eu.blackwoods.levitate.syntax.BooleanSyntax;
import eu.blackwoods.levitate.syntax.ChoiceIgnoreCaseSyntax;
import eu.blackwoods.levitate.syntax.ChoiceSyntax;
import eu.blackwoods.levitate.syntax.DoubleSyntax;
import eu.blackwoods.levitate.syntax.EnumSyntax;
import eu.blackwoods.levitate.syntax.EqualsIgnoreCaseSyntax;
import eu.blackwoods.levitate.syntax.EqualsSyntax;
import eu.blackwoods.levitate.syntax.IntegerSyntax;
import eu.blackwoods.levitate.syntax.ItemStackSyntax;
import eu.blackwoods.levitate.syntax.NotEqualsIgnoreCaseSyntax;
import eu.blackwoods.levitate.syntax.NotEqualsSyntax;
import eu.blackwoods.levitate.syntax.PlayerSyntax;
import eu.blackwoods.levitate.syntax.StringSyntax;
import eu.blackwoods.levitate.syntax.URLSyntax;
import eu.blackwoods.levitate.syntax.WildcardSyntax;
import eu.blackwoods.levitate.syntax.WorldSyntax;

public class SyntaxValidations {
	
	private Plugin plugin;
	public SyntaxValidations(Plugin plugin)
	{
		this.plugin = plugin;
	}
	
	private HashMap<String, SyntaxHandler> syntaxes = new HashMap<String, SyntaxHandler>();
	
	/**
	 * Register default syntaxes to create your command
	 */
	public void registerDefaultSyntax() {
		registerSyntax("boolean", new BooleanSyntax());
		registerSyntax("int", new IntegerSyntax());
		registerSyntax("double", new DoubleSyntax());
		registerSyntax("string", new StringSyntax());
		registerSyntax("eq", new EqualsSyntax());
		registerSyntax("neq", new NotEqualsSyntax());
		registerSyntax("eqi", new EqualsIgnoreCaseSyntax());
		registerSyntax("neqi", new NotEqualsIgnoreCaseSyntax());
		registerSyntax("enum", new EnumSyntax());
		registerSyntax("choice", new ChoiceSyntax());
		registerSyntax("choicei", new ChoiceIgnoreCaseSyntax());
		registerSyntax("player", new PlayerSyntax());
		registerSyntax("item", new ItemStackSyntax(this.plugin));
		registerSyntax("*", new WildcardSyntax());
		registerSyntax("world", new WorldSyntax());
		registerSyntax("url", new URLSyntax());
	}
	
	/**
	 * Register your own syntax.
	 * @param method The base command of your syntax
	 * @param handler The handler to check values against your syntax
	 */
	public void registerSyntax(String method, SyntaxHandler handler) 
	{
		this.syntaxes.put(method, handler);
	}
	
	public boolean existHandler(String method) 
	{
		for(String m : this.syntaxes.keySet()) 
			if(m.equalsIgnoreCase(method)) 
				return true;
		return false;
	}
	
	public static Iterable<MatchResult> allMatches(final Pattern p, final CharSequence input) 
	{
		return new Iterable<MatchResult>() {
			public Iterator<MatchResult> iterator() {
				return new Iterator<MatchResult>() {
					final Matcher matcher = p.matcher(input);
					MatchResult pending;

					public boolean hasNext() 
					{
						if (pending == null && matcher.find()) 
						{
							pending = matcher.toMatchResult();
						}
						return pending != null;
					}

					public MatchResult next() {
						if (!hasNext()) {
							throw new NoSuchElementException();
						}
						MatchResult next = pending;
						pending = null;
						return next;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
}
