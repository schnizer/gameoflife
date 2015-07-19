
/**
 * @author 2883670
 *
 */
public class Cell {
	
	static enum State {ALIVE, DEAD}				//New datatype State for Cell (ALIVE, DEAD).
	
	private int age;							
	private State state;						
	private State bufferState;					
	
	
	/**
	 * Default initialization for each Cell with state dead. 
	 */
	public Cell(){
		this.age = 0;
		this.state = State.DEAD;
	}
	
	/**
	 * @return the current age of the Cell.
	 */
	int getAge(){								
		return age;
	}
	
	/**
	 * Increases the age of the current Cell.
	 */
	void age(){									//If cell is alive age is increased by 1
		if (this.state == State.ALIVE) {
			this.age++;
		}
	}
	
	/**
	 * @param newState buffer for next generation.
	 */
	void setBufferState(Cell.State newState)
	{
		this.bufferState = newState;
	}
	
	/**
	 * @return the current cell state
	 */
	Cell.State getState()						
	{
		return state;
	}
	
	/**
	 * Cell adopts buffered state as state
	 */
	void persistBufferState(){					
		if (this.state == State.DEAD && this.bufferState == State.ALIVE){ //if cell comes to life age is reset
			this.age = 0;
		}
		this.state = bufferState;
	}
	
}
