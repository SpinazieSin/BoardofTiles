/**
 * Written by Jonathan Gerbscheid and Thomas Groot
 * Jonathan-gerb@hotmail.com and thomas--g@hotmail.com
 * for datastructures project, March 2016. 
 */
import java.util.ArrayList;

public class Board {
    
    public Tile[][] tiles;
    public int xMax;
    public int yMax;
    public int yMin;
    
    /**
     * Initializes all Tiles and adds them to the tiles array.
     */
    private ArrayList<Tile> initializeTiles(int xMaxInt, int yMaxInt, int yMinInt){
        ArrayList<Tile> tileList = new ArrayList<Tile>();
        xMax = xMaxInt;
        yMax = yMaxInt;
        yMin = yMinInt;
        int[] placeholderUnit = {0,0,0};
        // Create middle line
        for (int x = 0; x <= xMax; x++) {
            ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
            Tile t= new Tile(x, yMin, placeholderUnit, neighbourPlaceholder);
            tileList.add(t);
        }
        for (int xOffset = 0; xOffset < xMax; xOffset++) {
            // Create top of the board
            if (xOffset <= xMax-yMax){
                for(int yOffset = 1; yOffset <= yMax; yOffset++){
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                }
            } else {
                for (int yOffset = 1; yOffset <= xMax-xOffset; yOffset++) {
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                }
            }
            // Create bottom of the board
            if (xOffset <= xMax-yMin){
                for(int yOffset = 1; yOffset <= yMin; yOffset++){
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, -yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                }
            } else {
                for (int yOffset = 1; yOffset <= xMax-xOffset; yOffset++) {
                    ArrayList<Integer[]> neighbourPlaceholder=  new ArrayList<Integer[]>();
                    Tile t= new Tile(xOffset, -yOffset+yMin, placeholderUnit, neighbourPlaceholder);
                    tileList.add(t);
                }
            }
        }
        return(tileList);
    }

    /**
     * Adds neighbours to all Tiles.
     */
    private void addNeighbours(ArrayList<Tile> tileList){
        int tileListSize = tileList.size();
        tiles = new Tile[tileListSize][yMax+yMin+1];
        for (int tileCount = 0; tileCount < tileListSize; tileCount++) {
            Tile tile = tileList.get(tileCount);
            // Find all neighbours to the tile
            ArrayList<Integer[]> neighbourList = new ArrayList<Integer[]>();
            for (int neighbourCount = 0; neighbourCount < tileListSize; neighbourCount++) {
                int neighbourXCor = tileList.get(neighbourCount).xCor;
                int neighbourYCor = tileList.get(neighbourCount).yCor;
                // Rules for finding the neighbours for the bottom half of the board.
                if (tile.yCor < yMin) {
                    if (
                        tile.xCor + 1 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                        tile.xCor + 0 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                        tile.xCor - 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ||
                        tile.xCor - 1 == neighbourXCor && tile.yCor - 1 == neighbourYCor ||
                        tile.xCor + 0 == neighbourXCor && tile.yCor - 1 == neighbourYCor ||
                        tile.xCor + 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ) {
                        Integer[] neighbourPosition = {neighbourXCor, neighbourYCor};
                        neighbourList.add(neighbourPosition);
                    }
                }
                // Rules for finding the neighbours for the top half of the board.
                if (tile.yCor > yMin) {
                    if (
                        tile.xCor + 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ||
                        tile.xCor + 0 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                        tile.xCor - 1 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                        tile.xCor - 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ||
                        tile.xCor + 0 == neighbourXCor && tile.yCor - 1 == neighbourYCor ||
                        tile.xCor + 1 == neighbourXCor && tile.yCor - 1 == neighbourYCor) {
                        Integer[] neighbourPosition = {neighbourXCor, neighbourYCor};
                        neighbourList.add(neighbourPosition);
                    }
                }
                // Rules for finding the neighbours for the bmiddle row of Tiles.
                if (tile.yCor == yMin) {
                    if (
                        tile.xCor + 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ||
                        tile.xCor + 0 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                        tile.xCor - 1 == neighbourXCor && tile.yCor + 1 == neighbourYCor ||
                        tile.xCor - 1 == neighbourXCor && tile.yCor + 0 == neighbourYCor ||
                        tile.xCor - 1 == neighbourXCor && tile.yCor - 1 == neighbourYCor ||
                        tile.xCor + 0 == neighbourXCor && tile.yCor - 1 == neighbourYCor) {
                        Integer[] neighbourPosition = {neighbourXCor, neighbourYCor};
                        neighbourList.add(neighbourPosition);
                    }
                }
            }
            Tile finalTile = new Tile(tile.xCor, tile.yCor, tile.unit, neighbourList);
            tiles[tile.xCor][tile.yCor] = finalTile;
        }
    }

    public Board(int xMaxInt, int yMaxInt, int yMinInt) {
        ArrayList<Tile> tileList = initializeTiles(xMaxInt, yMaxInt, yMinInt);
        addNeighbours(tileList);
    }

    /** Prints the coordinates of the neighbours of a tile. */
    public static void printNeighbours(Tile tile, Board board) {
        System.out.println("Neighbours of tile: " + tile.xCor + "," + tile.yCor);
        for (Integer[] neighbour : tile.neighbours) {
            Tile unit = board.tiles[neighbour[0]][neighbour[1]];
            System.out.println("x,y: " + 
                                unit.xCor + "," + 
                                unit.yCor + " - " + 
                                Movement.getUnitName(unit));
        }
    }

    /** 
     * Counts all units on the board and uses those numbers to determine if
     * one of the two sides has won. Returns 0 if no player has won,
     * 1 if humans win and 2 if orcs win. 
     */
    public static int gameWon(Board board) {
        int swordsmancounter = 0;
        int goblincounter = 0;
        int orccounter = 0;
        int generalcounter = 0;
        for (int j = 0; j<board.xMax+1; j++) {
            for (int i = 0; i<board.tiles[j].length; i++) {
                if(board.tiles[j][i] == null){
                    continue;
                }
                if(board.tiles[j][i].unit[0] == 1) swordsmancounter++;
                if(board.tiles[j][i].unit[0] == 2) generalcounter++;
                if(board.tiles[j][i].unit[0] == 3) goblincounter++;
                if(board.tiles[j][i].unit[0] == 4) orccounter++;
            }
        }
        if(swordsmancounter == 0 && generalcounter == 0){
            return(-1);
        }
        if(goblincounter == 0 && orccounter == 0){
            return(1);
        }
        return(0);
        
    }

    /**
     * Cloning function that copies the content of a board to a 
     * new board, used for Learning.
     */
    public static Board deepCloneBoard(Board board) {
        Board boardClone = new Board(8,4,4);
        for (int i = 0; i < board.tiles.length; i++) {
            for (int j = 0; j < board.tiles[i].length; j++) {
                if (board.tiles[i][j] == null) continue;
                boardClone.tiles[i][j] = Tile.cloneTile(board.tiles[i][j].xCor, 
                                                        board.tiles[i][j].yCor,
                                                        board.tiles[i][j].unit,
                                                        board.tiles[i][j].neighbours);
            }
        }
        return boardClone;
    }
}