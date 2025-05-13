package jasper.forsyth;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		
		int NUM_TILES = 9;
		// Create my 9 tiles
		ArrayList<Tile> tiles = new ArrayList<>();
		
		for (int i=0; i<NUM_TILES; i++) {
			tiles.add(new Tile((i+1)));
		}
		
		
		
		boolean done = false;
		while (!done) {	
			for (Tile t: tiles) {
				System.out.print(t + " ");
			}
			done = true;
		}
		
		
	}

}
