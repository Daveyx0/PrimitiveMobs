package net.daveyx0.primitivemobs.item;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.multimob.util.NBTUtil;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
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
	
	public static ItemStack onGroveSpriteDrop(World worldIn, EntityGroveSprite sprite, ItemStack stack)
	{
    	if(sprite != null)
    	{
    		NBTTagCompound itemtagcompound = new NBTTagCompound();
    		
    		if(sprite.getLog() != null)
    		{
    			NBTUtil.setBlockStateToNBT(sprite.getLog(), "LogState", itemtagcompound);
    			
    			int[] logTop = sprite.getColor(worldIn, sprite.getLog(), null, null);
    			Color logTopColor = new Color(logTop[0], logTop[1], logTop[2]);
    			
            	NBTTagCompound nbttagcompound1 = itemtagcompound.getCompoundTag("display");

                if (!itemtagcompound.hasKey("display", 10))
                {
                	itemtagcompound.setTag("display", nbttagcompound1);
                }
                
                nbttagcompound1.setInteger("color", logTopColor.getRGB());
    			
    			
        		stack.setTagCompound(itemtagcompound);
    		}
    	}
    	return stack;
		
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
