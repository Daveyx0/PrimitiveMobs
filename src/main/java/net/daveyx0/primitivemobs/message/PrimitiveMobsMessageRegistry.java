package net.daveyx0.primitivemobs.message;

import net.daveyx0.multimob.message.MMMessageRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class PrimitiveMobsMessageRegistry extends MMMessageRegistry{

	public static void registerMessages()
	{
		registerMessage(MessagePrimitiveColor.Handler.class, MessagePrimitiveColor.class, Side.SERVER);
		registerMessage(MessagePrimitiveJumping.Handler.class, MessagePrimitiveJumping.class, Side.SERVER);
		registerMessage(MessageTeleportEye.Handler.class, MessageTeleportEye.class, Side.SERVER);

		//registerMessage(MessagePrimitiveSummonable.Handler.class, MessagePrimitiveSummonable.class, Side.CLIENT);
	}
}
