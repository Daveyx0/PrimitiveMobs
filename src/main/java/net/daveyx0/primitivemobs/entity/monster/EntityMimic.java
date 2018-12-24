package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.daveyx0.multimob.message.MMMessageRegistry;
import net.daveyx0.multimob.message.MessageMMParticle;
import net.daveyx0.multimob.util.NBTUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityMimic extends EntityMob {

	private static final DataParameter<Optional<IBlockState>> CHEST = EntityDataManager.<Optional<IBlockState>>createKey(EntityMimic.class, DataSerializers.OPTIONAL_BLOCK_STATE);
	
    public float nommingb;
    public float nommingc;
    public float nommingd;
    public float nomminge;
    public float nommingh;
    public float rotation;
    
    private boolean explode;
    int explosionTimer = 0;
    
    
	public EntityMimic(World worldIn) {
		super(worldIn);
		setSize(0.9F, 0.9F);
		explode = false;
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.8D);
    }
	
	protected void initEntityAI()
    {
		int prio = 0;
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, new EntityAIAttackMelee(this, 1.15D, false));
        this.tasks.addTask(++prio, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(++prio, new EntityAILookIdle(this));
        int attackPrio = 1;
        this.targetTasks.addTask(++attackPrio, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(++attackPrio, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }	
	
	public void setToExplode()
	{
		explode = true;
	}
	
    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return false;
    }
    
    public void onLivingUpdate()
    {
        if (onGround && (motionX > 0.05D || motionZ > 0.05D || motionX < -0.05D || motionZ < -0.05D))
        {
            motionY = 0.4D;
        }
        
    	if(collidedHorizontally)
    	{
    		motionY = 0.5D;
    	}

    	nomminge = nommingb;
        nommingd = nommingc;

        nommingc = (float)((double)nommingc + 4 * 0.8D);

        if (nommingc < 0.0F)
        {
            nommingc = 0.0F;
        }

        if (nommingc > 0.2F)
        {
            nommingc = 0.2F;
        }

        if (nommingh < 0.2F)
        {
            nommingh = 0.2F;
        }

        nommingh = (float)((double)nommingh * 0.9D);
        nommingb += nommingh * 2.0F;
        rotation =  -0.7853982F;
        
       	if(rand.nextInt(80) == 0)
        {
             float f = 0.01745278F;
             double d = posX - Math.sin(rotationYaw * f) / 3D;
             double d1 = posY + rand.nextDouble() / 3D;
             double d2 = posZ + Math.cos(rotationYaw * f) / 3D;
        	 for (int j = 0; j < 12; j++)
          {
        		 this.getEntityWorld().spawnParticle(EnumParticleTypes.WATER_SPLASH, d, d1 + 0.29999999999999999D, d2, 0.0D, 0.0D, 0.0D);
          }
        }
       	
       	if(explode)
       	{
       	   this.setAIMoveSpeed(0);
       	   MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), 10, (float)this.posX + (rand.nextFloat() - rand.nextFloat()), (float)this.posY + 1f + (rand.nextFloat() - rand.nextFloat()) , (float)this.posZ + (rand.nextFloat() - rand.nextFloat()), 0D,0D,0D, 0));
     	   if(explosionTimer == 0)
     	   {
     		   this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1, 1);
     	   }
     	   else if(explosionTimer >= 40 && !this.getEntityWorld().isRemote)
     	   {
     		   this.getEntityWorld().createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
     		   this.setDead();
     	   }
     	   explosionTimer ++;
       	}
       	super.onLivingUpdate();
    }
    
    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return SoundEvents.BLOCK_CHEST_OPEN;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.BLOCK_CHEST_CLOSE;
    }
    
    protected void entityInit()
    {
        Optional<IBlockState> chest = Optional.of(Blocks.CHEST.getDefaultState());
        
        this.getDataManager().register(CHEST, chest);
        super.entityInit();
    }
    
    public void setChest(IBlockState chest)
    {
    	Optional<IBlockState> newChest = Optional.of(chest);
        this.getDataManager().set(CHEST, newChest);
    }
    
    public IBlockState getChest()
    {
    	Optional<IBlockState> state = (Optional<IBlockState>)this.getDataManager().get(CHEST);
    	return state != null ? state.get() : Blocks.CHEST.getDefaultState();
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_MIMIC;
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        NBTUtil.setBlockStateToNBT(this.getChest(), "chestState", compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        this.setChest(NBTUtil.getBlockStateFromNBT("chestState", compound));
    }

}
