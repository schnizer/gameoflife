import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * Main class of the project
 * @author 4434822
 *
 */
public class GameOfLife {

	/**
	 * Entrypoint of this Application. Run this to start GameOfLife.
	 * 
	 * Necessary parameters: inputFileName: ASCII-text-file that contains a starting-condition. (. is a cell that is dead, * is a cell that is alive in the first generation)
	 * 										Also this file describes the complete field of the Application.
	 * 						 sleepTime:		Amount of time, the application waits before computing the next generation of cells.
	 * 						 gameMode:		Mode of the application, can be "torus" or "bordered". In bordered-mode the surrounding cells are estimated dead, in torus-mode the field is "infinite". 
	 * 										(Cells on the right of the field are considered to be neighbours of the leftmost ones, etc.)
	 * 						 uiMode:		Determines which UI is used. Can be either "gui" or "cli". "gui" starts a graphical-interface, "cli" a text-based one.
	 * 
	 * Usage: GameOfLife inputFileName sleepTime gameMode uiMode
	 * Example: GameOfLife D:\file2.txt 300 torus gui
	 * 
	 * @param args Console parameters for the start of the GameOfLife-Application
	 */
	public static void main(String[] args) {
		
		if(args.length != 4) { // Abort run if invalid number of parameters
			printErrorMessageAndExit("Invalid number of input parameters. Should be four. For example: GameOfLive /home/user/inputfile.txt 300 torus gui");
		}
		// initialize parameter-variables
		String inputFilePath = null; 
		int sleepTime = 0;
		SimulationEngine.EdgeMode edgeMode = null;
		String guiMode = null;
		
		try { // determine, if the given path is a valid one
			Paths.get(args[0]);
			inputFilePath = args[0];
		} catch (InvalidPathException ipe) {
			printErrorMessageAndExit("Inputfile does not seem to be a vaild path.");
		}
		
		try { // determine, if the given sleeptime is a number that can be converted to a integer
			sleepTime = Integer.parseInt(args[1]);
		} catch (NumberFormatException nfe) {
			printErrorMessageAndExit("Sleeptime does not seem to be a number.");
		}
		
		if (args[2].equalsIgnoreCase("torus") || args[2].equalsIgnoreCase("bordered")) { //test, if gameMode is one of the two allowed ones
			edgeMode = parseEdgeMode(args[2].toLowerCase());
		} else {
			printErrorMessageAndExit("Wrong inputparameters: Mode must be \"torus\" or \"bordered\"");
		}
		if (args[3].equalsIgnoreCase("cli") || args[3].equalsIgnoreCase("gui")) { //test, if uiMode is one of the two allowd ones
			guiMode = args[3].toLowerCase();
		} else {
			printErrorMessageAndExit("Wrong inputparameters: UI must be \"cli\" or \"gui\"");
		}

		Rule[] rules = { new RPopulation() }; // create a new rule of type "RPopulation" (this is the standard game of life ruleset.)
		Parser parser = null;				  // Declare Parser object, this object is used to parse the input file into an Cell-Array. 
		try {
			parser = new Parser(inputFilePath);
		} catch (IncorrectCharException e) { // Catch possible errors, print error-message and exit.
			printErrorMessageAndExit("Unexpected character in inputfile.");
		} catch (IncorrectSizeException e) {
			printErrorMessageAndExit("Inputfile not rectangular.");
		} catch (FileNotFoundException e) {
			printErrorMessageAndExit("Inputfile not found.");
		} catch (IOException e) {
			printErrorMessageAndExit("An error occurred when opening inputfile.");
		}

		SimulationEngine engine = new SimulationEngine(edgeMode, rules,
				parser.getCellsFromFile()); // create SimulationEngine and pass edgeMode, rules, and initial Cell-Array

		ifGUI gui = null; 
		if (guiMode.equals("gui")) { // set UI to either CLI-Mode or GUI-Mode
			gui = new SwingGUI(engine.getCells());
		} else if (guiMode.equals("cli")) {
			gui = new ConsoleGUI();
		}

		long lastTime;  // Timer-variable that represents the time before a engine-tick
		long lastDelta; // Timer-variable that represents the time after a engine-tick

		while (true) {
			lastTime = System.currentTimeMillis(); // save current time
			engine.tick(); 						   // compute next generation
			gui.displayArray(engine.getCells());   // view next generation on UI
			lastDelta = System.currentTimeMillis() - lastTime; // compute time that was needed to compute and view the next generation
			try {
				Thread.sleep(Math.max(0, sleepTime - lastDelta)); // if sleep <= 0, no sleep is executed, so the sleeptime does not have to be 
																  // checked for validity. On slow systems, the application will run as fast as 
																  // possible, if the sleeptime-value is set too low. 
																  // The Thread only sleeps the ammount of time specified by sleepTime minus lastDelta 
																  // (which is the time the Engine took to recalculate the generation). So the "tick" 
																  // is really executed every 'sleepTime' millis. 
			} catch (InterruptedException e) {
				// Sleep interrupted, as we are not working multithreaded, this can be safely ignored.
			}
		}

	}

	/**
	 * Method for printing error messages to the error stream and exit the application
	 * This is the only place, where exiting occurs.
	 * @param errorMessage String that is printed to the errorstream before exiting the application
	 */
	private static void printErrorMessageAndExit(String errorMessage) {
		System.err.println(errorMessage);
		System.exit(-1);
	}

	/**
	 * Parses the edge-mode string to an SimulationEngin.EdgeMode-enum so that the SimulationEngine can work with it.
	 * @param stringToEdgeMode String representation of the edge-mode
	 * @return Returns SimulationEngine.EdgeMode representation of input String
	 */
	private static SimulationEngine.EdgeMode parseEdgeMode(String stringToEdgeMode) { // This method is outsourced from main to have a better code-readability
		SimulationEngine.EdgeMode edgeMode = null;

		switch (stringToEdgeMode) { // Determine which edge-mode to use
		case "torus":
			edgeMode = SimulationEngine.EdgeMode.TORUS;
			break;
		case "bordered":
			edgeMode = SimulationEngine.EdgeMode.BORDERED;
			break;
		}

		return edgeMode;
	}
}
