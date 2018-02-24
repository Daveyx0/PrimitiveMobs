package net.daveyx0.primitivemobs.config;

import net.minecraftforge.common.config.Configuration;

public class PrimitiveMobsConfigMobs {
	
	public static boolean enableChameleon, enableTreasureSlime, enableHauntedTool,
		enableGroveSprite, enableEnchantedBook, enableFilchLizard, enableBrainSlime,
		enableRocketCreeper, enableFestiveCreeper, enableSupportCreeper, enableSkeletonWarrior,
		enableBlazingJuggernaut, enableLilyLurker, enableSpiderFamily, enableTrollager,
		enableLostMiner, enableSquirrel, enableMerchant, enableDodo, enableMimic;
	
	public static void load(Configuration config) {
		String category = "Mob Activation Settings";
		
		config.addCustomCategoryComment(category, "Enable/Disable mobs");
		config.setCategoryRequiresMcRestart(category, true);

		enableChameleon = config.get(category, "Enable Chameleon", true, "Enable/Disable the Chameleon").getBoolean();
		enableTreasureSlime = config.get(category, "Enable Treasure Slime", true, "Enable/Disable the Treasure Slime").getBoolean();
		enableHauntedTool = config.get(category, "Enable Haunted Tool", true, "Enable/Disable the Haunted Tool").getBoolean();
		enableGroveSprite = config.get(category, "Enable Grove Sprite", true, "Enable/Disable the Grove Sprite").getBoolean();
		enableEnchantedBook = config.get(category, "Enable Bewitched Tome", true, "Enable/Disable the Bewitched Tome").getBoolean();
		enableFilchLizard = config.get(category, "Enable Filch Lizard", true, "Enable/Disable the Filch Lizard").getBoolean();
		enableBrainSlime = config.get(category, "Enable Brain Slime", true, "Enable/Disable the Brain Slime").getBoolean();
		enableRocketCreeper = config.get(category, "Enable Rocket Creeper", true, "Enable/Disable the Rocket Creeper").getBoolean();
		enableFestiveCreeper = config.get(category, "Enable Festive Creeper", true, "Enable/Disable the Festive Creeper").getBoolean();
		enableSupportCreeper = config.get(category, "Enable Support Creeper", true, "Enable/Disable the Support Creeper").getBoolean();
		enableSkeletonWarrior = config.get(category, "Enable Skeleton Warrior", true, "Enable/Disable the Skeleton Warrior").getBoolean();
		enableBlazingJuggernaut = config.get(category, "Enable Blazing Juggernaut", true, "Enable/Disable the Blazing Juggernaut").getBoolean();
		enableLilyLurker = config.get(category, "Enable Lily Lurker", true, "Enable/Disable the Lily lurker").getBoolean();
		enableSpiderFamily = config.get(category, "Enable Spider Family", true, "Enable/Disable the Spider Family").getBoolean();
		enableTrollager = config.get(category, "Enable Troll", true, "Enable/Disable the Troll").getBoolean();
		enableLostMiner = config.get(category, "Enable Lost Miner", true, "Enable/Disable the Lost Miner").getBoolean();
		enableSquirrel = config.get(category, "Enable Squirrel", true, "Enable/Disable the Squirrel").getBoolean();
		enableMerchant = config.get(category, "Enable Traveling Merchant", true, "Enable/Disable the Traveling Merchant").getBoolean();
		enableDodo = config.get(category, "Enable Dodo", true, "Enable/Disable the Dodo").getBoolean();
		enableMimic= config.get(category, "Enable Mimic", true, "Enable/Disable the Mimic").getBoolean();
	}

}
