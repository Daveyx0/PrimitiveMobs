package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import net.daveyx0.multimob.entity.IMultiMob;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.entity.ai.EntityAIFollowerHurtByTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMotherSpider extends EntityPrimitiveSpider implements IMultiMob {

	private final int minFollowers = 3;
	private final int maxFollowers = PrimitiveMobsConfigSpecial.getMaxSpiderFamilySize();
	private EntityLivingBase[] followers = new EntityLivingBase[maxFollowers];
	
	private static final DataParameter<Boolean> IS_ANGRY = EntityDataManager.<Boolean>createKey(EntityMotherSpider.class, DataSerializers.BOOLEAN);
	
	public EntityMotherSpider(World worldIn) {
		super(worldIn);
		
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }
	
    protected void entityInit()
    {
        super.entityInit();
        if(!PrimitiveMobsConfigMobs.enableSpiderFamily)
        {
        	this.setDead();
        }
        this.getDataManager().register(IS_ANGRY, Boolean.valueOf(false));
    }
	
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityPrimitiveSpider.AISpiderAttack(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAIFollowerHurtByTarget(this));
        this.targetTasks.addTask(3, new EntityPrimitiveSpider.AISpiderTarget(this, EntityPlayer.class));
        this.targetTasks.addTask(4, new EntityPrimitiveSpider.AISpiderTarget(this, EntityIronGolem.class));
    }
	
	public void onUpdate()
	{
		super.onUpdate();
		
		if(!this.getPassengers().isEmpty())
		{
	        if (this.getPassengers().get(0) instanceof EntityBabySpider)
	        {
	        	EntityBabySpider baby = (EntityBabySpider)this.getPassengers().get(0);
	        	if(baby != null)
	        	{
	        		baby.getNavigator().setPath(this.getNavigator().getPath(), 1.5D);
	        		baby.getMoveHelper().read(this.getMoveHelper());
	        	}
	        }
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18);
		}
		else
		{
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
		}
		
		if(this.isAngry())
		{
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat(), posZ + (rand.nextFloat() - rand.nextFloat()), 0.0D, 0.0D, 0.0D);
		}
		
	}
	
	@Override
    public boolean isNotColliding()
    {
        return !this.world.containsAnyLiquid(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }

    
    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return PrimitiveMobsSoundEvents.ENTITY_MOTHERSPIDER_SCREECH;
    }
    
    public void addFollower(EntityLivingBase follower)
    {
    	for(int i = 0; i < this.followers.length; i++)
    	{
    		if(followers[i] == null)
    		{
    			followers[i] = follower;
    			return;
    		}
    	}
    }
    
    public EntityLivingBase[] getFollowers()
    {
    	return followers;
    }
    
    public boolean hasFollowers()
    {
    	if(getFollowers() == null){return false;}
    	
    	for(int i = 0; i < this.followers.length; i++)
    	{
    		if(followers[i] != null)
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
   
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));

        if (livingdata == null)
        {
            livingdata = new EntityMotherSpider.GroupData();

            if (this.world.getDifficulty() == EnumDifficulty.HARD && this.world.rand.nextFloat() < 0.1F * difficulty.getClampedAdditionalDifficulty())
            {
                ((EntityMotherSpider.GroupData)livingdata).setRandomEffect(this.world.rand);
            }
        }

        if (livingdata instanceof EntityMotherSpider.GroupData)
        {
            Potion potion = ((EntityMotherSpider.GroupData)livingdata).effect;

            if (potion != null)
            {
                this.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE));
            }
        }
        
        for(int i = 0; i < maxFollowers; i++)
        {
        	if(!this.getEntityWorld().isRemote && (rand.nextInt(2) == 0 || i <= minFollowers))
        	{
        		EntityBabySpider entityBabySpider = new EntityBabySpider(this.getEntityWorld());
        		entityBabySpider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        		entityBabySpider.setTamed(true);
        		entityBabySpider.setOwnerId(this.getUniqueID());
        		this.getEntityWorld().spawnEntity(entityBabySpider);
        		
        		if(!this.isBeingRidden())
        		{
        			entityBabySpider.startRiding(this);
        		}
        	}
        }

        return livingdata;
    }
    
    public void setIsAngry(boolean begging)
    {
        this.getDataManager().set(IS_ANGRY, Boolean.valueOf(begging));
    }
    
    public boolean isAngry()
    {
        return ((Boolean)this.getDataManager().get(IS_ANGRY)).booleanValue();
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        compound.setBoolean("Angry", this.isAngry());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setIsAngry(compound.getBoolean("Angry"));
    }
    
    public void updatePassenger(Entity passenger)
    {
        super.updatePassenger(passenger);
        float f = MathHelper.sin(this.renderYawOffset * 0.017453292F);
        float f1 = MathHelper.cos(this.renderYawOffset * 0.017453292F);
        float f2 = 0.0F;
        
        if(passenger instanceof EntityBabySpider)
        {
        	f2 = 0.25f;
        }
        
        passenger.setPosition(this.posX + (double)(0.1F * f), this.posY + (double)(this.height * 0.5F + f2) + passenger.getYOffset() + 0.0D, this.posZ - (double)(0.1F * f1));

        if (passenger instanceof EntityLivingBase)
        {
            ((EntityLivingBase)passenger).renderYawOffset = this.renderYawOffset;
        }
        

    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_MOTHERSPIDER;
    }
    
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	if(type == EnumCreatureType.MONSTER){return false;}
    	return super.isCreatureType(type, forSpawnCount);
    }
    
}
