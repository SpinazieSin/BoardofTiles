import java.util.*;

public class Movement {

	public static void moveChar(Board board, Tile tile, Tile newPos) {
		int[] emptyUnit = {0,0,0};
		// Do we move to an empty tile?
		if(board.tiles[newPos.x_cor][newPos.y_cor].unit[0] == 0){
			board.tiles[newPos.x_cor][newPos.y_cor].unit = tile.unit;
			board.tiles[tile.x_cor][tile.y_cor].unit = emptyUnit;
			System.out.println("Moved to: "+ newPos.x_cor+", "+newPos.y_cor);
		// Hit unit if tile not empty
		} else{
			System.out.println("Attacking "+getUnitName(newPos));
			if (Combat.hit(tile, newPos, board.tiles)) {
				System.out.println("Hit!");
				int[] hitUnit = board.tiles[newPos.x_cor][newPos.y_cor].unit;
				// if the unit that is hit has more than no hitpoints, do one damage
				if (hitUnit[1] > 0) hitUnit[1] = hitUnit[1]-1;
				// if unit has one hitpoint and is hit, it dies
				else {
					hitUnit = emptyUnit;
					System.out.println("it died");
				}
				board.tiles[newPos.x_cor][newPos.y_cor].unit = hitUnit;
			} else {
				System.out.println("Missed..");
			}
		}
	}

	public static void playerMove(Board board){
		ArrayList<Tile> charList = new ArrayList<Tile>(); // list of board locations/characters that need to be moved
		for (int j = 0; j<board.x_max+1; j++) {
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
			int old_x_cor = 0;
			int old_y_cor = 0;
			int new_x_cor = 0;
			int new_y_cor = 0;
			// Input loop
			while(true) {
				try {
					Main.print_board(board);
					System.out.println("characters to move: ");
					Set<Tile> charHash = new HashSet<>();
					charHash.addAll(charList);
			     	for(Tile charTile : charHash){
			     		System.out.println( getUnitName(charTile) + " at " + "["+ charTile.x_cor + "," + charTile.y_cor + "]");
			     	}
					System.out.println("Please select a character to move.");	
					String selectUnit = scan.nextLine();
					old_x_cor = Character.getNumericValue(selectUnit.charAt(0));
					old_y_cor = Character.getNumericValue(selectUnit.charAt(2));
					if (!isExistingTile(old_x_cor, old_y_cor, charList)) {
						System.out.println("Not a unit, use x and y coordinates seperated by a ,");
						continue;
					}
					System.out.println("Please select where you want to move it.");
					System.out.print("possible moves: ");
					for(Integer[] neighbour : board.tiles[old_x_cor][old_y_cor].neighbours){
						System.out.print("[" + neighbour[0] + "," + neighbour[1] + "] ");
					}
					System.out.println("");
					String newPosition = scan.nextLine();
					new_x_cor = Character.getNumericValue(selectUnit.charAt(0));
					new_y_cor = Character.getNumericValue(selectUnit.charAt(2));
					if (!isExistingNeighbour(new_x_cor, new_y_cor, board.tiles[old_x_cor][old_y_cor].neighbours)) {
						System.out.println("Not an existing tile, use x and y coordinates seperated by a ,");
						continue;
					}
					break;
				} catch(Exception e) {
					System.out.println("Bad input, please give x and y coordinates seperated by a ,");
					continue;
				}
			}
			Tile selectedUnit = board.tiles[old_x_cor][old_y_cor];
			Tile newPos = board.tiles[new_x_cor][new_y_cor];
			moveChar(board, selectedUnit, newPos);
			ArrayList<Tile> doublemove = new ArrayList<Tile>();
			for(int i = 0; i< charList.size(); i++){
				charList.get(i);
				// add the left over moves to a temporary arraylist
				if(charList.get(i).x_cor == old_x_cor && charList.get(i).y_cor == old_y_cor){
					doublemove.add(charList.get(i));
				}
			}
			// if more than one move left, change leftover move to new location and remove one move
			Boolean removeTile = true;
			if(doublemove.size() > 1) {
				for(int i = 0; i< charList.size(); i++){
					if(charList.get(i).x_cor == old_x_cor && charList.get(i).y_cor == old_y_cor && removeTile){
						charList.remove(charList.get(i));
						removeTile = false;
					}
					if(charList.get(i).x_cor == old_x_cor && charList.get(i).y_cor == old_y_cor){
						charList.get(i).x_cor = new_x_cor;
						charList.get(i).y_cor = new_y_cor;
					}
				}
			} else if(doublemove.size() == 1) {
				for(int i = 0; i< charList.size(); i++){
					if(charList.get(i).x_cor == old_x_cor && charList.get(i).y_cor == old_y_cor){
						charList.remove(charList.get(i));
					}
				}
			}
		}
	}

	private static Boolean isExistingTile(int xCor, int yCor, ArrayList<Tile> tileList) {
		for (Tile tile : tileList) {
			if (tile.x_cor == xCor && tile.y_cor == yCor) {
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

	private static String getUnitName(Tile t) {
		if(t.unit[0] == 1) return("swordsman");
		if(t.unit[0] == 2) return("general");
		if(t.unit[0] == 3) return("goblin");
		if(t.unit[0] == 4) return("orc");
		return("empty tile");
	}

}