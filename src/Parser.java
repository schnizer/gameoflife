import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 8388391
 * The parser contains methods to read an input file, check correctness and return the generation zero to start the game.
 */
public class Parser {

	private final Cell[][] cells;
	private final int countLines;
	private final int countColumns;
	private final String sourceFilePath;

	/**
	 * The path of source file has to bee known.
	 * 
	 * @param sourceFilePath
	 * @throws IncorrectCharException
	 * @throws IncorrectSizeException
	 * @throws IOException
	 */
	public Parser(String sourceFilePath) throws IncorrectCharException,
			IncorrectSizeException, IOException {
		this.sourceFilePath = sourceFilePath;
		this.countLines = initializeRowCount();
		this.countColumns = initializeColumnCount();
		cells = readCellsFromFile();
	}

	/**
	 * @return The path of the file which contains the cell pattern to start.
	 */
	public String getSourceFilePath() {
		return this.sourceFilePath;
	}

	/**
	 * @return The position and kind of cells in the source file
	 */
	public Cell[][] getCellsFromFile() {
		return cells;
	}

	/**
	 * @return The number of Lines in the source file
	 */
	public int getCountLines() {
		return this.countLines;
	}

	/**
	 * @return The number of Columns in the source file.
	 */
	public int getCountColumns() {
		return this.countColumns;
	}

	/**
	 * Counts number of lines the source file contains.
	 * 
	 * @return The number of lines in the source file.
	 * @throws IOException
	 */
	private int initializeRowCount() throws IOException {
		int lines = 0;
		BufferedReader reader = new BufferedReader(new FileReader(
				this.sourceFilePath));
		while (reader.readLine() != null) {
			lines++;
		}

		reader.close();

		return lines;
	}

	/**
	 * Counts number of characters in one row of the source file. Exception is
	 * thrown on deversing line lengths.
	 * 
	 * @return The number of characters in one row of the source file.
	 * @throws IOException
	 * @throws IncorrectSizeException
	 */
	private int initializeColumnCount() throws IOException,
			IncorrectSizeException {
		int countRows[] = new int[this.getCountLines()]; // buffer for all line
															// lengths
		int count = 0;
		String line = null;
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(this.sourceFilePath));
		while ((line = reader.readLine()) != null) {
			line = line.trim(); // cut break patterns at the end of the line
			countRows[count] = line.length();

			// compare line length with the one before
			if (count != 0) {
				if ((countRows[count - 1]) != countRows[count]) {
					reader.close();
					throw new IncorrectSizeException();
				}
			}

			count++;
		}
		reader.close();
		return countRows[0];
	}

	/**
	 * Reads source file and sets dead cell for '.' and living cell for '*'.
	 * Exception is thrown on other characters
	 * 
	 * @return The an Array with living and dead cells, as described in the
	 *         source file
	 * @throws IncorrectCharException
	 */
	private Cell[][] readCellsFromFile() throws IncorrectCharException,
			IOException {

		Cell[][] readCells = new Cell[this.getCountLines()][this
				.getCountColumns()];

		FileInputStream fis = new FileInputStream(this.sourceFilePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		//read each line and determine determine local postion and set cell dead on '.' or alive on '*' 
		for (int rowNr = 0; rowNr < this.getCountLines(); rowNr++) {
			line = br.readLine();
			for (int charLocation = 0; charLocation < this.getCountColumns(); charLocation++) {
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

		return readCells;
	}

}
