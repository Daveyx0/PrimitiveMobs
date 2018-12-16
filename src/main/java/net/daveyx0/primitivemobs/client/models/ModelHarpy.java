package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.monster.EntityHarpy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelHarpy extends ModelBase
{
  //fields
    ModelRenderer body;
    ModelRenderer legLeftOverlay;
    ModelRenderer legRightOverlay;
    ModelRenderer head;
    ModelRenderer wingLeft;
    ModelRenderer wingRight;
    ModelRenderer legRight;
    ModelRenderer legLeft;
    ModelRenderer tail;
    private ModelHarpy.State state = ModelHarpy.State.STANDING;
  
  public ModelHarpy()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      body = new ModelRenderer(this, 0, 16);
      body.addBox(-4F, -6F, -3F, 10, 14, 8);
      body.setRotationPoint(-1F, 10F, -2F);
      body.setTextureSize(64, 64);
      body.mirror = true;
      setRotation(body, 0.1745329F, 0F, 0F);
      legLeftOverlay = new ModelRenderer(this, 20, 38);
      legLeftOverlay.addBox(-2.5F, -2F, -2.5F, 5, 6, 5);
      legLeftOverlay.setRotationPoint(3F, 18F, 0F);
      legLeftOverlay.setTextureSize(64, 64);
      legLeftOverlay.mirror = true;
      setRotation(legLeftOverlay, 0F, 0F, 0F);
      legRightOverlay = new ModelRenderer(this, 0, 38);
      legRightOverlay.addBox(-2.5F, -2F, -2.5F, 5, 6, 5);
      legRightOverlay.setRotationPoint(-3F, 18F, 0F);
      legRightOverlay.setTextureSize(64, 64);
      legRightOverlay.mirror = true;
      setRotation(legRightOverlay, 0F, 0F, 0F);
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-4F, -4F, -8F, 8, 8, 8);
      head.setRotationPoint(0F, 7F, -4F);
      head.setTextureSize(64, 64);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      wingLeft = new ModelRenderer(this, 44, 38);
      wingLeft.addBox(0F, 0F, -2F, 1, 14, 8);
      wingLeft.setRotationPoint(5F, 5F, -4F);
      wingLeft.setTextureSize(64, 64);
      wingLeft.mirror = true;
      setRotation(wingLeft, 0.2443461F, 0F, 0F);
      wingRight = new ModelRenderer(this, 44, 16);
      wingRight.addBox(-1F, 0F, -2F, 1, 14, 8);
      wingRight.setRotationPoint(-5F, 5F, -4F);
      wingRight.setTextureSize(64, 64);
      wingRight.mirror = true;
      setRotation(wingRight, 0.2443461F, 0F, 0F);
      legRight = new ModelRenderer(this, 0, 49);
      legRight.addBox(-2F, 1F, -2F, 4, 5, 4);
      legRight.setRotationPoint(-3F, 18F, 0F);
      legRight.setTextureSize(64, 64);
      legRight.mirror = true;
      setRotation(legRight, 0F, 0F, 0F);
      legLeft = new ModelRenderer(this, 16, 49);
      legLeft.addBox(-2F, 1F, -2F, 4, 5, 4);
      legLeft.setRotationPoint(3F, 18F, 0F);
      legLeft.setTextureSize(64, 64);
      legLeft.mirror = true;
      setRotation(legLeft, 0F, 0F, 0F);
      tail = new ModelRenderer(this, 32, 0);
      tail.addBox(-4F, 0F, 0F, 8, 10, 1);
      tail.setRotationPoint(0F, 17F, 3F);
      tail.setTextureSize(64, 64);
      tail.mirror = true;
      setRotation(tail, 1.047198F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    EntityLivingBase entityliving = (EntityLivingBase)entity;
    
        body.render(f5);
        legLeftOverlay.render(f5);
        legRightOverlay.render(f5);
        head.render(f5);
        wingLeft.render(f5);
        wingRight.render(f5);
        legRight.render(f5);
        legLeft.render(f5);
        tail.render(f5);
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
    this.head.rotateAngleX = headPitch * 0.017453292F;
    this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    this.head.rotateAngleZ = 0F;

    if (this.state != ModelHarpy.State.FLYING)
    {
        if (this.state == ModelHarpy.State.SITTING)
        {
            return;
        }

        this.legLeft.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legRight.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    }
    
    float f = ageInTicks * 0.3F;
    this.wingLeft.rotateAngleZ = -0.0873F - ageInTicks;
    this.wingLeft.rotationPointY = 5F + f;
    this.wingRight.rotateAngleZ = 0.0873F + ageInTicks;
    this.wingRight.rotationPointY = 5F + f;
    this.legLeftOverlay.rotateAngleX = this.legLeft.rotateAngleX;
    this.legLeftOverlay.rotateAngleZ = this.legLeft.rotateAngleZ;
    this.legRightOverlay.rotateAngleX = this.legRight.rotateAngleX;
    this.legRightOverlay.rotateAngleZ = this.legRight.rotateAngleZ;
  }
  
  /**
   * Used for easily adding entity-dependent animations. The second and third float params here are the same second
   * and third as in the setRotationAngles method.
   */
  public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
  {
      this.body.rotateAngleX = 0.2443461F;
      this.legLeft.rotateAngleX = 0F;
      this.legRight.rotateAngleX = 0F;

      if (entitylivingbaseIn instanceof EntityHarpy)
      {
    	  EntityHarpy entityharpy = (EntityHarpy)entitylivingbaseIn;

          if (entityharpy.isFlying())
          {
              this.legLeft.rotateAngleX += ((float)Math.PI * 2F / 9F);
              this.legRight.rotateAngleX += ((float)Math.PI * 2F / 9F);
              state = ModelHarpy.State.FLYING;
          }
          else
          {
        	  state = ModelHarpy.State.STANDING;
          }

          this.legLeft.rotateAngleZ = 0.0F;
          this.legRight.rotateAngleZ = 0.0F;
      }
  }
  
  
  @SideOnly(Side.CLIENT)
  static enum State
  {
      FLYING,
      STANDING,
      SITTING;
  }
}

