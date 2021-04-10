import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

final public class Game implements Serializable{
    private List<Character> playersTeam = new ArrayList<>();
    private List<Item> inventory = new ArrayList<>();
    private Map map;
    private static final long serialVersionUID = 1L;
  
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Unrecognised command (use -h for help)");
            System.exit(0);
        }

        String inputString = args[0];
        if (inputString.equals("-g")){
            System.out.println("Starting graphical user interface...");
            GUI gui = new GUI(10);
        }else if (inputString.equals("-l")){
            CLI cli = new CLI(10);
            cli.run();
        }else if (inputString.equals("-s")){
            String savedGame = CLI.chooseLocalCopy();
            int userInput = HelperClass.inputInt("Enter [1] to restore a save or [2] to delete one (0 ro cancel): ", 0, 2);

            if(userInput == 0){
                System.out.println("Canceling...");
                System.exit(0);
            } else if (userInput == 1){
                chooseInterface(Game.restoreLocalCopy(savedGame));
            } else if (userInput == 2){
                if (removeLocalCopy(savedGame))
                    System.out.println("Deleted successefully");
                else
                    System.out.println("Error occured while trying to delete "+savedGame);
            }
            
        }else if (inputString.equals("-h")){
            System.out.println("-g\tuse graphical user interface");
            System.out.println("-l\tuse command line based interface");
            System.out.println("-s\trestore or delete a local save of the game");
        }else{
            System.out.println("Unrecognised command (use -h for help)");
        }
    }


    public Game(int sizeOfTheMap) {
        this.map = new Map(sizeOfTheMap);

        List<Character> playersTeam = new ArrayList<>();    // ALLY
        playersTeam.add(new Paladin(90, 0.25));
        playersTeam.add(new Thief(60, 0.15));
        playersTeam.add(new Preacher(60));
        setPlayersTeam(playersTeam);

        createInventory();
    }

    public static void chooseInterface(Game gameToPlay){
        String inputString = HelperClass.inputString("Enter -g if you what to use GUI or -l for CLI: ");
        if (inputString.equals("-g")){
            System.out.println("Starting graphical user interface...");
            GUI gui = new GUI(gameToPlay);
        }else if (inputString.equals("-l")){
            CLI cli = new CLI(gameToPlay);
            cli.run();
        }else{
            chooseInterface(gameToPlay);
        }
    }
    
    public void createInventory() {
        this.inventory.add(     PermanentItem.getRedPotion(8, 4)        );
        this.inventory.add(     PermanentItem.getNastyPoison(5, 2)      );
        this.inventory.add(     TemporaryItem.getFishingNet(5, 3, 1)    );
        this.inventory.add(     TemporaryItem.getHydroAcid(10, 3, 3)    );
    }



    public void updateInventory() { // remove any items that have finished
        for (int i = 0; i < inventory.size(); i++) {            
            if(inventory.get(i).getNumberOf() <= 0)
                inventory.remove(i);
        }
    }

    // Same an instane of the game using Serialisation
    //
    public void makeLocalCopy(String nameOfSave){
        try {
            FileOutputStream fos = new FileOutputStream(nameOfSave+".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception e){
            System.out.println("Exception "+ e + " occured when trying to make a local copy of the game.");
            System.exit(0);
        }
    }

    // Return list of local copies
    //
    public static String listLocalCopies(){
        if (!areLocalCopiesPresent()){
            return "There no local saves.";
        }else{
            String[] localCopies = HelperClass.findFilesWithExt("ser");
            String output = "";
            output += ("The save/s stored on your machine is/are: ");
            for (int i = 0; i < localCopies.length; i++) {
                output += ("["+(i+1)+"] "+localCopies[i]);
                if(i != localCopies.length-1){
                    output += (", ");
                }else{
                    output += ("\n");
                }
            }
            return output;
        }
    }

    // Check if there are any local copies
    //
    public static boolean areLocalCopiesPresent(){
        String[] localCopies = HelperClass.findFilesWithExt("ser");
        if (localCopies.length == 0){
            return false;
        } else {
            return true;
        }
    }

    // Remove a local save
    //
    public static boolean removeLocalCopy(String nameOfSave){
        File file = new File(nameOfSave);
        return file.delete();
    }

    // Restore game from a saved file
    // 
    public static Game restoreLocalCopy(String nameOfSave){
        Game gameFromFile = null;
        try {
            FileInputStream fis = new FileInputStream(nameOfSave);
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameFromFile = (Game) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("Exception "+ e + " occured when trying to restore a local copy of the game.");
            System.exit(0);
        }

        return gameFromFile;
    }

    // Remove any dead characters in team and enemy arrays
    //
    public void removeDead() {
        // Remove dead in team
        for (int i = 0; i < this.getPlayersTeam().size(); i++) {
            Character currentCharacter = this.getPlayersTeam().get(i);

            if (currentCharacter.getHealth() <= 0)
                this.getPlayersTeam().remove(currentCharacter);
        }
        // Remove dead enemies
        for (int i = 0; i < map.getCurrentEnemies().size(); i++) {
            Character currentCharacter = map.getCurrentEnemies().get(i);

            if (currentCharacter.getHealth() <= 0)
                map.getCurrentEnemies().remove(currentCharacter);
        }
    }

    public String getCurrentCoordinatesString(){
     return "Current tile (column, row): "+(getMap().getCurrentCoordinateY()+1)+","+(getMap().getCurrentCoordinateX()+1);
    }


    public List<Character> getPlayersTeam() {
        return this.playersTeam;
    }

    public void setPlayersTeam(List<Character> newPlayersTeam) {
        this.playersTeam = newPlayersTeam;
    }

    public boolean isTeamDead(){
        if (this.getPlayersTeam().size() <= 0)
            return true;
        else
            return false;
    }

    public boolean areEnemiesPresentHere(){
        return this.getMap().getCurrentTile().areEnemiesPresent();
    }

    public Map getMap() {
        return this.map;
    }

    public void setMap(Map newMap){
        this.map = newMap;
    }

    public boolean isEndOfTheMap(){
        return getMap().isEndOfTheMap();
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    public void setInventory(List<Item> newInventory){
        this.inventory = newInventory;
    }
}
