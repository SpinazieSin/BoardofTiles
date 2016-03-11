import java.util.*;

public class Main {

    public static void main (String[] args) {
        Board board = build_board(8, 4, 4);
        // Gameloop
        Movement.playerMove(board);
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
        //Initialize Greenskin side
        board.tiles[8][4].unit = orc;
        board.tiles[3][8].unit = orc;
        board.tiles[7][4].unit = goblin;
        board.tiles[7][3].unit = goblin;
        board.tiles[6][2].unit = goblin;
        board.tiles[6][5].unit = goblin;
        board.tiles[5][5].unit = goblin;
        board.tiles[5][7].unit = goblin;
        board.tiles[4][7].unit = goblin;
        board.tiles[3][7].unit = goblin;

        // Initialize Human side
        board.tiles[0][4].unit = general;
        board.tiles[0][1].unit = general;
        board.tiles[1][0].unit = general;
        board.tiles[0][5].unit = swordsman;
        board.tiles[0][3].unit = swordsman;
        board.tiles[1][4].unit = swordsman;
        board.tiles[1][3].unit = swordsman;
        board.tiles[1][2].unit = swordsman;
        board.tiles[1][1].unit = swordsman;
    }


    // returns 0 if no player has won, 1 if humans win and 2 if orcs win.
    public int gameWon(Board board){
        int tilecounter = 0;
        int swordsmancounter = 0;
        int goblincounter = 0;
        int orccounter = 0;
        int generalcounter = 0;
        for (int j = 0; j<board.x_max+1; j++) {
            for (int i = 0; i<board.tiles[j].length; i++) {
                if(board.tiles[j][i] == null){
                    continue;
                } else {
                    tilecounter ++;
                    if(board.tiles[j][i].unit[0] == 1) {
                        swordsmancounter++;
                        System.out.println("xcor: " + board.tiles[j][i].x_cor);
                        System.out.println("ycor: " + board.tiles[j][i].y_cor);

                    }
                    if(board.tiles[j][i].unit[0] == 2) generalcounter++;
                    if(board.tiles[j][i].unit[0] == 3) goblincounter++;
                    if(board.tiles[j][i].unit[0] == 4) orccounter++;

                }

            }
        }
        System.out.println("swordsman " + swordsmancounter);
        System.out.println("general " + generalcounter);
        System.out.println("goblin " + goblincounter);
        System.out.println("orc " + orccounter);

        if(swordsmancounter == 0 && generalcounter == 0){
            return(1);
        } else if(goblincounter == 0 && orccounter == 0){
            return(2);
        } else {
            return(0);
        }
    }
}