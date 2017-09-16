package net.daveyx0.primitivemobs.config;

import net.daveyx0.primitivemobs.loot.FilchLizardLoot;
import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PrimitiveMobsConfigMobs {
	
	public static String[] treasureSlimeLoot;
	public static String[] hauntedToolLoot;
	public static String[] filchStealLoot;
	public static boolean debug;
	
	public static void load(Configuration config) {
		
		config.addCustomCategoryComment("Mob Settings", "Mob config settings");
		treasureSlimeLoot = config.get("Mob Settings", "Treasure Slime Loot List", TreasureSlimeLoot.getDefaultValues()).getStringList();
		hauntedToolLoot = config.get("Mob Settings", "Haunted Tool Loot List", HauntedToolLoot.getDefaultValues()).getStringList();
		filchStealLoot = config.get("Mob Settings", "Filch Lizard Loot List", FilchLizardLoot.getDefaultValues()).getStringList();
		debug = config.get("Mob Settings", "Enable Debug methods", false).getBoolean();
	}
	
	public static String[] getTreasureSlimeLoot()
	{
		return treasureSlimeLoot;
	}
	
	public static String[] getHauntedToolLoot()
	{
		return hauntedToolLoot;
	}
	
	public static String[] getFilchStealLoot()
	{
		return filchStealLoot;
	}
	
	public static boolean getDebug()
	{
		return debug;
	}
}
