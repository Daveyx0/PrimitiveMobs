package net.daveyx0.primitivemobs.entity.monster;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPrimitiveSpider extends EntityPrimitiveTameableMob {

	 private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityPrimitiveSpider.class, DataSerializers.BYTE);

	    public EntityPrimitiveSpider(World worldIn)
	    {
	        super(worldIn);
	        this.setSize(1.4F, 0.9F);
	    }

	    public static void registerFixesSpider(DataFixer fixer)
	    {
	        EntityLiving.registerFixesMob(fixer, EntitySpider.class);
	    }

	    /**
	     * Returns the Y offset from the entity's position for any entity riding this one.
	     */
	    public double getMountedYOffset()
	    {
	        return (double)(this.height * 0.5F);
	    }

	    /**
	     * Returns new PathNavigateGround instance
	     */
	    protected PathNavigate createNavigator(World worldIn)
	    {
	        return new PathNavigateClimber(this, worldIn);
	    }

	    protected void entityInit()
	    {
	        super.entityInit();
	        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
	    }

	    /**
	     * Called to update the entity's position/logic.
	     */
	    public void onUpdate()
	    {
	        super.onUpdate();

	        if (!this.world.isRemote)
	        {
	            this.setBesideClimbableBlock(this.isCollidedHorizontally);
	        }
	    }

	    protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
	    }

	    protected SoundEvent getAmbientSound()
	    {
	        return SoundEvents.ENTITY_SPIDER_AMBIENT;
	    }

	    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
	    {
	        return SoundEvents.ENTITY_SPIDER_HURT;
	    }

	    protected SoundEvent getDeathSound()
	    {
	        return SoundEvents.ENTITY_SPIDER_DEATH;
	    }

	    protected void playStepSound(BlockPos pos, Block blockIn)
	    {
	        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
	    }

	    @Nullable
	    protected ResourceLocation getLootTable()
	    {
	        return LootTableList.ENTITIES_SPIDER;
	    }

	    /**
	     * returns true if this entity is by a ladder, false otherwise
	     */
	    public boolean isOnLadder()
	    {
	        return this.isBesideClimbableBlock();
	    }

	    /**
	     * Sets the Entity inside a web block.
	     */
	    public void setInWeb()
	    {
	    }

	    /**
	     * Get this Entity's EnumCreatureAttribute
	     */
	    public EnumCreatureAttribute getCreatureAttribute()
	    {
	        return EnumCreatureAttribute.ARTHROPOD;
	    }

	    public boolean isPotionApplicable(PotionEffect potioneffectIn)
	    {
	        return potioneffectIn.getPotion() == MobEffects.POISON ? false : super.isPotionApplicable(potioneffectIn);
	    }

	    /**
	     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	     * setBesideClimableBlock.
	     */
	    public boolean isBesideClimbableBlock()
	    {
	        return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
	    }

	    /**
	     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	     * false.
	     */
	    public void setBesideClimbableBlock(boolean climbing)
	    {
	        byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

	        if (climbing)
	        {
	            b0 = (byte)(b0 | 1);
	        }
	        else
	        {
	            b0 = (byte)(b0 & -2);
	        }

	        this.dataManager.set(CLIMBING, Byte.valueOf(b0));
	    }

	    /**
	     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
	     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	     */
	    @Nullable
	    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	    {
	        livingdata = super.onInitialSpawn(difficulty, livingdata);

	        if (this.world.rand.nextInt(100) == 0)
	        {
	            EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
	            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
	            entityskeleton.onInitialSpawn(difficulty, (IEntityLivingData)null);
	            this.world.spawnEntity(entityskeleton);
	            entityskeleton.startRiding(this);
	        }

	        if (livingdata == null)
	        {
	            livingdata = new EntitySpider.GroupData();

	            if (this.world.getDifficulty() == EnumDifficulty.HARD && this.world.rand.nextFloat() < 0.1F * difficulty.getClampedAdditionalDifficulty())
	            {
	                ((EntitySpider.GroupData)livingdata).setRandomEffect(this.world.rand);
	            }
	        }

	        if (livingdata instanceof EntitySpider.GroupData)
	        {
	            Potion potion = ((EntitySpider.GroupData)livingdata).effect;

	            if (potion != null)
	            {
	                this.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE));
	            }
	        }

	        return livingdata;
	    }

	    public float getEyeHeight()
	    {
	        return 0.65F;
	    }

	    static class AISpiderAttack extends EntityAIAttackMelee
	        {
	    		EntityPrimitiveSpider spider;
	            public AISpiderAttack(EntityPrimitiveSpider spider)
	            {
	                super(spider, 1.0D, true);
	                this.spider = spider;
	            }

	            /**
	             * Returns whether an in-progress EntityAIBase should continue executing
	             */
	            public boolean shouldContinueExecuting()
	            {
	                float f = this.attacker.getBrightness();

	                if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0)
	                {
	                    this.attacker.setAttackTarget((EntityLivingBase)null);
	                    return false;
	                }
	                else
	                {
	                    return super.shouldContinueExecuting();
	                }
	            }

	            protected double getAttackReachSqr(EntityLivingBase attackTarget)
	            {
	            	if(this.spider instanceof EntityBabySpider)
	            	{
	            		return (double)(2.0F + attackTarget.width);
	            	}
	                return (double)(4.0F + attackTarget.width);
	            }
	        }

	    static class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
	        {
	            public AISpiderTarget(EntityPrimitiveSpider spider, Class<T> classTarget)
	            {
	                super(spider, classTarget, true);
	            }

	            /**
	             * Returns whether the EntityAIBase should begin execution.
	             */
	            public boolean shouldExecute()
	            {
	                float f = this.taskOwner.getBrightness();
	                return f >= 0.5F ? false : super.shouldExecute();
	            }
	        }

	    public static class GroupData implements IEntityLivingData
	        {
	            public Potion effect;

	            public void setRandomEffect(Random rand)
	            {
	                int i = rand.nextInt(5);

	                if (i <= 1)
	                {
	                    this.effect = MobEffects.SPEED;
	                }
	                else if (i <= 2)
	                {
	                    this.effect = MobEffects.STRENGTH;
	                }
	                else if (i <= 3)
	                {
	                    this.effect = MobEffects.REGENERATION;
	                }
	                else if (i <= 4)
	                {
	                    this.effect = MobEffects.INVISIBILITY;
	                }
	            }
	        }
	}
