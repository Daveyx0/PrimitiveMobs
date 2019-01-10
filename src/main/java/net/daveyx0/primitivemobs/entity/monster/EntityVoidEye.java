package net.daveyx0.primitivemobs.entity.monster;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.multimob.entity.EntityMMFlyingCreature;
import net.daveyx0.multimob.entity.IMultiMob;
import net.daveyx0.multimob.entity.ai.EntityAIFlyingAround;
import net.daveyx0.multimob.entity.ai.EntityAISenseEntityNearestPlayer;
import net.daveyx0.multimob.message.MMMessageRegistry;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.message.MessageTeleportEye;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper.Action;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.entity.boss.EntityDragon;

public class EntityVoidEye extends EntityMMFlyingCreature implements IMultiMob {

    private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.<Integer>createKey(EntityVoidEye.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> CAN_SEE_TARGET = EntityDataManager.<Boolean>createKey(EntityVoidEye.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DOES_TELEPORT = EntityDataManager.<Boolean>createKey(EntityVoidEye.class, DataSerializers.BOOLEAN);
    private EntityLivingBase targetedEntity;
    private int clientSideAttackTime;

	public EntityVoidEye(World worldIn) {
		super(worldIn);
		this.setSize(0.6f, 0.6f);
		this.setTeleports(false);
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
    }
    
	
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityVoidEye.AIVoidEyeAttack(this));
        this.tasks.addTask(2, new EntityAIFlyingAround(this));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAISenseEntityNearestPlayer(this, 18));
    }
    

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(TARGET_ENTITY, Integer.valueOf(0));
        this.dataManager.register(CAN_SEE_TARGET, Boolean.valueOf(false));
        this.dataManager.register(DOES_TELEPORT, Boolean.valueOf(false));
    }
    
	public void setCanSeeTarget(boolean sees)
    {
        this.getDataManager().set(CAN_SEE_TARGET, Boolean.valueOf(sees));
    }
    
    public boolean canSeeTarget()
    {
        return ((Boolean)this.getDataManager().get(CAN_SEE_TARGET)).booleanValue();
    }
    
	public void setTeleports(boolean sees)
    {
        this.getDataManager().set(DOES_TELEPORT, Boolean.valueOf(sees));
    }
    
    public boolean doesTeleport()
    {
        return ((Boolean)this.getDataManager().get(DOES_TELEPORT)).booleanValue();
    }
    
    public float getAttackAnimationScale(float p_175477_1_)
    {
        return ((float)this.clientSideAttackTime + p_175477_1_) / (float)this.getAttackDuration();
    }
    
    public float getEyeHeight()
    {
        return this.height * 0.5F;
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            if (this.hasTargetedEntity())
            {
                if (this.clientSideAttackTime < this.getAttackDuration())
                {
                    ++this.clientSideAttackTime;
                }

                /*
                EntityLivingBase entitylivingbase = this.getTargetedEntity();

                if (entitylivingbase != null)
                {
                    this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
                    this.getLookHelper().onUpdateLook();
                    double d5 = (double)this.getAttackAnimationScale(0.0F);
                    double d0 = entitylivingbase.posX - this.posX;
                    double d1 = entitylivingbase.posY + (double)(entitylivingbase.height * 0.5F) - (this.posY + (double)this.getEyeHeight());
                    double d2 = entitylivingbase.posZ - this.posZ;
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    d0 = d0 / d3;
                    d1 = d1 / d3;
                    d2 = d2 / d3;
                    double d4 = this.rand.nextDouble();

                    while (d4 < d3)
                    {
                        d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
                        this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + d0 * d4, this.posY + d1 * d4 + (double)this.getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D);
                    }
                } */
            }
        }
        
        if(!this.world.isRemote)
        {
            if(this.getTargetedEntity() != null && this.canEntityBeSeen(this.getTargetedEntity()))
            {
            	this.setCanSeeTarget(true);
            }
            else
            {
            	this.setCanSeeTarget(false);
            }
        }
        
        if(this.doesTeleport())
        {
            this.teleportRandomly();
        }



        if (this.hasTargetedEntity())
        {
            this.rotationYaw = this.rotationYawHead;
        }

        super.onLivingUpdate();
    }
    
    @Nullable
    public EntityLivingBase getTargetedEntity()
    {
        if (!this.hasTargetedEntity())
        {
            return null;
        }
        else if (this.world.isRemote)
        {
            if (this.targetedEntity != null)
            {
                return this.targetedEntity;
            }
            else
            {
                Entity entity = this.world.getEntityByID(((Integer)this.dataManager.get(TARGET_ENTITY)).intValue());

                if (entity instanceof EntityLivingBase)
                {
                    this.targetedEntity = (EntityLivingBase)entity;
                    return this.targetedEntity;
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            return this.getAttackTarget();
        }
    }
    
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        super.notifyDataManagerChange(key);

        if (TARGET_ENTITY.equals(key))
        {
            this.clientSideAttackTime = 0;
            this.targetedEntity = null;
        }
    }
    
    public int getAttackDuration()
    {
        return 75;
    }

    private void setTargetedEntity(int entityId)
    {
        this.dataManager.set(TARGET_ENTITY, Integer.valueOf(entityId));
    }

    public boolean hasTargetedEntity()
    {
        return ((Integer)this.dataManager.get(TARGET_ENTITY)).intValue() != 0;
    }
    
    public int getTargetedEntityID( )
    {
    	 return ((Integer)this.dataManager.get(TARGET_ENTITY)).intValue();
    }

    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public void onUpdate()
    {
    	super.onUpdate();
    	
    	this.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY + 0.5, posZ, 0.0D, 0.0D, 0.0D);
    }
    
    static class AIVoidEyeAttack extends EntityAIBase
    {
        private final EntityVoidEye eye;
        private int tickCounter;

        public AIVoidEyeAttack(EntityVoidEye eye)
        {
            this.eye = eye;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = this.eye.getAttackTarget();
            List<EntityVoidEye> voidEyesList =  this.eye.world.<EntityVoidEye>getEntitiesWithinAABB(EntityVoidEye.class, this.eye.getEntityBoundingBox().grow(20, 20, 20), null);
            if(voidEyesList != null && !voidEyesList.isEmpty() && !this.eye.hasTargetedEntity())
            {
            	for(EntityVoidEye voidEye : voidEyesList)
            	{
            		if(entitylivingbase != null && voidEye.hasTargetedEntity() && voidEye.getTargetedEntityID() == entitylivingbase.getEntityId())
            		{
            			return false;
            		}
            	}
            }
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }


        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.tickCounter = -10;
            this.eye.getNavigator().clearPath();
            this.eye.getLookHelper().setLookPositionWithEntity(this.eye.getAttackTarget(), 90.0F, 90.0F);
            this.eye.isAirBorne = true;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            this.eye.setTargetedEntity(0);
            this.eye.setAttackTarget((EntityLivingBase)null);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            EntityLivingBase entitylivingbase = this.eye.getAttackTarget();
            this.eye.getNavigator().clearPath();
            this.eye.getMoveHelper().action = Action.WAIT;
            this.eye.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);

                ++this.tickCounter;

                if (this.tickCounter == 0)
                {
                	this.eye.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 1, 1);
                    this.eye.setTargetedEntity(this.eye.getAttackTarget().getEntityId());
                    //this.eye.world.setEntityState(this.eye, (byte)21);
                }
                else if (this.tickCounter >= this.eye.getAttackDuration())
                {
                    if(this.eye.canSeeTarget())
                    {
                        entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.eye, this.eye), (float)this.eye.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                        //entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.eye), (float)this.eye.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    }
                    
                    entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 240, 0));
                    entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 240, 0));
                    entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 240, 0));

                    this.eye.setAttackTarget((EntityLivingBase)null);
                }

                super.updateTask();
            
        }
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else 
        {
            boolean flag = super.attackEntityFrom(source, amount);
            if (source.isUnblockable() && this.rand.nextInt(5) != 0 && world.isRemote)
            {
            	setTeleports(true);
            	MMMessageRegistry.getNetwork().sendToServer(new MessageTeleportEye(true, this.getUniqueID().toString()));
            }

            return flag;
        }
    }
    
    protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 16.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(32) - 16);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 16.0D;
        return this.teleportTo(d0, d1, d2);
    }
    
	
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_VOIDEYE;
    }
    

    private boolean teleportTo(double x, double y, double z)
    {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag)
        {
            this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            this.setTeleports(false);
           // MMMessageRegistry.getNetwork().sendToServer(new MessageTeleportEye(false, this.getUniqueID().toString()));
        }
        
        return flag;
    }

    
    @Nullable
    public SoundEvent getAmbientSound()
    {
        return PrimitiveMobsSoundEvents.ENTITY_VOIDEYE_IDLE;
    }
    
    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return SoundEvents.ENTITY_ENDEREYE_LAUNCH;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_ENDEREYE_DEATH;
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
    	if(this.world.provider.getDimension() == 1)
    	{
    		for(Entity entity : this.world.loadedEntityList)
    		{
    			if(entity instanceof EntityDragon)
    			{
    				return false;
    			}
    		}
    	}
        return super.getCanSpawnHere();
    }
    
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	if(type == EnumCreatureType.MONSTER){return false;}
    	return super.isCreatureType(type, forSpawnCount);
    }

}
