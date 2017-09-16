package net.daveyx0.primitivemobs.message;

import java.util.Iterator;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePrimitiveColor implements IMessage 
{
    
    private int color;
    private EntityEquipmentSlot slot;
    private String id;

    public MessagePrimitiveColor() { }

    public MessagePrimitiveColor(int color, EntityEquipmentSlot slot, String id) {
        this.color = color;
        this.slot = slot;
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        color = buf.readInt();
        slot = EntityEquipmentSlot.values()[buf.readByte()];
        id = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(color);
        buf.writeByte(slot.ordinal());
        ByteBufUtils.writeUTF8String(buf, id);
    }
    public static class Handler implements IMessageHandler<MessagePrimitiveColor, IMessage> {
        
        @Override
        public IMessage onMessage(MessagePrimitiveColor message, MessageContext ctx) 
        {
            EntityLivingBase entity = (EntityLivingBase)ctx.getServerHandler().player.getServerWorld().getEntityFromUuid(UUID.fromString(message.id));
            if(entity != null)
            {
            	ItemStack armor = entity.getItemStackFromSlot(message.slot);
            
            	if(armor.getItem() instanceof ItemCamouflageArmor)
            	{
            		ItemCamouflageArmor camo = (ItemCamouflageArmor)armor.getItem();
            		camo.setColor(armor, message.color);
            	}
            }
            return null;
        }
    }

}