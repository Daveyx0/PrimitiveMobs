package net.daveyx0.primitivemobs.client.models;

import net.daveyx0.primitivemobs.entity.passive.EntityFilchLizard;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelFilchLizard extends ModelBase
{
	  //fields
		public ModelRenderer Body;
	    public ModelRenderer Head;
	    public ModelRenderer Tail;
	    public ModelRenderer Leg1;
	    public ModelRenderer Leg2;
	    public ModelRenderer Leg3;
	    public ModelRenderer Leg4;
	    
	    public ModelRenderer FoldHead;
	    public ModelRenderer FoldFilch1;
	    public ModelRenderer FoldFilch2;
	    
	    public ModelRenderer Filch1;
	    public ModelRenderer Filch2;
	    public ModelRenderer Filch3;
	    public ModelRenderer Filch5;
	    public ModelRenderer Filch4;
	    public ModelRenderer Filch6;
	  
	  public ModelFilchLizard()
	  {
	    textureWidth = 64;
	    textureHeight = 32;
	      
	      Body = new ModelRenderer(this, 0, 6);
	      Body.addBox(-2F, -1.5F, -6F, 4, 3, 12);
	      Body.setTextureSize(64, 32);
	      Tail = new ModelRenderer(this, 32, 9);
	      Tail.addBox(-1F, -0.5F, 0F, 2, 2, 10);
	      Tail.setTextureSize(64, 32);
	      
	      Leg1 = new ModelRenderer(this, 16, 0);
	      Leg1.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
	      Leg1.setTextureSize(64, 32);
	      Leg2 = new ModelRenderer(this, 16, 3);
	      Leg2.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
	      Leg2.setTextureSize(64, 32);
	      
	      Leg3 = new ModelRenderer(this, 16, 0);
	      Leg3.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
	      Leg3.setTextureSize(64, 32);
	      Leg4 = new ModelRenderer(this, 16, 3);
	      Leg4.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
	      Leg4.setTextureSize(64, 32);
	      
	      FoldFilch1 = new ModelRenderer(this, 0, 22);
	      FoldFilch1.addBox(1F, -1.5F, 0F, 1, 3, 6);
	      FoldFilch1.setRotationPoint(0F, 21F, -6F);
	      FoldFilch1.setTextureSize(64, 32);
	      setRotation(FoldFilch1, 0F, 0.0349066F, 0F);
	      FoldFilch2 = new ModelRenderer(this, 14, 22);
	      FoldFilch2.addBox(-2F, -1.5F, 0F, 1, 3, 6);
	      FoldFilch2.setRotationPoint(0F, 21F, -6F);
	      FoldFilch2.setTextureSize(64, 32);
	      setRotation(FoldFilch2, 0F, -0.0349066F, 0F);
	      
	      Filch1 = new ModelRenderer(this, 0, 22);
	      Filch1.addBox(0F, -2.5F, 2.5F, 1, 3, 6);
	      Filch1.setRotationPoint(0F, 0F, 0F);
	      Filch1.setTextureSize(64, 32);	      
	      setRotation(Filch1, 0.3665191F, 1.570796F, -0.296706F);
	      
	      Filch2 = new ModelRenderer(this, 14, 22);
	      Filch2.addBox(-1F, -2.5F, 2.5F, 1, 3, 6);
	      Filch2.setRotationPoint(0F, 0F, 0F);
	      Filch2.setTextureSize(64, 32);
	      setRotation(Filch2, 0.3665191F, -1.570796F, 0.296706F);
	      
	      Filch3 = new ModelRenderer(this, 0, 22);
	      Filch3.addBox(-0.5F, -2.5F, 2F, 1, 3, 6);
	      Filch3.setRotationPoint(0F, 0F, 0F);
	      Filch3.setTextureSize(64, 32);
	      setRotation(Filch3, 0F, 1.570796F, -0.2617994F);
	      Filch4 = new ModelRenderer(this, 14, 22);
	      Filch4.addBox(-0.5F, -2.5F, 2F, 1, 3, 6);
	      Filch4.setRotationPoint(0F, 0F, 0F);
	      Filch4.setTextureSize(64, 32);
	      setRotation(Filch4, 0F, -1.570796F, 0.2617994F);
	      Filch5 = new ModelRenderer(this, 0, 22);
	      Filch5.addBox(-1F, -2.5F, 1.5F, 1, 3, 6);
	      Filch5.setRotationPoint(0F, 0F, 0F);
	      Filch5.setTextureSize(64, 32);
	      setRotation(Filch5, -0.3839724F, 1.570796F, -0.2617994F);
	      Filch6 = new ModelRenderer(this, 14, 22);
	      Filch6.addBox(0F, -2.5F, 1.5F, 1, 3, 6);
	      Filch6.setRotationPoint(0F, 0F, 0F);
	      Filch6.setTextureSize(64, 32);
	      setRotation(Filch6, -0.4014257F, -1.570796F, 0.2617994F);

	      FoldHead = new ModelRenderer(this, 0, 0);
	      FoldHead.addBox(-2F, -0.5F, -4F, 4, 2, 4);
	      FoldHead.setRotationPoint(0F, 21F, -6F);
	      FoldHead.setTextureSize(64, 32);
	      setRotation(FoldHead, 0F, 0F, 0F);
	      
	      Head = new ModelRenderer(this, 0, 0);
	      Head.addBox(-2F, -2.5F, -4F, 4, 2, 4);
	      Head.setRotationPoint(0F, 12F, -1F);
	      Head.setTextureSize(64, 32);
	      setRotation(Head, 0F, 0F, 0F);
	   
	      Head.addChild(Filch1);
	      Head.addChild(Filch2);
	      Head.addChild(Filch3);
	      Head.addChild(Filch4);
	      Head.addChild(Filch5);
	      Head.addChild(Filch6);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    
	    EntityFilchLizard lizard = (EntityFilchLizard)entity;
	    if(lizard.getHeldItemMainhand() != null)
	    {
	    	Head.render(f5);
	    }
	    else
	    {
	    	FoldHead.render(f5);
	    	FoldFilch1.render(f5);
	    	FoldFilch2.render(f5);
	    }
	    
    	Body.render(f5);
    	Tail.render(f5);
    	Leg1.render(f5);
    	Leg2.render(f5);
    	Leg3.render(f5);
    	Leg4.render(f5);
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
	    
	    EntityFilchLizard lizard = (EntityFilchLizard)entity;
	    
	    if(lizard.getHeldItemMainhand() != null)
	    {
		    Leg1.setRotationPoint(-2F, 13F, -1F);
		    setRotation(Leg1, 0F, 1.047198F, 0.6981317F);
		    Leg2.setRotationPoint(2F, 13F, -1F);
		    setRotation(Leg2, 0F, -1.047198F, -0.6981317F);
		    Leg3.setRotationPoint(2F, 20F, 5F);
		    setRotation(Leg3, 0F, 0F, 1.396263F);
		    Leg4.setRotationPoint(-2F, 20F, 5F);
		    setRotation(Leg4, 0F, 0F, -1.396263F);
		    Body.setRotationPoint(0F, 16F, 2F);
		    setRotation(Body, -0.9948377F, 0F, 0F);
		    Tail.setRotationPoint(0F, 20F, 6F);
		    setRotation(Tail, 0.6806784F, 0F, 0F);
		    
		    Head.rotateAngleX = f4 / (180F / (float)Math.PI);
	        Head.rotateAngleY = f3 / (180F / (float)Math.PI);
	    }
	    else
	    {
	    	Leg1.setRotationPoint(2F, 22F, -4F);
	    	setRotation(Leg1, 0F, 0F, 0.3839724F);
	    	Leg2.setRotationPoint(-2F, 22F, -4F);
	    	setRotation(Leg2, 0F, 0F, -0.3839724F);
	    	Leg3.setRotationPoint(2F, 22F, 5F);
	    	setRotation(Leg3, 0F, 0F, 0.3839724F);
	    	Leg4.setRotationPoint(-2F, 22F, 5F);
	        setRotation(Leg4, 0F, 0F, -0.3839724F);
	        Body.setRotationPoint(0F, 21F, 0F);
	        setRotation(Body, 0F, 0F, 0F);
	        Tail.setRotationPoint(0F, 21F, 6F);
	        setRotation(Tail, 0F, 0F, 0F);
	        
		    Leg1.rotateAngleY = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1;
		    Leg2.rotateAngleY = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1;
		       
		    FoldHead.rotateAngleX = f4 / (180F / (float)Math.PI);
	        FoldHead.rotateAngleY = f3 / (180F / (float)Math.PI);
	    }

        Leg3.rotateAngleY = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1;
        Leg4.rotateAngleY = MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.6F * f1;
        Tail.rotateAngleY = -MathHelper.cos(f * 0.6662F * 2.0F + (float)Math.PI) * 0.2F * f1;
	  }

}