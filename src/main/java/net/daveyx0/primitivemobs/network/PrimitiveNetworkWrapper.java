package net.daveyx0.primitivemobs.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * A small network implementation/wrapper using AbstractPackets instead of IMessages.
 * Instantiate in your mod class and register your packets accordingly.
 *
 * @author Slimeknights
 */
public class PrimitiveNetworkWrapper {

  public final SimpleNetworkWrapper network;

  public PrimitiveNetworkWrapper(String channelName) {
    network = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
  }
  
  public static void sendPacket(Entity player, Packet<?> packet) {
	    if(player instanceof EntityPlayerMP && ((EntityPlayerMP) player).connection != null) {
	      ((EntityPlayerMP) player).connection.sendPacket(packet);
	  }
  }
}