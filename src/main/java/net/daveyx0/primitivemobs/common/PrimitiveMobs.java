package net.daveyx0.primitivemobs.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.logging.Logger;

import net.daveyx0.primitivemobs.client.TabPrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfig;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.daveyx0.primitivemobs.core.PrimitiveMobsEntities;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLootTables;
import net.daveyx0.primitivemobs.core.PrimitiveMobsMessages;
import net.daveyx0.primitivemobs.core.PrimitiveMobsParticles;
import net.daveyx0.primitivemobs.core.PrimitiveMobsRecipes;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSoundEvents;
import net.daveyx0.primitivemobs.core.PrimitiveMobsSpawnList;
import net.daveyx0.primitivemobs.event.PrimitiveMobsEventHandler;
import net.daveyx0.primitivemobs.event.PrimitiveMobsSpawnerEventHandler;
import net.daveyx0.primitivemobs.loot.HauntedToolLoot;
import net.daveyx0.primitivemobs.loot.TreasureSlimeLoot;
import net.daveyx0.primitivemobs.network.PrimitiveNetworkWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

@Mod(modid= PrimitiveMobsReference.MODID, name = PrimitiveMobsReference.NAME, version = PrimitiveMobsReference.VERSION, acceptedMinecraftVersions = "[1.12]",	
		guiFactory = "net.daveyx0."+ PrimitiveMobsReference.MODID+".config.PrimitiveMobsFactoryGui")

public class PrimitiveMobs {
	
	@Instance(PrimitiveMobsReference.MODID)
	public static PrimitiveMobs instance = new PrimitiveMobs();
	
	public static TabPrimitiveMobs tabPrimitiveMobs;
	
	public static PrimitiveNetworkWrapper network;
	
	
	@SidedProxy(clientSide = "net.daveyx0.primitivemobs.client.PrimitiveMobsClientProxy", serverSide = "net.daveyx0.primitivemobs.common.PrimitiveMobsCommonProxy")
	public static PrimitiveMobsCommonProxy proxy;
	
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new PrimitiveMobs());
		PrimitiveMobsLogger.preInit();
		tabPrimitiveMobs = new TabPrimitiveMobs(CreativeTabs.getNextID(),"tabPrimitiveMobs");
		
		network = new PrimitiveNetworkWrapper("primitivemobs");
		
		PrimitiveMobsConfig.load(event);
		
		PrimitiveMobsMessages.preInit();
		PrimitiveMobsEntities.preInit();
		PrimitiveMobsLootTables.preInit();
		PrimitiveMobsRecipes.init();
		
		MinecraftForge.EVENT_BUS.register(new PrimitiveMobsEventHandler());
		MinecraftForge.EVENT_BUS.register(new PrimitiveMobsSpawnerEventHandler());
		
		proxy.preInit(event);		
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{

		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		PrimitiveMobsSpawnList.postInit();
		proxy.postInit(event);
	}
	
	  
	public static SimpleNetworkWrapper getSimpleNetworkWrapper()
	{
	  return network.network;
	}
		

}
