package net.daveyx0.primitivemobs.modint;

import java.util.ArrayList;
import java.util.List;

import jeresources.api.IJERAPI;
import jeresources.api.JERPlugin;
import jeresources.api.conditionals.LightLevel;
import jeresources.api.conditionals.LightLevel.Relative;
import jeresources.api.drop.LootDrop;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.monster.EntityMimic;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySkeletonWarrior;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.daveyx0.primitivemobs.entity.passive.EntityDodo;
import net.daveyx0.primitivemobs.entity.passive.EntityFilchLizard;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class JustEnoughResourcesIntegration implements IModIntegration {

	@JERPlugin
	public static IJERAPI jerAPI;

	@Override
	public void init() {
				World world = jerAPI.getWorld();
				
				//Loottable mob loot
				jerAPI.getMobRegistry().register(new EntityFestiveCreeper(world), LightLevel.hostile, PrimitiveMobsLootTables.ENTITIES_FESTIVECREEPER);
				jerAPI.getMobRegistry().register(new EntityRocketCreeper(world), LightLevel.hostile, PrimitiveMobsLootTables.ENTITIES_ROCKETCREEPER);
				jerAPI.getMobRegistry().register(new EntitySupportCreeper(world), LightLevel.hostile, PrimitiveMobsLootTables.ENTITIES_SUPPORTCREEPER);
				jerAPI.getMobRegistry().register(new EntityLilyLurker(world), PrimitiveMobsLootTables.ENTITIES_LILYLURKER);
				jerAPI.getMobRegistry().register(new EntityMimic(world), PrimitiveMobsLootTables.ENTITIES_MIMIC);
				jerAPI.getMobRegistry().register(new EntityDodo(world), PrimitiveMobsLootTables.ENTITIES_DODO);
				
				//Default mob loot
				jerAPI.getMobRegistry().register(new EntityBabySpider(world), LightLevel.hostile, LootTableList.ENTITIES_SPIDER);
				jerAPI.getMobRegistry().register(new EntityMotherSpider(world), LightLevel.hostile, LootTableList.ENTITIES_SPIDER);
				jerAPI.getMobRegistry().register(new EntityBrainSlime(world), LightLevel.any, LootTableList.ENTITIES_SLIME);
				jerAPI.getMobRegistry().register(new EntitySkeletonWarrior(world), LootTableList.ENTITIES_SKELETON);
				
				//Defined mob loot
				jerAPI.getMobRegistry().register(new EntityEnchantedBook(world), LightLevel.hostile, new LootDrop(new ItemStack(Items.ENCHANTED_BOOK)));
				jerAPI.getMobRegistry().register(new EntityTrollager(world), LightLevel.hostile,
						new LootDrop[]{new LootDrop(new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)), 0.5f), 
									   new LootDrop(new ItemStack(Items.EMERALD), 0.4f),
									   new LootDrop(new ItemStack(Items.GOLDEN_APPLE), 0.1f)
								});
				jerAPI.getMobRegistry().register(new EntityGroveSprite(world), LightLevel.hostile, new LootDrop(new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))));
				
				//Chest loot
				jerAPI.getDungeonRegistry().registerCategory("mimic_treasure", "JER.catagory.primitivemobs.mimic_treasure");
				jerAPI.getDungeonRegistry().registerChest("mimic_treasure", PrimitiveMobsLootTables.MIMIC_TREASURE);		
				jerAPI.getDungeonRegistry().registerCategory("mimic_trap", "JER.catagory.primitivemobs.mimic_trap");
				jerAPI.getDungeonRegistry().registerChest("mimic_trap", PrimitiveMobsLootTables.MIMIC_TRAP);
				
				//Custom mob loot
				jerAPI.getMobRegistry().register(new EntityHauntedTool(world), LightLevel.hostile, PrimitiveMobsLootTables.HAUNTEDTOOL_SPAWN);
				jerAPI.getMobRegistry().register(new EntityFilchLizard(world), PrimitiveMobsLootTables.FILCHLIZARD_SPAWN);
				jerAPI.getMobRegistry().register(new EntityTreasureSlime(world), PrimitiveMobsLootTables.TREASURESLIME_SPAWN);

	}
}