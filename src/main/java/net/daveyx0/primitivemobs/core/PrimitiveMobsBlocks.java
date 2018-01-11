package net.daveyx0.primitivemobs.core;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

import net.daveyx0.primitivemobs.client.models.ModelManagerPrimitiveMobs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsBlocks {
	
	
	public static final Set<Block> BLOCKS = new HashSet<>();
	
	@Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
	public static class RegistrationHandler {
		public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			final IForgeRegistry<Block> registry = event.getRegistry();

			final Block[] blocks = {
					//CHEST
			};
			registry.registerAll(blocks);
		}

		/*private static void registerSlabGroup(final IForgeRegistry<Block> registry, final BlockSlabTestMod3.SlabGroup<?, ?, ?> slabGroup) {
		}*/

		@SubscribeEvent
		public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
			final ItemBlock[] items = {
					//new ItemBlock(CHEST)
			};

			final IForgeRegistry<Item> registry = event.getRegistry();

			for (final ItemBlock item : items) {
				final Block block = item.getBlock();
				final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
				PrimitiveMobsLogger.info("Setting itemblock to " + registryName);
				registry.register(item.setRegistryName(registryName));
				ITEM_BLOCKS.add(item);
			}

			registerTileEntities();
		}
	}

	private static void registerTileEntities() {
		//registerTileEntity(TileEntityPrimitiveChest.class, "chest");
	}

	private static void registerTileEntity(final Class<? extends TileEntity> tileEntityClass, final String name) {
		GameRegistry.registerTileEntity(tileEntityClass, PrimitiveMobsReference.RESOURCE_PREFIX + name);
	}
}