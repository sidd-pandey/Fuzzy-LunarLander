package lunarlander.players;

import java.awt.event.KeyEvent;
import java.util.Random;

public class RandomPlayer implements Player {
	
	int[] moves = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT}; 
	
	public int nextMove() {
		int rnd = new Random().nextInt(this.moves.length);
		return moves[rnd];
	}
}
