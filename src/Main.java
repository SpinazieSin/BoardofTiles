import java.util.*;

public class Main {

    public static void main (String[] args) {
        Board board = buildBoard(8, 4, 4);
        // Gameloop
        Movement.playerMove(board);
        while (true) {
            Scanner scan = new Scanner(System.in);
            try {
                int xCor = scan.nextInt();
                int yCor = scan.nextInt();
                System.out.println(xCor);
                System.out.println(yCor);
            }
            catch (Exception e) {
                System.out.println("Bad input, try x and y coordinates seperated by spaces");
                continue;
            }
            printBoard(board);
        }
    }

    public static Board buildBoard(int xMax, int yMax, int yMin){
        Board board = new Board(xMax,yMax,yMin);
        initUnits(board);
        return(board);
    }


    public static void printBoard(Board board){
        System.out.print("+-");
        for (int yAxis = 0; yAxis < board.yMax+board.yMin+1; yAxis++) {
            System.out.print("-"+yAxis+"-");
        }
        System.out.println("-+");
        for (int xOffset = 0; xOffset < board.xMax+1; xOffset++) {
            System.out.print(xOffset+"-");
            for (int yOffset = 0; yOffset < board.yMax+board.yMin+1; yOffset++) {
                if (board.tiles[xOffset][yOffset] == null) {
                    System.out.print("---");
                   continue;
                } else {
                    String unit = getUnitName(board.tiles[xOffset][yOffset].unit[0]);
                    System.out.print("["+unit+"]");
                }
            }
            System.out.println("-"+xOffset);
        }
        System.out.print("+-");
        for (int yAxis = 0; yAxis < board.yMax+board.yMin+1; yAxis++) {
            System.out.print("-"+yAxis+"-");
        }
        System.out.println("-+");
    }

    public static void initUnits(Board board) {
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
        for (int j = 0; j<board.xMax+1; j++) {
            for (int i = 0; i<board.tiles[j].length; i++) {
                if(board.tiles[j][i] == null){
                    continue;
                } else {
                    tilecounter ++;
                    if(board.tiles[j][i].unit[0] == 1) {
                        swordsmancounter++;
                        System.out.println("xcor: " + board.tiles[j][i].xCor);
                        System.out.println("ycor: " + board.tiles[j][i].yCor);

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

    private static String getUnitName(int index){
        if(index == 0) return(" ");
        if(index == 1) return("S");
        if(index == 2) return("E");
        if(index == 3) return("G");
        if(index == 4) return("O");
        return("WHAT?");
    }
}