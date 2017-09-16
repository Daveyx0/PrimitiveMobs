package net.daveyx0.primitivemobs.entity.passive;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.Tuple2;


public class EntityChameleon extends EntityTameable
{

	private float R;
	private float G;
	private float B;
	private float NewR;
	private float NewG;
	private float NewB;
	private int colorSpeed = 4;

	private IBlockState currentState;
	private int currentMultiplier;
	
	public EntityChameleon(World worldIn)
	{
		super(worldIn);
		this.setSize(0.7f, 0.5f);
		this.setSkinRGB(new int[]{0,125,25});
		this.stepHeight = 1.0f;
		this.setTamed(false);
	}
	
	protected void initEntityAI()
    {
		int prio = 0;
		this.aiSit = new EntityAISit(this);
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, this.aiSit);
        this.tasks.addTask(++prio, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(++prio, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(++prio, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAITempt(this, 1.1D, Items.FERMENTED_SPIDER_EYE, false));
        this.tasks.addTask(++prio, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(++prio, new EntityAILookIdle(this));
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
        if (this.isTamed())
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        }
        else
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }
        
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
    }
    
    public boolean canDespawn()
    {
    	return !isTamed();
    } 
    
    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		
		return new EntityChameleon(this.getEntityWorld());
	}
	
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return PrimitiveMobsLootTables.ENTITIES_CHAMELEON;
    }
	
	@Override
	public void onUpdate() 
	{
		
		if (this.isInWater() && !isCollidedHorizontally) {
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
        }
        
		super.onUpdate();
	}
	
    public boolean isHealingItem(@Nullable ItemStack stack)
    {
        return stack == null ? false : stack.getItem() == Items.SPIDER_EYE;
    }
    
    public boolean isTamingItem(@Nullable ItemStack stack)
    {
        return stack == null ? false : stack.getItem() == Items.MELON;
    }
    
    public boolean isBreedingItem(@Nullable ItemStack stack)
    {
        return stack == null ? false : stack.getItem() == Items.FERMENTED_SPIDER_EYE;
    }
	
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
    {
        if (this.isTamed())
        {
            if (stack != null)
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
            }

            if (this.isOwner(player) && !this.isBreedingItem(stack))
            {
            	if(!this.getEntityWorld().isRemote)
            	{
            		this.aiSit.setSitting(!this.isSitting());
            		this.isJumping = false;
            		this.navigator.clearPathEntity();
            	}
            	else
            	{
            		this.playSitEffect();
            	}
            }
        }
        else if (stack != null && this.isTamingItem(stack))
        {
            if (!player.capabilities.isCreativeMode)
            {
            	stack.shrink(1);
            }

            if (!this.getEntityWorld().isRemote)
            {
                    this.setTamed(true);
                    this.navigator.clearPathEntity();
                    this.setHealth(20.0F);
                    this.setOwnerId(player.getUniqueID());
                    this.playTameEffect(true);
                    this.getEntityWorld().setEntityState(this, (byte)7);
            }

            return true;
        }
        
        /*
        if(worldObj.isRemote)
        {
        	int[] newColor = new int[3];
        	
        	if(stack != null)
        	{
        			newColor = ColorUtil.getItemStackColor(stack, worldObj);
            		
            		if(newColor != null)
            		{
            			setNewSkinRGB(newColor);
            		}
        	
        	}
        }*/
        
        return super.processInteract(player, hand);
    }
	
    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    protected void playHealEffect()
    {
        EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;

        for (int i = 0; i < 7; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.getEntityWorld().spawnParticle(enumparticletypes, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
        }
    }
    
    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    protected void playSitEffect()
    {
        EnumParticleTypes enumparticletypes = EnumParticleTypes.NOTE;

        for (int i = 0; i < 7; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.getEntityWorld().spawnParticle(enumparticletypes, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
        }
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
		int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.getEntityBoundingBox().minY);
        int k = MathHelper.floor(entity.posZ);
        
		if(entity.getEntityWorld().getBlockState(new BlockPos(i, j, k)).getBlock() == Blocks.AIR)
		{
			j = MathHelper.floor(entity.getEntityBoundingBox().minY - 0.1);
		}
		
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState state = entity.getEntityWorld().getBlockState(pos);
		
		int colorMultiplier = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, getEntityWorld(), pos, 0);
		
		//PrimitiveMobsLogger.info(worldObj, state + " " + colorMultiplier);
		if(state.getBlock() != Blocks.AIR && (currentState != state || currentMultiplier != colorMultiplier))
		{
			currentState = state;
			currentMultiplier = colorMultiplier;
			
			int[] newColor = ColorUtil.getBlockStateColor(state, pos, getEntityWorld());
			if(newColor != null)
			{
				setNewSkinRGB(newColor);
			}
		}
	}
	
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere();
    }

}
