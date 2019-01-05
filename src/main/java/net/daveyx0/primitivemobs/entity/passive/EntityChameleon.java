package net.daveyx0.primitivemobs.entity.passive;

import javax.annotation.Nullable;

import net.daveyx0.multimob.entity.IMultiMob;
import net.daveyx0.multimob.entity.IMultiMobPassive;
import net.daveyx0.multimob.util.ColorUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityChameleon extends EntityAnimal implements IMultiMobPassive
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
	}
	
	protected void initEntityAI()
    {
		int prio = 0;
        this.tasks.addTask(++prio, new EntityAISwimming(this));
        this.tasks.addTask(++prio, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(++prio, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(++prio, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++prio, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(++prio, new EntityAILookIdle(this));
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
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
        
		super.onUpdate();
	}
    
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.FERMENTED_SPIDER_EYE;
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
			
			int[] newColor = ColorUtil.getBlockStateColor(state, pos, getEntityWorld(), true);
			if(newColor != null)
			{
				if(ColorUtil.isColorInvalid(newColor))
				{
					newColor = new int[]{63,118,42,255};
				}
				setNewSkinRGB(newColor);
			}
		}
	}
	
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	if(type == EnumCreatureType.CREATURE){return false;}
    	return super.isCreatureType(type, forSpawnCount);
    }

}
