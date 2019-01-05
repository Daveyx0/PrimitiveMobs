package net.daveyx0.primitivemobs.entity.passive;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.daveyx0.multimob.entity.IMultiMobPassive;
import net.daveyx0.multimob.entity.ai.EntityAIGrabItemFromFloor;
import net.daveyx0.multimob.entity.ai.EntityAIStealFromPlayer;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityFilchLizard extends EntityCreature implements IMultiMobPassive {

	private int itemChance = 4;
	private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
	
    public ItemStack[] stealItems;
	
	public EntityFilchLizard(World worldIn) {
		super(worldIn);
		this.inventoryHandsDropChances[0] = 0f;
		this.inventoryHandsDropChances[1] = 0f;
		this.setSize(0.6f, 0.5f);
	}
	
    protected void initEntityAI()
    {
    	int prio = 0;
    	stealItems = EntityUtil.getCustomLootItems(this, this.getStealLootTable(), new ItemStack(Items.IRON_INGOT));
    	
        this.tasks.addTask(prio++, new EntityAISwimming(this));
        this.tasks.addTask(prio++, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(prio++, new EntityAIGrabItemFromFloor(this, 1.2D, Sets.newHashSet(stealItems), true));
        this.tasks.addTask(prio++, new EntityAIStealFromPlayer(this, 0.8D, Sets.newHashSet(stealItems), true));
        this.tasks.addTask(prio++, new EntityFilchLizard.AIAvoidWhenNasty(this, EntityPlayer.class, 16.0F, 1.0D, 1.33D));
        this.tasks.addTask(prio++, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(prio++, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(prio++, new EntityAILookIdle(this));
    }
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(!this.getHeldItemMainhand().isEmpty())
        {
        	this.setSize(0.6f, 0.75f);
        }
        else
        {
        	this.setSize(0.6f, 0.3f);
        }
        	
    }
    
    @Nullable
    protected ResourceLocation getSpawnLootTable()
    {
        return PrimitiveMobsLootTables.FILCHLIZARD_SPAWN;
    }

    @Nullable
    protected ResourceLocation getStealLootTable()
    {
        return PrimitiveMobsLootTables.FILCHLIZARD_STEAL;
    }


    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25000000417232513D);
    }
   
    protected void updateAITasks()
    {
        super.updateAITasks();
    }

	
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	int chance = PrimitiveMobsConfigSpecial.getFilchLizardLootChance();
    	
    	if(chance > 0 && (chance >= 100 || (rand.nextInt(100/chance) == 0)))
    	{
    		while(this.getHeldItemMainhand().isEmpty() && !getEntityWorld().isRemote)
    		{
    			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EntityUtil.getCustomLootItem(this, this.getSpawnLootTable(), new ItemStack(Items.IRON_INGOT)));
    		}
    	}
    		
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public EntityItem dropItemStack(ItemStack itemIn, float offsetY)
    {
        return this.entityDropItem(itemIn, offsetY);
    }
    
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
	    if (par1DamageSource.getTrueSource() != null)
	    {
	    	this.setLastAttackedEntity(par1DamageSource.getTrueSource());
	    }
	    
        ItemStack stack = this.getHeldItemMainhand();

        if (!stack.isEmpty() && !getEntityWorld().isRemote)
        {
            ItemStack newStack = stack.copy();
            this.dropItemStack(newStack, 1);
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

	    return super.attackEntityFrom(par1DamageSource, par2);
	}
    
    static class AIAvoidWhenNasty extends EntityAIAvoidEntity
    {
        public AIAvoidWhenNasty(EntityCreature theEntityIn, Class classToAvoidIn, float avoidDistanceIn, double farSpeedIn,
				double nearSpeedIn) {
			super(theEntityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
		}

		/**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
        	return !entity.getHeldItemMainhand().isEmpty() && super.shouldExecute();
        }	
    }
    
    protected Block spawnableBlock = Blocks.SAND;
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        return this.getEntityWorld().getBlockState(blockpos.down()).getBlock() == this.spawnableBlock && this.getEntityWorld().getLight(blockpos) > 8 && super.getCanSpawnHere();
    }
    
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	return super.isCreatureType(type, forSpawnCount);
    }
}
