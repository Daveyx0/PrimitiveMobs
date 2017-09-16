package net.daveyx0.primitivemobs.client;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabPrimitiveMobs extends CreativeTabs { 

	public TabPrimitiveMobs(int index, String label) {
        super(index, label);
    }

	public String getTranslatedTabLabel()
	{
		return "PrimitiveMobs"; 
	}
	
	@Override
	public ItemStack getTabIconItem() {

		return new ItemStack(PrimitiveMobsItems.CAMOUFLAGE_DYE);
	}
}