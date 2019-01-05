package net.daveyx0.primitivemobs.entity.monster;

import java.util.Collection;
import java.util.Random;

import javax.annotation.Nullable;

import net.daveyx0.multimob.entity.IMultiMob;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityHauntedTool extends EntityMob implements IMultiMob {

    public float floatingb;
    public float floatingc;
    public float floatingd;
    public float floatinge;
    public float floatingh;
    
	public EntityHauntedTool(World worldIn) {
		super(worldIn);
        setSize(0.6F, 1.0F);
        floatingb = 0.0F;
        floatingc = 0.0F;
        floatingh = 1.0F;
		this.inventoryHandsDropChances[0] = 0f;
		this.inventoryHandsDropChances[1] = 0f;
	} 
	
	protected void initEntityAI()
    {
		int prio = 0;
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, new EntityAIAttackMelee(this, 1.3D, false));
        this.tasks.addTask(++prio, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(++prio, new EntityAILookIdle(this));
        int attackPrio = 1;
        this.targetTasks.addTask(++attackPrio, new EntityAIHurtByTarget(this, false));
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	while(this.getHeldItemMainhand().isEmpty() && !getEntityWorld().isRemote)
    	{
    		ItemStack tool = EntityUtil.getCustomLootItem(this, this.getSpawnLootTable(), new ItemStack(Items.WOODEN_SWORD));
    		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, tool);
    		if(tool.isItemStackDamageable())
    		{
    			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)getHealthFromTool(tool));
    			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getDamageFromHeldItem(this));
    			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getSpeedFromHeldItem(this));
    			this.setHealth((float)this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue());
    		}
    		
    		//PrimitiveMobs.PMlogger.info(this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + "");
    	}
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
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
        	getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + (rand.nextFloat()/2 - rand.nextFloat()/2), posY + (rand.nextFloat()/2 - rand.nextFloat()/2) + 1F, posZ + (rand.nextFloat()/2 - rand.nextFloat()/2), 0, 0, 0);
        }
        
        if(this.getHeldItemMainhand().isEmpty())
        {
        	this.setDead();
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected Item getDropItem()
    {
        return this.getHeldItemMainhand().getItem();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return null;
    }
    
    
    @Nullable
    protected ResourceLocation getSpawnLootTable()
    {
        return PrimitiveMobsLootTables.HAUNTEDTOOL_SPAWN;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot()
    {
        return true;
    }
    
    public EntityItem dropItemStack(ItemStack itemIn, float offsetY)
    {
        return this.entityDropItem(itemIn, offsetY);
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        ItemStack stack = this.getHeldItemMainhand();

        if (!stack.isEmpty() && !getEntityWorld().isRemote)
        {
        	if(!PrimitiveMobsConfigSpecial.getHauntedToolDurability())
        	{
        	if (lootingModifier > 3) {lootingModifier = 3;}
        		 
        	Random rand = new Random();
        		
        	int itemDurability = stack.getMaxDamage();
        	int minDurability = itemDurability / 10;
        	int maxDurability = itemDurability / 2;
        	
        	minDurability *= lootingModifier + 1;
        	maxDurability *= lootingModifier + 2;
        	stack.setItemDamage(itemDurability - MathHelper.getInt(rand, minDurability, maxDurability));
        	}
        	this.dropItemStack(stack, 1);
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
        return SoundEvents.ENTITY_ITEM_BREAK;
    }

    protected SoundEvent getDeathSound()
    {
    	return SoundEvents.ENTITY_ITEM_BREAK;
    }
    
    public static float getHealthFromTool(ItemStack tool)
	{
		if(!tool.isEmpty() && tool.getItem().isDamageable())
		{
			float health = tool.getItem().getMaxDamage(tool)/10f;
			if(health > 100f)
			{
				health = 100f;
			}
			return health;
		}

		return 10f;
	}
	
	public static double getDamageFromHeldItem(EntityLiving entity)
	{
		if(!entity.getHeldItemMainhand().isEmpty() && entity.getHeldItemMainhand().getItem().isDamageable());
		{
			Collection<AttributeModifier> modifiers = entity.getHeldItemMainhand().getAttributeModifiers(entity.getSlotForItemStack(entity.getHeldItemMainhand())).get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
			if(modifiers != null && !modifiers.isEmpty())
			{
				Object[] mods = modifiers.toArray(new Object[modifiers.size()]);
				AttributeModifier attribute = (AttributeModifier)mods[0];
				double attackDamage = (attribute.getAmount() / 2D);
				if(attackDamage <= 1.0D)
				{
					attackDamage = 1.0D;
				}
				else if(attackDamage > 8.0D)
				{
					attackDamage = 8.0D;
				}
				
				return attackDamage;
			}
		}

		return 1.0D;
	}
	
	public static double getSpeedFromHeldItem(EntityLiving entity)
	{
		if(!entity.getHeldItemMainhand().isEmpty() && entity.getHeldItemMainhand().getItem().isDamageable())
		{
			Collection<AttributeModifier> modifiers = entity.getHeldItemMainhand().getAttributeModifiers(entity.getSlotForItemStack(entity.getHeldItemMainhand())).get(SharedMonsterAttributes.ATTACK_SPEED.getName());
			if(modifiers != null && !modifiers.isEmpty())
			{
				Object[] mods = modifiers.toArray(new Object[modifiers.size()]);
				AttributeModifier attribute = (AttributeModifier)mods[0];
				double attackSpeed = (0.5D - (attribute.getAmount() * -1 * 0.1D));
				
				if(attackSpeed <= 0.1D)
				{
					attackSpeed = 0.1D;
				}
				else if(attackSpeed > 0.3D)
				{
					attackSpeed = 0.3D;
				}
				return attackSpeed;
			}
		}

		return 0.2D;
	}

    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	if(type == EnumCreatureType.MONSTER){return false;}
    	return super.isCreatureType(type, forSpawnCount);
    }
}
