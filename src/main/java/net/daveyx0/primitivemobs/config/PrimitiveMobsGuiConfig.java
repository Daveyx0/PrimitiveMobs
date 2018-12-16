package net.daveyx0.primitivemobs.config;

import java.util.List;
import java.util.stream.Collectors;

import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class PrimitiveMobsGuiConfig extends GuiConfig {

	public PrimitiveMobsGuiConfig(GuiScreen parentScreen) 
	{
		super(parentScreen, getConfigElements(), PrimitiveMobsReference.MODID, false, false, "primitivemobs.config.title");
	}

	private static List<IConfigElement> getConfigElements() 
	{
		return PrimitiveMobsConfig.config.getCategoryNames().stream()
				.map(categoryName -> new ConfigElement(PrimitiveMobsConfig.config.getCategory(categoryName).setLanguageKey("primitivemobs.config." + categoryName)))
				.collect(Collectors.toList());
	}
}
