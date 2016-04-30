package move_commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

public class TestMoveCommandEquality {
	private Owner owner;
	private Owner otherOwner;
	private BoardState board;

	private RegularMove regularMove;
	private PullMove pullMove;
	private PushMove pushMove;

	@Before
	public void setup() {
		this.owner = Owner.Player1;
		this.otherOwner = Owner.Player2;
		this.board = new BoardState();
		this.board.movePiece(new Coordinate(3, 0), new Coordinate(3, 3));
		this.board.movePiece(new Coordinate(3, 6), new Coordinate(3, 4));
		this.board.movePiece(new Coordinate(2, 6), new Coordinate(2, 3));

		this.regularMove = new RegularMove(this.board.clone(), new Coordinate(3, 3), new Coordinate(4, 3), this.owner);
		this.pullMove = new PullMove(this.board.clone(), new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner);
		this.pushMove = new PushMove(this.board.clone(), new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner);
	}

	@Test
	public void testRegularMove() {
		assertNotEquals(this.regularMove, null);

		assertEquals(this.regularMove, this.regularMove);
		assertEquals(this.regularMove,
				new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3), this.owner));

		assertNotEquals(this.regularMove,
				new RegularMove(new BoardState(), new Coordinate(3, 3), new Coordinate(4, 3), this.owner));
		assertNotEquals(this.regularMove,
				new RegularMove(this.board, new Coordinate(5, 3), new Coordinate(4, 3), this.owner));
		assertNotEquals(this.regularMove,
				new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(3, 2), this.owner));
		assertNotEquals(this.regularMove,
				new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3), this.otherOwner));

		assertNotEquals(this.regularMove, this.pullMove);
		assertNotEquals(this.regularMove, this.pushMove);
	}

	@Test
	public void testPullMove() {
		assertNotEquals(this.pullMove, null);

		assertEquals(this.pullMove, this.pullMove);
		assertEquals(this.pullMove,
				new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner));

		assertNotEquals(this.pullMove, new PullMove(new BoardState(), new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner));
		assertNotEquals(this.pullMove,
				new PullMove(this.board, new Coordinate(5, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner));
		assertNotEquals(this.pullMove,
				new PullMove(this.board, new Coordinate(3, 3), new Coordinate(3, 2), new Coordinate(3, 4), this.owner));
		assertNotEquals(this.pullMove,
				new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(2, 3), this.owner));
		assertNotEquals(this.pullMove, new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.otherOwner));

		assertNotEquals(this.pullMove, this.regularMove);
		assertNotEquals(this.pullMove, this.pushMove);
	}

	@Test
	public void testPushMove() {
		assertNotEquals(this.pushMove, null);

		assertEquals(this.pushMove, this.pushMove);
		assertEquals(this.pushMove,
				new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(4, 4), this.owner));

		assertNotEquals(this.pushMove,
				new PushMove(new BoardState(), new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(4, 4), this.owner));
		assertNotEquals(this.pushMove,
				new PushMove(this.board, new Coordinate(4, 4), new Coordinate(3, 4), new Coordinate(4, 4), this.owner));
		assertNotEquals(this.pushMove,
				new PushMove(this.board, new Coordinate(3, 3), new Coordinate(2, 3), new Coordinate(4, 4), this.owner));
		assertNotEquals(this.pushMove,
				new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(2, 4), this.owner));
		assertNotEquals(this.pushMove,
				new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(4, 4), this.otherOwner));

		assertNotEquals(this.pushMove, this.regularMove);
		assertNotEquals(this.pushMove, this.pushMove);
	}
}
