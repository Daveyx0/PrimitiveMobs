package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVoidEye extends ModelBase
{
  //fields
    ModelRenderer body1;
    ModelRenderer body2;
    ModelRenderer eye;
    boolean renderBody;
  
  public ModelVoidEye(boolean renderBody)
  {
    textureWidth = 64;
    textureHeight = 64;
    this.renderBody = renderBody;
    
      body1 = new ModelRenderer(this, 0, 10);
      body1.addBox(-4.5F, -4.5F, -4.5F, 9, 9, 9);
      body1.setRotationPoint(0F, 17F, 0F);
      body1.setTextureSize(64, 64);
      body1.mirror = true;
      setRotation(body1, 0F, 0F, 0F);
      body2 = new ModelRenderer(this, 0, 28);
      body2.addBox(-6.5F, -6.5F, -6.5F, 13, 13, 19);
      body2.setRotationPoint(0F, 17F, 0F);
      body2.setTextureSize(64, 64);
      body2.mirror = true;
      setRotation(body2, 0F, 0F, 0F);
      eye = new ModelRenderer(this, 0, 0);
      eye.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5);
      eye.setRotationPoint(0F, 17F, 0F);
      eye.setTextureSize(64, 64);
      eye.mirror = true;
      setRotation(eye, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    if(renderBody)
    {
        body1.render(f5);
        body2.render(f5);
    }

    eye.render(f5);
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
    body1.rotateAngleZ -= 0.0075F;
    body2.rotateAngleZ += 0.0075F;
    eye.rotateAngleY = f3 / 57.29578F;
    eye.rotateAngleX = f4 / 57.29578F;
  }

}