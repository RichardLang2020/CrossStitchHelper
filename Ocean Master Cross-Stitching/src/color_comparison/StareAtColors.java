package color_comparison;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class StareAtColors {

	public static void main(String[] args) throws Exception {
		int[][] true_color_array = {
				{255, 255, 255},
				{190,  81,  98},
				{224, 170, 179},
				{255, 227, 232},
				{244, 198, 206},
				{213, 132, 146},
				{238,  88, 112},
				{255, 249, 249},
				{236, 179, 189},
				{225, 215, 222},
				{236, 225, 230},
				{177, 120,  67},
				{246, 213, 159},
				{247, 133, 113},
				{149,  70,  83},
				{244, 200, 207},
				{245, 214, 211},
				{176, 159, 171},
				{246, 233, 224},
				{218, 144, 153},
				{214, 133, 147},
				{ 86,  57,  74},
				{238, 197, 182}
		};
		int[][] mod_color_array = {
				{255, 255, 255},
				{188,  67, 101},
				{219, 169, 178},
				{255, 226, 226},
				{248, 202, 200},
				{230, 138, 138},
				{238,  84, 110},
				{252, 251, 248},
				{223, 179, 187},
				{230, 204, 217},
				{235, 234, 231},
				{184, 119,  72},
				{246, 220, 152},
				{255, 131, 111},
				{161,  75,  81},
				{248, 202, 200},
				{240, 206, 212},
				{183, 157, 167},
				{240, 234, 218},
				{232, 135, 155},
				// {230, 138, 138},
				//{ 72,  72,  72},
				{ 92,  24,  78},
				{247, 203, 191}
		};
		
		BufferedImage output = new BufferedImage(200, true_color_array.length * 100, BufferedImage.TYPE_INT_RGB);
		int currentY = 0;
		for(int i = 0; i < true_color_array.length; i++) {
            int rgb_true_hex = (true_color_array[i][0] << 16) | (true_color_array[i][1] << 8) | true_color_array[i][2];
            int rgb_mod_hex = (mod_color_array[i][0] << 16) | (mod_color_array[i][1] << 8) | mod_color_array[i][2];
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
		
		ImageIO.write(output, "png", new File("C:\\Users\\SibuTV\\Documents\\Ocean Master\\rose_color_comparison.png"));
	}
}
