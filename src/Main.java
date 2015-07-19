import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		
		if(args.length < 4) {
			printErrorMessage("Invalid number of input parameters. Should be four. For example: GameOfLive /home/user/inputfile.txt 300 torus gui");
		}
		
		String inputFilePath = null;
		int sleepTime = 0;
		SimulationEngine.EdgeMode edgeMode = null;
		String guiMode = null;
		
		try {
			Paths.get(args[0]);
			inputFilePath = args[0];
		} catch (InvalidPathException ipe) {
			printErrorMessage("Inputfile does not seem to be a vaild path.");
		}
		
		try {
			sleepTime = Integer.parseInt(args[1]);
		} catch (NumberFormatException nfe) {
			printErrorMessage("Sleeptime does not seem to be a number.");
		}
		
		if (args[2].equalsIgnoreCase("torus") || args[2].equalsIgnoreCase("bordered")) {
			edgeMode = parseEdgeMode(args[2].toLowerCase());
		} else {
			printErrorMessage("Wrong inputparameters: Mode must be \"torus\" or \"bordered\"");
		}
		if (args[3].equalsIgnoreCase("cli") || args[3].equalsIgnoreCase("gui")) {
			guiMode = args[3].toLowerCase();
		} else {
			printErrorMessage("Wrong inputparameters: UI must be \"cli\" or \"gui\"");
		}

		Rule[] rules = { new RPopulation() };
		Parser parser = null;
		try {
			parser = new Parser(inputFilePath);
		} catch (IncorrectCharException e) {
			printErrorMessage("Unexpected character in inputfile!");
		} catch (IncorrectSizeException e) {
			printErrorMessage("Inputfile not rectangular");
		} catch (FileNotFoundException e) {
			printErrorMessage("Inputfile not found!");
		} catch (IOException e) {
			printErrorMessage("Error occured when opening inputfile");
		}

		SimulationEngine engine = new SimulationEngine(edgeMode, rules,
				parser.getCellsFromFile());

		ifGUI gui = null;
		if (guiMode.equalsIgnoreCase("gui")) {
			gui = new SwingGUI(engine.getCells());
		} else if (guiMode.equalsIgnoreCase("cli")) {
			gui = new ConsoleGUI();
		}

		long lastTime;
		long lastDelta;

		while (true) {
			lastTime = System.currentTimeMillis();
			engine.tick();
			gui.displayArray(engine.getCells());
			lastDelta = System.currentTimeMillis() - lastTime;
			try {
				Thread.sleep(Math.max(0, sleepTime - lastDelta)); // if sleep <=
																	// 0, no
																	// sleep is
																	// executed
			} catch (InterruptedException e) {
				// Sleep interrupted
			}
		}

	}

	public static void printErrorMessage(String errorMessage) {
		System.err.println(errorMessage);
		System.exit(-1);
	}

	public static SimulationEngine.EdgeMode parseEdgeMode(
			String stringToEdgeMode) {
		SimulationEngine.EdgeMode edgeMode = null;

		switch (stringToEdgeMode) {
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
