package net.daveyx0.primitivemobs.message;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.daveyx0.multimob.core.MultiMob;
import net.daveyx0.primitivemobs.entity.passive.EntityGroveSprite;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.item.ItemGroveSpriteSap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePrimitiveColorSap implements IMessage 
{
    
    private int color;
    private String id;

    public MessagePrimitiveColorSap() { }

    public MessagePrimitiveColorSap(int color, String id) {
        this.color = color;
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        color = buf.readInt();
        id = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(color);
        ByteBufUtils.writeUTF8String(buf, id);
    }
    public static class Handler implements IMessageHandler<MessagePrimitiveColorSap, IMessage> {
        
        @Override
        public IMessage onMessage(MessagePrimitiveColorSap message, MessageContext ctx) 
        {
            Entity entity = (Entity)ctx.getServerHandler().player.getServerWorld().getEntityFromUuid(UUID.fromString(message.id));
         
            if(entity != null && entity instanceof EntityGroveSprite)
            {
            	EntityGroveSprite sprite = (EntityGroveSprite)entity;
            	if(!sprite.getHeldItemOffhand().isEmpty())
            	{
            		ItemGroveSpriteSap.setColor(sprite.getHeldItemOffhand(), message.color);
            		ItemGroveSpriteSap.setSapLogState(sprite, sprite.getHeldItemOffhand());
            	}
            }
            return null;
        }
    }

}