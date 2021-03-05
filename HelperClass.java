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

    // Generate a random number within a range
    //
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }
}