import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Game {
    public static void main(String[] args) {
        play();
    }

    public static void play() {
        Map map = new Map(10);
        if (true) {
            map.draw();
            System.out.println("Current tile: "+(map.getCurrentCoordinates()[1]+1)+","+(map.getCurrentCoordinates()[0]+1));
            map.fight();
            
            map.move(new byte[] {1,1});
            map.draw();
            System.out.println("Current tile: "+(map.getCurrentCoordinates()[1]+1)+","+(map.getCurrentCoordinates()[0]+1));
            
            List<Character> playersTeam = new ArrayList<>();    // ALLY
            playersTeam.add(new Paladin(30, 10, 5, 3));
            playersTeam.add(new Paladin(20, 12, 6, 0));
            playersTeam.add(new Paladin(16, 10, 2, 1));
            map.setPlayersTeam(playersTeam);
            
            List<Character> enemies = new ArrayList<>();    // ENEMY
            enemies.add(new Paladin(12, 10, 5, 3));
            enemies.add(new Paladin(20, 12, 10, 0));
            enemies.add(new Paladin(30, 10, 2, 1));
            map.getCurrentTile().setEnemies(enemies);

            map.fight();
            System.out.println("last line in main function");
        }
        
    }

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