package net.daveyx0.primitivemobs.client;

import net.daveyx0.primitivemobs.common.PrimitiveMobsCommonProxy;
import net.daveyx0.primitivemobs.core.PrimitiveMobsEntityRegistry;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value= Side.CLIENT, modid = PrimitiveMobsReference.MODID)
public class PrimitiveMobsClientProxy extends PrimitiveMobsCommonProxy {
	
	 @Override
	    public void preInit(FMLPreInitializationEvent event) {
	        OBJLoader.INSTANCE.addDomain(PrimitiveMobsReference.MODID);
	        PrimitiveMobsEntityRegistry.registerRenderers();
	        super.preInit(event);
	    }

	    @Override
	    public void init(FMLInitializationEvent event) {
	    	super.init(event);
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent event) {
	    	PrimitiveMobsItems.registerItemColors();
	    	super.postInit(event);
	    }
	    
}