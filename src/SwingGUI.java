import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class SwingGUI implements ifGUI {

	JFrame frame;
	CellWrapper[][] wrappers;
	final int height;
	final int width;

	public SwingGUI(Cell[][] cells, int width, int height) {
		this.height = height;
		this.width = width;
		this.frame = new JFrame("Game of Life");
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(cells[0].length, cells.length));

		wrappers = new CellWrapper[cells.length][cells[0].length];

		for (int i = 0; i < wrappers.length; i++) {
			for (int j = 0; j < wrappers.length; j++) {
				wrappers[i][j] = new CellWrapper(cells[i][j]);
				frame.add(wrappers[i][j]);
			}
		}
	}

	@Override
	public void displayArray(Cell[][] cells) {
		int cellWidth = frame.getWidth() / cells[0].length;
		int cellHeight = frame.getHeight() / cells.length;

		for (CellWrapper[] row : wrappers) {
			for (CellWrapper wrapper : row) {
				wrapper.updateColor(cellWidth, cellHeight);
			}
		}
		frame.validate();
	}

}
