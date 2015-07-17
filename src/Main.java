public class Main {
	public final static int CELLWIDTH = 50;
	public final static int CELLHEIGHT = 50;

	public static void main(String[] args) {
		
		Rule[] rules = { new RPopulation() };

		Config config = new Config(SimulationEngine.EdgeMode.BORDERED, "D:/inputfile.txt");
		
		SimulationEngine engine = new SimulationEngine(
				SimulationEngine.EdgeMode.BORDERED, rules, config.getCellsFromFile());
		

		ifGUI gui = new SwingGUI(engine.getCells(), CELLWIDTH * config.getCountColumns(), CELLHEIGHT * config.getCountLines());
		//ifGUI gui = new ConsoleGUI();

		long lastTime;
		long lastDelta;

		while (true) {
			lastTime = System.currentTimeMillis();
			engine.tick();
			gui.displayArray(engine.getCells());
			lastDelta = System.currentTimeMillis() - lastTime;
			try {
				Thread.sleep(Math.max(0, 300 - lastDelta));
			} catch (InterruptedException e) {
				// Sleep interrupted
			}
		}

	}

}
