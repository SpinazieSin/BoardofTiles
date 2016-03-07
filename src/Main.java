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
        init_units(board);
        print_board(board, x_max, y_max, y_min);
    }

    public static void print_board(Board board, int x_max, int y_max, int y_min){
    	int i = 0;
    	while(i <= x_max){
	    	for(Tile t : board.tiles){
	    		if(t.y_cor == y_max - i){
	    			// System.out.print("[" + t.x_cor + "," + t.y_cor + "]");
                    System.out.print("[" + t.unit[0] + "]");
	    			// System.out.print()
	    		}
	    	}
	    	System.out.println();
	    	i++;
	    }
    }

    public static void init_units(Board board) {
        int[] orc = {4, 10, 8};
        int[] goblin = {3, 3, 4};
        int[] general = {2, 5, 8};
        int[] swordsman = {1, 4, 6};
        for(Tile t : board.tiles){
            //Initialize Greenskin side
            if(t.x_cor == 8 && t.y_cor == 0) t.unit = orc;
            if(t.x_cor == 3 && t.y_cor == 4) t.unit = orc;
            if(t.x_cor == 7 && t.y_cor == 0) t.unit = goblin;
            if(t.x_cor == 7 && t.y_cor == -1) t.unit = goblin;
            if(t.x_cor == 6 && t.y_cor == -2) t.unit = goblin;
            if(t.x_cor == 6 && t.y_cor == 1) t.unit = goblin;
            if(t.x_cor == 5 && t.y_cor == 2) t.unit = goblin;
            if(t.x_cor == 5 && t.y_cor == 3) t.unit = goblin;
            if(t.x_cor == 4 && t.y_cor == 3) t.unit = goblin;
            if(t.x_cor == 3 && t.y_cor == 3) t.unit = goblin;

            // Initialize Human side
            if(t.x_cor == 0 && t.y_cor == 0) t.unit = general;
            if(t.x_cor == 0 && t.y_cor == -3) t.unit = general;
            if(t.x_cor == 1 && t.y_cor == -4) t.unit = general;
            if(t.x_cor == 0 && t.y_cor == 1) t.unit = swordsman;
            if(t.x_cor == 0 && t.y_cor == -1) t.unit = swordsman;
            if(t.x_cor == 1 && t.y_cor == 0) t.unit = swordsman;
            if(t.x_cor == 1 && t.y_cor == -1) t.unit = swordsman;
            if(t.x_cor == 1 && t.y_cor == -2) t.unit = swordsman;
            if(t.x_cor == 1 && t.y_cor == -3) t.unit = swordsman;
        }
    }
}