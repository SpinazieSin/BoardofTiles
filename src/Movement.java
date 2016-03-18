import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import drawing.Hexagon;
import drawing.Main;
import javax.swing.JFrame;

public class Movement {

	public static JFrame f;
	public static boolean drawFrames = false;
	private static Scanner scan;

	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int random = rand.nextInt((max - min) + 1) + min;
	    return random;
	}

	public static void moveChar(Board board, Tile tile, Tile newPos) {
		int[] emptyUnit = {0,0,0};
		// Do we move to an empty tile?
		if (board.tiles[newPos.xCor][newPos.yCor].unit[0] == 0){
			newPos.unit = emptyUnit;
			board.tiles[newPos.xCor][newPos.yCor].unit = tile.unit;
			board.tiles[tile.xCor][tile.yCor].unit = newPos.unit;
			if(drawFrames) System.out.println("Moved to: "+ newPos.xCor+", "+newPos.yCor);
		// Hit unit if tile not empty
		} else { 
			if(drawFrames) System.out.println(getUnitName(tile) + " is attacking "+getUnitName(newPos));
			if (Combat.hit(tile, newPos, board.tiles)) {
				if(drawFrames) System.out.println("Hit!");
				// if the unit that is hit has more than no hitpoints, do one damage
				if (board.tiles[newPos.xCor][newPos.yCor].unit[1] > 1) {
					board.tiles[newPos.xCor][newPos.yCor].unit[1]--;
				} else {
				// if unit has one hitpoint and is hit, it dies
					board.tiles[newPos.xCor][newPos.yCor].unit = emptyUnit;
					if(drawFrames) System.out.println("it died");
				}
			} else {
				if(drawFrames) System.out.println("Missed..");
			}
		}
	}

	public static Tile moveAiChar(Board board, Tile tile, Tile newPos) {
		int[] emptyUnit = {0,0,0};
		// Do we move to an empty tile?
		if(board.tiles[newPos.xCor][newPos.yCor].unit[0] == 0){
			board.tiles[newPos.xCor][newPos.yCor].unit = tile.unit;
			board.tiles[tile.xCor][tile.yCor].unit = emptyUnit;
			if(drawFrames) System.out.println("Moved to: "+ newPos.xCor+", "+newPos.yCor);
			return newPos;
		// Hit unit if tile not empty
		} else if (newPos.xCor != tile.xCor && newPos.yCor != tile.yCor) {
			if(drawFrames) System.out.println(getUnitName(tile) + " " + tile.xCor + "," + tile.yCor + " is attacking " + getUnitName(newPos) + " " + newPos.xCor + "," + newPos.yCor);
			if (Combat.hit(tile, newPos, board.tiles)) {
				if(drawFrames) System.out.println("Hit!");
				// if the unit that is hit has more than no hitpoints, do one damage
				if (board.tiles[newPos.xCor][newPos.yCor].unit[1] > 1) {
					board.tiles[newPos.xCor][newPos.yCor].unit[1]--;
				} else {
					// if unit has one hitpoint and is hit, it dies
					board.tiles[newPos.xCor][newPos.yCor].unit = emptyUnit;
					if(drawFrames) System.out.println("it died");
				}
			} else {
				if(drawFrames) System.out.println("Missed..");
			}
			return tile;
		}
		return tile;
	}

	public static Tile simulateMove(Board board, Tile tile, Tile newPos) {
		int[] emptyUnit = {0,0,0};
		// Do we move to an empty tile?
		if(board.tiles[newPos.xCor][newPos.yCor].unit[0] == 0){
			board.tiles[newPos.xCor][newPos.yCor].unit = tile.unit;
			board.tiles[tile.xCor][tile.yCor].unit = emptyUnit;
			return newPos;
		// Hit unit if tile not empty
		} else {
			return newPos;
		}
	}

	public static void aiMove(Board board, int race) {
		// race = 0 means humans, race = 2 means greenskins
		// list of board locations/characters that need to be moved
		ArrayList<Tile> charList = new ArrayList<Tile>();
		for (int j = 0; j<board.xMax+1; j++) {
     		for (int i = 0; i<board.tiles[j].length; i++) {
     			if(board.tiles[j][i] == null){
     				continue;
     			} else {
     				if (board.tiles[j][i].unit[0] == race + 1 ||
     					board.tiles[j][i].unit[0] == race + 2){
     					Tile movetile = new Tile(board.tiles[j][i].xCor, board.tiles[j][i].yCor, board.tiles[j][i].unit, board.tiles[j][i].neighbours);
     					charList.add(movetile);
     				}
     			}
     		}
     	}
	    while (!charList.isEmpty()) {
	    	Tile randomUnit = charList.get(randInt(0, charList.size()-1));
	    	charList.remove(randomUnit);
	     	for (int moveCount = 0; moveCount < 2; moveCount++) {
	     		int neighbourListSize = randomUnit.neighbours.size()-1;
	     		Tile newPos = randomUnit;
	     		for (int neighbourCount = 0; neighbourCount < neighbourListSize; neighbourCount++) {
	     			Integer[] newCors = randomUnit.neighbours.get(randInt(0, neighbourListSize));
	     			if (board.tiles[newCors[0]][newCors[1]].unit[0] != race + 1 &&
	     				board.tiles[newCors[0]][newCors[1]].unit[0] != race + 2 &&
	     				board.tiles[newCors[0]][newCors[1]].unit[0] != 0) {
	     				newPos = board.tiles[newCors[0]][newCors[1]];
	     				break;
	     			}
	     			if (board.tiles[newCors[0]][newCors[1]].unit[0] != race + 1 &&
	     				board.tiles[newCors[0]][newCors[1]].unit[0] != race + 2) {
	     				newPos = board.tiles[newCors[0]][newCors[1]];
	     			}
	     		}
	     		if (newPos != randomUnit) {
	     			randomUnit = moveAiChar(board, randomUnit, newPos);
	     			if(drawFrames){
						Integer[][][] newdata = boardDrawData(board);
						f.setContentPane(new drawing.Main(newdata));
				        f.pack();
				        f.setVisible(true);
				    }
                    try {
	                    if(drawFrames) Thread.sleep(300);
	                } catch(InterruptedException ex) {
	                    Thread.currentThread().interrupt();
	                }
	     		}
	     	}
	    }
	}

	public static void reinforcedLearningMove(Board board, int race) {
		ArrayList<Tile> charList = new ArrayList<Tile>();
		for (int j = 0; j<board.xMax+1; j++) {
     		for (int i = 0; i<board.tiles[j].length; i++) {
     			if(board.tiles[j][i] == null){
     				continue;
     			} else {
     				if (board.tiles[j][i].unit[0] == race + 1 ||
     					board.tiles[j][i].unit[0] == race + 2){
     					Tile movetile = new Tile(board.tiles[j][i].xCor, board.tiles[j][i].yCor, board.tiles[j][i].unit, board.tiles[j][i].neighbours);
     					charList.add(movetile);
     				}
     			}
     		}
     	}
     	Tile[] unitsToMove;
     	while (!charList.isEmpty()) {
     		int characterAmount = charList.size();
     		if (characterAmount > 4) {
     			unitsToMove = new Tile[4];
     		}
     		else {
     			unitsToMove = new Tile[characterAmount];
     		}
     		for (int unitCount = 0; unitCount < unitsToMove.length; unitCount++) {
				characterAmount-=1;
     			unitsToMove[unitCount] = charList.get(characterAmount);
     			charList.remove(characterAmount);
			}
			createNewMove(board, unitsToMove, race);
		}
	}

	public static void createNewMove(Board board, Tile[] unitsToMove, int race) {
		Tile[][] nextStates = getPossibleStates(board, unitsToMove, race);
		// Board boardCopy = Board.deepCloneBoard(board);
		Tile[] nextState = Learning.Learning(board, unitsToMove, nextStates, race);
		for (int unitCount = 0; unitCount < unitsToMove.length; unitCount++){
			Tile movetile = new Tile(unitsToMove[unitCount].xCor, unitsToMove[unitCount].yCor, unitsToMove[unitCount].unit, unitsToMove[unitCount].neighbours);
			moveAiChar(board, movetile, nextState[unitCount]);
		    try {
                if(drawFrames) Thread.sleep(300);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
			if(drawFrames){
				Integer[][][] newdata = boardDrawData(board);
				f.setContentPane(new drawing.Main(newdata));
		        f.pack();
		        f.setVisible(true);
		    }
		}
	}

	public static Tile[][] getPossibleStates(Board board, Tile[] state, int race) {
		Tile[][] nextStateList = new Tile[400][state.length];
		for (int strategyCount = 0; strategyCount < 400; strategyCount++) {
			Board boardCopy = new Board(8,4,4);
			boardCopy = Board.deepCloneBoard(board);
			Tile[] nextState = getNextStates(boardCopy, state, race);
			for (int tilesInState = 0; tilesInState < nextState.length; tilesInState++) {
				if(nextState[tilesInState].unit[0] != 0 && 
					nextState[tilesInState].unit[0] != race + 1	&&
					nextState[tilesInState].unit[0] != race + 2) {
					nextStateList = new Tile[1][nextState.length];
					nextStateList[0] = nextState;
					return nextStateList;
				}
			}
			nextStateList[strategyCount] = nextState;
		}
		return nextStateList;
	}

	public static Tile[] getNextStates(Board boardCopy, Tile[] state, int race) {
		Random random = new Random();
		Tile[] nextStates = new Tile[state.length];
		for (int stateCount = 0; stateCount < state.length; stateCount++) {
			Tile currentState = state[stateCount];
			ArrayList<Tile> nextStateCandidates = new ArrayList<Tile>();
			for (Integer[] neighbour : currentState.neighbours) {
				Tile neighbourTile = boardCopy.tiles[neighbour[0]][neighbour[1]];
				if(neighbourTile.unit[0] != 0 && 
					neighbourTile.unit[0] != race + 1 &&
					neighbourTile.unit[0] != race + 2) {
					nextStateCandidates = new ArrayList<Tile>();
					nextStateCandidates.add(neighbourTile);
					break;
				}
				if (neighbourTile.unit[0] != race + 1 &&
					neighbourTile.unit[0] != race + 2) {
					nextStateCandidates.add(neighbourTile);
				}
			}
			Tile nextState;
			if (nextStateCandidates.size() == 1) {
				nextState = nextStateCandidates.get(0);
			} else if (!nextStateCandidates.isEmpty()) {
				nextState = nextStateCandidates.get(random.nextInt(nextStateCandidates.size()));
			} else {
				nextState = currentState;
			}
			Tile movetile = new Tile(currentState.xCor, currentState.yCor, currentState.unit, currentState.neighbours);
			nextStates[stateCount] = simulateMove(boardCopy, movetile, nextState);
		}
		return nextStates;
	}

	public static void playerMove(Board board, int race){
		// list of board locations/characters that need to be moved
		ArrayList<Tile> charList = new ArrayList<Tile>();
		for (int j = 0; j<board.xMax+1; j++) {
     		for (int i = 0; i<board.tiles[j].length; i++) {
     			if(board.tiles[j][i] == null){
     				continue;
     			} else {
     				if (board.tiles[j][i].unit[0] == race + 1 ||
     					board.tiles[j][i].unit[0] == race + 2){
     					Tile movetile = new Tile(board.tiles[j][i].xCor, board.tiles[j][i].yCor, board.tiles[j][i].unit, board.tiles[j][i].neighbours);
     					charList.add(movetile);
     					charList.add(movetile);
     				}
     			}
     		}
     	}
     	while(!charList.isEmpty()){
     		Integer[][][] newdata = boardDrawData(board);
			f.setContentPane(new drawing.Main(newdata));
	        f.pack();
	        f.setVisible(true);
	     	scan = new Scanner(System.in);
			int oldXCor = 0;
			int oldYCor = 0;
			int newXCor = 0;
			int newYCor = 0;
			// Input loop
			while(true) {
				try {
					// Main.printBoard(board);
					System.out.println("characters to move: ");
					Set<Tile> charHash = new HashSet<>();
					charHash.addAll(charList);
			     	for(Tile charTile : charHash){
			     		System.out.println( getUnitName(charTile) + " at " + "["+ charTile.xCor + "," + charTile.yCor + "]" + " HP: " + board.tiles[charTile.xCor][charTile.yCor].unit[1]);
			     	}
					System.out.println("Please select a character to move.");	
					String selectUnit = scan.nextLine();
					oldXCor = Character.getNumericValue(selectUnit.charAt(0));
					oldYCor = Character.getNumericValue(selectUnit.charAt(2));
					if (!isExistingTile(oldXCor, oldYCor, charList)) {
						System.out.println("Not a unit, use x and y coordinates seperated by a ,");
						continue;
					}
					System.out.println("Please select where you want to move it.");
					System.out.print("possible moves: ");
					for(Integer[] neighbour : board.tiles[oldXCor][oldYCor].neighbours){
						System.out.print("[" + neighbour[0] + "," + neighbour[1] + "] ");
					}
					System.out.println("");
					String newPosition = scan.nextLine();
					newXCor = Character.getNumericValue(newPosition.charAt(0));
					newYCor = Character.getNumericValue(newPosition.charAt(2));
					if (!isExistingNeighbour(newXCor, newYCor, board.tiles[oldXCor][oldYCor].neighbours)) {
						System.out.println("Not an existing tile, use x and y coordinates seperated by a ,");
						continue;
					}
					break;
				} catch(Exception e) {
					System.out.println("Bad input, please give x and y coordinates seperated by a ,");
					continue;
				}
			}
			Tile selectedUnit = board.tiles[oldXCor][oldYCor];
			Tile newPos = board.tiles[newXCor][newYCor];

			moveAiChar(board, selectedUnit, newPos);

			ArrayList<Tile> doublemovecheck = new ArrayList<Tile>();
			for(int i = 0; i< charList.size(); i++){
				charList.get(i);
				// add the left over moves to a temporary arraylist
				if(charList.get(i).xCor == oldXCor && charList.get(i).yCor == oldYCor){
					doublemovecheck.add(charList.get(i));
				}
			}
			// if more than one move left, change leftover move to new location and remove one move
			Boolean removeTile = true;
			if(doublemovecheck.size() > 1) {
				for(int i = 0; i< charList.size(); i++){
					if(charList.get(i).xCor == oldXCor && charList.get(i).yCor == oldYCor && removeTile){
						charList.remove(charList.get(i));
						removeTile = false;
					}
					if(board.tiles[charList.get(i).xCor][charList.get(i).yCor].unit[0] == 0){
						if(charList.get(i).xCor == oldXCor && charList.get(i).yCor == oldYCor){
							charList.get(i).xCor = newXCor;
							charList.get(i).yCor = newYCor;
						}
					}
				}
			} else if(doublemovecheck.size() == 1) {
				for(int i = 0; i< charList.size(); i++){
					if(charList.get(i).xCor == oldXCor && charList.get(i).yCor == oldYCor){
						charList.remove(charList.get(i));
					}
				}
			}
		}
	}

	private static Boolean isExistingTile(int xCor, int yCor, ArrayList<Tile> tileList) {
		for (Tile tile : tileList) {
			if (tile.xCor == xCor && tile.yCor == yCor) {
				return true;
			}
		}
		return false;
	}

	private static Boolean isExistingNeighbour(int xCor, int yCor, ArrayList<Integer[]> neighbourList) {
		for (Integer[] neighbour: neighbourList) {
			if (neighbour[0] == xCor && neighbour[1] == yCor) {
				return true;
			}
		}
		return false;
	}

	public static String getUnitName(Tile t) {
		if(t.unit[0] == 1) return("swordsman");
		if(t.unit[0] == 2) return("general");
		if(t.unit[0] == 3) return("goblin");
		if(t.unit[0] == 4) return("orc");
		return("empty tile");
	}

	public static Integer[][][] boardDrawData(Board board){
        Integer[][][] data = new Integer[9][9][3]; 
        for (int j = 0; j<board.xMax+1; j++) {
            for (int i = 0; i<board.tiles[j].length; i++) {
                if(board.tiles[j][i] == null){
                    continue;
                } else {
                    if (board.tiles[j][i].unit[0] == 1){
                        data[j][i][0] = 1;
                        data[j][i][1] = board.tiles[j][i].unit[1];
                        data[j][i][2] = 6;
                    }else if(board.tiles[j][i].unit[0] == 2){
                        data[j][i][0] = 2;
                        data[j][i][1] = board.tiles[j][i].unit[1];
                        data[j][i][2] = 8;
                    }else if(board.tiles[j][i].unit[0] == 3){
                        data[j][i][0] = 3;
                        data[j][i][1] = board.tiles[j][i].unit[1];
                        data[j][i][2] = 4;
                    }else if(board.tiles[j][i].unit[0] == 4){
                        data[j][i][0] = 4;
                        data[j][i][1] = board.tiles[j][i].unit[1];
                        data[j][i][2] = 8;
                    } else {
                        data[j][i][0] = 0;
                        data[j][i][1] = 0;
                        data[j][i][2] = 0;
                    }
                }
            }
        }
        return data;
    }
}