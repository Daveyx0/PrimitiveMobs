package net.daveyx0.primitivemobs.core;

import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.Nullable;

import net.daveyx0.multimob.message.MMMessageRegistry;
import net.daveyx0.multimob.message.MessageMMParticle;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.entity.monster.EntityHarpy;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityMimic;
import net.daveyx0.primitivemobs.entity.monster.EntityRocketCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntitySkeletonWarrior;
import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.daveyx0.primitivemobs.entity.passive.EntitySheepman;
import net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.item.ItemGoblinMace;
import net.daveyx0.primitivemobs.message.MessagePrimitiveJumping;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
			
				if(villager != null && (villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.MERCHANT_PROFESSION)
						|| villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.FAKE_MERCHANT_PROFESSION)))
				{
					villager.setProfession(0);
				}
		}
		
		if(event.getEntity() instanceof EntityVillager && !(event.getEntity() instanceof EntitySheepman))
		{
			EntityVillager villager = (EntityVillager)event.getEntity();
			
				if(villager != null && (villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.SHEEPMAN_PROFESSION_ALCHEMIST)
						|| villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.SHEEPMAN_PROFESSION_SCAVENGER)
						|| villager.getProfession() == net.minecraftforge.fml.common.registry.VillagerRegistry.getId(PrimitiveMobsVillagerProfessions.SHEEPMAN_PROFESSION_THIEF)))
				{
					villager.setProfession(0);
				}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLogOutEvent(PlayerLoggedOutEvent event)
	{
		if(event.player.isBeingRidden())
		{
			event.player.removePassengers();

	        if(event.player instanceof EntityPlayerMP) {
	        	MMMessageRegistry.networkWrapper.sendPacket(event.player, new SPacketSetPassengers(event.player));
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
			
			if(event.getEntityPlayer().isCreative() && isEmpty && event.getEntityPlayer().getHeldItemMainhand() != null && event.getEntityPlayer().getHeldItemMainhand().getItem() == PrimitiveMobsItems.MIMIC_ORB)
			{
			if(chest.adjacentChestXNeg == null && chest.adjacentChestXPos == null && chest.adjacentChestZNeg == null && chest.adjacentChestZPos == null)
			{
				consumeItemFromStack(event.getEntityPlayer(), event.getEntityPlayer().getHeldItemMainhand());
				compound.setInteger("Mimic", 1);
				MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), 10, event.getPos().getX() + 0.05f, event.getPos().getY() + 0.05F, event.getPos().getZ() + 0.05f, 0D, 0.01D,0.0D, 0));
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

					MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.CLOUD.getParticleID(), 10, event.getPos().getX() + 0.05f, event.getPos().getY() + 0.05F, event.getPos().getZ() + 0.05f, 0D, 0.01D,0.0D, 0));
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
					compound.setInteger("Mimic", 2);
					event.setCanceled(true);
					
					MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.CLOUD.getParticleID(), 10, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5F, event.getPos().getZ() + 0.5f, 0D, 0.0D,0.0D, 0));
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
					compound.setInteger("Mimic", 2);
					MMMessageRegistry.getNetwork().sendToAll(new MessageMMParticle(EnumParticleTypes.CLOUD.getParticleID(), 10, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5F, event.getPos().getZ() + 0.5f, 0D, 0.0D,0.0D, 0));
					//chest.setLockCode(new LockCode(event.getWorld().rand.toString()));
					event.setCanceled(true);
				}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		// Sync armor when on a different entity then a player
		EntityLivingBase entityLiving = event.getEntityLiving();
		if(entityLiving != null && entityLiving.ticksExisted % 5 == 0)
		{
			ItemCamouflageArmor.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.CHEST);
			ItemCamouflageArmor.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.FEET);
			ItemCamouflageArmor.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.HEAD);
			ItemCamouflageArmor.setCamouflageArmorNBT(entityLiving, EntityEquipmentSlot.LEGS);
		}
		

	}
	
	@SubscribeEvent
	public void onSetAttackTarget(LivingSetAttackTargetEvent event)
	{
		EntityLivingBase entityLiving = event.getEntityLiving();
		if(entityLiving != null && entityLiving instanceof EntityZombie)
		{
			EntityZombie zombie = (EntityZombie)entityLiving;
			if(event.getTarget() instanceof EntitySheepman)
			{
				zombie.setAttackTarget(null);
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
    
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event)
    {
    	if(event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityRocketCreeper)
    	{
    		event.setCanceled(true);
    	}
    }
    
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event)
    {
    	if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase)
    	{
    		EntityLivingBase sourceEntity = (EntityLivingBase)event.getSource().getTrueSource();
    		
    		if(sourceEntity.getHeldItemMainhand().getItem() == PrimitiveMobsItems.GOBLIN_MACE)
    		{
    		ArrayList<ItemStack> damageableArmorPieces = new ArrayList<ItemStack>();
    	    Iterable<ItemStack> armorPieces = event.getEntityLiving().getArmorInventoryList();
    	    
    	    if(armorPieces!= null && !armorPieces.equals(Collections.<ItemStack>emptyList()))
    	    {
    	    	for (ItemStack piece : event.getEntityLiving().getArmorInventoryList())
    	    	{
    	    		if(!piece.isEmpty() && piece.isItemStackDamageable())
    	    		{
    	    			damageableArmorPieces.add(piece);
    	    		}
    	    	}
    	    	
    	    	if(!damageableArmorPieces.isEmpty())
    	    	{
    	    		ItemStack targetPiece = damageableArmorPieces.get(event.getEntityLiving().getRNG().nextInt(damageableArmorPieces.size()));
    	    		targetPiece.damageItem((int)(targetPiece.getMaxDamage() * ItemGoblinMace.damagePercentage), event.getEntityLiving());
    	    		//EntityEquipmentSlot slot = getSlotFromItemStack(event.getEntityLiving(), targetPiece);
    				//MultiMob.LOGGER.info(targetPiece.getDisplayName() + " " + targetPiece.getItemDamage() + " " + slot.name());
    	    		//if(slot != null)
    	    		//{

    	    			//event.getEntityLiving().setItemStackToSlot(slot, targetPiece.copy());
    	    		//}

    	    	}
    	    }
    		}
    	}
    }
    
    @Nullable
    public EntityEquipmentSlot getSlotFromItemStack(EntityLivingBase entityIn, ItemStack stack)
    {
    	if(entityIn != null && !stack.isEmpty())
    	{
    		if(stack.getItem() == entityIn.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem())
    		{
    			return EntityEquipmentSlot.HEAD;
    		}
    		else if(stack.getItem() == entityIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem())
    		{
    			return EntityEquipmentSlot.CHEST;
    		}
    		else if(stack.getItem() == entityIn.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem())
    		{
    			return EntityEquipmentSlot.LEGS;
    		}
    		else if(stack.getItem() == entityIn.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem())
    		{
    			return EntityEquipmentSlot.FEET;
    		}
    	}

		return null;
    }
    
	@SubscribeEvent
	public static void DismountPlayerEvent(EntityMountEvent event)
	{
		if(event.isDismounting() && event.getEntityBeingMounted() != null && event.getEntityBeingMounted() instanceof EntityHarpy)
		{
			if(event.getEntityMounting() != null && event.getEntityMounting().isSneaking() && event.getEntityMounting() instanceof EntityPlayer)
			{
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void PlayEntitySound (PlaySoundAtEntityEvent event)
	{
		//Cheating the system cause the getEntity() will always result in null
		if(event.getVolume() == 1.11F)
		{
			EntitySheepman sheepman = (EntitySheepman)event.getEntity();

			if(event.getSound() == SoundEvents.ENTITY_VILLAGER_YES)
			{
				event.setSound(SoundEvents.ENTITY_SHEEP_AMBIENT);
			}
			else if(event.getSound() == SoundEvents.ENTITY_VILLAGER_NO)
			{
				event.setSound(SoundEvents.ENTITY_SHEEP_HURT);
			}

		}
	}
	
	/**
	* KeyInputEvent is in the FML package, so we must register to the FML event bus
	*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(KeyInputEvent event) 
	{
		if(Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown())
		{
			String UUID = Minecraft.getMinecraft().player.getUniqueID().toString();
			MMMessageRegistry.getNetwork().sendToServer(new MessagePrimitiveJumping(UUID));
		}

	}
    
}
}
