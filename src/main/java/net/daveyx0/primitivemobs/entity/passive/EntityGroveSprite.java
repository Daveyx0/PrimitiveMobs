package net.daveyx0.primitivemobs.entity.passive;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import net.daveyx0.multimob.entity.ai.EntityAITemptItemStack;
import net.daveyx0.multimob.message.MMMessageRegistry;
import net.daveyx0.multimob.message.MessageMMParticle;
import net.daveyx0.multimob.util.ColorUtil;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.multimob.util.NBTUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.entity.ai.EntityAIGroveSpriteTempt;
import net.daveyx0.primitivemobs.item.ItemGroveSpriteSap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGroveSprite extends EntityCreature 
{
	private float LeavesR = 0f;
	private float LeavesG = 0f;
	private float LeavesB = 0f;
	private float LogR = 0f;
	private float LogG = 0f;
	private float LogB = 0f;
	private float LogTopR = 0f;
	private float LogTopG = 0f;
	private float LogTopB = 0f;
	private boolean changedColor = false;
	
	private static final DataParameter<Boolean> IS_CINDER = EntityDataManager.<Boolean>createKey(EntityGroveSprite.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SAPLING_AMOUNT = EntityDataManager.<Integer>createKey(EntityGroveSprite.class, DataSerializers.VARINT);
	private static final DataParameter<Optional<IBlockState>> LEAVES = EntityDataManager.<Optional<IBlockState>>createKey(EntityGroveSprite.class, DataSerializers.OPTIONAL_BLOCK_STATE);
	private static final DataParameter<Optional<IBlockState>> LOG = EntityDataManager.<Optional<IBlockState>>createKey(EntityGroveSprite.class, DataSerializers.OPTIONAL_BLOCK_STATE);
	private static final DataParameter<BlockPos> LEAVES_POS = EntityDataManager.<BlockPos>createKey(EntityGroveSprite.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<Boolean> IS_BEGGING = EntityDataManager.<Boolean>createKey(EntityGroveSprite.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CAN_DESPAWN = EntityDataManager.<Boolean>createKey(EntityGroveSprite.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SAPLING_TIMER = EntityDataManager.<Integer>createKey(EntityGroveSprite.class, DataSerializers.VARINT);
	
	public EntityGroveSprite(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 1.0f);
		this.inventoryHandsDropChances[0] = 1f;
		this.inventoryHandsDropChances[1] = 1f;
	}

	protected void initEntityAI()
    {
		int prio = 0;
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(++prio, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(++prio, new AIManageSaplings(this));
        this.tasks.addTask(++prio, new EntityAIGroveSpriteTempt(this, 1.1D, false, Sets.newHashSet(new ItemStack[] {this.getHeldItemMainhand(), new ItemStack(Items.DYE, 1, 15)})));
        this.tasks.addTask(++prio, new EntityAILookIdle(this));
        int attackPrio = 1;
        this.targetTasks.addTask(++attackPrio, new EntityAIHurtByTarget(this, false));
    }
	
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	if(!getEntityWorld().isRemote)
    	{
    		determineLogAndLeaves();

    		ItemStack sapling = new ItemStack(this.getLeaves().getBlock().getItemDropped(this.getLeaves(), rand, 100), 1, this.getLeaves().getBlock().damageDropped(this.getLeaves()));
			if(!sapling.isEmpty())
			{
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, sapling);
			}
		
			this.setSaplingAmount(1 + rand.nextInt(4));		
			this.setSaplingTimer(rand.nextInt(1000) + 1000);
    	}
		
        return super.onInitialSpawn(difficulty, livingdata);
    }
	
    private void determineLogAndLeaves() {

    	Object[] tree = EntityUtil.searchTree(this, 10);
    	
    	if(tree != null && tree.length > 0)
    	{
    		this.setLog((IBlockState)tree[0]);
    		this.setLeaves((IBlockState)tree[1]);
    		this.setLeavesPos((BlockPos)tree[2]);
    	}
	}
    
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000298023224D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }
    
    protected void entityInit()
    {
        Optional<IBlockState> leaves = Optional.of(Blocks.LEAVES.getDefaultState());
        Optional<IBlockState> log = Optional.of(Blocks.LOG.getDefaultState());
        
        this.getDataManager().register(IS_CINDER, Boolean.valueOf(false));
        this.getDataManager().register(SAPLING_AMOUNT, Integer.valueOf(0));
        this.getDataManager().register(LEAVES, leaves);
        this.getDataManager().register(LOG, log);
        this.getDataManager().register(LEAVES_POS, new BlockPos(0,0,0));
        this.getDataManager().register(CAN_DESPAWN, Boolean.valueOf(true));
        this.getDataManager().register(IS_BEGGING, Boolean.valueOf(false));
        this.getDataManager().register(SAPLING_TIMER, Integer.valueOf(0));
        super.entityInit();
    }
    
   	public void onUpdate() 
   	{	
   		if(this.getFlag(0))
   		{
   			this.setCinderSprite(true);
   			changedColor = false;
   		}
   		
   		if(this.isCinderSprite())
   		{
   			world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY + this.height + 0.2F, this.posZ, 0, 0, 0);
   		}
   		else
   		{
   			this.setSaplingTimer(this.getSaplingTimer() - 1);
   			
   			if(this.getSaplingTimer() <= 0)
   			{
   				this.setSaplingAmount(this.getSaplingAmount() + 1);
   	            for (int i = 0; i < 8; i++)
   	            {
   	            	world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
   	            }
   	            this.setSaplingTimer(rand.nextInt(1000) + 1000);
   			}
   		}
   		
   		if(getEntityWorld().isRemote && !changedColor)
   		{
   			changedColor = true;
   			if(!this.isCinderSprite())
   			{
   	   			setLeavesRGB(getColor(this.getEntityWorld(), this.getLeaves(), this.getLeavesPos(), null));
   	   			setLogRGB(getColor(this.getEntityWorld(), this.getLog(), null, EnumFacing.WEST));
   	   			setLogTopRGB(getColor(this.getEntityWorld(), this.getLog(), null, null));
   			}
   			else
   			{
   				setLeavesRGB(new int[]{177, 100, 0});
   				setLogRGB(new int[]{90, 86, 80});
   				setLogTopRGB(new int[]{102, 98, 94});
   	   			this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.TORCH));
   				this.isImmuneToFire = true;
   			}

   		}
     
   		super.onUpdate();
   	}
   	
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if((source == DamageSource.ON_FIRE || source == DamageSource.LAVA || source == DamageSource.IN_WALL)  && this.isCinderSprite())
    	{
    		return false;
    	}
        return super.attackEntityFrom(source, amount);
    }
   	
    public boolean attackEntityAsMob(Entity entityIn)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            if (this.isCinderSprite())
            {
                entityIn.setFire(8);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
    
    public static int[] getColor(World worldIn, IBlockState state, @Nullable BlockPos pos, @Nullable EnumFacing face)
	{	
		if(state.getBlock() != Blocks.AIR)
		{
			int[] newColor = new int[3];
			
			if(face != null)
			{
				newColor =	ColorUtil.getBlockStateColor(state, pos, worldIn, face);
			}
			else
			{
				newColor =	ColorUtil.getBlockStateColor(state, pos, worldIn);
			}
			
			if(newColor != null)
			{
				return newColor;
			}
		}
		
		return new int[]{255,255,255};
	}
    
    public EntityItem dropItemStack(ItemStack itemIn, float offsetY)
    {
        return this.entityDropItem(itemIn, offsetY);
    }
    
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == this.getHeldItemMainhand().getItem() && itemstack.getMetadata() == this.getHeldItemMainhand().getMetadata();
        
        if(this.isCinderSprite()) {return false;}
        
        if (flag)
        {
        	this.consumeItemFromStack(player, itemstack);
        	if(((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue())
        	{
            	this.setCanDespawn(false);
        	}
        	this.setSaplingAmount(this.getSaplingAmount() + 1);
            for (int i = 0; i < 8; i++)
            {
            	world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
            }
            this.playSound(PrimitiveMobsSoundEvents.ENTITY_GROVESPRITE_THANKS, 1, 1);
            return true;
        }
        else if(itemstack.getItem() == Items.WHEAT_SEEDS)
        {
        	this.consumeItemFromStack(player, itemstack);
            for (int i = 0; i < 8; i++)
            {
            	world.spawnParticle(EnumParticleTypes.HEART, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
            }
            this.playSound(PrimitiveMobsSoundEvents.ENTITY_GROVESPRITE_THANKS, 1, 1);
        	this.setHealth(this.getMaxHealth());
        	return true;
        }
        else if(itemstack.getItem() == Items.DYE && itemstack.getMetadata() == 15  && this.getSaplingAmount() > 0)
        {
        	if (this.getLog() != null)
            {
        		this.consumeItemFromStack(player, itemstack);
                for (int i = 0; i < 8; i++)
                {
                	world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
                }
                this.setSaplingAmount(this.getSaplingAmount() - 1);
                this.playSound(PrimitiveMobsSoundEvents.ENTITY_GROVESPRITE_THANKS, 1, 1);
                if(!this.world.isRemote)
                {
                	ItemStack sap = new ItemStack(PrimitiveMobsItems.WONDER_SAP, rand.nextInt(4) + 1);
            		sap = ItemGroveSpriteSap.onGroveSpriteDrop(this.world, this, sap);
            		EntityItem item = this.entityDropItem(sap , 1);
                }
            }
        	return true;
        }
        else
        {
            return super.processInteract(player, hand);
        }
    }
    
    /**
     * Decreases ItemStack size by one
     */
    protected void consumeItemFromStack(EntityPlayer player, ItemStack stack)
    {
        if (!player.capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
    	if(this.isCinderSprite())
    	{
        	ItemStack coal = new ItemStack(Items.COAL, rand.nextInt(5) + lootingModifier);
    		EntityItem item = this.entityDropItem(coal , 1);
    	}
    	else if (this.getLog() != null)
        {
        	ItemStack sap = new ItemStack(PrimitiveMobsItems.WONDER_SAP);
    		sap = ItemGroveSpriteSap.onGroveSpriteDrop(this.world, this, sap);
    		EntityItem item = this.entityDropItem(sap , 1);
        }
    }
    

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return this.isCinderSprite() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
    }
    
    public float[] getLeavesRGB()
   	{
   		return new float[]{LeavesR,LeavesG,LeavesB};
   	}
   	
   	public void setLeavesRGB(int[] RGB)
   	{
   		LeavesR = (float)RGB[0];
   		LeavesG = (float)RGB[1];
   		LeavesB = (float)RGB[2];
   	}
   	
   	public float[] getLogRGB()
   	{
   		return new float[]{LogR,LogG,LogB};
   	}
   	
   	public void setLogRGB(int[] RGB)
   	{
   		LogR = (float)RGB[0];
   		LogG = (float)RGB[1];
   		LogB = (float)RGB[2];
   	}
   	
   	public float[] getLogTopRGB()
   	{
   		return new float[]{LogTopR,LogTopG,LogTopB};
   	}
   	
   	public void setLogTopRGB(int[] RGB)
   	{
   		LogTopR = (float)RGB[0];
   		LogTopG = (float)RGB[1];
   		LogTopB = (float)RGB[2];
   	}
    
    public void setCinderSprite(boolean cinder)
    {
        this.getDataManager().set(IS_CINDER, Boolean.valueOf(cinder));
    }
    
    public boolean isCinderSprite()
    {
        return ((Boolean)this.getDataManager().get(IS_CINDER)).booleanValue();
    }
    
    public void setSaplingAmount(int saplings)
    {
        this.getDataManager().set(SAPLING_AMOUNT, Integer.valueOf(saplings));
    }
    
    public int getSaplingAmount()
    {
        return ((Integer)this.getDataManager().get(SAPLING_AMOUNT)).intValue();
    }
    
    public void setSaplingTimer(int timer)
    {
        this.getDataManager().set(SAPLING_TIMER, Integer.valueOf(timer));
    }
    
    public int getSaplingTimer()
    {
        return ((Integer)this.getDataManager().get(SAPLING_TIMER)).intValue();
    }
    
    public void setLeaves(IBlockState leaves)
    {
    	Optional<IBlockState> newLeaves = Optional.of(leaves);
        this.getDataManager().set(LEAVES, newLeaves);
    }
    
    public IBlockState getLeaves()
    {
    	Optional<IBlockState> state = (Optional<IBlockState>)this.getDataManager().get(LEAVES);
    	return state != null && state.isPresent() ? state.get() : Blocks.LEAVES.getDefaultState();
    }
    
    public void setLog(IBlockState log)
    {
    	Optional<IBlockState> newLog = Optional.of(log);
        this.getDataManager().set(LOG, newLog);
    }
    
    public IBlockState getLog()
    {
    	Optional<IBlockState> state = (Optional<IBlockState>)this.getDataManager().get(LOG);
    	return state != null && state.isPresent() ? state.get() : Blocks.LOG.getDefaultState();
    }
    
    public void setLeavesPos(BlockPos pos)
    {
        this.getDataManager().set(LEAVES_POS, pos);
    }
    
    public BlockPos getLeavesPos()
    {
    	BlockPos pos = (BlockPos)this.getDataManager().get(LEAVES_POS);
    	return pos != null ? pos : new BlockPos(0,0,0);
    }

    protected SoundEvent getAmbientSound()
    {
        return PrimitiveMobsSoundEvents.ENTITY_GROVESPRITE_IDLE;
    }
    
    public void setIsBegging(boolean begging)
    {
        this.getDataManager().set(IS_BEGGING, Boolean.valueOf(begging));
    }
    
    public boolean isBegging()
    {
        return ((Boolean)this.getDataManager().get(IS_BEGGING)).booleanValue();
    }
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
	public void setCanDespawn(boolean b) {

		 this.dataManager.set(CAN_DESPAWN, b);
	}
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        NBTUtil.setBlockPosToNBT(this.getLeavesPos(), "LeavesPos", compound);
        NBTUtil.setBlockStateToNBT(this.getLeaves(), "LeavesState", compound);
        NBTUtil.setBlockStateToNBT(this.getLog(), "LogState", compound);

        compound.setInteger("SaplingAmount", this.getSaplingAmount());
        compound.setInteger("SaplingTimer", this.getSaplingTimer());
        compound.setBoolean("isCinder", this.isCinderSprite());
        compound.setBoolean("canDespawn", ((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        this.setLeavesPos(NBTUtil.getBlockPosFromNBT("LeavesPos", compound));
        this.setLeaves(NBTUtil.getBlockStateFromNBT("LeavesState", compound));
        this.setLog(NBTUtil.getBlockStateFromNBT("LogState", compound));
        
        this.setSaplingAmount(compound.getInteger("SaplingAmount"));
        this.setSaplingTimer(compound.getInteger("SaplingTimer"));
        this.setCinderSprite(compound.getBoolean("isCinder"));
        this.setCanDespawn(compound.getBoolean("canDespawn"));
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
    	return ((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue();
    }
    
    protected Block spawnableBlock = Blocks.GRASS;
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
    
    static class AIManageSaplings extends EntityAIMoveToBlock
    {
        private final EntityGroveSprite sprite;
        private int manageDelay = 0;
        boolean isNearGoal = false;
        int type = 0;
        private int timeoutCounter;
        boolean placeSapling = true;
        boolean hasChosen = false;

        public AIManageSaplings(EntityGroveSprite sprite)
        {
            super(sprite, 0.699999988079071D, 16);
            this.sprite = sprite;
            this.manageDelay = sprite.world.rand.nextInt(100) + 100;
        }
        
        public boolean shouldExecute()
        {
        	manageDelay--;
           return searchForDestination() && manageDelay <= 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            super.updateTask();
            this.sprite.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY()), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.sprite.getVerticalFaceSpeed());
            if (this.sprite.getDistanceSqToCenter(this.destinationBlock.up()) < 12D)
            {
            	this.isNearGoal = true;
            }
            
            if (this.getIsAboveDestination())
            {
            	if(type == 0)
            	{
                	this.manageDelay = sprite.world.rand.nextInt(200) + 200;
                    ItemDye.applyBonemeal(new ItemStack(Items.DYE, 1, 15), this.sprite.world, destinationBlock);
                    MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), 10, destinationBlock.getX() + 0.5F + (sprite.rand.nextFloat() - sprite.rand.nextFloat()), destinationBlock.getY() + 0.5F, destinationBlock.getZ() + 0.5F + (sprite.rand.nextFloat() - sprite.rand.nextFloat()), 0D,0D,0D, 0));
                    isNearGoal =  false;
            	}
            	else
            	{
            		this.manageDelay = sprite.world.rand.nextInt(300) + 300;
                    ItemStack heldItem = this.sprite.getHeldItemMainhand();
                    
                    if (heldItem != null && !heldItem.isEmpty() && heldItem.getItem() instanceof ItemBlock)
                    {
                        ItemBlock item = (ItemBlock)heldItem.getItem();
                        item.placeBlockAt(heldItem, null, sprite.world, destinationBlock, EnumFacing.NORTH, 0, 0, 0, item.getBlock().getStateFromMeta(this.sprite.getHeldItemMainhand().getMetadata()));
                        sprite.world.playSound((double)destinationBlock.getX(), (double)destinationBlock.getY(), (double)destinationBlock.getZ(), SoundEvents.BLOCK_GLASS_PLACE, 
                        		SoundCategory.BLOCKS, 1f, 1f, false);
                        this.sprite.setSaplingAmount(this.sprite.getSaplingAmount() - 1);
                    }
            		isNearGoal =  false;
            	}
            	hasChosen = false;
            	placeSapling = true;
            }

        }
        

        private boolean searchForDestination()
        {
            int i = 16;
            int j = 1;
            BlockPos blockpos = new BlockPos(this.sprite);
            
        	if(this.sprite.getSaplingAmount() <= 0 || !hasChosen && sprite.rand.nextInt(20) != 0)
        	{
        		placeSapling = false;
        	}
        	
        	hasChosen = true;

            for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k)
            {
                for (int l = 0; l < i; ++l)
                {
                    for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1)
                    {
                        for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1)
                        {
                            BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);

                            if (this.sprite.isWithinHomeDistanceFromPosition(blockpos1))
                            {

                            	if(placeSapling && sprite.getSaplingAmount() > 0 && this.shouldMoveTo(this.sprite.world, blockpos1))
                             	{
                                 	this.destinationBlock = blockpos1;
                                 	type = 1;
                                 	return true;
                             	}
                            	else if(!placeSapling && this.shouldMoveToSapling(this.sprite.world, blockpos1))
                            	{
                                	this.destinationBlock = blockpos1;
                                	type = 0;
                                	return true;
                            	}
                            }
                        }
                    }
                }
            }
        
        	hasChosen = false;
            return false;
        }
        
        @Override
        protected boolean getIsAboveDestination()
        {
            return this.isNearGoal;
        }
        

        protected boolean shouldMoveToSapling(World worldIn, BlockPos pos)
        {
            ItemStack heldItem = this.sprite.getHeldItemMainhand();

            if (heldItem != null && !heldItem.isEmpty() && heldItem.getItem() instanceof ItemBlock)
            {
                IBlockState state = worldIn.getBlockState(pos);
                ItemBlock item = (ItemBlock)heldItem.getItem();
                
            	if(state != null)
            	{
                    Block block = worldIn.getBlockState(pos).getBlock();
                    IBlockState droppedState = block.getStateFromMeta(block.damageDropped(state));
                	IBlockState handState = item.getBlock().getStateFromMeta(this.sprite.getHeldItemMainhand().getMetadata());
                	
                	//MultiMob.LOGGER.info(block + " " + droppedState);
                	if((droppedState != null && handState != null && handState == droppedState))
                	{
                            pos = pos.up();
                            if(worldIn.isAirBlock(pos))
                            {
                            	return true;
                            }
                	}
            	}
            }

            return false;
        }
        

        protected boolean shouldMoveTo(World worldIn, BlockPos pos)
        {
            ItemStack heldItem = this.sprite.getHeldItemMainhand();
            
            if (heldItem != null && !heldItem.isEmpty() && heldItem.getItem() instanceof ItemBlock)
            {
                ItemBlock item = (ItemBlock)heldItem.getItem();
                Block block = worldIn.getBlockState(pos).getBlock();
                
                if(block != null && block.canPlaceBlockAt(worldIn, pos))
                {
                	return true;
                }
            }

            return false;
        }

    }
}
