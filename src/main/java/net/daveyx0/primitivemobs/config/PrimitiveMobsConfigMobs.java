package net.daveyx0.primitivemobs.config;

import net.daveyx0.primitivemobs.loot.FilchLizardLoot;
import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.minecraftforge.common.config.Configuration;

public class PrimitiveMobsConfigMobs {
	
	public static String[] treasureSlimeLoot;
	public static String[] hauntedToolLoot;
	public static String[] filchStealLoot;
	public static boolean debug;
	
	public static boolean enableChameleon, enableTreasureSlime, enableHauntedTool,
		enableGroveSprite, enableEnchantedBook, enableFilchLizard, enableBrainSlime,
		enableRocketCreeper, enableFestiveCreeper, enableSupportCreeper, enableSkeletonWarrior,
		enableBlazingJuggernaut, enableLilyLurker;
	
	public static void load(Configuration config) {
		String category = "Mob Settings";
		
		config.addCustomCategoryComment(category, "Mob config settings");
		treasureSlimeLoot = config.get(category, "Treasure Slime Loot List", TreasureSlimeLoot.getDefaultValues()).getStringList();
		hauntedToolLoot = config.get(category, "Haunted Tool Loot List", HauntedToolLoot.getDefaultValues()).getStringList();
		filchStealLoot = config.get(category, "Filch Lizard Loot List", FilchLizardLoot.getDefaultValues()).getStringList();
		debug = config.get(category, "Enable Debug methods", false).getBoolean();
		
		enableChameleon = config.get(category, "Enable Chameleon", true).getBoolean();
		enableTreasureSlime = config.get(category, "Enable Treasure Slime", true).getBoolean();
		enableHauntedTool = config.get(category, "Enable Haunted Tool", true).getBoolean();
		enableGroveSprite = config.get(category, "Enable Grove Sprite", true).getBoolean();
		enableEnchantedBook = config.get(category, "Enable Enchanted Book", true).getBoolean();
		enableFilchLizard = config.get(category, "Enable Filch Lizard", true).getBoolean();
		enableBrainSlime = config.get(category, "Enable Brain Slime", true).getBoolean();
		enableRocketCreeper = config.get(category, "Enable Rocket Creeper", true).getBoolean();
		enableFestiveCreeper = config.get(category, "Enable Festive Creeper", true).getBoolean();
		enableSupportCreeper = config.get(category, "Enable Support Creeeper", true).getBoolean();
		enableSkeletonWarrior = config.get(category, "Enable Skeleton Warrior", true).getBoolean();
		enableBlazingJuggernaut = config.get(category, "Enable Blazing Juggernaut", true).getBoolean();
		enableLilyLurker = config.get(category, "Enable Lily Lurker", true).getBoolean();
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
