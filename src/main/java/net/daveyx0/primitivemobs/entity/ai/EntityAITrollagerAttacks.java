package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.primitivemobs.entity.IAttackAnimationMob;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class EntityAITrollagerAttacks<T extends EntityMob & IAttackAnimationMob> extends EntityAIBase
{
    private final T entity;
    private final double moveSpeedAmp;
    private final float maxAttackDistance;
    private final float meleeAttackDistance;
    private int attackTime = -1;
    private int animTime = -1;
    private int seeTime;

    public EntityAITrollagerAttacks(T entity, double moveSpeed, float meleeAttackDistance, float maxAttackDistance)
    {
        this.entity = entity;
        this.moveSpeedAmp = moveSpeed;
        this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
        this.meleeAttackDistance = meleeAttackDistance * meleeAttackDistance;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.entity.getAttackTarget() != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute() || !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.seeTime = 100;
        EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
        if (entitylivingbase != null)
        {
        	double distanceToEnemy = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
        	boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase);
            boolean flag1 = distanceToEnemy <= (double)this.meleeAttackDistance && flag;
            boolean flag3 = checkCanThrow();
            boolean flag4 = distanceToEnemy <= (double)this.maxAttackDistance && (!flag || !flag3);
            
            if(flag1)
            {
            	((IAttackAnimationMob)this.entity).setAnimationState(0);
                this.attackTime = 20;
            }
            else if(flag4)
        	{
        		((IAttackAnimationMob)this.entity).setAnimationState(3);
                this.attackTime = 40;
        	}
        	else
        	{
        		((IAttackAnimationMob)this.entity).setAnimationState(1);
                this.attackTime = 40;
        	}
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
        ((IAttackAnimationMob)this.entity).setAnimationState(0);
        this.seeTime = 100;
        this.attackTime = -1;
        this.animTime = -1;
    }
    
    public boolean checkCanThrow()
    {
    	EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
    	EntityTrollager troll = (EntityTrollager)this.entity;
    	
    	if(entitylivingbase != null && troll != null)
    	{
    		return troll.canBlockAreaSeeEntity(entitylivingbase);
    	}
    	else
    	{
    		return false;
    	}
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

        if (entitylivingbase != null)
        {
            double distanceToEnemy = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            boolean flag = this.entity.getEntitySenses().canSee(entitylivingbase) && this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
            boolean flag1 = distanceToEnemy <= (double)this.meleeAttackDistance && flag;
            boolean flag3 = distanceToEnemy > (double)this.meleeAttackDistance && distanceToEnemy <= (double)this.maxAttackDistance && flag;
            boolean flag4 = checkCanThrow();  
            boolean flag5 = distanceToEnemy <= (double)this.maxAttackDistance && (!flag || !flag4);

            if (distanceToEnemy >= (double)this.maxAttackDistance)
            {
                this.entity.getNavigator().clearPathEntity();
            }
            else
            {
            	seeTime = 100;
                this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
            }
            
            if (--this.attackTime <= 0)
            {
            	if(((IAttackAnimationMob)this.entity).getAnimationState() == 3)
            	{
                    ((IAttackAnimationMob)this.entity).performAttack(entitylivingbase, 1);
                    ((IAttackAnimationMob)this.entity).setAnimationState(4);
                    this.animTime = 20;
            	}
            	else if(((IAttackAnimationMob)this.entity).getAnimationState() == 0)
            	{
                    ((IAttackAnimationMob)this.entity).performAttack(entitylivingbase, 2);
                    ((IAttackAnimationMob)this.entity).setAnimationState(6);
                    this.animTime = 7;
            	}
            	else
            	{
                   ((IAttackAnimationMob)this.entity).performAttack(entitylivingbase, 0);
                   ((IAttackAnimationMob)this.entity).setAnimationState(2);
                   this.animTime = 30;
            	}
               this.attackTime = Integer.MAX_VALUE;
            }
            
            if(animTime >= 0 && --this.animTime == 0)
            {
            	if(((IAttackAnimationMob)this.entity).getAnimationState() == 4)
            	{
            		((IAttackAnimationMob)this.entity).setAnimationState(5);
            		this.animTime = 10;
            	}
            	else if(((IAttackAnimationMob)this.entity).getAnimationState() == 6)
            	{
            		((IAttackAnimationMob)this.entity).setAnimationState(7);
            		this.animTime = 7;
            	}
            	else
            	{
                	if(flag1)
                	{
                    	((IAttackAnimationMob)this.entity).setAnimationState(0);
                    	this.attackTime = 20;
                	}
                	else if(flag5)
                	{
                		((IAttackAnimationMob)this.entity).setAnimationState(3);
                    	this.attackTime = 40;
                	}
                	else
                	{
                    	((IAttackAnimationMob)this.entity).setAnimationState(1);
                    	this.attackTime = 40;
                	}
                	this.animTime = -1;
            	}
            }

           this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);

        }
    }
}