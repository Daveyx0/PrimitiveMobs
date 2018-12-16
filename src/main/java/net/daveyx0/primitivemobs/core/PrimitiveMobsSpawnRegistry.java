package net.daveyx0.primitivemobs.core;

import net.daveyx0.multimob.spawn.MMConfigSpawnEntry;
import net.daveyx0.multimob.spawn.MMSpawnRegistry;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;

public class PrimitiveMobsSpawnRegistry extends MMSpawnRegistry 
{

	public static void registerSpawns() {
		
		registerSpawnEntry(new MMConfigSpawnEntry("_Chameleon_Forest", "primitivemobs:chameleon", 6, true).setupBaseAnimalSpawnEntry(false).setBiomeTypes(new String[]{"FOREST"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_Chameleon_Jungle", "primitivemobs:chameleon", 6, true).setupBaseAnimalSpawnEntry(false).setBiomeTypes(new String[]{"JUNGLE"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_FilchLizard", "primitivemobs:filch_lizard", 6, true).setBiomeTypes(new String[]{"SANDY"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_GroveSprite", "primitivemobs:grovesprite", 6, true).setupBaseAnimalSpawnEntry(false).setBiomeTypes(new String[]{"FOREST"}).setAdditionalRarity(2));;
		registerSpawnEntry(new MMConfigSpawnEntry("_BrainSlime_Beach", "primitivemobs:brain_slime", 8, true).setBiomeTypes(new String[]{"BEACH"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_HauntedTool", "primitivemobs:haunted_tool", 4, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(2));
		registerSpawnEntry(new MMConfigSpawnEntry("_RocketCreeper", "primitivemobs:rocket_creeper", 4, true).setupBaseMobSpawnEntry(false).setHeightLevel(50, -1).setAdditionalRarity(2));
		registerSpawnEntry(new MMConfigSpawnEntry("_FestiveCreeper", "primitivemobs:festive_creeper", 2, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(3));
		registerSpawnEntry(new MMConfigSpawnEntry("_FestiveCreeper_Nether", "primitivemobs:festive_creeper", 3, true).setDimensions(new int[]{-1}).setAdditionalRarity(5));
		registerSpawnEntry(new MMConfigSpawnEntry("_SupportCreeper", "primitivemobs:support_creeper", 3, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(3));
		registerSpawnEntry(new MMConfigSpawnEntry("_TreasureSlime", "primitivemobs:treasure_slime", 4, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(5));
		registerSpawnEntry(new MMConfigSpawnEntry("_BewitchedTome", "primitivemobs:bewitched_tome", 2, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(5));
		registerSpawnEntry(new MMConfigSpawnEntry("_BewitchedTome_Stronghold", "primitivemobs:bewitched_tome", 6, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 45).setStructures(new String[]{"Stronghold"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_SkeletonWarrior", "primitivemobs:skeleton_warrior", 8, true).setupBaseMobSpawnEntry(false));
		registerSpawnEntry(new MMConfigSpawnEntry("_SpiderFamily", "primitivemobs:mother_spider", 6, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(3));
		registerSpawnEntry(new MMConfigSpawnEntry("_LilyLurker", "primitivemobs:lily_lurker", 6, true).setHeightLevel(50, -1).setSpawnType("WATER").setBiomeTypes(new String[]{"SWAMP"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_BlazingJuggernaut", "primitivemobs:blazing_juggernaut",5, true).setDimensions(new int[]{-1}));
		registerSpawnEntry(new MMConfigSpawnEntry("_Trollager", "primitivemobs:trollager", 1, true).setupBaseMobSpawnEntry(false).setNeedsMoreSpace(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_TrollagerUnderground", "primitivemobs:trollager", 6, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 45).setNeedsMoreSpace(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_LostMiner", "primitivemobs:lost_miner", 2, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 45).setAdditionalRarity(5));
		registerSpawnEntry(new MMConfigSpawnEntry("_TravelingMerchant", "primitivemobs:traveling_merchant", 2, true).setupBaseAnimalSpawnEntry(false).setAdditionalRarity(20));
		registerSpawnEntry(new MMConfigSpawnEntry("_Dodo", "primitivemobs:dodo", 3, true).setupBaseAnimalSpawnEntry(false).setSpawnBlocks(new String[]{"minecraft:mycelium"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_FlameSpewer_Overworld", "primitivemobs:flame_spewer", 5, true).setDimensions(new int[]{0}).setNeedsMoreSpace(true).setSpawnType("LAVA"));
		registerSpawnEntry(new MMConfigSpawnEntry("_FlameSpewer_Nether", "primitivemobs:flame_spewer", 3, true).setDimensions(new int[]{-1}).setNeedsMoreSpace(true).setSpawnType("LAVA"));
		registerSpawnEntry(new MMConfigSpawnEntry("_VoidEye_Overworld", "primitivemobs:void_eye", 5, true).setupBaseMobSpawnEntry(false).setDimensions(new int[]{0}).setHeightLevel(-1, 20));
		registerSpawnEntry(new MMConfigSpawnEntry("_VoidEye_End", "primitivemobs:void_eye", 8, true).setDimensions(new int[]{1}).setAdditionalRarity(8));
		registerSpawnEntry(new MMConfigSpawnEntry("_Harpy", "primitivemobs:harpy", 6, true).setHeightLevel(100, -1).setOverrideCanSpawnHere(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_Sheepman", "primitivemobs:sheepman", 6, true).setDimensions(new int[]{-1}));
		registerSpawnEntry(new MMConfigSpawnEntry("_Goblin", "primitivemobs:goblin", 4, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 30));
		
		if(PrimitiveMobsConfigSpecial.getVanillaSpawns())
		{
		registerSpawnEntry(new MMConfigSpawnEntry("_ZombieExtra", "minecraft:zombie", 5, true).setupBaseMobSpawnEntry(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_SkeletonExtra", "minecraft:skeleton", 5, true).setupBaseMobSpawnEntry(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_SpiderExtra", "minecraft:spider", 4, true).setupBaseMobSpawnEntry(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_CreeperExtra", "minecraft:creeper", 4, true).setupBaseMobSpawnEntry(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_EndermanExtra", "minecraft:enderman", 1, true).setupBaseMobSpawnEntry(true));
		}
		/*
		MIMIC_SPAWNS = registerSpawnNormal(new MMSpawnEntry(EntityMimic.class, PrimitiveMobsConfigSpecial.getMimicGeneratesInCaves() ? 0 : PrimitiveMobsConfigSpawns.mimicSpawnRate));
		*/
	}
	
}