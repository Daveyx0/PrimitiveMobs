package net.daveyx0.primitivemobs.entity.passive;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.daveyx0.multimob.entity.ai.EntityAITemptItemStack;
import net.daveyx0.primitivemobs.core.PrimitiveMobsVillagerProfessions;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySheepman extends EntityVillager implements net.minecraftforge.common.IShearable{

	private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.<Byte>createKey(EntitySheepman.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> CAN_DESPAWN = EntityDataManager.<Boolean>createKey(EntitySheepman.class, DataSerializers.BOOLEAN);
    /**
     * Internal crafting inventory used to check the result of mixing dyes corresponding to the fleece color when
     * breeding sheep.
     */
    private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container()
    {
        /**
         * Determines whether supplied player can use this container
         */
        public boolean canInteractWith(EntityPlayer playerIn)
        {
            return false;
        }
    }, 2, 1);
    
    protected void initEntityAI()
    {
    	super.initEntityAI();
        this.tasks.addTask(8, new EntityAITemptItemStack(this, 1.1D, false, Sets.newHashSet(new ItemStack[] {new ItemStack(Items.GOLD_INGOT)})));
    }
  
    private int sheepTimer;
    
	public EntitySheepman(World worldIn) {
		super(worldIn);
        this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.DYE));
        this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.DYE));
	}
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(DYE_COLOR, Byte.valueOf((byte)0));
        this.dataManager.register(CAN_DESPAWN, true);
    }
    
	public void setCanDespawn(boolean b) {

		 this.dataManager.set(CAN_DESPAWN, b);
	}
	
    
	@Override
 public IEntityLivingData finalizeMobSpawn(DifficultyInstance p_190672_1_, @Nullable IEntityLivingData p_190672_2_, boolean p_190672_3_)
 {
     int profession = this.rand.nextInt(3);
     
     switch(profession)
     {
     	case 0:
     	{
     	    this.setProfession(PrimitiveMobsVillagerProfessions.SHEEPMAN_PROFESSION_SCAVENGER);
     		this.setFleeceColor(EnumDyeColor.RED);
     		break;
     	}
     	case 1:
     	{
     	    this.setProfession(PrimitiveMobsVillagerProfessions.SHEEPMAN_PROFESSION_ALCHEMIST);
     		this.setFleeceColor(EnumDyeColor.ORANGE);
     		break;
     	}
     	case 2:
     	{
     	    this.setProfession(PrimitiveMobsVillagerProfessions.SHEEPMAN_PROFESSION_THIEF);
     		this.setFleeceColor(EnumDyeColor.BLACK);
     		break;
     	}
     }
     
     
     
     return super.finalizeMobSpawn(p_190672_1_, p_190672_2_, false);
 }
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        return livingdata;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume()
    {
        return 1.1111F;
    }


protected SoundEvent getAmbientSound()
{
    return SoundEvents.ENTITY_SHEEP_AMBIENT;
}

protected SoundEvent getHurtSound(DamageSource p_184601_1_)
{
    return SoundEvents.ENTITY_SHEEP_HURT;
}

protected SoundEvent getDeathSound()
{
    return SoundEvents.ENTITY_SHEEP_DEATH;
}

protected void playStepSound(BlockPos pos, Block blockIn)
{
    this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
}

@Nullable
protected ResourceLocation getLootTable()
{
    if (this.getSheared())
    {
        return LootTableList.ENTITIES_SHEEP;
    }
    else
    {
        switch (this.getFleeceColor())
        {
            case WHITE:
            default:
                return LootTableList.ENTITIES_SHEEP_WHITE;
            case ORANGE:
                return LootTableList.ENTITIES_SHEEP_ORANGE;
            case MAGENTA:
                return LootTableList.ENTITIES_SHEEP_MAGENTA;
            case LIGHT_BLUE:
                return LootTableList.ENTITIES_SHEEP_LIGHT_BLUE;
            case YELLOW:
                return LootTableList.ENTITIES_SHEEP_YELLOW;
            case LIME:
                return LootTableList.ENTITIES_SHEEP_LIME;
            case PINK:
                return LootTableList.ENTITIES_SHEEP_PINK;
            case GRAY:
                return LootTableList.ENTITIES_SHEEP_GRAY;
            case SILVER:
                return LootTableList.ENTITIES_SHEEP_SILVER;
            case CYAN:
                return LootTableList.ENTITIES_SHEEP_CYAN;
            case PURPLE:
                return LootTableList.ENTITIES_SHEEP_PURPLE;
            case BLUE:
                return LootTableList.ENTITIES_SHEEP_BLUE;
            case BROWN:
                return LootTableList.ENTITIES_SHEEP_BROWN;
            case GREEN:
                return LootTableList.ENTITIES_SHEEP_GREEN;
            case RED:
                return LootTableList.ENTITIES_SHEEP_RED;
            case BLACK:
                return LootTableList.ENTITIES_SHEEP_BLACK;
        }
    }
}

public boolean processInteract(EntityPlayer player, EnumHand hand)
{
    ItemStack itemstack = player.getHeldItem(hand);
    boolean flag = itemstack.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK);
    
    if (flag && ((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue())
    {
    	this.consumeItemFromStack(player, itemstack);
    	this.setCanDespawn(false);
        for (int i = 0; i < 8; i++)
        {
        	world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
        }
        return true;
    }
    else if (itemstack.getItem() == Items.SHEARS && !this.getSheared() && !this.isChild())   //Forge: Moved to onSheared
    {
        if (!this.world.isRemote)
        {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);

            for (int j = 0; j < i; ++j)
            {
                EntityItem entityitem = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, this.getFleeceColor().getMetadata()), 1.0F);
                entityitem.motionY += (double)(this.rand.nextFloat() * 0.05F);
                entityitem.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                entityitem.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
            }
        }

        itemstack.damageItem(1, player);
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return true;
    }
    
    return super.processInteract(player, hand);

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
 * (abstract) Protected helper method to write subclass entity data to NBT.
 */
public void writeEntityToNBT(NBTTagCompound compound)
{
    super.writeEntityToNBT(compound);
    compound.setBoolean("Sheared", this.getSheared());
    compound.setByte("Color", (byte)this.getFleeceColor().getMetadata());
    compound.setBoolean("canDespawn", ((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue());
}

/**
 * (abstract) Protected helper method to read subclass entity data from NBT.
 */
public void readEntityFromNBT(NBTTagCompound compound)
{
    super.readEntityFromNBT(compound);
    this.setSheared(compound.getBoolean("Sheared"));
    this.setFleeceColor(EnumDyeColor.byMetadata(compound.getByte("Color")));
    this.setCanDespawn(compound.getBoolean("canDespawn"));
}

/**
 * Gets the wool color of this sheep.
 */
public EnumDyeColor getFleeceColor()
{
    return EnumDyeColor.byMetadata(((Byte)this.dataManager.get(DYE_COLOR)).byteValue() & 15);
}

/**
 * Sets the wool color of this sheep
 */
public void setFleeceColor(EnumDyeColor color)
{
    byte b0 = ((Byte)this.dataManager.get(DYE_COLOR)).byteValue();
    this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 & 240 | color.getMetadata() & 15)));
}

/**
 * returns true if a sheeps wool has been sheared
 */
public boolean getSheared()
{
    return (((Byte)this.dataManager.get(DYE_COLOR)).byteValue() & 16) != 0;
}

/**
 * make a sheep sheared if set to true
 */
public void setSheared(boolean sheared)
{
    byte b0 = ((Byte)this.dataManager.get(DYE_COLOR)).byteValue();

    if (sheared)
    {
        this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 | 16)));
    }
    else
    {
        this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 & -17)));
    }
}

@Override
public EntitySheepman createChild(EntityAgeable ageable)
{
	EntitySheepman entitysheep = (EntitySheepman)ageable;
	EntitySheepman entitysheep1 = new EntitySheepman(this.world);
    //entitysheep1.setFleeceColor(this.getDyeColorMixFromParents(this, entitysheep));
    return entitysheep1;
}

@Override public boolean isShearable(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos){ return !this.getSheared() && !this.isChild(); }
@Override
public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
{
    this.setSheared(true);
    int i = 1 + this.rand.nextInt(3);

    java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
    for (int j = 0; j < i; ++j)
        ret.add(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, this.getFleeceColor().getMetadata()));

    this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
    return ret;
}

/**
 * Attempts to mix both parent sheep to come up with a mixed dye color.
 */
private EnumDyeColor getDyeColorMixFromParents(EntitySheepman father, EntitySheepman mother)
{
    int i = ((EntitySheepman)father).getFleeceColor().getDyeDamage();
    int j = ((EntitySheepman)mother).getFleeceColor().getDyeDamage();
    this.inventoryCrafting.getStackInSlot(0).setItemDamage(i);
    this.inventoryCrafting.getStackInSlot(1).setItemDamage(j);
    ItemStack itemstack = CraftingManager.findMatchingResult(this.inventoryCrafting, ((EntitySheepman)father).world);
    int k;

    if (itemstack.getItem() == Items.DYE)
    {
        k = itemstack.getMetadata();
    }
    else
    {
        k = this.world.rand.nextBoolean() ? i : j;
    }

    return EnumDyeColor.byDyeDamage(k);
}

/**
 * Determines if an entity can be despawned, used on idle far away entities
 */
protected boolean canDespawn()
{
	return ((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue();
}

/**
 * Called when a lightning bolt hits the entity.
 */
@Override
public void onStruckByLightning(EntityLightningBolt lightningBolt)
{
    if (!this.world.isRemote && !this.isDead)
    {
    	EntitySheep sheep = new EntitySheep(this.world);
    	sheep.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    	sheep.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(sheep)), (IEntityLivingData)null);
    	sheep.setNoAI(this.isAIDisabled());
    	sheep.setFleeceColor(this.getFleeceColor());

        if (this.hasCustomName())
        {
        	sheep.setCustomNameTag(this.getCustomNameTag());
        	sheep.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
        }

        this.world.spawnEntity(sheep);
        this.setDead();
    }
}

@Override
public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
{
    return false;
}


}

