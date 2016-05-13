package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import board.Coordinate;
import game.Game;
import piece.Owner;

public class TestMoveCommandEquality {
	private Owner owner;
	private Owner otherOwner;
	private Game game;

	private RegularMove regularMove;
	private PullMove pullMove;
	private PushMove pushMove;

	@Before
	public void setup() {
		this.owner = Owner.Player1;
		this.otherOwner = Owner.Player2;
		this.game = new Game();
		this.game.getBoardState().movePiece(new Coordinate(3, 0), new Coordinate(3, 3));
		this.game.getBoardState().movePiece(new Coordinate(3, 6), new Coordinate(3, 4));
		this.game.getBoardState().movePiece(new Coordinate(2, 6), new Coordinate(2, 3));

		this.regularMove = new RegularMove(new Game(this.game), new Coordinate(3, 3), new Coordinate(4, 3), this.owner);
		this.pullMove = new PullMove(new Game(this.game), new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.owner);
		this.pushMove = new PushMove(new Game(this.game), new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.owner);
	}

	@Test
	public void testEqualityRegularMove() {
		assertNotEquals(this.regularMove, null);

		assertEquals(this.regularMove, this.regularMove);
		assertEquals(this.regularMove,
				new RegularMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), this.owner));

		assertNotEquals(this.regularMove,
				new RegularMove(new Game(), new Coordinate(3, 3), new Coordinate(4, 3), this.owner));
		assertNotEquals(this.regularMove,
				new RegularMove(this.game, new Coordinate(5, 3), new Coordinate(4, 3), this.owner));
		assertNotEquals(this.regularMove,
				new RegularMove(this.game, new Coordinate(3, 3), new Coordinate(3, 2), this.owner));
		assertNotEquals(this.regularMove,
				new RegularMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), this.otherOwner));

		assertNotEquals(this.regularMove, this.pullMove);
		assertNotEquals(this.regularMove, this.pushMove);
	}

	@Test
	public void testEqualityPullMove() {
		assertNotEquals(this.pullMove, null);

		assertEquals(this.pullMove, this.pullMove);
		assertEquals(this.pullMove,
				new PullMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner));

		assertNotEquals(this.pullMove,
				new PullMove(new Game(), new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner));
		assertNotEquals(this.pullMove,
				new PullMove(this.game, new Coordinate(5, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner));
		assertNotEquals(this.pullMove,
				new PullMove(this.game, new Coordinate(3, 3), new Coordinate(3, 2), new Coordinate(3, 4), this.owner));
		assertNotEquals(this.pullMove,
				new PullMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(2, 3), this.owner));
		assertNotEquals(this.pullMove, new PullMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.otherOwner));

		assertNotEquals(this.pullMove, this.regularMove);
		assertNotEquals(this.pullMove, this.pushMove);
	}

	@Test
	public void testEqualityPushMove() {
		assertNotEquals(this.pushMove, null);

		assertEquals(this.pushMove, this.pushMove);
		assertEquals(this.pushMove,
				new PushMove(this.game, new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(4, 4), this.owner));

		assertNotEquals(this.pushMove,
				new PushMove(new Game(), new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(4, 4), this.owner));
		assertNotEquals(this.pushMove,
				new PushMove(this.game, new Coordinate(4, 4), new Coordinate(3, 4), new Coordinate(4, 4), this.owner));
		assertNotEquals(this.pushMove,
				new PushMove(this.game, new Coordinate(3, 3), new Coordinate(2, 3), new Coordinate(4, 4), this.owner));
		assertNotEquals(this.pushMove,
				new PushMove(this.game, new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(2, 4), this.owner));
		assertNotEquals(this.pushMove, new PushMove(this.game, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.otherOwner));

		assertNotEquals(this.pushMove, this.regularMove);
		assertNotEquals(this.pushMove, this.pullMove);
	}

	@Test
	public void testHashCodeRegularMove() {
		assertNotNull(this.regularMove.hashCode());

		assertEquals(this.regularMove.hashCode(), this.regularMove.hashCode());
		assertEquals(this.regularMove.hashCode(),
				new RegularMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), this.owner).hashCode());

		assertNotEquals(this.regularMove.hashCode(),
				new RegularMove(new Game(), new Coordinate(3, 3), new Coordinate(4, 3), this.owner).hashCode());
		assertNotEquals(this.regularMove.hashCode(),
				new RegularMove(this.game, new Coordinate(5, 3), new Coordinate(4, 3), this.owner).hashCode());
		assertNotEquals(this.regularMove.hashCode(),
				new RegularMove(this.game, new Coordinate(3, 3), new Coordinate(3, 2), this.owner).hashCode());
		assertNotEquals(this.regularMove.hashCode(),
				new RegularMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), this.otherOwner).hashCode());

		assertNotEquals(this.regularMove.hashCode(), this.pullMove.hashCode());
		assertNotEquals(this.regularMove.hashCode(), this.pushMove.hashCode());
	}

	@Test
	public void testHashCodePullMove() {
		assertNotNull(this.pullMove.hashCode());

		assertEquals(this.pullMove.hashCode(), this.pullMove.hashCode());
		assertEquals(this.pullMove.hashCode(),
				new PullMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner)
						.hashCode());

		assertNotEquals(this.pullMove.hashCode(),
				new PullMove(new Game(), new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner)
						.hashCode());
		assertNotEquals(this.pullMove.hashCode(),
				new PullMove(this.game, new Coordinate(5, 3), new Coordinate(4, 3), new Coordinate(3, 4), this.owner)
						.hashCode());
		assertNotEquals(this.pullMove.hashCode(),
				new PullMove(this.game, new Coordinate(3, 3), new Coordinate(3, 2), new Coordinate(3, 4), this.owner)
						.hashCode());
		assertNotEquals(this.pullMove.hashCode(),
				new PullMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(2, 3), this.owner)
						.hashCode());
		assertNotEquals(this.pullMove.hashCode(), new PullMove(this.game, new Coordinate(3, 3), new Coordinate(4, 3),
				new Coordinate(3, 4), this.otherOwner).hashCode());

		assertNotEquals(this.pullMove.hashCode(), this.regularMove);
		assertNotEquals(this.pullMove.hashCode(), this.pushMove.hashCode());
	}

	@Test
	public void testHashCodePushMove() {
		assertNotEquals(this.pushMove.hashCode(), null);

		assertEquals(this.pushMove.hashCode(), this.pushMove.hashCode());
		assertEquals(this.pushMove.hashCode(),
				new PushMove(this.game, new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(4, 4), this.owner)
						.hashCode());

		assertNotEquals(this.pushMove.hashCode(),
				new PushMove(new Game(), new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(4, 4), this.owner)
						.hashCode());
		assertNotEquals(this.pushMove.hashCode(),
				new PushMove(this.game, new Coordinate(4, 4), new Coordinate(3, 4), new Coordinate(4, 4), this.owner)
						.hashCode());
		assertNotEquals(this.pushMove.hashCode(),
				new PushMove(this.game, new Coordinate(3, 3), new Coordinate(2, 3), new Coordinate(4, 4), this.owner)
						.hashCode());
		assertNotEquals(this.pushMove.hashCode(),
				new PushMove(this.game, new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(2, 4), this.owner)
						.hashCode());
		assertNotEquals(this.pushMove.hashCode(), new PushMove(this.game, new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(4, 4), this.otherOwner).hashCode());

		assertNotEquals(this.pushMove.hashCode(), this.regularMove);
		assertNotEquals(this.pushMove.hashCode(), this.pullMove.hashCode());
	}
}
