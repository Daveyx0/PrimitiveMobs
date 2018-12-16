package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.core.PrimitiveMobs;
import net.minecraft.item.Item;

public class ItemPrimitive extends Item {

	public ItemPrimitive(String itemName) {
		setItemName(this, itemName);
		setCreativeTab(PrimitiveMobs.tabPrimitiveMobs);
	}

	public static void setItemName(Item item, String itemName) {
		item.setRegistryName(itemName);
		item.setUnlocalizedName(item.getRegistryName().toString());
	}
}
