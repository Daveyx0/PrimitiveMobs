package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.passive.EntitySquirrel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.math.MathHelper;

public class ModelSquirrel extends ModelBase
{
  //fields
    ModelRenderer Body;
    ModelRenderer Head;
    ModelRenderer LegLeft;
    ModelRenderer FootLeft;
    ModelRenderer LegRight;
    ModelRenderer FootRight;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer Nose;
    ModelRenderer PawLeft;
    ModelRenderer PawRight;
    ModelRenderer Tail1;
    ModelRenderer Cheeks;
    private float jumpRotation;
  
  public ModelSquirrel()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Body = new ModelRenderer(this, 0, 0);
      Body.addBox(-2F, -3F, -7F, 4, 4, 8);
      Body.setRotationPoint(0F, 22F, 3F);
      Body.setTextureSize(64, 32);
      Body.mirror = true;
      setRotation(Body, -0.3490659F, 0F, 0F);
      Head = new ModelRenderer(this, 24, 0);
      Head.addBox(-2F, -2F, -4F, 4, 3, 4);
      Head.setRotationPoint(0F, 19F, -3F);
      Head.setTextureSize(64, 32);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      LegLeft = new ModelRenderer(this, 8, 12);
      LegLeft.addBox(0F, -1.5F, -1F, 1, 3, 4);
      LegLeft.setRotationPoint(2F, 21F, 1F);
      LegLeft.setTextureSize(64, 32);
      LegLeft.mirror = true;
      setRotation(LegLeft, -0.3490659F, 0F, 0F);
      FootLeft = new ModelRenderer(this, 8, 19);
      FootLeft.addBox(0F, 2F, -1.5F, 1, 1, 4);
      FootLeft.setRotationPoint(2F, 21F, 1F);
      FootLeft.setTextureSize(64, 32);
      FootLeft.mirror = true;
      setRotation(FootLeft, 0F, 0F, 0F);
      LegRight = new ModelRenderer(this, 18, 12);
      LegRight.addBox(-1F, -1.5F, -1F, 1, 3, 4);
      LegRight.setRotationPoint(-2F, 21F, 1F);
      LegRight.setTextureSize(64, 32);
      LegRight.mirror = true;
      setRotation(LegRight, -0.3141593F, 0F, 0F);
      FootRight = new ModelRenderer(this, 18, 19);
      FootRight.addBox(-1F, 2F, -1.5F, 1, 1, 4);
      FootRight.setRotationPoint(-2F, 21F, 1F);
      FootRight.setTextureSize(64, 32);
      FootRight.mirror = true;
      setRotation(FootRight, 0F, 0F, 0F);
      Ear1 = new ModelRenderer(this, 47, 0);
      Ear1.addBox(0F, -4F, -0.5F, 2, 2, 1);
      Ear1.setRotationPoint(0F, 19F, -3F);
      Ear1.setTextureSize(64, 32);
      Ear1.mirror = true;
      setRotation(Ear1, 0F, 0.3490659F, 0F);
      Ear2 = new ModelRenderer(this, 41, 0);
      Ear2.addBox(-2F, -4F, -0.5F, 2, 2, 1);
      Ear2.setRotationPoint(0F, 19F, -3F);
      Ear2.setTextureSize(64, 32);
      Ear2.mirror = true;
      setRotation(Ear2, 0F, -0.3490659F, 0F);
      Nose = new ModelRenderer(this, 24, 7);
      Nose.addBox(-0.5F, -0.5F, -4.5F, 1, 1, 1);
      Nose.setRotationPoint(0F, 19F, -3F);
      Nose.setTextureSize(64, 32);
      Nose.mirror = true;
      setRotation(Nose, 0F, 0F, 0F);
      PawLeft = new ModelRenderer(this, 4, 12);
      PawLeft.addBox(-0.5F, 0F, -1F, 1, 4, 1);
      PawLeft.setRotationPoint(2F, 20F, -3F);
      PawLeft.setTextureSize(64, 32);
      PawLeft.mirror = true;
      setRotation(PawLeft, -0.3490659F, 0F, 0F);
      PawRight = new ModelRenderer(this, 0, 12);
      PawRight.addBox(-0.5F, 0F, -1F, 1, 4, 1);
      PawRight.setRotationPoint(-2F, 20F, -3F);
      PawRight.setTextureSize(64, 32);
      PawRight.mirror = true;
      setRotation(PawRight, -0.3490659F, 0F, 0F);
      Tail1 = new ModelRenderer(this, 28, 13);
      Tail1.addBox(-2F, -8F, 0F, 4, 9, 2);
      Tail1.setRotationPoint(0F, 21F, 4F);
      Tail1.setTextureSize(64, 32);
      Tail1.mirror = true;
      setRotation(Tail1, -0.3490659F, 0F, 0F);
      Cheeks = new ModelRenderer(this, 40, 3);
      Cheeks.addBox(-3F, -1.1F, -3.5F, 6, 2, 3);
      Cheeks.setRotationPoint(0F, 19F, -3F);
      Cheeks.setTextureSize(64, 32);
      Cheeks.mirror = true;
      setRotation(Cheeks, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    if (this.isChild)
    {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.56666666F, 0.56666666F, 0.56666666F);
        GlStateManager.translate(0.0F, 22.0F * f5, 2.0F * f5);
    	Head.render(f5);
    	Ear1.render(f5);
    	Ear2.render(f5);
    	Nose.render(f5);
    	//Cheeks.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
        GlStateManager.translate(0.0F, 36.0F * f5, 0.0F);
    	Body.render(f5);
    	LegLeft.render(f5);
    	FootLeft.render(f5);
    	LegRight.render(f5);
    	FootRight.render(f5);
    	PawLeft.render(f5);
    	PawRight.render(f5);
    	Tail1.render(f5);
        GlStateManager.popMatrix();
    }
    else
    {
    	GlStateManager.pushMatrix();
    	GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	GlStateManager.translate(0.0F, 16.0F * f5, 0.0F);
    	Body.render(f5);
    	Head.render(f5);
    	LegLeft.render(f5);
    	FootLeft.render(f5);
    	LegRight.render(f5);
    	FootRight.render(f5);
    	Ear1.render(f5);
    	Ear2.render(f5);
    	Nose.render(f5);
    	PawLeft.render(f5);
    	PawRight.render(f5);
    	Tail1.render(f5);
    	//Cheeks.render(f5);
    	GlStateManager.popMatrix();
    }
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
  {
    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    this.Nose.rotateAngleX = headPitch * 0.017453292F;
    this.Head.rotateAngleX = headPitch * 0.017453292F;
    this.Cheeks.rotateAngleX = headPitch * 0.017453292F;
    this.Ear1.rotateAngleX = headPitch * 0.017453292F;
    this.Ear2.rotateAngleX = headPitch * 0.017453292F;
    this.Nose.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Cheeks.rotateAngleY = netHeadYaw * 0.017453292F;
    this.Ear1.rotateAngleY = this.Nose.rotateAngleY + 0.3490659F;
    this.Ear2.rotateAngleY = this.Nose.rotateAngleY - 0.3490659F;
    
    this.LegLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * limbSwingAmount -0.3490659F;
    this.LegRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * limbSwingAmount -0.3490659F;
    this.FootLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * limbSwingAmount;
    this.FootRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * limbSwingAmount;
    this.PawLeft.rotateAngleX = -MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * limbSwingAmount;
    this.PawRight.rotateAngleX = -MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.8F * limbSwingAmount;
}

}
