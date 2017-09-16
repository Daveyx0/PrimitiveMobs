package net.daveyx0.primitivemobs.recipe;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CamouflageArmorRecipe implements IRecipe
{
    protected ItemStack output = null;
    protected ArrayList<Object> input = new ArrayList<Object>();
    protected ItemStack inputStack = null;
    protected String nbtKey = "";

    public CamouflageArmorRecipe(ItemStack recipe)
    {
            if (recipe instanceof ItemStack)
            {
            	input.add((ItemStack)recipe);
            }
            else
            {
                String ret = "Invalid shapeless ore recipe ";
                throw new RuntimeException(ret);
            }
    }


    @Override
    public ItemStack getRecipeOutput(){ return output; }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1)
    {   	
    	for(int i = 0 ; i < var1.getSizeInventory(); i++ )
    	{
    	ItemStack stack = var1.getStackInSlot(i).copy();
    	
    	if(stack != null && stack.getItem() instanceof ItemCamouflageArmor)
    	{
    		ItemCamouflageArmor armor = (ItemCamouflageArmor)stack.getItem();
    		int color = armor.getColor(stack);
    		armor.setColor(stack, color);
    		armor.setCannotChange(stack, !armor.getCannotChange(stack));
    		
    		return stack;
    	}
    	}
    	
    	return new ItemStack(Items.APPLE);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(InventoryCrafting var1, World world)
    {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for (int x = 0; x < var1.getSizeInventory(); x++)
        {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if (next instanceof ItemStack)
                    {
                        match = OreDictionary.itemMatches((ItemStack)next, slot, false);
                    }
                    else if (next instanceof List)
                    {
                        Iterator<ItemStack> itr = ((List<ItemStack>)next).iterator();
                        while (itr.hasNext() && !match)
                        {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     * @return The recipes input vales.
     */
    public ArrayList<Object> getInput()
    {
        return this.input;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) //getRecipeLeftovers
    {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }


	protected boolean itemMatches(ItemStack required, ItemStack present) {
		return required.getItem() instanceof ItemCamouflageArmor;
	}


	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		
		return null;
	}


	@Override
	public ResourceLocation getRegistryName() {
		
		return null;
	}


	@Override
	public Class<IRecipe> getRegistryType() {
		
		return null;
	}


	@Override
	public boolean canFit(int width, int height) {

		return false;
	}
}