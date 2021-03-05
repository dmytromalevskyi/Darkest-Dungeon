import java.util.ArrayList;
import java.util.List;

public class Tile {
    private boolean isWall;
    private List<Character> enemies = new ArrayList<>();
    
    public Tile(boolean isWall){
        this.isWall = isWall;
    }

    public Tile(List<Character> enemies){
        this.isWall = false;
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

    public boolean getIsWall(){
        return this.isWall;
    }

    public void setIsWall(boolean isWall){
        this.isWall = isWall;
    }
}
