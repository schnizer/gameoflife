import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	private final static int CELLWIDTH = 50;
	private final static int CELLHEIGHT = 50;

	public static void main(String[] args) {

		String inputFilePath = null;
		int sleepTime = 0;
		SimulationEngine.EdgeMode edgeMode = null;
		String guiMode = null;

		inputFilePath = args[0];
		sleepTime = Integer.parseInt(args[1]);
		if (args[2].equals("torus") || args[2].equals("bordered")) {
			edgeMode = parseEdgeMode(args[2]);
		} else {
			printErrorMessage("Inputparameter");
		}
		guiMode = args[3];

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
		if (guiMode == "gui") {
			gui = new SwingGUI(engine.getCells(), CELLWIDTH
					* parser.getCountColumns(), CELLHEIGHT
					* parser.getCountLines());
		} else if (guiMode == "cli") {
			gui = new ConsoleGUI();
		} else {
			gui = new SwingGUI(engine.getCells(), CELLWIDTH
					* parser.getCountColumns(), CELLHEIGHT
					* parser.getCountLines());
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
	
	public static void printErrorMessage(String errorMessage){
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
		default:
			// Fehler
		}

		return edgeMode;
	}
}
