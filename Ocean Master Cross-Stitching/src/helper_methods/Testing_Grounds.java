package helper_methods;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Testing_Grounds {

	public static void main(String[] args) throws Exception {
		File text = new File("C:\\Users\\SibuTV\\Downloads\\dmc_raw.txt");
		Scanner scanner = new Scanner(text);
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parsed = line.split("\\s+");
			
			// Find out how long the name is
			int currentIndex = 2;
			String color_name = parsed[1];
			while(true) {
				String temp = parsed[currentIndex];
				
				try {
					Integer.parseInt(temp);
					break;
				} catch(NumberFormatException e) {
					color_name += " " + temp;
					currentIndex++;
					continue;
				}
			}
			
			ArrayList<String> owned_list = new ArrayList<String>(
				List.of("310", "725", "783", "931", "307", "932", "White", "924", "3750",
				"160", "169", "211", "413", "3799", "3853", "350", "3858", "3832",
				"209", "996", "995", "603", "B5200", "742", "798", "340", "371",
				"3839", "3838", "745", "3811", "3747", "3760"));
			if(!owned_list.contains(parsed[0])) {
				//continue;
			}
			
			// Parse the string to fit needs
			/*String output = "new DMC_Color(\"" + parsed[0] + "\", \"" + color_name + "\", "
					+ parsed[currentIndex] + ", " + parsed[currentIndex + 1] + ", " + 
					parsed[currentIndex + 2] + ", \"" + parsed[currentIndex + 3] + "\"),";*/
			// Change into JSON for Rees's Python coding
			String output = parsed[0] + ":{\"color\": \"" + color_name + "\", \"rgb\": ("
					+ parsed[currentIndex] + ", " + parsed[currentIndex + 1] + ", " + parsed[currentIndex + 2]
					+ "), \"hex\": \"" + parsed[currentIndex + 3] + "\"}";
			System.out.println(output);
		}
		
		scanner.close();
	}
}