package net.daveyx0.primitivemobs.entity.ai;

import java.util.Set;

import net.daveyx0.multimob.entity.ai.EntityAITemptItemStack;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;

public class EntityAIGroveSpriteTempt extends EntityAITemptItemStack 
{

	public EntityAIGroveSpriteTempt(EntityCreature temptedEntityIn, double speedIn, boolean scaredByPlayerMovementIn,
			Set<ItemStack> temptItemIn) {
		super(temptedEntityIn, speedIn, scaredByPlayerMovementIn, temptItemIn);

	}
	
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	super.startExecuting();
        if(this.temptedEntity instanceof EntityGroveSprite)
        {
        	((EntityGroveSprite)temptedEntity).setIsBegging(true);
        }
    }
    
    /**
     * Resets the task
     */
    public void resetTask()
    {
    	super.resetTask();
        if(this.temptedEntity instanceof EntityGroveSprite)
        {
        	((EntityGroveSprite)temptedEntity).setIsBegging(false);
        }
    }

}
