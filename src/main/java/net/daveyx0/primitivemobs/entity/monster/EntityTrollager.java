package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import net.daveyx0.multimob.entity.ai.EntityAISenseEntityNearestPlayer;
import net.daveyx0.multimob.message.MMMessageRegistry;
import net.daveyx0.multimob.message.MessageMMParticle;
import net.daveyx0.multimob.util.NBTUtil;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.entity.IAnimatedMob;
import net.daveyx0.primitivemobs.entity.ai.EntityAITrollagerAttacks;
import net.daveyx0.primitivemobs.entity.item.EntityThrownBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityTrollager extends EntityMob implements IAnimatedMob {

	 private static final DataParameter<Integer> ANIMATION_STATE = EntityDataManager.<Integer>createKey(EntityTrollager.class, DataSerializers.VARINT);
	 private static final DataParameter<BlockPos> CURRENT_THROWN_BLOCK = EntityDataManager.<BlockPos>createKey(EntityTrollager.class, DataSerializers.BLOCK_POS);
	 private static final DataParameter<Boolean> IS_STONE = EntityDataManager.<Boolean>createKey(EntityTrollager.class, DataSerializers.BOOLEAN);

	 private int previousState = 0;
	 private float animVar = 0;
	 private float previousYawStone = -2;
	 private float previousPitchStone = -2;
	 private float previousYawHeadStone = -2;
	 public boolean isBeingSupported;
	    
	public EntityTrollager(World worldIn) {
		super(worldIn);
		this.setSize(2.25f, 3f);
		isBeingSupported = false;
	}
	

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
	
	protected void initEntityAI()
    {
		int prio = 0;
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAITrollagerAttacks(this, 1.25D, 2.5F, 20.0F));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(++prio, new EntityAILookIdle(this));
        int attackPrio = 1;
        this.targetTasks.addTask(++attackPrio, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(++attackPrio, new EntityAISenseEntityNearestPlayer(this, 40));
    }
	
	/**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        if (this.world.rand.nextInt(20) == 0)
        {
        	EntityGoblin goblin = new EntityGoblin(this.world);
        	goblin.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        	goblin.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(goblin);
            goblin.startRiding(this);
        }

        return livingdata;
    }
    

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return (double)this.height * 0.9D;
    }

	

	public void onUpdate()
	{
		super.onUpdate();
		
		if(this.isBeingRidden() && !world.isRemote)
		{
			if(this.getPassengers().get(0) != null && getPassengers().get(0) instanceof EntityLivingBase)
			{
				EntityLiving ridingEntity = (EntityLiving)getPassengers().get(0);
				if(ridingEntity.getAttackTarget() != null)
				{
					this.setAttackTarget(ridingEntity.getAttackTarget());
				}
			}
			
			if(this.collidedHorizontally)
			{
				jump();
			}
		}
		
		if(this.getAttackTarget() != null && this.getAttackTarget().isDead)
		{
			this.setAttackTarget(null);
		}
	
		animationHandling();

		if(!(this.getAnimationState() == 1 || this.getAnimationState() == 2) && this.ticksExisted % 5 == 0)
		{
			setThrowingBlockFromFloor();
		}
		
		if(isBeingSupported) {this.setStone(false);}
		
		if(isStone())
		{	
			this.despawnEntity();
			this.getNavigator().setPath(null, 0);
			this.setAttackTarget(null);
			this.setNoAI(true);
			if(previousYawStone == -2)
			{
				previousYawStone = rotationYaw;
				previousYawHeadStone = rotationYawHead;
				previousPitchStone = rotationPitch;
			}
			
			this.setRotation(previousYawStone, previousPitchStone);
			this.setRotationYawHead(previousYawHeadStone);
			if(this.isBeingRidden())
			{
				if(this.getPassengers().get(0) != null)
				{
					this.getPassengers().get(0).dismountRidingEntity();
				}
			}
			
		}
		else
		{
			this.previousPitchStone = -2;
			this.previousYawHeadStone = -2;
			this.previousYawStone = -2;
			this.setNoAI(false);
		}
		
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
     * Called when a player attacks an entity. If this returns true the attack will not happen.
     */
    @Override
    public boolean hitByEntity(Entity p_85031_1_)
    {
    	if(this.isStone() && p_85031_1_ != null && p_85031_1_ instanceof EntityLivingBase)
    	{
    		ItemStack stack = ((EntityLivingBase) p_85031_1_).getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
    		
    		if(stack != null && stack.canHarvestBlock(Blocks.STONE.getDefaultState()))
    		{
    			return false;
    		}
    		
    		return true;
    	}
    	
        return false;
    }
    
    public EntityItem dropItemStack(ItemStack itemIn, float offsetY)
    {
        return this.entityDropItem(itemIn, offsetY);
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        if (!this.isStone() && !getEntityWorld().isRemote)
        {
            int i = 1 + rand.nextInt(2);

            if (lootingModifier > 0)
            {
                i += this.rand.nextInt(lootingModifier + 1);
            }

            for (int j = 0; j < i; ++j)
            {
            	ItemStack newStack = this.getRandomLoot();
            	
            	if(newStack != null)
            	{
            		this.dropItemStack(newStack, 1);
            	}
            }
        }
        else if(this.isStone() && !getEntityWorld().isRemote)
        {
        	this.dropItemStack(new ItemStack(Item.getItemFromBlock(Blocks.STONE), 32), 1);
        }
    }
    
    public ItemStack getRandomLoot()
    {
    	int chance = rand.nextInt(100);
    	
    	if(chance > 50)
    	{
    		return new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN));
    	}
    	else if(chance > 10)
    	{
    		return new ItemStack(Items.EMERALD);
    	}
    	else
    	{
    		return new ItemStack(Items.GOLDEN_APPLE);
    	}
    }
	
	public void onLivingUpdate()
    {
		boolean flag = false;
        if (this.world.isDaytime() && !this.world.isRemote)
        {
            float f = this.getBrightness();

            if (this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
            {
                ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty())
                {
                	this.setStone(false);
                }
                else
                {
                	this.setStone(true);
                }
            }
            else
            {
            	this.setStone(false);
            }
        }
        else if(!this.world.isDaytime() && !this.world.isRemote)
        {     	
        	this.setStone(false);
        }
        
        super.onLivingUpdate();
    }
	
	public void setStone(boolean b) {

		 this.dataManager.set(IS_STONE, b);
	}
	
   public boolean isStone()
   {
		return ((Boolean)this.dataManager.get(IS_STONE)).booleanValue();
   }
	
	public void animationHandling()
	{
		if(!isStone())
		{
			if(this.getPreviousAnimationState() != this.getAnimationState())
			{
				this.setPreviousAnimationState(this.getAnimationState());
				animVar = 0;
			}
		
			if(animVar < 1f)
			{
				animVar += 0.01f;
			}
		
			else{animVar = 1f;}
		}
	}
	
	public void setThrowingBlockFromFloor()
	{
		for(int i = 1; i < 64; i++)
		{
			BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY - i, this.posZ);
			
			if(blockPos != null)
			{
				if(this.getThrownBlock().equals(blockPos))
				{
					break;
				}
				IBlockState state = this.getEntityWorld().getBlockState(blockPos);
				
				if(state != null && state.getBlock().isFullBlock(state))
				{
					this.setThrownBlock(blockPos);
					break;
				}
				else
				{
					continue;
				}
			}
		}
	}
	

    
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ANIMATION_STATE, 0);
        this.dataManager.register(CURRENT_THROWN_BLOCK, new BlockPos(0,0,0));
        this.dataManager.register(IS_STONE, false);
    }

	@Override
	public void setAnimationState(int state) {

		 this.dataManager.set(ANIMATION_STATE, state);
	}
	
	@Override
    public int getAnimationState()
    {
        return ((Integer)this.dataManager.get(ANIMATION_STATE)).intValue();
    }
	
    protected SoundEvent getAmbientSound()
    {
    	if(this.isStone())return null;
    	
        return PrimitiveMobsSoundEvents.ENTITY_TROLLAGER_IDLE;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
    	if(this.isStone())return SoundEvents.BLOCK_STONE_BREAK;
    	
        return PrimitiveMobsSoundEvents.ENTITY_TROLLAGER_HIT;
    }

    protected SoundEvent getDeathSound()
    {
        return PrimitiveMobsSoundEvents.ENTITY_TROLLAGER_DEATH;
    }
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 1.0F;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_IRONGOLEM_STEP;
    }
	
	
    public boolean canBlockAreaSeeEntity(Entity entityIn)
    {
    	boolean flag = true;
    	for(int i = 0; i < 4; i++)
    	{
    		flag = this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + (double)this.getEyeHeight() + i, this.posZ), new Vec3d(entityIn.posX, entityIn.posY + (double)entityIn.getEyeHeight(), entityIn.posZ), false, true, false) == null;
    	}
        return flag;
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
	@Override
    public void performAction(EntityLivingBase target, int id)
    {
		switch (id)
		{
		case 0:
		{
	        EntityThrownBlock thownBlock = new EntityThrownBlock(this.getEntityWorld(), this.posX, this.posY, this.posZ, this, this.getThrownBlock());
	        thownBlock.setLocationAndAngles(this.posX, this.posY + 4D, this.posZ, this.rotationYaw, 0.0F);
	        thownBlock.motionX = (target.posX - thownBlock.posX) / 18D;
	        thownBlock.motionY = (target.posY - thownBlock.posY) / 18D + 0.5D;
	        thownBlock.motionZ = (target.posZ - this.posZ) / 18D;
	        this.getEntityWorld().spawnEntity(thownBlock);
	        this.playSound(PrimitiveMobsSoundEvents.ENTITY_TROLLAGER_ATTACK, this.getSoundVolume(), ((this.getRNG().nextFloat() - this.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
	        break;
		}
		case 1:
		{
			double distanceX = (this.getLookHelper().getLookPosX() - this.posX);
			double distanceZ = (this.getLookHelper().getLookPosZ() - this.posZ);
		    double length = Math.sqrt((distanceX*distanceX) +(distanceZ*distanceZ) );
		    if (length != 0) {
		    	distanceX = distanceX/length;
		    	distanceZ = distanceZ/length;
		      }

		    double addedHeight = 0D;
		    if(target.posY > this.posY)
		    {
		    	addedHeight = 0.5D;
		    }
		    else if(target.posY < this.posY)
		    {
		    	addedHeight = -0.5D;
		    }
			double explosionX = this.posX + (distanceX * 2D);
			double explosionZ = this.posZ + (distanceZ * 2D);
			double explosionY = this.posY + addedHeight;
			boolean flag = true;
			if(!this.getEntityWorld().getGameRules().getBoolean("mobGriefing") || !PrimitiveMobsConfigSpecial.getTrollDestruction())
			{
				flag = false;
			}
			this.newExplosion(this, explosionX ,this.posY + this.getEyeHeight(), explosionZ, 3F, false, flag);
			
			//MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), 50, (float)explosionX, (float)explosionY, (float)explosionZ, 0D,0D,0D, blockId));
			MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), 10, (float)explosionX, (float)explosionY, (float)explosionZ, 1D,0D,0D, 0));
			this.playSound(PrimitiveMobsSoundEvents.ENTITY_TROLLAGER_ATTACK, this.getSoundVolume(), ((this.getRNG().nextFloat() - this.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
			break;
		}
		case 2:
		{
			if(this.getAttackTarget() != null && !this.isStone())
			{
				double d0 = this.getAttackReachSqr(this.getAttackTarget());
				double d1 = this.getDistanceSq(getAttackTarget().posX, getAttackTarget().getEntityBoundingBox().minY, getAttackTarget().posZ);
				boolean flag = this.getEntitySenses().canSee(this.getAttackTarget());
				
	        	if (d1 <= d0 && flag)
	        	{
	        		this.attackEntityAsMob(this.getAttackTarget());
	        	}
	        	this.playSound(PrimitiveMobsSoundEvents.ENTITY_TROLLAGER_ATTACK, this.getSoundVolume(), ((this.getRNG().nextFloat() - this.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}
		}
		default: break;
		}
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return (double)(this.width * 2F * this.width * 2F + attackTarget.width);
    }
	
    public Explosion newExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking)
    {
        Explosion explosion = new Explosion(this.world, entityIn, x, y, z, strength, isFlaming, isSmoking);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.world, explosion)) return explosion;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }
	
    public void setThrownBlock(BlockPos pos)
    {
        this.getDataManager().set(CURRENT_THROWN_BLOCK, pos);
    }
    
    public BlockPos getThrownBlock()
    {
    	BlockPos pos = (BlockPos)this.getDataManager().get(CURRENT_THROWN_BLOCK);
    	return pos != null ? pos : new BlockPos(0,0,0);
    }
	
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        NBTUtil.setBlockPosToNBT(this.getThrownBlock(), "ThrownBlock", compound);
        compound.setBoolean("Stone", this.isStone());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        this.setThrownBlock(NBTUtil.getBlockPosFromNBT("ThrownBlock", compound));
        this.setStone(compound.getBoolean("Stone"));
    }
    
	public int getPreviousAnimationState()
	{
		return this.previousState;
	}
	
	public void setPreviousAnimationState(int state)
	{
		this.previousState = state;
	}
	
	public float getAnimVar()
	{
		return animVar;
	}
	
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
    	boolean flag = true;
    	
    	if(this.posY > 40D && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
    	{
    		flag = rand.nextInt(5) == 0;
    	}
    	
        return flag && super.getCanSpawnHere();
    }
    
    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
    	if(this.isStone())
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
    	if(this.isStone())
    	{
    		return this.getEntityBoundingBox();
    	}
    	else
    	{
    		return super.getCollisionBoundingBox();
    	}
    }
    
    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    public boolean isNotColliding()
    {
        return !this.world.containsAnyLiquid(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox().expand(1.5D, 1.5D, 1.5D)).isEmpty() && this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

	@Override
	public void setAnimVar(float var) {
		
		animVar = var;
	}
    
}
