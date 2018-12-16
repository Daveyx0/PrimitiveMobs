package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGoblin extends ModelBiped
{
	  //fields
    ModelRenderer Nose;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer Helmet;
    ModelRenderer Armor;
    ModelRenderer Bag;
  
  public ModelGoblin()
  {
    textureWidth = 128;
    textureHeight = 64;
    this.leftArmPose = ModelBiped.ArmPose.EMPTY;
    this.rightArmPose = ModelBiped.ArmPose.EMPTY;
    
      bipedHead = new ModelRenderer(this, 0, 0);
      bipedHead.addBox(-4F, -9F, -5.1F, 8, 10, 8);
      bipedHead.setRotationPoint(0F, 7F, -3F);
      bipedHead.setTextureSize(128, 64);
      bipedHead.mirror = true;
      setRotation(bipedHead, 0F, 0F, 0F);
      bipedBody = new ModelRenderer(this, 17, 20);
      bipedBody.addBox(-5F, 0F, -4F, 10, 10, 6);
      bipedBody.setRotationPoint(0F, 6F, 0F);
      bipedBody.setTextureSize(128, 64);
      bipedBody.mirror = true;
      setRotation(bipedBody, 0F, 0F, 0F);
      bipedLeftLeg = new ModelRenderer(this, 0, 22);
      bipedLeftLeg.addBox(-2.1F, 0F, -2F, 4, 8, 4);
      bipedLeftLeg.setRotationPoint(2F, 16F, -1F);
      bipedLeftLeg.setTextureSize(128, 64);
      bipedLeftLeg.mirror = true;
      setRotation(bipedLeftLeg, 0F, 0F, 0F);
      bipedRightLeg = new ModelRenderer(this, 0, 22);
      bipedRightLeg.addBox(-1.9F, 0F, -2F, 4, 8, 4);
      bipedRightLeg.setRotationPoint(-2F, 16F, -1F);
      bipedRightLeg.setTextureSize(128, 64);
      bipedRightLeg.mirror = true;
      setRotation(bipedRightLeg, 0F, 0F, 0F);
      bipedRightArm = new ModelRenderer(this, 48, 22);
      bipedRightArm.addBox(-4F, -1F, -2F, 4, 10, 4);
      bipedRightArm.setRotationPoint(-5F, 7F, -1F);
      bipedRightArm.setTextureSize(128, 64);
      bipedRightArm.mirror = true;
      setRotation(bipedRightArm, 0F, 0F, 0F);


      bipedLeftArm = new ModelRenderer(this, 48, 22);
      bipedLeftArm.addBox(0F, -1F, -2F, 4, 10, 4);
      bipedLeftArm.setRotationPoint(5F, 7F, -1F);
      bipedLeftArm.setTextureSize(128, 64);
      bipedLeftArm.mirror = true;
      setRotation(bipedLeftArm, 0F, 0F, 0F);
      
      Nose = new ModelRenderer(this, 24, 0);
      Nose.addBox(-1F, -2F, -7F, 2, 4, 2);
      Nose.setTextureSize(128, 64);
      setRotation(Nose, -0.3490659F, 0F, 0F);
      Nose.mirror = true;
      Ear1 = new ModelRenderer(this, 33, 0);
      Ear1.addBox(0F, -9F, 0F, 1, 3, 5);
      Ear1.setTextureSize(128, 64);
      Ear1.mirror = true;
      setRotation(Ear1, 0.4537856F, 0F, 0.5759587F);
      Ear2 = new ModelRenderer(this, 46, 0);
      Ear2.addBox(-1F, -9F, 0F, 1, 3, 5);
      Ear2.setTextureSize(128, 64);
      Ear2.mirror = true;
      setRotation(Ear2, 0.4537856F, 0F, -0.5759587F);
      Helmet = new ModelRenderer(this, 0, 36);
      Helmet.addBox(-4.5F, -9.5F, -5.5F, 9, 11, 9);
      Helmet.setTextureSize(128, 64);
      Helmet.mirror = true;
      setRotation(Helmet, 0F, 0F, 0F);
      Armor = new ModelRenderer(this, 37, 36);
      Armor.addBox(-5.5F, -0.5F, -4.5F, 11, 11, 7);
      Armor.setRotationPoint(0F, 6F, 0F);
      Armor.setTextureSize(128, 64);
      Armor.mirror = true;
      setRotation(Armor, 0F, 0F, 0F);
      Bag = new ModelRenderer(this, 64, 0);
      Bag.addBox(-8F, -8F, 0F, 16, 16, 16);
      Bag.setRotationPoint(0F, 8F, 2F);
      Bag.setTextureSize(128, 64);
      Bag.mirror = true;
      setRotation(Bag, 0.1396263F, 0F, 0F);
      
      bipedHead.addChild(Nose);
      bipedHead.addChild(Ear1);
      bipedHead.addChild(Ear2);
      bipedHead.addChild(Helmet);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Armor.render(f5);
    Bag.render(f5);
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
    this.bipedHead.rotationPointY = 7.0F;
    this.bipedRightLeg.rotationPointY = 16.0F;
    this.bipedLeftLeg.rotationPointY = 16.0F;
    
    this.Bag.rotateAngleX = MathHelper.cos(f * 0.6662F * 1.0F + 0.0F) * 0.1F * f1 ;
    
  }
  
  private float rotate(float p_78172_1_, float p_78172_2_)
  {
      return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
  }

}