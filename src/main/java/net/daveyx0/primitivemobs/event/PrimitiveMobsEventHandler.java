package net.daveyx0.primitivemobs.event;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import net.daveyx0.primitivemobs.client.renderer.entity.layer.LayerSpiritEntity;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.entity.monster.EntityEnchantedBook;
import net.daveyx0.primitivemobs.entity.passive.EntityChameleon;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColor;
import net.daveyx0.primitivemobs.util.ColorUtil;
import net.daveyx0.primitivemobs.util.NBTUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class PrimitiveMobsEventHandler {

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		// Sync armor when on a different entity then a player
		EntityLivingBase entityLiving = event.getEntityLiving();
		if(entityLiving != null && entityLiving.getEntityWorld().rand.nextInt(10) == 0)
		{
			NBTUtil.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.CHEST);
			NBTUtil.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.FEET);
			NBTUtil.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.HEAD);
			NBTUtil.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.LEGS);
		}
	}
	
   
}
