package net.daveyx0.primitivemobs.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySkeletonWarrior;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.passive.*;
import net.daveyx0.primitivemobs.spawn.PrimitiveMobsSpawnEntry;

public class PrimitiveMobsSpawnList
{
    private static final List<PrimitiveMobsSpawnEntry> SPAWNS = new ArrayList<PrimitiveMobsSpawnEntry>();

    //Creature spawns
    public static final PrimitiveMobsSpawnEntry CHAMELEON_SPAWNS;
    public static final PrimitiveMobsSpawnEntry FILCHLIZARD_SPAWNS;
    public static final PrimitiveMobsSpawnEntry GROVESPRITE_SPAWNS;
    
    //Mob spawns
    public static final PrimitiveMobsSpawnEntry BRAINSLIME_SPAWNS;
    public static final PrimitiveMobsSpawnEntry HAUNTEDTOOL_SPAWNS;
    public static final PrimitiveMobsSpawnEntry TREASURESLIME_SPAWNS;
    public static final PrimitiveMobsSpawnEntry ROCKETCREEPER_SPAWNS;
    public static final PrimitiveMobsSpawnEntry SUPPORTCREEPER_SPAWNS;
    public static final PrimitiveMobsSpawnEntry FESTIVECREEPER_SPAWNS;
    public static final PrimitiveMobsSpawnEntry BEWITCHEDTOME_SPAWNS;
    public static final PrimitiveMobsSpawnEntry SKELETONWARRIOR_SPAWNS;
    public static final PrimitiveMobsSpawnEntry LILYLURKER_SPAWNS;
    
    //Nether spawns
    public static final PrimitiveMobsSpawnEntry BLAZINGJUGGERNAUT_SPAWNS;
    
    public static List<PrimitiveMobsSpawnEntry> getSpawnEntries()
    {
        return SPAWNS;
    }

    static
    {
    	CHAMELEON_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityChameleon.class, 0.25F)
        {
            @Override
            public boolean isBiomeSuitable(Biome biome)
            {
                return BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS) && super.isBiomeSuitable(biome);
            }
        });
        
    	FILCHLIZARD_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityFilchLizard.class, 0.25F)
        {
            @Override
            public boolean isBiomeSuitable(Biome biome)
            {
                return BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY) && super.isBiomeSuitable(biome);
            }
        });
    	
    	GROVESPRITE_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityGroveSprite.class, 0.25F)
        {
            @Override
            public boolean isBiomeSuitable(Biome biome)
            {
                return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) && super.isBiomeSuitable(biome);
            }
        });
    	
    	BRAINSLIME_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityBrainSlime.class, 0.5F)
        {
            @Override
            public boolean isBiomeSuitable(Biome biome)
            {
                return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.BEACH) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER)) && super.isBiomeSuitable(biome);
            }
        });
    	
    	HAUNTEDTOOL_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityHauntedTool.class, 0.15F), new Type[]{Type.BEACH});
    	ROCKETCREEPER_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityRocketCreeper.class, 0.25F));
    	FESTIVECREEPER_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityFestiveCreeper.class, 0.15F));
    	SUPPORTCREEPER_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntitySupportCreeper.class, 0.15F));
    	TREASURESLIME_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityTreasureSlime.class, 0.2F));
    	BEWITCHEDTOME_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityEnchantedBook.class, 0.3F));
    	SKELETONWARRIOR_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntitySkeletonWarrior.class, 0.25F));
    	
    	LILYLURKER_SPAWNS = registerSpawnWater(new PrimitiveMobsSpawnEntry(EntityLilyLurker.class, 0.3F)
        {
            @Override
            public boolean isBiomeSuitable(Biome biome)
            {
                return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) && super.isBiomeSuitable(biome);
            }
        });
    	
    	BLAZINGJUGGERNAUT_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityBlazingJuggernaut.class, 0.25F)
        {
            @Override
            public boolean isBiomeSuitable(Biome biome)
            {
                return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.END);
            }
        });
    }
    
    public static void postInit() {

        registerSpawns();
    }

    public static void registerSpawns() {
       
    }
    
    private static PrimitiveMobsSpawnEntry registerSpawnNormal(PrimitiveMobsSpawnEntry entry, BiomeDictionary.Type[]... array ) {

    	SPAWNS.add(entry);
    	EntitySpawnPlacementRegistry.setPlacementType(entry.getEntityClass(), EntityLiving.SpawnPlacementType.ON_GROUND);
    	return entry;
    }
    
    private static PrimitiveMobsSpawnEntry registerSpawnWater(PrimitiveMobsSpawnEntry entry) {

    	SPAWNS.add(entry);
    	EntitySpawnPlacementRegistry.setPlacementType(entry.getEntityClass(), EntityLiving.SpawnPlacementType.IN_WATER);
    	return entry;
    }
    
    private static PrimitiveMobsSpawnEntry registerSpawnFlying(PrimitiveMobsSpawnEntry entry) {

    	SPAWNS.add(entry);
    	EntitySpawnPlacementRegistry.setPlacementType(entry.getEntityClass(), EntityLiving.SpawnPlacementType.IN_AIR);
    	return entry;
    }
}