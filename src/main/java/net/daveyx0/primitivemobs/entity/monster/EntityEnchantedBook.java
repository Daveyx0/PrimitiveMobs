package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityEnchantedBook extends EntityMob {

    public float floatingb;
    public float floatingc;
    public float floatingd;
    public float floatinge;
    public float floatingh;
    
	public EntityEnchantedBook(World worldIn) {
		super(worldIn);
        setSize(0.5F, 1.0F);
        floatingb = 0.0F;
        floatingc = 0.0F;
        floatingh = 1.0F;
	}
	
	protected void initEntityAI()
    {
		int prio = 0;
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(++prio, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(++prio, new EntityAILookIdle(this));
        int attackPrio = 1;
        this.targetTasks.addTask(++attackPrio, new EntityAIHurtByTarget(this, true));
    }
	
    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return false;
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000298023224D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public void onLivingUpdate()
    {
    	floatinge = floatingb;
        floatingd = floatingc;
        floatingc = (float)((double)floatingc + 2 * 0.8D);

        if (floatingc < 0.0F)
        {
            floatingc = 0.0F;
        }

        if (floatingc > 0.2F)
        {
            floatingc = 0.2F;
        }

        if (floatingh < 0.2F)
        {
            floatingh = 0.2F;
        }

        floatingh = (float)((double)floatingh * 0.9D);
        floatingb += floatingh * 1.0F;

        if(getEntityWorld().isRemote)
        {
        	getEntityWorld().spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, posX + (rand.nextFloat() - rand.nextFloat()), posY + (rand.nextFloat() - rand.nextFloat()) + 1F, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
        }
        
        super.onLivingUpdate();
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return null;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot()
    {
        return true;
    }
    
    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        if (!getEntityWorld().isRemote)
        {
        	int chance = rand.nextInt(2);
        	
        	if(chance == 0)
        	{
    		Enchantment enchantment = Enchantment.REGISTRY.getRandomObject(getEntityWorld().rand);
    		int maxPower = enchantment.getMaxLevel();
    		int randomPower = 1 + rand.nextInt(maxPower);
    		
    		EntityItem drop = entityDropItem(new ItemStack(Items.ENCHANTED_BOOK), 1);
        	ItemStack stack = drop.getItem();
        	if(stack != null && randomPower > 0 && enchantment != null)
        	{
        		ItemEnchantedBook book = (ItemEnchantedBook)stack.getItem();
        		book.addEnchantment(stack, new EnchantmentData(enchantment, randomPower));
        		stack = new ItemStack(book, 1);
        	}
        	}
        	else
        	{
        		entityDropItem(new ItemStack(Items.BOOK), 1);
        	}
        }
    }
    

    public boolean isOnLadder()
    {
        return collidedHorizontally;
    }
    
    public void jump()
    {	
    }
    
    protected SoundEvent getFallSound(int heightIn)
    {
        return null;
    }
    
    public void fall(float distance, float damageMultiplier)
    {
    }
    
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
    }
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    protected SoundEvent getHurtSound()
    {
        return SoundEvents.BLOCK_CLOTH_HIT;
    }

    protected SoundEvent getDeathSound()
    {
    	return SoundEvents.BLOCK_CLOTH_BREAK;
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.posY < 40D;
    }
  

}
