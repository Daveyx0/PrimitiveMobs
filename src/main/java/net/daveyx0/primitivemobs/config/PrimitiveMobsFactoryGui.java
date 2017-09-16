package net.daveyx0.primitivemobs.config;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class PrimitiveMobsFactoryGui implements IModGuiFactory {
	
	@Override
	public void initialize(Minecraft minecraftInstance) {

	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}


	@Override
	public boolean hasConfigGui() {
		
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {

		return new PrimitiveMobsGuiConfig(parentScreen);
	}

}