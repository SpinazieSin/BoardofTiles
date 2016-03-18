/**
 * Written by Jonathan Gerbscheid and Thomas Groot
 * Jonathan-gerb@hotmail.com and thomas--g@hotmail.com
 * for datastructures project, March 2016. 
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Main {

    public static void main (String[] args) {
        int balance = 0;
        int breakCount = 0;
        try {
            Learning.readDoubleFromFile("learning.model");
        } catch (Exception e) {
        }
        int games = 1000;
        int gameMode= gameMode();
        if(gameMode == 1 || gameMode == 2 || gameMode == 3) {
            Movement.drawFrames  = true;
            games = 1;
        }
        for (int gameCount = 0; gameCount < games; gameCount++) {
            Board board = buildBoard(8, 4, 4);
            if(Movement.drawFrames){
                System.out.println("drawing frames enabled.");
                JFrame fr = new JFrame();
                try {
                    fr.setAutoRequestFocus(false);
                }catch(ArrayIndexOutOfBoundsException e) {
                }
                fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                fr.pack();
                fr.setVisible(true);
                Movement.f = fr;
            }
            String victor = "unknown, something went wrong...";
            for (int i = 0; i < 1000; i++) { // after 1000 moves something is clearly wrong
                System.out.println("-----------Human Turn-----------");
                if(gameMode == 1){
                    Movement.playerMove(board, 0);
                }
                if(gameMode == 2 ||gameMode == 3 || gameMode == 4){
                    Movement.reinforcedLearningMove(board, 0);
                    Movement.reinforcedLearningMove(board, 0);
                }
                System.out.println("-----------Greenskin Turn------------");
                if(gameMode == 1){
                    Movement.aiMove(board, 2);
                }
                if(gameMode == 2){
                    Movement.playerMove(board, 2);
                }
                if(gameMode == 3){
                    Movement.aiMove(board, 2);
                }
                if(gameMode == 4){
                    Movement.aiMove(board, 2);
                }
                breakCount++;
                if (Board.gameWon(board) == -1) {
                    // Orcs won
                    victor = "Orcs";
                    balance--;
                    break;
                }
                if (Board.gameWon(board) == 1) {
                    // Humans won
                    victor = "Humans";
                    balance++;
                    break;
                }
            }
            System.out.println("GAME - " + gameCount + " victor: " + victor);
        }
        try {
            Learning.writeDoubleArrayToFile("learning.model", Learning.learning);
        } catch (Exception e) {
        }
        System.out.println();
        System.out.println(breakCount);
        System.out.println("---------------------------------");
        System.out.println("BALANCE IS " + balance + " -----------");
        System.out.println("---------------------------------");
    }

    /**
     * Prints introductory text and reads an integer to determine 
     * the desired game mode.  
     */
    private static int gameMode(){
        System.out.println("========================================");
        System.out.println("=====Welcome to Legends of Arborea!=====");
        System.out.println("========================================");
        System.out.println("What version would you like to play?");
        System.out.println("To play against easy (random move) AI, enter: 1");
        System.out.println("To play against hard, (Reinforced learning) AI, you can only play as Orcs, enter: 2 ");
        System.out.println("To watch easy AI fight hard AI, enter: 3");
        System.out.println("To simulate 1000 matches (without visuals) of hard vs easy AI, enter: 4");
        int version = 1;
        try{
            Scanner versionreader = new Scanner(System.in);
            version = versionreader.nextInt();
        } catch(Exception e) {
            System.out.println("bad input, selecting option 1");
        }
        return(version);
    }

    /** initializes the board and adds the units.*/
    public static Board buildBoard(int xMax, int yMax, int yMin){
        Board board = new Board(xMax,yMax,yMin);
        initUnits(board);
        return(board);
    }

    /** Takes the board as input and adds units at the appropriate positions. */
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

}