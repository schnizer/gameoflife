import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {

	private final Cell[][] cells;
	private final int countLines;
	private final int countColumns;
	private final String sourceFilePath;

	public Parser(String sourceFilePath)
			throws IncorrectCharException, IncorrectSizeException {
		this.sourceFilePath = sourceFilePath;
		this.countLines = setRowCount();
		this.countColumns = setColumnCount();
		cells = readCellsFromFile();
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

	private int setColumnCount() throws IncorrectSizeException {
		int countRows[] = new int[this.getCountLines()];
		int count = 0;
		String line = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.sourceFilePath));

			while ((line = reader.readLine()) != null) {
				line = line.trim();
				countRows[count] = line.length();
				if (count != 0) {
					if ((countRows[count - 1]) != countRows[count]) {
						throw new IncorrectSizeException();
					}
				}
				count += 1;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// Cannot close Reader
				e.printStackTrace();
			}
		}
		return countRows[0];
	}

	private Cell[][] readCellsFromFile() throws IncorrectCharException {

		Cell[][] readCells = new Cell[this.getCountLines()][this
				.getCountColumns()];

		try {
			FileInputStream fis = new FileInputStream(this.sourceFilePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
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
						throw new IncorrectCharException();
					}
				}
			}
			br.close();
		} catch (IOException e) {
			//
		}
		return readCells;
	}

}
