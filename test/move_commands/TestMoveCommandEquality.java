package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import game.BoardState;
import game.Coordinate;
import piece.Owner;

public class TestMoveCommandEquality {
	private static final int NUMBER_OF_MOVES = 4;
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

		this.regularMove = new RegularMove(this.board.clone(), new Coordinate(3, 3), new Coordinate(4, 3), this.owner,
				NUMBER_OF_MOVES);
		this.pullMove = new PullMove(this.board.clone(), new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES);
		this.pushMove = new PushMove(this.board.clone(), new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES);
	}

	@Test
	public void testEqualityRegularMove() {
		assertNotEquals(this.regularMove, null);

		assertEquals(this.regularMove, this.regularMove);
		assertEquals(this.regularMove,
				new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3), this.owner, NUMBER_OF_MOVES));

		assertNotEquals(this.regularMove, new RegularMove(new BoardState(), new Coordinate(3, 3), new Coordinate(4, 3),
				this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.regularMove,
				new RegularMove(this.board, new Coordinate(5, 3), new Coordinate(4, 3), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.regularMove,
				new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(3, 2), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.regularMove, new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				this.otherOwner, NUMBER_OF_MOVES));

		assertNotEquals(this.regularMove, this.pullMove);
		assertNotEquals(this.regularMove, this.pushMove);
	}

	@Test
	public void testEqualityPullMove() {
		assertNotEquals(this.pullMove, null);

		assertEquals(this.pullMove, this.pullMove);
		assertEquals(this.pullMove, new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES));

		assertNotEquals(this.pullMove, new PullMove(new BoardState(), new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pullMove, new PullMove(this.board, new Coordinate(5, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pullMove, new PullMove(this.board, new Coordinate(3, 3), new Coordinate(3, 2),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pullMove, new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(2, 3), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pullMove, new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.otherOwner, NUMBER_OF_MOVES));

		assertNotEquals(this.pullMove, this.regularMove);
		assertNotEquals(this.pullMove, this.pushMove);
	}

	@Test
	public void testEqualityPushMove() {
		assertNotEquals(this.pushMove, null);

		assertEquals(this.pushMove, this.pushMove);
		assertEquals(this.pushMove, new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES));

		assertNotEquals(this.pushMove, new PushMove(new BoardState(), new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pushMove, new PushMove(this.board, new Coordinate(4, 4), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pushMove, new PushMove(this.board, new Coordinate(3, 3), new Coordinate(2, 3),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pushMove, new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(2, 4), this.owner, NUMBER_OF_MOVES));
		assertNotEquals(this.pushMove, new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.otherOwner, NUMBER_OF_MOVES));

		assertNotEquals(this.pushMove, this.regularMove);
		assertNotEquals(this.pushMove, this.pullMove);
	}

	@Test
	public void testHashCodeRegularMove() {
		assertNotNull(this.regularMove.hashCode());

		assertEquals(this.regularMove.hashCode(), this.regularMove.hashCode());
		assertEquals(this.regularMove.hashCode(),
				new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3), this.owner, NUMBER_OF_MOVES)
						.hashCode());

		assertNotEquals(this.regularMove.hashCode(), new RegularMove(new BoardState(), new Coordinate(3, 3),
				new Coordinate(4, 3), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.regularMove.hashCode(),
				new RegularMove(this.board, new Coordinate(5, 3), new Coordinate(4, 3), this.owner, NUMBER_OF_MOVES)
						.hashCode());
		assertNotEquals(this.regularMove.hashCode(),
				new RegularMove(this.board, new Coordinate(3, 3), new Coordinate(3, 2), this.owner, NUMBER_OF_MOVES)
						.hashCode());
		assertNotEquals(this.regularMove.hashCode(), new RegularMove(this.board, new Coordinate(3, 3),
				new Coordinate(4, 3), this.otherOwner, NUMBER_OF_MOVES).hashCode());

		assertNotEquals(this.regularMove.hashCode(), this.pullMove.hashCode());
		assertNotEquals(this.regularMove.hashCode(), this.pushMove.hashCode());
	}

	@Test
	public void testHashCodePullMove() {
		assertNotNull(this.pullMove.hashCode());

		assertEquals(this.pullMove.hashCode(), this.pullMove.hashCode());
		assertEquals(this.pullMove.hashCode(), new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES).hashCode());

		assertNotEquals(this.pullMove.hashCode(), new PullMove(new BoardState(), new Coordinate(3, 3),
				new Coordinate(4, 3), new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pullMove.hashCode(), new PullMove(this.board, new Coordinate(5, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pullMove.hashCode(), new PullMove(this.board, new Coordinate(3, 3), new Coordinate(3, 2),
				new Coordinate(3, 4), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pullMove.hashCode(), new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(2, 3), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pullMove.hashCode(), new PullMove(this.board, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.otherOwner, NUMBER_OF_MOVES).hashCode());

		assertNotEquals(this.pullMove.hashCode(), this.regularMove);
		assertNotEquals(this.pullMove.hashCode(), this.pushMove.hashCode());
	}

	@Test
	public void testHashCodePushMove() {
		assertNotEquals(this.pushMove.hashCode(), null);

		assertEquals(this.pushMove.hashCode(), this.pushMove.hashCode());
		assertEquals(this.pushMove.hashCode(), new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES).hashCode());

		assertNotEquals(this.pushMove.hashCode(), new PushMove(new BoardState(), new Coordinate(3, 3),
				new Coordinate(3, 4), new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pushMove.hashCode(), new PushMove(this.board, new Coordinate(4, 4), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pushMove.hashCode(), new PushMove(this.board, new Coordinate(3, 3), new Coordinate(2, 3),
				new Coordinate(4, 4), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pushMove.hashCode(), new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(2, 4), this.owner, NUMBER_OF_MOVES).hashCode());
		assertNotEquals(this.pushMove.hashCode(), new PushMove(this.board, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.otherOwner, NUMBER_OF_MOVES).hashCode());

		assertNotEquals(this.pushMove.hashCode(), this.regularMove);
		assertNotEquals(this.pushMove.hashCode(), this.pullMove.hashCode());
	}
}
