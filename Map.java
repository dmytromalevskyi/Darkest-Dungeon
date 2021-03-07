import java.util.List;

public final class Map {
    private Tile[][] tiles;
    private byte[] currentCoordinates = new byte[2];

    public Map(int sizeOfTheMap){
        this.tiles = new Tile[sizeOfTheMap][sizeOfTheMap];
        this.currentCoordinates = new byte[2];
        currentCoordinates[0] = 1;
        currentCoordinates[1] = 0;
        // make all outter tiles as walls exept the starting positing
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles.length; j++) {
                if (i == 1 && j == 0) // starting point
                    this.tiles[i][j] = new Tile(false);
                else if (i == 0)
                    this.tiles[i][j] = new Tile(true); // top walls
                else if (i == sizeOfTheMap - 1)
                    this.tiles[i][j] = new Tile(true); // bottom walls
                else if (j == 0)
                    this.tiles[i][j] = new Tile(true); // left edge
                else if (j == sizeOfTheMap - 1)
                    this.tiles[i][j] = new Tile(true); // right edge
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

        String isWallColour = greenBG;
        String notWallColour = reset;

        System.out.println("dimention: "+dimention);
        
        // Generate borders
        String topBorder = generateTopBorder(dimention);
        String bottomBorder = generateBottomBorder(dimention);
        String seperatorBorder = generateSeperatorBorder(dimention);

        System.out.println(topBorder);
        for (int i = 0; i <= dimention-1; i++){
            System.out.print(" ┃");
            for (int z = 0; z <= dimention-1; z++){
                
                String lookChange = (this.tiles[i][z].getIsWall()) ? isWallColour : notWallColour;
                
                if (z == dimention-1){
                    // If the boolean is true print 1
                    if (this.tiles[i][z].getIsWall())
                        System.out.print(lookChange + forTrue + reset);
                    else
                        System.out.print(lookChange + forFalse + reset);
                    System.out.print("┃ " + (i+1) + "\n");
                }else{
                    // If the boolean is true print 1
                    if (this.tiles[i][z].getIsWall())
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
    public void move(byte[] newCoordinates) {
        this.currentCoordinates = newCoordinates;
    }

    public Tile getCurrentTile() {
        return this.tiles[this.currentCoordinates[0]][this.currentCoordinates[1]];
    }

    public byte[] getCurrentCoordinates() {
        return this.currentCoordinates;
    }

}
