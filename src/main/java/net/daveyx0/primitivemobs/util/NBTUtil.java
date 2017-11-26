package net.daveyx0.primitivemobs.util;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class NBTUtil {

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
						PrimitiveMobs.getSimpleNetworkWrapper().sendToServer(new MessagePrimitiveColor(item.getColor(stack), slot,  entity.getUniqueID().toString()));
					}
				}
			}
	    }
	 
	 
	 //Sets blockpos to NBT
	 public static void setBlockPosToNBT(BlockPos pos, String key, NBTTagCompound compound)
	 {
		 compound.setInteger(key + "X", pos.getX());
		 compound.setInteger(key + "Y", pos.getY());
		 compound.setInteger(key + "Z", pos.getZ());
	 }
	 
	 //Gets blockpos from NBT, use with setBlockPosToNBT
	 public static BlockPos getBlockPosFromNBT(String key, NBTTagCompound compound)
	 {
		 int X = compound.getInteger(key + "X");
		 int Y = compound.getInteger(key + "Y");
		 int Z = compound.getInteger(key + "Z");
		 return new BlockPos(X,Y,Z);
	 }
	 
	 //Sets blockstate to NBT
	 public static void setBlockStateToNBT(IBlockState state, String key, NBTTagCompound compound)
	 {
		 int stateID = Block.getStateId(state);
		 compound.setInteger(key + "ID", stateID);
	 }
	 
	 //Receive a blockstate from NBT, use with setBlockStateToNBT
	 public static IBlockState getBlockStateFromNBT(String key, NBTTagCompound compound)
	 {
		 return Block.getStateById(compound.getInteger(key + "ID"));
	 }
}
