package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityRocketCreeper extends EntityPrimitiveCreeper {

	private float explosionRadius = 3;

	public EntityRocketCreeper(World worldIn) {
		super(worldIn);
		setRocket(false);
	}
	
	private static final DataParameter<Boolean> IS_ROCKET = EntityDataManager.<Boolean>createKey(EntityRocketCreeper.class, DataSerializers.BOOLEAN);
	
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(IS_ROCKET, Boolean.valueOf(false));
    }
    
    public void fall(float distance, float damageMultiplier)
    {
        if(isRocket())
        {
        	this.explode();
        }
        else
        {
        	super.fall(distance, damageMultiplier);
        }
    }
    
    private void explode()
    {
        if (!this.getEntityWorld().isRemote)
        {
            boolean flag = this.getEntityWorld().getGameRules().getBoolean("mobGriefing");
            float f = this.getPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.getEntityWorld().createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius  * f, flag);
            this.setDead();
        }
    }
    
    public boolean hasEnoughSpaceToJump(Entity entityIn)
    {
    	boolean flag = true;
    	for(int i = 0; i < 5; i++)
    	{
    		flag = this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + (double)this.getEyeHeight() + i, this.posZ), new Vec3d(entityIn.posX, entityIn.posY + (double)entityIn.getEyeHeight(), entityIn.posZ), false, true, false) == null;
    	}
        return flag;
    }
    
    public void onUpdate()
    {
        if (this.isEntityAlive() && getAttackTarget() != null && this.hasEnoughSpaceToJump(getAttackTarget()))
        {
            int var1 = this.getCreeperState();
            
            this.setIgnitedTime(0);
            if (var1 > 0 && onGround)
            { 
            	if(getEntityWorld().isRemote)
            	{
            		getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + (rand.nextFloat() - rand.nextFloat()), posY - (rand.nextFloat() - rand.nextFloat()) - 1F, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
            		this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            	}
                motionY = 1.2000000476837158D;
                motionX = (getAttackTarget().posX - posX) / 10D;
                motionZ = (getAttackTarget().posZ - posZ) / 10D;
                setRocket(true);
            }
        }

        super.onUpdate();
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
    	if(this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
    	{
    		return super.getCanSpawnHere();
    	}
    	else
    	{
    		return false;
    	}
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_ROCKETCREEPER;
    }
    
    public void setRocket(boolean rocket)
    {
        this.getDataManager().set(IS_ROCKET, Boolean.valueOf(rocket));
    }
    
    public boolean isRocket()
    {
        return ((Boolean)this.getDataManager().get(IS_ROCKET)).booleanValue();
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Rocket", this.isRocket());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setRocket(compound.getBoolean("Rocket"));
    }

}
