package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLilyLurker extends ModelBase
{
  //fields
    ModelRenderer Body1;
    ModelRenderer Fin1;
    ModelRenderer Fin2;
    ModelRenderer Body2;
    ModelRenderer Fin3;
    ModelRenderer Fin4;
    public ModelRenderer Root1;
    ModelRenderer Root2;
  
  public ModelLilyLurker()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Body1 = new ModelRenderer(this, 28, 0);
      Body1.addBox(-4F, 0F, -5F, 8, 4, 10);
      Body1.setRotationPoint(0F, 20F, 0F);
      Body1.setTextureSize(64, 32);
      Body1.mirror = true;
      setRotation(Body1, 0F, 0F, 0F);
      Fin1 = new ModelRenderer(this, 0, 0);
      Fin1.addBox(0F, 0F, -1.5F, 6, 1, 3);
      Fin1.setRotationPoint(3F, 22F, -1F);
      Fin1.setTextureSize(64, 32);
      Fin1.mirror = true;
      setRotation(Fin1, 0F, -0.6806784F, 0F);
      Fin2 = new ModelRenderer(this, 0, 0);
      Fin2.addBox(0F, 0F, -1.5F, 6, 1, 3);
      Fin2.setRotationPoint(-3F, 22F, -1F);
      Fin2.setTextureSize(64, 32);
      Fin2.mirror = true;
      setRotation(Fin2, 0F, -2.460914F, 0F);
      Body2 = new ModelRenderer(this, 0, 10);
      Body2.addBox(-3F, -1F, 0F, 6, 3, 2);
      Body2.setRotationPoint(0F, 22F, 5F);
      Body2.setTextureSize(64, 32);
      Body2.mirror = true;
      setRotation(Body2, 0F, 0F, 0F);
      Fin3 = new ModelRenderer(this, 0, 4);
      Fin3.addBox(0F, 0F, 0F, 2, 1, 5);
      Fin3.setRotationPoint(0F, 22F, 7F);
      Fin3.setTextureSize(64, 32);
      Fin3.mirror = true;
      setRotation(Fin3, 0F, 0.4712389F, 0F);
      Fin4 = new ModelRenderer(this, 0, 4);
      Fin4.addBox(-2F, 0F, 0F, 2, 1, 5);
      Fin4.setRotationPoint(0F, 22F, 7F);
      Fin4.setTextureSize(64, 32);
      Fin4.mirror = true;
      setRotation(Fin4, 0F, -0.4712389F, 0F);
      Root1 = new ModelRenderer(this, 18, 0);
      Root1.addBox(-0.5F, -5F, -0.5F, 1, 5, 1);
      Root1.setRotationPoint(0F, 21F, -2F);
      Root1.setTextureSize(64, 32);
      Root1.mirror = true;
      setRotation(Root1, -0.6981317F, 0F, 0F);
      Root2 = new ModelRenderer(this, 18, 0);
      Root2.addBox(-0.5F, -4F, -0.5F, 1, 5, 1);
      Root2.setRotationPoint(0F, 16.5F, 1F);
      Root2.setTextureSize(64, 32);
      Root2.mirror = true;
      setRotation(Root2, 0.3316126F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Body1.render(f5);
    Fin1.render(f5);
    Fin2.render(f5);
    Body2.render(f5);
    Fin3.render(f5);
    Fin4.render(f5);
    if(entity != null && entity instanceof EntityLiving)
    {
    	EntityLiving living = (EntityLiving)entity;
    	if(living.getHeldItemMainhand() != null)
    	{
    		Root1.render(f5);
    		Root2.render(f5);
    	}
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
    Fin1.rotateAngleY = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 -0.6806784F;
    Fin2.rotateAngleY = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1 -2.460914F;
    Fin3.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1;
    Fin4.rotateAngleX = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1;
  }

}