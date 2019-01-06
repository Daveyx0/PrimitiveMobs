package net.daveyx0.primitivemobs.modint;

import java.util.ArrayList;
import java.util.List;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ITreePart;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.Species;

import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.multimob.modint.DynamicTreesIntegration;
import net.daveyx0.multimob.modint.IModIntegration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PrimitiveMobsDTIntegration extends DynamicTreesIntegration {

	@Override
	public void init() 
	{
		super.init();
	}
	
	 public Object[] searchDynamicTree(EntityLiving entity, double d)
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
	                    else if (TreeHelper.isBranch(state))
	                    {
                        	Species species = TreeHelper.getExactSpecies(state, entity.getEntityWorld(), pos);
	                    	BlockPos posRoot = TreeHelper.findRootNode(state, entity.getEntityWorld(), pos);

	                    	states[0] = species.getFamily().getPrimitiveLog();

	                    	for (int l = 0; l < 64; l++)
	                        {
	                    		BlockPos pos2 = new BlockPos(posRoot.getX(), posRoot.getY() + l, posRoot.getZ());
	                    		IBlockState state2 = entity.getEntityWorld().getBlockState(pos2);
	                    	
	                            if (state2 == null || state2.getBlock() == Blocks.AIR)
	                            {
	                                continue;
	                            }
	                            else if (state2 != null && (state2.getBlock() instanceof BlockDynamicLeaves || state2.getMaterial() == Material.LEAVES))
	                    		 {
	                            	states[1] = state2;
	                            	states[2] = pos2;

	                            	if(species != null)
	                            	{
	                            		ItemStack stack = new ItemStack(species.getSeed(), 1);
		                            	entity.setHeldItem(EnumHand.MAIN_HAND, stack);
	                            	}

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