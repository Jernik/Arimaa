package move_commands;

import java.util.HashMap;

import org.junit.Before;

import game.BoardState;
import game.Coordinate;
import game.Game;
import piece.AbstractPiece;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class PullSetup {
	protected Game pullingGame;

	@Before
	public void setup() {
		HashMap<Coordinate, AbstractPiece> pullp = new HashMap<Coordinate, AbstractPiece>();
		pullp.put(new Coordinate(0, 7), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(1, 7), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(3, 7), new Rabbit(Owner.Player1));
		pullp.put(new Coordinate(4, 7), new Elephant(Owner.Player2));
		pullp.put(new Coordinate(0, 6), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(1, 6), new Elephant(Owner.Player2));
		pullp.put(new Coordinate(3, 6), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(4, 6), new Elephant(Owner.Player2));
		pullp.put(new Coordinate(5, 6), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(5, 5), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(6, 5), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(6, 4), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(7, 4), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(2, 3), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(5, 3), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(1, 2), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(2, 2), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(3, 2), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(5, 2), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(6, 2), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(0, 1), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(1, 1), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(2, 1), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(4, 1), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(6, 1), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(7, 1), new Rabbit(Owner.Player1));
		pullp.put(new Coordinate(1, 0), new Rabbit(Owner.Player2));
		pullp.put(new Coordinate(3, 0), new Elephant(Owner.Player1));
		pullp.put(new Coordinate(6, 0), new Rabbit(Owner.Player1));
		pullp.put(new Coordinate(7, 0), new Elephant(Owner.Player2));
		pullingGame = new Game(new BoardState(pullp));
	}
}
