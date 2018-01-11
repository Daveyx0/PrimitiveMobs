package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelMimic extends ModelBase
{
	 //fields
  ModelRenderer Top;
  ModelRenderer Lock;
  ModelRenderer Bottom;
  ModelRenderer Teeth;

public ModelMimic()
{
  textureWidth = 64;
  textureHeight = 64;
  
    Top = new ModelRenderer(this, 0, 0);
    Top.addBox(-7F, -5F, -14F, 14, 5, 14);
    Top.setRotationPoint(0F, 15F, 7F);
    Top.setTextureSize(64, 64);
    Top.mirror = true;
    setRotation(Top, 0F, 0F, 0F);
    Lock = new ModelRenderer(this, 0, 0);
    Lock.addBox(-1F, -2F, -15F, 2, 4, 1);
    Lock.setRotationPoint(0F, 15F, 7F);
    Lock.setTextureSize(64, 64);
    Lock.mirror = true;
    setRotation(Lock, 0F, 0F, 0F);
    Bottom = new ModelRenderer(this, 0, 19);
    Bottom.addBox(-7F, 0F, -7F, 14, 10, 14);
    Bottom.setRotationPoint(0F, 14F, 0F);
    Bottom.setTextureSize(64, 64);
    Bottom.mirror = true;
    setRotation(Bottom, 0F, 0F, 0F);
    Teeth = new ModelRenderer(this, 0, 43);
    Teeth.addBox(-6.5F, -1F, -6.5F, 13, 1, 13);
    Teeth.setRotationPoint(0F, 14F, 0F);
    Teeth.setTextureSize(64, 64);
    Teeth.mirror = true;
    setRotation(Teeth, 0F, 0F, 0F);
}

public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
{
  super.render(entity, f, f1, f2, f3, f4, f5);
  setRotationAngles(f, f1, f2, f3, f4, f5, entity);

  	   Top.render(f5);
	   Lock.render(f5);
	   Bottom.render(f5);
	   Teeth.render(f5);
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
  Top.rotateAngleX = f2;
  Lock.rotateAngleX = f2;
}

}
