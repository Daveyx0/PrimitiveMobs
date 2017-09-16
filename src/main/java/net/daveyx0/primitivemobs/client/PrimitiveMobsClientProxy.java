package net.daveyx0.primitivemobs.client;

import net.daveyx0.primitivemobs.client.models.ModelManagerPrimitiveMobs;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderChameleon;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.common.PrimitiveMobsCommonProxy;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.daveyx0.primitivemobs.core.PrimitiveMobsEntities;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class PrimitiveMobsClientProxy extends PrimitiveMobsCommonProxy 
{
	
	 @Override
	    public void preInit(FMLPreInitializationEvent event) {
		 	
	        OBJLoader.INSTANCE.addDomain(PrimitiveMobsReference.MODID);
	        ModelManagerPrimitiveMobs.INSTANCE.registerAllModels();
	        
	        PrimitiveMobsEntities.registerRenderers();
	    }

	    @Override
	    public void init(FMLInitializationEvent event) {
	    	
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent event) {
	    	ModelManagerPrimitiveMobs.INSTANCE.registerItemColors();
	    }

   
}