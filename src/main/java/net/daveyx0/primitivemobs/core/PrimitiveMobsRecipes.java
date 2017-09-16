package net.daveyx0.primitivemobs.core;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.registries.IForgeRegistry;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.daveyx0.primitivemobs.recipe.CamouflageArmorRecipe;

public class PrimitiveMobsRecipes {

    public static void init() {
        registerRecipes();
    }
    
    public static void registerRecipes() {
    	
    	//RecipeSorter.register("primitivemobs:camouflagerecipe", CamouflageArmorRecipe.class, SHAPELESS, "after:forge:shapelessore");
    	
    	//Camouflage armor
		//GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_HELMET, 1), new Object [] {new ItemStack(Items.LEATHER_HELMET) , new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_DYE, 1)});
		//GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_CHEST, 1), new Object [] {new ItemStack(Items.LEATHER_CHESTPLATE) , new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_DYE, 1)});
		//GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_LEGS, 1), new Object [] {new ItemStack(Items.LEATHER_LEGGINGS) , new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_DYE, 1)});
		//GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_BOOTS, 1), new Object [] {new ItemStack(Items.LEATHER_BOOTS) , new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_DYE, 1)});
		
		//ItemStack stack = new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_BOOTS, 1);
		//GameRegistry.addShapelessRecipe(stack, stack);
		/*
		GameRegistry.addRecipe(new CamouflageArmorRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_HELMET, 1)));
		GameRegistry.addRecipe(new CamouflageArmorRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_CHEST, 1)));
		GameRegistry.addRecipe(new CamouflageArmorRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_LEGS, 1)));
		GameRegistry.addRecipe(new CamouflageArmorRecipe(new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_BOOTS, 1)));*/
    }
    
}
