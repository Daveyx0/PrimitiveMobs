package net.daveyx0.primitivemobs.core;

import java.util.logging.Logger;

import net.minecraft.world.World;

public class PrimitiveMobsLogger {

	
	public static Logger PMlogger;
	
	public static void preInit()
	{
		PMlogger = Logger.getLogger(PrimitiveMobsReference.MODID);
	}
	
	public static void info(World world, String message)
	{
		if(world.isRemote)
		{
			PMlogger.info("Client log: " + message);
		}
		else
		{
			PMlogger.info("Server log: " + message);
		}
	}
	
	public static void info(String message)
	{
		PMlogger.info(message);
	}
	
	public static void gotHere()
	{
		PMlogger.info("got here");
	}
}
