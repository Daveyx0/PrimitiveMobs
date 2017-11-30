package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveTameableMob;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIFollowerHurtByTarget extends EntityAITarget
{
	EntityMotherSpider user;
	EntityLivingBase[] followers;
    EntityLivingBase attacker;
    EntityLivingBase currentFollower;
    private int timestamp;

    public EntityAIFollowerHurtByTarget(EntityMotherSpider user)
    {
        super(user, false);
        this.user = user;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.user.hasFollowers())
        {
        	//PrimitiveMobsLogger.info(user.world, "got here");
            return false;
        }
        else
        {
        	this.followers = this.user.getFollowers();
        	
            for(int i = 0; i < followers.length; i++)
            {
            	EntityLivingBase entitylivingbase = followers[i];
            	
            	if(entitylivingbase == null)
            	{
            		continue;
            	}
            	else
            	{
            		currentFollower = entitylivingbase;
            	}
            	
            	if(this.attacker == null)	
            	{
            		this.attacker = entitylivingbase.getRevengeTarget();
            	}
            	
                int j = entitylivingbase.getRevengeTimer();
                
                if(j != this.timestamp && this.isSuitableTarget(this.attacker, false) && this.user.shouldAttackEntity(this.attacker, entitylivingbase))
                {
                	return true;
                }
                else
                {
                	currentFollower = null;
                	continue;
                }
            }
            
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);

        if (currentFollower != null)
        {
            this.timestamp = currentFollower.getRevengeTimer();
        }

        super.startExecuting();
    }
}