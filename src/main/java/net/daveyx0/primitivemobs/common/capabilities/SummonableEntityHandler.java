package net.daveyx0.primitivemobs.common.capabilities;

import java.util.UUID;
import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author Daveyx0
 **/
public class SummonableEntityHandler implements ISummonableEntity {

	protected UUID summonerID;
	protected boolean isSummoned;
	protected boolean isFollowing;
	
	public SummonableEntityHandler()
	{
		summonerID = null;
		isSummoned = false;
		isFollowing = false;
	}
	
	public SummonableEntityHandler(UUID id)
	{
		summonerID = id;
		isSummoned = true;
		isFollowing = true;
	}
	
	@Override
	@Nullable
	public EntityLivingBase getSummoner(EntityLivingBase entityIn) {
		try
        {
            UUID uuid = this.getSummonerId();
            if(uuid != null)
            	{
            	EntityPlayer player = entityIn.world.getPlayerEntityByUUID(uuid);
            		if(player != null){return player;}else { 
            			EntityLivingBase entity = EntityUtil.getLoadedEntityByUUID(uuid, entityIn.world);
            			if(entity != null) {return entity;} else {return null;}
            		}
            	}
            else {return null;}
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
	}
	
    public boolean isOwner(EntityLivingBase thisEntity, EntityLivingBase entityIn)
    {
        return entityIn == this.getSummoner(thisEntity);
    }

	@Override
	public void setSummoner(UUID id) {
		summonerID = id;
	}

	@Override
	public boolean isSummonedEntity() {

		return isSummoned;
	}

	@Override
	public void setSummonedEntity(boolean set) {
		isSummoned = set;
	}
	
	@Override
	public UUID getSummonerId() {

		return summonerID;
	}
	
	@Override
	public boolean isFollowing() {

		return isFollowing;
	}

	@Override
	public void setFollowing(boolean set) {

		isFollowing = set;
	}
	
	
	private static class Factory implements Callable<ISummonableEntity> {

		  @Override
		  public ISummonableEntity call() throws Exception {
		    return new SummonableEntityHandler();
		  }
	}

}
