package net.daveyx0.primitivemobs.entity;

import net.minecraft.entity.EntityLivingBase;

public interface IAnimatedMob {

    void performAction(EntityLivingBase target, int id);
    
    void setAnimationState(int state);
    
    int getAnimationState();
    
    void setPreviousAnimationState(int state);
    
    int getPreviousAnimationState();
    
    void setAnimVar(float var);
    
    float getAnimVar();
}