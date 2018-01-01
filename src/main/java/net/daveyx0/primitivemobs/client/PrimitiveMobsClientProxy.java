package net.daveyx0.primitivemobs.client;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.client.models.ModelManagerPrimitiveMobs;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderChameleon;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.common.PrimitiveMobsCommonProxy;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.daveyx0.primitivemobs.core.PrimitiveMobsEntities;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class PrimitiveMobsClientProxy extends PrimitiveMobsCommonProxy {
	
	private final Minecraft MINECRAFT = Minecraft.getMinecraft();
	
	 @Override
	    public void preInit(FMLPreInitializationEvent event) {
	        OBJLoader.INSTANCE.addDomain(PrimitiveMobsReference.MODID);
	        
	        MinecraftForge.EVENT_BUS.register(ModelManagerPrimitiveMobs.INSTANCE);
	        
	        PrimitiveMobsEntities.registerRenderers();
	    }

	    @Override
	    public void init(FMLInitializationEvent event) {
	    	
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent event) {
	    	ModelManagerPrimitiveMobs.INSTANCE.registerItemColors();
	    }
	    
		@Nullable
		@Override
		public EntityPlayer getClientPlayer() {
			return MINECRAFT.player;
		}

		@Nullable
		@Override
		public World getClientWorld() {
			return MINECRAFT.world;
		}

		@Override
		public IThreadListener getThreadListener(final MessageContext context) {
			if (context.side.isClient()) {
				return MINECRAFT;
			} else {
				return context.getServerHandler().player.mcServer;
			}
		}

		@Override
		public EntityPlayer getPlayer(final MessageContext context) {
			if (context.side.isClient()) {
				return MINECRAFT.player;
			} else {
				return context.getServerHandler().player;
			}
		}
	    
}