import java.util.*;

public class Tile {
    // the Tile
    // three fields
    public int xCor;
    public int yCor;
    public int[] unit;
    public ArrayList<Integer[]> neighbours;
        
    // the Tile class
    // one constructor
    public Tile(int xCorInput, int yCorInput, int[] unitInput, ArrayList<Integer[]> neighbourList) {
        neighbours = neighbourList;
        xCor = xCorInput;
        yCor = yCorInput;
        unit = unitInput;
    }

    public static Tile cloneTile(int xCorInput, int yCorInput, int[] unitInput, ArrayList<Integer[]> neighbourList) {
        Tile clonedTile = new Tile(xCorInput, yCorInput, unitInput, neighbourList);
        return clonedTile;
    }
}
