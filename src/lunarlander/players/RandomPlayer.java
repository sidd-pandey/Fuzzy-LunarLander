package lunarlander.players;

import java.awt.event.KeyEvent;
import java.util.Random;

public class RandomPlayer implements Player {
	
	int[] moves = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
	int currentMove;
	
	public int nextMove() {
		int rnd = new Random().nextInt(this.moves.length);
		this.currentMove = moves[rnd];
		return this.currentMove;
	}
	
	public int currentMove() {
		return this.currentMove;	
	}
}
