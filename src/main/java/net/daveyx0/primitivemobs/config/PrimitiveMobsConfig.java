package net.daveyx0.primitivemobs.config;

import java.io.File;

import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PrimitiveMobsConfig {

	static Configuration config;
	public static void load(FMLPreInitializationEvent event) {
		File dir = getPrimitiveMobsConfigurationLocation(event);
		
		if(!dir.exists())
		{
			dir.mkdirs();
		}

		config = new Configuration(new File(dir, "primitivemobs.cfg"));
		reloadConfig();
		
		MinecraftForge.EVENT_BUS.register(new PrimitiveMobsConfig());
	}

	private static void reloadConfig() {
		
		PrimitiveMobsConfigMobs.load(config);
		PrimitiveMobsConfigSpecial.load(config);

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(PrimitiveMobsReference.MODID)) {
			reloadConfig();
		}
	}
	
	public static File getPrimitiveMobsConfigurationLocation(FMLPreInitializationEvent event)
	{
		return new File(event.getModConfigurationDirectory(), "primitivemobs");
	}
}