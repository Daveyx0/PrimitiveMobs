package net.daveyx0.primitivemobs.message;

import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.core.PrimitiveMobsItems;
import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.daveyx0.primitivemobs.item.ItemCamouflageArmor;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePrimitiveParticle implements IMessage 
{
    
    int id;
    int amount;
    int block;
    float x;
    float y;
    float z;
    double xVel;
    double yVel;
    double zVel;

    public MessagePrimitiveParticle() { }

    public MessagePrimitiveParticle(int particleId, int amount, float x, float y, float z, double xVelocity, double yVelocity, double zVelocity, int blockID) {
        this.id = particleId;
        this.amount = amount;
        this.block = blockID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xVel = xVelocity;
        this.yVel = yVelocity;
        this.zVel = zVelocity;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        amount = buf.readInt();
        block = buf.readInt();
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();
        xVel = buf.readDouble();
        yVel = buf.readDouble();
        zVel = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(amount);
        buf.writeInt(block);
    	buf.writeFloat(x);
    	buf.writeFloat(y);
    	buf.writeFloat(z);
    	buf.writeDouble(xVel);
    	buf.writeDouble(yVel);
    	buf.writeDouble(zVel);
    }
    public static class Handler implements IMessageHandler<MessagePrimitiveParticle, IMessage> {
        
        @Override
        public IMessage onMessage(MessagePrimitiveParticle message, MessageContext ctx) 
        {
        	
        	PrimitiveMobs.proxy.getThreadListener(ctx).addScheduledTask(() -> {
        		
        		if(message.amount > 0)
        		{
                for(int i = 0; i < message.amount; i++)
                {
                	Random rand = PrimitiveMobs.proxy.getClientWorld().rand;
                	if(message.block == 0)
                	{
                		PrimitiveMobs.proxy.getClientWorld().spawnParticle(EnumParticleTypes.getParticleFromId(message.id), message.x + (rand.nextFloat() - rand.nextFloat()), message.y, message.z + (rand.nextFloat() - rand.nextFloat()), message.xVel, message.yVel, message.zVel);
                	}
                	else
                	{
                		PrimitiveMobs.proxy.getClientWorld().spawnParticle(EnumParticleTypes.getParticleFromId(message.id), message.x + (rand.nextFloat() - rand.nextFloat()), message.y, message.z + (rand.nextFloat() - rand.nextFloat()), (rand.nextFloat() - rand.nextFloat()), 1.0D, (rand.nextFloat() - rand.nextFloat()), message.block);
                	}
                }
        		}
        		/*
        		EntityLivingBase entity = (EntityLivingBase)PrimitiveMobs.proxy.getClientWorld().getEntityByID(message.id);
        		if(entity != null && entity instanceof EntityTrollager)
        		{
        			EntityTrollager troll = (EntityTrollager)entity;
            		troll.playParticles(message.x, message.y, message.z);
        		}*/
        	});
            return null;
        }
    }

}