import java.util.*;

public class Board {
        
    public ArrayList<Tile> tile_list;
    public int x_size;
    public int y_size;
        
    // the Tile class
    // one constructor
    public Board(int x_size_in, int y_size_in) {
        tile_list = new ArrayList<Tile>();
        x_size = x_size_in;
        y_size = y_size_in;
        int id = 0;
        // for(int i = 1;i <= y_size/2; i++){
        //     ArrayList<Integer> intarr=  new ArrayList<Integer>();
        //     intarr.add(id);
        //     Tile t= new Tile(0,i,"empty",intarr);
        //     tile_list.add(t);
        //     id++;

        // }
        // for(int i = 0;i >= -y_size/2; i--){
        //     ArrayList<Integer> intarr=  new ArrayList<Integer>();
        //     Tile t= new Tile(0,i,"empty",intarr);
        //     intarr.add(id);
        //     tile_list.add(t);
        //     id++;
        // }
        for(int x = 0; x <= x_size/2; x++){
            for(int y = 0;y >= -y_size/2; y--){
                ArrayList<Integer> intarr=  new ArrayList<Integer>();
                Tile t= new Tile(x,y,"empty",intarr);
                intarr.add(id);
                tile_list.add(t);
                id++;
            }
            for(int y = 1;y >= y_size/2; y++){
                ArrayList<Integer> intarr=  new ArrayList<Integer>();
                Tile t= new Tile(x,y,"empty",intarr);
                intarr.add(id);
                tile_list.add(t);
                id++;
            }
        }
        int variable_x_size = x_size;
        int variable_y_size = (y_size/2) -1;
        for(int x = x_size/2; x <= x_size; x++){
            for(int y = 0;y >= -variable_y_size; y--){
                ArrayList<Integer> intarr=  new ArrayList<Integer>();
                Tile t= new Tile(x,y,"empty",intarr);
                intarr.add(id);
                tile_list.add(t);
                id++;
            }
            for(int y = 1;y <= variable_y_size; y++){
                ArrayList<Integer> intarr=  new ArrayList<Integer>();
                Tile t= new Tile(x,y,"empty",intarr);
                intarr.add(id);
                tile_list.add(t);
                id++;
            }
            variable_y_size--;
        }
        for(Tile t: tile_list){
            System.out.println("===========");
            System.out.println(t.x_cor);
            System.out.println(t.y_cor);
            System.out.println("===========");
        }
        System.out.println(id);
    }  
}
