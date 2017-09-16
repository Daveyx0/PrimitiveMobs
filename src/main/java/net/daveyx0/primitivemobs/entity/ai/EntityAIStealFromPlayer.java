package net.daveyx0.primitivemobs.entity.ai;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityAIStealFromPlayer extends EntityAIBase {

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
    private EntityPlayer temptingPlayer;
    private boolean isRunning;
    private final Set<ItemStack> temptItem;
    private boolean canGetScared;
    private int stealDelay = 0;

    public EntityAIStealFromPlayer(EntityCreature temptedEntityIn, double speedIn, Set<ItemStack> temptItemIn, boolean canGetScared)
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

    	if(this.temptedEntity.getHeldItemMainhand() != null)
    	{
    		return false;
    	}
    	
        this.temptingPlayer = this.temptedEntity.getEntityWorld().getClosestPlayerToEntity(this.temptedEntity, 10.0D);
        
        if (this.stealDelay > 0)
        {
            --this.stealDelay;
            if(stealDelay == 0)
            {
            	temptedEntity.setLastAttackedEntity(null);
            }
            return false;
        }
        else if(temptingPlayer != null)
        {
        	for(int i = 0 ; i < this.temptingPlayer.inventory.getSizeInventory() ; i++)
			{
				ItemStack item = this.temptingPlayer.inventory.getStackInSlot(i);
			
				if(item != null)
				{
					if(this.isTempting(item))
					{
						return true;
					}
				}
			}
    	}	

        return false;
    }

    protected boolean isTempting(@Nullable ItemStack stack)
    {
    	if(stack != null)
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
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
        this.isRunning = true;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.isRunning = false;
        if(canGetScared)
        {
        	this.stealDelay = 100;
        }
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, (float)(this.temptedEntity.getHorizontalFaceSpeed() + 20), (float)this.temptedEntity.getVerticalFaceSpeed());

        if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 3.25D)
        {
            this.temptedEntity.getNavigator().clearPathEntity();
            
            for(int i = 0 ; i < this.temptingPlayer.inventory.getSizeInventory() ; i++)
			{
				ItemStack item = this.temptingPlayer.inventory.getStackInSlot(i);
			
				if(item != null)
				{
		    		for(ItemStack itemstack : temptItem)
		    		{
		    			if(itemstack != null && itemstack.getItem() == item.getItem() && itemstack.getMetadata() == item.getMetadata())
		    			{
		    				if(!temptingPlayer.capabilities.isCreativeMode)
		    				{
		    					item.shrink(1);;
		    				}
							
							World world = this.temptingPlayer.getEntityWorld();
							
							this.temptingPlayer.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
		
							ItemStack loot = item.copy();
							this.temptedEntity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, loot);
							return;	
		    			}
		    		}
				}
			}
			
        }
        else
        {
            this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
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
