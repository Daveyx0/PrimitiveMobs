package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.core.PrimitiveMobsLogger;
import net.daveyx0.primitivemobs.entity.IAttackAnimationMob;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelTrollager extends ModelBase
{
	public ModelRenderer Head;
    public ModelRenderer Body;
    public ModelRenderer LegLeft;
    public ModelRenderer LegRight;
    public ModelRenderer ArmLeft;
    public ModelRenderer Nose;
    public ModelRenderer ArmRight;
    public ModelRenderer BlockHolder;
    public ModelRenderer Mouth;
  
  public ModelTrollager()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Head = new ModelRenderer(this, 0, 0);
      Head.addBox(-4F, -9.5F, -5.1F, 8, 10, 8);
      Head.setRotationPoint(0F, 5F, -10F);
      Head.setTextureSize(128, 64);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      Body = new ModelRenderer(this, 0, 20);
      Body.addBox(-8F, -10.5F, -6F, 16, 21, 12);
      Body.setRotationPoint(0F, 6.5F, 0F);
      Body.setTextureSize(128, 64);
      Body.mirror = true;
      setRotation(Body, 0.9599311F, 0F, 0F);
      LegLeft = new ModelRenderer(this, 69, 3);
      LegLeft.addBox(-3F, 0F, -3F, 6, 9, 6);
      LegLeft.setRotationPoint(4F, 15F, 5F);
      LegLeft.setTextureSize(128, 64);
      LegLeft.mirror = true;
      setRotation(LegLeft, 0F, 0F, 0F);
      LegRight = new ModelRenderer(this, 69, 3);
      LegRight.addBox(-3F, 0F, -3F, 6, 9, 6);
      LegRight.setRotationPoint(-4F, 15F, 5F);
      LegRight.setTextureSize(128, 64);
      LegRight.mirror = true;
      setRotation(LegRight, 0F, 0F, 0F);
      ArmLeft = new ModelRenderer(this, 57, 24);
      ArmLeft.addBox(-6F, -3F, -3F, 6, 23, 6);
      ArmLeft.setRotationPoint(8F, 4F, -6F);
      ArmLeft.setTextureSize(128, 64);
      ArmLeft.mirror = true;
      setRotation(ArmLeft, 0F, 3.141593F, 0F);
      Nose = new ModelRenderer(this, 24, 0);
      Nose.addBox(-1F, -2.5F, -7F, 2, 5, 2);
      //Nose.setRotationPoint(0F, 4F, -10F);
      Nose.setTextureSize(128, 64);
      Nose.mirror = true;
      setRotation(Nose, -0.1570796F, 0F, 0F);
      ArmRight = new ModelRenderer(this, 57, 24);
      ArmRight.addBox(-6F, -3F, -3F, 6, 23, 6);
      ArmRight.setRotationPoint(-8F, 4F, -6F);
      ArmRight.setTextureSize(128, 64);
      ArmRight.mirror = true;
      setRotation(ArmRight, 0F, 0F, 0F);
      BlockHolder = new ModelRenderer(this, 57, 24);
      BlockHolder.addBox(-6F, -3F, -3F, 6, 23, 6);
      BlockHolder.setRotationPoint(-8F, 4F, -6F);
      BlockHolder.setTextureSize(128, 64);
      BlockHolder.mirror = true;
      setRotation(BlockHolder, 0F, 0F, 0F);
      Mouth = new ModelRenderer(this, 32, 5);
      Mouth.addBox(-4.5F, -2F, -5.5F, 9, 4, 9);
      //Mouth.setRotationPoint(0F, 5F, -10F);
      Mouth.setTextureSize(128, 64);
      Mouth.mirror = true;
      setRotation(Mouth, 0F, 0F, 0F);
      
      Head.addChild(Nose);
      Head.addChild(Mouth);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Head.render(f5);
    Body.render(f5);
    LegLeft.render(f5);
    LegRight.render(f5);
    ArmLeft.render(f5);
    //Nose.render(f5);
    ArmRight.render(f5);
    //Mouth.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    
    EntityTrollager troll = (EntityTrollager)entity;
    
    float speed = 1f;

    Head.rotateAngleX = f4 / (180F / (float)Math.PI);
    Head.rotateAngleY = f3 / (180F / (float)Math.PI);
    
    LegRight.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 0.8F * f1;
    LegLeft.rotateAngleX = -MathHelper.cos(f * 0.6662F + (float)Math.PI) * 0.8F * f1;

    if(troll != null)
    {
    	switch (troll.getAnimationState())
    	{
    		case 0: 
    		{
    			//Idle and walking animations\\
    		    ArmLeft.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 0.8F * f1;
    		    ArmRight.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 0.8F * f1;
    		    ArmLeft.rotateAngleZ = 0.0f;
    		    ArmRight.rotateAngleZ = 0.0f;
    			Body.rotateAngleX = 0.9599311F;
    			Body.offsetY = 0F;
    			ArmLeft.offsetY = 0F;
    			ArmRight.offsetY = 0F;
    			Head.offsetY = 0F;
    			Head.offsetZ = 0F;
    			break;
    		}
    		case 1: 
    		{
    			//Prepare throwing attack animation\\
    			speed = 3f;
    			if(troll.getAnimVar() != 1)
    			{
    			ArmLeft.rotateAngleX = (float)MathHelper.clampedLerp(0, 3.5f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleX = (float)MathHelper.clampedLerp(0, -3.5f, troll.getAnimVar() * speed);
    			BlockHolder.rotateAngleX = (float)MathHelper.clampedLerp(0, -3.5f, troll.getAnimVar() * speed);
    			ArmLeft.rotateAngleZ = (float)MathHelper.clampedLerp(0.1, -0.22f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleZ = (float)MathHelper.clampedLerp(-0.1, 0.22f, troll.getAnimVar() * speed);
    			ArmLeft.offsetY = 0F;
    			ArmRight.offsetY = 0F;
    			
    			Body.rotateAngleX = 0.9599311F;
    			Body.offsetY = 0F;
    			Head.offsetY = 0F;
    			Head.offsetZ = 0F;
    			}
    			break;
    		}
    		case 2:
    		{
    			//Perform Throwing animation\\
    			speed = 8f;
    			if(troll.getAnimVar() != 1)
    			{
    			ArmLeft.rotateAngleX = (float)MathHelper.clampedLerp(3.5f, 0, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleX = (float)MathHelper.clampedLerp(-3.5f, 0, troll.getAnimVar() * speed);
    			BlockHolder.rotateAngleX = (float)MathHelper.clampedLerp(-3.5f, 0, troll.getAnimVar() * speed);
    			ArmLeft.rotateAngleZ = (float)MathHelper.clampedLerp(-0.22, 0.1f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleZ = (float)MathHelper.clampedLerp(0.22, -0.1f, troll.getAnimVar() * speed);
    			ArmLeft.offsetY = 0F;
    			ArmRight.offsetY = 0F;

    			Body.rotateAngleX = 0.9599311F;
    			Body.offsetY = 0F;
    			Head.offsetY = 0F;
    			Head.offsetZ = 0F;
    			this.Mouth.rotateAngleX = 0.15f;
    			this.Mouth.rotateAngleZ = 0.0f;
    			}
    			break;
    		}
    		case 3:
    			//Prepare crushing attack animation\\
    			speed = 3f;
    			if(troll.getAnimVar() != 1)
    			{
    			ArmLeft.rotateAngleX = (float)MathHelper.clampedLerp(0, 3.5f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleX = (float)MathHelper.clampedLerp(0, -3.5f, troll.getAnimVar() * speed);
    			ArmLeft.rotateAngleZ = (float)MathHelper.clampedLerp(0.1, -0.22f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleZ = (float)MathHelper.clampedLerp(-0.1, 0.22f, troll.getAnimVar() * speed);
    			ArmLeft.offsetY = 0F;
    			ArmRight.offsetY = 0F;
    			
    			Body.rotateAngleX = 0.9599311F;
    			Body.offsetY = 0F;
    			Head.offsetY = 0F;
    			Head.offsetZ = 0F;
    			}
    			break;
    		case 4:
    			//Perform crushing attack animation\\
    			speed = 12f;
    			if(troll.getAnimVar() != 1)
    			{
    			ArmLeft.rotateAngleX = (float)MathHelper.clampedLerp(3.5f, 0.5f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleX = (float)MathHelper.clampedLerp(-3.5F, -0.5F, troll.getAnimVar() * speed);
    		    ArmLeft.rotateAngleZ = 0.0f;
    		    ArmRight.rotateAngleZ = 0.0f;
    			ArmLeft.offsetY = (float)MathHelper.clampedLerp(0F, 0.2F, troll.getAnimVar() * speed);
    			ArmRight.offsetY = (float)MathHelper.clampedLerp(0F, 0.2F, troll.getAnimVar() * speed);
    			
    			Body.rotateAngleX = (float)MathHelper.clampedLerp(0.9599311F, 1.32645F, troll.getAnimVar() * speed);
    			Body.offsetY = (float)MathHelper.clampedLerp(0F, 0.2F, troll.getAnimVar() * speed);
    			Head.offsetY = (float)MathHelper.clampedLerp(0F, 0.2F, troll.getAnimVar() * speed);
    			Head.offsetZ = (float)MathHelper.clampedLerp(0F, -0.1F, troll.getAnimVar() * speed);
    			this.Mouth.rotateAngleX = 0.15f;
    			this.Mouth.rotateAngleZ = 0.0f;
    			}
    			break;
    		case 5:
    			//Recover from crushing attack animation\\
    			speed = 10f;
    			if(troll.getAnimVar() != 1)
    			{
    			ArmLeft.rotateAngleX = (float)MathHelper.clampedLerp(0.5f, 0.0f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleX = (float)MathHelper.clampedLerp(-0.5F, 0.0F, troll.getAnimVar() * speed);
    		    ArmLeft.rotateAngleZ = 0.0f;
    		    ArmRight.rotateAngleZ = 0.0f;
    			ArmLeft.offsetY = (float)MathHelper.clampedLerp(0.2F, 0.0F, troll.getAnimVar() * speed);
    			ArmRight.offsetY = (float)MathHelper.clampedLerp(0.2F, 0.0F, troll.getAnimVar() * speed);
    			
    			Body.rotateAngleX = (float)MathHelper.clampedLerp(1.32645F, 0.9599311F, troll.getAnimVar() * speed);
    			Body.offsetY = (float)MathHelper.clampedLerp(0.2F, 0.0F, troll.getAnimVar() * speed);
    			Head.offsetY = (float)MathHelper.clampedLerp(0.2F, 0.0F, troll.getAnimVar() * speed);
    			Head.offsetZ = (float)MathHelper.clampedLerp(-0.1F, 0.0F, troll.getAnimVar() * speed);
    			}
    			break;
    		case 6:
    			//Perform melee attack
    			speed = 22f;
    			if(troll.getAnimVar() != 1)
    			{
    			ArmLeft.rotateAngleX = (float)MathHelper.clampedLerp(0, 1.5f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleX = (float)MathHelper.clampedLerp(0, -1.5f, troll.getAnimVar() * speed);
    			ArmLeft.rotateAngleZ = 0.0f;
    			ArmRight.rotateAngleZ = 0.0f;
    			ArmLeft.offsetY = 0F;
    			ArmRight.offsetY = 0F;
    			
    			Body.rotateAngleX = 0.9599311F;
    			Body.offsetY = 0F;
    			Head.offsetY = 0F;
    			Head.offsetZ = 0F;
    			}
    		    break;
    		case 7:
    			//Recover melee attack
    			speed = 22f;
    			if(troll.getAnimVar() != 1)
    			{
    			ArmLeft.rotateAngleX = (float)MathHelper.clampedLerp(1.5f, 0f, troll.getAnimVar() * speed);
    			ArmRight.rotateAngleX = (float)MathHelper.clampedLerp(-1.5f, 0, troll.getAnimVar() * speed);
    			ArmLeft.rotateAngleZ = 0.0f;
    			ArmRight.rotateAngleZ = 0.0f;
    			ArmLeft.offsetY = 0F;
    			ArmRight.offsetY = 0F;
    			
    			Body.rotateAngleX = 0.9599311F;
    			Body.offsetY = 0F;
    			Head.offsetY = 0F;
    			Head.offsetZ = 0F;
    			}
    		    break;
    		default: 
    			break;
    	}
    	
    	if(troll.getAnimationState() != 2 && troll.getAnimationState() != 4 && !troll.isStone())
    	{
		    idleMouth((float)entity.ticksExisted * 0.5F);
    	}
    }
  }
  
  public void idleMouth(float e)
  {
	    this.Mouth.offsetX = 0.0F;
	    this.Mouth.offsetY = 0.0F;
	    this.Mouth.offsetZ = 0.0F;   

	    this.Mouth.rotateAngleX = MathHelper.sin(e) * 4.5F * 0.017453292F;
	    this.Mouth.rotateAngleY = 0.0F;
	    this.Mouth.rotateAngleZ = MathHelper.cos(e) * 2.5F * 0.017453292F;
  }

}