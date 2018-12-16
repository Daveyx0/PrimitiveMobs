package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveCreeper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpiderEgg extends EntityPrimitiveThrowable{

	public EntitySpiderEgg(World worldIn) {

		super(worldIn);
	}
	
	public EntitySpiderEgg(World worldIn, EntityPlayer playerIn, Class<? extends EntityLiving> entry, int spawnChance) {

		super(worldIn, playerIn, entry, spawnChance);
	}
	

	public ItemStack getItemFromEntity() {

		return new ItemStack(PrimitiveMobsItems.SPIDER_EGG);
	}

}
