package net.daveyx0.primitivemobs.util;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Util class that allows you to get rgb colors from blockstates and itemstacks
 *
 * @author Daveyx0
 */
@SideOnly(Side.CLIENT)
public class ColorUtil 
{
	//Gets the RGB color of a blockstate
	public static int[] getBlockStateColor(IBlockState state, @Nullable BlockPos pos, @Nullable World worldObj)
	{
		return getBlockStateColor(state, pos, worldObj, EnumFacing.UP);
	}
	
	//Gets the RGB color of a blockstate
	public static int[] getBlockStateColor(IBlockState state, @Nullable BlockPos pos, @Nullable World worldObj, EnumFacing face)
	{
			int[] color = new int[3];
			int colorMultiplier = -1;
			
			colorMultiplier = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, worldObj, pos, 0);
					
			if(state.getBlock() != Blocks.AIR)
			{			
				Color colour = new Color(colorMultiplier, true);
					
			if(colorMultiplier != -1  && !(colour.getRed() == colour.getGreen() && colour.getGreen() == colour.getBlue() && colour.getBlue() == colour.getRed()) 
					&& state.getBlock() != Blocks.DOUBLE_PLANT)
			{
				color[0] = colour.getRed();
				color[1] = colour.getGreen();
				color[2] = colour.getBlue();		
			}
			else
			{
				TextureAtlasSprite sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
				
				if(sprite != null)
				{
					String textureName = sprite.getIconName();
				
					String modelName = textureName.replaceAll(":", ":models/");
					ModelResourceLocation model = new ModelResourceLocation(modelName);
					String topTextureName = "";
				
					if(model != null)
					{
						IBakedModel bakedModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
					
					if(bakedModel != null)
					{
						List<BakedQuad> quads = bakedModel.getQuads(state, face, 1);
						 
						if(quads!= null && !quads.isEmpty() && quads.size() > 0)
						{
							topTextureName = quads.get(0).getSprite().getIconName();
						}
					}

				}

				if(!topTextureName.equals("") && !topTextureName.equals("missingno"))
				{
					color = getTextureColor(topTextureName, "blocks");
				}
				else if(!textureName.equals("") && !textureName.equals("missingno"))
				{
					color = getTextureColor(textureName, "blocks");
				}
				}
			}
			
			if(color == null && colorMultiplier == -1)
			{
				MapColor mapcolor = state.getMapColor(worldObj, pos);
				if(mapcolor != null)
				{
					colorMultiplier = mapcolor.colorValue;
				}
			}
		}

			return color;
	}

	//Gets the RGB color of the item in an Itemstack. Use only on actual Items, when using Blocks try using the other method.
	public static int[] getItemStackColor(ItemStack stack, World worldObj)
	{
			int[] color = new int[3];
			int colorMultiplier = -1;

			colorMultiplier = Minecraft.getMinecraft().getItemColors().getColorFromItemstack(stack, 0);

			if(colorMultiplier != -1)//  && !(colour.getRed() == colour.getGreen() && colour.getGreen() == colour.getBlue() && colour.getBlue() == colour.getRed()))
			{
				Color colour = new Color(colorMultiplier, true);
				color[0] = colour.getRed();
				color[1] = colour.getGreen();
				color[2] = colour.getBlue();		
			}
			else
			{
	        	String textureName = ""; //Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, worldObj, null).getQuads(null, null, 1).get(0).getSprite().getIconName();
	        	List<BakedQuad> quads = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, worldObj, null).getQuads(null, null, 1);

				if(quads!= null && !quads.isEmpty() && quads.size() > 0)
				{
					textureName = quads.get(0).getSprite().getIconName();
				}

				if(!textureName.equals("") && !textureName.equals("missingno"))
				{
					color = getTextureColor(textureName, "items");
				}
			}
		
			return color;
	}
	
	//Used the get the color of any texture
	public static int[] getTextureColor(String name, String type)
	{
		SimpleReloadableResourceManager resourceManager;
		resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
		Set set = resourceManager.getResourceDomains(); 
		Object[] domains = set.toArray();
		IResource resource = null;
		int[] rgb = null;
		
		if(name.contains(":"))
		{
			String[] divided = name.split(":");
			if(divided != null && divided.length > 0)
			{
				resource = ResourceLocationUtil.getResource(divided[0].toString(), "textures/" + divided[1] + ".png");
			}
		}
		else
		{
			for(int i = 0; i < domains.length; i ++)
			{
				resource = ResourceLocationUtil.getResource(domains[i].toString(), "textures/" + type + "/" + name + ".png");
				/*
				if(resource == null)
				{
					String[] divided = name.split(":");
					if(divided != null && divided.length > 0)
					{
						resource = ResourceChecker.getResource(domains[i].toString(), "textures/blocks/" + divided[1] + ".png");
					}
				}*/
				
				if(resource != null)
				{
					break;
				}
			}
		}
		
		if(resource != null)
		{
			InputStream stream = resource.getInputStream();
			
		
			try 
			{
				rgb = ImageUtil.main(stream);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
				{
				try 
				{
					stream.close();
					
				} 
				catch (IOException ioex) 
				{
					
				}
		}
		
	}
		return rgb;
	}
	
	public static int[] setBrightness(int[] rgb, float brightness)
    {
        float newRed = rgb[0]/255f;
        float newGreen = rgb[1]/255f;
        float newBlue = rgb[2]/255f;

        if(brightness < 0)
        {
            float newBrightness = brightness * -1;
            newBrightness = 1 - (newBrightness / 100);
            newRed = newRed * newBrightness;
            newGreen = newGreen * newBrightness;
            newBlue = newBlue * newBrightness;
        }
        else if (brightness > 0)
        {
            float newBrightness = brightness;
            newBrightness = newBrightness / 100;
            float tempRed = (1 - newRed) * newBrightness; ;
            float  tempGreen = (1 - newGreen) * newBrightness;
            float tempBlue = (1 - newBlue) * newBrightness;
            newRed = newRed + tempRed;
            newGreen = newGreen + tempGreen;
            newBlue = newBlue + tempBlue;
        }

        if(newRed > 1)
        {
            newRed = 1;
        }
        if (newGreen > 1)
        {
            newGreen = 1;
        }
        if (newBlue > 1)
        {
            newBlue = 1;
        }
        
        int[] newColor = new int[3];
        newColor[0] = Math.round(newRed * 255f);
        newColor[1] = Math.round(newGreen * 255f);
        newColor[2] = Math.round(newBlue * 255f);

        return newColor;
    }
	
    public static int getBlockColor(Entity entity)
	{
		int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.getEntityBoundingBox().minY);
        int k = MathHelper.floor(entity.posZ);
        
		if(entity.getEntityWorld().getBlockState(new BlockPos(i, j, k)).getBlock() == Blocks.AIR)
		{
			j = MathHelper.floor(entity.getEntityBoundingBox().minY - 0.1);
		}
		
		BlockPos pos = new BlockPos(i, j, k);
		IBlockState state = entity.getEntityWorld().getBlockState(pos);
		
		int colorMultiplier = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, entity.getEntityWorld(), pos, 0);
		
		if(state.getBlock() != Blocks.AIR)
		{
			
			int[] newColor = ColorUtil.getBlockStateColor(state, pos, entity.getEntityWorld());
			if(newColor != null)
			{
				Color color = new Color(newColor[0], newColor[1], newColor[2]);
				return color.hashCode();
			}
		}
		return -1;
	}

}
