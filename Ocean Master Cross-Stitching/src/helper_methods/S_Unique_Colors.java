package helper_methods;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class S_Unique_Colors {
	public static void main(String[] args) throws Exception {
		File file= new File("C:\\Users\\SibuTV\\Documents\\Ocean Master\\Stardew Valley\\Blackberry.png");
		BufferedImage image = ImageIO.read(file);
		ArrayList<Color> uniqueColors = new ArrayList<Color>();
		
		for (int y = 0; y < image.getHeight(); y++) {
	         for (int x = 0; x < image.getWidth(); x++) {
	            //Retrieving contents of a pixel
	            int pixel = image.getRGB(x,y);
	            // Exit if the pixel is transparent
	            if( (pixel>>24) == 0x00 ) {
	                continue;
	            }
	            
	            java.awt.Color tempColor = new java.awt.Color(pixel, true);
	            Color currentColor = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue());
	            
	            boolean unique = true;
	            for(Color c : uniqueColors) {
	            	if(c.compareColor(currentColor)) {
	            		unique = false;
	            		break;
	            	}
	            }
	            
	            if(unique) {
	            	uniqueColors.add(currentColor);
	            }
	         }
		}
		
		System.out.println("{");
		for(Color c : uniqueColors) {
			System.out.println("\tnew Color(" + c.getRed() + ", " + c.getGreen() + 
					", " + c.getBlue() + "),");
		}
		System.out.println("}");
	}
}

// OUTPUT INFORMATION BELOW
/* Rose output
 * {
	new Color(190, 81, 98),
	new Color(224, 170, 179),
	new Color(255, 227, 232),
	new Color(244, 198, 206),
	new Color(213, 132, 146),
	new Color(238, 88, 112),
	new Color(255, 249, 249),
	new Color(236, 179, 189),
	new Color(225, 215, 222),
	new Color(236, 225, 230),
	new Color(177, 120, 67),
	new Color(246, 213, 159),
	new Color(247, 133, 113),
	new Color(149, 70, 83),
	new Color(244, 200, 207),
	new Color(245, 214, 211),
	new Color(246, 233, 224),
	new Color(218, 144, 153),
	new Color(214, 133, 147),
	new Color(86, 57, 74),
	new Color(238, 197, 182),
}
 */