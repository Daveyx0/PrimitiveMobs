package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.entity.IAttackAnimationMob;
import net.daveyx0.primitivemobs.entity.ai.EntityAISenseEntityNearestPlayer;
import net.daveyx0.primitivemobs.entity.ai.EntityAITrollagerAttacks;
import net.daveyx0.primitivemobs.entity.item.EntityThrownBlock;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColor;
import net.daveyx0.primitivemobs.message.MessagePrimitiveParticle;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.daveyx0.primitivemobs.util.NBTUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTrollager extends EntityMob implements IAttackAnimationMob {

	 private static final DataParameter<Integer> ANIMATION_STATE = EntityDataManager.<Integer>createKey(EntityTrollager.class, DataSerializers.VARINT);
	 private static final DataParameter<BlockPos> CURRENT_THROWN_BLOCK = EntityDataManager.<BlockPos>createKey(EntityTrollager.class, DataSerializers.BLOCK_POS);
	 private static final DataParameter<Boolean> IS_STONE = EntityDataManager.<Boolean>createKey(EntityLostMiner.class, DataSerializers.BOOLEAN);

	 private int previousState = 0;
	 private boolean resetAnimation;
	 private float animVar = 0;
	    
	public EntityTrollager(World worldIn) {
		super(worldIn);
		this.setSize(2.25f, 3f);
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
        this.targetTasks.addTask(++attackPrio, new EntityAISenseEntityNearestPlayer(this));
    }
	

	public void onUpdate()
	{
		super.onUpdate();
	
		animationHandling();

		if(!(this.getAnimationState() == 1 || this.getAnimationState() == 2) && this.ticksExisted % 5 == 0)
		{
			setThrowingBlockFromFloor();
		}
		
		if(isStone())
		{	
			this.despawnEntity();
			this.getNavigator().setPath(null, 0);
			this.setAttackTarget(null);
			this.setNoAI(true);
		}
		else
		{
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
   

	
	public void playParticles(float x, float y, float z)
	{
		
        for(int i = 0; i < 10; i++)
        {
        	this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, x + (rand.nextFloat() - rand.nextFloat()), y, z + (rand.nextFloat() - rand.nextFloat()), 1.0D, 0.0D, 0.0D);
        }
		
        for(int j = 0; j < 50; j++)
        {
            this.getEntityWorld().spawnParticle(EnumParticleTypes.BLOCK_CRACK, x + (rand.nextFloat() - rand.nextFloat()), y, z + (rand.nextFloat() - rand.nextFloat()), (rand.nextFloat() - rand.nextFloat()), 1.0D, (rand.nextFloat() - rand.nextFloat()), Block.getIdFromBlock(this.world.getBlockState(this.getThrownBlock()).getBlock()));
        }
	}
	
	public void animationHandling()
	{
		if(!isStone())
		{
			if(this.getPreviousState() != this.getAnimationState())
			{
				this.setPreviousState(this.getAnimationState());
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
				
				if(state != null && !state.getBlock().equals(Blocks.AIR) && state.getBlock().isFullBlock(state))
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
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
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
    public void performAttack(EntityLivingBase target, int id)
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
			PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(this.getUniqueID().toString(), (float)explosionX, (float)explosionY, (float)explosionZ));
			break;
		}
		case 2:
		{
			if(this.getAttackTarget() != null)
			{
				double d0 = this.getAttackReachSqr(this.getAttackTarget());
				double d1 = this.getDistanceSq(getAttackTarget().posX, getAttackTarget().getEntityBoundingBox().minY, getAttackTarget().posZ);
				boolean flag = this.getEntitySenses().canSee(this.getAttackTarget());
				
	        	if (d1 <= d0 && flag)
	        	{
	        		this.attackEntityAsMob(this.getAttackTarget());
	        	}
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
    
	public int getPreviousState()
	{
		return this.previousState;
	}
	
	public void setPreviousState(int state)
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
    	
    	if(this.posY > 40D)
    	{
    		flag = rand.nextInt(5) == 0;
    	}
    	
    	if(PrimitiveMobsConfigSpecial.getTrollUnderground())
    	{
            flag = this.posY < 40D;
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
    
}
