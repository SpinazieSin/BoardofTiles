import java.util.*;

public class Main {

    public static void main (String[] args) {
        build_board(8,8);
        for (String s: args) {
            System.out.println(s);
        }
    }

    public static void build_board(int x_size, int y_size){
        Board board = new Board(x_size,y_size);
    }
}

