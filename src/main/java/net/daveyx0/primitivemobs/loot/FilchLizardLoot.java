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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scala.Tuple2;

public class FilchLizardLoot {

	private static final Map<Tuple2<Item, Integer>, Integer> possibleLoot = new HashMap<Tuple2<Item, Integer>, Integer>();
	
	public static void load()
	{
		possibleLoot.clear();
		String[] finalStealLoot = PrimitiveMobsConfigMobs.getFilchStealLoot();

		for(String entry : finalStealLoot)
		{
			addLoot(entry);
		}
	}
	
	public static String[] getDefaultValues()
	{
		return new String[]{
				"modID:itemName:metaData:weight",
				"minecraft:gold_ingot:0:3",
				"minecraft:iron_ingot:0:6",
				"minecraft:dye:4:4",
				"minecraft:diamond:0:1",
				"minecraft:emerald:0:1",
				"minecraft:ender_pearl:0:2",
				"minecraft:flint:0:8",
			};
	}
	
	public static void addLoot(String configEntry)
	{
		if(configEntry.equals("modID:itemName:metaData:weight")){return;}
		
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
				PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Treasure Slime treasure entry has incorrect format.");
			}
			
			if(!mod.isEmpty() && !item.isEmpty() && meta >= 0 && weight > 0)
			{
				addLoot(mod, item, meta, weight);
			}
			else
			{
				PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Treasure Slime treasure entry has incorrect format.");
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
			PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Treasure Slime treasure entry does not excist.");
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
			PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Treasure Slime treasure entry does not excist.");
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
			PrimitiveMobsLogger.PMlogger.log(Level.WARNING, "Treasure Slime treasure entry does not excist.");
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
	
	public static ItemStack[] getLootList()
	{
		if(!possibleLoot.isEmpty() && possibleLoot != null)
		{	
			ItemStack[] itemstacks = new ItemStack[getTotalWeight()];
			Collection<Tuple2<Item, Integer>> tuples = possibleLoot.keySet();
			
			Object[] tuplesArray = tuples.toArray(new Object[tuples.size()]);
			int currentIndex = 0;
			
			for(int i = 0; i < tuplesArray.length; i++)
			{			
					Tuple2<Item, Integer> tuple = (Tuple2<Item, Integer>)tuplesArray[i];
					
					itemstacks[currentIndex] = new ItemStack((Item)tuple._1, 1, (int)tuple._2);
					
					currentIndex++;
			}
			
			return itemstacks;
		}
		
		return new ItemStack[]{new ItemStack(Items.GOLD_INGOT)};
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
		
		return new ItemStack(Items.SLIME_BALL);
	}
}
