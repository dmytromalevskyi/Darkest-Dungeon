import java.util.List;

public final class Map {
    private Tile[][] tiles;
    private int[] currentCoordinates = new int[2];
    private int[] endOfTheMap = new int[2];

    public Map(int sizeOfTheMap){
        this.tiles = new Tile[sizeOfTheMap][sizeOfTheMap];
        this.currentCoordinates = new int[2];
        int approximateMiddleX = Math.round(sizeOfTheMap/2);
        currentCoordinates = new int[]{approximateMiddleX,0};
        endOfTheMap = new int[]{approximateMiddleX, sizeOfTheMap-1};
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles.length; j++) {
                if (i == currentCoordinates[0])
                    this.tiles[i][j] = new Tile(true);
                else
                    this.tiles[i][j] = new Tile(false);
            }
        }
    }

    // Draw a board of any square dimention
    //
    public void draw(){
        int dimention = this.tiles.length;
        String forTrue = "  ";
        String forFalse = "  ";

        // Colour strings
        String reset = "\u001b[0m";
        String redBG = "\u001B[41m";
        String greenBG = "\u001B[42m";

        String isPathColour = greenBG;
        String notPathColour = reset;
        String currentCoordinateColour = redBG;

        System.out.println("dimention: "+dimention);
        
        // Generate borders
        String topBorder = generateTopBorder(dimention);
        String bottomBorder = generateBottomBorder(dimention);
        String seperatorBorder = generateSeperatorBorder(dimention);

        System.out.println(topBorder);
        for (int i = 0; i <= dimention-1; i++){
            System.out.print(" ┃");
            for (int z = 0; z <= dimention-1; z++){
                
                String lookChange;
                if(i == getCurrentCoordinateX() && z == getCurrentCoordinateY()){
                    lookChange = currentCoordinateColour;
                }else{
                    lookChange = (this.tiles[i][z].getIsPath()) ? isPathColour  : notPathColour;
                }
                
                if (z == dimention-1){
                    if (this.tiles[i][z].getIsPath())
                        System.out.print(lookChange + forTrue + reset);
                    else
                        System.out.print(lookChange + forFalse + reset);
                    System.out.print("┃ " + (i+1) + "\n");
                }else{
                    if (this.tiles[i][z].getIsPath())
                        System.out.print(lookChange + forTrue + reset);
                    else
                        System.out.print(lookChange + forFalse + reset);
                    System.out.print("┃");
                }
                

            }
            if (i != dimention-1)
                System.out.print(seperatorBorder+"\n");

        }        
        System.out.println(bottomBorder);
    }

    public void name() {
        
    }

    // Generate top border for board of size dimention
    //
    public static String generateTopBorder(int dimention) {
        String topBorder = " ";

        topBorder = topBorder + "┏";
        for (int i = 1; i <= dimention + dimention - 1; i++){
            if (i == (dimention + dimention - 1))
                topBorder = topBorder + "━━";
            else if (i%2 == 0)
                topBorder = topBorder + "┳";
            else
                topBorder = topBorder + "━━";
        }
        topBorder = topBorder + "┓ ";

        return topBorder;
    }


    // Generate seperator border for board of size dimention
    //
    public static String generateSeperatorBorder(int dimention) {
        String topBorder = " ";

        topBorder = topBorder + "┣";
        for (int i = 1; i <= dimention + dimention - 1; i++){
            if (i == (dimention + dimention - 1))
                topBorder = topBorder + "━━";
            else if (i%2 == 0)
                topBorder = topBorder + "╋";
            else
                topBorder = topBorder + "━━";
        }
        topBorder = topBorder + "┫ ";

        return topBorder;
    }


    // Generate bottom border for board of size dimention
    //
    public static String generateBottomBorder(int dimention) {
        String topBorder = " ";

        topBorder = topBorder + "┣";
        for (int i = 1; i <= dimention + dimention - 1; i++){
            if (i == (dimention + dimention - 1))
                topBorder = topBorder + "━━";
            else if (i%2 == 0)
                topBorder = topBorder + "┻";
            else
                topBorder = topBorder + "━━";
        }
        topBorder = topBorder + "┛ ";

        return topBorder;
    }

    public List<Character> getCurrentEnemies(){
        return getCurrentTile().getEnemies();
    }

    // Change position of the player on the map
    //
    public void move(int[] newCoordinates) {
        this.currentCoordinates = newCoordinates;
    }

    public Tile getCurrentTile() {
        return getTile(currentCoordinates);
    }

    public Tile getTile(int[] coordinates) {
        return this.tiles[coordinates[0]][coordinates[1]];
    } 

    public int[] getCurrentCoordinates() {
        return this.currentCoordinates;
    }

    public int getCurrentCoordinateX(){
        return getCurrentCoordinates()[0];
    }

    public int getCurrentCoordinateY(){
        return getCurrentCoordinates()[1];
    }

    public int getMaxCoordinateX(){
        return tiles.length;
    }

    public int getMaxCoordinateY(){
        return tiles[0].length;
    }

    public int[] getEndOfTheMap(){
        return this.endOfTheMap;
    }

    public void setEndOfTheMap(int[] endOfTheMap){
        this.endOfTheMap = endOfTheMap;
    }

    public boolean isEndOfTheMap(){
        if (getCurrentCoordinateX() == getEndOfTheMap()[0] && getCurrentCoordinateY() == getEndOfTheMap()[1]){
            return true;
        }else{
            return false;
        }
    }

}
