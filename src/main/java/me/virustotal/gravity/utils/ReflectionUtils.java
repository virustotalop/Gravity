package me.virustotal.gravity.utils;

public class ReflectionUtils {
	
	public static boolean existClass(String clazz) 
	{
		try 
		{
			Class.forName(clazz);
			return true;
		} 
		catch (Exception e) 
		{ 
			return false;
		}
	}
}