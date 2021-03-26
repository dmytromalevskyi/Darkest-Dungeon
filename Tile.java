import java.util.ArrayList;
import java.util.List;

public class Tile {
    private boolean isPath = false;
    private List<Character> enemies = new ArrayList<>();
    
    public Tile(boolean isPath){
        this.isPath = isPath;
    }

    // Initialise a tile that is not a path
    //
    public Tile(){
    }

    public Tile(List<Character> enemies){
        this.isPath = true;
        this.enemies = enemies;
    }

    public List<Character> getEnemies(){
        return this.enemies;
    }

    public void setEnemies(List<Character> enemies){
        this.enemies = enemies;
    }

    public boolean areEnemiesPresent() {
        for (int i = 0; i < this.getEnemies().size(); i++) {
            if (this.getEnemies().get(i).getHealth() > 0)
                return true;
        }
        return false;
    }

    public boolean getIsPath(){
        return this.isPath;
    }

    public void setIsPath(boolean isPath){
        this.isPath = isPath;
    }
}
