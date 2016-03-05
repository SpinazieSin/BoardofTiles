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
    }
}