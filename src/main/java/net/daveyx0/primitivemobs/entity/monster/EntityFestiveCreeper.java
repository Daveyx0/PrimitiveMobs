package net.daveyx0.primitivemobs.entity.monster;


import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.entity.ai.EntityAIBackOffFromEnemy;
import net.daveyx0.primitivemobs.entity.item.EntityPrimitiveTNTPrimed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFestiveCreeper extends EntityPrimitiveCreeper{
	
	public EntityFestiveCreeper(World worldIn) {
		super(worldIn);
		isImmuneToFire = true;
	}

    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityFestiveCreeper.EntityAIThrowTNT(this));
        this.tasks.addTask(3, new EntityAIBackOffFromEnemy(this, 7.5D, true));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    public class EntityAIThrowTNT extends EntityAIBase
    {
    	EntityFestiveCreeper creeper;
    	EntityLivingBase target;
    	int power;
    	int attackCooldown;
    	
    	public EntityAIThrowTNT(EntityFestiveCreeper entityFestiveCreeper) {
    		creeper = entityFestiveCreeper;
    		power = 1;
    		attackCooldown = 0;
		}

		/**
		* Returns whether the EntityAIBase should begin execution.
		*/
		public boolean shouldExecute()
		{
	        target = this.creeper.getAttackTarget();

	        if (target == null)
	        {
	            return false;
	        }
	        else if (!target.isEntityAlive())
	        {
	            return false;
	        }
	        else
	        {
	        	if(this.creeper.getDistanceToEntity(target) > 2.0D && this.creeper.canEntityBeSeen(target))
	        	{
	        		return true;
	        	}
	        	
	        	return false;
	        }
		}
		
		/**
	    * Returns whether an in-progress EntityAIBase should continue executing
		*/
		public boolean continueExecuting()
	    {
			return shouldExecute();
	    }

	    /**
	     * Resets the task
	     */
	    public void resetTask()
	    {
	    	target = null;
	    	attackCooldown = 0;
	    }
	    
	    /**
	     * Updates the task
	     */
	    public void updateTask()
	    {
	    	if(this.creeper.getPowered()){power = 3;};
	    	
	    	if(target != null && --attackCooldown <= 0)
	    	{
	    		if(!getEntityWorld().isRemote)
	    		{
	    			EntityPrimitiveTNTPrimed tnt = new EntityPrimitiveTNTPrimed(this.creeper.getEntityWorld(), creeper.posX, creeper.posY, creeper.posZ, this.creeper, power, 30);
	    			tnt.setLocationAndAngles(this.creeper.posX, this.creeper.posY, this.creeper.posZ, this.creeper.rotationYaw, 0.0F);
	    			tnt.motionX = (this.target.posX - tnt.posX) / 18D;
	    			tnt.motionY = (this.target.posY - tnt.posY) / 18D + 0.5D;
	    			tnt.motionZ = (this.target.posZ - creeper.posZ) / 18D;
	    			this.creeper.getEntityWorld().spawnEntity(tnt);
	    		}
	    		this.creeper.playSound(SoundEvents.ENTITY_TNT_PRIMED, this.creeper.getSoundVolume(), this.creeper.getSoundPitch());
	    		attackCooldown = 60;
	    	}
	    }
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_FESTIVECREEPER;
    }

}
