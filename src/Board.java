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


        // Create middle line
        for (int x = 0; x <= x_max; x++) {
            ArrayList<Integer> intarr=  new ArrayList<Integer>();
            Tile t= new Tile(x,0,"empty",intarr);
            intarr.add(id);
            tile_list.add(t);
            id++;
        }
        for (int x_offset = 0; x_offset < x_max; x_offset++) {
            // Create top of the board
            if (x_offset <= x_max-y_max){
                for(int y_offset = 1; y_offset <= y_max; y_offset++){
                    ArrayList<Integer> intarr=  new ArrayList<Integer>();
                    Tile t= new Tile(x_offset, y_offset,"empty",intarr);
                    intarr.add(id);
                    tile_list.add(t);
                    id++;
                }
            } else {
                for (int y_offset = 1; y_offset <= x_max-x_offset; y_offset++) {
                    ArrayList<Integer> intarr=  new ArrayList<Integer>();
                    Tile t= new Tile(x_offset, y_offset,"empty",intarr);
                    intarr.add(id);
                    tile_list.add(t);
                    id++;
                }
            }
            // Create bottom of the board
            if (x_offset <= x_max+y_min){
                for(int y_offset = 1; y_offset <= -y_min; y_offset++){
                    ArrayList<Integer> intarr=  new ArrayList<Integer>();
                    Tile t= new Tile(x_offset, -y_offset,"empty",intarr);
                    intarr.add(id);
                    tile_list.add(t);
                    id++;
                }
            } else {
                for (int y_offset = 1; y_offset <= x_max-x_offset; y_offset++) {
                    ArrayList<Integer> intarr=  new ArrayList<Integer>();
                    Tile t= new Tile(x_offset, -y_offset,"empty",intarr);
                    intarr.add(id);
                    tile_list.add(t);
                    id++;
                }
            }
        }
        // // Create square
        // for(int x = 0; x <= x_max/2; x++){
        //     for(int y = 0;y >= -y_max; y--){
        //         ArrayList<Integer> intarr=  new ArrayList<Integer>();
        //         Tile t= new Tile(x,y,"empty",intarr);
        //         intarr.add(id);
        //         tile_list.add(t);
        //         id++;
        //     }
        //     for(int y = 1;y <= y_min; y++){
        //         ArrayList<Integer> intarr=  new ArrayList<Integer>();
        //         Tile t= new Tile(x,y,"empty",intarr);
        //         intarr.add(id);
        //         tile_list.add(t);
        //         id++;
        //     }
        // }
        // // Create triangle point
        // for(int x_offset = x_max-1; x_offset <= x_max-y_max; x++){
        //     // Create bottom half
        //     for(int y_offset = 0;y >= y_min+x_offset; y--){
        //         ArrayList<Integer> intarr=  new ArrayList<Integer>();
        //         Tile t= new Tile(x,y,"empty",intarr);
        //         intarr.add(id);
        //         tile_list.add(t);
        //         id++;
        //     }
        //     // Create top half
        //     for(int y_offset = 1;y <= y_max-x_offset; y++){
        //         ArrayList<Integer> intarr=  new ArrayList<Integer>();
        //         Tile t= new Tile(x,y,"empty",intarr);
        //         intarr.add(id);
        //         tile_list.add(t);
        //         id++;
        //     }
        // }
        System.out.println(id);
    }  
}
