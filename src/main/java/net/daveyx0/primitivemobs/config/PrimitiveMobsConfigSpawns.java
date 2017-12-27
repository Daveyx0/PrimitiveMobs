package net.daveyx0.primitivemobs.config;

import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PrimitiveMobsConfigSpawns {
	
	public static float chameleonSpawnRate, treasureslimeSpawnRate, hauntedToolSpawnRate, groveSpriteSpawnRate, bewitchedTomeSpawnRate, filchLizardSpawnRate, brainSlimeSpawnRate,
	rocketCreeperSpawnRate, festiveCreeperSpawnRate, supportCreeperSpawnRate, skeletonWarriorSpawnRate,
	blazingJuggernautSpawnRate, lilyLurkerSpawnRate, spiderFamilySpawnRate, trollSpawnRate, lostMinerSpawnRate;

	public static void load(Configuration config) {
		String category = "Spawn Settings";
				
		config.addCustomCategoryComment(category, "Mob spawn config settings (0 spawn rate = no spawns, 25 = normal spawns, 100 = v.high spawns)");
		config.setCategoryRequiresMcRestart(category, true);
		chameleonSpawnRate = config.get(category, "Chameleon Spawn Rate", 25, "Chameleons spawn in Forests and Jungles").getInt()/100f;
		treasureslimeSpawnRate = config.get(category, "Treasure Slime Spawn Rate", 20, "Treasure Slimes spawn everywhere in the dark").getInt()/100f;
		hauntedToolSpawnRate = config.get(category, "Haunted Tool Spawn Rate", 15, "Haunted Tools spawn everywhere in the dark").getInt()/100f;
		groveSpriteSpawnRate = config.get(category, "Grove Sprite Spawn Rate", 25, "Grove Sprites spawn in Forests").getInt()/100f;
		bewitchedTomeSpawnRate = config.get(category, "Bewitched Tome Spawn Rate", 25, "Bewitched Tomes spawn deep underground").getInt()/100f;
		filchLizardSpawnRate = config.get(category, "Filch Lizard Spawn Rate", 25, "Filch Lizards spawn in Deserts").getInt()/100f;
		brainSlimeSpawnRate = config.get(category, "Brain Slime Spawn Rate", 50, "Brain Slimes spawn on Beaches").getInt()/100f;
		rocketCreeperSpawnRate = config.get(category, "Rocket Creeper Spawn Rate", 20, "Rocket Creepers spawn everywhere in the dark").getInt()/100f;
		festiveCreeperSpawnRate = config.get(category, "Festive Creeper Spawn Rate", 10, "Festive Creepers spawn everywhere in the dark").getInt()/100f;
		supportCreeperSpawnRate = config.get(category, "Support Creeper Spawn Rate", 15, "Support Creepers spawn everywhere in the dark").getInt()/100f;
		skeletonWarriorSpawnRate = config.get(category, "Skeleton Warrior Spawn Rate", 20, "Skeleton Warriors spawn everywhere in the dark").getInt()/100f;
		blazingJuggernautSpawnRate = config.get(category, "Blazing Juggernaut Spawn Rate", 25, "Blazing Juggernauts spawn in the Nether").getInt()/100f;
		lilyLurkerSpawnRate = config.get(category, "Lily Lurker Spawn Rate", 30, "Lily Lurkers spawn in Swamps").getInt()/100f;
		spiderFamilySpawnRate = config.get(category, "Spider Family Spawn Rate", 12, "Spider Families spawn everywhere in the dark").getInt()/100f;
		trollSpawnRate = config.get(category, "Trollager Spawn Rate", 5, "Trollagers spawn everywhere in the dark (or only underground when config is changed)").getInt()/100f;
		lostMinerSpawnRate = config.get(category, "Lost Miner Spawn Rate", 20, "Lost Miners spawn underground").getInt()/100f;
	}
}
