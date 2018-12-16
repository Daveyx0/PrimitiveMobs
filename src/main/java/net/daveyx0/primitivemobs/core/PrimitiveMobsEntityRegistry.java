package net.daveyx0.primitivemobs.core;

import net.daveyx0.multimob.core.MMEntityRegistry;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderBabySpider;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderBlazingJuggernaut;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderBrainSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderChameleon;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderDodo;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderEchantedBook;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderFilchLizard;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderFlameSpewer;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderFlyingItem;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderGoblin;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderGroveSprite;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderHarpy;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderHauntedTool;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderLilyLurker;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderLostMiner;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderMimic;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderMotherSpider;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderPrimitiveCreeper;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderPrimitiveTNTPrimed;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderSheepman;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderSkeletonWarrior;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderThownBlock;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderTravelingMerchant;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderTreasureSlime;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderTrollager;
import net.daveyx0.primitivemobs.client.renderer.entity.RenderVoidEye;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigMobs;
import net.daveyx0.primitivemobs.entity.item.EntityFlameSpit;
import net.daveyx0.primitivemobs.entity.item.EntityPrimitiveTNTPrimed;
import net.daveyx0.primitivemobs.entity.item.EntityPrimitiveThrowable;
import net.daveyx0.primitivemobs.entity.item.EntitySpiderEgg;
import net.daveyx0.primitivemobs.entity.item.EntityThrownBlock;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut;
import net.daveyx0.primitivemobs.entity.monster.EntityBrainSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityFlameSpewer;
import net.daveyx0.primitivemobs.entity.monster.EntityGoblin;
import net.daveyx0.primitivemobs.entity.monster.EntityHarpy;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.daveyx0.primitivemobs.entity.monster.EntityMimic;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySkeletonWarrior;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.daveyx0.primitivemobs.entity.monster.EntityVoidEye;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.daveyx0.primitivemobs.entity.passive.EntityDodo;
import net.daveyx0.primitivemobs.entity.passive.EntityFilchLizard;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.daveyx0.primitivemobs.entity.passive.EntitySheepman;
import net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PrimitiveMobsEntityRegistry extends MMEntityRegistry{

		public static int id;
		
	    public static void registerEntities()
	    {
	    	id = 0;
	    	addEntities(EntityChameleon.class, "chameleon", ++id ,0x048C00, 0x025600, PrimitiveMobsConfigMobs.enableChameleon);
	    	addEntities(EntityTreasureSlime.class, "treasure_slime", ++id , 0xFFF399, 0xFFE332, PrimitiveMobsConfigMobs.enableTreasureSlime);
	    	addEntities(EntityHauntedTool.class, "haunted_tool", ++id , 0x493615, 0x684E1E, PrimitiveMobsConfigMobs.enableHauntedTool);
	    	addEntities(EntityGroveSprite.class, "grovesprite", ++id , 0x5B4E3D, 0x62A72F, PrimitiveMobsConfigMobs.enableGroveSprite);
	    	addEntities(EntityEnchantedBook.class, "bewitched_tome", ++id , 0xB77A35, 0xD0D0D0, PrimitiveMobsConfigMobs.enableEnchantedBook);
	    	addEntities(EntityFilchLizard.class, "filch_lizard", ++id , 0xC2B694, 0xD1CDC0, PrimitiveMobsConfigMobs.enableFilchLizard);
	    	addEntities(EntityBrainSlime.class, "brain_slime", ++id , 0xC696B0, 0xD1A5BD, PrimitiveMobsConfigMobs.enableBrainSlime);
	    	addEntities(EntityRocketCreeper.class, "rocket_creeper", ++id ,0x4CA9D0, 0x000000, PrimitiveMobsConfigMobs.enableRocketCreeper);
	    	addEntities(EntityFestiveCreeper.class, "festive_creeper", ++id ,0xBC3608, 0x000000, PrimitiveMobsConfigMobs.enableFestiveCreeper);
	    	addEntities(EntitySupportCreeper.class, "support_creeper", ++id ,0xDBBD2F, 0x000000, PrimitiveMobsConfigMobs.enableSupportCreeper);
	    	addEntities(EntitySkeletonWarrior.class, "skeleton_warrior", ++id ,0xABA188, 0x6C5239, PrimitiveMobsConfigMobs.enableSkeletonWarrior);
	    	addEntities(EntityBlazingJuggernaut.class, "blazing_juggernaut", ++id ,0x30181C, 0xB0A938, PrimitiveMobsConfigMobs.enableBlazingJuggernaut);
	    	addEntities(EntityLilyLurker.class, "lily_lurker", ++id, 0x593D29, 0x3D3C1C, PrimitiveMobsConfigMobs.enableLilyLurker);
	    	addEntities(EntityMotherSpider.class, "mother_spider", ++id, 0x250522, 11013646, PrimitiveMobsConfigMobs.enableSpiderFamily);
	    	addEntities(EntityBabySpider.class, "baby_spider", ++id, 0xB59468, 11013646, PrimitiveMobsConfigMobs.enableSpiderFamily);
	    	addEntities(EntityTrollager.class, "trollager", ++id, 0x56845D, 0x35251F, PrimitiveMobsConfigMobs.enableTrollager);
	    	addEntities(EntityLostMiner.class, "lost_miner", ++id, 0x6C3626, 0xBD8B72, PrimitiveMobsConfigMobs.enableLostMiner);
	    	addEntities(EntityTravelingMerchant.class, "traveling_merchant", ++id, 0x606051, 0xBD8B72, PrimitiveMobsConfigMobs.enableMerchant);
	    	addEntities(EntityDodo.class, "dodo", ++id, 0x725643, 0xBCA18C, PrimitiveMobsConfigMobs.enableDodo);
	    	addEntities(EntityMimic.class, "mimic", ++id, 0xAB792D, 0x2A251D, PrimitiveMobsConfigMobs.enableMimic);
	    	addEntities(EntitySheepman.class, "sheepman", ++id, 0x262626, 0xB39680, PrimitiveMobsConfigMobs.enableSheepman);
	    	addEntities(EntityGoblin.class, "goblin", ++id, 0x8B976D, 0x524137, PrimitiveMobsConfigMobs.enableGoblin);
	    	addEntities(EntityHarpy.class, "harpy", ++id, 0x3D3454, 0xA4936B, PrimitiveMobsConfigMobs.enableHarpy);
	    	addEntities(EntityFlameSpewer.class, "flame_spewer", ++id, 0x86462E, 0x8D6E66, PrimitiveMobsConfigMobs.enableFlameSpewer);
	    	addEntities(EntityVoidEye.class, "void_eye", ++id, 0x0C0D12, 0x8ECD79, PrimitiveMobsConfigMobs.enableVoidWatcher);
	    	
	    	addCustomEntities(EntityPrimitiveTNTPrimed.class, "primitive_tnt_primed", ++id, 64, 20, true);
	    	addCustomEntities(EntityFlameSpit.class, "flame_spit", ++id, 64, 1, false);
	    	addCustomEntities(EntityThrownBlock.class, "thrown_block", ++id, 64, 20, true);
	    	addCustomEntities(EntityPrimitiveThrowable.class, "primitive_egg", ++id, 64, 20, true);
	    	addCustomEntities(EntitySpiderEgg.class, "spider_egg", ++id, 64, 20, true);
	    }
	    
	    @SideOnly(Side.CLIENT)
	    public static void registerRenderers()
	    {
	    	RenderingRegistry.registerEntityRenderingHandler(EntityChameleon.class, RenderChameleon::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityTreasureSlime.class, RenderTreasureSlime::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityHauntedTool.class, RenderHauntedTool::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityGroveSprite.class, RenderGroveSprite::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityEnchantedBook.class, RenderEchantedBook::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityFilchLizard.class, RenderFilchLizard::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityBrainSlime.class, RenderBrainSlime::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityRocketCreeper.class, RenderPrimitiveCreeper::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityFestiveCreeper.class, RenderPrimitiveCreeper::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntitySupportCreeper.class, RenderPrimitiveCreeper::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonWarrior.class, RenderSkeletonWarrior::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityBlazingJuggernaut.class, RenderBlazingJuggernaut::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityLilyLurker.class, RenderLilyLurker::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityMotherSpider.class, RenderMotherSpider::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityBabySpider.class, RenderBabySpider::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityTrollager.class, RenderTrollager::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityLostMiner.class, RenderLostMiner::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityTravelingMerchant.class, RenderTravelingMerchant::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityDodo.class, RenderDodo::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityMimic.class, RenderMimic::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntitySheepman.class, RenderSheepman::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityGoblin.class, RenderGoblin::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityHarpy.class, RenderHarpy::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityFlameSpewer.class, RenderFlameSpewer::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityVoidEye.class, RenderVoidEye::new);
	    	
	    	RenderingRegistry.registerEntityRenderingHandler(EntityPrimitiveTNTPrimed.class, RenderPrimitiveTNTPrimed::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityThrownBlock.class, RenderThownBlock::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityPrimitiveThrowable.class, RenderFlyingItem::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntitySpiderEgg.class, RenderFlyingItem::new);
	    	RenderingRegistry.registerEntityRenderingHandler(EntityFlameSpit.class, RenderFlyingItem::new);
	    }
	    
	    public static void addEntities(Class var1, String name1,  int entityid, int bkEggColor, int fgEggColor, boolean flag)
	    {
	    	addEntities(PrimitiveMobsReference.MODID, PrimitiveMobs.instance, var1, name1, entityid, bkEggColor, fgEggColor, flag);
	    }
	    
	    public static void addEntitiesWithoutEgg(Class var1, String name1,  int entityid, boolean flag)
	    {
	    	addEntitiesWithoutEgg(PrimitiveMobsReference.MODID, PrimitiveMobs.instance, var1, name1, entityid, flag);
	    }
	    
	    
	    public static void addCustomEntities(Class var1, String name1,  int entityid, int track, int freq, boolean vel)
	    {
	    	addCustomEntities(PrimitiveMobsReference.MODID, PrimitiveMobs.instance, var1, name1, entityid, track, freq, vel);
	    }
	    
		 
	}