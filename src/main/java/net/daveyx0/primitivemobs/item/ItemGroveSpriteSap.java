package net.daveyx0.primitivemobs.item;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.multimob.message.MMMessageRegistry;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.multimob.util.NBTUtil;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColor;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColorSap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGroveSpriteSap extends ItemPrimitive {

	public ItemGroveSpriteSap(String itemName) {
		super(itemName);
	}
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	NBTTagCompound nbttagcompound = stack.getTagCompound();
    	
    	if(nbttagcompound != null)
    	{
    		if(nbttagcompound.hasKey("LogStateID"))
    		{
    			IBlockState logBlockState = NBTUtil.getBlockStateFromNBT("LogState", nbttagcompound);
    			
    			tooltip.add("Type: " + EntityUtil.getBlockStateName(logBlockState));
    		}
    	}
    	else
    	{
    		tooltip.add("Type: Unknown");
    	}
    }
	
	public static void setSapLogState(EntityGroveSprite sprite, ItemStack stack)
	{
    	if(sprite != null)
    	{
    		NBTTagCompound itemtagcompound = new NBTTagCompound();
    		
    		if(stack.hasTagCompound())
    		{
    			itemtagcompound = stack.getTagCompound();
    		}
    		
    		if(sprite.getLog() != null)
    		{
    			NBTUtil.setBlockStateToNBT(sprite.getLog(), "LogState", itemtagcompound);
    			
        		stack.setTagCompound(itemtagcompound);
    		}
    	}
		
	}
	
    /**
     * Sets the color of the specified armor ItemStack
     */
    public static void setColor(ItemStack stack, int color)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display", 10))
        {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", color);
    
    }
	
	public static ItemStack getLogFromSap(ItemStack sap, int logAmount)
	{
		NBTTagCompound nbttagcompound = sap.getTagCompound();
    	
    	if(nbttagcompound != null)
    	{
    		if(nbttagcompound.hasKey("LogStateID"))
    		{
    			IBlockState logBlockState = NBTUtil.getBlockStateFromNBT("LogState", nbttagcompound);
    			int meta = logBlockState.getBlock().getMetaFromState(logBlockState);
    			ItemStack log = new ItemStack(logBlockState.getBlock() ,logAmount, meta);
    			return log;
    		}
    	}
		return ItemStack.EMPTY;
	}
	
    /**
     * Return the color for the specified armor ItemStack.
     */ 
    public static int getColor(ItemStack stack)
    {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
                {
                    return nbttagcompound1.getInteger("color");
                }
            }

            return 16777215;
    }

}
