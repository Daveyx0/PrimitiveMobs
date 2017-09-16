package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.primitivemobs.entity.EntitySwimmingCreature;
import net.daveyx0.primitivemobs.entity.monster.EntityLilyLurker;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class EntityAISwimmingUnderwater extends EntityAIBase
{
    private EntitySwimmingCreature swimmingEntity;
    private double xPosition;
    private double yPosition;
    private double zPosition;

    public EntityAISwimmingUnderwater(EntitySwimmingCreature entity)
    {
        this.swimmingEntity = entity;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.swimmingEntity, 6, 2);

        if(swimmingEntity instanceof EntityLilyLurker)
        {
        	EntityLilyLurker lurker = (EntityLilyLurker)swimmingEntity;
        	if(lurker.isCamouflaged())
        	{
        		return false;
        	}
        }
        if (vec3d == null)
        {
            return false;
        }
        else
        {
            this.xPosition = vec3d.x;
            this.yPosition = vec3d.y;
            this.zPosition = vec3d.z;
            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !this.swimmingEntity.getNavigator().noPath();
    }

    @Override
    public void startExecuting()
    {
        this.swimmingEntity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1.0D);
    }
}