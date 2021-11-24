package color_comparison;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GetPixels {
   public static void main(String args[])throws Exception {
      FileWriter writer = new FileWriter("C:\\Users\\SibuTV\\Downloads\\test.txt");
      ArrayList<int[]> existingColors = new ArrayList<int[]>();
      int[][] owned_color_array = {
				{255, 253, 249},
				{252, 249, 153}, 
				{253, 233,  73}, 
				{252, 205,  45}, 
				{245, 188,  19}, 
				{246, 239, 218}, 
				{253, 190, 142}, 
				{249, 135,  86}, 
				{244, 100,  64}, 
				{253,  72,  12}, 
				{200,  58,  36}, 
				{189,  17,  54}, 
				{252, 175, 185}, 
				{241,  90, 145}, 
				{206,  43,  99}, 
				{199, 133,  89}, 
				{115,  66,  30}, 
				{ 83,  51,  45}, 
				{157, 199,  45}, 
				{108, 158,  41}, 
				{ 70, 121,  36}, 
				{110, 211, 154}, 
				{206, 233, 234}, 
				{151, 216, 211}, 
				{ 94, 204, 236}, 
				{ 73, 168, 235}, 
				{ 31, 127, 160}, 
				{  0,  97, 176}, 
				{220, 156, 222}, 
				{212, 159, 225}, 
				{144,  47, 153},  
				{184, 185, 189}, 
				{118, 110, 114}, 
				{ 74,  71,  73}, 
				{  0,   0,   0}
		};
      /* ROSE
      int[][] owned_color_array = {
				{255, 255, 255},
				{188,  67, 101},
				{219, 169, 178},
				{255, 226, 226},
				// {248, 202, 200},
				{240, 206, 212},
				// {230, 138, 138},
				{238,  84, 110},
				{252, 251, 248},
				{223, 179, 187},
				{230, 204, 217},
				{235, 234, 231},
				{184, 119,  72},
				{246, 220, 152},
				{255, 131, 111},
				{161,  75,  81},
				// {248, 202, 200},
				// {240, 206, 212},
				{255, 223, 213},
				{183, 157, 167},
				// {240, 234, 218},
				{255, 238, 227},
				{232, 135, 155},
				// {230, 138, 138},
				//{ 72,  72,  72},
				{ 92,  24,  78},
				{247, 203, 191}
		};
		*/
      int[] color_usage = new int[owned_color_array.length];
      //Reading the image
      File file= new File("C:\\Users\\SibuTV\\Documents\\Ocean Master\\gift_rose_pixel_art.png");
      BufferedImage img = ImageIO.read(file);
      BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < img.getHeight(); y++) {
         for (int x = 0; x < img.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = img.getRGB(x,y);
            
            // Exit for transparent pixels lmao
            if( (pixel>>24) == 0x00 ) {
            	int rgb_hex = (255 << 16) | (255 << 8) | 255;
                output.setRGB(x, y, rgb_hex);
                continue;
            }
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            //Retrieving the R G B values
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            //Create new image
            int bestMatch = color_comparison.outputBestIndexMatch(owned_color_array, red, green, blue);
            color_usage[bestMatch]++;
            int rgb_hex = (owned_color_array[bestMatch][0] << 16) | (owned_color_array[bestMatch][1] << 8) | owned_color_array[bestMatch][2];
            // System.out.println(rgb_hex);
            System.out.println("(x, y): (" + x + ", " + y + ")");
            output.setRGB(x, y, rgb_hex);
            
            // ADDED: CHECK FOR UNIQUENESS
            boolean unique = true;
            for(int[] i : existingColors) {
            	if(i[0] == red && i[1] == green && i[2] == blue) {
            		unique = false;
            	}
            }
            /*
            if(unique) {
            	existingColors.add(new int[] {red, green, blue});
            	writer.append("{");
            	if(red >= 100) {
            		writer.append(red + ", ");
            	} else if(red >= 10) {
            		writer.append(" " + red + ", ");
            	} else if(red >= 0) {
            		writer.append("  " + red + ", ");
            	} else {
            		writer.append("E" + red);
            	}
                
            	if(green >= 100) {
            		writer.append(green + ", ");
            	} else if(green >= 10) {
            		writer.append(" " + green + ", ");
            	} else if(green >= 0) {
            		writer.append("  " + green + ", ");
            	} else {
            		writer.append("E" + green);
            	}
            	
            	if(blue >= 100) {
            		writer.append(blue + "");
            	} else if(blue >= 10) {
            		writer.append(" " + blue);
            	} else if(blue >= 0) {
            		writer.append("  " + blue);
            	} else {
            		writer.append("E" + blue);
            	}
                writer.append("},\n");
                writer.flush();
            }
            */
         }
      }
      ImageIO.write(output, "png", new File("C:\\Users\\SibuTV\\Documents\\Ocean Master\\rose-t.png"));
      
      writer.close();
      System.out.println("RGB values at each pixel are stored in the specified file");
      
      for(int i = 0; i < color_usage.length; i++) {
    	  System.out.println(i + ": " + color_usage[i] + "Xs.");
      }
   }
}