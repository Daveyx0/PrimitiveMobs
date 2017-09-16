package net.daveyx0.primitivemobs.client.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPrimitiveTrading extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {

		return false;
	}

}
