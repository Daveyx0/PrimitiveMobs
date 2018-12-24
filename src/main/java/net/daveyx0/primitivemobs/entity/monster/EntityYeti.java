package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityYeti  extends EntityMob
{
	
    Item[] foodItems = new Item[]{Items.PORKCHOP, Items.BEEF, Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.CHICKEN, Items.COOKED_CHICKEN};
	private int eatingTimer;
	public EntityYeti(World world)
	{
		super(world);
		
		this.setSize(1.7F, 3.0F);
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, true));
        //this.setHungryAmount(2500);
       // this.setOwnerUUID("");
        //this.setAttackTimer(20);
        eatingTimer = 0;
	}
	/*
	

    protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
    {
        if (p_70785_1_!= this &&  this.attackTime <= 0 && p_70785_2_ < 3.5F && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(p_70785_1_);
            setAttackTimer(0);
        }
        
    }
    
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.80000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10D);
    }
	
    protected Entity findPlayerToAttack()
    {
    	if(getHungryAmount() > 4500 && eatingTimer <= 0)
    	{
    		EntityPlayer entityplayer = this.getEntityWorld().getClosestPlayerToEntity(this, 12.0D);
    		if(entityplayer != null && !entityplayer.getUniqueID().toString().equals(this.getOwnerUUID()))
    		{
    			return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    		}
    	}
    	else if(getHungryAmount() > 2500 && eatingTimer <= 0)
    	{
    		EntityPlayer entityplayer = this.getEntityWorld().getClosestPlayerToEntity(this, 4.0D);
    		if(entityplayer != null && !entityplayer.getUniqueID().toString().equals(this.getOwnerUUID()))
    		{
    			return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    		}
    	}

    	return null;

	}
    
    
    protected Entity findEnemyToAttack()
    {
    	List list = getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(8D, 4D, 8D));

        for (int i = 0; i < list.size(); i++)
        {
        Entity entity = (Entity)list.get(i);
        if (entity != null)
        {
            if(entity instanceof EntitySlime || entity instanceof EntityMob || (entity instanceof EntityAnimal && getHungryAmount() > 4500))
            {
            	entityToAttack = entity;	
            }  	
        }
        }
        return null;
    	
    }
	
	public void onUpdate()
	   {
		   super.onUpdate();
		   
		   if(getAttackTimer() < 20)
		   {
			   setAttackTimer(getAttackTimer() + 1);
		   }
		   
		   if(getHungryAmount() > 2500 && entityToAttack == null && eatingTimer <= 0)
		   {
			   EntityItem item = findItemsOnGround(foodItems);
			   EntityPlayer player = findPlayerWithItems(foodItems);
			   if(item != null)
			   {
		       		PathEntity pathToItem = this.worldObj.getPathEntityToEntity(this, item, 16.0F, true, false, false, true);
		       		this.setPathToEntity(pathToItem);
		       		
		       		if(getDistanceToEntity(item) <= 2.5D)
		       		{
		       			if(!worldObj.isRemote)
		       			{
		       				if(--item.getEntityItem().stackSize == 0)
		       				{
		       					item.setDead();
		       				}
		       			}
		       			EntityPlayer player1 = worldObj.getClosestPlayerToEntity(this, 12D);
		       			if(player1 != null)
		       			{
		       				setOwnerUUID(player1.getUniqueID().toString());
		       			}
		       			this.setCurrentItemOrArmor(0, item.getEntityItem());
		       			eatingTimer = 50;
		       		}
			   }
			   else if(player != null)
			   {
				   if(getDistanceToEntity(player) >= 5D)
				   {
					   PathEntity pathToPlayer = this.worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
					   this.setPathToEntity(pathToPlayer);
				   }
				   else
				   {
					   this.setPathToEntity(null);
				   }
			   }	   

		   }
		   else
		   {
			   followAndDefendPal();
		   }
		   
		   if(entityToAttack == null)
		   {
			   findEnemyToAttack();
		   }
		   
		   if(getHungryAmount() < 5000)
		   {
			   setHungryAmount(getHungryAmount() + 1);
		   }
		   else
		   {
			   setHungryAmount(5000);
		   }
		   
		   if(getHungryAmount() > 4500)
		   {
			   worldObj.spawnParticle("smoke", this.posX + (rand.nextFloat() - rand.nextFloat()), this.posY + 3.5D + (rand.nextFloat() - rand.nextFloat()) , this.posZ + (rand.nextFloat() - rand.nextFloat()), 0.0D, 0.0D, 0.0D);
		   }
		   
		   if(eatingTimer > 0 && getHeldItem() != null)
		   {
			   entityToAttack = null;
			   eatingTimer--;
			   String s = "iconcrack_" + Item.getIdFromItem(getHeldItem().getItem());
			   if (getHeldItem().getHasSubtypes())
               {
                   s = s + "_" + getHeldItem().getItemDamage();
               }
			   if(eatingTimer % 2 == 0)
			   {
				   this.worldObj.spawnParticle(s, this.posX, this.posY + 3.5D, this.posZ - 1.4D, (this.rand.nextFloat() - this.rand.nextFloat()), 0.1D, (this.rand.nextFloat() - this.rand.nextFloat()));
				   this.playSound("random.eat", 0.5F + 0.5F * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			   }
			   if(eatingTimer == 1)
			   {
				   setHungryAmount(0);
				   this.setCurrentItemOrArmor(0, null);
				   eatingTimer = 0;
				   
		            for (int i = 0; i < 8; i++)
		            {
		                worldObj.spawnParticle("heart", this.posX + (rand.nextFloat() - rand.nextFloat()), this.posY + 3.5D + (rand.nextFloat() - rand.nextFloat()) , this.posZ + (rand.nextFloat() - rand.nextFloat()), 0.0D, 0.0D, 0.0D);
		            }
		            setHealth(100);
			   }
		   }
		   
		   
		   //Diversity.Divlogger.info("" + getHungryAmount());

	   }

	
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.cow.step", 0.15F, 1.0F);
    }
	
	public EntityPlayer findPlayerWithItems(Item[] items)
	{
	    EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 12D);
	    
	    if(player != null && player.getHeldItem() != null)
	    {
	    	ItemStack useItem = player.getHeldItem();
        	
        	for (int j = 0; j< items.length; j++)
        	{
        		if(useItem.getItem() == items[j])
        		{
        			return player;
        		}
        	}
	    }
	    return null;
	}
	
	public void followAndDefendPal()
	{
	    EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 12D);
	    
	    if(player != null && player.getUniqueID().toString().equals(this.getOwnerUUID()))
	    {
	    	if(player.getLastAttacker() != null)
	    	{
	    		entityToAttack = player.getLastAttacker();
	    	}
	    	else if (player.getAITarget() != null)
	    	{
	    		entityToAttack = player.getAITarget();
	    	}
	    	else if(getDistanceToEntity(player) >= 3.5D)
			   {
				   PathEntity pathToPlayer = this.worldObj.getPathEntityToEntity(this, player, 12.0F, true, false, false, true);
				   this.setPathToEntity(pathToPlayer);
			   }
			   else
			   {
				   this.setPathToEntity(null);
			   }
	    }
	}
	
	public EntityItem findItemsOnGround(Item[] items)
	{
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(8D, 4D, 8D));

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
	            	ItemStack stack = item.getEntityItem();
	            	
	            	for (int j = 0; j< items.length; j++)
	            	{
	            	if(stack != null && stack.getItem() == items[j])
	            	{
	            		return item;
	            	}
	            	}
	            }  	
	        }
	        }
	        return null;
	}
	
	public boolean interact(EntityPlayer entityplayer)
	{
	    ItemStack itemstack = entityplayer.inventory.getCurrentItem();

	    if (itemstack != null && getHungryAmount() > 2500)
	    {
	    
	    for(int i = 0; i < foodItems.length; i++)
	    {
	    if (itemstack.getItem() == foodItems[i])
	    {
	        if (--itemstack.stackSize == 0)
	        {
	            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
	        }
	        
	        this.setCurrentItemOrArmor(0, itemstack);
	        setOwnerUUID(entityplayer.getUniqueID().toString());
	        eatingTimer = 50;
	        entityToAttack = null;
	        return true;
	    }
	    }
	    }
	    
	 return super.interact(entityplayer);
	}
	
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
    	if (super.attackEntityFrom(p_70097_1_, p_70097_2_))
        {
    		this.motionX *= 0.1;
    		this.motionZ *= 0.1;
    		return true;
        }
    	return false;
    }

 	
	protected void entityInit()
	{
	    super.entityInit();
	    this.dataWatcher.addObject(18,  new Float(0));
	    this.dataWatcher.addObject(19, "");
	    this.dataWatcher.addObject(20,  new Float(0));
	}
	
	public float getHungryAmount()
	{
	    return this.dataWatcher.getWatchableObjectFloat(18);
	}

	public void setHungryAmount(float p_152115_1_)
	{
	    this.dataWatcher.updateObject(18, p_152115_1_);
	}
	
	public float getAttackTimer()
	{
	    return this.dataWatcher.getWatchableObjectFloat(20);
	}

	public void setAttackTimer(float p_152115_1_)
	{
	    this.dataWatcher.updateObject(20, p_152115_1_);
	}
	

	@Override
    protected boolean canDespawn()
    {
        return false;
    }
	

    protected float getSoundPitch()
    {
    	 return  (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F + 0.1F;
    }
    

    protected String getHurtSound()
    {
    		return "mob.villager.hit";
    }
    

    protected String getLivingSound()
    {
        
        	return "mob.villager.idle";
    }

    protected String getDeathSound()
    {
        return "mob.villager.death";
    }
    

public String getOwnerUUID()
{
    return this.dataWatcher.getWatchableObjectString(19);
}

public void setOwnerUUID(String p_152115_1_)
{
    this.dataWatcher.updateObject(19, p_152115_1_);
}

public EntityLivingBase getOwner()
{
    try
    {
        UUID uuid = UUID.fromString(this.getOwnerUUID());
        return uuid == null ? null : this.worldObj.func_152378_a(uuid);
    }
    catch (IllegalArgumentException illegalargumentexception)
    {
        return null;
    }
}

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setFloat("Hungry", getHungryAmount());
        nbttagcompound.setFloat("Timer", getAttackTimer());
        if (this.getOwnerUUID() == null)
        {
        	nbttagcompound.setString("OwnerUUID", "");
        }
        else
        {
        	nbttagcompound.setString("OwnerUUID", this.getOwnerUUID());
        }
        
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.setHungryAmount(nbttagcompound.getFloat("Hungry"));
        this.setAttackTimer(nbttagcompound.getFloat("Timer"));
        
        String var2 = nbttagcompound.getString("Owner");
        String s = "";

        if (nbttagcompound.hasKey("OwnerUUID", 8))
        {
            s = nbttagcompound.getString("OwnerUUID");
        }
        else
        {
            String s1 = nbttagcompound.getString("Owner");
            s = PreYggdrasilConverter.func_152719_a(s1);
        }

        if (s.length() > 0)
        {
            this.setOwnerUUID(s);
        }
    }*/

}