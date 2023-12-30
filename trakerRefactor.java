package PR;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class trakerRefactor {

	public static void main(String args[]) {
		
	}
	
    // Loads file or creates if it's a brand new file
 // Loads file or creates if it's a brand new file
    public static void load_file(String filename, String date, double proteinIntake) {
        try {
            File myfile = new File(filename);
            if (myfile.createNewFile()) {
                System.out.println("File created: " + myfile.getName());
            }

            // Use FileWriter in append mode (second argument is true)
            FileWriter file = new FileWriter(filename, true);

            if (!check_Date(filename, date)) {
                System.out.println("Updating date");
                // If dates are not the same, add a new date
                file.append(date + "\n");
            }
            // Add protein after date has been checked
            file.append(Double.toString(proteinIntake) + "\n");
            file.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
 // Check if the given date exists, otherwise load_file will create a new date
    public static boolean check_Date(String filename, String targetDate) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filename));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            for (String line : lines) {
                LocalDate entryDate;
                try {
                    entryDate = LocalDate.parse(line, formatter);
                } catch (DateTimeParseException e) {
                    // Skip lines that do not have valid dates in the expected format
                    continue;
                }

                if (entryDate.format(formatter).equals(targetDate)) {
                    return true; // Return true if a matching date is found
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        return false; // Return false if the date is not found in the file or there's an error
    }
}
