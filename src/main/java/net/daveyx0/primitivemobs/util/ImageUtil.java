package net.daveyx0.primitivemobs.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

//Credit to stackoverflow

public class ImageUtil {


    public static int[] main(InputStream fileloc) throws Exception 
    {
        //File file = new File(fileloc);
        
        ImageInputStream is = ImageIO.createImageInputStream(fileloc);
        Iterator iter = ImageIO.getImageReaders(is);

        if (!iter.hasNext())
        {
            System.out.println("Cannot load the specified file ");
            //System.exit(1);
        }
        ImageReader imageReader = (ImageReader)iter.next();
        imageReader.setInput(is);

        BufferedImage imageOld = imageReader.read(0);
        BufferedImage image = getScaledImage(imageOld, 8, 8);
        
        //BufferedImage image = getScaledImage(image2,1,1);
        //image.getScaledInstance(1, 1, 1);

        int[] colour = getAverageColour(image);//getMostCommonColour(image);
        //System.out.println(colourHex);
		try {
	        is.close();
	    } catch (IOException ioex) {
	        //omitted.
	    }
		
        return colour;
		}


    public static int[] getMostCommonColour(BufferedImage image) 
    {
        int height = image.getHeight();
        int width = image.getWidth();

        Map map = new HashMap();
        for(int i=0; i < width ; i++)
        {
            for(int j=0; j < height ; j++)
            {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRGBArr(rgb);
                if(rgbArr[3] != 0)
                {
                        Integer counter = (Integer) map.get(rgb);   
                        if (counter == null)
                            counter = 0;
                        counter++;                                
                        map.put(rgb, counter);    
                }
            }
        }        
        List<?> list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() 
        {
              public int compare(Object o1, Object o2) 
              {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
              }
        });    
        Map.Entry me = (Map.Entry )list.get(list.size()-1);
        int[] rgb= getRGBArr((Integer)me.getKey());
        return rgb;    
    } 
    
    public static int[] getAverageColour(BufferedImage image) 
    {
        int height = image.getHeight();
        int width = image.getWidth();
        
        List<int[]> list = new ArrayList<int[]>();
        
        for(int i=0; i < width ; i++)
        {
            for(int j=0; j < height ; j++)
            {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRGBArr(rgb);
                if(rgbArr[3] != 0)
                {
                	list.add(rgbArr);
                }
            }
        }
        
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        
        for(int[] color : list)
        {
        	totalRed += color[0];
        	totalGreen += color[1];
        	totalBlue += color[2];
        }
        
        int avgRed = totalRed/ list.size();
        int avgGreen = totalGreen/ list.size();
        int avgBlue = totalBlue/ list.size();
        
        return new int[]{avgRed, avgGreen, avgBlue, 1};    
    } 

    public static int[] getRGBArr(int pixel)
    {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red,green,blue,alpha};

  }
    
    private static BufferedImage getScaledImage(BufferedImage src, int w, int h){
        int finalw = w;
        int finalh = h;
        double factor = 1.0d;
        if(src.getWidth() > src.getHeight()){
            factor = ((double)src.getHeight()/(double)src.getWidth());
            finalh = (int)(finalw * factor);                
        }else{
            factor = ((double)src.getWidth()/(double)src.getHeight());
            finalw = (int)(finalh * factor);
        }
        if(finalw <= 0)
        {
        	finalw = w;
        }
        if(finalh <= 0)
        {
        	finalh = h;
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, finalw, finalh, null);
        g2.dispose();
        return resizedImg;
    }
}