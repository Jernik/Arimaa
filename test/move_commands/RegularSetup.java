package move_commands;

import java.util.HashMap;

import org.junit.Before;

import board.BoardState;
import board.Coordinate;
import game.Game;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class RegularSetup {
	protected Game g;
	protected Game freezingGame;

	@Before
	public void setup() {
		g = new Game();

		HashMap<Coordinate, AbstractPiece> fp = new HashMap<Coordinate, AbstractPiece>();
		fp.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		fp.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(3, 4), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		fp.put(new Coordinate(5, 3), new Elephant(Owner.Player1));
		fp.put(new Coordinate(4, 4), new Camel(Owner.Player2));
		BoardState fb = new BoardState(fp);
		freezingGame = new Game(fb);
	}


}
