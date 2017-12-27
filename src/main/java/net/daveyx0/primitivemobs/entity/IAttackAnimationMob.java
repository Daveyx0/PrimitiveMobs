package net.daveyx0.primitivemobs.entity;

import net.minecraft.entity.EntityLivingBase;

public interface IAttackAnimationMob {

    void performAttack(EntityLivingBase target, int id);
    
    void setAnimationState(int state);
    
    int getAnimationState();
}