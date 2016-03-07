import java.util.*;

public class Main {

    public static void main (String[] args) {
        build_board(8, 4, 4);
        for (String s: args) {
            System.out.println(s);
        }	
    }

    public static void build_board(int x_max, int y_max, int y_min){
        Board board = new Board(x_max,y_max,y_min);
        print_board(board, x_max, y_max, y_min);
    }

    public static void print_board(Board board, int x_max, int y_max, int y_min){
    	int i = 0;
    	while(i <= x_max){
	    	for(Tile t : board.final_tile_list){
	    		if(t.y_cor == y_max - i){
	    			System.out.print("[" + t.x_cor + "," + t.y_cor + "]");
	    			// System.out.print()
	    		}
	    	}
	    	System.out.println();
	    	i++;
	    }
    }
}