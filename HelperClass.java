import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HelperClass {
    // Get int from user that is in the interval [minValue, maxValue]
    // after printing a message
    //
    public static int inputInt(String message, int minValue, int maxValue) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        int userNumber = 0;

        // Make sure it is an int
        try {
            userNumber = Integer.parseInt( scanner.next() );
        } catch (Exception e) {
            System.out.println("Invalid. Please enter a number.");
            return inputInt(message, minValue, maxValue);
        }

        if ( (minValue <= userNumber) && (userNumber <= maxValue) ){
            return userNumber;
        }else{
            System.out.println("The number should be between "+ minValue +" and "+ maxValue +".");
            return inputInt(message, minValue, maxValue);
        }
    }

    public static String inputString(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    // Generate a random number within a range
    //
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }

    // Return list of all files with a given extention (without ".")
    //
    public static String[] findFilesWithExt(String ext){
        ArrayList<String> fileNames = new ArrayList<>();

        try {
            File folder = Paths.get(".").toFile();
            File filesList[] = folder.listFiles();
            
            for(File file : filesList) {
                if(file.isFile() && file.getName().contains("."+ext)){
                    fileNames.add(file.getName());
                }
            }
        } catch (Exception e){
            System.out.println("Exception "+ e + " occured when trying to find files with particular extention.");
            System.exit(0);
        }
        
        return fileNames.toArray(new String[0]);
    }
}