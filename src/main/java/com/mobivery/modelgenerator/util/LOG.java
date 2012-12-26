package com.mobivery.modelgenerator.util;

import org.apache.log4j.Logger;

public class LOG {	
	private static Logger logger=Logger.getLogger("Generator");
	public static void debug(String s)
	{
		logger.debug(s);
	}
	public static void info(String s)
	{
		logger.info(s);
	}
	public static void error(String s)
	{
		logger.error(s);
	}
	public static void warn(String s)
	{
		logger.warn(s);
	}
}
