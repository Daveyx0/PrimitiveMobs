package net.daveyx0.primitivemobs.core;

import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsVillagerProfessions.ItemAndItemToEmerald;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsEvents {


@Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
public static class EntityEventHandler {

	@SubscribeEvent
	public static void spawnEvent(EntityJoinWorldEvent event)
	{
		if(!PrimitiveMobsConfigSpecial.getMinerInVillage() && event.getEntity() instanceof EntityVillager && !(event.getEntity() instanceof EntityLostMiner))
		{
			EntityVillager villager = (EntityVillager)event.getEntity();
			
			if(villager != null && villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.MINER_PROFESSION))
			{
				villager.setProfession(0);
			}
		}
	}
}
}
