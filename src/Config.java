import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Config {

	SimulationEngine.EdgeMode edgeMode;
	private final Cell[][] cells;
	private final int countLines;
	private final int countColumns;
	private final String sourceFilePath;

	public Config(SimulationEngine.EdgeMode edgeMode, String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
		this.edgeMode = edgeMode;
		this.countLines = setRowCount();
		this.countColumns = setColumnCount();
		cells = readCellsFromFile();
	}

	public SimulationEngine.EdgeMode getEdgeMode() {
		return this.edgeMode;
	}

	public String getSourceFilePath() {
		return this.sourceFilePath;
	}

	public Cell[][] getCellsFromFile() {
		return cells;
	}

	public int getCountLines() {
		return this.countLines;
	}

	public int getCountColumns() {
		return this.countColumns;
	}

	private int setRowCount() {
		int lines = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					this.sourceFilePath));
			while (reader.readLine() != null) {
				lines++;
			}

			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}

	private int setColumnCount() {
		int countRows[] = new int[this.getCountLines()];
		int count = 0;
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					this.sourceFilePath));
			try {
				while ((line = reader.readLine()) != null) {

					countRows[count] = line.length();
					if (count != 0) {
						if ((countRows[count]) != countRows[count - 1]) {

							throw new IncorrectSizeException();

						}
					}

				}
			} catch (IncorrectSizeException e) {
				//
			}

			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countRows[0];
	}

	private Cell[][] readCellsFromFile() {

		Cell[][] readCells = new Cell[this.getCountLines()][this
				.getCountColumns()];

		try {

			try {
				FileInputStream fis = new FileInputStream(this.sourceFilePath);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fis));
				String line = null;
				for (int rowNr = 0; rowNr < this.getCountLines(); rowNr++) {
					line = br.readLine();
					for (int charLocation = 0; charLocation < this
							.getCountColumns(); charLocation++) {
						readCells[rowNr][charLocation] = new Cell();

						switch (line.charAt(charLocation)) {
						case '.':
							break;
						case '*':
							readCells[rowNr][charLocation]
									.setBufferState(Cell.State.ALIVE);
							readCells[rowNr][charLocation].persistBufferState();
							break;
						default:
							throw new IncorrectCharInInput();
						}
					}
				}
				br.close();

			} catch (IncorrectCharInInput e) {
				System.out.println("incorrect char");
			}
		} catch (IOException e) {
			//
		}
		return readCells;
	}

}
