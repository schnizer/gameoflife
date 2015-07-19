/**
 * @author Timo
 *
 */
public class ConsoleGUI implements ifGUI {

	@Override
	public void displayArray(Cell[][] cells) {
		System.out.printf("\n\n\n");

		for (Cell[] row : cells) {
			for (Cell cell : row) {
				System.out.print(getSymbolForCell(cell)); //Prints Symbol for every cell
			}
			System.out.println(); //Prints new line after all cells in the row are printed
		}

	}

	/**
	 * Determines symbol for cell according to 
	 * 
	 * @param cell Cell to return symbol for
	 * @return returns determined symbol for cell
	 */
	private String getSymbolForCell(Cell cell) {

		if (cell.getState() == Cell.State.DEAD) {
			return " "; //Dead cells are spaces
		} else {
			switch (cell.getAge()) { //Determines symbol according to cell age
			case 0:
				return ".";
			case 1:
				return "-";
			case 2:
				return "+";
			case 3:
				return "*";
			default:
				return "#";
			}
		}

	}

}
