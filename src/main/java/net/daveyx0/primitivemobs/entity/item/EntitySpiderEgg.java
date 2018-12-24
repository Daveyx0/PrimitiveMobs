package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveCreeper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpiderEgg extends EntityPrimitiveThrowable{

	public EntitySpiderEgg(World worldIn) {

		super(worldIn);
	}
	
	public EntitySpiderEgg(World worldIn, EntityPlayer playerIn, Class<? extends EntityLiving> entry, int spawnChance) {

		super(worldIn, playerIn, entry, spawnChance);
	}
	
	/**
     * Called when this EntityThrowable hits a block or entity.
     */
	@Override
    protected void onImpact(RayTraceResult result)
    {

    	if(!this.world.isRemote)
    	{
    		EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + 0.5D, this.posZ, new ItemStack(PrimitiveMobsItems.SPIDER_EGGSHELL));
            entityitem.setDefaultPickupDelay();
            this.world.spawnEntity(entityitem);
    	}
    	
		super.onImpact(result);
		
    }
	

	public ItemStack getItemFromEntity() {

		return new ItemStack(PrimitiveMobsItems.SPIDER_EGG);
	}

}
