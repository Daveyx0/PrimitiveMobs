package net.daveyx0.primitivemobs.core;

import java.util.HashSet;
import java.util.Set;

import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.item.ItemCamouflageDye;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsItems {

	 public static final Set<Item> ITEMS = new HashSet<>();
	
	 public static final ItemCamouflageDye CAMOUFLAGE_DYE = new ItemCamouflageDye("camouflage_dye");;
	 public static final ItemCamouflageArmor CAMOUFLAGE_HELMET = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.HEAD, "camouflage_helmet");;
	 public static final ItemCamouflageArmor CAMOUFLAGE_CHEST = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST, "camouflage_chestplate");;
	 public static final ItemCamouflageArmor CAMOUFLAGE_BOOTS = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.FEET, "camouflage_boots");;
	 public static final ItemCamouflageArmor CAMOUFLAGE_LEGS = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.LEGS, "camouflage_leggings");;
	 
	 
	 private static void initialiseItems() {
	 
	 }

	    @Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
		public static class RegistrationHandler {

			@SubscribeEvent
			public static void registerItems(final RegistryEvent.Register<Item> event) {
				final Item[] items = {
						CAMOUFLAGE_DYE,
						CAMOUFLAGE_HELMET,
						CAMOUFLAGE_CHEST,
						CAMOUFLAGE_BOOTS,
						CAMOUFLAGE_LEGS,
				};

				final IForgeRegistry<Item> registry = event.getRegistry();

				for (final Item item : items) {
					registry.register(item);
					ITEMS.add(item);
				}
				
				initialiseItems();
			}
		}

	}