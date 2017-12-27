package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityPrimitiveTNTPrimed extends Entity
{
    private static final DataParameter<Integer> FUSE = EntityDataManager.<Integer>createKey(EntityPrimitiveTNTPrimed.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> STRENGTH = EntityDataManager.<Integer>createKey(EntityPrimitiveTNTPrimed.class, DataSerializers.VARINT);
    
    private EntityLivingBase tntPlacedBy;
    /** How long the fuse is */
    private int fuse;
    private int strength;

    public EntityPrimitiveTNTPrimed(World worldIn)
    {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.fuse = 80;
        this.strength = 1;
        this.setSize(0.98f, 0.98f);
    }

    public EntityPrimitiveTNTPrimed(World worldIn, double x, double y, double z, EntityLivingBase igniter, int power, int fuse)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        float f = (float)(Math.random() * (Math.PI * 2D));
        this.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
        this.setFuse(fuse);
        this.setStrenght(power);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.tntPlacedBy = igniter;
    }

    protected void entityInit()
    {
        this.dataManager.register(FUSE, Integer.valueOf(80));
        this.dataManager.register(STRENGTH, Integer.valueOf(1));
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.hasNoGravity())
        {
            this.motionY -= 0.03999999910593033D;
        }

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        --this.fuse;

        if (this.fuse <= 0)
        {
            this.setDead();

            if (!this.getEntityWorld().isRemote)
            {
                this.explode();
            }
        }
        else
        {
            this.handleWaterMovement();
            this.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    private void explode()
    {
    	boolean flag = true;
    	
    	if(!PrimitiveMobsConfigSpecial.getFestiveCreeperDestruction())
    	{
    		flag = false;
    	}
    	else
    	{
        	flag = this.getEntityWorld().getGameRules().getBoolean("mobGriefing");
    	}

        this.getEntityWorld().createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, strength, flag);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setShort("Fuse", (short)this.getFuse());
        compound.setShort("Strength", (short)this.getStrength());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setFuse(compound.getShort("Fuse"));
        this.setStrenght(compound.getShort("Strength"));
    }

    /**
     * returns null or the entityliving it was placed or ignited by
     */
    public EntityLivingBase getTntPlacedBy()
    {
        return this.tntPlacedBy;
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    public void setFuse(int fuseIn)
    {
        this.dataManager.set(FUSE, Integer.valueOf(fuseIn));
        this.fuse = fuseIn;
    }
    
    public void setStrenght(int strengthIn)
    {
        this.dataManager.set(STRENGTH, Integer.valueOf(strengthIn));
        this.strength = strengthIn;
    }

    public void notifyDataManagerChange(DataParameter<?> key)
    {
        if (FUSE.equals(key))
        {
            this.fuse = this.getFuseDataManager();
        }
        if (STRENGTH.equals(key))
        {
            this.strength = this.getStrengthDataManager();
        }
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuseDataManager()
    {
        return ((Integer)this.dataManager.get(FUSE)).intValue();
    }
    
    /**
     * Gets the fuse from the data manager
     */
    public int getStrengthDataManager()
    {
        return ((Integer)this.dataManager.get(STRENGTH)).intValue();
    }

    public int getFuse()
    {
        return this.fuse;
    }
    
    public int getStrength()
    {
        return this.strength;
    }
}

