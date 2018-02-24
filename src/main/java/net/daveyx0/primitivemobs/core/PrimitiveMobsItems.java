package net.daveyx0.primitivemobs.core;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.entity.passive.EntityDodo;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.item.ItemCamouflageDye;
import net.daveyx0.primitivemobs.item.ItemPrimitive;
import net.daveyx0.primitivemobs.item.ItemPrimitiveEgg;
import net.daveyx0.primitivemobs.item.ItemPrimitiveFood;
import net.daveyx0.primitivemobs.item.ItemSummonerOrb;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsItems {

	 public static final Set<Item> ITEMS = new HashSet<>();
	
	 public static final ItemCamouflageDye CAMOUFLAGE_DYE = new ItemCamouflageDye("camouflage_dye");
	 public static final ItemCamouflageArmor CAMOUFLAGE_HELMET = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.HEAD, "camouflage_helmet");
	 public static final ItemCamouflageArmor CAMOUFLAGE_CHEST = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST, "camouflage_chestplate");
	 public static final ItemCamouflageArmor CAMOUFLAGE_BOOTS = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.FEET, "camouflage_boots");
	 public static final ItemCamouflageArmor CAMOUFLAGE_LEGS = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.LEGS, "camouflage_leggings");
	 public static final ItemPrimitiveFood RAW_DODO = (ItemPrimitiveFood) new ItemPrimitiveFood("dodo", 4, 1.2F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F);
	 public static final ItemPrimitiveFood COOKED_DODO = (ItemPrimitiveFood) new ItemPrimitiveFood("cooked_dodo", 8, 2.4F, true);
	 public static final ItemPrimitiveEgg DODO_EGG = new ItemPrimitiveEgg("dodo_egg", EntityDodo.class);
	 public static final ItemPrimitive MIMIC_ORB = new ItemPrimitive("mimic_orb"){
		 
		@SideOnly(Side.CLIENT)
	    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	    {
	        tooltip.add("L-Click empty Chest = always Mimic mob");
	        tooltip.add("R-Click empty Chest = random Mimic effect");
	    	
	    	super.addInformation(stack, worldIn, tooltip, flagIn);
	    }};
	 public static final ItemSummonerOrb SUMMONER_ORB = new  ItemSummonerOrb("summoner_orb");
	 
	 private static void initialiseItems() {
	 
	 }

	    @Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
		public static class RegistrationHandler {

			@SubscribeEvent
			public static void registerItems(final RegistryEvent.Register<Item> event) {
				Item[] items = {
						CAMOUFLAGE_DYE,
						CAMOUFLAGE_HELMET,
						CAMOUFLAGE_CHEST,
						CAMOUFLAGE_BOOTS,
						CAMOUFLAGE_LEGS,
						RAW_DODO,
						COOKED_DODO,
						DODO_EGG,
						MIMIC_ORB
				};

				final IForgeRegistry<Item> registry = event.getRegistry();

				for (final Item item : items) {
					registry.register(item);
					ITEMS.add(item);
				}
				
				
				if(PrimitiveMobsConfigSpecial.getSummonEnable())
				{
					registry.register(SUMMONER_ORB);
					ITEMS.add(SUMMONER_ORB);
				}
				
				initialiseItems();
			}
		}

	}