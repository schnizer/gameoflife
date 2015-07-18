public class Main {
	private final static int CELLWIDTH = 50;
	private final static int CELLHEIGHT = 50;

	public static void main(String[] args) {

		int sleepTime = 300;
		Rule[] rules = { new RPopulation() };
		Parser parser = null;
		SimulationEngine.EdgeMode edgeMode = SimulationEngine.EdgeMode.TORUS;
		try {
			parser = new Parser("D:/file2.txt");
		} catch (IncorrectCharException e) {
			System.exit(0);
		} catch (IncorrectSizeException e) {
			System.exit(0);
		}

		SimulationEngine engine = new SimulationEngine(edgeMode,
				rules, parser.getCellsFromFile());

		ifGUI gui = new SwingGUI(engine.getCells(), CELLWIDTH
				* parser.getCountColumns(), CELLHEIGHT * parser.getCountLines());
		// ifGUI gui = new ConsoleGUI();

		long lastTime;
		long lastDelta;

		while (true) {
			lastTime = System.currentTimeMillis();
			engine.tick();
			gui.displayArray(engine.getCells());
			lastDelta = System.currentTimeMillis() - lastTime; 
			try {
				Thread.sleep(Math.max(0, sleepTime - lastDelta)); // if sleep <= 0, no sleep is executed
			} catch (InterruptedException e) {
				// Sleep interrupted
			}
		}

	}

}
