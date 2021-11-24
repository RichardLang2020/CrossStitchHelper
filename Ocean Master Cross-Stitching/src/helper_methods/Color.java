package helper_methods;

public class Color {
	// Class variables
	private int r, g, b;
	
	// Constructor
	public Color(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	// Basic getter methods
	public int getRed() {
		return this.r;
	}
	public int getGreen() {
		return this.g;
	}
	public int getBlue() {
		return this.b;
	}
	public int getRGB() {
		return (this.r << 16) | (this.g << 8) | this.b;
	}
	public String getString() {
		return "R: " + this.getRed() + " G: " + this.getGreen() + " B: " + this.getBlue();
	}
	
	// Helper methods
	public boolean compareColor(Color c) {
		// Outputs true if the color is the same, or false if the colors are different.
		if((c.getRed() == this.getRed()) && (c.getGreen() == this.getGreen()) 
				&& (c.getBlue() == this.getBlue())) {
			return true;
		} else {
			return false;
		}
	}
	public DMC_Color getClosestDMC() {
		// Outputs the DMC color closest to the current color value.
		int index = -1;
		double currentMinDiff = 9999;
		
		for(int i = 0; i < Full_DMC_Colors.DMC_Dictionary.length; i++) {
			DMC_Color dc = Full_DMC_Colors.DMC_Dictionary[i];
			double currentDiff = dc.findDifference(this);
			
			if(currentDiff < currentMinDiff) {
				index = i;
				currentMinDiff = currentDiff;
			}
		}
		
		return Full_DMC_Colors.DMC_Dictionary[index];
	}
	public DMC_Color getClosestOwnedColor() {
		// Outputs the DMC color closest to the current color value.
		int index = -1;
		double currentMinDiff = 9999;
		
		for(int i = 0; i < Full_DMC_Colors.Owned_Dictionary.length; i++) {
			DMC_Color dc = Full_DMC_Colors.Owned_Dictionary[i];
			double currentDiff = dc.findDifference(this);
			
			if(currentDiff < currentMinDiff) {
				index = i;
				currentMinDiff = currentDiff;
			}
		}
		
		return Full_DMC_Colors.Owned_Dictionary[index];
	}
}