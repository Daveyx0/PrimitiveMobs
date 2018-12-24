package net.daveyx0.primitivemobs.entity.monster;

import java.util.Random;

import javax.annotation.Nullable;

import net.daveyx0.multimob.entity.EntityMMFlyingMob;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityHarpy extends EntityMMFlyingMob {

	public EntityHarpy(World worldIn) {
		super(worldIn);
		this.setSize(0.8f, 1f);
		this.moveHelper = new EntityHarpyFlyHelper(this);
	}

    protected void initEntityAI()
    {
    	super.initEntityAI();
        this.tasks.addTask(1, new AIHarpyLift(this, 1.5D, false));
        int attackPrio = 1;
        this.targetTasks.addTask(++attackPrio, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(++attackPrio, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.5000000059604645D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }
    
    @Nullable
    public SoundEvent getAmbientSound()
    {
        return PrimitiveMobsSoundEvents.ENTITY_HARPY_IDLE;
    }
    
    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return false;
    }
    
    public void onUpdate()
    {
    	if(this.isBeingRidden())
    	{
    		this.motionY = 0.25F;
    		
    		if(!this.world.isRemote && (getDistanceToGround(new BlockPos(this.posX, this.posY, this.posZ)) >= 20 || !this.world.isAirBlock(new BlockPos(this.posX, this.posY + 1, this.posZ))))
    		{
    			this.removePassengers();
    		}
    	}

    	super.onUpdate();
    }

    public double getDistanceToGround(BlockPos pos)
    {
    	for(int i = 0; i < 64; i++)
    	{
    		BlockPos currentPos = pos.down(i);
    		if(this.world.isAirBlock(currentPos))
    		{
    			continue;
    		}
    		else
    		{
    			return this.getDistance(currentPos.getX(), currentPos.getY(), currentPos.getZ());
    		}
    	}
    	return 20;
    }
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(!this.world.isRemote && this.isBeingRidden())
    	{
    		this.removePassengers();
    	}
    	return super.attackEntityFrom(source, amount);
    }
    
	@Override
    public boolean canRiderInteract() 
	{
        return true;
    }
	
	@Override
	protected void collideWithEntity(Entity entity) {

	}

	@Override
	public boolean shouldRiderSit() {
		return false;
	}
	
    public class AIHarpyLift extends EntityAIAttackMelee
    {
		public AIHarpyLift(EntityCreature creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);

		}

		@Override
		public boolean shouldExecute() {

			return super.shouldExecute() && !this.attacker.isBeingRidden();
		}
		
	    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
	    {
	        double d0 = this.getAttackReachSqr(p_190102_1_);

	        if (p_190102_2_ <= d0 && this.attackTick <= 0)
	        {
	        	if(this.attacker.getAttackTarget().isBeingRidden() || !this.attacker.world.canSeeSky(new BlockPos(this.attacker.posX, this.attacker.posY, this.attacker.posZ)))
	        	{
		            this.attacker.swingArm(EnumHand.MAIN_HAND);
		            this.attacker.attackEntityAsMob(p_190102_1_);
	        	}
	        	else
	        	{
	        		this.attacker.getAttackTarget().startRiding(this.attacker);
	        	}
	        	
	            this.attackTick = 20;
	        }
	    }

    }
    
	@Override
	public double getYOffset() {
		if (this.isBeingRidden() && !this.getPassengers().isEmpty() && this.getPassengers().get(0) != null)
			return this.getPassengers().get(0).height;
		else
			return super.getYOffset();
	}
    

    
	@Override
	public void updatePassenger(Entity entity) {
		super.updatePassenger(entity);
		if (entity instanceof EntityLivingBase) 
		{
			entity.setPosition(posX, posY - getYOffset(), posZ);
			if (entity.isSneaking())
			{
				entity.setSneaking(false);
			}
		}
	}
	
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_HARPY;
    }
	
	public class EntityHarpyFlyHelper extends EntityMoveHelper
	{
	    public EntityHarpyFlyHelper(EntityLiving p_i47418_1_)
	    {
	        super(p_i47418_1_);
	    }

	    public void onUpdateMoveHelper()
	    {
	        if (this.action == EntityMoveHelper.Action.MOVE_TO)
	        {
	            this.action = EntityMoveHelper.Action.WAIT;
	            this.entity.setNoGravity(true);
	            double d0 = this.posX - this.entity.posX;
	            double d1 = this.posY - this.entity.posY;
	            double d2 = this.posZ - this.entity.posZ;
	            double d3 = d0 * d0 + d1 * d1 + d2 * d2;

	            if (d3 < 2.500000277905201E-7D)
	            {
	                this.entity.setMoveVertical(0.0F);
	                this.entity.setMoveForward(0.0F);
	                return;
	            }

	            float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
	            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, 10.0F);
	            float f1;

	            if (this.entity.onGround)
	            {
	                f1 = (float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
		            this.entity.setAIMoveSpeed(f1);
	            }
	            else
	            {
	                f1 = (float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue());
		            this.entity.setAIMoveSpeed(f1 * 2.5F);
	            }


	            double d4 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
	            float f2 = (float)(-(MathHelper.atan2(d1, d4) * (180D / Math.PI)));
	            this.entity.rotationPitch = this.limitAngle(this.entity.rotationPitch, f2, 10.0F);
	            this.entity.setMoveVertical(d1 > 0.0D ? f1 : -f1);
	        }
	        else
	        {
	            this.entity.setNoGravity(false);
	            this.entity.setMoveVertical(0.0F);
	            this.entity.setMoveForward(0.0F);
	        }
	    }
	}
	
    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.8F;
    }
    
    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return PrimitiveMobsSoundEvents.ENTITY_HARPY_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return PrimitiveMobsSoundEvents.ENTITY_HARPY_HURT;
    }
}
