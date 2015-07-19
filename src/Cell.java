
/**
 * @author 2883670
 *
 */
public class Cell {
	
	static enum State {ALIVE, DEAD}				//New datatype State for Cell (ALIVE, DEAD).
	
	private int age;							//Integer to age
	private State state;						//State to state
	private State bufferState;					//State to bufferState
	
	
	/**
	 * Default initialisation for each Cell. 
	 */
	public Cell(){								//Default initialisation for Cell to DEAD.
		this.age = 0;
		this.state = State.DEAD;
	}
	
	/**
	 * @return the current age of the Cell.
	 */
	int getAge(){								//Returns the age of the Cell.
		return age;
	}
	
	/**
	 * Increases the age from the current Cell.
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
	
	Cell.State getState()						//Get the current state of the Cell (Alive or Dead).
	{
		return state;
	}
	
	/**
	 * Proves current Cell and the next generation Cell for age count.
	 */
	void persistBufferState(){					//When current state is DEAD and current bufferState is Alive reset age of current Cell.
		if (this.state == State.DEAD && this.bufferState == State.ALIVE){
			this.age = 0;
		}
		this.state = bufferState;
	}
	
}
