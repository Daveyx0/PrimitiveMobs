package net.daveyx0.primitivemobs.common.capabilities;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class CapabilityProviderSerializable<H> implements ICapabilityProvider, INBTSerializable<NBTBase>  {

	protected final Capability<H> capability;
	protected final EnumFacing facing;
	
	protected final H instance;
	
	public CapabilityProviderSerializable(Capability<H> capability, @Nullable EnumFacing facing, @Nullable H instance)
	{
		this.capability = capability;
		this.facing = facing;
		this.instance = instance;
	}
	
	public CapabilityProviderSerializable(Capability<H> capability, @Nullable EnumFacing facing)
	{
		this(capability, facing, capability.getDefaultInstance());
	}
	
	public CapabilityProviderSerializable(Capability<H> capability)
	{
		this(capability, null, capability.getDefaultInstance());
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

		return capability == getCapability();
	}

	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

		if (capability == getCapability()) {
			return getCapability().cast(getInstance());
		}

		return null;
	}
	

	@Override
	public NBTBase serializeNBT() {
		return getCapability().writeNBT(getInstance(), getFacing());
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		getCapability().readNBT(getInstance(), getFacing(), nbt);
	}
	
	@Nullable
	public EnumFacing getFacing() {
		return facing;
	}
	
	public final Capability<H> getCapability() {
		return capability;
	}
	
	@Nullable
	public final H getInstance() {
		return instance;
	}
}
