import java.util.*;

public class Movement {

	public static void moveChar(Board board, int[] startPos, int[] endPos) {
		Tile t = board.tiles[startPos[0]][startPos[1]];
		int[] tempUnit = t.unit;
		int[] emptyunit = {0,0,0};
		if(!Arrays.equals(board.tiles[endPos[0]][endPos[1]].unit, emptyunit)){
			board.tiles[endPos[0]][endPos[1]].unit = tempUnit;
		} else{
			System.out.print("new location is not empty!");
		}
	}

	public static void playerMove(Board board){
		for (int j = 0; j<board.x_max+1; j++) {
     		for (int i = 0; i<board.tiles[j].length; i++) {
     			if(board.tiles[j][i] == null){
     				continue;
     			} else {
     				if(board.tiles[j][i].unit[0] != 0) askForMove(board, board.tiles[j][i]);
     			}
     		}
     	}
	}

	public static void askForMove(Board board, Tile t){
		System.out.println("please move: " + getUnitName(t) + " at " + "["+ t.x_cor + "," + t.y_cor + "]");
		System.out.print("possible moves: ");
		for(Integer[] neighbour : t.neighbours){
			System.out.print("[" + neighbour[0] + "," + neighbour[1] + "] ");
		}
		System.out.println("");
		Scanner scan = new Scanner(System.in);
		int new_x_cor = 0;
		int new_y_cor = 0;
		try {
			new_x_cor = scan.nextInt();
			new_y_cor = scan.nextInt();
		} catch(Exception e) {
			System.out.println("bad input");
		}
		int[] input = {t.x_cor, t.y_cor};
		int[] output = {new_x_cor, new_y_cor};
		moveChar(board, input, output);
	}

	private static String getUnitName(Tile t){
		if(t.unit[0] == 1) return("swordsman");
		if(t.unit[0] == 2) return("general");
		if(t.unit[0] == 3) return("goblin");
		if(t.unit[0] == 4) return("orc");
		return("empty tile");
	}

}