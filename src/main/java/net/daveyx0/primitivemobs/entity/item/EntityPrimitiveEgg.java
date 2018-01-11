package net.daveyx0.primitivemobs.entity.item;

import net.daveyx0.primitivemobs.entity.passive.EntityDodo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityPrimitiveEgg extends EntityEgg {

	Class<? extends EntityAnimal> spawnEntityClass;
	
	public EntityPrimitiveEgg(World worldIn){
		super(worldIn);
		spawnEntityClass = EntityChicken.class;
	}
	
	  public EntityPrimitiveEgg(World worldIn, Class<? extends EntityAnimal> entity) {
		super(worldIn);
		this.spawnEntityClass = entity;
	}
	  
	    public EntityPrimitiveEgg(World worldIn, EntityLivingBase throwerIn, Class<? extends EntityAnimal> entity)
	    {
	        super(worldIn, throwerIn);
	        this.spawnEntityClass = entity;
	    }

	    public EntityPrimitiveEgg(World worldIn, double x, double y, double z, Class<? extends EntityAnimal> entity)
	    {
	        super(worldIn, x, y, z);
	        this.spawnEntityClass = entity;
	    }


	/**
     * Called when this EntityThrowable hits a block or entity.
     */
	@Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }

        if (!this.world.isRemote)
        {
            if (this.rand.nextInt(8) == 0)
            {
                int i = 1;

                if (this.rand.nextInt(32) == 0)
                {
                    i = 4;
                }

                for (int j = 0; j < i; ++j)
                {
                	EntityAnimal entity = (EntityAnimal)EntityRegistry.getEntry(spawnEntityClass).newInstance(world);
                	entity.setGrowingAge(-24000);
                	entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                    this.world.spawnEntity(entity);
                }
            }

            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }

}
