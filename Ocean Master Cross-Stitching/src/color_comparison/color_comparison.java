package color_comparison;

import java.io.FileWriter;

public class color_comparison {
	private static void appendToWriter(FileWriter writer, int red, int green, int blue) throws Exception {
		writer.append("{");
    	if(red >= 100) {
    		writer.append(red + ", ");
    	} else if(red >= 10) {
    		writer.append(" " + red + ", ");
    	} else if(red == 0) {
    		writer.append("  " + red + ", ");
    	} else {
    		writer.append("ERROR");
    	}
        
    	if(green >= 100) {
    		writer.append(green + ", ");
    	} else if(green >= 10) {
    		writer.append(" " + green + ", ");
    	} else if(green == 0) {
    		writer.append("  " + green + ", ");
    	} else {
    		writer.append("ERROR");
    	}
    	
    	if(blue >= 100) {
    		writer.append(blue + "");
    	} else if(blue >= 10) {
    		writer.append(" " + blue);
    	} else if(blue == 0) {
    		writer.append("  " + blue);
    	} else {
    		writer.append("ERROR");
    	}
        writer.append("}");
	}
	public static int outputBestIndexMatch(int[][] owned_color_array, int red, int green, int blue) {
		// COLOR MATCHING SECTION
		double min_color_diff = 999.9;
		int min_color_index = -1;
		int r_1 = red;
		int g_1 = green;
		int b_1 = blue;
		
		for(int i = 0; i < owned_color_array.length; i++) {
			int r_2 = owned_color_array[i][0];
			int g_2 = owned_color_array[i][1];
			int b_2 = owned_color_array[i][2];

			/*  
			 * ALGORITHM PULLED FROM WIKIPEDIA
			 * https://en.wikipedia.org/wiki/Color_difference
			 */
			int r_line = (r_1 + r_2) / 2;
			
			double r_w_diff = (2 + (r_line / 256.0)) * Math.pow(r_2 - r_1, 2.0);
			double g_w_diff = 4.0 * Math.pow(g_2 - g_1, 2.0);
			double b_w_diff = (2 + ((255.0 - r_line) / 256.0)) * Math.pow(b_2 - b_1, 2.0);
			double color_diff = Math.sqrt(r_w_diff + g_w_diff + b_w_diff);
			
			if(color_diff < min_color_diff) {
				min_color_diff = color_diff;
				// System.out.println("New color min: " + color_diff);
				min_color_index = i;
			}
		}
		
		return min_color_index;
	}
	public static void main(String[] args) throws Exception {
	    FileWriter writer = new FileWriter("C:\\Users\\SibuTV\\Downloads\\cc_output.txt");
	    writer.append("TRUE COLOR		ESTIMATED COLOR		ESTIMATED GRAY\n");
		writer.flush();
		int[][] owned_color_array = {
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
				{144,  47, 153}
		};
		int[][] owned_gray_array = {
				{255, 253, 249}, 
				{184, 185, 189}, 
				{118, 110, 114}, 
				{ 74,  71,  73}, 
				{  0,   0,   0}
		};
		
		/*
		 * PIPLUP COLORS
		 */
		int[][] true_colors = {
				{255, 255, 255},
				{  0,   0,   0},
				{ 78,  78, 111},
				{159, 155, 168},
				{192, 192, 208},
				{247, 226, 229},
				{206, 187, 192},
				{243, 243, 251},
				{125, 124, 200},
				{220, 224, 242},
				{204, 209, 234},
				{141, 129, 147},
				{109, 108, 151},
				{178, 187, 236},
				{215, 220, 248}
		};
		
		for(int trueColorCounter = 0; trueColorCounter < true_colors.length; trueColorCounter++) {
			int[] comparing_color = true_colors[trueColorCounter];
			int r_1 = comparing_color[0];
			int g_1 = comparing_color[1];
			int b_1 = comparing_color[2];
			appendToWriter(writer, r_1, g_1, b_1);
			writer.append("		");
			writer.flush();
			
			// COLOR MATCHING SECTION
			double min_color_diff = 999.9;
			int min_color_index = -1;
			
			for(int i = 0; i < owned_color_array.length; i++) {
				int r_2 = owned_color_array[i][0];
				int g_2 = owned_color_array[i][1];
				int b_2 = owned_color_array[i][2];
	
				/*  
				 * ALGORITHM PULLED FROM WIKIPEDIA
				 * https://en.wikipedia.org/wiki/Color_difference
				 */
				int r_line = (r_1 + r_2) / 2;
				
				double r_w_diff = (2 + (r_line / 256.0)) * Math.pow(r_2 - r_1, 2.0);
				double g_w_diff = 4.0 * Math.pow(g_2 - g_1, 2.0);
				double b_w_diff = (2 + ((255.0 - r_line) / 256.0)) * Math.pow(b_2 - b_1, 2.0);
				double color_diff = Math.sqrt(r_w_diff + g_w_diff + b_w_diff);
				
				if(color_diff < min_color_diff) {
					min_color_diff = color_diff;
					System.out.println("New color min: " + color_diff);
					min_color_index = i;
				}
			}
			
			System.out.println("The color that matched best with R:" 
								+ comparing_color[0] + ", G:" 
								+ comparing_color[1] + ", B:" 
								+ comparing_color[2] + " is R:"
								+ owned_color_array[min_color_index][0] + ", G:" 
								+ owned_color_array[min_color_index][1] + ", B:" 
								+ owned_color_array[min_color_index][2] + " at index " 
								+ min_color_index + ".");
			appendToWriter(writer, owned_color_array[min_color_index][0], owned_color_array[min_color_index][1], owned_color_array[min_color_index][2]);
			writer.append(" @" + min_color_index + " " + (int)min_color_diff + "	");
			writer.flush();
			
			// GRAY MATCHING SECTION
			double min_gray_diff = 999.9;
			int min_gray_index = -1;
			
			for(int i = 0; i < owned_gray_array.length; i++) {
				int r_2 = owned_gray_array[i][0];
				int g_2 = owned_gray_array[i][1];
				int b_2 = owned_gray_array[i][2];
	
				/*  
				 * ALGORITHM PULLED FROM WIKIPEDIA
				 * https://en.wikipedia.org/wiki/Color_difference
				 */
				int r_line = (r_1 + r_2) / 2;
				
				double r_w_diff = (2 + (r_line / 256.0)) * Math.pow(r_2 - r_1, 2.0);
				double g_w_diff = 4.0 * Math.pow(g_2 - g_1, 2.0);
				double b_w_diff = (2 + ((255.0 - r_line) / 256.0)) * Math.pow(b_2 - b_1, 2.0);
				double color_diff = Math.sqrt(r_w_diff + g_w_diff + b_w_diff);
				
				if(color_diff < min_gray_diff) {
					min_gray_diff = color_diff;
					System.out.println("New gray min: " + min_gray_diff);
					min_gray_index = i;
				}
			}
			
			System.out.println("The gray that matched best with R:" 
								+ comparing_color[0] + ", G:" 
								+ comparing_color[1] + ", B:" 
								+ comparing_color[2] + " is R:"
								+ owned_gray_array[min_gray_index][0] + ", G:" 
								+ owned_gray_array[min_gray_index][1] + ", B:" 
								+ owned_gray_array[min_gray_index][2] + " at index " 
								+ min_gray_index + ".");
			appendToWriter(writer, owned_gray_array[min_gray_index][0], owned_gray_array[min_gray_index][1], owned_gray_array[min_gray_index][2]);
			writer.append(" @" + min_gray_index + " " + (int)min_gray_diff + "\n");
			writer.flush();
		}
		writer.close();
	}
}