package net.daveyx0.primitivemobs.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

	@SideOnly(Side.CLIENT)
	public class ModelTravelingMerchant extends ModelBase
	{
		  //fields
		    ModelRenderer Head;
		    ModelRenderer Body;
		    ModelRenderer LegLeft;
		    ModelRenderer LegRight;
		    ModelRenderer ArmLeftShoulder;
		    ModelRenderer ArmLeftHand;
		    ModelRenderer Burden1;
		    ModelRenderer Burden2;
		    ModelRenderer ArmRightShoulder;
		    ModelRenderer ArmRightHand;
		    ModelRenderer Nose;
		  
		  public ModelTravelingMerchant()
		  {
		    textureWidth = 128;
		    textureHeight = 64;
		    
		      Head = new ModelRenderer(this, 0, 0);
		      Head.addBox(-4F, -9.5F, -5.1F, 8, 10, 8);
		      Head.setRotationPoint(0F, 0F, 0F);
		      Head.setTextureSize(128, 64);
		      Head.mirror = true;
		      setRotation(Head, 0F, 0F, 0F);
		      Body = new ModelRenderer(this, 0, 39);
		      Body.addBox(-5F, 0F, -4F, 8, 17, 6);
		      Body.setRotationPoint(1F, 0.5F, 0F);
		      Body.setTextureSize(128, 64);
		      Body.mirror = true;
		      setRotation(Body, 0F, 0F, 0F);
		      LegLeft = new ModelRenderer(this, 0, 22);
		      LegLeft.addBox(-2.1F, 0F, -2F, 4, 12, 4);
		      LegLeft.setRotationPoint(2F, 12F, -1F);
		      LegLeft.setTextureSize(128, 64);
		      LegLeft.mirror = true;
		      setRotation(LegLeft, 0F, 0F, 0F);
		      LegRight = new ModelRenderer(this, 0, 22);
		      LegRight.addBox(-1.9F, 0F, -2F, 4, 12, 4);
		      LegRight.setRotationPoint(-2F, 12F, -1F);
		      LegRight.setTextureSize(128, 64);
		      LegRight.mirror = true;
		      setRotation(LegRight, 0F, 0F, 0F);
		      ArmLeftShoulder = new ModelRenderer(this, 40, 38);
		      ArmLeftShoulder.addBox(0F, -2F, -2F, 4, 4, 4);
		      ArmLeftShoulder.setRotationPoint(2F, 3F, 1F);
		      ArmLeftShoulder.setTextureSize(128, 64);
		      ArmLeftShoulder.mirror = true;
		      setRotation(ArmLeftShoulder, 0.6981317F, 0F, 0F);
		      ArmLeftHand = new ModelRenderer(this, 44, 22);
		      ArmLeftHand.addBox(0F, -6F, -6F, 4, 8, 4);
		      ArmLeftHand.setRotationPoint(2F, 3F, 1F);
		      ArmLeftHand.setTextureSize(128, 64);
		      ArmLeftHand.mirror = true;
		      setRotation(ArmLeftHand, 0.6981317F, 0F, 0F);
		      Burden1 = new ModelRenderer(this, 66, 26);
		      Burden1.addBox(-8F, 0F, 0F, 16, 6, 6);
		      Burden1.setRotationPoint(0F, -8F, 3F);
		      Burden1.setTextureSize(128, 64);
		      Burden1.mirror = true;
		      setRotation(Burden1, 0F, 0F, 0F);
		      Burden2 = new ModelRenderer(this, 66, 38);
		      Burden2.addBox(-7F, 0F, 0F, 14, 18, 8);
		      Burden2.setRotationPoint(0F, -2F, 3F);
		      Burden2.setTextureSize(128, 64);
		      Burden2.mirror = true;
		      setRotation(Burden2, 0F, 0F, 0F);
		      ArmRightShoulder = new ModelRenderer(this, 40, 38);
		      ArmRightShoulder.addBox(-4F, -2F, -2F, 4, 4, 4);
		      ArmRightShoulder.setRotationPoint(-2F, 3F, 1F);
		      ArmRightShoulder.setTextureSize(128, 64);
		      ArmRightShoulder.mirror = true;
		      setRotation(ArmRightShoulder, 0.6981317F, 0F, 0F);
		      ArmRightHand = new ModelRenderer(this, 44, 22);
		      ArmRightHand.addBox(-4F, -6F, -6F, 4, 8, 4);
		      ArmRightHand.setRotationPoint(-2F, 3F, 1F);
		      ArmRightHand.setTextureSize(128, 64);
		      ArmRightHand.mirror = true;
		      setRotation(ArmRightHand, 0.6981317F, 0F, 0F);
		      Nose = new ModelRenderer(this, 24, 0);
		      Nose.addBox(-1F, -2.5F, -7F, 2, 4, 2);
		      Nose.setRotationPoint(0F, 0F, 0F);
		      Nose.setTextureSize(128, 64);
		      Nose.mirror = true;
		      setRotation(Nose, 0F, 0F, 0F);
		  }
		  
		  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
		  {
		    super.render(entity, f, f1, f2, f3, f4, f5);
		    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		    Head.render(f5);
		    Body.render(f5);
		    LegLeft.render(f5);
		    LegRight.render(f5);
		    ArmLeftShoulder.render(f5);
		    ArmLeftHand.render(f5);
		    Burden1.render(f5);
		    Burden2.render(f5);
		    ArmRightShoulder.render(f5);
		    ArmRightHand.render(f5);
		    Nose.render(f5);
		  }
		  
		  private void setRotation(ModelRenderer model, float x, float y, float z)
		  {
		    model.rotateAngleX = x;
		    model.rotateAngleY = y;
		    model.rotateAngleZ = z;
		  }

	    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	    {
	        this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
	        this.Head.rotateAngleX = headPitch * 0.017453292F;
	        this.Nose.rotateAngleX = this.Head.rotateAngleX;
	        this.Nose.rotateAngleY = this.Head.rotateAngleY;
	        this.LegRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
	        this.LegLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
	        this.LegRight.rotateAngleY = 0.0F;
	        this.LegLeft.rotateAngleY = 0.0F;
	    }
	}
