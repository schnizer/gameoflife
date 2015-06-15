public class Config {

	SimulationEngine.EdgeMode edgeMode;
	private Cell[][] cells;

	public Config(SimulationEngine.EdgeMode edgeMode) {

		this.edgeMode = edgeMode;
	}

	public Cell[][] getCellsFromFile() {
		return cells;
	}

}
