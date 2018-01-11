package net.daveyx0.primitivemobs.item;

import net.daveyx0.primitivemobs.entity.item.EntityPrimitiveEgg;
import net.daveyx0.primitivemobs.entity.passive.EntityDodo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ItemPrimitiveEgg extends ItemPrimitive
	{
		Class<? extends EntityAnimal> entry;

	    public ItemPrimitiveEgg(String itemName, Class<? extends EntityAnimal> entry) {
		super(itemName);
		this.maxStackSize = 16;
		this.entry = entry;
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
	            EntityPrimitiveEgg entityegg = new EntityPrimitiveEgg(worldIn, playerIn, entry);
	            entityegg.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
	            worldIn.spawnEntity(entityegg);
	        }

	        playerIn.addStat(StatList.getObjectUseStats(this));
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	    }
}

