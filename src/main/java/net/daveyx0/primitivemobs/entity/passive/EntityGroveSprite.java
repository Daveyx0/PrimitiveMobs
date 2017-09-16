package net.daveyx0.primitivemobs.entity.passive;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsParticles;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.entity.ai.EntityAITemptItemStack;
import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.util.BlockStateUtil;
import net.daveyx0.primitivemobs.util.ColorUtil;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.daveyx0.primitivemobs.util.NBTUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	
	public EntityGroveSprite(World worldIn) {
		super(worldIn);
		
	}

	protected void initEntityAI()
    {
		int prio = 0;
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(++prio, new EntityAITemptItemStack(this, 1.1D, false, Sets.newHashSet(new ItemStack[] {this.getHeldItemMainhand(), new ItemStack(Items.DYE, 1, 15)})));
        this.tasks.addTask(++prio, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
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
    	}
		
        return super.onInitialSpawn(difficulty, livingdata);
    }
	
    private void determineLogAndLeaves() {

    	Object[] tree = EntityUtil.searchTree(this, 6.0D);
    	
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
        super.entityInit();
    }
    
   	public void onLivingUpdate() 
   	{	
   		if(getEntityWorld().isRemote && !changedColor)
   		{
   			changedColor = true;		
   			setLeavesRGB(this.getColor(this, this.getLeaves(), this.getLeavesPos(), null));
   			setLogRGB(this.getColor(this, this.getLog(), null, EnumFacing.WEST));
   			setLogTopRGB(this.getColor(this, this.getLog(), null, null));
   		}
     
   		super.onLivingUpdate();
   	}
    
    public int[] getColor(Entity entity, IBlockState state, @Nullable BlockPos pos, @Nullable EnumFacing face)
	{	
		if(state.getBlock() != Blocks.AIR)
		{
			int[] newColor = new int[3];
			
			if(face != null)
			{
				newColor =	ColorUtil.getBlockStateColor(state, pos, getEntityWorld(), face);
			}
			else
			{
				newColor =	ColorUtil.getBlockStateColor(state, pos, getEntityWorld());
			}
			
			if(newColor != null)
			{
				return newColor;
			}
		}
		
		return new int[]{255,255,255};
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
    
    public void setLeaves(IBlockState leaves)
    {
    	Optional<IBlockState> newLeaves = Optional.of(leaves);
        this.getDataManager().set(LEAVES, newLeaves);
    }
    
    public IBlockState getLeaves()
    {
    	Optional<IBlockState> state = (Optional<IBlockState>)this.getDataManager().get(LEAVES);
    	return state != null ? state.get() : Blocks.LEAVES.getDefaultState();
    }
    
    public void setLog(IBlockState log)
    {
    	Optional<IBlockState> newLog = Optional.of(log);
        this.getDataManager().set(LOG, newLog);
    }
    
    public IBlockState getLog()
    {
    	Optional<IBlockState> state = (Optional<IBlockState>)this.getDataManager().get(LOG);
    	return state != null ? state.get() : Blocks.LOG.getDefaultState();
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
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
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
}
