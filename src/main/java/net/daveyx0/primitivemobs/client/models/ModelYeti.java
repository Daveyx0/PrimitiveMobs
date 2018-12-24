package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.monster.EntityYeti;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelYeti extends ModelBase
{
	  //fields
	    ModelRenderer Head;
	    ModelRenderer Body;
	    ModelRenderer LegLeft;
	    ModelRenderer LegRight;
	    ModelRenderer ArmLeft;
	    ModelRenderer Nose;
	    public ModelRenderer ArmRight;
	    ModelRenderer ArmLeftHide;
	    ModelRenderer ArmRightHide;
	    ModelRenderer HeadHide;
	    ModelRenderer LegRightHide;
	    ModelRenderer LegLeftHide;
	  
	  public ModelYeti()
	  {
	    textureWidth = 128;
	    textureHeight = 128;
	    
	      Head = new ModelRenderer(this, 0, 0);
	      Head.addBox(-4F, -9.5F, -5F, 8, 10, 8);
	      Head.setRotationPoint(0F, -9F, -1F);
	      Head.setTextureSize(128, 128);
	      Head.mirror = true;
	      setRotation(Head, 0F, 0F, 0F);
	      Body = new ModelRenderer(this, 0, 20);
	      Body.addBox(-8F, -10.5F, -6F, 16, 21, 12);
	      Body.setRotationPoint(0F, 0F, 3.5F);
	      Body.setTextureSize(128, 128);
	      Body.mirror = true;
	      setRotation(Body, 0.1745329F, 0F, 0F);
	      LegLeft = new ModelRenderer(this, 69, 3);
	      LegLeft.addBox(-3F, 0F, -3F, 6, 14, 6);
	      LegLeft.setRotationPoint(4F, 10F, 5F);
	      LegLeft.setTextureSize(128, 128);
	      LegLeft.mirror = true;
	      setRotation(LegLeft, 0F, 0F, 0F);
	      LegRight = new ModelRenderer(this, 69, 3);
	      LegRight.addBox(-3F, 0F, -3F, 6, 14, 6);
	      LegRight.setRotationPoint(-4F, 10F, 5F);
	      LegRight.setTextureSize(128, 128);
	      LegRight.mirror = true;
	      setRotation(LegRight, 0F, 0F, 0F);
	      LegRight.mirror = false;
	      ArmLeft = new ModelRenderer(this, 57, 24);
	      ArmLeft.addBox(-6F, -3F, -3F, 6, 23, 6);
	      ArmLeft.setRotationPoint(8F, -5F, 2F);
	      ArmLeft.setTextureSize(128, 128);
	      ArmLeft.mirror = true;
	      setRotation(ArmLeft, 0F, 3.141593F, 0F);
	      Nose = new ModelRenderer(this, 24, 0);
	      Nose.addBox(-1F, -2.5F, -7F, 2, 5, 2);
	      Nose.setRotationPoint(0F, -9F, -1F);
	      Nose.setTextureSize(128, 128);
	      Nose.mirror = true;
	      setRotation(Nose, -0.1570796F, 0F, 0F);
	      ArmRight = new ModelRenderer(this, 57, 24);
	      ArmRight.addBox(-6F, -3F, -3F, 6, 23, 6);
	      ArmRight.setRotationPoint(-8F, -5F, 2F);
	      ArmRight.setTextureSize(128, 128);
	      ArmRight.mirror = true;
	      setRotation(ArmRight, 0F, 0F, 0F);
	      ArmLeftHide = new ModelRenderer(this, 57, 55);
	      ArmLeftHide.addBox(-6.5F, -3.5F, -3.5F, 7, 17, 7);
	      ArmLeftHide.setRotationPoint(8F, -5F, 2F);
	      ArmLeftHide.setTextureSize(128, 128);
	      ArmLeftHide.mirror = true;
	      setRotation(ArmLeftHide, 0F, 3.141593F, 0F);
	      ArmRightHide = new ModelRenderer(this, 57, 55);
	      ArmRightHide.addBox(-6.5F, -3.533333F, -3.5F, 7, 17, 7);
	      ArmRightHide.setRotationPoint(-8F, -5F, 2F);
	      ArmRightHide.setTextureSize(128, 128);
	      ArmRightHide.mirror = true;
	      setRotation(ArmRightHide, 0F, 0F, 0F);
	      HeadHide = new ModelRenderer(this, 0, 54);
	      HeadHide.addBox(-4.5F, -10F, -5.5F, 9, 11, 9);
	      HeadHide.setRotationPoint(0F, -9F, -1F);
	      HeadHide.setTextureSize(128, 128);
	      HeadHide.mirror = true;
	      setRotation(HeadHide, 0F, 0F, 0F);
	      LegRightHide = new ModelRenderer(this, 83, 24);
	      LegRightHide.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
	      LegRightHide.setRotationPoint(-4F, 10F, 5F);
	      LegRightHide.setTextureSize(128, 128);
	      LegRightHide.mirror = true;
	      setRotation(LegRightHide, 0F, 0F, 0F);
	      LegLeftHide = new ModelRenderer(this, 83, 24);
	      LegLeftHide.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
	      LegLeftHide.setRotationPoint(4F, 10F, 5F);
	      LegLeftHide.setTextureSize(128, 128);
	      LegLeftHide.mirror = true;
	      setRotation(LegLeftHide, 0F, 0F, 0F);
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
	    Nose.render(f5);
	    ArmRight.render(f5);
	    ArmLeftHide.render(f5);
	    ArmRightHide.render(f5);
	    HeadHide.render(f5);
	    LegRightHide.render(f5);
	    LegLeftHide.render(f5);
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
	    
	      EntityYeti yeti = (EntityYeti)entity;
	      float i = 0;//yeti.getAttackTimer();

	      
	      if (i < 20)
	      {
	    	  ArmRight.rotateAngleX = -2.0F + 1.5F * this.func_78172_a(i - f5, 10.0F);
	    	  ArmLeft.rotateAngleX = 2.0F + 1.5F * this.func_78172_a(i - f5, 10.0F);
	      }
	      else
	      {
	    	  ArmRight.rotateAngleX = 1.5F * this.func_78172_a(f, 13.0F) * f1;;
	    	  ArmLeft.rotateAngleX = 1.5F * this.func_78172_a(f, 13.0F) * f1;
	      }
	      
	  
	      Head.rotateAngleY = f3 / 57.29578F;
	      Head.rotateAngleX = f4 / 57.29578F;
	      Nose.rotateAngleY = HeadHide.rotateAngleY = Head.rotateAngleY;
	      Nose.rotateAngleX = HeadHide.rotateAngleX = Head.rotateAngleX;
	      ArmRightHide.rotateAngleX = ArmRight.rotateAngleX;
	      ArmLeftHide.rotateAngleX = ArmLeft.rotateAngleX;
	      LegRight.rotateAngleX = 1.5F * this.func_78172_a(f, 13.0F) * f1;
	      LegRightHide.rotateAngleX = LegRight.rotateAngleX;
	      LegLeft.rotateAngleX = -1.5F * this.func_78172_a(f, 13.0F) * f1;
	      LegLeftHide.rotateAngleX = LegLeft.rotateAngleX;
	  }
	  
	    private float func_78172_a(float p_78172_1_, float p_78172_2_)
	    {
	        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
	    }
}
