package net.daveyx0.primitivemobs.entity.monster;

import java.util.Random;

import javax.annotation.Nullable;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.entity.ai.EntityAICustomFollowOwner;
import net.daveyx0.multimob.entity.ai.EntityAICustomOwnerHurtByTarget;
import net.daveyx0.multimob.entity.ai.EntityAICustomOwnerHurtTarget;
import net.daveyx0.multimob.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBabySpider extends EntityPrimitiveSpider
{
    private int rideAttemptDelay = rand.nextInt(100);
    boolean initChild = false;
    private int jumpMountTicks;
    
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.<Byte>createKey(EntityBabySpider.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> GROWTH_LEVEL = EntityDataManager.<Integer>createKey(EntityBabySpider.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_JUMPING = EntityDataManager.<Boolean>createKey(EntityBabySpider.class, DataSerializers.BOOLEAN);
    
	public EntityBabySpider(World worldIn) {
		super(worldIn);
		this.setTamed(false);
		this.setSize(0.5F, 0.3F);
		this.setGrowthLevel(0);
	}

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(GROWTH_LEVEL, Integer.valueOf(0));
        this.getDataManager().register(DYE_COLOR, Byte.valueOf((byte)0));
        this.getDataManager().register(IS_JUMPING, false);
    }
    
    public float getEyeHeight()
    {
        return this.height/2;
    }
    
    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    @Override
    public boolean canBeSteered()
    {
        return this.getGrowthLevel() >= 5;
    }
    
    public boolean isOnLadder()
    {
        int j = MathHelper.floor(posX);
        int k = MathHelper.floor(posY);
        int l = MathHelper.floor(posZ);
        boolean side1 = world.isBlockFullCube(new BlockPos(j + 1, k, l));
        boolean side2 = world.isBlockFullCube(new BlockPos(j - 1, k, l));
        boolean side3 = world.isBlockFullCube(new BlockPos(j, k, l + 1));
        boolean side4 = world.isBlockFullCube(new BlockPos(j, k, l - 1));
        return this.isBesideClimbableBlock() || (side1 || side2 || side3 || side4);
    }
   
    protected void initEntityAI()
    {	
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityPrimitiveSpider.AISpiderAttack(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAICustomFollowOwner(this, 1.0D, 8.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAICustomOwnerHurtByTarget(this));
        this.targetTasks.addTask(3, new EntityAICustomOwnerHurtTarget(this));
    }
    

    
    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked()
    {
		ITameableEntity tameable = EntityUtil.getCapability(this, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
		if(tameable != null && tameable.isTamed())
		{
			if(tameable.getFollowState() == 0)
			{
				return true;
			}
		}
        return false;
    }
    
    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return !this.isBeingRidden();
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(this.getOwner() != null && this.getOwner() instanceof EntityMotherSpider)
    	{
    		((EntityMotherSpider)this.getOwner()).setIsAngry(true);
    	}
        return source.getDamageType().equals("inWall") ? !isRiding() :  super.attackEntityFrom(source, amount);
    }
    
	public void onUpdate()
	{
		super.onUpdate();
        if(!initChild && this.getOwner() != null && this.getOwner() instanceof EntityMotherSpider)
        {
        	EntityMotherSpider mother = (EntityMotherSpider)this.getOwner();
        	mother.addFollower(this);
        	initChild = true;
        }
        
		if(rideAttemptDelay++ > 100 && this.getOwner() != null && !this.getOwner().isBeingRidden() && this.getOwner() instanceof EntityMotherSpider)
		{
			this.startRiding(this.getOwner());
		}
		
        if(getEntityWorld().isRemote && this.getOwner() == null)
        {
        	getEntityWorld().spawnParticle(EnumParticleTypes.WATER_SPLASH, posX + (rand.nextFloat() - rand.nextFloat()), posY + (rand.nextFloat() - rand.nextFloat()) + 1F, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
        }
        
        switch(this.getGrowthLevel())
        {
        	case 0:
        		this.setSize(0.5F, 0.3F); break;
        	case 1:
        		this.setSize(0.7F, 0.4F); break;
        	case 2:
        		this.setSize(0.9F, 0.5F); break;
        	case 3:
        		this.setSize(1.1F, 0.6F); break;
        	case 4:
        		this.setSize(1.3F, 0.7F); break;
        	case 5:
        		this.setSize(1.5F, 0.8F); break;
        }

	}
	
	  public void setGrowthLevel(int saplings)
	    {
	        this.getDataManager().set(GROWTH_LEVEL, Integer.valueOf(saplings));
	    }
	    
	    public int getGrowthLevel()
	    {
	        return ((Integer)this.getDataManager().get(GROWTH_LEVEL)).intValue();
	    }
	    
	    
	    public boolean processInteract(EntityPlayer player, EnumHand hand)
	    {
	    	 ItemStack itemstack = player.getHeldItem(hand);
		        boolean flag = itemstack.getItem() == Items.FERMENTED_SPIDER_EYE;
		        
		        if (flag)
		        {
		        	this.consumeItemFromStack(player, itemstack);
		        	this.setGrowthLevel(this.getGrowthLevel() + 1);
	             for (int i = 0; i < 8; i++)
	             {
	             	world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
	             }
	             	
	             	if(this.getGrowthLevel() == 5)
	             	{
	             		this.setEyeColor(getRandomEyeColor(this.world.rand));
	             		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D + (rand.nextFloat() * 0.1));
	             	}
		            return true;
		        }
		        else
		        {
		        	if(!itemstack.isEmpty() && itemstack.getItem() == Items.DYE)
		        	{
		                EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

		                if (this.getEyeColor() != enumdyecolor)
		                {
		                    this.setEyeColor(enumdyecolor);
		                    itemstack.shrink(1);
		                }
		        	}
		            return super.processInteract(player, hand);
		        }
	    }
	    
	    /**
	     * Decreases ItemStack size by one
	     */
	    protected void consumeItemFromStack(EntityPlayer player, ItemStack stack)
	    {
	        if (!player.capabilities.isCreativeMode)
	        {
	            stack.shrink(1);
	        }
	    }
	
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Growth", this.getGrowthLevel());
        compound.setBoolean("Jumping", this.getIsJumping());
        compound.setByte("Color", (byte)this.getEyeColor().getMetadata());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setGrowthLevel(compound.getInteger("Growth"));
        this.setIsJumping(compound.getBoolean("Jumping"));
        this.setEyeColor(EnumDyeColor.byMetadata(compound.getByte("Color")));
    }
    
    /**
     * Gets the wool color of this sheep.
     */
    public EnumDyeColor getEyeColor()
    {
        return EnumDyeColor.byMetadata(((Byte)this.dataManager.get(DYE_COLOR)).byteValue() & 15);
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setEyeColor(EnumDyeColor color)
    {
        byte b0 = ((Byte)this.dataManager.get(DYE_COLOR)).byteValue();
        this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 & 240 | color.getMetadata() & 15)));
    }
    
    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
    }
    
    
    public void travel(float strafe, float vertical, float forward)
    {
        if (this.jumpMountTicks > 0)
        {
            --this.jumpMountTicks;
        }
        
        if (this.isBeingRidden() && this.canBeSteered())
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();
            this.rotationYaw = entitylivingbase.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.renderYawOffset;
            strafe = entitylivingbase.moveStrafing * 0.5F;
            forward = entitylivingbase.moveForward;
            
            if (!this.world.isRemote)
            {
                this.setBesideClimbableBlock(this.collidedHorizontally);
            }
            
            if (forward <= 0.0F)
            {
                forward *= 0.5F;
            }
            
            if(this.isInWater() && !this.isBesideClimbableBlock())
            {
            	motionY = 0.02D;
            }
            
            /*
            if(getIsJumping() && this.onGround)
            {
                this.motionY = 0.4;

                if (this.isPotionActive(MobEffects.JUMP_BOOST))
                {
                    this.motionY += (double)((float)(this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
                }
                
                this.isAirBorne = true;

                    if (forward > 0.0F)
                    {
                        float f = MathHelper.sin(entitylivingbase.rotationYaw * 0.017453292F);
                        float f1 = MathHelper.cos(entitylivingbase.rotationYaw * 0.017453292F);
                        this.motionX += (double)(-0.4F * f * 0.5);
                        this.motionZ += (double)(0.4F * f1 * 0.5);
                    }
            	
            	this.setIsJumping(false);
            }*/
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.5F;

            if (this.canPassengerSteer())
            {
            	this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()/1.5f);
                super.travel(strafe, vertical, forward);
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

            if (f2 > 1.0F)
            {
                f2 = 1.0F;
            }
            

            this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        }
        else
        {
            super.travel(strafe, vertical, forward);
        }
    }
    
    @Override
    protected float getJumpUpwardsMotion()
    {
        return 0.62F;
    }



	public void setIsJumping(boolean b) {

		 this.dataManager.set(IS_JUMPING, b);
	}
	
    public boolean getIsJumping()
    {
		return ((Boolean)this.dataManager.get(IS_JUMPING)).booleanValue();
    }
    
    /**
     * Chooses a "vanilla" sheep color based on the provided random.
     */
    public static EnumDyeColor getRandomEyeColor(Random random)
    {
        int i = random.nextInt(EnumDyeColor.values().length);
        return EnumDyeColor.byMetadata(i);
    }

    
    
}
