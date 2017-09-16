package net.daveyx0.primitivemobs.core;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PrimitiveMobsLootTables {

	public static final ResourceLocation ENTITIES_CHAMELEON = new ResourceLocation(PrimitiveMobsReference.MODID + ":" + "entities/chameleon");
	
    public static void preInit() {
    	registerLootTables();
    }

    public static void registerLootTables() {
    	LootTableList.register(ENTITIES_CHAMELEON);
    }

}
