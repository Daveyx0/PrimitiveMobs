package net.daveyx0.primitivemobs.config;

import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PrimitiveMobsConfigSpawns {
	
	public static float chameleonSpawnRate, treasureslimeSpawnRate, hauntedToolSpawnRate, groveSpriteSpawnRate, bewitchedTomeSpawnRate, filchLizardSpawnRate, brainSlimeSpawnRate,
	rocketCreeperSpawnRate, festiveCreeperSpawnRate, supportCreeperSpawnRate, skeletonWarriorSpawnRate,
	blazingJuggernautSpawnRate, lilyLurkerSpawnRate, spiderFamilySpawnRate;

	public static void load(Configuration config) {
		String category = "Spawn Settings";
				
		config.addCustomCategoryComment(category, "Mob spawn config settings (0 spawn rate = no spawns, 25 = normal spawns, 100 = v.high spawns)");
		chameleonSpawnRate = config.get(category, "Chameleon Spawn Rate", 25).getInt()/100f;
		treasureslimeSpawnRate = config.get(category, "Treasure Slime Spawn Rate", 20).getInt()/100f;
		hauntedToolSpawnRate = config.get(category, "Haunted Tool Spawn Rate", 15).getInt()/100f;
		groveSpriteSpawnRate = config.get(category, "Grove Sprite Spawn Rate", 25).getInt()/100f;
		bewitchedTomeSpawnRate = config.get(category, "Bewitched Tome Spawn Rate", 30).getInt()/100f;
		filchLizardSpawnRate = config.get(category, "Filch Lizard Spawn Rate", 25).getInt()/100f;
		brainSlimeSpawnRate = config.get(category, "Brain Slime Spawn Rate", 50).getInt()/100f;
		rocketCreeperSpawnRate = config.get(category, "Rocket Creeper Spawn Rate", 25).getInt()/100f;
		festiveCreeperSpawnRate = config.get(category, "Festive Creeper Spawn Rate", 15).getInt()/100f;
		supportCreeperSpawnRate = config.get(category, "Support Creeper Spawn Rate", 15).getInt()/100f;
		skeletonWarriorSpawnRate = config.get(category, "Skeleton Warrior Spawn Rate", 25).getInt()/100f;
		blazingJuggernautSpawnRate = config.get(category, "Blazing Juggernaut Spawn Rate", 25).getInt()/100f;
		lilyLurkerSpawnRate = config.get(category, "Lily Lurker Spawn Rate", 30).getInt()/100f;
		spiderFamilySpawnRate = config.get(category, "Spider Family Spawn Rate", 25).getInt()/100f;
	}
}
