package net.daveyx0.primitivemobs.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class PrimitiveMobsLootTables {

	//Entity drops
	public static final ResourceLocation ENTITIES_CHAMELEON = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/chameleon");
	public static final ResourceLocation ENTITIES_ROCKETCREEPER = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/rocket_creeper");
	public static final ResourceLocation ENTITIES_FESTIVECREEPER = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/festive_creeper");
	public static final ResourceLocation ENTITIES_SUPPORTCREEPER = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/support_creeper");
	public static final ResourceLocation ENTITIES_BLAZINGJUGGERNAUT = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/blazing_juggernaut");
	public static final ResourceLocation ENTITIES_LILYLURKER = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/lily_lurker");
	public static final ResourceLocation ENTITIES_DODO = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/dodo");
	public static final ResourceLocation ENTITIES_MIMIC = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/mimic");
	public static final ResourceLocation ENTITIES_FLAMESPEWER = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/flame_spewer");
	public static final ResourceLocation ENTITIES_VOIDEYE = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/void_eye");
	public static final ResourceLocation ENTITIES_HARPY = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/harpy");
	public static final ResourceLocation ENTITIES_GOBLIN = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/goblin");
	public static final ResourceLocation ENTITIES_MOTHERSPIDER = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/mother_spider");
	
	//Mimic chest loot
	public static final ResourceLocation MIMIC_TREASURE= new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "chests/mimic_treasure");
	public static final ResourceLocation MIMIC_TRAP = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "chests/mimic_trap");
	
	//Special entity loot
	public static final ResourceLocation FILCHLIZARD_STEAL = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/special/filch_lizard_steal");
	public static final ResourceLocation FILCHLIZARD_SPAWN = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/special/filch_lizard_spawn");
	public static final ResourceLocation HAUNTEDTOOL_SPAWN = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/special/haunted_tool");
	public static final ResourceLocation TREASURESLIME_SPAWN = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/special/treasure_slime");

    public static void registerLootTables() {
    	LootTableList.register(ENTITIES_CHAMELEON);
    	LootTableList.register(ENTITIES_ROCKETCREEPER);
    	LootTableList.register(ENTITIES_FESTIVECREEPER);
    	LootTableList.register(ENTITIES_SUPPORTCREEPER);
    	LootTableList.register(ENTITIES_BLAZINGJUGGERNAUT);
    	LootTableList.register(ENTITIES_LILYLURKER);
    	LootTableList.register(ENTITIES_DODO);
    	LootTableList.register(ENTITIES_MIMIC);
    	LootTableList.register(ENTITIES_GOBLIN);
    	LootTableList.register(ENTITIES_HARPY);
    	LootTableList.register(ENTITIES_VOIDEYE);
    	LootTableList.register(ENTITIES_FLAMESPEWER);
    	LootTableList.register(ENTITIES_MOTHERSPIDER);
    	
    	LootTableList.register(MIMIC_TREASURE);
    	LootTableList.register(MIMIC_TRAP);
    	
    	LootTableList.register(FILCHLIZARD_STEAL);
    	LootTableList.register(FILCHLIZARD_SPAWN);
    	LootTableList.register(HAUNTEDTOOL_SPAWN);
    	LootTableList.register(TREASURESLIME_SPAWN);
    }

}
