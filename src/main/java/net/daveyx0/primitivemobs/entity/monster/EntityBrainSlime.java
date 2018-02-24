package net.daveyx0.primitivemobs.entity.monster;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.core.PrimitiveMobsParticles;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColor;
import net.daveyx0.primitivemobs.message.MessagePrimitiveParticle;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public class EntityBrainSlime extends EntitySlime {

	public int attackDelay;
	public float suckingb;
	public float suckingc;
	public float suckingd;
	public float suckinge;
	public float suckingh;
	private boolean wasOnGround;
	public int maxStack;
	private boolean checkedAI = false;
	private final EntityAIFindEntityNearest hostilityAI = new EntityAIFindEntityNearest(this, EntityAnimal.class);
	
	private static final DataParameter<Integer> SATURATION = EntityDataManager.<Integer>createKey(EntityBrainSlime.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> ATTACK_DELAY = EntityDataManager.<Integer>createKey(EntityBrainSlime.class, DataSerializers.VARINT);
	
	public EntityBrainSlime(World worldIn) {
		super(worldIn);
		this.moveHelper = new EntityBrainSlime.SlimeMoveHelper(this);
		this.setAttackDelay(0);
	    suckingb = 0.0F;
	    suckingc = 0.0F;
	    suckingh = 1.0F;
	    maxStack = 10;
	}
	
	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityBrainSlime.AISlimeFloat(this));
        this.tasks.addTask(2, new EntityBrainSlime.AISlimeAttack(this));
        this.tasks.addTask(3, new EntityBrainSlime.AISlimeFaceRandom(this));
        this.tasks.addTask(4, new EntityBrainSlime.AISlimeHop(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }
	
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(ATTACK_DELAY, Integer.valueOf(0));
        this.getDataManager().register(SATURATION, Integer.valueOf(0));
        this.dismountRidingEntity();
        if(!PrimitiveMobsConfigMobs.enableBrainSlime)
        {
        	this.setDead();
        }
    }
	
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        int i = this.rand.nextInt(3);
        this.setSlimeSize(i, true);
        return livingdata;
    }
	
	public void onUpdate()
	{
		if(!checkedAI)
		{
		if(!this.targetTasks.taskEntries.contains(hostilityAI) && PrimitiveMobsConfigSpecial.getBrainSlimeHostility() && this.getSlimeSize() < 3)
		{
			this.targetTasks.addTask(2, hostilityAI);
		}
		checkedAI = true;
		}
		
		if(this.getSaturation() >= 10)
		{
			this.setSaturation(this.getSaturation() + 1);
			if(this.getSaturation() >= 100)
			{
				this.setSlimeSize(this.getSlimeSize() + 1, true);
				this.setSaturation(0);
				this.setAttackDelay(50);
				if(this.getSlimeSize() >= 3){this.targetTasks.removeTask(hostilityAI);}
				if(!world.isRemote)
				{
					PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), 10, (float)this.posX + 0.5f, (float)this.posY + 0.5F, (float)this.posZ + 0.5f, 0D, 0.0D,0.0D, 0));
				}
			}
		}
		suckinge = suckingb;
	    suckingd = suckingc;
	    setAttackDelay(getAttackDelay() - 1);
	    
		if(!this.isRiding())
		{
		    suckingb = 0.0F;
		    suckingc = 0.0F;
		    suckingh = 1.0F;

		    if(getAttackDelay() <= 0)
		    {
		    	EntityPlayer player = this.getEntityWorld().getNearestAttackablePlayer(this, 2D, 2D);
		    	
		    	if(player != null)
		    	{
		    		if(!getEntityWorld().isRemote && this.startRidingTopEntity(player, false))
		    		{
		    	        if(player instanceof EntityPlayerMP) {
		    	            PrimitiveMobs.network.sendPacket(player, new SPacketSetPassengers(player));
		    	        }
		    		}
		    		setAttackDelay(20);
		    	}
		    	else
		    	{
		    		List<Entity> list = this.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(1D, 0D, 1D));
		    	
		    		if (list != null && !list.isEmpty())
		    		{
		    			for (int j1 = 0; j1 < list.size(); ++j1)
		    			{
		    				Entity entity1 = (Entity)list.get(j1);
                
		    				if (entity1 != null && hasBrainToSuck(entity1, true))
		    				{
		    					if(this.startRidingTopEntity((EntityLivingBase) entity1, false))
		    					{
			    					setAttackDelay(10);
		    						break;
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		}
		else
		{
			if(getAttackDelay() <= 0)
		    {
				this.attackLowestVulnerableRidingEntity();
				setAttackDelay(10);
		    }
			
			suckingc = (float)((double)suckingc + 4 * 0.8D);

		    if (suckingc < 0.0F)
		    {
		        suckingc = 0.0F;
		    }

		    if (suckingc > 0.2F)
		    {
		        suckingc = 0.2F;
		    }

		    if (suckingh < 0.2F)
		    {
		        suckingh = 0.2F;
		    }

		    suckingh = (float)((double)suckingh * 0.9D);
		    suckingb += suckingh * 2.0F;
		}
		
		super.onUpdate();
	}
	
	public boolean hasBrainToSuck(Entity entity, boolean ignoreBrainSlime)
	{
		if(this.getSaturation() >= 10){return false;}
		if(!PrimitiveMobsConfigSpecial.getBrainSlimeHostility()){return false;}
		if(this.getSlimeSize() >= 3){return false;}
			
		if(!(entity instanceof EntityLivingBase)) {return false;}
		
		if(entity instanceof EntityTameable)
		{
			EntityTameable tameable = (EntityTameable)entity;
			if(tameable.isTamed()) {return false;}
		}
		
		if(entity instanceof EntityCreature)
		{
			EntityCreature creature = (EntityCreature)entity;
			if(creature.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {return false;};
		}
		
		if(entity instanceof EntitySlime)
		{
			if(!ignoreBrainSlime) {return entity instanceof EntityBrainSlime;};
			return false;
		}
		
		if(entity instanceof EntityPlayer || entity instanceof EntityPlayerMP)
		{
			return false;
		}
		
		return true;
	}
	
    
    public boolean startRidingTopEntity(EntityLivingBase entity, boolean force)
    {
    	EntityLivingBase entityOut = entity;
		for(int i = 0; i < maxStack; i++)
		{
			if(entityOut.isBeingRidden())
			{
				Entity entityTest = entityOut.getPassengers().get(0);
				if(entityTest != null && hasBrainToSuck(entityTest, false) && entityTest.isRiding())
				{
					entityOut = (EntityLivingBase)entityTest;
					
					if(entityTest.isBeingRidden()) {continue;}
					else {break;}
				}
				else if(entityTest != null && entityTest instanceof EntityPlayer&& entityTest.isRiding())
				{
					entityOut = (EntityLivingBase)entityTest;
					
					if(entityTest.isBeingRidden()) {continue;}
					else {break;}
				}else {break;}
			}else {break;}
		}
		
		return this.startRiding(entityOut, force);
    }
    
    public void attackLowestVulnerableRidingEntity()
    {
    	if(this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityLivingBase)
    	{
    		EntityLivingBase entityOut = (EntityLivingBase)this.getRidingEntity();
    		for(int i = 0; i < maxStack; i++)
    		{
    			if(entityOut.isRiding())
    			{
    				Entity entityTest = entityOut.getRidingEntity();
    				if(entityTest != null && hasBrainToSuck(entityTest, false) && entityTest.isBeingRidden())
    				{
    					entityOut = (EntityLivingBase)entityTest;
    					if(entityTest instanceof EntityBrainSlime && entityTest.isRiding()) {
    						continue;}
    					else {break;}
    				}
    				else if(entityTest != null && entityTest instanceof EntityPlayer && entityTest.isBeingRidden())
    				{
    					entityOut = (EntityLivingBase)entityTest;
    					if(entityTest instanceof EntityBrainSlime && entityTest.isRiding()) {
    						continue;}
    					else {break;}
    					
    				}else{break;}
    			} else{break;}
    		}
    		
    		if(this.getSaturation() < 10 && (hasBrainToSuck(entityOut, true) || entityOut instanceof EntityPlayer))
    		{
    			this.damageHelmetOrEntity(entityOut);
    			this.setSaturation(this.getSaturation() + 1);
    		}
    		else
    		{
    			this.dismountRidingEntity();
    			setAttackDelay(10);
    		}
    	}
    }
    
    
	public void damageHelmetOrEntity(EntityLivingBase base)
	{
		ItemStack stack = base.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		if(!stack.isEmpty())
		{
			stack.damageItem(this.getAttackStrength(), base);
			if(stack.getItemDamage() == 0)
			{
				base.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
			}
		}
		else if(base.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength() * 1.25F))
		{
			this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.applyEnchantments(this, base);
		}
	}
    
	protected boolean spawnCustomParticles() { 
		if(getEntityWorld().isRemote)
		{
		int i = this.getSlimeSize();
        for (int j = 0; j < i * 8; ++j)
        {
            float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
            float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
            float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
            World world = this.getEntityWorld();
            double d0 = this.posX + (double)f2;
            double d1 = this.posZ + (double)f3;
            PrimitiveMobsParticles.spawnParticle("slime", d0, this.getEntityBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D, new float[]{209,165,189});
        }
        }return true; }
	
	
    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    protected int getAttackStrength()
    {
        return this.getSlimeSize() + 1;
    }
	
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if(this.isRiding())
    	{
    		this.dismountRidingEntity();
    		this.setAttackDelay(10);
    	}
    	return super.attackEntityFrom(source, amount);
    }
	
	
	 static class AISlimeAttack extends EntityAIBase
     {
         private final EntityBrainSlime slime;

         public AISlimeAttack(EntityBrainSlime slimeIn)
         {
             this.slime = slimeIn;
             this.setMutexBits(2);
         }

         /**
          * Returns whether the EntityAIBase should begin execution.
          */
         public boolean shouldExecute()
         {
             EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
             if(this.slime.getSaturation() >= 10) {return false;}
             
             if(entitylivingbase != null && slime.isRidingOrBeingRiddenBy(entitylivingbase))
             {
            	 return false;
             }
             
             if(entitylivingbase != null && slime.getDistanceToEntity(entitylivingbase) > 12.0F)
             {
            	 return false;
             }
             return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage);
         }

         /**
          * Execute a one shot task or start executing a continuous task
          */
         public void startExecuting()
         {
             super.startExecuting();
         }

         /**
          * Returns whether an in-progress EntityAIBase should continue executing
          */
         public boolean continueExecuting()
         {
             return this.shouldExecute();
         }

         /**
          * Updates the task
          */
         public void updateTask()
         {
             this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
             EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
             ((EntityBrainSlime.SlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, this.slime.canDamagePlayer());
         }
     }
	 
	    public boolean isSmallSlime()
	    {
	        return false;
	    }
	    
	    protected EntitySlime createInstance()
	    {
	        return new EntityBrainSlime(this.getEntityWorld());
	    }

	 
	    public EntityMoveHelper getMoveHelper()
	    {
	        return this.moveHelper;
	    }
	    
	    /**
	     * Applies a velocity to the entities, to push them away from eachother.
	     */
	    public void applyEntityCollision(Entity entityIn)
	    {
	        super.applyEntityCollision(entityIn);
	    }
	    
	    /**
	     * Called by a player entity when they collide with an entity
	     */
	    public void onCollideWithPlayer(EntityPlayer entityIn)
	    {
	    }
	 
	 static class AISlimeFaceRandom extends EntityAIBase
     {
         private final EntityBrainSlime slime;
         private float chosenDegrees;
         private int nextRandomizeTime;

         public AISlimeFaceRandom(EntityBrainSlime slimeIn)
         {
             this.slime = slimeIn;
             this.setMutexBits(2);
         }

         /**
          * Returns whether the EntityAIBase should begin execution.
          */
         public boolean shouldExecute()
         {
             return this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(MobEffects.LEVITATION));
         }

         /**
          * Updates the task
          */
         public void updateTask()
         {
             if (--this.nextRandomizeTime <= 0)
             {
                 this.nextRandomizeTime = 10 + this.slime.getRNG().nextInt(60);
                 this.chosenDegrees = (float)this.slime.getRNG().nextInt(360);
             }

             ((EntityBrainSlime.SlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
         }
     }
	 
	 static class AISlimeHop extends EntityAIBase
     {
         private final EntityBrainSlime slime;

         public AISlimeHop(EntityBrainSlime slimeIn)
         {
             this.slime = slimeIn;
             this.setMutexBits(5);
         }

         /**
          * Returns whether the EntityAIBase should begin execution.
          */
         public boolean shouldExecute()
         {
             return true;
         }

         /**
          * Updates the task
          */
         public void updateTask()
         {
             ((EntityBrainSlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0D);
         }
     }
	 
	 public double getYOffset()
	 {
		 if(this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityPlayer)
		 {
			 return 0.25F;
		 }
		 else
		 {
			 return 0.0D;
		 }
	 }
	
	static class AISlimeFloat extends EntityAIBase
    {
        private final EntityBrainSlime slime;

        public AISlimeFloat(EntityBrainSlime slimeIn)
        {
            this.slime = slimeIn;
            this.setMutexBits(5);
            ((PathNavigateGround)slimeIn.getNavigator()).setCanSwim(true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return this.slime.isInWater() || this.slime.isInLava();
        }

        /**
         * Updates the task
         */
        public void updateTask()
        {
            if (this.slime.getRNG().nextFloat() < 0.8F)
            {
                this.slime.getJumpHelper().setJumping();
            }

            ((EntityBrainSlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2D);
        }
    }
	
	 static class SlimeMoveHelper extends EntityMoveHelper
     {
         private float yRot;
         private int jumpDelay;
         private final EntityBrainSlime slime;
         private boolean isAggressive;

         public SlimeMoveHelper(EntityBrainSlime slimeIn)
         {
             super(slimeIn);
             this.slime = slimeIn;
             this.yRot = 180.0F * slimeIn.rotationYaw / (float)Math.PI;
         }

         public void setDirection(float p_179920_1_, boolean p_179920_2_)
         {
             this.yRot = p_179920_1_;
             this.isAggressive = p_179920_2_;
         }

         public void setSpeed(double speedIn)
         {
             this.speed = speedIn;
             this.action = EntityMoveHelper.Action.MOVE_TO;
         }

         public void onUpdateMoveHelper()
         {
             this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
             this.entity.rotationYawHead = this.entity.rotationYaw;
             this.entity.renderYawOffset = this.entity.rotationYaw;

             if (this.action != EntityMoveHelper.Action.MOVE_TO)
             {
                 this.entity.setMoveForward(0.0F);
             }
             else
             {
                 this.action = EntityMoveHelper.Action.WAIT;

                 if (this.entity.onGround)
                 {
                     this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                     if (this.jumpDelay-- <= 0)
                     {
                         this.jumpDelay = this.slime.getJumpDelay();

                         if (this.isAggressive && this.slime.getSaturation() < 10)
                         {
                             if (this.slime.makesSoundOnJump())
                             {
                                 this.slime.playSound(PrimitiveMobsSoundEvents.ENTITY_BRAINSLIME_CHARGE, this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                             }
                        	 this.performChargeAttack();
                         }
                         else
                         {

                        	 this.slime.getJumpHelper().setJumping();

                        	 if (this.slime.makesSoundOnJump())
                        	 {
                        		 this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                        	 }
                         }
                     }
                     else
                     {
                         this.slime.moveStrafing = 0.0F;
                         this.slime.moveForward = 0.0F;
                         this.entity.setAIMoveSpeed(0.0F);
                     }
                 }
                 else
                 {
                     this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                 }
             }
         }
         
         public void performChargeAttack()
         {
        	 EntityLivingBase entity = this.entity.getAttackTarget();
        	 if(entity != null)
        	 {
        		this.entity.motionX += (entity.posX - this.entity.posX) / 8D;
        	 	this.entity.motionY += (entity.posY - this.entity.posY) / 8D;
             	if(this.entity.motionY <= 0.0D)
             	{
                 	this.entity.motionY = 0.0D;
             	}
             	this.entity.motionY += 0.5D;
             	this.entity.motionZ += (entity.posZ - this.entity.posZ) / 8D;    
             	}
         }
     }
	 
	    public void setAttackDelay(int delay)
	    {
	        this.getDataManager().set(ATTACK_DELAY, Integer.valueOf(delay));
	    }
	    
	    public int getAttackDelay()
	    {
	        return ((Integer)this.getDataManager().get(ATTACK_DELAY)).intValue();
	    }
	    
	    public void setSaturation(int delay)
	    {
	        this.getDataManager().set(SATURATION, Integer.valueOf(delay));
	    }
	    
	    public int getSaturation()
	    {
	        return ((Integer)this.getDataManager().get(SATURATION)).intValue();
	    }
	 
	 /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    public void writeEntityToNBT(NBTTagCompound compound)
	    {
	        super.writeEntityToNBT(compound);
	        compound.setInteger("CurrentsAttackDelay", this.getAttackDelay());
	        compound.setInteger("Saturation", this.getSaturation());
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    public void readEntityFromNBT(NBTTagCompound compound)
	    {
	        super.readEntityFromNBT(compound);
	        this.setAttackDelay(compound.getInteger("CurrentAttackDelay"));
	        this.setSaturation(compound.getInteger("Saturation"));
	    }
	    
	    protected Block spawnableBlock = Blocks.SAND;
	    /**
	     * Checks if the entity's current position is a valid location to spawn this entity.
	     */
	    @Override
	    public boolean getCanSpawnHere()
	    {
	        int i = MathHelper.floor(this.posX);
	        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
	        int k = MathHelper.floor(this.posZ);
	        BlockPos blockpos = new BlockPos(i, j, k);
	        return this.getEntityWorld().getBlockState(blockpos.down()).getBlock() == this.spawnableBlock;
	    }
}
	
