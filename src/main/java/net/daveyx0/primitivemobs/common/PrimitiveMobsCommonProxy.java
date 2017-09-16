package net.daveyx0.primitivemobs.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class PrimitiveMobsCommonProxy
{
	
private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();


public void preInit(FMLPreInitializationEvent event) {

}

public void init(FMLInitializationEvent event) {

}

public void postInit(FMLPostInitializationEvent event) {

}

public static void storeEntityData(String name, NBTTagCompound compound)
{
extendedEntityData.put(name, compound);
}

public static NBTTagCompound getEntityData(String name)
{
return extendedEntityData.remove(name);
}
}