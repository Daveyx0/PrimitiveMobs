package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.passive.EntityLostMiner;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

	@SideOnly(Side.CLIENT)
	public class ModelLostMiner extends ModelVillager
	{
	    
		public ModelRenderer ArmLeftShoulder;
	    public ModelRenderer ArmLeftHand;
	    public ModelRenderer ArmRightShoulder;
	    public ModelRenderer ArmRightHand;
	    public ModelRenderer Hat1;
	    public ModelRenderer Hat2;

	    public ModelLostMiner(float scale)
	    {
	        this(scale, 0.0F, 64, 64);
	    }

	    public ModelLostMiner(float scale, float p_i1164_2_, int width, int height)
	    {
	        super(scale, p_i1164_2_, width, height);
	        ArmLeftShoulder = new ModelRenderer(this, 44, 0).setTextureSize(width, height);
	        ArmLeftShoulder.addBox(2F, -4F, -2F, 4, 4, 4);
	        ArmLeftShoulder.setRotationPoint(2F, 3F, 1F);
	        ArmLeftShoulder.setTextureSize(64, 64);
	        ArmLeftShoulder.mirror = true;
	        setRotation(ArmLeftShoulder, 1.396263F, 0F, 0F);
	        ArmLeftHand = new ModelRenderer(this, 44, 9).setTextureSize(width, height);;
	        ArmLeftHand.addBox(2F, -8F, -6F, 4, 8, 4);
	        ArmLeftHand.setRotationPoint(2F, 3F, 1F);
	        ArmLeftHand.setTextureSize(64, 64);
	        ArmLeftHand.mirror = true;
	        setRotation(ArmLeftHand, 1.396263F, 0F, 0F);
	        ArmRightShoulder = new ModelRenderer(this, 44, 0).setTextureSize(width, height);;
	        ArmRightShoulder.addBox(-6F, -4F, -2F, 4, 4, 4);
	        ArmRightShoulder.setRotationPoint(-2F, 3F, 1F);
	        ArmRightShoulder.setTextureSize(64, 64);
	        ArmRightShoulder.mirror = true;
	        setRotation(ArmRightShoulder, 1.396263F, 0F, 0F);
	        ArmRightHand = new ModelRenderer(this, 44, 9).setTextureSize(width, height);;
	        ArmRightHand.addBox(-6F, -8F, -6F, 4, 8, 4);
	        ArmRightHand.setRotationPoint(-2F, 3F, 1F);
	        ArmRightHand.setTextureSize(64, 64);
	        ArmRightHand.mirror = true;
	        setRotation(ArmRightHand, 1.396263F, 0F, 0F);
	        Hat1 = new ModelRenderer(this, 28, 51).setTextureSize(width, height);;
	        Hat1.addBox(-4.5F, -10.5F, -4.5F, 9, 4, 9);
	        //Hat1.setRotationPoint(0F, 0.5F, 0F);
	        Hat1.setTextureSize(64, 64);
	        Hat1.mirror = true;
	        setRotation(Hat1, 0F, 0F, 0F);
	        Hat2 = new ModelRenderer(this, 28, 46).setTextureSize(width, height);;
	        Hat2.addBox(-1F, -10F, -5.6F, 2, 2, 1);
	        //Hat2.setRotationPoint(0F, 1F, 0F);
	        Hat2.setTextureSize(64, 64);
	        Hat2.mirror = true;
	        setRotation(Hat2, 0F, 0F, 0F);
	        
	        this.villagerHead.addChild(Hat1);
	        this.villagerHead.addChild(Hat2);
	    }

	    /**
	     * Sets the models various rotation angles then renders the model.
	     */
	    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	    {
	        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
	        this.villagerHead.render(scale);
	        this.villagerBody.render(scale);
	        this.rightVillagerLeg.render(scale);
	        this.leftVillagerLeg.render(scale);
	        if(entityIn instanceof EntityLostMiner)
	        {
	        	EntityLostMiner lostMiner = (EntityLostMiner)entityIn;
	        	
	        	if(lostMiner.isSaved())
	        	{
	    	        this.villagerArms.render(scale);
	        	}
	        	else
	        	{
	    	        this.ArmLeftShoulder.render(scale);
	    	        this.ArmLeftHand.render(scale);
	    	        this.ArmRightShoulder.render(scale);
	    	        this.ArmRightHand.render(scale);
	        	}
	        }

	    }
	    
	    private void setRotation(ModelRenderer model, float x, float y, float z)
	    {
	      model.rotateAngleX = x;
	      model.rotateAngleY = y;
	      model.rotateAngleZ = z;
	    }

	    /**
	     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	     * "far" arms and legs can swing at most.
	     */
	    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	    {
	        this.villagerHead.rotateAngleY = netHeadYaw * 0.017453292F;
	        this.villagerHead.rotateAngleX = headPitch * 0.017453292F;
	        this.villagerArms.rotationPointY = 3.0F;
	        this.villagerArms.rotationPointZ = -1.0F;
	        this.villagerArms.rotateAngleX = -0.75F;
	        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
	        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
	        this.rightVillagerLeg.rotateAngleY = 0.0F;
	        this.leftVillagerLeg.rotateAngleY = 0.0F;
	        
	        this.ArmLeftShoulder.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F + 1.396263F;
	        this.ArmLeftHand.rotateAngleX = this.ArmLeftShoulder.rotateAngleX;
	        this.ArmRightShoulder.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F + 1.396263F;
	        this.ArmRightHand.rotateAngleX = this.ArmRightShoulder.rotateAngleX;
	    }
	}
