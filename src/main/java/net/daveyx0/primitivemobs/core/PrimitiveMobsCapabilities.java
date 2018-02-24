package net.daveyx0.primitivemobs.core;

import net.daveyx0.primitivemobs.common.capabilities.CapabilitySummonableEntity;

public class PrimitiveMobsCapabilities {

	public static void preInit()
	{
		CapabilitySummonableEntity.register();
	}
}
