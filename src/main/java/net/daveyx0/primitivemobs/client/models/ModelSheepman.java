package net.daveyx0.primitivemobs.client.models;

import org.lwjgl.opengl.GL11;

import net.daveyx0.primitivemobs.entity.passive.EntitySheepman;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSheepman extends ModelBase
{
    int scale;
  //fields
    ModelRenderer Head;
    ModelRenderer HeadWool;
    ModelRenderer Body;
    ModelRenderer LegLeft;
    ModelRenderer LegRight;
    ModelRenderer ArmLeft;
    ModelRenderer ArmRight;
    ModelRenderer BodyWool;
    ModelRenderer LegLeftWool;
    ModelRenderer LegRightWool;
    ModelRenderer FoldedArms;
  
  public ModelSheepman(int scale)
  {
    textureWidth = 64;
    textureHeight = 64;
    this.scale = scale;
    
      Head = new ModelRenderer(this, 0, 0);
      Head.addBox(-3F, -6F, -6F, 6, 6, 8);
      Head.setRotationPoint(0F, 2F, 0F);
      Head.setTextureSize(64, 64);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      HeadWool = new ModelRenderer(this, 0, 14);
      HeadWool.addBox(-3F, -6F, -4F, 6, 6, 6);
      HeadWool.setRotationPoint(0F, 2F, 0F);
      HeadWool.setTextureSize(64, 64);
      HeadWool.mirror = true;
      setRotation(HeadWool, 0F, 0F, 0F);
      Body = new ModelRenderer(this, 0, 42);
      Body.addBox(-4F, 0F, -3F, 8, 12, 6);
      Body.setRotationPoint(0F, 2F, 0F);
      Body.setTextureSize(64, 64);
      Body.mirror = true;
      setRotation(Body, 0F, 0F, 0F);
      LegLeft = new ModelRenderer(this, 0, 26);
      LegLeft.addBox(-2F, 0F, -2F, 4, 12, 4);
      LegLeft.setRotationPoint(2F, 12F, 0F);
      LegLeft.setTextureSize(64, 64);
      LegLeft.mirror = true;
      setRotation(LegLeft, 0F, 0F, 0F);
      LegRight = new ModelRenderer(this, 0, 26);
      LegRight.addBox(-2F, 0F, -2F, 4, 12, 4);
      LegRight.setRotationPoint(-2F, 12F, 0F);
      LegRight.setTextureSize(64, 64);
      LegRight.mirror = true;
      setRotation(LegRight, 0F, 0F, 0F);
      ArmLeft = new ModelRenderer(this, 16, 26);
      ArmLeft.addBox(0F, 0F, -2F, 3, 12, 4);
      ArmLeft.setRotationPoint(4F, 2F, 0F);
      ArmLeft.setTextureSize(64, 64);
      ArmLeft.mirror = true;
      setRotation(ArmLeft, 0F, 0F, 0F);
      ArmRight = new ModelRenderer(this, 16, 26);
      ArmRight.addBox(-3F, 0F, -2F, 3, 12, 4);
      ArmRight.setRotationPoint(-4F, 2F, 0F);
      ArmRight.setTextureSize(64, 64);
      ArmRight.mirror = true;
      setRotation(ArmRight, 0F, 0F, 0F);
      BodyWool = new ModelRenderer(this, 36, 0);
      BodyWool.addBox(-4F, 0F, -3F, 8, 12, 6);
      BodyWool.setRotationPoint(0F, 2F, 0F);
      BodyWool.setTextureSize(64, 64);
      BodyWool.mirror = true;
      setRotation(BodyWool, 0F, 0F, 0F);
      LegLeftWool = new ModelRenderer(this, 30, 26);
      LegLeftWool.addBox(2F, 0F, -2F, 4, 4, 4);
      LegLeftWool.setRotationPoint(-2F, 12F, 0F);
      LegLeftWool.setTextureSize(64, 64);
      LegLeftWool.mirror = true;
      setRotation(LegLeftWool, 0F, 0F, 0F);
      LegRightWool = new ModelRenderer(this, 30, 26);
      LegRightWool.addBox(-2F, 0F, -2F, 4, 4, 4);
      LegRightWool.setRotationPoint(-2F, 12F, 0F);
      LegRightWool.setTextureSize(64, 64);
      LegRightWool.mirror = true;
      setRotation(LegRightWool, 0F, 0F, 0F);
      FoldedArms = new ModelRenderer(this, 20, 52);
      FoldedArms.addBox(-7F, 0F, -8F, 14, 4, 8);
      FoldedArms.setRotationPoint(0F, 2F, 0F);
      FoldedArms.setTextureSize(64, 64);
      FoldedArms.mirror = true;
      setRotation(FoldedArms, 0.5235988F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    
    EntitySheepman sheepman = (EntitySheepman)entity;
    
    if(sheepman.isChild())
    {     
        float var8 = 1.5F;
        GL11.glPushMatrix();
        GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
        GL11.glTranslatef(0.0F, 28.0F * f5 - 1, 0.0F);
        if(scale == 0)
        {
            Head.render(f5);
            Body.render(f5);
            LegLeft.render(f5);
            LegRight.render(f5);
            if(sheepman.getSheared())
            {
                ArmLeft.render(f5);
                ArmRight.render(f5);
            }
        }
        else
        {
            FoldedArms.render(f5);
        	GL11.glScalef(1.1F, 1.1F, 1.1F);
            HeadWool.render(f5);
            LegLeftWool.render(f5);
            LegRightWool.render(f5);
            GL11.glTranslatef(0.0F, -0.05F, 0.0F);
            BodyWool.render(f5);
        }
        GL11.glPopMatrix();
    }
    else
    {
    	GL11.glPushMatrix();
        if(scale == 0)
        {
            Head.render(f5);
            Body.render(f5);
            LegLeft.render(f5);
            LegRight.render(f5);
            if(sheepman.getSheared())
            {
                ArmLeft.render(f5);
                ArmRight.render(f5);
            }
        }
        else
        {
            FoldedArms.render(f5);
        	GL11.glScalef(1.1F, 1.1F, 1.1F);
            HeadWool.render(f5);
            LegLeftWool.render(f5);
            LegRightWool.render(f5);
            GL11.glTranslatef(0.0F, -0.05F, 0.0F);
            BodyWool.render(f5);
        }
        GL11.glPopMatrix();
    }
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
    this.Head.rotateAngleY = f4 / (180F / (float)Math.PI);
    this.Head.rotateAngleX = f5 / (180F / (float)Math.PI);
    this.HeadWool.rotateAngleY = this.Head.rotateAngleY;
    this.HeadWool.rotateAngleX = this.Head.rotateAngleX;
    this.LegRight.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
    this.LegLeft.rotateAngleX = - MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
    this.LegRightWool.rotateAngleX = this.LegRight.rotateAngleX;
    this.LegLeftWool.rotateAngleX = this.LegLeft.rotateAngleX;
    
    this.ArmRight.rotateAngleX = -MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
    this.ArmLeft.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.6F * f1;
  }

}