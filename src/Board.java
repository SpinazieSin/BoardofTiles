import java.util.*;

public class Board {
        
    public Tile[][] tiles;
    public int xMax;
    public int yMax;
    public int yMin;
        
    // the Tile class
    // one constructor
    public Board(int xMaxInt, int yMaxInt, int yMinInt) {
        ArrayList<Tile> tileList = new ArrayList<Tile>();
        xMax = xMaxInt;
        yMax = yMaxInt;
        yMin = yMinInt;
        int id = 0;

        int[] placeholderUnit = {0,0,0};
        // Create middle line
        for (int x = 0; x <= xMax; x++) {
            ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
            Tile t= new Tile(x, yMin, placeholderUnit, neighbourPlaceholder);
            tileList.add(t);
            id++;
        }
        for (int xOffset = 0; xOffset < xMax; xOffset++) {
            // Create top of the board
            if (xOffset <= xMax-yMax){
                for(int yOffset = 1; yOffset <= yMax; yOffset++){
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                    id++;
                }
            } else {
                for (int yOffset = 1; yOffset <= xMax-xOffset; yOffset++) {
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                    id++;
                }
            }
            // Create bottom of the board
            if (xOffset <= xMax-yMin){
                for(int yOffset = 1; yOffset <= yMin; yOffset++){
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, -yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                    id++;
                }
            } else {
                for (int yOffset = 1; yOffset <= xMax-xOffset; yOffset++) {
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, -yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                    id++;
                }
            }
        }

        // Loop over previous array to create a final list of all the tiles
        int tileListSize = tileList.size();
        tiles = new Tile[tileListSize][yMax+yMin+1];
        for (int tileCount = 0; tileCount < tileListSize; tileCount++) {
            Tile tile = tileList.get(tileCount);
            // Find all neighbours to the tile
            ArrayList<Integer[]> neighbourList = new ArrayList<Integer[]>();
            for (int neighbourCount = 0; neighbourCount < tileListSize; neighbourCount++) {
                int neighbourXCor = tileList.get(neighbourCount).xCor;
                int neighbourYCor = tileList.get(neighbourCount).yCor;
                if (tile.xCor + 1 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                    tile.xCor + 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ||
                    tile.xCor + 0 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                    tile.xCor + 0 == neighbourXCor && tile.yCor - 1 == neighbourYCor ||
                    tile.xCor - 1 == neighbourXCor && tile.yCor - 1 == neighbourYCor ||
                    tile.xCor - 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ) {
                    Integer[] neighbourPosition = {neighbourXCor, neighbourYCor};
                    neighbourList.add(neighbourPosition);
                }
            }
            Tile finalTile = new Tile(tile.xCor, tile.yCor, tile.unit, neighbourList);
            tiles[tile.xCor][tile.yCor] = finalTile;
        }
    }

    // Prints the coordinates of the neighbours of a tile
    private void printNeighbours(Tile tile) {
        System.out.println("Neighbours of tile: " + tile.xCor + "," + tile.yCor);
        for (Integer[] neighbour : tile.neighbours) {
            System.out.println("x,y: " + neighbour[0] + "," + neighbour[1]);
        }
    }
}
