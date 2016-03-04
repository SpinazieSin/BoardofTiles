import java.util.*;

public class Tile {
        
    // the Tile
    // three fields
    public int x_cor;
    public int y_cor;
    public int[] unit;
    public ArrayList<Integer[]> neighbours;
        
    // the Tile class
    // one constructor
    public Tile(int x_cor_in, int y_cor_in, int[] unit_in, ArrayList<Integer[]> neighbour_in) {
        neighbours = neighbour_in;
        x_cor = x_cor_in;
        y_cor = y_cor_in;
        unit = unit_in;
    }  
}
