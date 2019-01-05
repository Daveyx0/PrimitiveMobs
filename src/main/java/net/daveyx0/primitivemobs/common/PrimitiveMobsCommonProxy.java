package net.daveyx0.primitivemobs.common;

import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.multimob.modint.MMModIntegrationRegistry;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.modint.PrimitiveMobsDTIntegration;
import net.daveyx0.primitivemobs.modint.PrimitiveMobsJERIntegration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
public class PrimitiveMobsCommonProxy
{

	public PrimitiveMobsDTIntegration DynamicTreesInt;

public void preInit(FMLPreInitializationEvent event) {

}

public void init(FMLInitializationEvent event) {

}

public void postInit(FMLPostInitializationEvent event) {

	MMModIntegrationRegistry.registerModIntegration(new PrimitiveMobsJERIntegration());
	DynamicTreesInt = (PrimitiveMobsDTIntegration)MMModIntegrationRegistry.registerModIntegration(new PrimitiveMobsDTIntegration());
}

}