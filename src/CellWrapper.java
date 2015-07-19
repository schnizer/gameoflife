import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author 8254649
 *
 */
public class CellWrapper extends JPanel {

	private Cell cell; //Cell the Wrapper is displaying

	/**
	 * Constructor
	 * 
	 * @param cell Cell the wrapper is displaying
	 */
	public CellWrapper(Cell cell) {
		// System.out.print("-");
		this.cell = cell;
		this.setBackground(getColorByCell());
		this.setBorder(BorderFactory.createEtchedBorder());
	}

	/**
	 * Determines the color to display
	 * 
	 * @return Returns color for the cell bound to the wrapper
	 */
	private Color getColorByCell() {
		if (cell.getState() == Cell.State.ALIVE) {

			switch (cell.getAge()) { //Cell is getting darker with increasing age
			case 0:
				return new Color(0.8f, 0.8f, 0.8f);
			case 1:
				return new Color(0.6f, 0.6f, 0.6f);
			case 2:
				return new Color(0.4f, 0.4f, 0.4f);
			case 3:
				return new Color(0.2f, 0.2f, 0.2f);
			default:
				return new Color(0f, 0.f, 0f);
			}
		} else {
			return new Color(1f, 1f, 1f); //Dead cells are white
		}
	}

	/**
	 * Updates color and size CellWrapper
	 * 
	 * @param width Set wrapper height. Necessary when window was resized
	 * @param height Set wrapper width. Necessary when window was resized
	 */
	public void updateColor(int width, int height) {
		this.setSize(width, height); //Sets new size

		setBackground(getColorByCell()); //Updates wrapper color according to cell age and state

	}
}
