import java.util.*;

public class Main {

    public static void main (String[] args) {
        Board board = build_board(8, 4, 4);
        // Gameloop
        while (true) {
            Scanner scan = new Scanner(System.in);
            try {
                int x_cor = scan.nextInt();
                int y_cor = scan.nextInt();
                System.out.println(x_cor);
                System.out.println(y_cor);
            }
            catch (Exception e) {
                System.out.println("Bad input, try x and y coordinates seperated by spaces");
                continue;
            }
            print_board(board);
        }
    }

    public static Board build_board(int x_max, int y_max, int y_min){
        Board board = new Board(x_max,y_max,y_min);
        init_units(board);
        return(board);
    }

    public static void print_board(Board board){
		for (int x_offset = 0; x_offset < board.x_max+1; x_offset++) {
            for (int y_offset = 0; y_offset < board.y_max+board.y_min; y_offset++) {
                if (board.tiles[x_offset][y_offset] == null) {
                   continue;
                } else {
                    System.out.print(board.tiles[x_offset][y_offset].unit[0]);
                }
            }
            System.out.println();
    	}
    }

    public static void init_units(Board board) {
        int[] orc = {4, 10, 8};
        int[] goblin = {3, 3, 4};
        int[] general = {2, 5, 8};
        int[] swordsman = {1, 4, 6};
    }
}