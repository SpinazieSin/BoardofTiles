import java.util.*;

public class Movement {

	public static void moveChar(Board board, int[] startPos, int[] endPos) {
		Tile t = board.tiles[startPos[0]][startPos[1]];
		int[] tempUnit = t.unit;
		int[] emptyunit = {0,0,0};
		if(board.tiles[endPos[0]][endPos[1]].unit[0] != 0){
			System.out.println(board.tiles[endPos[0]][endPos[1]].unit[0]);
			System.out.println("new location is not empty!");
		} else{ 
			board.tiles[endPos[0]][endPos[1]].unit = tempUnit;
		}
	}

	public static void playerMove(Board board){
		ArrayList<Tile> charList = new ArrayList<Tile>(); // list of board locations/characters that need to be moved
		for (int j = 0; j<board.x_max+1; j++) {
     		for (int i = 0; i<board.tiles[j].length; i++) {
     			if(board.tiles[j][i] == null){
     				continue;
     			} else {
     				if(board.tiles[j][i].unit[0] != 0){
     					charList.add(board.tiles[j][i]);
     				}
     			}
     		}
     	}
     	while(charList.size() != 0){
     		System.out.println("characters to move: ");
	     	for(Tile charTile : charList){
	     		System.out.println( getUnitName(charTile) + " at " + "["+ charTile.x_cor + "," + charTile.y_cor + "]");
	     	}
	     	Scanner scan = new Scanner(System.in);
			int old_x_cor = 0;
			int old_y_cor = 0;
			int new_x_cor = 0;
			int new_y_cor = 0;
			try {
				System.out.println("please select a character to move.");	
				old_x_cor = scan.nextInt();
				old_y_cor = scan.nextInt();
				System.out.println("please select where you want to move it.");
				System.out.print("possible moves: ");
				for(Integer[] neighbour : board.tiles[old_x_cor][old_y_cor].neighbours){
					System.out.print("[" + neighbour[0] + "," + neighbour[1] + "] ");
				}
				System.out.println("");
				new_x_cor = scan.nextInt();
				new_y_cor = scan.nextInt();
			} catch(Exception e) {
				System.out.println("bad input");
			}
			int[] input = {old_x_cor, old_y_cor};
			int[] output = {new_x_cor, new_y_cor};
			moveChar(board, input, output);
			for(int i = 0; i< charList.size(); i++){
				charList.get(i);
				if(charList.get(i).x_cor == old_x_cor && charList.get(i).y_cor == old_y_cor){
					charList.remove(i);
				}
			}
		}

	}

	// public static void askForMove(Board board, Tile t){
	// 	// System.out.println("please move: " + getUnitName(t) + " at " + "["+ t.x_cor + "," + t.y_cor + "]");
	// 	// System.out.print("possible moves: ");
	// 	// for(Integer[] neighbour : t.neighbours){
	// 	// 	System.out.print("[" + neighbour[0] + "," + neighbour[1] + "] ");
	// 	// }
	// 	// System.out.println("");
	// 	System.out.print("characters to move: ");
	// 	Scanner scan = new Scanner(System.in);
	// 	int new_x_cor = 0;
	// 	int new_y_cor = 0;
	// 	try {
	// 		new_x_cor = scan.nextInt();
	// 		new_y_cor = scan.nextInt();
	// 	} catch(Exception e) {
	// 		System.out.println("bad input");
	// 	}
	// 	int[] input = {t.x_cor, t.y_cor};
	// 	int[] output = {new_x_cor, new_y_cor};
	// 	moveChar(board, input, output);
	// }

	private static String getUnitName(Tile t) {
		if(t.unit[0] == 1) return("swordsman");
		if(t.unit[0] == 2) return("general");
		if(t.unit[0] == 3) return("goblin");
		if(t.unit[0] == 4) return("orc");
		return("empty tile");
	}

}