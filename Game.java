import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

final public class Game {
    private List<Character> playersTeam = new ArrayList<>();
    private List<Item> inventory = new ArrayList<>();
    private Map map;
  
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Unrecognised command (use -h for help)");
            System.exit(0);
        }

        String inputString = args[0];
        if (inputString.equals("-g")){
            System.out.println("Starting graphical user interface...");
            GUI gui = new GUI(10);
            gui.update();
        }else if (inputString.equals("-l")){
            CLI cli = new CLI(10);
            cli.play();
        }else if (inputString.equals("-h")){
            System.out.println("-g\tuse graphical user interface");
            System.out.println("-l\tuse command line based interface");
        }else{
            System.out.println("Unrecognised command (use -h for help)");
        }
    }


    public Game(int sizeOfTheMap) {
        this.map = new Map(sizeOfTheMap);
        createInventory();
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

    public List<Item> getInventory() {
        return this.inventory;
    }

    public void setInventory(List<Item> newInventory){
        this.inventory = newInventory;
    }
}
