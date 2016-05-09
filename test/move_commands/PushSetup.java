package move_commands;

import java.util.HashMap;

import org.junit.Before;

import game.BoardState;
import game.Coordinate;
import game.Game;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class PushSetup {
	protected Game g2;
	protected Game pushingGame;

	@Before
	public void setup() {
		HashMap<Coordinate, AbstractPiece> p2 = new HashMap<Coordinate, AbstractPiece>();
		p2.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p2.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		p2.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p2.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p2.put(new Coordinate(4, 4), new Elephant(Owner.Player1));

		p2.put(new Coordinate(5, 7), new Rabbit(Owner.Player2));
		p2.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p2.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		p2.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		BoardState b2 = new BoardState(p2);
		g2 = new Game(b2);

		HashMap<Coordinate, AbstractPiece> pp = new HashMap<Coordinate, AbstractPiece>();
		pp.put(new Coordinate(0, 7), new Elephant(Owner.Player1));
		pp.put(new Coordinate(1, 7), new Rabbit(Owner.Player2));
		pp.put(new Coordinate(3, 7), new Camel(Owner.Player1));
		pp.put(new Coordinate(0, 6), new Rabbit(Owner.Player2));
		pp.put(new Coordinate(2, 6), new Camel(Owner.Player1));
		pp.put(new Coordinate(3, 6), new Rabbit(Owner.Player2));
		pp.put(new Coordinate(4, 6), new Camel(Owner.Player1));
		pp.put(new Coordinate(3, 5), new Camel(Owner.Player1));
		pp.put(new Coordinate(5, 5), new Cat(Owner.Player2));
		pp.put(new Coordinate(6, 5), new Rabbit(Owner.Player1));
		pp.put(new Coordinate(4, 4), new Cat(Owner.Player2));
		pp.put(new Coordinate(5, 4), new Elephant(Owner.Player1));
		pp.put(new Coordinate(6, 4), new Camel(Owner.Player2));
		pp.put(new Coordinate(2, 3), new Rabbit(Owner.Player1));
		pp.put(new Coordinate(5, 3), new Dog(Owner.Player2));
		pp.put(new Coordinate(1, 2), new Rabbit(Owner.Player1));
		pp.put(new Coordinate(2, 2), new Elephant(Owner.Player1));
		pp.put(new Coordinate(3, 2), new Rabbit(Owner.Player1));
		pp.put(new Coordinate(0, 1), new Rabbit(Owner.Player2));
		pp.put(new Coordinate(1, 1), new Dog(Owner.Player1));
		pp.put(new Coordinate(2, 1), new Rabbit(Owner.Player1));
		pp.put(new Coordinate(4, 1), new Elephant(Owner.Player1));
		pp.put(new Coordinate(6, 1), new Elephant(Owner.Player1));
		pp.put(new Coordinate(7, 1), new Rabbit(Owner.Player1));
		pp.put(new Coordinate(1, 0), new Rabbit(Owner.Player2));
		pp.put(new Coordinate(6, 0), new Rabbit(Owner.Player1));
		pp.put(new Coordinate(7, 0), new Elephant(Owner.Player2));
		pushingGame = new Game(new BoardState(pp));
	}
}
