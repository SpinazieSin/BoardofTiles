import java.util.*;

public class Movement {

	public static void moveChar(Board board, Tile tile, Tile newPos) {
		int[] emptyUnit = {0,0,0};
		// Do we move to an empty tile?
		if(board.tiles[newPos.xCor][newPos.yCor].unit[0] == 0){
			board.tiles[newPos.xCor][newPos.yCor].unit = tile.unit;
			board.tiles[tile.xCor][tile.yCor].unit = emptyUnit;
			System.out.println("Moved to: "+ newPos.xCor+", "+newPos.yCor);
		// Hit unit if tile not empty
		} else{
			System.out.println("Attacking "+getUnitName(newPos));
			if (Combat.hit(tile, newPos, board.tiles)) {
				System.out.println("Hit!");
				int[] hitUnit = board.tiles[newPos.xCor][newPos.yCor].unit;
				// if the unit that is hit has more than no hitpoints, do one damage
				if (hitUnit[1] > 0) hitUnit[1] = hitUnit[1]-1;
				// if unit has one hitpoint and is hit, it dies
				else {
					hitUnit = emptyUnit;
					System.out.println("it died");
				}
				board.tiles[newPos.xCor][newPos.yCor].unit = hitUnit;
			} else {
				System.out.println("Missed..");
			}
		}
	}

	public static void playerMove(Board board){
		ArrayList<Tile> charList = new ArrayList<Tile>(); // list of board locations/characters that need to be moved
		for (int j = 0; j<board.xMax+1; j++) {
     		for (int i = 0; i<board.tiles[j].length; i++) {
     			if(board.tiles[j][i] == null){
     				continue;
     			} else {
     				if (board.tiles[j][i].unit[0] == 1 ||
     					board.tiles[j][i].unit[0] == 2){
     					charList.add(board.tiles[j][i]);
     					charList.add(board.tiles[j][i]);
     				}
     			}
     		}
     	}
     	while(!charList.isEmpty()){
	     	Scanner scan = new Scanner(System.in);
			int oldXCor = 0;
			int oldYCor = 0;
			int newXCor = 0;
			int newYCor = 0;
			// Input loop
			while(true) {
				try {
					Main.printBoard(board);
					System.out.println("characters to move: ");
					Set<Tile> charHash = new HashSet<>();
					charHash.addAll(charList);
			     	for(Tile charTile : charHash){
			     		System.out.println( getUnitName(charTile) + " at " + "["+ charTile.xCor + "," + charTile.yCor + "]");
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

			moveChar(board, selectedUnit, newPos);

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
			System.out.println(xCor + "--------------------" + neighbour[0]);
			if (neighbour[0] == xCor && neighbour[1] == yCor) {
				return true;
			}
		}
		return false;
	}

	private static String getUnitName(Tile t) {
		if(t.unit[0] == 1) return("swordsman");
		if(t.unit[0] == 2) return("general");
		if(t.unit[0] == 3) return("goblin");
		if(t.unit[0] == 4) return("orc");
		return("empty tile");
	}

}