package net.daveyx0.primitivemobs.entity.passive;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.daveyx0.multimob.entity.ai.EntityAITemptItemStack;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsVillagerProfessions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTravelingMerchant extends EntityVillager {

	private static final DataParameter<Boolean> CAN_DESPAWN = EntityDataManager.<Boolean>createKey(EntityTravelingMerchant.class, DataSerializers.BOOLEAN);
	
	public EntityTravelingMerchant(World worldIn) {
		super(worldIn);
	}
	
    protected void initEntityAI()
    {
    	super.initEntityAI();
        this.tasks.addTask(8, new EntityAITemptItemStack(this, 1.1D, false, Sets.newHashSet(new ItemStack[] {new ItemStack(Items.EMERALD)})));
        
        if(!PrimitiveMobsConfigSpecial.getTravelerVisit())
        {
			while(this.tasks.taskEntries.stream()
			.filter(taskEntry -> taskEntry.action instanceof EntityAIMoveIndoors || taskEntry.action instanceof EntityAIRestrictOpenDoor
					|| taskEntry.action instanceof EntityAIOpenDoor).findFirst().isPresent())
			{
				this.tasks.taskEntries.stream().filter(taskEntry -> taskEntry.action instanceof EntityAIMoveIndoors || taskEntry.action instanceof EntityAIRestrictOpenDoor
						|| taskEntry.action instanceof EntityAIOpenDoor)
				.findFirst().ifPresent(taskEntry -> this.tasks.removeTask(taskEntry.action));
			}
        }
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CAN_DESPAWN, true);
    }
       
	@Override
    public IEntityLivingData finalizeMobSpawn(DifficultyInstance p_190672_1_, @Nullable IEntityLivingData p_190672_2_, boolean p_190672_3_)
    {
        this.setProfession(PrimitiveMobsVillagerProfessions.MERCHANT_PROFESSION);
        
        return super.finalizeMobSpawn(p_190672_1_, p_190672_2_, false);
    }
	
	   public boolean processInteract(EntityPlayer player, EnumHand hand)
	    {
	        ItemStack itemstack = player.getHeldItem(hand);
	        boolean flag = itemstack.getItem() == Item.getItemFromBlock(Blocks.EMERALD_BLOCK);
	        
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
	        else
	        {
	            return super.processInteract(player, hand);
	        }
	    }
	   
		public void setCanDespawn(boolean b) {

			 this.dataManager.set(CAN_DESPAWN, b);
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
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
    	return ((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue();
    }
    
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("canDespawn", ((Boolean)this.dataManager.get(CAN_DESPAWN)).booleanValue());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setCanDespawn(compound.getBoolean("canDespawn"));
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        return (this.getEntityWorld().getBlockState(blockpos.down()).getBlock() == Blocks.GRASS || this.getEntityWorld().getBlockState(blockpos.down()).getBlock() == Blocks.DIRT ||
        		this.getEntityWorld().getBlockState(blockpos.down()).getBlock() == Blocks.STONE || this.getEntityWorld().getBlockState(blockpos.down()).getBlock() == Blocks.SAND
        		 || this.getEntityWorld().getBlockState(blockpos.down()).getBlock() == Blocks.SNOW
        		) && j > 20 && this.getEntityWorld().getLight(blockpos) > 8 && super.getCanSpawnHere();
    }


}
