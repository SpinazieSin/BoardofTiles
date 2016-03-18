import java.util.*;
import java.io.*;
public class Learning {


	// Board data
	public static double[] learning = new double[999999];

	public static Tile[] Learning(Board board, Tile[] states, Tile[][] newStates, int race) {
		Double previousValue = 0.0;
		Double currentWeaponSkill = Combat.relativeWeaponSkill(board);
		Tile[] finalState = new Tile[states.length];
		for (int strategyCount = 0; strategyCount < newStates.length; strategyCount++) {
			Board boardCopy = Board.deepCloneBoard(board);
			boardCopy = getNewBoard(boardCopy, states, newStates[strategyCount], race);
			// Consider next state
			double learn = learning(newStates[strategyCount]);
			double maxLearn = maxLearning(boardCopy, newStates[strategyCount], race);

			double reward = (double)Board.gameWon(boardCopy)*20;
			reward += ( Combat.relativeWeaponSkill(boardCopy) - currentWeaponSkill );
			// The multipliers are learning parameters
			double value = learn + 1.0 * (reward + 0.5 * maxLearn - 0.5 * learn);
			if (value > previousValue) {
				finalState = newStates[strategyCount];
				previousValue = value;
			}
			setLearning(newStates[strategyCount], value);
		}
		if (previousValue == 0.0) {
			Random random = new Random();
			finalState = newStates[random.nextInt(newStates.length)];
		}
		return finalState;
	}

	private static Board getNewBoard(Board boardCopy, Tile[] currentState, Tile[] newState, int race) {
		for (int moveCount = 0; moveCount < newState.length; moveCount++) {
			Tile movetile = new Tile(currentState[moveCount].xCor, currentState[moveCount].yCor, currentState[moveCount].unit, currentState[moveCount].neighbours);
			Movement.moveAiChar(boardCopy, movetile, newState[moveCount]);
		}
		return boardCopy;
	}

	// How much value can i get from the next move?
	private static double maxLearning(Board boardCopy, Tile[] state, int race) {
		Tile[][] nextStateList = Movement.getPossibleStates(boardCopy, state, race);
		double value = 0.0;
		for (int strategyCount = 0; strategyCount < nextStateList.length; strategyCount++) {
			double newValue = learning(nextStateList[strategyCount]);
			if (newValue > value) {
				value = newValue;
			}
		}
		return value;
	}

	private static double learning(Tile[] state) {
		int stateValue = getPositionValue(state);
		return learning[stateValue];
	}

	static void setLearning(Tile[] state, double value) {
		int stateValue = getPositionValue(state);
		learning[stateValue] = value;
	}

	private static int getPositionValue(Tile[] state) {
		int index = 0;
		if (state.length > 1) {
			for (int tileCount = 0; tileCount < state.length*2; tileCount+=2) {
					index+= state[tileCount/2].yCor*((int)Math.pow(10, tileCount)) + state[tileCount/2].xCor*((int)Math.pow(10, tileCount+1));
			}
		}
		return index;
	}

	public static void readDoubleFromFile(String filename) throws IOException {
        BufferedReader fileReader = null;
        fileReader = new BufferedReader(new FileReader(filename));
        int arrayIndex = 0;
		System.out.println("Reading model from file: " + filename);
        while(true) {
        	String newLine = fileReader.readLine();
        	if (newLine == null) break;
        	learning[arrayIndex] = Double.parseDouble(newLine);
        }
        fileReader.close();  
    }
}