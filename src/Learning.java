import java.util.*;

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
			// double learn = learning(newStates[strategyCount]);
			// double maxLearn = maxLearning(boardCopy, newStates[strategyCount], race);
			double learn = learning(states);
			double maxLearn = maxLearning(newStates[strategyCount]);

			double reward = (double)Board.gameWon(boardCopy)*100;
			reward += ( Combat.relativeWeaponSkill(boardCopy) - currentWeaponSkill );
			// 0.1 and 0.9 are learning parameters
			double value = learn + 0.1 * (reward + 0.9 * maxLearn - learn);
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

	private static double maxLearning(Tile[] nextState) {
		double value = learning(nextState);
		return value;
	}
	// Max value of next state
	// private static double maxLearning(Board boardCopy, Tile[] state, int race) {
	// 	double value = Double.MIN_VALUE;
	// 	Tile[][] nextStates = Movement.getPossibleStates(boardCopy, state, race);
	// 	for (int i = 0; i < nextStates.length; i++) {
	// 		Tile[] nextState = nextStates[i];
	// 		double newValue = learning(nextStates[i]);
	// 		if (newValue > value) {
	// 			value = newValue;				
	// 		}
	// 	}
	// 	return value;
	// }

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
}