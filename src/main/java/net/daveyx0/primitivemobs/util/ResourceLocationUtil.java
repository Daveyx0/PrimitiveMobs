package net.daveyx0.primitivemobs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;


public class ResourceLocationUtil
{

	private static SimpleReloadableResourceManager resourceManager;


	public static IResource getResource(String domain, String filename) {

		if(resourceManager == null)
		{
			resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
		}

		// Try to get the resource
		try 
		{
			return resourceManager.getResource((new ResourceLocation(domain, filename)));
		} 
		catch(java.lang.Exception e) 
		{
			return null;
		}
	}
	
	public static void getModFileDirectories()
	{
		//SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
		IResource resource = getResource("primitivemobs", "sounds.json");
		//ResourceLocation location = new ResourceLocation("primitivemobs", "sounds.json");
		InputStream stream = resource.getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	        try {
	            while( reader.ready() ) 
	            {
	                PrimitiveMobs.LOGGER.info(reader.readLine());
	            }
	        } catch( IOException e ) {
	            e.printStackTrace();
	        }
			finally {
			    try {
			        stream.close();
			    } catch (IOException ioex) {
			        //omitted.
			    }
			}
	}
}
