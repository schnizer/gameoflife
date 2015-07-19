/**
 * @author Andi
 *
 */
public abstract class Rule {

	/** This method calls the applyRule of every Rule.
	 * @param neighbours Array of Cells which are direct neigbours of the current Cell
	 * @param currentCell Cell in the middle of its neighbours
	 * @return Returns the next State of the current Cell depending on the outcome of the Rule
	 */
	public Cell.State apply(Cell[] neighbours, Cell currentCell){
		return applyRule(neighbours, currentCell);
	}

	/** Abstract method that is called by apply(Cell[] neighbours, Cell currentCell) to determine whether a Cell is alive in the next Generation or not. 
	 * @param neighbours Array of Cells which are direct neigbours of the current Cell
	 * @param currentCell Cell in the middle of its neighbours
	 * @return Returns next state of the currentCell
	 */
	protected abstract Cell.State applyRule(Cell[] neighbours,
			Cell currentCell);

}