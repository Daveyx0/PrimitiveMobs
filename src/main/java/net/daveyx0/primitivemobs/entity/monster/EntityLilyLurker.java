package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.multimob.entity.EntityMMSwimmingCreature;
import net.daveyx0.multimob.entity.ai.EntityAISwimmingUnderwater;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityLilyLurker extends EntityMMSwimmingCreature {

	int aggroTimer;
	int timeOnLand;
	
	public EntityLilyLurker(World worldIn) {
		super(worldIn);
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.WATERLILY));
    	this.setCamouflaged(true);
    	aggroTimer = 0;
    	timeOnLand = 0;
	}
	
	private static final DataParameter<Boolean> IS_CAMOUFLAGED = EntityDataManager.<Boolean>createKey(EntityLilyLurker.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TIME_REGROW = EntityDataManager.<Integer>createKey(EntityLilyLurker.class, DataSerializers.VARINT);

    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAISwimmingUnderwater(this));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        int attackPrio = 1;
        this.targetTasks.addTask(++attackPrio, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(++attackPrio, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false, false));
        this.targetTasks.addTask(++attackPrio, new EntityAINearestAttackableTarget(this, EntitySquid.class, false, false));
    }
    
    public float getEyeHeight()
    {
        return this.height * 0.25F;
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return false;
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
    }
    
    protected void entityInit()
    {      
        this.getDataManager().register(IS_CAMOUFLAGED, Boolean.valueOf(false));
        this.getDataManager().register(TIME_REGROW, Integer.valueOf(0));
        super.entityInit();
    }
    
    public boolean canBreatheUnderwater()
    {
        return true;
    }
    
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
    }
    
    public boolean canDespawn()
    {
        return true;
    }
    
    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
    	if(this.isCamouflaged())
    	{
    		return entityIn.getEntityBoundingBox();
    	}
    	else
    	{
    		return super.getCollisionBox(entityIn);
    	}
    }

    /**
     * Returns the collision bounding box for this entity
     */
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
    	if(this.isCamouflaged())
    	{
    		return this.getEntityBoundingBox();
    	}
    	else
    	{
    		return super.getCollisionBoundingBox();
    	}
    }
    
	public void onUpdate() 
	{	
		if(isCamouflaged())
		{	
	        if(!this.isInWater())
	        {
	        	this.setCamouflaged(false);
	        }
	        
			this.despawnEntity();
			this.setSize(0.5F, 0.98F);
			this.rotationYaw = 0.25F;
			this.rotationYawHead = 0.25F;
			this.setNoGravity(true);
			this.getNavigator().setPath(null, 0);
			if(!getEntityWorld().isRemote)
			{
				if(EntityUtil.distanceToSurface(this, this.getEntityWorld()) > 1.5F)
				{
					this.move(MoverType.SELF, 0, 0.05, 0);
				}
			}
			
			List<Entity> list = this.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D));
	        EntityLivingBase base = null;

	        if(this.getAttackTarget() == null || !this.getAttackTarget().isEntityAlive())
	        {
	        	for (Entity entity : list)
	        	{
	        		if(entity != null && entity instanceof EntityLivingBase)
	        		{
	        			if(entity instanceof EntityPlayer)
	        			{
	        				EntityPlayer player = (EntityPlayer)entity;
	        				if(player.isCreative())
	        				{
	        					continue;
	        				}
	        			}
	        			
	        			base = (EntityLivingBase)entity;
	        			this.setAttackTarget(base);
	        		}
	        	}
	        }
	        else
	        {
	        	this.setCamouflaged(false);
	        }
	        
	        
	        aggroTimer = 0; 
		}
		else
		{
			this.setNoGravity(false);
			this.setSize(0.5F, 0.5F);
			
	    	if(this.isInWater() && (this.getAttackTarget() == null || !this.getAttackTarget().isEntityAlive()) && ++this.aggroTimer > 250)
	    	{
	    		aggroTimer = 0;
	    		this.setCamouflaged(true);
	    	}
	    	
	    	if(!this.isInWater())
	    	{
	    		if(++timeOnLand > 100)
	    		{
	    			this.attackEntityFrom(DamageSource.DROWN, 3f);
	    			this.jump();
	    			this.motionX = (this.getRNG().nextFloat() - this.getRNG().nextFloat())/ 2f;
	    			this.motionZ = (this.getRNG().nextFloat() - this.getRNG().nextFloat())/ 2f;
	    			timeOnLand = 80;
	    		}
	    	}
	    	else
	    	{
	    		timeOnLand = 0;
	    	}
		}

		super.onUpdate();
	}
	
    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn)
    {
        if (entityIn instanceof EntityBoat)
        {
            if (entityIn.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
            {
                super.applyEntityCollision(entityIn);
            }
        }
        else if (entityIn.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY)
        {
            super.applyEntityCollision(entityIn);
        }
    }
	
    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked()
    {
        return isCamouflaged() || super.isMovementBlocked();
    }
    
    public boolean handleWaterMovement()
    {
    	if(!isCamouflaged()) return  super.handleWaterMovement();
    	else
    	{
    		return false;
    	}
    }
	
    /**
     * Called when a player attacks an entity. If this returns true the attack will not happen.
     */
    public boolean hitByEntity(Entity p_85031_1_)
    {
    	setCamouflaged(false);
        return false;
    }
    
    public EntityItem dropItemStack(ItemStack itemIn, float offsetY)
    {
        return this.entityDropItem(itemIn, offsetY);
    }	
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_LILYLURKER;
    }
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
       this.setCamouflaged(false);
       if(p_70097_1_ == DamageSource.IN_WALL && this.isInWater())
       {
    	   return false;
       }
       return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
	
	public void setCamouflaged(boolean camouflaged)
    {
        this.getDataManager().set(IS_CAMOUFLAGED, Boolean.valueOf(camouflaged));
    }
    
    public boolean isCamouflaged()
    {
        return ((Boolean)this.getDataManager().get(IS_CAMOUFLAGED)).booleanValue();
    }
    
    public void setTimeToRegrow(int time)
    {
        this.getDataManager().set(TIME_REGROW, Integer.valueOf(time));
    }
    
    public int getTimeToRegrow()
    {
        return ((Integer)this.getDataManager().get(TIME_REGROW)).intValue();
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        compound.setBoolean("Camouflaged", this.isCamouflaged());
        compound.setInteger("RegrowTime", this.getTimeToRegrow());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        
        this.setCamouflaged(compound.getBoolean("Camouflaged"));
        this.setTimeToRegrow(compound.getInteger("RegrowTime"));
    }
    
    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    @Override
    public boolean isNotColliding()
    {
        return this.getEntityWorld().checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        int i = (int)posX;
        int j = (int)posY;
        int k = (int)posZ;
        return this.posY > 45.0D && this.posY < (double)this.getEntityWorld().getSeaLevel();
    }

}
