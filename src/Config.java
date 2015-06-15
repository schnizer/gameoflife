public class Config {

	private int cellCountX;
	private int cellCountY;
	private int height;
	private int width;
	SimulationEngine.EdgeMode edgeMode;
	private Cell[][] cells;

	public Config(int height, int width, SimulationEngine.EdgeMode edgeMode) {
		
		this.height = height;
		this.width = width;
		this.edgeMode = edgeMode;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public Cell[][] getCells() {
		return cells;
	}

}
