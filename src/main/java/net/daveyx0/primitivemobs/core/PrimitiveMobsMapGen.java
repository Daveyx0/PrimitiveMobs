package net.daveyx0.primitivemobs.core;

import net.daveyx0.primitivemobs.world.gen.WorldGenMimic;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PrimitiveMobsMapGen 
{
	public static void registerMapGen() {
	}

	public static void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new WorldGenMimic(), 100);
	}
}