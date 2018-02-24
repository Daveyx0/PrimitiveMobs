package net.daveyx0.primitivemobs.common.capabilities;

import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.http.util.EntityUtils;

import com.google.common.base.Optional;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.config.PrimitiveMobsConfigSpecial;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.core.PrimitiveMobsReference;
import net.daveyx0.primitivemobs.entity.ai.EntityAISenseEntityNearestPlayer;
import net.daveyx0.primitivemobs.entity.ai.EntityAISummonFollowOwner;
import net.daveyx0.primitivemobs.entity.ai.EntityAISummonOwnerHurtByTarget;
import net.daveyx0.primitivemobs.entity.ai.EntityAISummonOwnerHurtTarget;
import net.daveyx0.primitivemobs.item.ItemSummonerOrb;
import net.daveyx0.primitivemobs.message.MessagePrimitiveParticle;
import net.daveyx0.primitivemobs.message.MessagePrimitiveSummonable;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author Daveyx0
 **/
public class CapabilitySummonableEntity {
	
	    @CapabilityInject(ISummonableEntity.class)
	    public static Capability<ISummonableEntity> SUMMONABLE_ENTITY_CAPABILITY = null;

	    public static final ResourceLocation capabilityID = new ResourceLocation(PrimitiveMobsReference.MODID, "Summonable");
	    
	    public static void register()
	    {
	        CapabilityManager.INSTANCE.register(ISummonableEntity.class, new Capability.IStorage<ISummonableEntity>()
	        {
	            @Override
	            public NBTBase writeNBT(Capability<ISummonableEntity> capability, ISummonableEntity instance, EnumFacing side)
	            {
	            	NBTTagCompound compound = new NBTTagCompound();
	            	UUID summoner = instance.getSummonerId();
		            	
	            	if (summoner == null)
	                {
	                    compound.setString("SummonerUUID", "");
	                }
	                else
	                {
	                	compound.setString("SummonerUUID", summoner.toString());
	                }
	            	
	            	compound.setBoolean("Following", instance.isFollowing());
	            	
	                return compound;
	            }

	            @Override
	            public void readNBT(Capability<ISummonableEntity> capability, ISummonableEntity instance, EnumFacing side, NBTBase base)
	            {
	            	NBTTagCompound compound = (NBTTagCompound)base;
	                String s = "";

	                if (compound.hasKey("SummonerUUID", 8))
	                {
	                    s = compound.getString("SummonerUUID");
	                }

	                if (!s.isEmpty())
	                {
	                    try
	                    {
	                        instance.setSummoner((UUID.fromString(s)));
	                        instance.setSummonedEntity(true);
	                    }
	                    catch (Throwable var4)
	                    {
	                    	instance.setSummonedEntity(false);
	                    }
	                }
	                
	                instance.setFollowing(compound.getBoolean("Following"));

	            }
	        }, SummonableEntityHandler::new);
	    }
	    
	    
	//Most stuff for the Summonable Entities is done through this event handler
	@Mod.EventBusSubscriber(modid = PrimitiveMobsReference.MODID)
	public static class EventHandler
	{
		//Attach Summonable Entity capability
		@SubscribeEvent
		public static void AttachEntityCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event)
		{
			if(event.getObject() != null && event.getObject() instanceof EntityLiving && PrimitiveMobsConfigSpecial.getSummonEnable())
			{	
				event.addCapability(capabilityID, new CapabilityProviderSerializable(SUMMONABLE_ENTITY_CAPABILITY));
			}
		}
		
		//Assign an ItemSummonerOrb in a player inventory with entity data randomly on death
		@SubscribeEvent
		public static void EntityLivingDeathEvent(LivingDeathEvent event)
		{
			if(isEntitySuitableForSummon(event.getEntityLiving()))
			{
				ISummonableEntity summonable = EntityUtil.getCapability(event.getEntity(), SUMMONABLE_ENTITY_CAPABILITY, null);
				EntityLiving entity = (EntityLiving)event.getEntityLiving();
				if(spiritCanBeCaptured(entity, summonable, event.getSource()) || summonable != null && summonable.isSummonedEntity())
				{
					EntityPlayer player = null;
					if(!summonable.isSummonedEntity())
					{
						player = (EntityPlayer)event.getSource().getTrueSource();
					}				
					else if(summonable.isSummonedEntity() && summonable.getSummoner(entity) instanceof EntityPlayer)
					{
						player = (EntityPlayer)summonable.getSummoner(entity);
					}
					
					if(player != null)
					{
					for(int i = 0 ; i < player.inventory.getSizeInventory() ; i++)
					{
						ItemStack item = player.inventory.getStackInSlot(i);
					
						if(!item.isEmpty())
						{
							if(item.getItem() instanceof ItemSummonerOrb && (item.getTagCompound() == null ||  !item.getTagCompound().hasKey("RegistryNameDomain")))
							{
								ItemSummonerOrb.setEntityData(item, entity, player);
								if(!entity.getEntityWorld().isRemote)
								{
									PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), 50, (float)entity.posX + 0.5f, (float)entity.posY + 0.5F, (float)entity.posZ + 0.5f, 0D, 0.0D,0.0D, 0));
								}
								break;
							}
						}
					
					}
					}
				}
			}
		}
		
		//Update the summoned entity AI once the entity joins the world
		@SubscribeEvent
		public static void JoinWorldEvent(EntityJoinWorldEvent event)
		{
			if(isEntitySuitableForSummon(event.getEntity()))
			{
				EntityLiving entity = (EntityLiving)event.getEntity();
				ISummonableEntity summonable = EntityUtil.getCapability(event.getEntity(), SUMMONABLE_ENTITY_CAPABILITY, null);
				if(summonable != null && summonable.isSummonedEntity())
				{
					updateEntityAI(entity);
					
					entity.tasks.taskEntries.stream().filter(taskEntry -> taskEntry.action instanceof EntityAISummonFollowOwner)
					.findFirst().ifPresent(taskEntry -> entity.tasks.removeTask(taskEntry.action));
					
					if(summonable.isFollowing())
					{
						entity.tasks.addTask(3, new EntityAISummonFollowOwner(entity, 1.2D, 8.0f, 2.0f));
					}
				}
			}
		}
		
		//When a summoned entity does not follow, constantly clear the path entity, which is essentially what the sit AI does
		@SubscribeEvent
		public static void EntityUpdateEvent(LivingUpdateEvent event)
		{
			if(isEntitySuitableForSummon(event.getEntityLiving()))
			{
				EntityLiving entity = (EntityLiving)event.getEntity();
				ISummonableEntity summonable = EntityUtil.getCapability(event.getEntity(), SUMMONABLE_ENTITY_CAPABILITY, null);
				if(summonable != null && summonable.isSummonedEntity() && !summonable.isFollowing())
				{
					entity.getNavigator().clearPathEntity();
				}
			}
		}
		
		//Make sure the summoned entity gets updated on client when a new player starts tracking it
		@SubscribeEvent
		public static void PlayerStartsTrackingEvent(StartTracking event)
		{
			if(!event.getEntityPlayer().getEntityWorld().isRemote && 
					isEntitySuitableForSummon(event.getTarget()))
			{
				ISummonableEntity summonable = EntityUtil.getCapability(event.getTarget(), SUMMONABLE_ENTITY_CAPABILITY, null);
				if(summonable.getSummonerId() != null)
				{
					PrimitiveMobs.getSimpleNetworkWrapper().sendToAllAround(new MessagePrimitiveSummonable(event.getTarget().getUniqueID().toString(), summonable.getSummonerId().toString(), summonable.isFollowing()), 
						new TargetPoint(event.getEntityPlayer().dimension, event.getTarget().posX, event.getTarget().posY, event.getTarget().posZ, 255D));
				}
			}
		}
		
		//Sneak + Right-click a summoned entity to make the follow or unfollow
		@SubscribeEvent
		public static void PlayerInteractEvent(EntityInteract event)
		{
			if(isEntitySuitableForSummon(event.getTarget()) && event.getHand() == EnumHand.MAIN_HAND)
			{
				EntityLiving entity = (EntityLiving)event.getTarget();
				ISummonableEntity summonable = EntityUtil.getCapability(event.getTarget(), SUMMONABLE_ENTITY_CAPABILITY, null);
				if(summonable != null && summonable.isSummonedEntity() && summonable.getSummoner(entity) == event.getEntityPlayer())
				{
					if(event.getEntityPlayer().isSneaking())
					{
						if(!event.getEntity().getEntityWorld().isRemote)
						{
							PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.NOTE.getParticleID(), 15, (float)entity.posX + 0.5f, (float)entity.posY + 0.5F, (float)entity.posZ + 0.5f, 0D, 0.0D,0.0D, 0));		
						}
						summonable.setFollowing(!summonable.isFollowing());
						PrimitiveMobs.getSimpleNetworkWrapper().sendToAllAround(new MessagePrimitiveSummonable(event.getTarget().getUniqueID().toString(), summonable.getSummonerId().toString(), summonable.isFollowing()), 
							new TargetPoint(event.getEntityPlayer().dimension, event.getTarget().posX, event.getTarget().posY, event.getTarget().posZ, 255D));
					
						if(summonable.isFollowing())
						{
							entity.tasks.addTask(3, new EntityAISummonFollowOwner(entity, 1.2D, 8.0f, 2.0f));
						}
						else
						{
							entity.tasks.taskEntries.stream().filter(taskEntry -> taskEntry.action instanceof EntityAISummonFollowOwner)
							.findFirst().ifPresent(taskEntry -> entity.tasks.removeTask(taskEntry.action));
						}
					}
					else if(entity.canBeSteered())
					{
						if(!event.getEntity().getEntityWorld().isRemote)
						{
							event.getEntityPlayer().startRiding(entity);
						}
					}
				}

				if(summonable != null && (summonable.isSummonedEntity() && summonable.getSummoner(entity) == event.getEntityPlayer() || !summonable.isSummonedEntity() && event.getEntityPlayer().isCreative()) && event.getEntityPlayer().getHeldItem(event.getHand()).getItem() instanceof ItemSummonerOrb  && event.getHand() == EnumHand.MAIN_HAND)
				{
					if(event.getEntityPlayer().getHeldItem(event.getHand()).getTagCompound() == null || !event.getEntityPlayer().getHeldItem(event.getHand()).getTagCompound().hasKey("RegistryNameDomain"))
					{
						ItemSummonerOrb orb = (ItemSummonerOrb)event.getEntityPlayer().getHeldItem(event.getHand()).getItem();
						orb.setEntityData(event.getEntityPlayer().getHeldItem(event.getHand()), entity, event.getEntityPlayer());
					}
					if(!event.getEntityPlayer().getEntityWorld().isRemote)
					{
						entity.setDead();
					}
				}
			}
		}
		
		public static void updateEntityAI(EntityLiving base)
		{
			while(base.targetTasks.taskEntries.stream()
			.filter(taskEntry -> taskEntry.action instanceof EntityAIBase).findFirst().isPresent())
			{
				base.targetTasks.taskEntries.stream().filter(taskEntry -> taskEntry.action instanceof EntityAIBase)
				.findFirst().ifPresent(taskEntry -> base.targetTasks.removeTask(taskEntry.action));
			}
			
			base.targetTasks.addTask(0, new EntityAISummonOwnerHurtByTarget(base));
			base.targetTasks.addTask(1, new EntityAISummonOwnerHurtTarget(base));
		}
		
		public static boolean isEntitySuitableForSummon(Entity entity)
		{
			return entity != null && entity instanceof EntityLiving && entity.hasCapability(SUMMONABLE_ENTITY_CAPABILITY, null);
		}
		
		public static boolean spiritCanBeCaptured(EntityLiving entity, ISummonableEntity summonable, DamageSource source)
		{
			return entity != null && summonable != null && !summonable.isSummonedEntity() && source != null && source.getTrueSource() != null && source.getTrueSource() instanceof EntityPlayer
					&& (int)entity.getMaxHealth() >= 0 && entity.getEntityWorld().rand.nextInt((int)entity.getMaxHealth()) == 0;
		}
		
	}

}
