package net.daveyx0.primitivemobs.message;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.entity.monster.EntityVoidEye;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTeleportEye implements IMessage 
{
    
    private boolean teleport;
    private String id;

    public MessageTeleportEye() { }

    public MessageTeleportEye(boolean tele, String id) {
        this.teleport = tele;
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	teleport = buf.readBoolean();
        id = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(teleport);
        ByteBufUtils.writeUTF8String(buf, id);
    }
    public static class Handler implements IMessageHandler<MessageTeleportEye, IMessage> {
        
        @Override
        public IMessage onMessage(MessageTeleportEye message, MessageContext ctx) 
        {
            EntityLivingBase entity = (EntityLivingBase)ctx.getServerHandler().player.getServerWorld().getEntityFromUuid(UUID.fromString(message.id));
            if(entity != null && entity instanceof EntityVoidEye)
            {
            	((EntityVoidEye)entity).setTeleports(message.teleport);
            }
            return null;
        }
    }

}