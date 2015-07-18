public class SimulationEngine {

	static enum EdgeMode {								//New datatype EdgeMode for framemode (TORUS, BORDERED).
		TORUS, BORDERED
	};

	private final EdgeMode mode;						//Declaration of an EdgeMode called mode.

	private Cell[][] cells;								//Declaration of an 2d array called cells.
	private Rule[] rules;								//Declaration of an array called rules.
/**
 * 
 * @param mode
 * @param rules
 * @param cells
 */
	public SimulationEngine(EdgeMode mode, Rule[] rules, Cell[][] cells) {

		this.mode = mode;
		this.rules = rules;

		this.cells = cells;
	}
	

	public Cell[][] getCells() {
		return cells;
	}
/**
 * 
 * @param x
 * @param y
 * @param state
 */
	public void setCellAtTo(int x, int y, Cell.State state) {
		this.cells[y][x].setBufferState(state);
		this.cells[y][x].persistBufferState();
	}

	public void tick() {

			Cell[] neighbours = new Cell[8];

			// Set Buffer State for each cell
			for (int y = 0; y < cells.length; y++) {
				for (int x = 0; x < cells[0].length; x++) {

					neighbours[0] = getCell(x - 1, y - 1);
					neighbours[1] = getCell(x, y - 1);
					neighbours[2] = getCell(x + 1, y - 1);
					neighbours[3] = getCell(x - 1, y);
					neighbours[4] = getCell(x + 1, y);
					neighbours[5] = getCell(x - 1, y + 1);
					neighbours[6] = getCell(x, y + 1);
					neighbours[7] = getCell(x + 1, y + 1);

					for (Rule r : rules) {
						Cell.State newState = r.apply(neighbours,
								cells[y][x]);
						cells[y][x].setBufferState(newState);
					}

				}
			}

			// persist buffer state
			for (int y = 0; y < cells.length; y++) {
				for (int x = 0; x < cells[0].length; x++) {
					cells[y][x].persistBufferState();
					cells[y][x].age();
				}
			}
	}
/**
 * 
 * @param x
 * @param y
 * @return
 */
	private Cell getCell(int x, int y) {
		if (x < 0) {
			if (this.mode == EdgeMode.TORUS) {
				x = this.cells[0].length - 1;
			} else {
				return new Cell();
			}
		} else if (x >= this.cells[0].length) {
			if (this.mode == EdgeMode.TORUS) {
				x = 0;
			} else {
				return new Cell();
			}
		}

		if (y < 0) {
			if (this.mode == EdgeMode.TORUS) {
				y = this.cells.length - 1;
			} else {
				return new Cell();
			}
		} else if (y >= this.cells.length) {
			if (this.mode == EdgeMode.TORUS) {
				y = 0;
			} else {
				return new Cell();
			}
		}

		return this.cells[y][x];
	}
}
