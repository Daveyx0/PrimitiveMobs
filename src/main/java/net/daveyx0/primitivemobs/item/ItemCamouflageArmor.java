package net.daveyx0.primitivemobs.item;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.multimob.message.MMMessageRegistry;
import net.daveyx0.multimob.util.ColorUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobs;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCamouflageArmor extends ItemArmor{

	private float R;
	private float G;
	private float B;
	private float NewR;
	private float NewG;
	private float NewB;
	private int colorSpeed = 4;
	private IBlockState currentState;
	private int currentMultiplier;
	
	public ItemCamouflageArmor(final ArmorMaterial material, final EntityEquipmentSlot equipmentSlot, final String armourName) {
		
		super(material, -1, equipmentSlot);
		ItemPrimitive.setItemName(this, armourName);
		setCreativeTab(PrimitiveMobs.tabPrimitiveMobs);
		setSkinRGB(new int[]{255,255,255});
	}

    public void onArmorTick(World world, EntityPlayer player, ItemStack armor) 
    {
		if(player != null && player.getEntityWorld().isRemote && !this.getCannotChange(armor))
		{
			this.changeColor(player);
			setColor(armor, new Color((int)getSkinRGB()[0],(int)getSkinRGB()[1],(int)getSkinRGB()[2]).hashCode());
			if(currentState != null)
			{
				setColorBlockState(armor, currentState);
			}
			MMMessageRegistry.getNetwork().sendToServer(new MessagePrimitiveColor(getColor(armor), this.armorType,  player.getUniqueID().toString()));
		}
		
		//PrimitiveMobs.PMlogger.info("" + getColor(armor));
		
    	if(R != NewR || G != NewG || B != NewB)
		{
			for(int i = 0; i < colorSpeed; i++)
			{
				if(R > NewR)
				{
					R--;
				}
				else if (R < NewR)
				{
					R++;
				}
			
				if(G > NewG)
				{
					G--;
				}
				else if (G < NewG)
				{
					G++;
				}
			
				if(B > NewB)
				{
					B--;
				}
				else if (B < NewB)
				{
					B++;
				}
			}
		}
    	
    	player.inventoryContainer.detectAndSendChanges();
    	super.onArmorTick(world, player, armor);
    }
    
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	if(!stack.isEmpty() && stack.getItem() instanceof ItemCamouflageArmor)
    	{
    	ItemCamouflageArmor armor = (ItemCamouflageArmor)stack.getItem();
    		
    	if(armor.getCannotChange(stack))
    	{
        	tooltip.add("Camouflage: disabled");
    	}
    	else
    	{
        	tooltip.add("Camouflage: enabled");
    	}

    	Color color = new Color(armor.getColor(stack));
    	//tooltip.add("\u00a74Red: " + color.getRed());
    	//tooltip.add("\u00a72Green: " + color.getGreen());
    	//tooltip.add("\u00a71Blue: " + color.getBlue());
    	IBlockState state = armor.getColorBlockState(stack);
    	if(state != null)
    	{
    		String name = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)).getDisplayName();
    		if(name.equals(Blocks.AIR.getLocalizedName()))
    		{
    			tooltip.add("Block: " + state.getBlock().getLocalizedName());
    		}
    		else
    		{
        	 	tooltip.add("Block: " + new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)).getDisplayName());
    		}
    	}
    	}
    	
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    
    public boolean hasOverlay(ItemStack stack)
    {
        return true;
    }
    
    public float[] getSkinRGB()
	{
		return new float[]{R,G,B};
	}
	
	public void setSkinRGB(int[] RGB)
	{
		R = (float)RGB[0];
		G = (float)RGB[1];
		B = (float)RGB[2];
	}
	
	public float[] getNewSkinRGB()
	{
		return new float[]{NewR,NewG,NewB};
	}
	
	public void setNewSkinRGB(int[] RGB)
	{
		NewR = (float)RGB[0];
		NewG = (float)RGB[1];
		NewB = (float)RGB[2];
	}
    
    public void changeColor(Entity entity)
	{
		int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.getEntityBoundingBox().minY);
        int k = MathHelper.floor(entity.posZ);
        
		if(entity.getEntityWorld().getBlockState(new BlockPos(i, j, k)).getBlock() == Blocks.AIR)
		{
			j = MathHelper.floor(entity.getEntityBoundingBox().minY - 0.1);
		}
		
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState state = entity.getEntityWorld().getBlockState(pos);
		
		int colorMultiplier = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, entity.getEntityWorld(), pos, 0);
		
		if(state.getBlock() != Blocks.AIR && (currentState != state || currentMultiplier != colorMultiplier))
		{
			currentState = state;
			currentMultiplier = colorMultiplier;
			
			int[] newColor = ColorUtil.getBlockStateColor(state, pos, entity.getEntityWorld(), true);
			if(newColor != null)
			{
				if(ColorUtil.isColorInvalid(newColor))
				{
					newColor = new int[]{255,255,255,255};
				}
				setNewSkinRGB(newColor);
			}
		}
	}
    
    /**
     * Return whether the specified armor ItemStack has a color.
     */
    public boolean hasColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    public int getColor(ItemStack stack)
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

    /**
     * Remove the color from the specified armor ItemStack.
     */
    public void removeColor(ItemStack stack)
    {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1.hasKey("color"))
                {
                    nbttagcompound1.removeTag("color");
                }
            }
        
    }
    
    @Nullable
    public IBlockState getColorBlockState(ItemStack p_82814_1_)
    {
            NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();

            if (nbttagcompound == null)
            {
                return Blocks.AIR.getDefaultState();
            }
            else
            {
                return NBTUtil.readBlockState(nbttagcompound);
            }
    }
    
    public void setColorBlockState(ItemStack stack, IBlockState state)
    {
    	 NBTTagCompound nbttagcompound = stack.getTagCompound();

             if (nbttagcompound == null)
             {
                 nbttagcompound = new NBTTagCompound();
                 stack.setTagCompound(nbttagcompound);
             }

             NBTUtil.writeBlockState(nbttagcompound, state);
    }

    /**
     * Sets the color of the specified armor ItemStack
     */
    public void setColor(ItemStack stack, int color)
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
    
    public boolean getCannotChange(ItemStack p_82814_1_)
    {
            NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();

            if (nbttagcompound == null)
            {
                return false;
            }
            else
            {
                return nbttagcompound == null ? false : (nbttagcompound.hasKey("change") ? nbttagcompound.getBoolean("change") : false);
            }
    }
    
    public void setCannotChange(ItemStack stack, boolean set)
    {
    	 NBTTagCompound nbttagcompound = stack.getTagCompound();

             if (nbttagcompound == null)
             {
                 nbttagcompound = new NBTTagCompound();
                 stack.setTagCompound(nbttagcompound);
             }

             nbttagcompound.setBoolean("change", set);
    }
    
	//Sets color nbt value of the camouflage armor and sends it over to the server so other player can see it, used for entities that wear the armor
	 public static void setCamouflageArmorNBT(EntityLivingBase entity, EntityEquipmentSlot slot)
	    {
	    	ItemStack stack = entity.getItemStackFromSlot(slot);
	    	if(stack.getItem() instanceof ItemCamouflageArmor)
			{
				ItemCamouflageArmor item = (ItemCamouflageArmor)stack.getItem();
				
				if(!item.getCannotChange(stack) && entity.getEntityWorld().isRemote)
				{
					int color = ColorUtil.getBlockColor(entity);
					if(color < -1)
					{
						item.setColor(stack, color);
						item.setColorBlockState(stack, ColorUtil.getBlockState(entity));
						MMMessageRegistry.getNetwork().sendToServer(new MessagePrimitiveColor(item.getColor(stack), slot,  entity.getUniqueID().toString()));
					}
				}
			}
	    }
}
