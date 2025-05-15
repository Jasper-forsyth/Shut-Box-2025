package jasper.forsyth;

public class Tile {
	private int value;
	private boolean isDown;
	private boolean selected;
	/**
	 * Creates a new tile 
	 * @param v uses this as the value of the tile 
	 */
	public Tile(int v) {
		value = v;
		isDown = false;
		selected = false;
	}
	/**
	 * Makes the tile shut or put down 
	 */
	public void shut() {
		isDown = true;
	}
	/**
	 * Returns if the tile is closed or not 
	 * @return a boolean if the tile is closed or not 
	 */
	public boolean isDown() {
		return isDown;
	}
	/**
	 * Makes the tile be selected 
	 */
	public void select() {
		selected = true;
	}
	/**
	 * Unselect the tile 
	 */
	public void deselect() {
		selected = false;
	}
	/**
	 * Checks to see the status of tile and if it is selected or not 
	 * @return the selected status of the given tile 
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * Resets the given tiles to be put back up 
	 */
	public void reset() {
		isDown = false;
	}
	/**
	 * Gets the value of the tile 
	 * @return the value of the given Tile 
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		String state = "";
		if (isDown) {
			state = "D";
		}
		else {
			state = "U";
		}
		return "" + value + ":" + state;
	}
	
}
