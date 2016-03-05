import java.util.*;

public class Board {
        
    public ArrayList<Tile> tile_list;
    public int x_max;
    public int y_max;
    public int y_min;
        
    // the Tile class
    // one constructor
    public Board(int x_max_in, int y_max_in, int y_min_in) {
        tile_list = new ArrayList<Tile>();
        x_max = x_max_in;
        y_max = y_max_in;
        y_min = y_min_in;
        int id = 0;

        int[] placeholder_unit = {0,0,0};
        // Create middle line
        for (int x = 0; x <= x_max; x++) {
            ArrayList<Integer[]> intarr=  new ArrayList<Integer[]>();
            Tile t= new Tile(x, 0, placeholder_unit, intarr);
            tile_list.add(t);
            id++;
        }
        for (int x_offset = 0; x_offset < x_max; x_offset++) {
            // Create top of the board
            if (x_offset <= x_max-y_max){
                for(int y_offset = 1; y_offset <= y_max; y_offset++){
                    ArrayList<Integer[]> intarr=  new ArrayList<Integer[]>();
                    Tile t= new Tile(x_offset, y_offset, placeholder_unit, intarr);
                    tile_list.add(t);
                    id++;
                }
            } else {
                for (int y_offset = 1; y_offset <= x_max-x_offset; y_offset++) {
                    ArrayList<Integer[]> intarr=  new ArrayList<Integer[]>();
                    Tile t= new Tile(x_offset, y_offset, placeholder_unit, intarr);
                    tile_list.add(t);
                    id++;
                }
            }
            // Create bottom of the board
            if (x_offset <= x_max-y_min){
                for(int y_offset = 1; y_offset <= y_min; y_offset++){
                    ArrayList<Integer[]> intarr=  new ArrayList<Integer[]>();
                    Tile t= new Tile(x_offset, -y_offset, placeholder_unit, intarr);
                    tile_list.add(t);
                    id++;
                }
            } else {
                for (int y_offset = 1; y_offset <= x_max-x_offset; y_offset++) {
                    ArrayList<Integer[]> intarr=  new ArrayList<Integer[]>();
                    Tile t= new Tile(x_offset, -y_offset, placeholder_unit, intarr);
                    tile_list.add(t);
                    id++;
                }
            }
        }

        // Loop over previous array to create a final list of all the tiles
        int tile_list_size = tile_list.size();
        Tile[] final_tile_list = new Tile[tile_list_size];
        for (int tile_count = 0; tile_count < tile_list_size; tile_count++) {
            Tile tile = tile_list.get(tile_count);
            // Find all neighbours to the tile
            ArrayList<Integer[]> neighbour_list = new ArrayList<Integer[]>();
            for (int neighbour_count = 0; neighbour_count < tile_list_size; neighbour_count++) {
                int neighbour_x_cor = tile_list.get(neighbour_count).x_cor;
                int neighbour_y_cor = tile_list.get(neighbour_count).y_cor;
                if (tile.x_cor - 1 == neighbour_x_cor && tile.y_cor - 1 == neighbour_y_cor ||
                    tile.x_cor == neighbour_x_cor && tile.y_cor -1 == neighbour_y_cor ||
                    tile.x_cor - 1 == neighbour_x_cor && tile.y_cor == neighbour_y_cor ||
                    tile.x_cor - 1 == neighbour_x_cor && tile.y_cor + 1 == neighbour_y_cor ||
                    tile.x_cor  == neighbour_x_cor && tile.y_cor + 1 == neighbour_y_cor ||
                    tile.x_cor + 1 == neighbour_x_cor && tile.y_cor == neighbour_y_cor ) {
                    Integer[] neighbour_cor = {neighbour_x_cor, neighbour_y_cor};
                    neighbour_list.add(neighbour_cor);
                }
            }
            Tile final_tile = new Tile(tile.x_cor, tile.y_cor, tile.unit, neighbour_list);
            final_tile_list[tile_count] = final_tile;
        }
        System.out.println(id);
    }  
}
