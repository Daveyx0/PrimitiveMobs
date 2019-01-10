package net.daveyx0.primitivemobs.core;

import net.daveyx0.multimob.spawn.MMConfigSpawnEntry;
import net.daveyx0.multimob.spawn.MMSpawnRegistry;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;

public class PrimitiveMobsSpawnRegistry extends MMSpawnRegistry 
{

	public static void registerSpawns() {
		
		//Hostile mobs
		registerSpawnEntry(new MMConfigSpawnEntry("_BrainSlime_Beach", "primitivemobs:brain_slime", 80, true).setBiomeTypes(new String[]{"BEACH"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_HauntedTool", "primitivemobs:haunted_tool", 70, true).setupBaseMobSpawnEntry(false));
		registerSpawnEntry(new MMConfigSpawnEntry("_RocketCreeper", "primitivemobs:rocket_creeper", 50, true).setupBaseMobSpawnEntry(false).setHeightLevel(50, -1));
		registerSpawnEntry(new MMConfigSpawnEntry("_FestiveCreeper", "primitivemobs:festive_creeper", 10, true).setupBaseMobSpawnEntry(false));
		registerSpawnEntry(new MMConfigSpawnEntry("_FestiveCreeper_Nether", "primitivemobs:festive_creeper", 10, true).setDimensions(new int[]{-1}));
		registerSpawnEntry(new MMConfigSpawnEntry("_SupportCreeper", "primitivemobs:support_creeper", 30, true).setupBaseMobSpawnEntry(false));
		registerSpawnEntry(new MMConfigSpawnEntry("_TreasureSlime", "primitivemobs:treasure_slime", 20, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(2));
		registerSpawnEntry(new MMConfigSpawnEntry("_BewitchedTome", "primitivemobs:bewitched_tome", 20, true).setupBaseMobSpawnEntry(false).setAdditionalRarity(2));
		registerSpawnEntry(new MMConfigSpawnEntry("_BewitchedTome_Stronghold", "primitivemobs:bewitched_tome", 100, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 45).setStructures(new String[]{"Stronghold"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_SkeletonWarrior", "primitivemobs:skeleton_warrior", 100, true).setupBaseMobSpawnEntry(false).setGroupSize(3, 3));
		registerSpawnEntry(new MMConfigSpawnEntry("_SpiderFamily", "primitivemobs:mother_spider", 40, true).setupBaseMobSpawnEntry(false));
		registerSpawnEntry(new MMConfigSpawnEntry("_BlazingJuggernaut", "primitivemobs:blazing_juggernaut", 10, true).setDimensions(new int[]{-1}));
		registerSpawnEntry(new MMConfigSpawnEntry("_Trollager", "primitivemobs:trollager", 5, true).setupBaseMobSpawnEntry(false).setNeedsMoreSpace(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_Trollager_Underground", "primitivemobs:trollager", 25, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 45).setNeedsMoreSpace(true));
		registerSpawnEntry(new MMConfigSpawnEntry("_VoidEye_Overworld", "primitivemobs:void_eye", 10, true).setupBaseMobSpawnEntry(false).setDimensions(new int[]{0}).setHeightLevel(-1, 20));
		registerSpawnEntry(new MMConfigSpawnEntry("_Harpy", "primitivemobs:harpy", 100, true).setHeightLevel(100, -1).setGroupSize(1, 3).setOverrideCanSpawnHere(true).setSpawnBlocks(new String[]{"minecraft:grass","minecraft:stone", "minecraft:dirt", "minecraft:snow"}));
		registerSpawnEntry(new MMConfigSpawnEntry("_Goblin", "primitivemobs:goblin", 50, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 30).setGroupSize(3, 3));
		registerSpawnEntry(new MMConfigSpawnEntry("_Goblin_Mineshaft", "primitivemobs:goblin", 100, true).setupBaseMobSpawnEntry(false).setStructures(new String[] {"Mineshaft"}));

		//Passive mobs
		registerSpawnEntry(new MMConfigSpawnEntry("_Chameleon_Forest", "primitivemobs:chameleon", 100, true).setupBaseAnimalSpawnEntry(false).setBiomeTypes(new String[]{"FOREST"}).setGroupSize(1, 3).setCreatureType("MULTIMOBPASSIVE"));
		registerSpawnEntry(new MMConfigSpawnEntry("_Chameleon_Jungle", "primitivemobs:chameleon", 100, true).setupBaseAnimalSpawnEntry(false).setBiomeTypes(new String[]{"JUNGLE"}).setGroupSize(1, 3).setCreatureType("MULTIMOBPASSIVE"));
		registerSpawnEntry(new MMConfigSpawnEntry("_LostMiner", "primitivemobs:lost_miner", 4, true).setupBaseMobSpawnEntry(false).setHeightLevel(-1, 45).setAdditionalRarity(5).setCreatureType("MULTIMOBPASSIVE"));
		registerSpawnEntry(new MMConfigSpawnEntry("_TravelingMerchant", "primitivemobs:traveling_merchant", 25, true).setupBaseAnimalSpawnEntry(false).setAdditionalRarity(30).setCreatureType("MULTIMOBPASSIVE"));
		registerSpawnEntry(new MMConfigSpawnEntry("_Dodo", "primitivemobs:dodo", 100, true).setupBaseAnimalSpawnEntry(false).setSpawnBlocks(new String[]{"minecraft:mycelium"}).setGroupSize(1, 2).setCreatureType("MULTIMOBPASSIVE"));
		registerSpawnEntry(new MMConfigSpawnEntry("_FilchLizard", "primitivemobs:filch_lizard", 25, true).setBiomeTypes(new String[]{"SANDY"}).setCreatureType("MULTIMOBPASSIVE").setGroupSize(1, 2));
		registerSpawnEntry(new MMConfigSpawnEntry("_GroveSprite", "primitivemobs:grovesprite", 100, true).setupBaseAnimalSpawnEntry(false).setBiomeTypes(new String[]{"FOREST"}).setCreatureType("MULTIMOBPASSIVE").setGroupSize(1, 2));
		registerSpawnEntry(new MMConfigSpawnEntry("_Sheepman", "primitivemobs:sheepman", 40, true).setDimensions(new int[]{-1}).setCreatureType("MULTIMOBPASSIVE").setGroupSize(1, 2));
		
		//Lava mobs
		registerSpawnEntry(new MMConfigSpawnEntry("_FlameSpewer_Overworld", "primitivemobs:flame_spewer", 100, true).setDimensions(new int[]{0}).setNeedsMoreSpace(true).setSpawnType("LAVA").setCreatureType("MULTIMOBLAVA"));
		registerSpawnEntry(new MMConfigSpawnEntry("_FlameSpewer_Nether", "primitivemobs:flame_spewer", 100, true).setDimensions(new int[]{-1}).setNeedsMoreSpace(true).setSpawnType("LAVA").setCreatureType("MULTIMOBLAVA"));
		
		//Water mobs
		registerSpawnEntry(new MMConfigSpawnEntry("_LilyLurker", "primitivemobs:lily_lurker", 100, true).setHeightLevel(50, -1).setSpawnType("WATER").setBiomeTypes(new String[]{"SWAMP"}).setCreatureType("MULTIMOBWATER"));
	}
	
}