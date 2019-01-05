package net.daveyx0.primitivemobs.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.daveyx0.multimob.core.MMTameableEntries;
import net.daveyx0.multimob.modint.MMModIntegrationRegistry;
import net.daveyx0.primitivemobs.client.TabPrimitiveMobs;
import net.daveyx0.primitivemobs.common.PrimitiveMobsCommonProxy;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfig;
import net.daveyx0.primitivemobs.message.PrimitiveMobsMessageRegistry;
import net.daveyx0.primitivemobs.modint.PrimitiveMobsDTIntegration;
import net.daveyx0.primitivemobs.modint.PrimitiveMobsJERIntegration;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid= PrimitiveMobsReference.MODID, name = PrimitiveMobsReference.NAME, version = PrimitiveMobsReference.VERSION, acceptedMinecraftVersions = "[1.12]",	
		guiFactory = "net.daveyx0."+ PrimitiveMobsReference.MODID+".config.PrimitiveMobsFactoryGui", dependencies= "required-after:multimob")

public class PrimitiveMobs {

	public static final Logger LOGGER = LogManager.getLogger(PrimitiveMobsReference.MODID);

	@Instance(PrimitiveMobsReference.MODID)
	public static PrimitiveMobs instance = new PrimitiveMobs();
	
	public static TabPrimitiveMobs tabPrimitiveMobs;
	
	@SidedProxy(clientSide = "net.daveyx0.primitivemobs.client.PrimitiveMobsClientProxy", serverSide = "net.daveyx0.primitivemobs.common.PrimitiveMobsCommonProxy")
	public static PrimitiveMobsCommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new PrimitiveMobs());
		MinecraftForge.EVENT_BUS.register(new PrimitiveMobsEvents.EntityEventHandler());
		
		PrimitiveMobsLogger.preInit();
		tabPrimitiveMobs = new TabPrimitiveMobs(CreativeTabs.getNextID(),"tabPrimitiveMobs");
		
		PrimitiveMobsConfig.load(event);

		PrimitiveMobsMessageRegistry.registerMessages();
		PrimitiveMobsEntityRegistry.registerEntities();
		PrimitiveMobsLootTables.registerLootTables();
		proxy.preInit(event);		
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		PrimitiveMobsMapGen.registerWorldGenerators();
		PrimitiveMobsSpawnRegistry.registerSpawns();
		PrimitiveTameableEntries.registerTameables();
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		PrimitiveMobsRecipes.registerRecipes();
		proxy.postInit(event);
	}
		
}
