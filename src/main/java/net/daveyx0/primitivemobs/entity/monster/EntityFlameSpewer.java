package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.entity.item.EntityFlameSpit;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFlameSpewer extends EntityMob implements IRangedAttackMob {

    private static final DataParameter<Byte> ON_FIRE = EntityDataManager.<Byte>createKey(EntityFlameSpewer.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> IN_DANGER = EntityDataManager.<Byte>createKey(EntityFlameSpewer.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> ATTACK_TIME = EntityDataManager.<Integer>createKey(EntityFlameSpewer.class, DataSerializers.VARINT);
    private static final DataParameter<Float> ATTACK_SIGNAL = EntityDataManager.<Float>createKey(EntityFlameSpewer.class, DataSerializers.FLOAT);
    
	public EntityFlameSpewer(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		this.setOnFire(false);
		this.setInDanger(false);
		this.setAttackTime(10);
		this.setAttackSignal(0);
		this.setSize(1f, 1.25f);
		this.setPathPriority(PathNodeType.LAVA, 10);
	}

	protected void initEntityAI()
    {
        //this.tasks.addTask(++prio, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityFlameSpewer.AIGoToLava(this));
		this.tasks.addTask(4, new EntityFlameSpewer.AIFlameSpewAttack(this));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
	
    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return false;
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ON_FIRE, Byte.valueOf((byte)0));
        this.dataManager.register(IN_DANGER, Byte.valueOf((byte)0));
        this.getDataManager().register(ATTACK_TIME, Integer.valueOf(0));
        this.getDataManager().register(ATTACK_SIGNAL, Float.valueOf(0));
    }
    
    protected SoundEvent getAmbientSound()
    {
        return PrimitiveMobsSoundEvents.ENTITY_FLAMESPEWER_IDLE;
    }
    
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SQUID_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }
    
    /**
     * Returns whether this Entity is invulnerable to the given DamageSource.
     */
    public boolean isEntityInvulnerable(DamageSource source)
    {
        return this.getAttackTime() < 10 || this.getAttackSignal() > 0;
    }

    
    public void setAttackTime(int time)
    {
        this.getDataManager().set(ATTACK_TIME, Integer.valueOf(time));
    }
    
    public void setAttackSignal(float signal)
    {
        this.getDataManager().set(ATTACK_SIGNAL, Float.valueOf(signal));
    }
    
    public int getAttackTime()
    {
        return ((Integer)this.getDataManager().get(ATTACK_TIME)).intValue();
    }
    
    public float getAttackSignal()
    {
        return ((Float)this.getDataManager().get(ATTACK_SIGNAL)).floatValue();
    }
    
	
	public void onUpdate()
	{
		super.onUpdate();
	    if(this.isInLava() || this.isInWater())
	    {
	    	if(!this.getEntityWorld().isAirBlock(new BlockPos(this.posX, this.posY + 0.5D, this.posZ)))
	    	{	
	    		motionY = 0.1D;
	    	}
	    	else
	    	{
	    		motionY = 0.0D;
	    	}
	    	
	    	if(this.isInWater() && this.ticksExisted % 15 == 0)
	    	{
	    		this.attackEntityFrom(DamageSource.DROWN, 4);
	    	}
	    }
	    else
	    {
	    	if(this.ticksExisted % 25 == 0)
	    	{
	    		this.attackEntityFrom(DamageSource.DROWN, 1);
    			this.jump();
    			this.setMoveForward(1);
	    	}
	    }
	    
	    //MultiMob.LOGGER.info(this.getAttackTime() + " " + this.getAttackSignal() + " " + this.isOnFire());
	}
	
    /**
     * Drops an item at the position of the entity.
     */
	@Override
    @Nullable
    public EntityItem entityDropItem(ItemStack stack, float offsetY)
    {
        if (stack.isEmpty())
        {
            return null;
        }
        else
        {
            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + 1.5D, this.posZ, stack);
            entityitem.setDefaultPickupDelay();
        	for(int i = 0; i < 50; i++)
        	{
        		Vec3d vec = RandomPositionGenerator.getLandPos(this, 10, 7);
        		if(vec != null)
        		{

            		entityitem.motionX = (vec.x - entityitem.posX) / 18D;
	    			entityitem.motionY = (vec.y - entityitem.posY) / 18D+ 0.5D;
	    			entityitem.motionZ = (vec.z - entityitem.posZ) / 18D;	
	    			break;
        		}
        	}
            if (captureDrops)
            {
                this.capturedDrops.add(entityitem);
            }
            else
            {
                this.world.spawnEntity(entityitem);
            }
            
            
            return entityitem;
        }
    }
	
	  /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("AttackTime", this.getAttackTime());
        compound.setFloat("AttackSignal", this.getAttackSignal());
        compound.setBoolean("onFire", this.isOnFire());
        compound.setBoolean("inDanger", this.isInDanger());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setAttackTime(compound.getInteger("AttackTime"));
        this.setAttackSignal(compound.getFloat("AttackSignal"));
        this.setOnFire(compound.getBoolean("onFire"));
        this.setInDanger(compound.getBoolean("inDanger"));
    }
    

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
	
		
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {

		
	}
	
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_FLAMESPEWER;
    }
	
	static class AIFlameSpewAttack extends EntityAIBase
    {
        private final EntityFlameSpewer spewer;
        private int attackStep;
        private int attackTime;
        private float attackSignal;
        private boolean performingAttack;
        private boolean hasSeenPlayerThisAttack;

        public AIFlameSpewAttack(EntityFlameSpewer spewerIn)
        {
            this.spewer = spewerIn;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = this.spewer.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.attackStep = 10;
            this.attackSignal = 0;
            this.attackTime = 100;
            performingAttack = false;
            hasSeenPlayerThisAttack = false;
        }
        
        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return this.shouldExecute();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            this.spewer.setOnFire(false);
            this.attackSignal = 0;
            this.attackStep = 10;
            this.attackTime = 100;
            spewer.setAttackTime(attackStep);
            spewer.setAttackSignal(attackSignal);
            spewer.setOnFire(false);
            this.spewer.setInDanger(false);
            performingAttack = false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.attackTime;
            EntityLivingBase entitylivingbase = this.spewer.getAttackTarget();
        
            if(!this.performingAttack && this.spewer.isInLava() && this.spewer.canEntityBeSeen(entitylivingbase))
            {
            	if(attackTime <= 50)
            	{
            		this.spewer.setOnFire(false);
            		hasSeenPlayerThisAttack = false;
            	}
            	
                if(!spewer.isOnFire())
                {
                    if(this.spewer.canEntityBeSeen(entitylivingbase))
                    {
                    	hasSeenPlayerThisAttack = true;
                    }
                    
                    attackStep = (attackTime * 2) /10;
                    if(attackTime <= 3)
                    {
                        attackSignal += 0.05f;
                    }
                }
            }

            double d0 = this.spewer.getDistanceSq(entitylivingbase);

            if (d0 < 5.0D && !this.spewer.isInLava())
            {
            	this.spewer.setInDanger(true);
                if (this.attackTime <= 0)
                {
                    this.attackTime = 20;
                    this.spewer.attackEntityAsMob(entitylivingbase);
                }
            }
            else if (d0 < (this.getFollowDistance()/2) * (this.getFollowDistance()/2)  && this.spewer.isInLava())
            {
            	this.spewer.setInDanger(false);
                double d1 = entitylivingbase.posX - this.spewer.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F + 0.25f) - (this.spewer.posY + (double)(this.spewer.height / 2.0F));
                double d3 = entitylivingbase.posZ - this.spewer.posZ;

                if (this.attackTime <= 0)
                {
                    ++this.attackStep;
                    attackSignal -= 0.05f;
                    
                    if (this.attackStep == 1)
                    {
                        this.attackTime = 30;
                        this.spewer.setOnFire(true);
                        performingAttack = true;
                    }
                    else if (this.attackStep <= 10)
                    {
                        this.attackTime = 3;
                        this.spewer.setOnFire(true);
                        performingAttack = true;
                    }
                    else
                    {
                        this.attackTime = 100;
                        this.attackStep = 10;
                        performingAttack = false;
                    }

                    if (this.attackStep > 1 && hasSeenPlayerThisAttack)
                    {
                        float f = MathHelper.sqrt(MathHelper.sqrt(d0) * 0.1F);
                        this.spewer.world.playEvent((EntityPlayer)null, 1018, new BlockPos((int)this.spewer.posX, (int)this.spewer.posY, (int)this.spewer.posZ), 0);

                        for (int i = 0; i < 1; ++i)
                        {
                            EntityFlameSpit entitysmallfireball = new EntityFlameSpit(this.spewer.world, this.spewer, d1 + this.spewer.getRNG().nextGaussian() * 0.02F * (double)f, d2 - this.spewer.getRNG().nextGaussian() * 0.02F * (double)f, d3 + this.spewer.getRNG().nextGaussian() * 0.02F * (double)f);
                            entitysmallfireball.posY = this.spewer.posY + (double)(this.spewer.height / 2.0F) -0.5F;
                            this.spewer.world.spawnEntity(entitysmallfireball);
                        }
                    }
                }
            }
            else if(this.spewer.isInLava()  && this.spewer.canEntityBeSeen(entitylivingbase))
            {
                double d1 = entitylivingbase.posX - this.spewer.posX;
                double d3 = entitylivingbase.posZ - this.spewer.posZ;
                spewer.motionX = d1 * 0.01;
                spewer.motionZ = d3 * 0.01;
            	this.spewer.setInDanger(false);
                spewer.setOnFire(false);
            }
            else
            {
            	this.spewer.getNavigator().clearPath();
            	this.spewer.setInDanger(false);
                spewer.setOnFire(false);
            }
            
            this.spewer.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            
            if(attackSignal < 0)
            {
            	attackSignal = 0; 
            }
            else if (attackSignal > 0.4)
            {
            	attackSignal = 0.4f;
            }
            
            spewer.setAttackSignal(attackSignal);
            spewer.setAttackTime(attackStep);

            super.updateTask();
        }

        private double getFollowDistance()
        {
            IAttributeInstance iattributeinstance = this.spewer.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
        }
    }
	
    public boolean isOnFire()
    {
        return (((Byte)this.dataManager.get(ON_FIRE)).byteValue() & 1) != 0;
    }

    public void setOnFire(boolean onFire)
    {
        byte b0 = ((Byte)this.dataManager.get(ON_FIRE)).byteValue();

        if (onFire)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(ON_FIRE, Byte.valueOf(b0));
    }
    
    public boolean isInDanger()
    {
        return (((Byte)this.dataManager.get(IN_DANGER)).byteValue() & 1) != 0;
    }

    public void setInDanger(boolean onFire)
    {
        byte b0 = ((Byte)this.dataManager.get(IN_DANGER)).byteValue();

        if (onFire)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(IN_DANGER, Byte.valueOf(b0));
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }
    
    static class AIGoToLava extends EntityAIMoveToBlock
    {
        private final EntityFlameSpewer spewer;

        public AIGoToLava(EntityFlameSpewer spewer)
        {
            super(spewer, 0.699999988079071D, 25);
            this.spewer = spewer;
        }
        
        public boolean shouldExecute()
        {
           return !this.spewer.isInLava() && super.shouldExecute();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            super.updateTask();
            this.spewer.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.spewer.getVerticalFaceSpeed());

            if (this.getIsAboveDestination())
            {
                this.runDelay = 10;
            }
        }

        /**
         * Return true to set given position as destination
         */
        protected boolean shouldMoveTo(World worldIn, BlockPos pos)
        {
            Block block = worldIn.getBlockState(pos).getBlock();
            BlockPos tempPos = pos;
            
            if (block == Blocks.LAVA)
            {
                if (worldIn.isAirBlock(pos.up()))
                {
                	if(worldIn.getBlockState(pos.east()).getBlock() == Blocks.LAVA && worldIn.getBlockState(pos.west()).getBlock() == Blocks.LAVA
                			&& worldIn.getBlockState(pos.north()).getBlock() == Blocks.LAVA && worldIn.getBlockState(pos.south()).getBlock() == Blocks.LAVA)
                	{
                        return true;
                	}
                }
            }

            return false;
        }
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
        return this.posY < 64;
    }
	
	

}
