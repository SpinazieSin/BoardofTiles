import java.util.Random;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Learning {


	// The learning array stores the board data
	public static double[] learning = new double[999999];

	/**
	* Calculates the next best possible move based on weapon skill gain and position value
	* Value is based on the relative weapon skill that is gained by making a move, the succes
	* it has gotten from previous games by making this move, and by the possible next moves
	* that are possible from this new state.
	*/
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
			double value = 0.1 * learn + 1.0 * (reward + 0.5 * maxLearn - 0.4 * learn);
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

	/**
	* Generates a test board where moves can be simulated on to calculate
	* the value of a new position
	*/
	private static Board getNewBoard(Board boardCopy, Tile[] currentState, Tile[] newState, int race) {
		for (int moveCount = 0; moveCount < newState.length; moveCount++) {
			Tile movetile = new Tile(currentState[moveCount].xCor, currentState[moveCount].yCor, currentState[moveCount].unit, currentState[moveCount].neighbours);
			Movement.simulateMove(boardCopy, movetile, newState[moveCount]);
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

	/**
	* Get value of the current board position
	*/
	private static double learning(Tile[] state) {
		int stateValue = getPositionValue(state);
		return learning[stateValue];
	}

	/**
	* Set the value of the current board position
	*/
	static void setLearning(Tile[] state, double value) {
		int stateValue = getPositionValue(state);
		learning[stateValue] = value;
	}

	/**
	* Hash the current board position so that it can be stored in the learning array
	*/
	private static int getPositionValue(Tile[] state) {
		int index = 0;
		if (state.length > 1) {
			for (int tileCount = 0; tileCount < state.length*2; tileCount+=2) {
					index+= state[tileCount/2].yCor*((int)Math.pow(10, tileCount)) + state[tileCount/2].xCor*((int)Math.pow(10, tileCount+1));
			}
		}
		return index;
	}

	/**
	* Read a model from a file
	*/
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

    /**
    * Write the new model to a file
    */
    public static void writeDoubleArrayToFile(String filename, double[] array) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        System.out.println("Writing model to file: " + filename);
        for (int i = 0; i < array.length; i++) {
            outputWriter.write(String.valueOf(array[i]));
            outputWriter.newLine();
        }
        outputWriter.flush();  
        outputWriter.close();  
    }
}