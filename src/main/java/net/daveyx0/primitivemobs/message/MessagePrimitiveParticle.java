package net.daveyx0.primitivemobs.message;

import java.util.Iterator;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePrimitiveParticle implements IMessage 
{
    
    private String id;
    float x;
    float y;
    float z;

    public MessagePrimitiveParticle() { }

    public MessagePrimitiveParticle(String id, float x, float y, float z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = ByteBufUtils.readUTF8String(buf);
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, id);
    	buf.writeFloat(x);
    	buf.writeFloat(y);
    	buf.writeFloat(z);
    }
    public static class Handler implements IMessageHandler<MessagePrimitiveParticle, IMessage> {
        
        @Override
        public IMessage onMessage(MessagePrimitiveParticle message, MessageContext ctx) 
        {
            EntityLivingBase entity = (EntityLivingBase)EntityUtil.getLoadedEntityByUUID(UUID.fromString(message.id), Minecraft.getMinecraft().world);
            if(entity != null && entity instanceof EntityTrollager)
            {
            	EntityTrollager troll = (EntityTrollager)entity;
            	troll.playParticles(message.x, message.y, message.z);
            }
            return null;
        }
    }

}