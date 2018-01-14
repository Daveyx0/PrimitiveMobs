package net.daveyx0.primitivemobs.core;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsVillagerProfessions.ItemAndItemToEmerald;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityMimic;
import net.daveyx0.primitivemobs.entity.monster.EntitySkeletonWarrior;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant;
import net.daveyx0.primitivemobs.message.MessagePrimitiveParticle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LockCode;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimitiveMobsEvents {


@Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
public static class EntityEventHandler {

	//Converts villagers spawning as the Miner or Merchant profession into a normal villager
	@SubscribeEvent
	public static void spawnEvent(EntityJoinWorldEvent event)
	{
		if(!PrimitiveMobsConfigSpecial.getMinerInVillage() && event.getEntity() instanceof EntityVillager && !(event.getEntity() instanceof EntityLostMiner))
		{
			EntityVillager villager = (EntityVillager)event.getEntity();
			if(villager != null && (villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.MINER_PROFESSION)))
			{
				villager.setProfession(0);
			}
		}
		
		if(event.getEntity() instanceof EntityVillager && !(event.getEntity() instanceof EntityTravelingMerchant))
		{
			EntityVillager villager = (EntityVillager)event.getEntity();
			
				if(villager != null && (villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.MERCHANT_PROFESSION)))
				{
					villager.setProfession(0);
				}
		}
	}
	
	@SubscribeEvent
	public static void onBlockLeftClickEvent(LeftClickBlock event)
	{
		TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
		if(tileEntity != null && tileEntity instanceof TileEntityChest && !event.getWorld().isRemote)
		{
			TileEntityChest chest = (TileEntityChest)tileEntity;
			NBTTagCompound compound = chest.getTileData();
			
			boolean isEmpty = true;
			for(int i = 0; i < chest.getSizeInventory(); i ++)
			{
				if(!chest.getStackInSlot(i).isEmpty())
				{
					isEmpty = false;
				}
			}
			
			if(isEmpty && event.getEntityPlayer().getHeldItemMainhand() != null && event.getEntityPlayer().getHeldItemMainhand().getItem() == PrimitiveMobsItems.MIMIC_ORB)
			{
			if(chest.adjacentChestXNeg == null && chest.adjacentChestXPos == null && chest.adjacentChestZNeg == null && chest.adjacentChestZPos == null)
			{
				consumeItemFromStack(event.getEntityPlayer(), event.getEntityPlayer().getHeldItemMainhand());
				compound.setInteger("Mimic", 2);
				PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), 10, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5F, event.getPos().getZ() + 0.5f, 0D, 0.0D,0.0D, 0));
				//chest.setLockCode(new LockCode(event.getWorld().rand.toString()));
				event.setCanceled(true);
			}
			}
		}
	}
	
	@SubscribeEvent
	public static void onBlockRightClickEvent(RightClickBlock event)
	{
		TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
		if(tileEntity != null && tileEntity instanceof TileEntityChest && !event.getWorld().isRemote)
		{
			TileEntityChest chest = (TileEntityChest)tileEntity;
			NBTTagCompound compound = chest.getTileData();
			
			
			if(compound.hasKey("Mimic"))
			{
				if(compound.getInteger("Mimic") != 0)
				{
				int chance = event.getWorld().rand.nextInt(3);
				boolean flag = chance == 0 || compound.getInteger("Mimic") == 2;
				boolean flag1 = chance == 1;
				if(flag && chest.adjacentChestXNeg == null && chest.adjacentChestXPos == null && chest.adjacentChestZNeg == null && chest.adjacentChestZPos == null)
				{
					EntityMimic mimic = new EntityMimic(event.getWorld());
					mimic.setLocationAndAngles(event.getPos().getX() + 0.5D, event.getPos().getY(), event.getPos().getZ() + 0.5D, 180.0f, 0.0f);
					mimic.setChest(event.getWorld().getBlockState(event.getPos()));
					event.getWorld().spawnEntity(mimic);
					event.getWorld().setBlockToAir(event.getPos());
					event.setCanceled(true);

			        PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.CLOUD.getParticleID(), 10, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5F, event.getPos().getZ() + 0.5f, 0D, 0.0D,0.0D, 0));
				}
				else if(flag1 && chest.adjacentChestXNeg == null && chest.adjacentChestXPos == null && chest.adjacentChestZNeg == null && chest.adjacentChestZPos == null)
				{
					int option = event.getWorld().rand.nextInt(3);
					if(option == 0)
					{
						EntitySkeletonWarrior skeleton = new EntitySkeletonWarrior(event.getWorld());
						skeleton.onInitialSpawn(event.getWorld().getDifficultyForLocation(new BlockPos(skeleton)), null);
						skeleton.setLocationAndAngles(event.getPos().getX() + 0.5D, event.getPos().getY() + 1D, event.getPos().getZ() + 0.5D, 180.0f, 0.0f);
						event.getWorld().spawnEntity(skeleton);
					}
					else if(option == 1)
					{
						for(int i = 0; i < 3; i++)
						{
							EntityBat bat = new EntityBat(event.getWorld());
							bat.setLocationAndAngles(event.getPos().getX() + 0.5D, event.getPos().getY() + 1D, event.getPos().getZ() + 0.5D, 180.0f, 0.0f);
							event.getWorld().spawnEntity(bat);
						}
					}
					else
					{
						EntityHauntedTool tool = new EntityHauntedTool(event.getWorld());
						tool.onInitialSpawn(event.getWorld().getDifficultyForLocation(new BlockPos(tool)), null);
						tool.setLocationAndAngles(event.getPos().getX() + 0.5D, event.getPos().getY() + 1D, event.getPos().getZ() + 0.5D, 180.0f, 0.0f);
						event.getWorld().spawnEntity(tool);
					}
					
					chest.setLootTable(PrimitiveMobsLootTables.MIMIC_TRAP, event.getWorld().rand.nextLong());
					compound.setInteger("Mimic", 0);
					event.setCanceled(true);
					
					PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.CLOUD.getParticleID(), 10, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5F, event.getPos().getZ() + 0.5f, 0D, 0.0D,0.0D, 0));
				}
				else if(chest.adjacentChestXNeg == null && chest.adjacentChestXPos == null && chest.adjacentChestZNeg == null && chest.adjacentChestZPos == null)
				{
					chest.setLootTable(PrimitiveMobsLootTables.MIMIC_TREASURE, event.getWorld().rand.nextLong());
					compound.setInteger("Mimic", 0);
				}
				}
			}
			else
			{
				boolean isEmpty = true;
				for(int i = 0; i < chest.getSizeInventory(); i ++)
				{
					if(!chest.getStackInSlot(i).isEmpty())
					{
						isEmpty = false;
					}
				}
				
				if(isEmpty && event.getEntityPlayer().getHeldItemMainhand() != null && event.getEntityPlayer().getHeldItemMainhand().getItem() == PrimitiveMobsItems.MIMIC_ORB)
				{
				if(chest.adjacentChestXNeg == null && chest.adjacentChestXPos == null && chest.adjacentChestZNeg == null && chest.adjacentChestZPos == null)
				{
					consumeItemFromStack(event.getEntityPlayer(), event.getEntityPlayer().getHeldItemMainhand());
					compound.setInteger("Mimic", 1);
					PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.CLOUD.getParticleID(), 10, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5F, event.getPos().getZ() + 0.5f, 0D, 0.0D,0.0D, 0));
					//chest.setLockCode(new LockCode(event.getWorld().rand.toString()));
					event.setCanceled(true);
				}
				}
			}
		}
	}
	
    protected static void consumeItemFromStack(EntityPlayer player, ItemStack stack)
    {
        if (!player.capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }
    }
}
}
