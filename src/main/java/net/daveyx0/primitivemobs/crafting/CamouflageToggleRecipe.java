package net.daveyx0.primitivemobs.crafting;

import javax.annotation.Nonnull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CamouflageToggleRecipe extends ShapelessOreRecipe {
	
	public CamouflageToggleRecipe(ResourceLocation group, NonNullList<Ingredient> input, @Nonnull ItemStack result) {
		super(group, input, result);
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting inv) {
		ItemStack output = super.getCraftingResult(inv); 
		
		if (!output.isEmpty()) {
			
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack ingredient = inv.getStackInSlot(i);
				if(!ingredient.isEmpty())
				{
				
				if (output.getItem().equals(ingredient.getItem())) { 
					
					if(output.getItem() instanceof ItemCamouflageArmor)
					{
						ItemCamouflageArmor armor = (ItemCamouflageArmor)output.getItem();
						armor.setColor(output, armor.getColor(ingredient));
						armor.setColorBlockState(output, armor.getColorBlockState(ingredient)); 
						armor.setCannotChange(output, !armor.getCannotChange(ingredient));
						output.setItemDamage(ingredient.getItemDamage());
					}
				}
				}
			}
		}
		
		return output;
	}
	public static class Factory implements IRecipeFactory {

		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			
	        String group = JsonUtils.getString(json, "group", "");

	        NonNullList<Ingredient> ings = NonNullList.create();
	        for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients"))
	        {
	            ings.add(CraftingHelper.getIngredient(ele, context));
	        }

	        if (ings.isEmpty())
	        {
	            throw new JsonParseException("No ingredients for shapeless recipe");
	        }

	        ItemStack itemstack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

			return new CamouflageToggleRecipe(group.isEmpty() ? null : new ResourceLocation(group), ings, itemstack);
		}
	}
}
