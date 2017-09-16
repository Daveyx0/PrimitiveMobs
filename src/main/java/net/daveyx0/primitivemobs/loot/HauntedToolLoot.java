package net.daveyx0.primitivemobs.loot;

import java.awt.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scala.Tuple2;

public class HauntedToolLoot {

	private static final Map<Tuple2<Item, Integer>, Integer> possibleLoot = new HashMap<Tuple2<Item, Integer>, Integer>();

	public static void load()
	{
		possibleLoot.clear();
		String[] finalToolLoot = PrimitiveMobsConfigMobs.getHauntedToolLoot();

		for(String entry : finalToolLoot)
		{
			addLoot(entry);
		}
	}
	
	public static String[] getDefaultValues()
	{
		return new String[]{
				"modID:toolName:metaData:weight",
				"minecraft:wooden_sword:0:10",
				"minecraft:wooden_axe:0:10",
				"minecraft:wooden_pickaxe:0:10",
				"minecraft:wooden_hoe:0:10",
				"minecraft:stone_sword:0:8",
				"minecraft:stone_axe:0:8",
				"minecraft:stone_pickaxe:0:8",
				"minecraft:stone_hoe:0:8",
				"minecraft:iron_sword:0:5",
				"minecraft:iron_axe:0:5",
				"minecraft:iron_pickaxe:0:5",
				"minecraft:iron_hoe:0:5",
				"minecraft:golden_sword:0:3",
				"minecraft:golden_axe:0:3",
				"minecraft:golden_pickaxe:0:3",
				"minecraft:golden_hoe:0:3",
				"minecraft:diamond_sword:0:1",
				"minecraft:diamond_axe:0:1",
				"minecraft:diamond_pickaxe:0:1",
				"minecraft:diamond_hoe:0:1"
			};
	}
	
	public static float getHealthFromTool(ItemStack tool)
	{
		if(!tool.isEmpty() && tool.getItem().isDamageable())
		{
			float health = tool.getItem().getMaxDamage(tool)/10f;
			if(health > 100f)
			{
				health = 100f;
			}
			return health;
		}

		return 10f;
	}
	
	public static double getDamageFromHeldItem(EntityLiving entity)
	{
		if(!entity.getHeldItemMainhand().isEmpty() && entity.getHeldItemMainhand().getItem().isDamageable());
		{
			Collection<AttributeModifier> modifiers = entity.getHeldItemMainhand().getAttributeModifiers(entity.getSlotForItemStack(entity.getHeldItemMainhand())).get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
			if(modifiers != null && !modifiers.isEmpty())
			{
				Object[] mods = modifiers.toArray(new Object[modifiers.size()]);
				AttributeModifier attribute = (AttributeModifier)mods[0];
				double attackDamage = (attribute.getAmount() / 2D);
				if(attackDamage <= 1.0D)
				{
					attackDamage = 1.0D;
				}
				else if(attackDamage > 8.0D)
				{
					attackDamage = 8.0D;
				}
				
				return attackDamage;
			}
		}

		return 1.0D;
	}
	
	public static double getSpeedFromHeldItem(EntityLiving entity)
	{
		if(!entity.getHeldItemMainhand().isEmpty() && entity.getHeldItemMainhand().getItem().isDamageable())
		{
			Collection<AttributeModifier> modifiers = entity.getHeldItemMainhand().getAttributeModifiers(entity.getSlotForItemStack(entity.getHeldItemMainhand())).get(SharedMonsterAttributes.ATTACK_SPEED.getName());
			if(modifiers != null && !modifiers.isEmpty())
			{
				Object[] mods = modifiers.toArray(new Object[modifiers.size()]);
				AttributeModifier attribute = (AttributeModifier)mods[0];
				double attackSpeed = (0.5D - (attribute.getAmount() * -1 * 0.1D));
				
				if(attackSpeed <= 0.1D)
				{
					attackSpeed = 0.1D;
				}
				else if(attackSpeed > 0.3D)
				{
					attackSpeed = 0.3D;
				}
				return attackSpeed;
			}
		}

		return 0.2D;
	}

	public static void addLoot(String configEntry)
	{
		if(configEntry.equals("modID:toolName:metaData:weight")){return;}
		
		String[] dividedEntry = configEntry.split(":");
		
		if(dividedEntry != null && dividedEntry.length == 4)
		{
			String mod = dividedEntry[0];
			String item = dividedEntry[1];
			int meta = 0;
			int weight = 0;
			
			try{
				meta = Integer.parseInt(dividedEntry[2]);
				weight = Integer.parseInt(dividedEntry[3]);
			} catch (NumberFormatException e) 
			{
				PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Haunted Tool tool entry has incorrect format.");
			}
			
			if(!mod.isEmpty() && !item.isEmpty() && meta >= 0 && weight > 0)
			{
				addLoot(mod, item, meta, weight);
			}
			else
			{
				PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Haunted Tool tool entry has incorrect format.");
			}
		}
	}
	
	public static void addLoot(Item item, int meta, int weight)
	{
		if(Item.REGISTRY.containsKey(item.getRegistryName())) 
		{
			possibleLoot.put(new Tuple2(item, meta), weight);
		}
		else
		{
			PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Haunted Tool tool entry does not excist.");
		}
	}
	
	public static void addLoot(String resourcelocation , int meta, int weight)
	{
		if(Item.REGISTRY.containsKey(new ResourceLocation(resourcelocation)))
		{
			possibleLoot.put(new Tuple2(Item.REGISTRY.getObject(new ResourceLocation(resourcelocation)), meta), weight);
		}
		else
		{
			PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Haunted Tool tool entry does not excist.");
		}
	}
	
	public static void addLoot(String mod, String itemName , int meta, int weight)
	{
		if(Item.REGISTRY.containsKey(new ResourceLocation(mod, itemName)))
		{
			possibleLoot.put(new Tuple2(Item.REGISTRY.getObject(new ResourceLocation(mod, itemName)), meta), weight);
		}
		else
		{
			PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Haunted Tool tool entry does not excist.");
		}
	}
	
	public static int getTotalWeight()
	{
		Collection<Integer> weights = possibleLoot.values();
		int totalWeights = 0;
		for(int weight : weights)
		{
			totalWeights+= weight;
		}
		
		return totalWeights;
	}
	
	public static ItemStack getRandomLootItem(Random random)
	{
		if(!possibleLoot.isEmpty() && possibleLoot != null)
		{	
			Item[] items = new Item[getTotalWeight()];
			int[] metas = new int[getTotalWeight()];
			
			Collection<Integer> weights = possibleLoot.values();
			Collection<Tuple2<Item, Integer>> tuples = possibleLoot.keySet();
			
			Object[] weightsArray = weights.toArray(new Object[weights.size()]);
			Object[] tuplesArray = tuples.toArray(new Object[tuples.size()]);
			
			int currentIndex = 0;
			
			for(int i = 0; i < tuplesArray.length; i++)
			{			
				for(int j = 0; j < (int)weightsArray[i] ; j++)
				{
					Tuple2<Item, Integer> tuple = (Tuple2<Item, Integer>)tuplesArray[i];
					
					items[currentIndex] = (Item)tuple._1;
					metas[currentIndex] = (int)tuple._2;
					
					currentIndex++;
				}
			}

			if(items != null && items.length > 0)
			{
				int randomIndex = random.nextInt(items.length);
				
				ItemStack randomStack = new ItemStack(items[randomIndex], 1, metas[randomIndex]);
				
				if(!randomStack.isEmpty())
				{
					return randomStack;
				}
			}
		}
		
		return new ItemStack(Items.WOODEN_SWORD);
	}
}

