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
	 * Constructor
	 * 
	 * @param sourceFilePath Path to input file
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
	 * @return The path of the file which contains the initial cell pattern.
	 */
	public String getSourceFilePath() {
		return this.sourceFilePath;
	}

	/**
	 * @return the position and cells states from input file
	 */
	public Cell[][] getCellsFromFile() {
		return cells;
	}

	/**
	 * @return The number of rows in the input file
	 */
	public int getCountLines() {
		return this.countLines;
	}

	/**
	 * @return The number of columns in the source file.
	 */
	public int getCountColumns() {
		return this.countColumns;
	}

	/**
	 * Counts number of lines in the input file.
	 * 
	 * @return The number of lines in the input file.
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
	 * Counts columns of input file. 
	 * Exception is thrown if results differ.
	 * 
	 * @return The number of columns in each row in the source file.
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
			line = line.trim(); // cut break patterns at the end of each line
			countRows[count] = line.length();

			// compare current line length with the previous line length
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
	 * Reads source file and sets dead cell for '.' and alive cell for '*'.
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
		//read each line and determine position and set cell dead on '.' or alive on '*' 
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
