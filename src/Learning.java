import java.util.*;

public class Learning {


	// Board data
	public static double[][] learning = new double[9999][9999];

	public static Tile[] Learning(Board board, Tile[] states, Tile[][] newStates, int race) {
		Double previousValue = 0.0;
		Double currentWeaponSkill = Combat.relativeWeaponSkill(board);
		Tile[] finalState = new Tile[states.length];
		for (int strategyCount = 0; strategyCount < newStates.length; strategyCount++) {
			Board boardCopy = board;
			for (int moveCount = 0; moveCount < newStates[strategyCount].length; moveCount++) {
				Movement.moveChar(boardCopy, states[moveCount], newStates[strategyCount][moveCount]);
			}
			// Consider next state
			double learn = learning(states, newStates[strategyCount]);
			double maxLearn = maxLearning(boardCopy, newStates[strategyCount], race);

			double reward = (double)Board.gameWon(boardCopy)*100;
			reward += ( Combat.relativeWeaponSkill(boardCopy) - currentWeaponSkill );
			// 0.1 and 0.9 are learning parameters
			double value = learn + 0.1 * (reward + 0.9 * maxLearn - learn);
			if (value > previousValue) {
				finalState = newStates[strategyCount];
				previousValue = value;
			}
			setLearning(states, newStates[strategyCount], value);
		}
		if (previousValue == 0.0) {
			Random random = new Random();
			finalState = newStates[random.nextInt(newStates.length)];
		}
		return finalState;
	}
	
	// Max value of next state
	private static double maxLearning(Board boardCopy, Tile[] state, int race) {
		double value = Double.MIN_VALUE;
		// getPossibleBoardStates TODO
		Tile[][] nextStates = Movement.getPossibleStates(boardCopy, state, race);
		for (int i = 0; i < nextStates.length; i++) {
			Tile[] nextState = nextStates[i];
			double newValue = learning(state, nextStates[i]);
			if (newValue > value) {
				value = newValue;				
			}
		}
		return value;
	}

	private static double learning(Tile[] state, Tile[] action) {
		int stateValue = getPositionValue(state);
		int actionValue = getPositionValue(action);
		return learning[stateValue][actionValue];
	}

	static void setLearning(Tile[] state, Tile[] action, double value) {
		int stateValue = getPositionValue(state);
		int actionValue = getPositionValue(action);
		learning[stateValue][actionValue] = value;
	}

	private static int getPositionValue(Tile[] states) {
		int index;
		if (states.length > 1) {
			index = states[0].yCor+states[0].xCor*10+states[1].yCor*100+states[1].xCor*100;
		} else {
			index = states[0].yCor+states[0].xCor*10;
		}
		return index;
	}
}