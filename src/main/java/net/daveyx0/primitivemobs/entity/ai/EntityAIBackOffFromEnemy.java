package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.primitivemobs.entity.item.EntityPrimitiveTNTPrimed;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class EntityAIBackOffFromEnemy extends EntityAIBase {

	EntityCreature creature;
	EntityLivingBase target;
	double maxDistance;
	boolean defensiveAttack;
	
	
	public EntityAIBackOffFromEnemy(EntityCreature entitycreature , double maxDistance, boolean defensiveAttack) 
	{
		this.creature = entitycreature;
		this.maxDistance = maxDistance;
		this.defensiveAttack = defensiveAttack;
	}

	/**
	* Returns whether the EntityAIBase should begin execution.
	*/
	public boolean shouldExecute()
	{
        target = this.creature.getAttackTarget();

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
        	if(this.creature.getDistanceToEntity(target) <= this.maxDistance && this.creature.canEntityBeSeen(target))
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
    }
    
    /**
     * Updates the task
     */
    public void updateTask()
    {
    	if(target != null)
    	{
            this.creature.motionX = this.target.posX - this.creature.posX;
            this.creature.motionZ = this.target.posZ - this.creature.posZ;
            double d = -0.69999999999999996D / (this.creature.motionX * this.creature.motionX + this.creature.motionZ * this.creature.motionZ + 0.0625D);
            this.creature.motionX *= d;
            this.creature.motionZ *= d;
            
            if(defensiveAttack && !this.creature.getEntityWorld().isRemote && this.creature.getHealth() < (this.creature.getMaxHealth() /2)&& this.creature.getRNG().nextInt(20) == 0)
            {
            	int i = (int)(this.creature.posX - 0.5F);
                int j = (int)this.creature.posY;
                int k = (int)(this.creature.posZ - 0.5F);
                BlockPos pos = new BlockPos(i, j ,k);
                Block block = this.creature.getEntityWorld().getBlockState(pos).getBlock();
                
                if(block.isReplaceable(this.creature.getEntityWorld(), pos) || block == Blocks.AIR)
                {
                    this.creature.getEntityWorld().setBlockState(pos, Blocks.FIRE.getDefaultState());
                }
            }
    	}
    }

}
