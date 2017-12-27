package net.daveyx0.primitivemobs.core;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Lists;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.item.ItemCamouflageDye;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsVillagerProfessions {
	
	public static final Set<VillagerProfession> PROFESSIONS = new HashSet<>();
	
	 
	 public static final ITradeList[][] primitive_trades = new ITradeList[][]{};
	 public static VillagerProfession MINER_PROFESSION;


	 private static void initialiseVillagerProfessions() {
		 
	 }

	    @Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
		public static class RegistrationHandler {

			@SubscribeEvent
			public static void registerProfessions(final RegistryEvent.Register<VillagerProfession> event) {
				final IForgeRegistry<VillagerProfession> registry = event.getRegistry();
				
				MINER_PROFESSION = new VillagerProfession("primitivemobs:miner",
			             "primitivemobs:textures/entity/villager/lostminer.png",
			             "primitivemobs:textures/entity/villager/zombie_miner.png");
						 {
							 registry.register(MINER_PROFESSION);
							 new VillagerCareer(MINER_PROFESSION, "primitivemobs.ore_miner")
							 .addTrade(1, new ItemAndItemToEmerald(Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1), Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1)))
							 .addTrade(1, new EntityVillager.ListItemForEmeralds(Items.COAL, new EntityVillager.PriceInfo(-22, -14)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.IRON_ORE), new EntityVillager.PriceInfo(-8, -6)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.GOLD_ORE), new EntityVillager.PriceInfo(-9, -7)))
							 .addTrade(3, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.OBSIDIAN), new EntityVillager.PriceInfo(-3, -1)));
							 new VillagerCareer(MINER_PROFESSION, "primitivemobs.stone_miner")
							 .addTrade(1, new ItemAndItemToEmerald(Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1), Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1)))
							 .addTrade(1, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.COBBLESTONE), new EntityVillager.PriceInfo(-60, -45)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 1), new EntityVillager.PriceInfo(-15, -10)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 3), new EntityVillager.PriceInfo(-15, -10)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 5), new EntityVillager.PriceInfo(-15, -10)))
							 .addTrade(3, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.STONE), new EntityVillager.PriceInfo(-20, -15)))
							 .addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.OBSIDIAN), new EntityVillager.PriceInfo(-3, -1)));
							 new VillagerCareer(MINER_PROFESSION, "primitivemobs.gem_miner")
							 .addTrade(1, new ItemAndItemToEmerald(Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1), Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1)))
							 .addTrade(1, new EntityVillager.ListItemForEmeralds(new ItemStack(Items.DYE, 1, 4), new EntityVillager.PriceInfo(-4, -3)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Items.REDSTONE, new EntityVillager.PriceInfo(-6, -3)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Items.QUARTZ, new EntityVillager.PriceInfo(-6, -3)))
							 .addTrade(3, new EntityVillager.ListItemForEmeralds(Items.DIAMOND, new EntityVillager.PriceInfo(2, 3)));
						 }
						 
				final VillagerProfession[] professions = {
						MINER_PROFESSION
				};
				
				for (final VillagerProfession profession : professions) {

					PROFESSIONS.add(profession);

				}
				
				initialiseVillagerProfessions();
			}
	    }
	    
	    public static class ItemAndItemToEmerald implements EntityVillager.ITradeList
        {
            public ItemStack buyingItemStack;
            public EntityVillager.PriceInfo buyingPriceInfo;
            public ItemStack buyingItemStack2;
            public EntityVillager.PriceInfo buyingPriceInfo2;

            public ItemAndItemToEmerald(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_)
            {
                this.buyingItemStack = new ItemStack(p_i45813_1_);
                this.buyingPriceInfo = p_i45813_2_;
                this.buyingItemStack2 = new ItemStack(p_i45813_3_);
                this.buyingPriceInfo2 = p_i45813_4_;
            }

            public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
            {
                int i = this.buyingPriceInfo.getPrice(random);
                int j = this.buyingPriceInfo2.getPrice(random);
                recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItemStack.getItem(), i, this.buyingItemStack.getMetadata()), new ItemStack(this.buyingItemStack2.getItem(), i, this.buyingItemStack2.getMetadata()), new ItemStack(Items.EMERALD)));
            }
        }
}

