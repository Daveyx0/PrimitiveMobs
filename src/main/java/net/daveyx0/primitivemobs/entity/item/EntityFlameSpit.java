package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.multimob.client.particle.MMParticles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityFlameSpit extends EntitySmallFireball {

	public EntityFlameSpit(World worldIn){
		super(worldIn);
	}
	
	public EntityFlameSpit(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
		super(worldIn, shooter, accelX, accelY, accelZ);
		
	}
	
    protected EnumParticleTypes getParticleType()
    {
        return EnumParticleTypes.FLAME;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	super.onUpdate();
        if (this.world.isRemote)
        {
        	for(int i = 0; i < 10; i++)
        	{
        		MMParticles.spawnParticle("flame", this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new float[3]);
        	}
        }
        
        if(this.ticksExisted > 30)
        {
        	this.setDead();
        }
    }

}
