package net.daveyx0.primitivemobs.util;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.daveyx0.primitivemobs.core.PrimitiveMobsEntities;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityUtil {

    
    public static Predicate<Entity> isNotPlayer()
    {
        return p -> !(p instanceof EntityPlayer);
    }
    
    public static void removeWhenDisabled(EntityLiving entity)
    {
		if (PrimitiveMobsEntities.enabledEntities.containsKey(entity.getClass()) && !PrimitiveMobsEntities.enabledEntities.get(entity.getClass()))
		{
			entity.setDead();
		}
    }
    
    @Nullable
    public static EntityLivingBase getLoadedEntityByUUID(UUID uuid, World world)
    {
    	
        for (int i = 0; i < world.loadedEntityList.size(); ++i)
        {
            Entity entity = world.loadedEntityList.get(i);

            if (uuid.equals(entity.getUniqueID()))
            {
            	if(entity instanceof EntityLivingBase){return (EntityLivingBase)entity;}
            	else{return null;}
            }
        }

        return null;
    }
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    public static boolean isValidMobLightLevel(EntityLivingBase entity)
    {
        BlockPos blockpos = new BlockPos(entity.posX, entity.getEntityBoundingBox().minY, entity.posZ);

        if (entity.getEntityWorld().getLightFor(EnumSkyBlock.SKY, blockpos) > entity.getRNG().nextInt(32))
        {
            return false;
        }
        else
        {
            int i = entity.getEntityWorld().getLightFromNeighbors(blockpos);

            if (entity.getEntityWorld().isThundering())
            {
                int j = entity.getEntityWorld().getSkylightSubtracted();
                entity.getEntityWorld().setSkylightSubtracted(10);
                i = entity.getEntityWorld().getLightFromNeighbors(blockpos);
                entity.getEntityWorld().setSkylightSubtracted(j);
            }

            return i <= entity.getRNG().nextInt(8);
        }
    }
    
    public static float distanceToSurface(EntityLivingBase entityLivingBase, World world)
    {
        int i = MathHelper.floor(entityLivingBase.posX);
        int j = MathHelper.floor(entityLivingBase.getEntityBoundingBox().minY);
        int k = MathHelper.floor(entityLivingBase.posZ);
        BlockPos pos = new BlockPos(i,j,k);
        IBlockState l = world.getBlockState(pos);

        if (l != null && l.getMaterial() == Material.WATER)
        {
            for (int j1 = 1; j1 < 64; j1++)
            {
                BlockPos pos1 = new BlockPos(i,j + j1,k);
                IBlockState i1 = world.getBlockState(pos1);

                if (i1.getBlock() == Blocks.AIR || i1.getMaterial() != Material.WATER)
                {
                    return (float)j1;
                }
            }
        }

        return -1.0F;
    }
    
    
    public static Object[] searchTree(Entity entity, double d)
    {
    	Object[] states = new Object[3];
    	
    	AxisAlignedBB axisalignedbb = new AxisAlignedBB(entity.posX - d, entity.posY - d, entity.posZ - d, entity.posX + d, entity.posY + d, entity.posZ + d);
        int n = MathHelper.floor(axisalignedbb.minX);
        int o = MathHelper.floor(axisalignedbb.maxX);
        int p = MathHelper.floor(axisalignedbb.minY);
        int q = MathHelper.floor(axisalignedbb.maxY);
        int r = MathHelper.floor(axisalignedbb.minZ);
        int s = MathHelper.floor(axisalignedbb.maxZ);

        for (int p1 = n; p1 < o; p1++)
        {
            for (int q1 = p; q1 < q; q1++)
            {
                for (int n2 = r; n2 < s; n2++)
                {
                	BlockPos pos = new BlockPos(p1, q1, n2);
                    IBlockState state = entity.getEntityWorld().getBlockState(pos);
                    
                    if (state == null || state.getBlock() == Blocks.AIR)
                    {
                        continue;
                    }
                    else if (state != null && (state.getBlock() instanceof BlockLog || state.getMaterial() == Material.WOOD && state.getBlock() instanceof BlockRotatedPillar 
                    		|| state.getMaterial() == Material.WOOD && state.getBlock().canSustainLeaves(state, entity.getEntityWorld(), pos)))
                    {
                    	states[0] = state;
                    	
                    	for (int l = 0; l < 64; l++)
                        {
                    		BlockPos pos2 = new BlockPos(p1, q1 + l, n2);
                    		IBlockState state2 = entity.getEntityWorld().getBlockState(pos2);
                    		
                            if (state2 == null || state2.getBlock() == Blocks.AIR)
                            {
                                continue;
                            }
                            else if (state2 != null && (state2.getBlock() instanceof BlockLeaves || state2.getMaterial() == Material.LEAVES))
                    		 {
                            	states[1] = state2;
                            	states[2] = pos2;
                                return states;
                    		 }
                    		 
                        }
                    }
                    	
                }
                	
              }
         }
        
       return null;
    }
    
}
