package net.daveyx0.primitivemobs.config;

import net.daveyx0.primitivemobs.loot.FilchLizardLoot;
import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PrimitiveMobsConfigSpecial {
	
	public static String[] treasureSlimeLoot;
	public static String[] hauntedToolLoot;
	public static String[] filchStealLoot;
	
	public static boolean minerInVillage;
	public static boolean festiveCreeperDestruction;
	public static boolean lostMinerSounds;
	public static int maxSpiderFamilySize;
	public static boolean trollDestruction;
	public static boolean trollOnlyUnderground;
	public static int tameableSlimeChance;
	public static int filchLizardLootChance;
	public static boolean merchantCanSettle;
	public static boolean mimicGenerates;
	public static boolean brainSlimeHostile;

	public static void load(Configuration config) {
		String category = "Loot Settings";

		config.addCustomCategoryComment(category, "Change the special loot of mobs, like which loot the treasure slime spawns with.");
		treasureSlimeLoot = config.get(category, "Treasure Slime Loot List", TreasureSlimeLoot.getDefaultValues(), "List of items the Treasure Slime can have inside it on spawn").getStringList();
		hauntedToolLoot = config.get(category, "Haunted Tool Loot List", HauntedToolLoot.getDefaultValues(), "List of tools the Haunted Tool can spawn as").getStringList();
		filchStealLoot = config.get(category, "Filch Lizard Loot List", FilchLizardLoot.getDefaultValues(), "List of items the Filch Lizard is interested in, can steal from players and can spawn with").getStringList();
		
		String category1 = "Mob Specific Settings";
		
		config.addCustomCategoryComment(category1, "Settings specific to certain mobs.");
		minerInVillage = config.get(category1, "The Miner profession spawns in villages", false, "Enable/Disable if the Miner Villager profession should also spawn naturally in villages").getBoolean();
		festiveCreeperDestruction = config.get(category1, "Festive Creeper tnt harms terrain", true, "Enable/Disable if the Festive Creeper throws tnt that harms the terrain (this way you do not have to disable ALL Creeper explosions with mobGriefing)").getBoolean();
		maxSpiderFamilySize = config.get(category1, "Max Spider Family Size", 6, "Set the maximum amount of Baby Spiders that can potentially spawn with a Mother Spider").getInt();
		lostMinerSounds = config.get(category1, "Lost Miner makes villager sounds", true, "Enable/Disable if the Lost Miner should make villager sounds").getBoolean();
		trollDestruction = config.get(category1, "Trollager can destroy terrain", true, "Enable/Disable if the Trollager can destroy terrain with its attack").getBoolean();
		trollOnlyUnderground = config.get(category1, "Trollager can only spawn underground", false, "Enable/Disable if the Trollager can only spawn underground (below Y=40)").getBoolean();
		tameableSlimeChance = config.get(category1, "Chance that a tameable Treasure Slime spawns", 5, "Set the chance that a tameable Treasure Slime can spawn in percentage").getInt();
		filchLizardLootChance = config.get(category1, "Chance that a Filch Lizard spawns holding loot", 25, "Set the chance that a Filch Lizard spawns holding loot in percentage").getInt();
		merchantCanSettle = config.get(category1, "Traveling Merchant can settle", true, "Enable/Disable if the Traveling Merchant can settle after being bribed with an Emerald Block").getBoolean();
		mimicGenerates = config.get(category1, "Mimic chests generate in caves", true, "Enable/Disable if the Mimic chests should generate in caves, instead of spawn like mobs").getBoolean();
		brainSlimeHostile = config.get(category1, "Brain Slimes attack animals", true, "Enable/Disable if Brain Slimes should attack other animals/creatures").getBoolean();
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
	
	public static boolean getMinerInVillage()
	{
		return minerInVillage;
	}
	
	public static boolean getFestiveCreeperDestruction()
	{
		return festiveCreeperDestruction;
	}
	
	public static int getMaxSpiderFamilySize()
	{
		return maxSpiderFamilySize;
	}
	
	public static boolean getLostMinerSounds()
	{
		return lostMinerSounds;
	}
	
	public static boolean getTrollDestruction()
	{
		return trollDestruction;
	}
	
	public static boolean getTrollUnderground()
	{
		return trollOnlyUnderground;
	}
	
	public static int getTameableSlimeChance()
	{
		return tameableSlimeChance;
	}
	
	public static int getFilchLizardLootChance()
	{
		return filchLizardLootChance;
	}
	
	public static boolean getMimicGeneratesInCaves()
	{
		return mimicGenerates;
	}
	
	public static boolean getBrainSlimeHostility()
	{
		return brainSlimeHostile;
	}
}
