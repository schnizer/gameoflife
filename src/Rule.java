public abstract class Rule {

	public Cell.State apply(Cell[] neighbours, Cell currentCell){
		return applyRule(neighbours, currentCell);
	}

	protected abstract Cell.State applyRule(Cell[] neighbours,
			Cell currentCell);

}
