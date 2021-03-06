import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * @author 8254649
 * Timo Schnizer
 *
 */
public class SwingGUI implements ifGUI {
	
	private final int INITIAL_WINDOW_WIDTH = 800;

	JFrame frame;
	CellWrapper[][] wrappers;
	final int height;
	final int width;

	/**
	 * Constructor
	 * 
	 * @param cells Array of cells to display initially
	 */
	public SwingGUI(Cell[][] cells) {
		this.height = (int)(((double)cells.length/(double)cells[0].length) * INITIAL_WINDOW_WIDTH);
		this.width = (INITIAL_WINDOW_WIDTH);
		this.frame = new JFrame("Game of Life");
		frame.setSize(new Dimension(this.width, this.height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(cells.length, cells[0].length));

		wrappers = new CellWrapper[cells.length][cells[0].length]; //Creates new array of CelWrappers the same size as the given array of cells

		for (int row = 0; row < wrappers.length; row++) {
			for (int column = 0; column < wrappers[0].length; column++) {
				wrappers[row][column] = new CellWrapper(cells[row][column]); //Assigns according cell to each wrapper
				frame.add(wrappers[row][column]);
			}
		}
	}

	@Override
	public void displayArray(Cell[][] cells) {
		int cellWidth = frame.getWidth() / cells[0].length;
		int cellHeight = frame.getHeight() / cells.length;

		for (CellWrapper[] row : wrappers) {
			for (CellWrapper wrapper : row) {
				wrapper.updateColor(cellWidth, cellHeight); //Displays each CellWrapper
			}
		}
		frame.validate();
	}

}
