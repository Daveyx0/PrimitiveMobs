package net.daveyx0.primitivemobs.entity.ai;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIGrabItemFromFloor extends EntityAIBase {

	 /** The entity using this AI that is tempted by the player. */
    private final EntityCreature temptedEntity;
    private final double speed;
    /** X position of player tempting this mob */
    private double targetX;
    /** Y position of player tempting this mob */
    private double targetY;
    /** Z position of player tempting this mob */
    private double targetZ;
    /** Tempting player's pitch */
    private double pitch;
    /** Tempting player's yaw */
    private double yaw;
    /** The player that is tempting the entity that is using this AI. */
    private EntityItem temptingItem;
    private boolean isRunning;
    private final Set<ItemStack> temptItem;
    private boolean canGetScared;
    private int stealDelay = 0;

    public EntityAIGrabItemFromFloor(EntityCreature temptedEntityIn, double speedIn, Set<ItemStack> temptItemIn, boolean canGetScared)
    {
        this.temptedEntity = temptedEntityIn;
        this.speed = speedIn;
        this.temptItem = temptItemIn;
        this.canGetScared = canGetScared;
        
        if (!(temptedEntityIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if(temptedEntity.getLastAttackedEntity() != null && canGetScared && stealDelay <= 0)
    	{
    		this.resetTask();
    		return false;
    	}

    	if(!this.temptedEntity.getHeldItemMainhand().isEmpty())
    	{
    		return false;
    	}
    	
        java.util.List<Entity> list = this.temptedEntity.getEntityWorld().getEntitiesWithinAABBExcludingEntity(temptedEntity, temptedEntity.getEntityBoundingBox().expand(6D, 4D, 6D));
        
        if (this.stealDelay > 0)
        {
            --this.stealDelay;
            if(stealDelay == 0)
            {
            	temptedEntity.setLastAttackedEntity(null);
            }
            return false;
        }
        else if(list != null && list.size() > 0)
        {
        	for (int i = 0; i < list.size(); i++)
 	        {
 	        Entity entity = (Entity)list.get(i);
 	        if (entity != null)
 	        {
 	            if (!(entity instanceof EntityItem))
 	            {
 	                continue;
 	            }
 	            else if(entity instanceof EntityItem)
 	            {
 	            	EntityItem item = (EntityItem)entity;
 	            	ItemStack stack = item.getItem();
 	            	if(!stack.isEmpty())
 	            	{
 	            		if(this.isTempting(stack))
 						{
 	            			this.temptingItem = item;
 							return true;
 						}
 	            	}
 	            }  	
 	        }
 	        }
 	     }

        return false;
    }

    protected boolean isTempting(ItemStack stack)
    {
    	if(!stack.isEmpty())
    	{
    		for(ItemStack item : temptItem)
    		{
    			if(item != null && item.getItem() == stack.getItem() && item.getMetadata() == stack.getMetadata())
    			{
    				return true;
    			}
    		}
    	}
    	return false;
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
        this.targetX = this.temptingItem.posX;
        this.targetY = this.temptingItem.posY;
        this.targetZ = this.temptingItem.posZ;
        this.isRunning = true;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.temptingItem = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.isRunning = false;
        if(canGetScared)
        {
        	this.stealDelay = 50;
        }
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingItem, (float)(this.temptedEntity.getHorizontalFaceSpeed() + 20), (float)this.temptedEntity.getVerticalFaceSpeed());

        if (this.temptedEntity.getDistanceSqToEntity(this.temptingItem) < 1.0D)
        {
            this.temptedEntity.getNavigator().clearPathEntity();
            
            ItemStack loot = temptingItem.getItem().copy();
            temptingItem.setDead();
			this.temptedEntity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, loot);
        }
        else
        {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingItem, this.speed);
        }
    }

    /**
     * @see #isRunning
     */
    public boolean isRunning()
    {
        return this.isRunning;
    }
}
