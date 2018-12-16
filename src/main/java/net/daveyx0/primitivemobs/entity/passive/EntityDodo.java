package net.daveyx0.primitivemobs.entity.passive;

import javax.annotation.Nullable;

import net.daveyx0.multimob.entity.ai.EntityAITemptItemStack;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityDodo extends EntityChicken
{
	public int timeUntilNextShed;
	public int timeUntilNextDodoEgg;
	private static final ItemStack[] BREEDINGITEMS = new ItemStack[]{new ItemStack(Items.DYE, 1, 3), new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM))
			,new ItemStack(Item.getItemFromBlock(Blocks.RED_MUSHROOM)), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.MELON_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.APPLE)
			, new ItemStack(Items.BEETROOT), new ItemStack(Items.CARROT), new ItemStack(Items.MELON), new ItemStack(Items.WHEAT), new ItemStack(Items.REEDS), new ItemStack(Items.POTATO)
			, new ItemStack(Item.getItemFromBlock(Blocks.PUMPKIN)), new ItemStack(Item.getItemFromBlock(Blocks.MELON_BLOCK))};
	
	public EntityDodo(World worldIn) {
		super(worldIn);
		this.timeUntilNextShed = this.rand.nextInt(6000) + 6000;
		this.timeUntilNextDodoEgg = this.rand.nextInt(10000) + 10000;
		this.setSize(0.75f, 0.75f);
	}
	
    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITemptItemStack(this, 1.0D, this.getHeldItemMainhand(), false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    
    public ItemStack getRandomBreedingItem()
    {
    	return BREEDINGITEMS[rand.nextInt(BREEDINGITEMS.length)];
    }
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == this.getHeldItemMainhand().getItem() && stack.getMetadata() == this.getHeldItemMainhand().getMetadata();
    }
    
	
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    	
        if(this.isInLove() && this.getHeldItemMainhand().getItem() != Item.getItemFromBlock(Blocks.AIR))
        {
        	this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Item.getItemFromBlock(Blocks.AIR)));
        }
        
        if(!this.isInLove() && !this.world.isRemote && (this.getHeldItemMainhand() == null || this.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.AIR)))
        {
        	this.setHeldItem(EnumHand.MAIN_HAND, this.getRandomBreedingItem());
        }
        
        this.timeUntilNextEgg = 100;
        
        if (!this.world.isRemote && !this.isChild() && !this.isChickenJockey() && --this.timeUntilNextShed <= 0 && PrimitiveMobsConfigSpecial.dodoMycelium)
        {
            this.playSound(SoundEvents.BLOCK_GRASS_BREAK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.setGrassToMycelium();
            this.timeUntilNextShed = this.rand.nextInt(6000) + 6000;
        }
        
        if (!this.world.isRemote && !this.isChild() && !this.isChickenJockey() && --this.timeUntilNextEgg <= 0)
        {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(PrimitiveMobsItems.DODO_EGG, 1);
            this.timeUntilNextDodoEgg = this.rand.nextInt(10000) + 10000;
        }
    }
    
    public EntityDodo createChild(EntityAgeable ageable)
    {
        return new EntityDodo(this.world);
    }

    
    public void setGrassToMycelium()
    {
    	BlockPos pos = new BlockPos(this.posX, this.getEntityBoundingBox().minY - 0.1D, this.posZ);
    	IBlockState state = world.getBlockState(pos);
    	Block block = state.getBlock();
    	
    	if(block != null && block instanceof BlockGrass && !world.isRemote)
    	{
    		world.setBlockState(pos, Blocks.MYCELIUM.getDefaultState(), 3);
    	}
    }
    
    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.1F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.6F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("DodoEggTime"))
        {
            this.timeUntilNextDodoEgg = compound.getInteger("DodoEggTime");
        }
        
        if (compound.hasKey("ShedTime"))
        {
            this.timeUntilNextShed = compound.getInteger("ShedTime");
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("ShedTime", this.timeUntilNextShed);
        compound.setInteger("DodoEggTime", this.timeUntilNextDodoEgg);
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_DODO;
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        boolean flag = iblockstate.canEntitySpawn(this);
        return this.world.getBlockState(blockpos.down()).getBlock() == Blocks.MYCELIUM && this.world.getLight(blockpos) > 8 && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F && flag;
    }


}
