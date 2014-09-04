
public class Cell {
	
	private boolean alive;

	// Cell Constructor
	public Cell() {
		alive = false;
	}

	//if true, set cell as alive. If false, set cell as dead
	public void setAlive(boolean b) {
		alive = b;
	}
	
	public boolean getAlive() {
		return alive;
	}
}
