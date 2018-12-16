package net.daveyx0.primitivemobs.entity.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.daveyx0.multimob.util.NBTUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrownBlock extends Entity
{
    private EntityLivingBase owner;
    /** How long the fuse is */

    private IBlockState fallTile;
    public NBTTagCompound tileEntityData;
    protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.<BlockPos>createKey(EntityFallingBlock.class, DataSerializers.BLOCK_POS);
    
    public EntityThrownBlock(World worldIn)
    {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(1.0f,1.0f);
    }

    public EntityThrownBlock(World worldIn, double x, double y, double z, EntityLivingBase igniter, BlockPos blockPos)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        float f = (float)(Math.random() * (Math.PI * 2D));
        this.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.owner = igniter;
        this.setOrigin(blockPos);
    }

    protected void entityInit()
    {
        this.dataManager.register(ORIGIN, BlockPos.ORIGIN);
    }
    
    public void setOrigin(BlockPos p_184530_1_)
    {
        this.dataManager.set(ORIGIN, p_184530_1_);
    }

    public BlockPos getOrigin()
    {
        return (BlockPos)this.dataManager.get(ORIGIN);
    }
    
    public void fall(float distance, float damageMultiplier)
    {
                List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(1.2, 1.2, 1.2)));

                for (Entity entity : list)
                {
                	if(this.owner == null)
                	{
                		entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this), 10F);
                	}
                	else
                	{
                		entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.owner), 10F);
                	}   
                }
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
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        NBTUtil.setBlockPosToNBT(this.getOrigin(), "BlockPos", compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
    	this.setOrigin(NBTUtil.getBlockPosFromNBT("BlockPos", compound));
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
            
            for(int i = 0; i < 36; i++)
            {
                this.getEntityWorld().spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (rand.nextFloat() - rand.nextFloat()), this.posY + 0.5D, this.posZ + (rand.nextFloat() - rand.nextFloat()), (rand.nextFloat() - rand.nextFloat()), 1.0D, (rand.nextFloat() - rand.nextFloat()), Block.getIdFromBlock(this.world.getBlockState(this.getOrigin()).getBlock()));
            }
            
            this.setDead();
        }
    }

    private void explode()
    {
    	boolean flag = true;
    	

       // this.getEntityWorld().createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, 1, flag);
    }
    /**
     * returns null or the entityliving it was placed or ignited by
     */
    public EntityLivingBase getOwner()
    {
        return this.owner;
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }
    
    /**
     * Return whether this entity should be rendered as on fire.
     */
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }
    
    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public World getWorldObj()
    {
        return this.world;
    }

}

