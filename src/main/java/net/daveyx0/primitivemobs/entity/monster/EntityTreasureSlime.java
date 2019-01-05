package net.daveyx0.primitivemobs.entity.monster;

import javax.annotation.Nullable;

import net.daveyx0.multimob.client.particle.MMParticles;
import net.daveyx0.multimob.entity.IMultiMob;
import net.daveyx0.multimob.util.ColorUtil;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityTreasureSlime extends EntityTameableSlime implements IMultiMob {

	private float R = 0f;
	private float G = 0f;
	private float B = 0f;
	private float NewR;
	private float NewG;
	private float NewB;
	private int colorSpeed = 10;
	
	private ItemStack currentItem;
	private boolean wasOnGround;
	
	public EntityTreasureSlime(World worldIn) {
		super(worldIn);
		if(getSkinRGB()[0] == 0f && getSkinRGB()[1] == 0f && getSkinRGB()[2] == 0f)
		{
			this.setSkinRGB(new int[]{255,255,255});
		}
		this.inventoryHandsDropChances[0] = 0f;
		this.inventoryHandsDropChances[1] = 0f;
	}
	
	public EntityTreasureSlime(World worldIn, ItemStack item, float[] rgb) {
		super(worldIn);
		this.setSkinRGB(rgb);
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, item);
		this.inventoryHandsDropChances[0] = 0f;
		this.inventoryHandsDropChances[1] = 0f;
	}
	
	protected boolean spawnCustomParticles() { return true; }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	int chance = PrimitiveMobsConfigSpecial.getTameableSlimeChance();
    	
    	if(!this.isTamed() && chance < 100 && (chance <= 0 || rand.nextInt(100/chance) != 0))
    	{
    		while(this.getHeldItemMainhand().isEmpty() && !getEntityWorld().isRemote)
    		{
    			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EntityUtil.getCustomLootItem(this, this.getSpawnLootTable(), new ItemStack(Items.SLIME_BALL)));
    		}
    	}
    		
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public boolean canDespawn()
    {
    	return !isTamed();
    }
    
    @Override
	public void onUpdate() 
	{
		if (this.isInWater() && !collidedHorizontally) {
			motionY = 0.02D;
		}
		
		if(getEntityWorld().isRemote)
		{
			changeColor(this);
		}

		if(R != NewR || G != NewG || B != NewB)
		{
			for(int i = 0; i < colorSpeed; i++)
			{
				if(R > NewR)
				{
					R--;
				}
				else if (R < NewR)
				{
					R++;
				}
			
				if(G > NewG)
				{
					G--;
				}
				else if (G < NewG)
				{
					G++;
				}
			
				if(B > NewB)
				{
					B--;
				}
				else if (B < NewB)
				{
					B++;
				}
			}
		}
		
        if (isTamed())
        {
            if (rand.nextInt(200) == 0)
            {
                getEntityWorld().spawnParticle(EnumParticleTypes.HEART, posX + (rand.nextFloat() - rand.nextFloat()), posY + rand.nextFloat() + 1D, posZ + (rand.nextFloat() - rand.nextFloat()), 1, 1, 1);
            }
            
            if(this.isSitting())
            {
            	this.setAttackTarget(null);
            }
        }

        if (this.onGround && !this.wasOnGround && getEntityWorld().isRemote)
        {
            int i = this.getSlimeSize();
            for (int j = 0; j < i * 8; ++j)
            {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
                World world = this.getEntityWorld();
                double d0 = this.posX + (double)f2;
                double d1 = this.posZ + (double)f3;
                MMParticles.spawnParticle("slime", this.world, d0, this.getEntityBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D, this.getSkinRGB());
            }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.squishAmount = -0.5F;
        }
        else if (!this.onGround && this.wasOnGround)
        {
            this.squishAmount = 1.0F;
        }

        this.wasOnGround = this.onGround;
            
		super.onUpdate();
	}
    
    protected EntityTreasureSlime createInstance()
    {
    	if(this.isTamed())
    	{
    		EntityTreasureSlime entityslime = new EntityTreasureSlime(this.getEntityWorld());
            if(this.isTamed() && this.getOwner() != null)
            {
            	entityslime.setTamed(true);
            	entityslime.setOwnerId(this.getOwnerId());
            }
    		return entityslime;
    	}
    	else
    	{
    		return new EntityTreasureSlime(this.getEntityWorld(), this.getHeldItemMainhand(), getSkinRGB());
    	}
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("wasOnGround", this.wasOnGround);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.wasOnGround = compound.getBoolean("wasOnGround");
    }
    
    /**
     * Will get destroyed next tick.
     */
    @Override
    public void setDead()
    {
        int i = this.getSlimeSize();

        if (!this.getEntityWorld().isRemote && i > 1 && this.getHealth() <= 0.0F)
        {
            int j = 2 + this.rand.nextInt(3);

            for (int k = 0; k < j; ++k)
            {
                float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
                float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
                EntityTreasureSlime entityslime = this.createInstance();

                if (this.hasCustomName())
                {
                    entityslime.setCustomNameTag(this.getCustomNameTag());
                }

                if (this.isNoDespawnRequired())
                {
                    entityslime.enablePersistence();
                }
                entityslime.setSlimeSize(i / 2, true);
                entityslime.setLocationAndAngles(this.posX + (double)f, this.posY + 0.5D, this.posZ + (double)f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.getEntityWorld().spawnEntity(entityslime);
            }
            
            if(this.isTamed())
            {
            	ItemStack stack = this.getHeldItemMainhand();

                if (!stack.isEmpty() && !getEntityWorld().isRemote)
                {
                    ItemStack newStack = stack.copy();
                    this.dropItemStack(newStack, 1);
                }
            }
        }

        this.isDead = true;
    }
    
    @Override
    protected Item getDropItem()
    {
        return this.getSlimeSize() == 1 ? this.getHeldItemMainhand().getItem() : null;
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
        return PrimitiveMobsLootTables.TREASURESLIME_SPAWN;
    }
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot()
    {
        return true;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        ItemStack stack = this.getHeldItemMainhand();

        if (!stack.isEmpty() && this.getSlimeSize() == 1 && !getEntityWorld().isRemote)
        {
            ItemStack newStack = stack.copy();
            this.dropItemStack(newStack, 1);
        }
    }
    
    public double getMountedYOffset()
    {
    	return (double)this.height * 1.2D;
    }
    
    public EntityItem dropItemStack(ItemStack itemIn, float offsetY)
    {
        return this.entityDropItem(itemIn, offsetY);
    }

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
		ItemStack stack = player.getHeldItem(hand);
        if (this.isTamed() && hand == EnumHand.MAIN_HAND)
        {
            if (!stack.isEmpty())
            {
                if (this.isHealingItem(stack))
                {
                     if (!player.capabilities.isCreativeMode)
                     {
                    	 stack.shrink(1);
                     }
                     
                     this.playHealEffect();
                     this.heal(20f);
                     return true;
                }
                
                if(this.isOwner(player))
                {
                    if(!this.getHeldItemMainhand().isEmpty() && !getEntityWorld().isRemote)
                    {
                    	this.dropItemStack(this.getHeldItemMainhand(), 0.0f);
                    }
                    
                    if(!stack.isEmpty())
                    {
                        ItemStack newStack = stack.copy();
                        newStack.setCount(1);

                        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, newStack);
                        
                        if (!player.capabilities.isCreativeMode)
                        {
                        	stack.shrink(1);
                        }
                    }
                }
            }
            else if (stack.isEmpty() && this.isOwner(player))
            {
            	this.setSitting(!this.isSitting());
            	this.playSitEffect();
            }
        }
        else if (!stack.isEmpty() && this.isTamingItem(stack) &&  hand == EnumHand.MAIN_HAND && this.getHeldItemMainhand().isEmpty())
        {
            if (!player.capabilities.isCreativeMode)
            {
                stack.shrink(1);
            }

            if (!this.getEntityWorld().isRemote)
            {
                    this.setTamed(true);
                    this.navigator.clearPath();
                    this.setHealth(20.0F);
                    this.setOwnerId(player.getUniqueID());
                    this.getEntityWorld().setEntityState(this, (byte)7);
            }
            
            this.playTameEffect(true);

            return true;
        }
        
        
        return super.processInteract(player, hand);
    }
	
    public void setTamed(boolean tamed)
    {
        super.setTamed(tamed);

        if (tamed)
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        }
        else
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        }
    }
    
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
	    if (par1DamageSource.damageType == "inWall" && isTamed())
	    {
	        return false;
	    }
	    
	    else if (par1DamageSource.damageType == "fall" && isTamed())
	    {
	        return false;
	    }
	    
	    else if (par1DamageSource.damageType == "drown" && isTamed())
	    {
	        return false;
	    }

	    return super.attackEntityFrom(par1DamageSource, par2);
	}
	
    public float[] getSkinRGB()
	{
		return new float[]{R,G,B};
	}
	
	public void setSkinRGB(int[] RGB)
	{
		R = (float)RGB[0];
		G = (float)RGB[1];
		B = (float)RGB[2];
	}
	
	public void setSkinRGB(float[] RGB)
	{
		R = RGB[0];
		G = RGB[1];
		B = RGB[2];
	}
	
	public float[] getNewSkinRGB()
	{
		return new float[]{NewR,NewG,NewB};
	}
	
	public void setNewSkinRGB(int[] RGB)
	{
		NewR = (float)RGB[0];
		NewG = (float)RGB[1];
		NewB = (float)RGB[2];
	}

	public void changeColor(Entity entity)
	{
        	int[] newColor = new int[3];
        	ItemStack heldItem = this.getHeldItemMainhand();
        		
        	if(!heldItem.isEmpty() && heldItem != currentItem)
        		{
        		currentItem = heldItem;

        		if(!(heldItem.getItem() instanceof ItemBlock))
        		{
        			newColor = ColorUtil.getItemStackColor(heldItem, getEntityWorld());
            		
            		//PrimitiveMobs.PMlogger.info(heldItem.getDisplayName() + " " + newColor);
            		
            		if(newColor != null)
            		{
            			newColor = ColorUtil.setBrightness(newColor, 25f);
            			setNewSkinRGB(newColor);
            			return;
            		}
        		}
        		else
        		{
        			ItemBlock itemblock = (ItemBlock)heldItem.getItem();
        			Block block = itemblock.getBlock();
        			BlockStateContainer container = block.getBlockState();
        			IBlockState blockstate = container.getValidStates().get(heldItem.getMetadata());
        			
        			newColor = ColorUtil.getBlockStateColor(blockstate, null, null, true);
            		
            		//PrimitiveMobs.PMlogger.info(heldItem.getDisplayName() + " " + newColor);
            		
            		newColor = ColorUtil.setBrightness(newColor, 25f);
            		
            		if(newColor != null)
            		{
            			setNewSkinRGB(newColor);
            			return;
            		}
        		}

        	}
        	
        	if(heldItem.isEmpty())
        	{	
            	setNewSkinRGB(new int[]{255,255,255});
        	}

     }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL && EntityUtil.isValidMobLightLevel(this);
    }
    
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	if(type == EnumCreatureType.MONSTER){return false;}
    	return super.isCreatureType(type, forSpawnCount);
    }
        
}
