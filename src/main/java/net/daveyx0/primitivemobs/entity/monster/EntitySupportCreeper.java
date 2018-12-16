package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntitySupportCreeper extends EntityPrimitiveCreeper {

	public EntitySupportCreeper(World worldIn) {
		super(worldIn);

	}
	
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntitySupportCreeper.EntityAIBuffMob(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }
    
    public void onUpdate()
    {
    	if(this.getHealth() < this.getMaxHealth()/2)
    	{
			while(this.tasks.taskEntries.stream()
			.filter(taskEntry -> taskEntry.action instanceof EntityAIAvoidEntity).findFirst().isPresent())
			{
				this.tasks.taskEntries.stream().filter(taskEntry -> taskEntry.action instanceof EntityAIAvoidEntity)
				.findFirst().ifPresent(taskEntry -> this.tasks.removeTask(taskEntry.action));
			}
			this.tasks.addTask(2, new EntityAICreeperSwell(this));
    		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    	}
    	super.onUpdate();
    }
    
    public class EntityAIBuffMob extends EntityAIBase
    {

    	EntitySupportCreeper creeper;
    	EntityLivingBase mobIdol;
    	int strength;
    	
		public EntityAIBuffMob(EntitySupportCreeper entitySupportCreeper) {

			creeper = entitySupportCreeper;
			mobIdol = null;
			strength = 1;
		}
		/**
		* Returns whether the EntityAIBase should begin execution.
		*/
		public boolean shouldExecute()
		{
			ITameableEntity tameable = EntityUtil.getCapability(this.creeper, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
			if(tameable != null && tameable.isTamed() && tameable.getFollowState() == 0)
			{
				return false;
			}
	        return true;
		}

		/**
	    * Returns whether an in-progress EntityAIBase should continue executing
		*/
		public boolean continueExecuting()
	    {
			ITameableEntity tameable = EntityUtil.getCapability(this.creeper, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
			if(tameable != null && tameable.isTamed() && tameable.getFollowState() == 0)
			{
				return false;
			}
			return this.mobIdol.isEntityAlive() && this.creeper.getDistanceSq(this.mobIdol) <= 25D * 25D;
	    }
		
	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	    	this.mobIdol = this.findMobToSupport();
	    }

	    /**
	     * Resets the task
	     */
	    public void resetTask()
	    {
	        this.mobIdol = null;
	    }
	    
	    public EntityLivingBase findMobToSupport()
	    {
			ITameableEntity tameable = EntityUtil.getCapability(this.creeper, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
			if(tameable != null && tameable.isTamed() && tameable.getFollowState() != 0)
			{
				if(tameable.getOwner(this.creeper) != null && tameable.getOwner(this.creeper).getDistanceSq(this.creeper) < 30)
				{
					return tameable.getOwner(this.creeper);
				}
			}
			else if(tameable == null || !tameable.isTamed())
			{
	        List<Entity> list = this.creeper.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this.creeper, this.creeper.getEntityBoundingBox().expand(10.0D, 4.0D, 10.0D));
	        EntityMob mob = null;
	        double d0 = Double.MAX_VALUE;

	        for (Entity entity : list)
	        {
	        	if(entity != null && entity instanceof EntityMob && !(entity instanceof EntitySupportCreeper))
	        	{
	        		EntityMob mob1 = (EntityMob)entity;
	        		
	        		if(mob1.getActivePotionEffects().isEmpty())
	        		{
	        			double d1 = this.creeper.getDistanceSq(mob1);

	        			if (d1 <= d0)
	        			{
	        				d0 = d1;
                    		mob = mob1;
	        			}
	        		}
	        	}
	        }
	        
	        if(mob != null)
	        {
	        	 return mob;
	        }
			}
	        return null;
	    }
	    
	    /**
	     * Updates the task
	     */
	    public void updateTask()
	    {
	    	if(mobIdol == null)
	    	{
	    		this.mobIdol = this.findMobToSupport();
	    	}
	    	else
	    	{
	    	if(this.creeper.getPowered()) {strength = 2;}
	    	else {strength = 1;}
	    	
	        if (this.creeper.getDistance(this.mobIdol) > 2D)
	        {
	            this.creeper.getNavigator().tryMoveToEntityLiving(this.mobIdol, 1F);
	        }
	        
            if(this.mobIdol instanceof EntityCreeper)
            {
            	EntityCreeper entitycreeper = (EntityCreeper)mobIdol;
            	
            	if(!entitycreeper.getPowered() && !getEntityWorld().isRemote)	
            	{
            		entitycreeper.onStruckByLightning(null);
            	}
            	
            	if(entitycreeper.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null)
            	{
            		entitycreeper.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60, strength));
            	}
            	
            	if(this.creeper.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null)
            	{
            		this.creeper.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60, strength));
            	}
            }
            else
            {
                if(this.mobIdol instanceof EntityTrollager)
                {
                	EntityTrollager entitytrollager = (EntityTrollager)mobIdol;
                	entitytrollager.isBeingSupported = true;
                }
            	if(mobIdol.getActivePotionEffect(MobEffects.STRENGTH) == null)
            	{
            		mobIdol.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60, strength));
            	}
            	if(mobIdol.getActivePotionEffect(MobEffects.SPEED) == null)
            	{
            		mobIdol.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, strength));
            	}
            	if(mobIdol.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null)
            	{
            		mobIdol.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60, strength));
            	}
            }
            
            if(this.creeper.getActivePotionEffect(MobEffects.SPEED) == null)
            {
            	creeper.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, strength));
            }
	    	}
	        
	    }
    	
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_SUPPORTCREEPER;
    }


}
