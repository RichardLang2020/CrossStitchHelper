package helper_methods;

public class DMC_Color {
	// Class variables
	private String floss_id;
	private String dmc_name;
	private int r, g, b;
	private String hex_value;
	private char character;
	
	// Constructor
	public DMC_Color(String floss_id, String color_name, int r, int g, int b, String hex) {
		this.floss_id = floss_id;
		this.dmc_name = color_name;
		this.r = r;
		this.g = g;
		this.b = b;
		this.hex_value = hex;
		this.character = ' ';
	}
	
	// Basic getter methods
	public String getFlossID() {
		return this.floss_id;
	}
	public String getColorName() {
		return this.dmc_name;
	}
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
	public String getHexValue() {
		return this.hex_value;
	}
	public char getChar() {
		return this.character;
	}
	
	// Setter method
	public void setChar(char c) {
		this.character = c;
	}
	
	// Helper methods
	public double findDifference(Color c) {
		// DETERMINE WHICH ALGORITHM TO USE
		boolean useAlgorithmOne = true;
		
		// Finds the difference between this DMC color and the given color
		if(useAlgorithmOne) {
			double r_line = (this.getRed() + c.getRed()) / 2.0;
			
			double r_w_diff = (2 + (r_line / 256.0)) * Math.pow(c.getRed() - this.getRed(), 2.0);
			double g_w_diff = 4.0 * Math.pow(c.getGreen() - this.getGreen(), 2.0);
			double b_w_diff = (2 + ((255.0 - r_line) / 256.0)) * Math.pow(c.getBlue() - this.getBlue(), 2.0);
			double color_diff = Math.sqrt(r_w_diff + g_w_diff + b_w_diff);
			return color_diff;
		} else {
			double[] dmc_lab = rgbToLab(this.getRed(), this.getGreen(), this.getBlue());
			double[] c_lab = rgbToLab(c.getRed(), c.getGreen(), c.getBlue());
			
			return Math.sqrt(Math.pow(dmc_lab[0] - c_lab[0], 2) + Math.pow(dmc_lab[1] - c_lab[1], 2) + Math.pow(dmc_lab[2] - c_lab[2], 2));
		}
	}
	public double findDifference(DMC_Color dc) {
		// Same as above, but takes in DMC_Color instead of Color
		return this.findDifference(new Color(dc.getRed(), dc.getGreen(), dc.getBlue()));
	}
	public boolean sameColor(Color c) {
		if(c.getRed() == this.getRed() && c.getGreen() == this.getGreen() && c.getBlue() == this.getBlue()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean sameColor(DMC_Color dc) {
		return this.sameColor(new Color(dc.getRed(), dc.getGreen(), dc.getBlue()));
	}
	
	// Pulled from Thanasis1101 on StackOverflow
	// https://stackoverflow.com/questions/4593469/java-how-to-convert-rgb-color-to-cie-lab
	public double[] rgbToLab(int R, int G, int B) {

	    double r, g, b, X, Y, Z, xr, yr, zr;

	    // D65/2°
	    double Xr = 95.047;  
	    double Yr = 100.0;
	    double Zr = 108.883;


	    // --------- RGB to XYZ ---------//

	    r = R/255.0;
	    g = G/255.0;
	    b = B/255.0;

	    if (r > 0.04045)
	        r = Math.pow((r+0.055)/1.055,2.4);
	    else
	        r = r/12.92;

	    if (g > 0.04045)
	        g = Math.pow((g+0.055)/1.055,2.4);
	    else
	        g = g/12.92;

	    if (b > 0.04045)
	        b = Math.pow((b+0.055)/1.055,2.4);
	    else
	        b = b/12.92 ;

	    r*=100;
	    g*=100;
	    b*=100;

	    X =  0.4124*r + 0.3576*g + 0.1805*b;
	    Y =  0.2126*r + 0.7152*g + 0.0722*b;
	    Z =  0.0193*r + 0.1192*g + 0.9505*b;


	    // --------- XYZ to Lab --------- //

	    xr = X/Xr;
	    yr = Y/Yr;
	    zr = Z/Zr;

	    if ( xr > 0.008856 )
	        xr =  (float) Math.pow(xr, 1/3.);
	    else
	        xr = (float) ((7.787 * xr) + 16 / 116.0);

	    if ( yr > 0.008856 )
	        yr =  (float) Math.pow(yr, 1/3.);
	    else
	        yr = (float) ((7.787 * yr) + 16 / 116.0);

	    if ( zr > 0.008856 )
	        zr =  (float) Math.pow(zr, 1/3.);
	    else
	        zr = (float) ((7.787 * zr) + 16 / 116.0);


	    double[] lab = new double[3];

	    lab[0] = (116*yr)-16;
	    lab[1] = 500*(xr-yr); 
	    lab[2] = 200*(yr-zr); 

	    return lab;
	}
}