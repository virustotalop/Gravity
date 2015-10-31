package me.virustotal.gravity;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.plugin.Plugin;

public class Snooper {

	/* Reference code
	 * https://github.com/virustotalop/FactionsReloaded/blob/master/src/me/virustotal/factionsreloaded/Snooper.java
	 */
	protected static void cleanupStaticVars(Plugin plugin)
	{
		try
		{
			File jarFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			JarFile jar = new JarFile(jarFile);
			Enumeration<JarEntry> entries = jar.entries();

			while(entries.hasMoreElements() )
			{
				JarEntry entry = entries.nextElement();
				String name = entry.getName().replace('/', '.');
				if(name.contains(".class"))
				{
					name = name.substring(0, name.lastIndexOf("."));
					Class<?> theClass = Class.forName(name);
					for(Field field : theClass.getFields())
					{
						if(Modifier.isStatic(field.getModifiers()))
						{
							//field should be reset to default value
							field.set(field.get(field.getType().newInstance()), theClass.newInstance().getClass().getField(field.getName()).get(null));
						}
					}
				}
			}
			jar.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}