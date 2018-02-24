package net.daveyx0.primitivemobs.client.renderer.entity;

import java.lang.reflect.Field;
import java.util.Map;

import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerSpiritEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderManagerPrimitiveMobs {

	 /**
     * @author Alex-the-666
     **/
	public static void addRenderLayers()
	{
		for (Map.Entry<Class<? extends Entity>, Render<? extends Entity>> entry : Minecraft.getMinecraft().getRenderManager().entityRenderMap.entrySet()) {
			Render render = entry.getValue();
			if (render instanceof RenderLivingBase && EntityLiving.class.isAssignableFrom(entry.getKey())) {
				((RenderLivingBase) render).addLayer(new LayerSpiritEntity((RenderLivingBase) render));

			}
		}

		Field renderingRegistryField = ReflectionHelper.findField(RenderingRegistry.class, ObfuscationReflectionHelper.remapFieldNames(RenderingRegistry.class.getName(), new String[]{"INSTANCE", "INSTANCE"}));
		Field entityRendersField = ReflectionHelper.findField(RenderingRegistry.class, ObfuscationReflectionHelper.remapFieldNames(RenderingRegistry.class.getName(), new String[]{"entityRenderers", "entityRenderers"}));
		Field entityRendersOldField = ReflectionHelper.findField(RenderingRegistry.class, ObfuscationReflectionHelper.remapFieldNames(RenderingRegistry.class.getName(), new String[]{"entityRenderersOld", "entityRenderersOld"}));
		RenderingRegistry registry = null;
		try 
		{
			Field modifier = Field.class.getDeclaredField("modifiers");
			modifier.setAccessible(true);
			registry = (RenderingRegistry) renderingRegistryField.get(null);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		if (registry != null) 
		{
			Map<Class<? extends Entity>, IRenderFactory<? extends Entity>> entityRenders = null;
			Map<Class<? extends Entity>, Render<? extends Entity>> entityRendersOld = null;
			try 
			{
				Field modifier1 = Field.class.getDeclaredField("modifiers");
				modifier1.setAccessible(true);
				entityRenders = (Map<Class<? extends Entity>, IRenderFactory<? extends Entity>>) entityRendersField.get(registry);
				entityRendersOld = (Map<Class<? extends Entity>, Render<? extends Entity>>) entityRendersOldField.get(registry);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (entityRenders != null) {
				for (Map.Entry<Class<? extends Entity>, IRenderFactory<? extends Entity>> entry : entityRenders.entrySet()) {
					Render render = entry.getValue().createRenderFor(Minecraft.getMinecraft().getRenderManager());
					if (render instanceof RenderLivingBase && EntityLiving.class.isAssignableFrom(entry.getKey())) {
						((RenderLivingBase) render).addLayer(new LayerSpiritEntity((RenderLivingBase) render));

					}
				}
			}
			if (entityRendersOld != null) {
				for (Map.Entry<Class<? extends Entity>, Render<? extends Entity>> entry : entityRendersOld.entrySet()) {
					Render render = entry.getValue();
					if (render instanceof RenderLivingBase && EntityLiving.class.isAssignableFrom(entry.getKey())) {
						((RenderLivingBase) render).addLayer(new LayerSpiritEntity((RenderLivingBase) render));

					}
				}
			}
		}

	}
}
