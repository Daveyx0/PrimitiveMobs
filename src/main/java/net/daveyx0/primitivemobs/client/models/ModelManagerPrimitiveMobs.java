package net.daveyx0.primitivemobs.client.models;

import java.util.HashSet;
import java.util.Set;

import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.util.IVariant;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelManagerPrimitiveMobs
{
	public static final ModelManagerPrimitiveMobs INSTANCE = new ModelManagerPrimitiveMobs();


	private ModelManagerPrimitiveMobs() {
	}

	public void registerAllModels() {

		registerBlockModels();
		registerItemModels();
	}
	
	private void registerBlockModels() {
		
		//ModBlocks.BLOCKS.stream().filter(block -> !itemsRegistered.contains(Item.getItemFromBlock(block))).forEach(this::registerBlockItemModel);
	}

	private final Set<Item> itemsRegistered = new HashSet<>();

	private void registerItemModels() {

		//registerItemModel(ModItems.SNOWBALL_LAUNCHER, "item:fishing_rod");
		
		PrimitiveMobsItems.ITEMS.stream().filter(item -> !itemsRegistered.contains(item)).forEach(this::registerItemModel);
	}
	
	public void registerItemColors() {
		
		registerItemColor(new IItemColor()
        {
			public int getColorFromItemstack(ItemStack stack, int tintIndex)
            {
                return tintIndex > 0 ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
            }
        }, new Item[] {PrimitiveMobsItems.CAMOUFLAGE_HELMET, PrimitiveMobsItems.CAMOUFLAGE_CHEST, PrimitiveMobsItems.CAMOUFLAGE_LEGS, PrimitiveMobsItems.CAMOUFLAGE_BOOTS});
	}
	
	private void registerItemColor(IItemColor itemcolor, Item... itemsIn)
	{
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(itemcolor, itemsIn);
	}

	private void registerBlockItemModel(Block block) {
		final Item item = Item.getItemFromBlock(block);
		if (item != null) {
			registerItemModel(item);
		}
	}

	private void registerBlockItemModel(Block block, String modelLocation) {
		final Item item = Item.getItemFromBlock(block);
		if (item != null) {
			registerItemModel(item, modelLocation);
		}
	}

	/**
	 *
	 * @author Choonster
	 */
	private <T extends IVariant> void registerVariantBlockItemModels(Block block, String variantName, T[] variants) {
		final Item item = Item.getItemFromBlock(block);
		if (item != null) {
			registerVariantItemModels(item, variantName, variants);
		}
	}

	private void registerBlockItemModel(Block block, ModelResourceLocation fullModelLocation) {
		final Item item = Item.getItemFromBlock(block);
		if (item != null) {
			registerItemModel(item);
		}
	}

	private void registerBlockItemModelForMeta(Block block, int metadata, String variant) {
		final Item item = Item.getItemFromBlock(block);
		if (item != null) {
			registerItemModelForMeta(item, metadata, variant);
		}
	}

	private void registerItemModel(Item item) {
		registerItemModel(item, item.getRegistryName().toString());
	}

	private void registerItemModel(Item item, String modelLocation) {
		final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
		registerItemModel(item, fullModelLocation);
	}

	private void registerItemModel(Item item, ModelResourceLocation fullModelLocation) {
		ModelBakery.registerItemVariants(item, fullModelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
		registerItemModel(item, MeshDefinitionFix.create(stack -> fullModelLocation));
	}

	private void registerItemModel(Item item, ItemMeshDefinition meshDefinition) {
		itemsRegistered.add(item);
		ModelLoader.setCustomMeshDefinition(item, meshDefinition);
	}

	private <T extends IVariant> void registerVariantItemModels(Item item, String variantName, T[] variants) {
		for (T variant : variants) {
			registerItemModelForMeta(item, variant.getMeta(), variantName + "=" + variant.getName());
		}
	}

	private void registerItemModelForMeta(Item item, int metadata, String variant) {
		registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
	}

	private void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation modelResourceLocation) {
		itemsRegistered.add(item);
		ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
	}
}