package net.daveyx0.primitivemobs.spawn;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class PrimitiveMobsSpawnEntry
{
    public Class<? extends EntityLiving> entityClass;
    public float rarity;

    public PrimitiveMobsSpawnEntry(Class<? extends EntityLiving> entityClassIn, float rarityIn)
    {
        this.entityClass = entityClassIn;
        this.rarity = rarityIn;
    }
    
    public Class getEntityClass()
    {
    	return entityClass;
    }

    public boolean isBiomeTypeSuitable(Biome biome)
    {
        return !BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.END);
    }
    
    public boolean isBiomeSuitable(Biome biome)
    {
        return !BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.END);
    }
    
    public boolean isWaterCreature()
    {
    	return false;
    }
    
    public boolean isBlockBelowSuitable(IBlockState state) {return true;};
    
    public boolean isCorrectLightLevel(World world, BlockPos pos) {return true;};
    
    public boolean isNearSpecifiedBlock(World world) {return true;};
    
    public boolean isNearSpecifiedEntity(World world) {return true;};
    
    public boolean isBetweenHeightLevels(BlockPos pos) {return true;};
    
    public int getAdditionalRarity()
    {
    	return 1;
    }
}