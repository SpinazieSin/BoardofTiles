import java.util.*;
import java.io.*;

public class Main {

    public static void main (String[] args) {
        int balance = 0;
        // Gameloop
        int breakCount = 0;
        for (int gameCount = 0; gameCount < 1000; gameCount++) {
            Board board = buildBoard(8, 4, 4);
            for (int i = 0; i < 100; i++) {
                // printBoard(board);
                // System.out.println("-----------Human Turn-----------");
                Movement.reinforcedLearningMove(board, 0);
                Movement.reinforcedLearningMove(board, 0);
                // Movement.aiMove(board, 0);
                // Movement.playerMove(board);
                // System.out.println("-----------Greenskin------------");
                Movement.aiMove(board, 2);
                try {
                    Thread.sleep(0);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                if (Board.gameWon(board) == -1) {
                    // Orcs won
                    balance-=1;
                    break;
                }
                if (Board.gameWon(board) == 1) {
                    // Humans won
                    balance+=1;
                    break;
                }
            }
            breakCount++;
            if (breakCount % 100 == 0) {
                System.out.println("Calculating.. " +breakCount);
            }
        }
        // try{
        //     writeDoubleArrayToFile("twoPositions.model", Learning.learning);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
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