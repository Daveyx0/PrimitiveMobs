package net.daveyx0.primitivemobs.message;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.entity.monster.EntityBabySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePrimitiveJumping implements IMessage 
{
    
    private String text;

    public MessagePrimitiveJumping() { }

    public MessagePrimitiveJumping(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
    }
    
    public static class Handler implements IMessageHandler<MessagePrimitiveJumping, IMessage> {
        
        @Override
        public IMessage onMessage(MessagePrimitiveJumping message, MessageContext ctx) 
        {
            //System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
        	UUID id = UUID.fromString(message.text);
        	if(ctx.getServerHandler().player != null)
        	{
        		EntityPlayer truePlayer = ctx.getServerHandler().player.world.getPlayerEntityByUUID(id);
                if(truePlayer.getRidingEntity() != null && truePlayer.getRidingEntity() instanceof EntityBabySpider)
                {
                	((EntityBabySpider)truePlayer.getRidingEntity()).setIsJumping(true);
                }
        	}


            return null;
            // no response in this case
        }
    }

}