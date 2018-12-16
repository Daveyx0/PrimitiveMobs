package net.daveyx0.primitivemobs.config;

import net.minecraftforge.common.config.Configuration;

public class PrimitiveMobsConfigSpecial {
	
	public static String[] treasureSlimeLoot;
	public static String[] hauntedToolLoot;
	public static String[] filchStealLoot;
	
	public static boolean minerInVillage;
	public static boolean festiveCreeperDestruction;
	public static boolean lostMinerSounds;
	public static int maxSpiderFamilySize;
	public static boolean trollDestruction;
	public static int tameableSlimeChance;
	public static int filchLizardLootChance;
	public static boolean merchantCanSettle;
	public static boolean mimicGenerates;
	public static boolean brainSlimeHostile;
	public static boolean dodoMycelium;
	public static boolean travelerVisit;
	public static boolean rocketCreeperAlwaysJump;
	public static boolean hauntedToolFullDurability;
	public static boolean addAdditionalVanillaSpawns;
	public static int mimicSpawnRate;
	public static int[] lostMinerLootRange;

	public static void load(Configuration config) {
		String category1 = "Mob Specific Settings";
		
		config.addCustomCategoryComment(category1, "Settings specific to certain mobs.");
		minerInVillage = config.get(category1, "The Miner profession spawns in villages", false, "Enable/Disable if the Miner Villager profession should also spawn naturally in villages").getBoolean();
		festiveCreeperDestruction = config.get(category1, "Festive Creeper tnt harms terrain", true, "Enable/Disable if the Festive Creeper throws tnt that harms the terrain (this way you do not have to disable ALL Creeper explosions with mobGriefing)").getBoolean();
		maxSpiderFamilySize = config.get(category1, "Max Spider Family Size", 6, "Set the maximum amount of Baby Spiders that can potentially spawn with a Mother Spider").getInt();
		lostMinerSounds = config.get(category1, "Lost Miner makes villager sounds", true, "Enable/Disable if the Lost Miner should make villager sounds").getBoolean();
		trollDestruction = config.get(category1, "Trollager can destroy terrain", true, "Enable/Disable if the Trollager can destroy terrain with its attack").getBoolean();
		tameableSlimeChance = config.get(category1, "Chance that a tameable Treasure Slime spawns", 5, "Set the chance that a tameable Treasure Slime can spawn in percentage").getInt();
		filchLizardLootChance = config.get(category1, "Chance that a Filch Lizard spawns holding loot", 25, "Set the chance that a Filch Lizard spawns holding loot in percentage").getInt();
		merchantCanSettle = config.get(category1, "Traveling Merchant can settle", true, "Enable/Disable if the Traveling Merchant can settle after being bribed with an Emerald Block").getBoolean();
		mimicGenerates = config.get(category1, "Mimic chests generate in caves", true, "Enable/Disable if the Mimic chests should generate in caves, instead of spawn like mobs").getBoolean();
		brainSlimeHostile = config.get(category1, "Brain Slimes attack animals", true, "Enable/Disable if Brain Slimes should attack other animals/creatures").getBoolean();
		dodoMycelium = config.get(category1, "Dodos convert Grass into Mycelium", true, "Enable/Disable if Dodos should convert Grass into Mycelium blocks overtime").getBoolean();
		travelerVisit = config.get(category1, "Traveling Merchant enters houses", true, "Enable/Disable if the Traveling Merchant should enter houses").getBoolean();
		rocketCreeperAlwaysJump = config.get(category1, "Rocket Creepers always jump, even without adequate room", false, "Enable/Disable if rocket creepers should always jump at the player, even if they would hit the ceiling").getBoolean();
		lostMinerLootRange = config.get(category1, "Lost Miner emerald loot range", new int[]{2,2}, "Change the amount of emeralds you receive when saving a Lost Miner. First number = minimum. Second number = variable addition").getIntList();
		hauntedToolFullDurability= config.get(category1, "Haunted Tool drop full durability", false, "Enable/Disable if Haunted Tools should drop full durability items.").getBoolean();
		addAdditionalVanillaSpawns= config.get(category1, "More vanilla mob spawns", true, "Enable/Disable if more vanilla mobs should spawn; can be used if amount of vanilla mobs seems low.").getBoolean();
		mimicSpawnRate = config.get(category1, "Spawn Rate of Mimics", 30, "How often a Mimic chest generates. Higher number = rarer. 0 = no mimics chests.").getInt();
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
	
	public static int getMimicRarity()
	{
		return mimicSpawnRate;
	}
	
	public static boolean getLostMinerSounds()
	{
		return lostMinerSounds;
	}
	
	public static boolean getTrollDestruction()
	{
		return trollDestruction;
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
	
	
	public static boolean getDodoMycelium()
	{
		return dodoMycelium;
	}
	
	public static boolean getTravelerVisit()
	{
		return travelerVisit;
	}
	
	public static boolean getRocketCreeperAlwaysJump()
	{
		return rocketCreeperAlwaysJump;
	}
	
	public static int[] getLostMinerLootRange()
	{
		return lostMinerLootRange;
	}
	
	public static boolean getHauntedToolDurability()
	{
		return hauntedToolFullDurability;
	}
	
	public static boolean getVanillaSpawns()
	{
		return addAdditionalVanillaSpawns;
	}
}
