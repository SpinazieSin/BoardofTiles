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
        for(int i = 1;i <= y_size/2; i++){
            ArrayList<Integer> intarr=  new ArrayList<Integer>();
            intarr.add(id);
            Tile t= new Tile(0,i,"empty",intarr);
            tile_list.add(t);
            id++;

        }
        for(int i = 0;i >= -y_size/2; i--){
            ArrayList<Integer> intarr=  new ArrayList<Integer>();
            Tile t= new Tile(0,i,"empty",intarr);
            intarr.add(id);
            tile_list.add(t);
            id++;
        }
        for()
            for(int i = 0;i >= x_size; i++){
                ArrayList<Integer> intarr=  new ArrayList<Integer>();
                Tile t= new Tile(i,0,"empty",intarr);
                intarr.add(id);
                tile_list.add(t);
                id++;

        for(Tile t : tile_list){
            System.out.println(t.y_cor);
        }
    }  
}
