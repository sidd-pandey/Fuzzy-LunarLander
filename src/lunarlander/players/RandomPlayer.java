package lunarlander.players;

import java.awt.event.KeyEvent;
import java.util.Random;

public class RandomPlayer implements Player {
	
	int[] yDirMoves = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, -1};
	int[] xDirMoves = {KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, -1};
	int[] currentMove;
	
	public int[] nextMove() {
		this.currentMove = new int[] {-1, -1};
		this.currentMove[0] = yDirMoves[new Random().nextInt(this.yDirMoves.length)];
		this.currentMove[1] = xDirMoves[new Random().nextInt(this.xDirMoves.length)];
		return this.currentMove;
	}
	
	public int[] currentMove() {
		return this.currentMove;	
	}
}
