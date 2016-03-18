import java.util.ArrayList;

public class Tile {
    public int xCor;
    public int yCor;
    public int[] unit;
    public ArrayList<Integer[]> neighbours;
        
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