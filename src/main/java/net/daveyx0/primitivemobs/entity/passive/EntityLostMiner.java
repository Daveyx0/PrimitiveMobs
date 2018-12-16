package net.daveyx0.primitivemobs.entity.passive;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsVillagerProfessions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityLostMiner extends EntityVillager {

	private static final DataParameter<Boolean> IS_SAVED = EntityDataManager.<Boolean>createKey(EntityLostMiner.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DOOR_NEARBY = EntityDataManager.<Boolean>createKey(EntityLostMiner.class, DataSerializers.BOOLEAN);
	
	EntityPlayer currentPlayer;
	
	public EntityLostMiner(World worldIn) {
		super(worldIn);
		currentPlayer = null;
		this.setDoorNearby(false);
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		//Used to follow player when scared. Not using an AI as it caused a mess with the existing Villager AI tasks and removing/adding is a pain
		if(!isSaved() && this.ticksExisted % 5 == 0)
		{
			EntityPlayer entityplayer = world.getClosestPlayer(posX, posY, posZ, 8D, false);
	    	EntityPlayer entityplayer1 = world.getClosestPlayer(posX, posY, posZ, 2D, false);
	    	if(entityplayer != null && entityplayer1 == null)
			{
	    		this.currentPlayer = entityplayer;
			}
	    	else
	    	{
	    		this.currentPlayer = null;
	    	}
	    	
	    	EnumParticleTypes particle = EnumParticleTypes.WATER_SPLASH;
			if(isNotScared())
			{
				particle = EnumParticleTypes.VILLAGER_HAPPY;
			}
			
			float f = 0.01745278F;
	        double d = posX - Math.sin(rotationYaw * f) / 3D;
	        double d1 = posY + rand.nextDouble() / 3D;
	        double d2 = posZ + Math.cos(rotationYaw * f) / 3D;
	        world.spawnParticle(particle, d, d1 + 1.8D, d2, 0.0D, 0.0D, 0.0D);
			
			if(!world.isRemote)
			{
				BlockPos blockpos = new BlockPos(this);
				Village village = this.world.getVillageCollection().getNearestVillage(blockpos, 14);
				if(village != null)
				{
					if(village.getDoorInfo(blockpos)!= null)
					{
						this.setDoorNearby(true);
					}
					else
					{
						this.setDoorNearby(false);
					}
				}
			}
		}
		
		if(!isSaved() && currentPlayer != null)
		{
    		this.getNavigator().tryMoveToEntityLiving(currentPlayer, 0.65F);
		}
		
	}
       
	@Override
    public IEntityLivingData finalizeMobSpawn(DifficultyInstance p_190672_1_, @Nullable IEntityLivingData p_190672_2_, boolean p_190672_3_)
    {
        this.setProfession(PrimitiveMobsVillagerProfessions.MINER_PROFESSION);
        
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        
        return super.finalizeMobSpawn(p_190672_1_, p_190672_2_, false);
    }
	
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
    	if(!isSaved())
    	{
    		BlockPos blockpos = new BlockPos(this);
    		
    		boolean flag = this.isNotScared();
    		if(flag)
    		{
    			this.setSaved(true);
                for (int i = 0; i < 8; i++)
                {
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 0, 0, 0);
                }
        		if(!world.isRemote)
        		{
            	       for (int j = 0; j < getEmeralds()[0] + rand.nextInt(getEmeralds()[1]); ++j)
            	       {
            	         this.dropItem(Items.EMERALD, 1);
            	       }
        		}
    		}
    		
    		return flag;
    	}
    	else
    	{
        	return super.processInteract(player, hand);
    	}
    }
    
    public boolean isNotScared()
    {
    	return this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)) && posY > 50D || isDoorNearby();
    }
    
    public int[] getEmeralds()
    {
    	return PrimitiveMobsConfigSpecial.getLostMinerLootRange();
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
    	if(PrimitiveMobsConfigSpecial.getLostMinerSounds())
    	{
    		return this.isTrading() ? SoundEvents.ENTITY_VILLAGER_TRADING : SoundEvents.ENTITY_VILLAGER_AMBIENT;
    	}
    	return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
    	if(PrimitiveMobsConfigSpecial.getLostMinerSounds())
    	{
    		return SoundEvents.ENTITY_VILLAGER_HURT;
    	}
    	return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
    	if(PrimitiveMobsConfigSpecial.getLostMinerSounds())
    	{
    		return SoundEvents.ENTITY_VILLAGER_DEATH;
    	}
    	return SoundEvents.ENTITY_GENERIC_HURT;
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !isSaved();
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_SAVED, false); 
        this.dataManager.register(DOOR_NEARBY, false); 
    }

	public void setSaved(boolean b) {

		 this.dataManager.set(IS_SAVED, b);
	}
	
    public boolean isSaved()
    {
		return ((Boolean)this.dataManager.get(IS_SAVED)).booleanValue();
    }
    
	public void setDoorNearby(boolean b) {

		 this.dataManager.set(DOOR_NEARBY, b);
	}
	
   public boolean isDoorNearby()
   {
		return ((Boolean)this.dataManager.get(DOOR_NEARBY)).booleanValue();
   }
    
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        compound.setBoolean("Saved", this.isSaved());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        this.setSaved(compound.getBoolean("Saved"));
    }
	
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere() && this.posY < 50D && this.posY > 20D;
    }

}
