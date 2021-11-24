package helper_methods;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class S_Direct_Color_Comparison {
	public static void main(String[] args) throws Exception {
		// Rose analysis
		/*Color[] givenColors = new Color[] {
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
			};*/
		Color[] givenColors = new Color[] {
			new Color(3, 50, 23),
			new Color(194, 234, 32),
			new Color(55, 143, 43),
			new Color(52, 80, 39),
			new Color(21, 17, 54),
			new Color(54, 48, 135),
			new Color(55, 109, 153)
		};
		
		BufferedImage output = new BufferedImage(200, givenColors.length * 100, BufferedImage.TYPE_INT_RGB);
		int currentY = 0;
		for(int i = 0; i < givenColors.length; i++) {
            int rgb_true_hex = givenColors[i].getRGB();
            int rgb_mod_hex = givenColors[i].getClosestDMC().getRGB();
            for(int y = 0; y < 100; y++) {
            	for(int x = 0; x < 100; x++) {
                    output.setRGB(x, y + currentY, rgb_true_hex);
            	}
            	for(int x = 100; x < 200; x++) {
                    output.setRGB(x, y + currentY, rgb_mod_hex);
            	}
            }
            currentY += 100;
		}
		
		ImageIO.write(output, "png", new File("C:\\Users\\SibuTV\\Documents\\Ocean Master\\Stardew Valley\\Blackberry-cc-full.png"));
	}
}