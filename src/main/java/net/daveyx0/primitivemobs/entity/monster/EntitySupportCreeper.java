package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;

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
    
    public class EntityAIBuffMob extends EntityAIBase
    {

    	EntitySupportCreeper creeper;
    	EntityMob mobIdol;
    	int strength;
    	
		public EntityAIBuffMob(EntitySupportCreeper entitySupportCreeper) {

			creeper = entitySupportCreeper;
			mobIdol = null;
			strength = 1;
			this.setMutexBits(3);
		}
		/**
		* Returns whether the EntityAIBase should begin execution.
		*/
		public boolean shouldExecute()
		{
	        List<Entity> list = this.creeper.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this.creeper, this.creeper.getEntityBoundingBox().expand(10.0D, 4.0D, 10.0D));
	        EntityMob mob = null;
	        double d0 = Double.MAX_VALUE;

	        for (Entity entity : list)
	        {
	        	if(entity != null && entity instanceof EntityMob && !(entity instanceof EntitySupportCreeper))
	        	{
	        		EntityMob mob1 = (EntityMob)entity;
	        		
	        		if(mob1.getActivePotionEffects().isEmpty() && this.creeper.canEntityBeSeen(mob1))
	        		{
	        			double d1 = this.creeper.getDistanceSqToEntity(mob1);

	        			if (d1 <= d0)
	        			{
	        				d0 = d1;
                    		mob = mob1;
	        			}
	        		}
	        	}
	        }
	        
	        if(mob == null)
	        {
	        	 return false;
	        }
	        else
	        {
	        	 mobIdol = mob;
	        	 return true;
	        }
		}

		/**
	    * Returns whether an in-progress EntityAIBase should continue executing
		*/
		public boolean continueExecuting()
	    {
			return this.mobIdol.isEntityAlive() && this.creeper.getDistanceSqToEntity(this.mobIdol) <= 25D;
	    }
		
	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	    }

	    /**
	     * Resets the task
	     */
	    public void resetTask()
	    {
	        this.mobIdol = null;
	    }
	    
	    /**
	     * Updates the task
	     */
	    public void updateTask()
	    {
	    	if(this.creeper.getPowered()) {strength = 2;}
	    	else {strength = 1;}
	    	
	        if (this.creeper.getDistanceToEntity(this.mobIdol) > 2D)
	        {
	            this.creeper.getNavigator().tryMoveToEntityLiving(this.mobIdol, 1F);
	        }
	        
            if(this.mobIdol instanceof EntityCreeper)
            {
            	EntityCreeper entitycreeper = (EntityCreeper)mobIdol;
            	
            	if(!entitycreeper.getPowered() && !getEntityWorld().isRemote)	
            	{
                    this.creeper.onStruckByLightning(null);
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
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_SUPPORTCREEPER;
    }


}
