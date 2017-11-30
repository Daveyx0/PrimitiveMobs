package net.daveyx0.primitivemobs.core;

import java.util.ArrayList;
import java.util.List;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpawns;
import net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySkeletonWarrior;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.daveyx0.primitivemobs.entity.passive.EntityFilchLizard;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.spawn.PrimitiveMobsSpawnEntry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class PrimitiveMobsSpawnList {
	private static final List<PrimitiveMobsSpawnEntry> SPAWNS = new ArrayList<PrimitiveMobsSpawnEntry>();

	// Creature spawns
	public static PrimitiveMobsSpawnEntry CHAMELEON_SPAWNS;
	public static PrimitiveMobsSpawnEntry FILCHLIZARD_SPAWNS;
	public static PrimitiveMobsSpawnEntry GROVESPRITE_SPAWNS;

	// Mob spawns
	public static PrimitiveMobsSpawnEntry BRAINSLIME_SPAWNS;
	public static PrimitiveMobsSpawnEntry HAUNTEDTOOL_SPAWNS;
	public static PrimitiveMobsSpawnEntry TREASURESLIME_SPAWNS;
	public static PrimitiveMobsSpawnEntry ROCKETCREEPER_SPAWNS;
	public static PrimitiveMobsSpawnEntry SUPPORTCREEPER_SPAWNS;
	public static PrimitiveMobsSpawnEntry FESTIVECREEPER_SPAWNS;
	public static PrimitiveMobsSpawnEntry BEWITCHEDTOME_SPAWNS;
	public static PrimitiveMobsSpawnEntry SKELETONWARRIOR_SPAWNS;
	public static PrimitiveMobsSpawnEntry LILYLURKER_SPAWNS;
	public static PrimitiveMobsSpawnEntry SPIDERFAMILY_SPAWNS;

	// Nether spawns
	public static PrimitiveMobsSpawnEntry BLAZINGJUGGERNAUT_SPAWNS;

	public static List<PrimitiveMobsSpawnEntry> getSpawnEntries() {
		return SPAWNS;
	}

	public static void preInit() {
		CHAMELEON_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityChameleon.class, PrimitiveMobsConfigSpawns.chameleonSpawnRate) {
			@Override
			public boolean isBiomeSuitable(Biome biome) {
				return BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS) && super.isBiomeSuitable(biome);
			}
		});

		FILCHLIZARD_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityFilchLizard.class, PrimitiveMobsConfigSpawns.filchLizardSpawnRate) {
			@Override
			public boolean isBiomeSuitable(Biome biome) {
				return BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY) && super.isBiomeSuitable(biome);
			}
		});

		GROVESPRITE_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityGroveSprite.class, PrimitiveMobsConfigSpawns.groveSpriteSpawnRate) {
			@Override
			public boolean isBiomeSuitable(Biome biome) {
				return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) && super.isBiomeSuitable(biome);
			}
		});

		BRAINSLIME_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityBrainSlime.class, PrimitiveMobsConfigSpawns.brainSlimeSpawnRate) {
			@Override
			public boolean isBiomeSuitable(Biome biome) {
				return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.BEACH)
						|| BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER)) && super.isBiomeSuitable(biome);
			}
		});

		HAUNTEDTOOL_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityHauntedTool.class, PrimitiveMobsConfigSpawns.hauntedToolSpawnRate),
				new Type[] { Type.BEACH });
		ROCKETCREEPER_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityRocketCreeper.class, PrimitiveMobsConfigSpawns.rocketCreeperSpawnRate));
		FESTIVECREEPER_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityFestiveCreeper.class, PrimitiveMobsConfigSpawns.festiveCreeperSpawnRate));
		SUPPORTCREEPER_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntitySupportCreeper.class, PrimitiveMobsConfigSpawns.supportCreeperSpawnRate));
		TREASURESLIME_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityTreasureSlime.class, PrimitiveMobsConfigSpawns.treasureslimeSpawnRate));
		BEWITCHEDTOME_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityEnchantedBook.class, PrimitiveMobsConfigSpawns.bewitchedTomeSpawnRate));
		SKELETONWARRIOR_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntitySkeletonWarrior.class, PrimitiveMobsConfigSpawns.skeletonWarriorSpawnRate));

		LILYLURKER_SPAWNS = registerSpawnWater(new PrimitiveMobsSpawnEntry(EntityLilyLurker.class, PrimitiveMobsConfigSpawns.lilyLurkerSpawnRate) {
			@Override
			public boolean isBiomeSuitable(Biome biome) {
				return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) && super.isBiomeSuitable(biome);
			}
		});

		BLAZINGJUGGERNAUT_SPAWNS = registerSpawnNormal(
				new PrimitiveMobsSpawnEntry(EntityBlazingJuggernaut.class, PrimitiveMobsConfigSpawns.blazingJuggernautSpawnRate) {
					@Override
					public boolean isBiomeSuitable(Biome biome) {
						return (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER))
								&& !BiomeDictionary.hasType(biome, BiomeDictionary.Type.END);
					}
				});
		
		SPIDERFAMILY_SPAWNS = registerSpawnNormal(new PrimitiveMobsSpawnEntry(EntityMotherSpider.class, PrimitiveMobsConfigSpawns.spiderFamilySpawnRate));
	}

	public static void postInit() {

		registerSpawns();
	}

	public static void registerSpawns() {

	}

	private static PrimitiveMobsSpawnEntry registerSpawnNormal(PrimitiveMobsSpawnEntry entry,
			BiomeDictionary.Type[]... array) {
		if (PrimitiveMobsEntities.enabledEntities.containsKey(entry.entityClass) && !PrimitiveMobsEntities.enabledEntities.get(entry.entityClass))
			return entry;

		SPAWNS.add(entry);
		EntitySpawnPlacementRegistry.setPlacementType(entry.getEntityClass(),
				EntityLiving.SpawnPlacementType.ON_GROUND);
		return entry;
	}

	private static PrimitiveMobsSpawnEntry registerSpawnWater(PrimitiveMobsSpawnEntry entry) {
		if (PrimitiveMobsEntities.enabledEntities.containsKey(entry.entityClass) && !PrimitiveMobsEntities.enabledEntities.get(entry.entityClass))
			return entry;

		SPAWNS.add(entry);
		EntitySpawnPlacementRegistry.setPlacementType(entry.getEntityClass(), EntityLiving.SpawnPlacementType.IN_WATER);
		return entry;
	}

	private static PrimitiveMobsSpawnEntry registerSpawnFlying(PrimitiveMobsSpawnEntry entry) {
		if (PrimitiveMobsEntities.enabledEntities.containsKey(entry.entityClass) && !PrimitiveMobsEntities.enabledEntities.get(entry.entityClass))
			return entry;

		SPAWNS.add(entry);
		EntitySpawnPlacementRegistry.setPlacementType(entry.getEntityClass(), EntityLiving.SpawnPlacementType.IN_AIR);
		return entry;
	}
}