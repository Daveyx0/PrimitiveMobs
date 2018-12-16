package net.daveyx0.primitivemobs.core;

import java.util.List;

import javax.annotation.Nullable;

import net.daveyx0.multimob.client.MMItemModelManager;
import net.daveyx0.multimob.core.MMItemRegistry;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.passive.EntityDodo;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.item.ItemCamouflageDye;
import net.daveyx0.primitivemobs.item.ItemGoblinMace;
import net.daveyx0.primitivemobs.item.ItemGroveSpriteSap;
import net.daveyx0.primitivemobs.item.ItemPrimitive;
import net.daveyx0.primitivemobs.item.ItemPrimitiveEgg;
import net.daveyx0.primitivemobs.item.ItemPrimitiveFood;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsItems extends MMItemRegistry{
	 
	 public static final ToolMaterial GOBLIN_METAL = EnumHelper.addToolMaterial("goblin_metal", 2, 400, 6.0F, 2.5F, 10);
	
	 public static final ItemCamouflageDye CAMOUFLAGE_DYE = new ItemCamouflageDye("camouflage_dye");
	 public static final ItemCamouflageArmor CAMOUFLAGE_HELMET = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.HEAD, "camouflage_helmet");
	 public static final ItemCamouflageArmor CAMOUFLAGE_CHEST = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST, "camouflage_chestplate");
	 public static final ItemCamouflageArmor CAMOUFLAGE_BOOTS = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.FEET, "camouflage_boots");
	 public static final ItemCamouflageArmor CAMOUFLAGE_LEGS = new ItemCamouflageArmor(ArmorMaterial.LEATHER, EntityEquipmentSlot.LEGS, "camouflage_leggings");
	 public static final ItemPrimitiveFood RAW_DODO = (ItemPrimitiveFood) new ItemPrimitiveFood("dodo", 4, 1.2F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F);
	 public static final ItemPrimitiveFood COOKED_DODO = (ItemPrimitiveFood) new ItemPrimitiveFood("cooked_dodo", 8, 2.4F, true);
	 public static final ItemPrimitiveEgg DODO_EGG = new ItemPrimitiveEgg("dodo_egg", EntityDodo.class, 8);
	 public static final ItemPrimitiveEgg SPIDER_EGG = new ItemPrimitiveEgg("spider_egg", EntityBabySpider.class, 1);
	 public static final ItemPrimitiveEgg MYSTERYEGG1 = new ItemPrimitiveEgg("mysteryegg1", EntityFestiveCreeper.class, 1){
	 
		    @SideOnly(Side.CLIENT)
		    public boolean hasEffect(ItemStack stack)
		    {
		        return true;
		    }
	 };
	 public static final ItemPrimitiveEgg MYSTERYEGG2 = new ItemPrimitiveEgg("mysteryegg2", EntitySupportCreeper.class, 1){
		 
		    @SideOnly(Side.CLIENT)
		    public boolean hasEffect(ItemStack stack)
		    {
		        return true;
		    }
	 };
	 public static final ItemPrimitiveEgg MYSTERYEGG3 = new ItemPrimitiveEgg("mysteryegg3", EntityRocketCreeper.class, 1){
		 
		    @SideOnly(Side.CLIENT)
		    public boolean hasEffect(ItemStack stack)
		    {
		        return true;
		    }
	 };
	// public static final ItemPrimitiveEgg MYSTERYEGG4 = new ItemPrimitiveEgg("mysteryegg4", EntityCreeper.class, 1);
	 public static final ItemPrimitive MIMIC_ORB = new ItemPrimitive("mimic_orb"){
		 
		@SideOnly(Side.CLIENT)
	    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	    {
			tooltip.add("Joke Item");
	        tooltip.add("R-Click empty Chest to change it into a Mimic");
	    	
	    	super.addInformation(stack, worldIn, tooltip, flagIn);
	    }};
	 public static final ItemGoblinMace GOBLIN_MACE = new ItemGoblinMace("goblin_mace", GOBLIN_METAL);
	 public static final ItemGroveSpriteSap WONDER_SAP = new ItemGroveSpriteSap("wonder_sap");
	 public static final ItemPrimitive SPIDER_EGGSHELL = new ItemPrimitive("spider_eggshell")
			 {
		    /**
		     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
		     */
		    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
		    {
		        if (target instanceof EntityBabySpider && hand == EnumHand.MAIN_HAND)
		        {
		        	target.attackEntityFrom(DamageSource.CRAMMING, 0.1f);
		        	EntityBabySpider spider = (EntityBabySpider)target;
		        	
		        	if(spider.isEntityAlive() && spider.getGrowthLevel() == 0)
		        	{
			        	target.setDead();
			        	playerIn.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(PrimitiveMobsItems.SPIDER_EGG));
		        	}

		            return true;
		        }
		        else
		        {
		            return false;
		        }
		    }
			 };
			 
			 
	    @Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
		public static class RegistrationHandler {

			@SubscribeEvent
			public static void registerItems(final RegistryEvent.Register<Item> event) {
				Item[] items = {
						CAMOUFLAGE_DYE,
						CAMOUFLAGE_HELMET,
						CAMOUFLAGE_CHEST,
						CAMOUFLAGE_BOOTS,
						CAMOUFLAGE_LEGS,
						RAW_DODO,
						COOKED_DODO,
						DODO_EGG,
						MIMIC_ORB,
						GOBLIN_MACE,
						WONDER_SAP,
						SPIDER_EGG,
						MYSTERYEGG1,
						MYSTERYEGG2,
						MYSTERYEGG3,
						SPIDER_EGGSHELL
				};

				final IForgeRegistry<Item> registry = event.getRegistry();

				for (final Item item : items) {
					registry.register(item);
					ITEMS.add(item);
				}
			}
	    		

	}
	    
	    public static void registerItemColors()
	    {
	    	Item[] coloredItems = new Item[] {CAMOUFLAGE_HELMET, CAMOUFLAGE_CHEST, CAMOUFLAGE_LEGS, CAMOUFLAGE_BOOTS, WONDER_SAP};
	    	MMItemModelManager.INSTANCE.registerItemColors(coloredItems);
	    }
}