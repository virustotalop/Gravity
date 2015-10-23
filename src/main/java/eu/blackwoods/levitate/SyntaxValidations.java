package eu.blackwoods.levitate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.plugin.java.JavaPlugin;

import eu.blackwoods.levitate.syntax.BooleanSyntax;
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

public class SyntaxValidations {
	
	public static HashMap<String, SyntaxHandler> syntaxes = new HashMap<String, SyntaxHandler>();
	
	/**
	 * Register default syntaxes to create your command
	 */
	public static void registerDefaultSyntax(JavaPlugin plugin) {
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
		registerSyntax("player", new PlayerSyntax());
		registerSyntax("item", new ItemStackSyntax(plugin));
	}
	
	/**
	 * Register your own syntax.
	 * @param method The base command of your syntax
	 * @param handler The handler to check values against your syntax
	 */
	public static void registerSyntax(String method, SyntaxHandler handler) {
		syntaxes.put(method, handler);
	}
	
	public static boolean existHandler(String method) {
		for(String m : syntaxes.keySet()) 
			if(m.equalsIgnoreCase(method)) return true;
		return false;
	}
	
	public static Iterable<MatchResult> allMatches(final Pattern p, final CharSequence input) {
		return new Iterable<MatchResult>() {
			public Iterator<MatchResult> iterator() {
				return new Iterator<MatchResult>() {
					final Matcher matcher = p.matcher(input);
					MatchResult pending;

					public boolean hasNext() {
						if (pending == null && matcher.find()) {
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
