package net.daveyx0.primitivemobs.client.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsBlocks;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.util.IVariant;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelManagerPrimitiveMobs
{
	public static final ModelManagerPrimitiveMobs INSTANCE = new ModelManagerPrimitiveMobs();


	private ModelManagerPrimitiveMobs() {
	}
	
	@SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
		registerBlockModels();
		registerItemModels();
	}

	private void registerBlockModels() {

	}

	private final Set<Item> itemsRegistered = new HashSet<>();

	private void registerItemModels() {
		
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
	
	/**
	 * A {@link StateMapperBase} used to create property strings.
	 */
	private final StateMapperBase propertyStringMapper = new StateMapperBase() {
		@Override
		protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
			return new ModelResourceLocation("minecraft:air");
		}
	};
	
	private void registerItemColor(IItemColor itemcolor, Item... itemsIn)
	{
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(itemcolor, itemsIn);
	}

	/**
	 * Register a single model for the {@link Block}'s {@link Item}.
	 * <p>
	 * Uses the registry name as the domain/path and the {@link IBlockState} as the variant.
	 *
	 * @param state The state to use as the variant
	 */
	private void registerBlockItemModel(final IBlockState state) {
		final Block block = state.getBlock();
		final Item item = Item.getItemFromBlock(block);

		if (item != Items.AIR) {
			final ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());
			registerItemModel(item, new ModelResourceLocation(registryName, "inventory"));
		}
	}

	/**
	 * Register a model for a metadata value of the {@link Block}'s {@link Item}.
	 * <p>
	 * Uses the registry name as the domain/path and the {@link IBlockState} as the variant.
	 *
	 * @param state    The state to use as the variant
	 * @param metadata The item metadata to register the model for
	 */
	private void registerBlockItemModelForMeta(final IBlockState state, final int metadata) {
		final Item item = Item.getItemFromBlock(state.getBlock());

		if (item != Items.AIR) {
			registerItemModelForMeta(item, metadata, propertyStringMapper.getPropertyString(state.getProperties()));
		}
	}

	/**
	 * Register a model for each metadata value of the {@link Block}'s {@link Item} corresponding to the values of an {@link IProperty}.
	 * <p>
	 * For each value:
	 * <li>The domain/path is the registry name</li>
	 * <li>The variant is {@code baseState} with the {@link IProperty} set to the value</li>
	 * <p>
	 * The {@code getMeta} function is used to get the metadata of each value.
	 *
	 * @param baseState The base state to use for the variant
	 * @param property  The property whose values should be used
	 * @param getMeta   A function to get the metadata of each value
	 * @param <T>       The value type
	 */
	private <T extends Comparable<T>> void registerVariantBlockItemModels(final IBlockState baseState, final IProperty<T> property, final ToIntFunction<T> getMeta) {
		property.getAllowedValues().forEach(value -> registerBlockItemModelForMeta(baseState.withProperty(property, value), getMeta.applyAsInt(value)));
	}

	/**
	 * Register a model for each metadata value of the {@link Block}'s {@link Item} corresponding to the values of an {@link IProperty}.
	 * <p>
	 * For each value:
	 * <li>The domain/path is the registry name</li>
	 * <li>The variant is {@code baseState} with the {@link IProperty} set to the value</li>
	 * <p>
	 * {@link IVariant#getMeta()} is used to get the metadata of each value.
	 *
	 * @param baseState The base state to use for the variant
	 * @param property  The property whose values should be used
	 * @param <T>       The value type
	 */
	private <T extends IVariant & Comparable<T>> void registerVariantBlockItemModels(final IBlockState baseState, final IProperty<T> property) {
		registerVariantBlockItemModels(baseState, property, IVariant::getMeta);
	}
	
	 /**
	 * Register a single model for an {@link Item}.
	 * <p>
	 * Uses the registry name as the domain/path and {@code "inventory"} as the variant.
	 *
	 * @param item The Item
	 */
	private void registerItemModel(final Item item) {
		final ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
		registerItemModel(item, registryName.toString());
	}

	/**
	 * Register a single model for an {@link Item}.
	 * <p>
	 * Uses {@code modelLocation} as the domain/path and {@link "inventory"} as the variant.
	 *
	 * @param item          The Item
	 * @param modelLocation The model location
	 */
	private void registerItemModel(final Item item, final String modelLocation) {
		final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
		registerItemModel(item, fullModelLocation);
	}

	/**
	 * Register a single model for an {@link Item}.
	 * <p>
	 * Uses {@code fullModelLocation} as the domain, path and variant.
	 *
	 * @param item              The Item
	 * @param fullModelLocation The full model location
	 */
	private void registerItemModel(final Item item, final ModelResourceLocation fullModelLocation) {
		ModelBakery.registerItemVariants(item, fullModelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
		registerItemModel(item, stack -> fullModelLocation);
	}

	/**
	 * Register an {@link ItemMeshDefinition} for an {@link Item}.
	 *
	 * @param item           The Item
	 * @param meshDefinition The ItemMeshDefinition
	 */
	private void registerItemModel(final Item item, final ItemMeshDefinition meshDefinition) {
		itemsRegistered.add(item);
		ModelLoader.setCustomMeshDefinition(item, meshDefinition);
	}

	/**
	 * Register a model for each metadata value of an {@link Item} corresponding to the values in {@code values}.
	 * <p>
	 * Uses the registry name as the domain/path and {@code "[variantName]=[valueName]"} as the variant.
	 * <p>
	 * Uses {@link IVariant#getMeta()} to determine the metadata of each value.
	 *
	 * @param item        The Item
	 * @param variantName The variant name
	 * @param values      The values
	 * @param <T>         The value type
	 */
	private <T extends IVariant> void registerVariantItemModels(final Item item, final String variantName, final T[] values) {
		for (final T value : values) {
			registerItemModelForMeta(item, value.getMeta(), variantName + "=" + value.getName());
		}
	}

	/**
	 * Register a model for a metadata value an {@link Item}.
	 * <p>
	 * Uses the registry name as the domain/path and {@code variant} as the variant.
	 *
	 * @param item     The Item
	 * @param metadata The metadata
	 * @param variant  The variant
	 */
	private void registerItemModelForMeta(final Item item, final int metadata, final String variant) {
		registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
	}

	/**
	 * Register a model for a metadata value of an {@link Item}.
	 * <p>
	 * Uses {@code modelResourceLocation} as the domain, path and variant.
	 *
	 * @param item                  The Item
	 * @param metadata              The metadata
	 * @param modelResourceLocation The full model location
	 */
	private void registerItemModelForMeta(final Item item, final int metadata, final ModelResourceLocation modelResourceLocation) {
		itemsRegistered.add(item);
		ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
	}
}