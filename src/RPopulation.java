import java.util.Arrays;

/**
 * @author Andi
 *
 */
public class RPopulation extends Rule {

	@Override
	protected Cell.State applyRule(Cell[] neighbours, Cell currentCell) {

		long livingCellCount = Arrays.stream(neighbours)
				.filter(c -> (c.getState() == Cell.State.ALIVE)).count(); // Convert array to stream, filter out everything that isn't alive and count the occurrences.

		if (currentCell.getState() == Cell.State.ALIVE) { // Action for cells that are alive
			if (livingCellCount < 2 || livingCellCount > 3) { 
				return Cell.State.DEAD;  // Kill if over- or underpopulated
			} else {
				return Cell.State.ALIVE; // Stay alive
			}
		} else { 										 // Action for cells that are dead
			if (livingCellCount == 3) {
				return Cell.State.ALIVE; // A new cell is born
			} else {
				return Cell.State.DEAD;  // Cell stays dead
			}
		}

	}
	
}
