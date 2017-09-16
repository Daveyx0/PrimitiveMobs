package net.daveyx0.primitivemobs.config;

import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PrimitiveMobsConfigSpawns {
	
	public static double treasureSlimeSpawn;

	public static void load(Configuration config) {
		
		config.addCustomCategoryComment("Spawn Settings", "Mob spawn config settings");
		treasureSlimeSpawn = config.get("Mob Settings", "Treasure Slime Spawn Rate. Spawns like regular mobs.", 0.25).getDouble();
	}
	
	public static double getTreasureSlimeSpawnRate()
	{
		return treasureSlimeSpawn;
	}
}
