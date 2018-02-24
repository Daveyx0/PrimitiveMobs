package net.daveyx0.primitivemobs.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.common.capabilities.CapabilitySummonableEntity;
import net.daveyx0.primitivemobs.common.capabilities.ISummonableEntity;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.entity.ai.EntityAISummonFollowOwner;
import net.daveyx0.primitivemobs.message.MessagePrimitiveParticle;
import net.daveyx0.primitivemobs.message.MessagePrimitiveSummonable;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSummonerOrb extends Item {

	public ItemSummonerOrb(String itemName) {
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(64);
		setItemName(this, itemName);
		setCreativeTab(PrimitiveMobs.tabPrimitiveMobs);
	}
	
	public static void setItemName(Item item, String itemName) {
		item.setRegistryName(itemName);
		item.setUnlocalizedName(item.getRegistryName().toString());
	}
	
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Items.ENDER_EYE;
    }
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	NBTTagCompound nbttagcompound = stack.getTagCompound();
    	if(nbttagcompound != null && nbttagcompound.hasKey("EntityName"))
    	{
    		if(nbttagcompound.getString("EntityName") != null && !nbttagcompound.getString("EntityName").isEmpty())
    		{
        		tooltip.add("Name: " + nbttagcompound.getString("EntityName"));
            	ResourceLocation registryName = new ResourceLocation(nbttagcompound.getString("RegistryNameDomain"), nbttagcompound.getString("RegistryNamePath"));
            	tooltip.add("Type: " + registryName.toString());
            }
        	else
        	{
        		tooltip.add("empty");
        	}
    	}
    	else
    	{
    		tooltip.add("empty");
    	}
    }
    
    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	EntityLiving summonedEntity = getEntityWithData(player.getHeldItemMainhand(), worldIn, player);
    	
    	if(summonedEntity != null)
    	{
    		if(!worldIn.isRemote)
    		{
    			summonedEntity.setLocationAndAngles(pos.getX(), pos.getY() + 1D, pos.getZ(), player.rotationYaw, player.rotationPitch);
    			summonedEntity.setRotationYawHead(player.rotationYawHead);
    			PrimitiveMobs.getSimpleNetworkWrapper().sendToAll(new MessagePrimitiveParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), 50, pos.getX() + 0.5f, pos.getY() + 0.5F, pos.getZ() + 0.5f, 0D, 0.0D,0.0D, 0));
    			worldIn.spawnEntity(summonedEntity);
    			player.getHeldItemMainhand().damageItem(8, player);
        		summonedEntity.setCustomNameTag(player.getName() + "'s " + player.getHeldItemMainhand().getTagCompound().getString("EntityName"));  				
    			if(EntityUtil.getCapability(summonedEntity, CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null) != null)
    			{
    				ISummonableEntity summonable = EntityUtil.getCapability(summonedEntity, CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null);
    				summonable.setSummoner(player.getUniqueID());
    				summonable.setSummonedEntity(true);
    				summonable.setFollowing(true);
    				PrimitiveMobs.getSimpleNetworkWrapper().sendToAllAround(new MessagePrimitiveSummonable(summonedEntity.getUniqueID().toString(), summonable.getSummonerId().toString(), summonable.isFollowing()), 
    						new TargetPoint(player.dimension, summonedEntity.posX, summonedEntity.posY, summonedEntity.posZ, 255D));
    				CapabilitySummonableEntity.EventHandler.updateEntityAI(summonedEntity);
    				summonedEntity.tasks.addTask(3, new EntityAISummonFollowOwner(summonedEntity, 1.2D, 8.0f, 2.0f));
    			}
    		}
    		setEntityData(player.getHeldItemMainhand(), null, player);
    		
    		return EnumActionResult.PASS;
    	}
        return EnumActionResult.PASS;
    }
    
    public static EntityLiving getEntityWithData(ItemStack stack, World world, EntityPlayer player)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            return null;
        }
        else
        {
        EntityLiving entityLiving = null;
        if(nbttagcompound.hasKey("RegistryNameDomain"))
        {

        	ResourceLocation registryName = new ResourceLocation(nbttagcompound.getString("RegistryNameDomain"), nbttagcompound.getString("RegistryNamePath"));
        	if (ForgeRegistries.ENTITIES.containsKey(registryName))
            {
        		EntityEntry entry = ForgeRegistries.ENTITIES.getValue(registryName);

                 try
                 {
                	 entityLiving = (EntityLiving)entry.getEntityClass().getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
                 }
                 catch (Exception exception)
                 {
                     exception.printStackTrace();
                 }
            }
        }
        	
        	if(entityLiving != null)
        	{
				nbttagcompound.setString("Owner", player.getUniqueID().toString());
				nbttagcompound.setString("OwnerUUID", player.getUniqueID().toString());
				nbttagcompound.setBoolean("Tame", true);
				nbttagcompound.setBoolean("Tamed", true);
        		entityLiving.readFromNBT(nbttagcompound);
        	}
        	
        	return entityLiving;
        }
        
    }
    
    public static void setEntityData(ItemStack stack, EntityLiving entity, EntityPlayer player)
    {
    	if(entity == null)
    	{
    		NBTTagCompound nbttagcompound = new NBTTagCompound();
    		stack.setTagCompound(nbttagcompound);
    	}
    	else
    	{
        	
        	if(EntityRegistry.getEntry(entity.getClass()) != null)
        	{
            	NBTTagCompound nbttagcompound = entity.writeToNBT(new NBTTagCompound());
            	nbttagcompound.setBoolean("PersistenceRequired", true);
            	nbttagcompound.removeTag("Dimension");
            	nbttagcompound.removeTag("ActiveEffects");
            	nbttagcompound.removeTag("Pos");
            	nbttagcompound.removeTag("Motion");
            	nbttagcompound.removeTag("Rotation");
            	nbttagcompound.removeTag("FallDistance");
            	nbttagcompound.removeTag("Fire");
            	nbttagcompound.setUniqueId("UUID", UUID.randomUUID());
            	nbttagcompound.setFloat("Health", entity.getMaxHealth());
            	
        		nbttagcompound.setString("RegistryNameDomain", EntityRegistry.getEntry(entity.getClass()).getRegistryName().getResourceDomain());
        		nbttagcompound.setString("RegistryNamePath", EntityRegistry.getEntry(entity.getClass()).getRegistryName().getResourcePath());
            	
        		if(EntityRegistry.getEntry(entity.getClass()).getEgg() != null && EntityRegistry.getEntry(entity.getClass()).getEgg().primaryColor != 0)
        		{
            	NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (!nbttagcompound.hasKey("display", 10))
                {
                    nbttagcompound.setTag("display", nbttagcompound1);
                }
                
                nbttagcompound1.setInteger("color", EntityRegistry.getEntry(entity.getClass()).getEgg().primaryColor);
        		}
            	
        		String name = entity.getName();
        		String message = "";
        		if(name.contains("'s "))
        		{
        			PrimitiveMobsLogger.gotHere();
        			String[] names = name.split("'s ");
        			name = names[1];
        			message = "Spirit Returned: " + name;
        		}
        		else
        		{
        			message = "Spirit Captured: " + name;        			
        		}
        		
            	nbttagcompound.setString("EntityName", name);
            	
        		
        		if(!player.getEntityWorld().isRemote)
        		{
        			player.sendStatusMessage(new TextComponentString(message), false);
        		}

                stack.setTagCompound(nbttagcompound);
        	}
        	else
        	{
        		return;
        	}
    	}
    }
    
    /**
     * Return the color for the specified armor ItemStack.
     */ 
    public static int getColor(ItemStack stack)
    {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
                {
                    return nbttagcompound1.getInteger("color");
                }
            }

            return 16777215;
    }

}
