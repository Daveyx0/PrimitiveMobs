package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityHauntedTool extends EntityMob {

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
        this.tasks.addTask(++prio, new EntityAIAttackMelee(this, 1.0D, false));
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
    	while(this.getHeldItemMainhand() == null && !getEntityWorld().isRemote)
    	{
    		ItemStack tool = HauntedToolLoot.getRandomLootItem(this.rand);
    		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, tool);
    		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)HauntedToolLoot.getHealthFromTool(tool));
    		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(HauntedToolLoot.getDamageFromHeldItem(this));
    		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(HauntedToolLoot.getSpeedFromHeldItem(this));
    		this.setHealth((float)this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue());
    		
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
        
        if(this.getHeldItemMainhand() == null)
        {
        	this.setDead();
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected Item getDropItem()
    {
        return this.getHeldItemMainhand() != null ? this.getHeldItemMainhand().getItem() : null;
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

        if (stack != null && !getEntityWorld().isRemote)
        {
            int i = 1;

            if (lootingModifier > 0)
            {
                i += this.rand.nextInt(lootingModifier + 1);
            }

            for (int j = 0; j < i; ++j)
            {
            	ItemStack newStack = new ItemStack(stack.getItem(), 1, stack.getMetadata());
            	
                this.dropItemStack(newStack, 1);
            }
        }
    }
    

    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
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

}
