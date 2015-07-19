
/**
 * @author 2883670
 *
 */
public class Cell {
	
	static enum State {ALIVE, DEAD}				//New data type State for Cell (ALIVE, DEAD).
	
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
	void age(){									//If current cell ALIVE increase age by one.
		if (this.state == State.ALIVE) {
			this.age++;
		}
	}
	
	/**
	 * @param newState buffer for new generation.
	 */
	void setBufferState(Cell.State newState)
	{
		this.bufferState = newState;
	}
	
	/**
	 * @return current state of cell
	 */
	Cell.State getState()						//Get the current state of the Cell (Alive or Dead).
	{
		return state;
	}
	
	/**
	 * adopts buffered state as state
	 */
	void persistBufferState(){					//When current state is DEAD and current bufferState is Alive reset age of current Cell.
		if (this.state == State.DEAD && this.bufferState == State.ALIVE){
			this.age = 0;
		}
		this.state = bufferState;
	}
	
}
