import java.util.*;
import java.io.*;
import drawing.*;

import javax.swing.JFrame;

public class Main {

    public static void main (String[] args) {
        int balance = 0;
        // Gameloop
        int breakCount = 0;
        for (int gameCount = 0; gameCount < 25; gameCount++) {
            Board board = buildBoard(8, 4, 4);
            // Movement.drawFrames = true; // comment this to disable drawing windows
            if(Movement.drawFrames){
                System.out.println("drawing frames enabled.");
                JFrame fr = new JFrame();
                fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                Integer[][][] data = Movement.boardDrawData(board);
                drawing.Main frame = new drawing.Main(data);
                Movement.f = fr;
                fr.pack();
                fr.setVisible(true);
            }
            String victor = "unknown, something went wrong...";
            for (int i = 0; i < 100; i++) {
                // printBoard(board);
                // System.out.println("-----------Human Turn-----------");
                // Movement.reinforcedLearningMove(board, 0);
                // Movement.reinforcedLearningMove(board, 0);
                Movement.aiMove(board, 0);
                // Movement.playerMove(board);
                // System.out.println("-----------Greenskin------------");
                Movement.aiMove(board, 2);
                // Movement.reinforcedLearningMove(board, 2);
                // Movement.reinforcedLearningMove(board, 2);
                breakCount++;
                if (Board.gameWon(board) == -1) {
                    // Orcs won
                    victor = "Orcs";
                    balance-=1;
                    break;
                }
                if (Board.gameWon(board) == 1) {
                    // Humans won
                    victor = "Humans";
                    balance+=1;
                    break;
                }
            }

            System.out.println("GAME - " + gameCount + " victor: " + victor);
        }
        System.out.println();
        System.out.println(breakCount);
        System.out.println("---------------------------------");
        System.out.println("BALANCE IS " + balance + " -----------");
        System.out.println("---------------------------------");
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
            int[] orc1 = {4, 10, 8};
            int[] orc2 = {4, 10, 8};
            int[] goblin1 = {3, 3, 4};
            int[] goblin2 = {3, 3, 4};
            int[] goblin3 = {3, 3, 4};
            int[] goblin4 = {3, 3, 4};
            int[] goblin5 = {3, 3, 4};
            int[] goblin6 = {3, 3, 4};
            int[] goblin7 = {3, 3, 4};
            int[] goblin8 = {3, 3, 4};
            int[] general1 = {2, 5, 8};
            int[] general2 = {2, 5, 8};
            int[] general3 = {2, 5, 8};
            int[] swordsman1 = {1, 4, 6};
            int[] swordsman2 = {1, 4, 6};
            int[] swordsman3 = {1, 4, 6};
            int[] swordsman4 = {1, 4, 6};
            int[] swordsman5 = {1, 4, 6};
            int[] swordsman6 = {1, 4, 6};

            //Initialize Greenskin side
            board.tiles[8][4].unit = orc1;
            board.tiles[3][8].unit = orc2;
            board.tiles[7][4].unit = goblin1;
            board.tiles[7][3].unit = goblin2;
            board.tiles[6][2].unit = goblin3;
            board.tiles[6][5].unit = goblin4;
            board.tiles[5][6].unit = goblin5;
            board.tiles[5][7].unit = goblin6;
            board.tiles[4][7].unit = goblin7;
            board.tiles[3][7].unit = goblin8;
            // Initialize Human side
            board.tiles[0][4].unit = general1;
            board.tiles[0][1].unit = general2;
            board.tiles[1][0].unit = general3;
            board.tiles[0][5].unit = swordsman1;
            board.tiles[0][3].unit = swordsman2;
            board.tiles[1][4].unit = swordsman3;
            board.tiles[1][3].unit = swordsman4;
            board.tiles[1][2].unit = swordsman5;
            board.tiles[1][1].unit = swordsman6;
    }

    private static String getUnitName(int index){
        if(index == 0) return(" ");
        if(index == 1) return("S");
        if(index == 2) return("E");
        if(index == 3) return("G");
        if(index == 4) return("O");
        return("WHAT?");
    }

    public static void writeDoubleArrayToFile(String filename, double[][] array) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                outputWriter.write(i);
                outputWriter.write(",");
                outputWriter.write(j);
                outputWriter.write(" - ");
                outputWriter.write(Double.toString(array[i][j]));
                outputWriter.newLine();
            }
        }
        outputWriter.flush();  
        outputWriter.close();  
    }
}