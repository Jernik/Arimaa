package move_commands;

import org.junit.Before;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

public class TestMoveCommandEquality {
	private RegularMove validMove;
	private RegularMove invalidOwnerMove;
	private RegularMove invalidDestinationMove;

	@Before
	public void setup() {
		this.validMove = new RegularMove(new BoardState(), new Coordinate(1, 1), new Coordinate(1, 2), Owner.Player1);
		this.invalidOwnerMove = new RegularMove(new BoardState(), new Coordinate(1, 1), new Coordinate(1, 2),
				Owner.Player2);
		this.invalidDestinationMove = new RegularMove(new BoardState(), new Coordinate(1, 1), new Coordinate(1, 0),
				Owner.Player1);
	}
}
