public class Config {

	private int height;
	private int width;
	SimulationEngine.EdgeMode edgeMode;

	public Config(int height, int width, SimulationEngine.EdgeMode edgeMode) {

		this.height = height;
		this.width = width;
		this.edgeMode = edgeMode;
	}

}
