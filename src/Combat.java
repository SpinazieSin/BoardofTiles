import java.util.*;

public class Combat {

	// Returns if something hit or not
	public static Boolean hit(Tile attacker, Tile defender, Tile[][] board) {
		// new weapons skill is calculated based on adjecent units
		int attackerSkill = calculateWeaponSkill(attacker, board);
		int defenderSkill = calculateWeaponSkill(defender, board);
		// is hit?
		Random r = new Random();
		if (r.nextDouble() < calculateHitChange(attackerSkill, defenderSkill)) {
			return true;
		}
		return false;
	}

	// Calculates the chance someone will hit
	private static double calculateHitChange(double attackerSkill, double defenderSkill) {
		return(1/(1+Math.exp(-0.4*(attackerSkill - defenderSkill))));
	}

	// Loop over adjecent units to get new weapon skill
	private static int calculateWeaponSkill(Tile unit, Tile[][] board) {
		int weaponSkill = unit.unit[2];
		for (int neighbourCount = 0; neighbourCount < unit.neighbours.size(); neighbourCount++) {
			Integer[] neighbour = unit.neighbours.get(neighbourCount);
			// If the unit is human
			if (unit.unit[0] == 1 || unit.unit[0] == 2) {
				// Swordsman
				if (board[neighbour[0]][neighbour[1]].unit[0] == 1) {
					weaponSkill+=1;
				// General
				} else if (board[neighbour[0]][neighbour[1]].unit[0] == 2) {
					weaponSkill+=2;
				// Goblin
				} else if (board[neighbour[0]][neighbour[1]].unit[0] == 3) {
					weaponSkill-=1;
				// Orc
				} else if (board[neighbour[0]][neighbour[1]].unit[0] == 4) {
					weaponSkill-=2;
				}
			// If the unit is orc
			} else {
				// Swordsman
				if (board[neighbour[0]][neighbour[1]].unit[0] == 1) {
					weaponSkill-=1;
				// General
				} else if (board[neighbour[0]][neighbour[1]].unit[0] == 2) {
					weaponSkill-=2;
				// Goblin
				} else if (board[neighbour[0]][neighbour[1]].unit[0] == 3) {
					weaponSkill+=1;
				// Orc
				} else if (board[neighbour[0]][neighbour[1]].unit[0] == 4) {
					weaponSkill+=2;
				}
			}
		}
		return weaponSkill;
	}
}