package net.daveyx0.primitivemobs.event;

import net.daveyx0.primitivemobs.spawn.PrimitiveMobsWorldSpawner;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PrimitiveMobsSpawnerEventHandler {
	
	    private PrimitiveMobsWorldSpawner worldSpawner;

	    public PrimitiveMobsSpawnerEventHandler()
	    {
	        this.worldSpawner = new PrimitiveMobsWorldSpawner();
	    }

	    @SubscribeEvent
	    public void onWorldTickEvent(TickEvent.WorldTickEvent event)
	    {
	        if (event.phase != TickEvent.WorldTickEvent.Phase.START || !(event.world instanceof WorldServer))
	        {
	            return;
	        }

	        WorldServer worldServer = (WorldServer)event.world;

	        if (worldServer.getGameRules().getBoolean("doMobSpawning"))
	        {
	            WorldInfo worldInfo = worldServer.getWorldInfo();

	            if (worldInfo.getWorldTotalTime() % 40L == 0L)
	            {
	                this.worldSpawner.doCustomSpawning(worldServer);
	            }
	        }
	    }

	    public PrimitiveMobsWorldSpawner getWorldSpawner()
	    {
	        return this.worldSpawner;
	    }
	}