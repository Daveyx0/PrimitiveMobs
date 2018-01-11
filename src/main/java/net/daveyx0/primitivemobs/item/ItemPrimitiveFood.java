package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ItemPrimitiveFood extends ItemFood {

	public ItemPrimitiveFood(String itemName, int amount, float saturation, boolean isWolfFood) {
		
		super(amount, saturation, isWolfFood);
		setItemName(this, itemName);
		setCreativeTab(PrimitiveMobs.tabPrimitiveMobs);
	}

	public static void setItemName(Item item, String itemName) {
		item.setRegistryName(itemName);
		item.setUnlocalizedName(item.getRegistryName().toString());
	}
}
