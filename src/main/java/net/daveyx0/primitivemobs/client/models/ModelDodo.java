package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelDodo extends ModelBase
{
  //fields
    ModelRenderer Body;
    ModelRenderer Neck;
    ModelRenderer Beak;
    ModelRenderer Wing1;
    ModelRenderer Wing2;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Feet1;
    ModelRenderer Feet2;
    ModelRenderer Tail;
  
  public ModelDodo()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Body = new ModelRenderer(this, 0, 12);
      Body.addBox(-4F, -4F, -6F, 8, 8, 12);
      Body.setRotationPoint(0F, 16F, 0F);
      Body.setTextureSize(64, 32);
      Body.mirror = true;
      setRotation(Body, -0.1919862F, 0F, 0F);
      Body.mirror = false;
      Neck = new ModelRenderer(this, 29, 0);
      Neck.addBox(-2F, -10F, -3F, 4, 10, 4);
      Neck.setRotationPoint(0F, 16F, -6F);
      Neck.setTextureSize(64, 32);
      Neck.mirror = true;
      setRotation(Neck, -0.2443461F, 0F, 0F);
      Beak = new ModelRenderer(this, 14, 0);
      Beak.addBox(-1F, -10F, -3F, 2, 3, 5);
      Beak.setRotationPoint(0F, 16F, -6F);
      Beak.setTextureSize(64, 32);
      Beak.mirror = true;
      setRotation(Beak, 0.2792527F, 0F, 0F);
      Wing1 = new ModelRenderer(this, 0, 14);
      Wing1.addBox(0F, 0F, 0F, 1, 5, 5);
      Wing1.setRotationPoint(4F, 12F, -3F);
      Wing1.setTextureSize(64, 32);
      Wing1.mirror = true;
      setRotation(Wing1, -0.1919862F, 0F, 0F);
      Wing2 = new ModelRenderer(this, 28, 14);
      Wing2.addBox(-1F, 0F, 0F, 1, 5, 5);
      Wing2.setRotationPoint(-4F, 12F, -3F);
      Wing2.setTextureSize(64, 32);
      Wing2.mirror = true;
      setRotation(Wing2, -0.1919862F, 0F, 0F);
      Leg1 = new ModelRenderer(this, 0, 0);
      Leg1.addBox(-0.5F, 0F, 0F, 1, 4, 1);
      Leg1.setRotationPoint(2F, 20F, 0F);
      Leg1.setTextureSize(64, 32);
      Leg1.mirror = true;
      setRotation(Leg1, 0F, 0F, 0F);
      Leg2 = new ModelRenderer(this, 0, 0);
      Leg2.addBox(-0.5F, 0F, 0F, 1, 4, 1);
      Leg2.setRotationPoint(-2F, 20F, 0F);
      Leg2.setTextureSize(64, 32);
      Leg2.mirror = true;
      setRotation(Leg2, 0F, 0F, 0F);
      Feet1 = new ModelRenderer(this, 1, 8);
      Feet1.addBox(-1.5F, 3F, -2F, 3, 1, 3);
      Feet1.setRotationPoint(2F, 20F, 0F);
      Feet1.setTextureSize(64, 32);
      Feet1.mirror = true;
      setRotation(Feet1, 0F, 0F, 0F);
      Feet2 = new ModelRenderer(this, 1, 8);
      Feet2.addBox(-1.5F, 3F, -2F, 3, 1, 3);
      Feet2.setRotationPoint(-2F, 20F, 0F);
      Feet2.setTextureSize(64, 32);
      Feet2.mirror = true;
      setRotation(Feet2, 0F, 0F, 0F);
      Tail = new ModelRenderer(this, 4, 0);
      Tail.addBox(-1F, -1F, 0F, 2, 2, 3);
      Tail.setRotationPoint(0F, 13F, 5F);
      Tail.setTextureSize(64, 32);
      Tail.mirror = true;
      setRotation(Tail, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);

    if (this.isChild)
    {
        float f6 = 2.0F;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 5.0F * f5, 2.0F * f5);
        Neck.render(f5);
        Beak.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
        GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
        Body.render(f5);
        Wing1.render(f5);
        Wing2.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Feet1.render(f5);
        Feet2.render(f5);
        Tail.render(f5);
        GlStateManager.popMatrix();
    }
    else
    {
    	Body.render(f5);
        Neck.render(f5);
        Beak.render(f5);
        Wing1.render(f5);
        Wing2.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Feet1.render(f5);
        Feet2.render(f5);
        Tail.render(f5);
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
    this.Neck.rotateAngleX = f4 / (220F / (float)Math.PI) - 0.2443461F;
    this.Neck.rotateAngleY = f3 / (220F / (float)Math.PI);
    this.Beak.rotateAngleX = f4 / (220F / (float)Math.PI) + 0.2792527F;
    this.Beak.rotateAngleY = f3 / (220F / (float)Math.PI);
    //this.Body.rotateAngleX = ((float)Math.PI / 2F) -0.1919862F;
    this.Leg2.rotateAngleX = MathHelper.cos(f* 0.6662F) * 1.4F * f1;
    this.Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
    this.Feet1.rotateAngleX = this.Leg1.rotateAngleX;
    this.Feet2.rotateAngleX = this.Leg2.rotateAngleX;
    this.Wing2.rotateAngleZ = f2;
    this.Wing1.rotateAngleZ = -f2;
  }

}