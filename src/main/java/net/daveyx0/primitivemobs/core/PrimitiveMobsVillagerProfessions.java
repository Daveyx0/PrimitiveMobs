package net.daveyx0.primitivemobs.core;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsVillagerProfessions {
	
	public static final Set<VillagerProfession> PROFESSIONS = new HashSet<>();
	
	 
	 public static final ITradeList[][] primitive_trades = new ITradeList[][]{};
	 public static VillagerProfession MINER_PROFESSION;
	 public static VillagerProfession MERCHANT_PROFESSION;
	 public static VillagerProfession FAKE_MERCHANT_PROFESSION;
	 public static VillagerProfession SHEEPMAN_PROFESSION_SCAVENGER;
	 public static VillagerProfession SHEEPMAN_PROFESSION_ALCHEMIST;
	 public static VillagerProfession SHEEPMAN_PROFESSION_THIEF;

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
							 //.addTrade(1, new EntityVillager.EmeraldForItems(Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1)))
							 .addTrade(1, new EntityVillager.ListItemForEmeralds(Items.COAL, new EntityVillager.PriceInfo(-22, -14)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.IRON_ORE), new EntityVillager.PriceInfo(-8, -6)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.GOLD_ORE), new EntityVillager.PriceInfo(-9, -7)))
							 .addTrade(3, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.OBSIDIAN), new EntityVillager.PriceInfo(-3, -1)));
							 new VillagerCareer(MINER_PROFESSION, "primitivemobs.stone_miner")
							 //.addTrade(1, new EntityVillager.EmeraldForItems(Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1)))
							 .addTrade(1, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.COBBLESTONE), new EntityVillager.PriceInfo(-60, -45)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 1), new EntityVillager.PriceInfo(-15, -10)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 3), new EntityVillager.PriceInfo(-15, -10)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, 5), new EntityVillager.PriceInfo(-15, -10)))
							 .addTrade(3, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.STONE), new EntityVillager.PriceInfo(-20, -15)))
							 .addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.OBSIDIAN), new EntityVillager.PriceInfo(-3, -1)));
							 new VillagerCareer(MINER_PROFESSION, "primitivemobs.gem_miner")
							 //.addTrade(1, new EntityVillager.EmeraldForItems(Items.IRON_PICKAXE, new EntityVillager.PriceInfo(1, 1)))
							 .addTrade(1, new EntityVillager.ListItemForEmeralds(new ItemStack(Items.DYE, 1, 4), new EntityVillager.PriceInfo(-4, -3)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Items.REDSTONE, new EntityVillager.PriceInfo(-6, -3)))
							 .addTrade(2, new EntityVillager.ListItemForEmeralds(Items.QUARTZ, new EntityVillager.PriceInfo(-6, -3)))
							 .addTrade(3, new EntityVillager.ListItemForEmeralds(Items.DIAMOND, new EntityVillager.PriceInfo(2, 3)));
						 }
						 
					MERCHANT_PROFESSION = new VillagerProfession("primitivemobs:merchant",
						 "primitivemobs:textures/entity/villager/villager_merchant.png",
						 "primitivemobs:textures/entity/villager/zombie_merchant.png");
						 {
							registry.register(MERCHANT_PROFESSION);
						 	new VillagerCareer(MERCHANT_PROFESSION, "primitivemobs.traveling_merchant")
						 	{
						 		
						    private List<List<ITradeList>> merchantTrades = Lists.newArrayList();
						 		
					        @Nullable
					        @Override
					        public List<ITradeList> getTrades(int level)
					        {
					        	if(level >= 3) {return null;}
						    	Random rand =  new Random();
					        	
					        	List<ITradeList> trades = Lists.newArrayList();
					        	
					        	if(level == 0)
					        	{
					        		trades.add(merchantTrades.get(level).get(merchantTrades.get(level).size() - 1));
					        	}
					        	
					        	trades.add(getRandomMerchantTrade(rand, level, trades, merchantTrades.get(level + 1)));
					        	trades.add(getRandomMerchantTrade(rand, level, trades, merchantTrades.get(level + 1)));

					        	if(trades.isEmpty()){return null;}
					            return trades;
					        }
					        
					        public VillagerCareer addTrade(int level, ITradeList... trades)
					        {
					            if (level <= 0)
					                throw new IllegalArgumentException("Levels start at 1");

					            List<ITradeList> levelTrades = level <= this.merchantTrades.size() ? this.merchantTrades.get(level - 1) : null;
					            if (levelTrades == null)
					            {
					                while (this.merchantTrades.size() < level)
					                {
					                    levelTrades = Lists.newArrayList();
					                    this.merchantTrades.add(levelTrades);
					                }
					            }
					            if (levelTrades == null) //Not sure how this could happen, but screw it
					            {
					                levelTrades = Lists.newArrayList();
					                this.merchantTrades.set(level - 1, levelTrades);
					            }
					            for (ITradeList t : trades)
					                levelTrades.add(t);
					            return this;
					        }
						 	}.addTrade(1, new EntityVillager.EmeraldForItems(Items.CARROT, new EntityVillager.PriceInfo(10, 14)))
						 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.BREAD, new EntityVillager.PriceInfo(4, 6)))
						 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.APPLE, new EntityVillager.PriceInfo(4, 6)))
						 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_BEEF, new EntityVillager.PriceInfo(4, 6)))
						 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_CHICKEN, new EntityVillager.PriceInfo(5, 7)))
						 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_FISH, new EntityVillager.PriceInfo(3, 5)))
						 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_PORKCHOP, new EntityVillager.PriceInfo(4, 6)))
						 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_RABBIT, new EntityVillager.PriceInfo(3, 5)))
						 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.PUMPKIN_SEEDS, new EntityVillager.PriceInfo(-10, -8)))
						 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.MELON_SEEDS, new EntityVillager.PriceInfo(-10, -8)))
						 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.GOLD_INGOT, new EntityVillager.PriceInfo(-10, -8)))
						 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.ARROW, new EntityVillager.PriceInfo(-15, -10)))
						 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.SLIME_BALL, new EntityVillager.PriceInfo(-7, -4)))
						 	.addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Items.DYE, 1, 3), new EntityVillager.PriceInfo(-8, -6)))
						 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.BEETROOT, new EntityVillager.PriceInfo(-16, -10)))
				    		.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.PAPER, new EntityVillager.PriceInfo(-38, -26)))
				    		.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.FLINT, new EntityVillager.PriceInfo(-10, -8)))
				    		.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.SNOWBALL, new EntityVillager.PriceInfo(-16, -14)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.BONE, new EntityVillager.PriceInfo(-10, -8)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.FIRE_CHARGE, new EntityVillager.PriceInfo(-10, -8)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.IRON_INGOT, new EntityVillager.PriceInfo(-10, -7)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.ARROW, new EntityVillager.PriceInfo(-15, -10)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.STRING, new EntityVillager.PriceInfo(-20, -15)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.CLAY_BALL, new EntityVillager.PriceInfo(-10, -6)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.RABBIT_FOOT, new EntityVillager.PriceInfo(-3, -2)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.REDSTONE, new EntityVillager.PriceInfo(-6, -3)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.BLAZE_POWDER, new EntityVillager.PriceInfo(-6, -4)))
				    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.BOOK, new EntityVillager.PriceInfo(-6, -4)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.ENDER_PEARL, new EntityVillager.PriceInfo(-1, -1)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.EXPERIENCE_BOTTLE, new EntityVillager.PriceInfo(1, 4)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.DIAMOND, new EntityVillager.PriceInfo(2, 3)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.GHAST_TEAR, new EntityVillager.PriceInfo(2, 4)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.PRISMARINE_CRYSTALS, new EntityVillager.PriceInfo(-7, -4)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.NAME_TAG, new EntityVillager.PriceInfo(-10, -5)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(PotionUtils.addPotionToItemStack(new ItemStack(Items.TIPPED_ARROW, 1), PotionType.getPotionTypeForName("poison")), new EntityVillager.PriceInfo(-15, -10)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.SADDLE, new EntityVillager.PriceInfo(4, 6)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.CHORUS_FRUIT, new EntityVillager.PriceInfo(-3, -2)))
				    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.GOLDEN_APPLE, new EntityVillager.PriceInfo(1, 1)));

						 }
						 
						 FAKE_MERCHANT_PROFESSION = new VillagerProfession("primitivemobs:fakemerchant",
								 "primitivemobs:textures/entity/villager/villager_merchant.png",
								 "primitivemobs:textures/entity/villager/zombie_merchant.png");
						 {
							 	registry.register(FAKE_MERCHANT_PROFESSION);
							 	new VillagerCareer(FAKE_MERCHANT_PROFESSION, "primitivemobs.fake_traveling_merchant")
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.CARROT, new EntityVillager.PriceInfo(10, 14)))
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.BREAD, new EntityVillager.PriceInfo(4, 6)))
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.APPLE, new EntityVillager.PriceInfo(4, 6)))
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_BEEF, new EntityVillager.PriceInfo(4, 6)))
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_CHICKEN, new EntityVillager.PriceInfo(5, 7)))
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_FISH, new EntityVillager.PriceInfo(3, 5)))
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_PORKCHOP, new EntityVillager.PriceInfo(4, 6)))
							 	.addTrade(1, new EntityVillager.EmeraldForItems(Items.COOKED_RABBIT, new EntityVillager.PriceInfo(3, 5)))
							 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.PUMPKIN_SEEDS, new EntityVillager.PriceInfo(-10, -8)))
							 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.MELON_SEEDS, new EntityVillager.PriceInfo(-10, -8)))
							 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.GOLD_INGOT, new EntityVillager.PriceInfo(-10, -8)))
							 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.ARROW, new EntityVillager.PriceInfo(-15, -10)))
							 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.SLIME_BALL, new EntityVillager.PriceInfo(-7, -4)))
							 	.addTrade(2, new EntityVillager.ListItemForEmeralds(new ItemStack(Items.DYE, 1, 3), new EntityVillager.PriceInfo(-8, -6)))
							 	.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.BEETROOT, new EntityVillager.PriceInfo(-16, -10)))
					    		.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.PAPER, new EntityVillager.PriceInfo(-38, -26)))
					    		.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.FLINT, new EntityVillager.PriceInfo(-10, -8)))
					    		.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.SNOWBALL, new EntityVillager.PriceInfo(-16, -14)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.BONE, new EntityVillager.PriceInfo(-10, -8)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.FIRE_CHARGE, new EntityVillager.PriceInfo(-10, -8)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.IRON_INGOT, new EntityVillager.PriceInfo(-10, -7)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.ARROW, new EntityVillager.PriceInfo(-15, -10)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.STRING, new EntityVillager.PriceInfo(-20, -15)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.CLAY_BALL, new EntityVillager.PriceInfo(-10, -6)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.RABBIT_FOOT, new EntityVillager.PriceInfo(-3, -2)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.REDSTONE, new EntityVillager.PriceInfo(-6, -3)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.BLAZE_POWDER, new EntityVillager.PriceInfo(-6, -4)))
					    		.addTrade(3, new EntityVillager.ListItemForEmeralds(Items.BOOK, new EntityVillager.PriceInfo(-6, -4)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.ENDER_PEARL, new EntityVillager.PriceInfo(-1, -1)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.EXPERIENCE_BOTTLE, new EntityVillager.PriceInfo(1, 4)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.DIAMOND, new EntityVillager.PriceInfo(2, 3)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.GHAST_TEAR, new EntityVillager.PriceInfo(2, 4)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.PRISMARINE_CRYSTALS, new EntityVillager.PriceInfo(-7, -4)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.NAME_TAG, new EntityVillager.PriceInfo(-10, -5)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(PotionUtils.addPotionToItemStack(new ItemStack(Items.TIPPED_ARROW, 1), PotionType.getPotionTypeForName("poison")), new EntityVillager.PriceInfo(-15, -10)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.SADDLE, new EntityVillager.PriceInfo(4, 6)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.CHORUS_FRUIT, new EntityVillager.PriceInfo(-3, -2)))
					    		.addTrade(4, new EntityVillager.ListItemForEmeralds(Items.GOLDEN_APPLE, new EntityVillager.PriceInfo(1, 1)));
						 }
						 
						 SHEEPMAN_PROFESSION_SCAVENGER = new VillagerProfession("primitivemobs:sheepman_scavenger",
						             "primitivemobs:textures/entity/villager/lostminer.png",
						             "primitivemobs:textures/entity/villager/zombie_miner.png");
									 {
										 registry.register(SHEEPMAN_PROFESSION_SCAVENGER);
										 new VillagerCareer(SHEEPMAN_PROFESSION_SCAVENGER, "primitivemobs.sheepman_scavenger")
										 .addTrade(1, new ListItemForGoldIngots(Items.NETHERBRICK, new EntityVillager.PriceInfo(-6, -4)))
										 .addTrade(2, new ListItemForGoldIngots(Items.FLINT, new EntityVillager.PriceInfo(-4, -2)))
										 .addTrade(2, new ListItemForGoldIngots(Items.STICK, new EntityVillager.PriceInfo(-10, -8)))
										 .addTrade(3, new ListItemForGoldIngots(Items.FLINT_AND_STEEL, new EntityVillager.PriceInfo(1, 2)))
										 .addTrade(3, new ListItemForGoldIngots(Items.QUARTZ, new EntityVillager.PriceInfo(-5, -3)))
										 .addTrade(4, new ListItemForGoldIngots(Item.getItemFromBlock(Blocks.OBSIDIAN), new EntityVillager.PriceInfo(12, 15)));
									 }
									 SHEEPMAN_PROFESSION_ALCHEMIST = new VillagerProfession("primitivemobs:sheepman_alchemist",
								             "primitivemobs:textures/entity/villager/lostminer.png",
								             "primitivemobs:textures/entity/villager/zombie_miner.png");
											 {
												 registry.register(SHEEPMAN_PROFESSION_ALCHEMIST);
												 new VillagerCareer(SHEEPMAN_PROFESSION_ALCHEMIST, "primitivemobs.sheepman_alchemist")
												 .addTrade(1, new ListItemForGoldIngots(Items.NETHER_WART, new EntityVillager.PriceInfo(2, 4)))
												 .addTrade(1, new ListItemForGoldIngots(Items.GLASS_BOTTLE, new EntityVillager.PriceInfo(1, 2)))
												 .addTrade(2, new ListItemForGoldIngots(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new EntityVillager.PriceInfo(4, 6)))
												 .addTrade(3, new ListItemForGoldIngots(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.REGENERATION), new EntityVillager.PriceInfo(12, 15)))
												 .addTrade(3, new ListItemForGoldIngots(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.FIRE_RESISTANCE), new EntityVillager.PriceInfo(10, 13)))
												 .addTrade(3, new ListItemForGoldIngots(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HEALING), new EntityVillager.PriceInfo(15, 18)))
												 .addTrade(4, new ListItemForGoldIngots(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.LONG_FIRE_RESISTANCE), new EntityVillager.PriceInfo(32, 40)));
											 }
											 SHEEPMAN_PROFESSION_THIEF = new VillagerProfession("primitivemobs:sheepman_thief",
										             "primitivemobs:textures/entity/villager/lostminer.png",
										             "primitivemobs:textures/entity/villager/zombie_miner.png");
													 {
														 registry.register(SHEEPMAN_PROFESSION_THIEF);
														 new VillagerCareer(SHEEPMAN_PROFESSION_THIEF, "primitivemobs.sheepman_thief")
														 .addTrade(1, new ListItemForGoldIngots(Items.GLOWSTONE_DUST, new EntityVillager.PriceInfo(-10, -8)))
														 .addTrade(2, new ListItemForGoldIngots(Items.BLAZE_POWDER, new EntityVillager.PriceInfo(2, 3)))
														 .addTrade(3, new ListItemForGoldIngots(Items.MAGMA_CREAM, new EntityVillager.PriceInfo(5, 8)))
														 .addTrade(3, new ListItemForGoldIngots(Items.BLAZE_ROD, new EntityVillager.PriceInfo(5, 8)))
														 .addTrade(4, new ListItemForGoldIngots(Items.GHAST_TEAR, new EntityVillager.PriceInfo(35, 40)));
													 }
									 

						 

						 
				final VillagerProfession[] professions = {
						MINER_PROFESSION,
						MERCHANT_PROFESSION,
						FAKE_MERCHANT_PROFESSION,
						SHEEPMAN_PROFESSION_SCAVENGER,
						SHEEPMAN_PROFESSION_ALCHEMIST,
						SHEEPMAN_PROFESSION_THIEF
				};
				
				for (final VillagerProfession profession : professions) {

					PROFESSIONS.add(profession);

				}
			}
	    }

	    
	    public static ITradeList getRandomMerchantTrade(Random rand, int level, List<ITradeList> currentTrades, List<ITradeList> tradesForLevel)
	    {
	    	EntityVillager.ListItemForEmeralds trade = (EntityVillager.ListItemForEmeralds)tradesForLevel.get(rand.nextInt(tradesForLevel.size() - 1));
	    	
	    	int index = 0;
	    	if(level == 0) {index = 1;}
	    	
	    	if(!currentTrades.isEmpty() && !(currentTrades.get(0) instanceof EntityVillager.EmeraldForItems))
	    	{
	    		for(int i = 0; i < 100; i++)
	    		{
	    			if(trade.itemToBuy.getItem().equals(((EntityVillager.ListItemForEmeralds)currentTrades.get(index)).itemToBuy.getItem()))
	    			{
	    				trade = (EntityVillager.ListItemForEmeralds)tradesForLevel.get(rand.nextInt(tradesForLevel.size() - 1));
	    			}
	    			else
	    			{
	    				return trade;
	    			}
	    		}
	    	}

	    	return trade;
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
	    
	    public static class GoldIngotsForItems implements EntityVillager.ITradeList
        {
            public Item buyingItem;
            public EntityVillager.PriceInfo price;

            public GoldIngotsForItems(Item itemIn, EntityVillager.PriceInfo priceIn)
            {
                this.buyingItem = itemIn;
                this.price = priceIn;
            }

            public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
            {
                int i = 1;

                if (this.price != null)
                {
                    i = this.price.getPrice(random);
                }

                recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItem, i, 0), Items.GOLD_INGOT));
            }
        }
	    
	    public static class ListItemForGoldIngots implements EntityVillager.ITradeList
        {
            /** The item that is being bought for emeralds */
            public ItemStack itemToBuy;
            /**
             * The price info for the amount of emeralds to sell for, or if negative, the amount of the item to buy for
             * an emerald.
             */
            public EntityVillager.PriceInfo priceInfo;

            public ListItemForGoldIngots(Item par1Item, EntityVillager.PriceInfo priceInfo)
            {
                this.itemToBuy = new ItemStack(par1Item);
                this.priceInfo = priceInfo;
            }

            public ListItemForGoldIngots(ItemStack stack, EntityVillager.PriceInfo priceInfo)
            {
                this.itemToBuy = stack;
                this.priceInfo = priceInfo;
            }

            public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
            {
                int i = 1;

                if (this.priceInfo != null)
                {
                    i = this.priceInfo.getPrice(random);
                }

                ItemStack itemstack;
                ItemStack itemstack1;

                if (i < 0)
                {
                    itemstack = new ItemStack(Items.GOLD_INGOT);
                    itemstack1 = new ItemStack(this.itemToBuy.getItem(), -i, this.itemToBuy.getMetadata());
                    if(this.itemToBuy.getItem() instanceof ItemPotion)
                    {
                    	itemstack1 = itemToBuy.copy();
                    }
                }
                else
                {
                    itemstack = new ItemStack(Items.GOLD_INGOT, i, 0);
                    itemstack1 = new ItemStack(this.itemToBuy.getItem(), 1, this.itemToBuy.getMetadata());
                    if(this.itemToBuy.getItem() instanceof ItemPotion)
                    {
                    	itemstack1 = itemToBuy.copy();
                    }
                }

                recipeList.add(new MerchantRecipe(itemstack, itemstack1));
            }
        }
	    
	    /*
	    public static ITradeList[][] merchantTrades = new EntityVillager.ITradeList[][]{
	    		new EntityVillager.ITradeList[]{
	    		new EntityVillager.EmeraldForItems(Items.CARROT, new EntityVillager.PriceInfo(10, 14)),
	    		new EntityVillager.EmeraldForItems(Items.BREAD, new EntityVillager.PriceInfo(4, 6)),
	    		new EntityVillager.EmeraldForItems(Items.APPLE, new EntityVillager.PriceInfo(4, 6)),
	    		new EntityVillager.EmeraldForItems(Items.COOKED_BEEF, new EntityVillager.PriceInfo(4, 6)),
	    		new EntityVillager.EmeraldForItems(Items.COOKED_CHICKEN, new EntityVillager.PriceInfo(5, 7)),
	    		new EntityVillager.EmeraldForItems(Items.COOKED_FISH, new EntityVillager.PriceInfo(3, 5)),
	    		new EntityVillager.EmeraldForItems(Items.COOKED_PORKCHOP, new EntityVillager.PriceInfo(4, 6)),
	    		new EntityVillager.EmeraldForItems(Items.COOKED_RABBIT, new EntityVillager.PriceInfo(3, 5))
	    		}, new EntityVillager.ITradeList[]{
	    		new EntityVillager.ListItemForEmeralds(Items.PUMPKIN_SEEDS, new EntityVillager.PriceInfo(-10, -8)),
	    		new EntityVillager.ListItemForEmeralds(Items.MELON_SEEDS, new EntityVillager.PriceInfo(-10, -8)),
	    		new EntityVillager.ListItemForEmeralds(Items.GOLD_INGOT, new EntityVillager.PriceInfo(-10, -8)),
	    		new EntityVillager.ListItemForEmeralds(Items.ARROW, new EntityVillager.PriceInfo(-15, -10)),
	    		new EntityVillager.ListItemForEmeralds(Items.SLIME_BALL, new EntityVillager.PriceInfo(-7, -4)),
	    		new EntityVillager.ListItemForEmeralds(new ItemStack(Items.DYE, 1, 3), new EntityVillager.PriceInfo(-8, -6)),
	    		new EntityVillager.ListItemForEmeralds(Items.BEETROOT, new EntityVillager.PriceInfo(-16, -10)),
	    		new EntityVillager.ListItemForEmeralds(Items.PAPER, new EntityVillager.PriceInfo(-38, -26)),
	    		new EntityVillager.ListItemForEmeralds(Items.FLINT, new EntityVillager.PriceInfo(-10, -8)),
	    		new EntityVillager.ListItemForEmeralds(Items.SNOWBALL, new EntityVillager.PriceInfo(-16, -14))
	    		}, new EntityVillager.ITradeList[]{
	    		new EntityVillager.ListItemForEmeralds(Items.BONE, new EntityVillager.PriceInfo(-10, -8)),
	    		new EntityVillager.ListItemForEmeralds(Items.FIRE_CHARGE, new EntityVillager.PriceInfo(-10, -8)),
	    		new EntityVillager.ListItemForEmeralds(Items.IRON_INGOT, new EntityVillager.PriceInfo(-10, -7)),
	    		new EntityVillager.ListItemForEmeralds(Items.ARROW, new EntityVillager.PriceInfo(-15, -10)),
	    		new EntityVillager.ListItemForEmeralds(Items.STRING, new EntityVillager.PriceInfo(-20, -15)),
	    		new EntityVillager.ListItemForEmeralds(Items.CLAY_BALL, new EntityVillager.PriceInfo(-10, -6)),
	    		new EntityVillager.ListItemForEmeralds(Items.RABBIT_FOOT, new EntityVillager.PriceInfo(-3, -2)),
	    		new EntityVillager.ListItemForEmeralds(Items.REDSTONE, new EntityVillager.PriceInfo(-6, -3)),
	    		new EntityVillager.ListItemForEmeralds(Items.BLAZE_POWDER, new EntityVillager.PriceInfo(-6, -4)),
	    		new EntityVillager.ListItemForEmeralds(Items.BOOK, new EntityVillager.PriceInfo(-6, -4))
	    		}, new EntityVillager.ITradeList[]{
	    		new EntityVillager.ListItemForEmeralds(Items.ENDER_PEARL, new EntityVillager.PriceInfo(-1, -1)),
	    		new EntityVillager.ListItemForEmeralds(Items.EXPERIENCE_BOTTLE, new EntityVillager.PriceInfo(1, 4)),
	    		new EntityVillager.ListItemForEmeralds(Items.DIAMOND, new EntityVillager.PriceInfo(2, 3)),
	    		new EntityVillager.ListItemForEmeralds(Items.GHAST_TEAR, new EntityVillager.PriceInfo(2, 4)),
	    		new EntityVillager.ListItemForEmeralds(Items.PRISMARINE_CRYSTALS, new EntityVillager.PriceInfo(-7, -4)),
	    		new EntityVillager.ListItemForEmeralds(Items.NAME_TAG, new EntityVillager.PriceInfo(-10, -5)),
	    		new EntityVillager.ListItemForEmeralds(PotionUtils.addPotionToItemStack(new ItemStack(Items.TIPPED_ARROW, 1), PotionType.getPotionTypeForName("poison")), new EntityVillager.PriceInfo(-15, -10)),
	    		new EntityVillager.ListItemForEmeralds(Items.SADDLE, new EntityVillager.PriceInfo(4, 6)),
	    		new EntityVillager.ListItemForEmeralds(Items.CHORUS_FRUIT, new EntityVillager.PriceInfo(-3, -2)),
	    		new EntityVillager.ListItemForEmeralds(Items.GOLDEN_APPLE, new EntityVillager.PriceInfo(1, 1))
	    		}};*/
	    
}

