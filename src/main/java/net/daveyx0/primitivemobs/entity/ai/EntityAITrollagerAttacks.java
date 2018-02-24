package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
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
    private final EntityTrollager entity;
    private final double moveSpeedAmp;
    private final float maxAttackDistance;
    private final float meleeAttackDistance;
    private int attackTime = -1;
    private int animTime = -1;
    private int attackDelay = -1;
    private int seeTime;
    private boolean isAttacking;

    public EntityAITrollagerAttacks(EntityTrollager entity, double moveSpeed, float meleeAttackDistance, float maxAttackDistance)
    {
        this.entity = entity;
        this.moveSpeedAmp = moveSpeed;
        this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
        this.meleeAttackDistance = meleeAttackDistance * meleeAttackDistance;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.entity.getAttackTarget() != null && !this.entity.isStone();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.seeTime = 0;
        this.attackTime = -1;
        this.animTime = -1;
        this.isAttacking = false;
        determineAttackAndPerform();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
        ((IAttackAnimationMob)this.entity).setAnimationState(0);
        this.seeTime = 0;
        this.attackTime = -1;
        this.animTime = -1;
        this.isAttacking = false;
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
    	determineAttackAndPerform();
    }
    
    public void determineAttackAndPerform()
    {
        EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

        if (entitylivingbase != null)
        {
            double distanceToEnemy = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            
            boolean canSeeEnemy = this.entity.getEntitySenses().canSee(entitylivingbase);
            boolean canNavigateToEnemy = this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
            boolean isWithinMeleeRange = distanceToEnemy <= (double)this.meleeAttackDistance * (double)this.meleeAttackDistance;
            boolean isWithinAttackRange = distanceToEnemy <= (double)this.maxAttackDistance * (double)this.maxAttackDistance;
            boolean canThrowBlock = checkCanThrow();  

            boolean canPerformMeleeAttack = isWithinMeleeRange && canSeeEnemy;
            boolean canPerformSmashAttack = PrimitiveMobsConfigSpecial.getTrollDestruction() && ((isWithinAttackRange && (!canSeeEnemy || !canThrowBlock)) || this.entity.world.rand.nextInt(4) == 0);
            boolean canPerformThrowAttack = canSeeEnemy && canThrowBlock;
            
            if (--this.attackTime <= 0 && this.isAttacking)
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
                   this.animTime = 20;
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
                	this.animTime = -1;
                	this.isAttacking = false;
            	}
            }
            
            if(!this.isAttacking)
            {
            	if(canPerformMeleeAttack)
        		{
            		((IAttackAnimationMob)this.entity).setAnimationState(0);
            		this.attackTime = 10;
            		this.isAttacking = true;
        		}
        		else if(canPerformSmashAttack)
        		{
        			((IAttackAnimationMob)this.entity).setAnimationState(3);
            		this.attackTime = 40;
            		this.isAttacking = true;
        		}
        		else if(canPerformThrowAttack)
        		{
            		((IAttackAnimationMob)this.entity).setAnimationState(1);
            		this.attackTime = 30;
            		this.isAttacking = true;
        		}
        		else
        		{
        			((IAttackAnimationMob)this.entity).setAnimationState(0);
        			this.attackTime = Integer.MAX_VALUE;
        		}
            }
 
            this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
            this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        }
    }
}