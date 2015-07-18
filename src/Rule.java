public abstract class Rule {

	public Cell.State apply(Cell[] neighbourhood, Cell currentCell){
		return applyRule(neighbourhood, currentCell);
	}

	protected abstract Cell.State applyRule(Cell[] neighbourhood,
			Cell currentCell);

}
