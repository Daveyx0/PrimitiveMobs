package net.daveyx0.primitivemobs.core;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.daveyx0.primitivemobs.message.MessagePrimitiveColor;
import net.daveyx0.primitivemobs.message.MessagePrimitiveParticle;
import net.daveyx0.primitivemobs.message.MessagePrimitiveSummonable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class PrimitiveMobsMessages {

		private static int id = 0;
		
		public static void preInit() {
	    	registerMessages();
	    }

		public static void registerMessages() {
			registerMessage(MessagePrimitiveColor.Handler.class, MessagePrimitiveColor.class, Side.SERVER);
			registerMessage(MessagePrimitiveParticle.Handler.class, MessagePrimitiveParticle.class, Side.CLIENT);
			registerMessage(MessagePrimitiveSummonable.Handler.class, MessagePrimitiveSummonable.class, Side.CLIENT);
		}

		private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side receivingSide) {
			
			PrimitiveMobs.getSimpleNetworkWrapper().registerMessage(messageHandler, requestMessageType, id++, receivingSide);
		}
	
}
