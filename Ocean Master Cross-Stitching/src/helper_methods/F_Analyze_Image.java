package helper_methods;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class F_Analyze_Image {
	// Algorithm choices at DMC_Color.findDifference()
	// TODO: Add manual override functionality for specific colors
	
	// Files names
	static String originalFilePrefix = "C:\\Users\\SibuTV\\Documents\\Ocean Master\\Cross Stitch\\";
	static String originalFileName = "Hogwarts Pixel Emblem.png";
	// For matching against all DMC threads, or just threads we own.
	static boolean useFullDMC = true;
	// For running on a full directory
	static boolean useFullDirectory = false;
	// This is so that we can see the .PNGs that are created and output, without having to enlargen them.
	static boolean scaleUpFinal = false;
	static int scaleUpMultiplier = 15;
	// These are only used for when images are uploaded improperly, with pixel 'chunks'.
	static boolean scaleDown = false;
	static int scaleDownMultiplier = 3;
	
	static String colorComparisonFileName, pixelReplacementFileName, textFileName;

	public static void main(String[] args) throws Exception {
		File file = new File(originalFilePrefix + "java_code\\");
		file.mkdir();
		
		if(useFullDirectory) {
		    for(final File fileEntry : new File(originalFilePrefix).listFiles()) {
		        if(!fileEntry.isDirectory()) {
		        	originalFileName = fileEntry.getName();
		    		colorComparisonFileName = originalFilePrefix + "java_code\\" + originalFileName.substring(0, originalFileName.length() - 4) + "_t_CC" + originalFileName.substring(originalFileName.length() - 4, originalFileName.length());
		    		pixelReplacementFileName = originalFilePrefix + "java_code\\" + originalFileName.substring(0, originalFileName.length() - 4) + "_t_PR" + originalFileName.substring(originalFileName.length() - 4, originalFileName.length());
		    		textFileName = originalFilePrefix + "java_code\\" + originalFileName.substring(0, originalFileName.length() - 4) + "_t_text.txt";
		    		
		    		ArrayList<Color> unique_colors = unique_colors();
		    		direct_color_comparison(unique_colors);
		    		pixel_replacement();
		        }
		    }
		} else {
			colorComparisonFileName = originalFilePrefix + "java_code\\" + originalFileName.substring(0, originalFileName.length() - 4) + "_t_CC" + originalFileName.substring(originalFileName.length() - 4, originalFileName.length());
			pixelReplacementFileName = originalFilePrefix + "java_code\\" + originalFileName.substring(0, originalFileName.length() - 4) + "_t_PR" + originalFileName.substring(originalFileName.length() - 4, originalFileName.length());
			textFileName = originalFilePrefix + "java_code\\" + originalFileName.substring(0, originalFileName.length() - 4) + "_t_text.txt";
			
			ArrayList<Color> unique_colors = unique_colors();
			direct_color_comparison(unique_colors);
			pixel_replacement();
		}
	}

	// S_Classes, but now they take in variable names.
	public static ArrayList<Color> unique_colors() throws Exception {
		File file= new File(originalFilePrefix + originalFileName);
		System.out.println(file.getAbsolutePath());
		BufferedImage image = ImageIO.read(file);
		ArrayList<Color> uniqueColors = new ArrayList<Color>();
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				// Retrieving contents of a pixel
				int pixel = image.getRGB(x, y);
				// Exit if the pixel is transparent
				if((pixel >> 24) == 0x00) {
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
		return uniqueColors;
	}
	public static void direct_color_comparison(ArrayList<Color> givenColors) throws Exception {
		BufferedImage output = new BufferedImage(200, givenColors.size() * 100, BufferedImage.TYPE_INT_RGB);
		int currentY = 0;
		for(int i = 0; i < givenColors.size(); i++) {
            int rgb_true_hex = givenColors.get(i).getRGB();
            int rgb_mod_hex;
            if(useFullDMC) {
            	rgb_mod_hex = givenColors.get(i).getClosestDMC().getRGB();
            } else {
            	rgb_mod_hex = givenColors.get(i).getClosestOwnedColor().getRGB();
            }
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
		
		ImageIO.write(output, "png", new File(colorComparisonFileName));
	}
	public static void pixel_replacement() throws Exception {
		System.out.println(textFileName);
		FileWriter writer = new FileWriter(textFileName);
		File file = new File(originalFilePrefix + originalFileName);
		BufferedImage image = ImageIO.read(file);
		BufferedImage output;
		int currentCharIndex = 0;
		ArrayList<DMC_Color> endTextOutput = new ArrayList<DMC_Color>();
		
		if(scaleUpFinal) {
			output = new BufferedImage((image.getWidth() * scaleUpMultiplier * 2) + 10, image.getHeight() * scaleUpMultiplier, BufferedImage.TYPE_INT_RGB);
		} else {
			output = new BufferedImage((image.getWidth() * 2) + 10, image.getHeight(), BufferedImage.TYPE_INT_RGB);
		}
	    
		// Space the initial and result image by 10 black pixels
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = image.getWidth(); x < image.getWidth() + 10; x++) {
				int rgb_hex = (0 << 16) | (0 << 8) | 0;
                output.setRGB(x, y, rgb_hex);
			}
		}
		
		int y_scaleDown = 0;
		for(int y = 0; y < image.getHeight(); y++) {
			int x_scaleDown = 0;
	        for(int x = 0; x < image.getWidth(); x++) {
	        	int rgb_true_hex, rgb_mod_hex;
	        	DMC_Color chosen_dmc = null;
	            // Retrieving contents of a pixel
	            int pixel = image.getRGB(x,y);
	            // Exit if the pixel is transparent
	            if((pixel>>24) == 0x00 ) {
	            	rgb_true_hex = (255 << 16) | (255 << 8) | 255;
	            	rgb_mod_hex = (255 << 16) | (255 << 8) | 255;
	            } else {
		            java.awt.Color tempColor = new java.awt.Color(pixel, true);
		            Color currentColor = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue());

		            rgb_true_hex = currentColor.getRGB();
		            if(useFullDMC) {
		            	chosen_dmc = currentColor.getClosestDMC();
		            	
		            	// HARD MANUAL OVERRIDE FOR Gray Outer on Pokeballs
		            	if(chosen_dmc.sameColor(new Color(49, 57, 25))) {
		            		chosen_dmc = Full_DMC_Colors.DMC_Dictionary[384];
		            	} else if(chosen_dmc.sameColor(new Color(72, 72, 72))) {
		            		chosen_dmc = Full_DMC_Colors.DMC_Dictionary[69];
		            	} else if(chosen_dmc.sameColor(new Color(152, 68, 54))) {
		            		chosen_dmc = Full_DMC_Colors.DMC_Dictionary[442];
		            	} else if(chosen_dmc.sameColor(new Color(61, 149, 165))) {
		            		chosen_dmc = Full_DMC_Colors.DMC_Dictionary[366];
		            	}
		            	
		            	rgb_mod_hex = chosen_dmc.getRGB();
		            } else {
		            	chosen_dmc = currentColor.getClosestOwnedColor();
		            	rgb_mod_hex = chosen_dmc.getRGB();
		            }
	            }

            	if(scaleUpFinal) {
            		for(int y1 = 0; y1 < scaleUpMultiplier; y1++) {
            			for(int x1 = 0; x1 < scaleUpMultiplier; x1++) {
            				output.setRGB((x * scaleUpMultiplier) + x1, (y * scaleUpMultiplier) + y1, rgb_true_hex);
    		                output.setRGB((x * scaleUpMultiplier) + x1 + (image.getWidth() * scaleUpMultiplier) + 10, (y * scaleUpMultiplier) + y1, rgb_mod_hex);
            			}
            		}
            	} else {
	                output.setRGB(x, y, rgb_true_hex);
	                output.setRGB(x + image.getWidth() + 10, y, rgb_mod_hex);
            	}
            	
            	// Extra output code for the text file we're testing
            	if((!scaleDown) || (scaleDown && (x_scaleDown % scaleDownMultiplier == 0) && (y_scaleDown % scaleDownMultiplier == 0))) {
                	if(chosen_dmc == null) {
                		writer.append(" \t");
                	} else {
                    	boolean new_dmc_color = true;
                    	for(DMC_Color dc : endTextOutput) {
                    		if(dc.sameColor(chosen_dmc)) {
                    			new_dmc_color = false;
                    			writer.append(dc.getChar() + "\t");
                    			break;
                    		} else {
                    			continue;
                    		}
                    	}
                    	
                    	if(new_dmc_color) {
                    		endTextOutput.add(chosen_dmc);
                    		chosen_dmc.setChar(Full_DMC_Colors.CharacterLibrary[currentCharIndex]);
                    		writer.append(Full_DMC_Colors.CharacterLibrary[currentCharIndex] + "\t");
                    		currentCharIndex++;
                    	}
                	}
            	}
        		x_scaleDown++;
	        }
	        if((!scaleDown) || (scaleDown && (y_scaleDown % scaleDownMultiplier == 0))) {
	        	writer.append("\n");
	        }
    		y_scaleDown++;
		}
		
		ImageIO.write(output, "png", new File(pixelReplacementFileName));
		for(DMC_Color dc : endTextOutput) {
			writer.append("Char\t\t\t" + dc.getChar() + "\t\tCode\t\t\t" + dc.getFlossID() + ";\t\t\t#" + dc.getHexValue() + "\n");
		}
		writer.close();
	}
}