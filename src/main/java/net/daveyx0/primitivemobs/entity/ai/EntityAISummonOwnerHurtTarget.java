package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.primitivemobs.common.capabilities.CapabilitySummonableEntity;
import net.daveyx0.primitivemobs.common.capabilities.ISummonableEntity;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveTameableMob;
import net.daveyx0.primitivemobs.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAISummonOwnerHurtTarget extends EntityCustomAITarget
{
	EntityLiving summon;
    EntityLivingBase attacker;
    private int timestamp;

    public EntityAISummonOwnerHurtTarget(EntityLiving entitySummoned)
    {
        super(entitySummoned, false);
        this.summon = entitySummoned;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.summon.hasCapability(CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null))
        {
            return false;
        }
        else
        {
        	ISummonableEntity summonable = EntityUtil.getCapability(summon, CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null);
        	EntityLivingBase entitylivingbase = summonable.getSummoner(summon);

            if (entitylivingbase == null)
            {
                return false;
            }
            else
            {
                this.attacker = entitylivingbase.getLastAttackedEntity();
                int i = entitylivingbase.getLastAttackedEntityTime();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);
        ISummonableEntity summonable = EntityUtil.getCapability(summon, CapabilitySummonableEntity.SUMMONABLE_ENTITY_CAPABILITY, null);
    	EntityLivingBase entitylivingbase = summonable.getSummoner(summon);

        if (entitylivingbase != null)
        {
            this.timestamp = entitylivingbase.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}