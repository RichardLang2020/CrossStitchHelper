package helper_methods;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class S_Pixel_Replacement {
	// TODO: ADD MANUAL OVERRIDE FOR CERTAIN COLORS.
	// This change might go under the 'Color' class, since the Color.getClosestDMC() is the algorithm function.
	// TODO: Add a switch to use full DMC colors, or only the subset we own
	// This should go under the 'Color' class as well.
	public static void main(String[] args) throws Exception {
		File file= new File("C:\\Users\\SibuTV\\Documents\\Ocean Master\\Stardew Valley\\Blackberry.png");
		BufferedImage image = ImageIO.read(file);
		
		BufferedImage output = new BufferedImage(image.getWidth() * 2 + 10, image.getHeight(), BufferedImage.TYPE_INT_RGB);
	    
		// Space the initial and result image by 10 black pixels
		for(int y = 0; y < output.getHeight(); y++) {
			for(int x = image.getWidth(); x < image.getWidth() + 10; x++) {
				int rgb_hex = (0 << 16) | (0 << 8) | 0;
                output.setRGB(x, y, rgb_hex);
			}
		}
		
		for(int y = 0; y < image.getHeight(); y++) {
	         for(int x = 0; x < image.getWidth(); x++) {
	            //Retrieving contents of a pixel
	            int pixel = image.getRGB(x,y);
	            // Exit if the pixel is transparent
	            if((pixel>>24) == 0x00 ) {
	            	int rgb_hex = (255 << 16) | (255 << 8) | 255;
	                output.setRGB(x, y, rgb_hex);
	                output.setRGB(x + image.getWidth() + 10, y, rgb_hex);
	                continue;
	            }
	            
	            java.awt.Color tempColor = new java.awt.Color(pixel, true);
	            Color currentColor = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue());

	            int rgb_true_hex = currentColor.getRGB();
	            output.setRGB(x, y, rgb_true_hex);
	            int rgb_mod_hex = currentColor.getClosestDMC().getRGB();
	            output.setRGB(x + image.getWidth() + 10, y, rgb_mod_hex);
	         }
		}
		
		ImageIO.write(output, "png", new File("C:\\Users\\SibuTV\\Documents\\Ocean Master\\Stardew Valley\\Blackberry-dmc-full.png"));
	}
}