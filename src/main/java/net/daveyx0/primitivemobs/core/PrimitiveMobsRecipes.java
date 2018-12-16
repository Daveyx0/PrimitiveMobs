package net.daveyx0.primitivemobs.core;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsRecipes {

	 public static final Set<IRecipe> RECIPES = new HashSet<>();

    public static void registerRecipes() {
    	
    	//RecipeSorter.register("primitivemobs:camouflagerecipe", CamouflageArmorRecipe.class, SHAPELESS, "after:forge:shapelessore");
    	GameRegistry.addSmelting(PrimitiveMobsItems.RAW_DODO, new ItemStack(PrimitiveMobsItems.COOKED_DODO), 0.45f);
    	OreDictionary.registerOre("egg", PrimitiveMobsItems.DODO_EGG);
    	OreDictionary.registerOre("foodSimpleEgg", PrimitiveMobsItems.DODO_EGG);
    	OreDictionary.registerOre("ingredientEgg", PrimitiveMobsItems.DODO_EGG);
    	OreDictionary.registerOre("listAllegg", PrimitiveMobsItems.DODO_EGG);
    	OreDictionary.registerOre("listAllegg", PrimitiveMobsItems.DODO_EGG);
    }


	    @Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
		public static class RegistrationHandler {

			@SubscribeEvent
			public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
				final IRecipe[] recipes = {
						
				};
				
				final IForgeRegistry<IRecipe> registry = event.getRegistry();
			}
		}
	    
	    
}
