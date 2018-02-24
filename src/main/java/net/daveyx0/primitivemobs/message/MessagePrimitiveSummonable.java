package net.daveyx0.primitivemobs.message;

import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.common.capabilities.CapabilitySummonableEntity;
import net.daveyx0.primitivemobs.common.capabilities.ISummonableEntity;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePrimitiveSummonable implements IMessage 
{
    private String entityId;
    private String summonerId;
    private boolean following;

    public MessagePrimitiveSummonable() { }

    public MessagePrimitiveSummonable(String entityInID, String summonerInID, boolean following) {
        this.entityId = entityInID;
        this.summonerId = summonerInID;
        this.following = following;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	entityId = ByteBufUtils.readUTF8String(buf);
    	summonerId = ByteBufUtils.readUTF8String(buf);
    	following = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, entityId);
        ByteBufUtils.writeUTF8String(buf, summonerId);
        buf.writeBoolean(following);
    }
    public static class Handler implements IMessageHandler<MessagePrimitiveSummonable, IMessage> {
        
        @Override
        public IMessage onMessage(MessagePrimitiveSummonable message, MessageContext ctx) 
        {
        	PrimitiveMobs.proxy.getThreadListener(ctx).addScheduledTask(() -> {
        		
        		if(!message.entityId.isEmpty() && !message.summonerId.isEmpty())
        		{
        			EntityLivingBase entity = EntityUtil.getLoadedEntityByUUID((UUID.fromString(message.entityId)), PrimitiveMobs.proxy.getClientWorld());
        			if(entity != null && entity.hasCapability(CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null))
        			{
        				ISummonableEntity summonable = EntityUtil.getCapability(entity, CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null);
        				summonable.setSummonedEntity(true);
        				summonable.setSummoner((UUID.fromString(message.summonerId)));
        				summonable.setFollowing(message.following);
        				NBTTagCompound nbttagcompound = entity.writeToNBT(new NBTTagCompound());
        				nbttagcompound.setString("Owner", message.summonerId);
        				nbttagcompound.setString("OwnerUUID", message.summonerId);
        				nbttagcompound.setBoolean("Tame", true);
        				nbttagcompound.setBoolean("Tamed", true);
        				entity.readFromNBT(nbttagcompound);
        			}
        		}
        	});
            return null;
        }
    }

}