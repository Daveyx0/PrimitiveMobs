package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.entity.item.EntityPrimitiveThrowable;
import net.daveyx0.primitivemobs.entity.item.EntitySpiderEgg;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPrimitiveEgg extends ItemPrimitive
	{
		Class<? extends EntityLiving> entry;
		int spawnChance;

	    public ItemPrimitiveEgg(String itemName, Class<? extends EntityLiving> entry, int spawnChance) {
		super(itemName);
		this.maxStackSize = 16;
		this.entry = entry;
		this.spawnChance = spawnChance;
	    }

		/**
	     * Called when the equipped item is right clicked.
	     */
	    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	    {
	        ItemStack itemstack = playerIn.getHeldItem(handIn);

	        if (!playerIn.capabilities.isCreativeMode)
	        {
		        itemstack.shrink(1);
	        }

	        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	        if (!worldIn.isRemote)
	        {
	        	EntityPrimitiveThrowable entityegg;
	        	if(entry.equals(EntityBabySpider.class))
	        	{
	        		entityegg = new EntitySpiderEgg(worldIn, playerIn, entry, this.spawnChance);
	        	}
	        	else
	        	{
	        		entityegg = new EntityPrimitiveThrowable(worldIn, playerIn, entry, this.spawnChance);
	        	}
	            entityegg.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
	            worldIn.spawnEntity(entityegg);
	        }

	        playerIn.addStat(StatList.getObjectUseStats(this));
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	    }
}

