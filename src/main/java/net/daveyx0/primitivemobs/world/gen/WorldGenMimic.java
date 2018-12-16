package net.daveyx0.primitivemobs.world.gen;

import java.util.Random;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenMimic implements IWorldGenerator {

	private void generateMimicChest(Random rand, World world, BlockPos pos) {

		BlockPos tempPos = new BlockPos(pos.getX() + rand.nextInt(16) + 8, 0, pos.getZ() + rand.nextInt(16) + 8);
		BlockPos newPos = getAboveSolidOrLiquidBlock(world, tempPos);
		
		if(newPos != null)
		{
			//PrimitiveMobsLogger.info("Spawned Chest at: " + newPos.getX() + " " + newPos.getY() + " " + newPos.getZ());

			world.setBlockState(newPos, Blocks.CHEST.getDefaultState());

			final TileEntity tileEntity = world.getTileEntity(newPos);
			if (tileEntity instanceof TileEntityChest) {
			
				((TileEntityChest)tileEntity).getTileData().setInteger("Mimic", 1);
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		int chance = PrimitiveMobsConfigSpecial.getMimicRarity();
		if (PrimitiveMobsConfigMobs.enableMimic && PrimitiveMobsConfigSpecial.getMimicGeneratesInCaves()
				&& PrimitiveMobsConfigSpecial.getMimicRarity() > 0 && world.provider.getDimension() == 0 && random.nextInt(chance) == 0) {
			
			final BlockPos basePos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
			generateMimicChest(random, world, basePos);
		}
	}
	
    public BlockPos getAboveSolidOrLiquidBlock(World world, BlockPos pos)
    {
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        BlockPos blockpos = null;
        BlockPos blockpos1;

        for (int i = 5; i < 40; i++)
        {
        	blockpos = new BlockPos(pos.getX(), i, pos.getZ());
            blockpos1 = blockpos.down();
            IBlockState state1 = chunk.getBlockState(blockpos1);

            if (world.isAirBlock(blockpos) && state1.getMaterial().blocksMovement() && !state1.getBlock().isLeaves(state1, world, blockpos1) && !state1.getBlock().isFoliage(world, blockpos1))
            {
            	break;
            }
            else
            {
                blockpos = null;
            }
        }
        
        return blockpos;


    }
}