package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import move_commands.RegularMove;
import piece.Owner;

public class TestGameEquality {
	private Game g1;
	private Game g2;

	@Before
	public void setup() {
		this.g1 = new Game();
		this.g2 = new Game();
		this.g1.move(new RegularMove(this.g1.getBoardState(), new Coordinate(0, 1), new Coordinate(0, 2), Owner.Player1,
				this.g1.getNumMoves()));
		this.g2.move(new RegularMove(this.g2.getBoardState(), new Coordinate(0, 1), new Coordinate(0, 2), Owner.Player1,
				this.g2.getNumMoves()));
	}

	public void testNotEquals(VoidMethod method) {
		method.execute();
		assertNotEquals(this.g1, this.g2);
	}

	@Test
	public void testGameNotSameRef() {
		assertFalse(this.g1 == this.g2);
	}

	@Test
	public void testGameNotEqualToNull() {
		assertNotEquals(this.g1, null);
	}

	@Test
	public void testGameEqualIsReflexive() {
		assertEquals(this.g1, this.g1);
	}

	@Test
	public void testGameEqualIsSymmetric() {
		assertEquals(this.g1, this.g2);
		assertEquals(this.g2, this.g1);
	}

	@Test
	public void testGameNotEqualIfDifferentMoves() {
		testNotEquals(() -> {
			RegularMove move = new RegularMove(this.g1.getBoardState(), new Coordinate(0, 2), new Coordinate(0, 3),
					Owner.Player1, this.g1.getNumMoves());
			this.g1.getMoves().add(move);
		});
	}

	@Test
	public void testGameNotEqualIfDifferentBoardStates() {
		testNotEquals(() -> {
			this.g1.getBoardState().movePiece(new Coordinate(0, 2), new Coordinate(0, 3));
			// hack to make the move lists still be the same
			this.g1.getMoves().clear();
			this.g1.getMoves().addAll(this.g2.getMoves());
		});
	}

	@Test
	public void testGameNotEqualIfDifferentTurn() {
		testNotEquals(() -> {
			this.g1.incrementTurn();
		});
	}

	@Test
	public void testGameNotEqualIfDifferentTurnCounter() {
		testNotEquals(() -> {
			// to increment the turn counter, make moves and delete them
			// 1st move
			RegularMove move = new RegularMove(this.g1.getBoardState(), new Coordinate(0, 2), new Coordinate(0, 3),
					Owner.Player1, this.g1.getNumMoves());
			this.g1.move(move);
			this.g1.getBoardState().movePiece(new Coordinate(0, 3), new Coordinate(0, 2));

			// 2nd move
			move = new RegularMove(this.g1.getBoardState(), new Coordinate(0, 2), new Coordinate(0, 3), Owner.Player1,
					this.g1.getNumMoves());
			this.g1.move(move);
			this.g1.getBoardState().movePiece(new Coordinate(0, 3), new Coordinate(0, 2));

			// 3rd move
			move = new RegularMove(this.g1.getBoardState(), new Coordinate(0, 2), new Coordinate(0, 3), Owner.Player1,
					this.g1.getNumMoves());
			this.g1.move(move);
			this.g1.getBoardState().movePiece(new Coordinate(0, 3), new Coordinate(0, 2));

			// 4th move
			move = new RegularMove(this.g1.getBoardState(), new Coordinate(0, 6), new Coordinate(0, 5), Owner.Player2,
					this.g1.getNumMoves());
			this.g1.move(move);
			this.g1.getBoardState().movePiece(new Coordinate(0, 5), new Coordinate(0, 6));

			this.g1.getMoves().clear();
			this.g1.getMoves().addAll(this.g2.getMoves());
		});
	}

	@Test
	public void testGameNotEqualIfDifferentWinner() {
		testNotEquals(() -> {
			this.g1.setWinner(Owner.Player1);
		});
		testNotEquals(() -> {
			this.g1.setWinner(Owner.Player2);
		});
		testNotEquals(() -> {
			this.g1.setWinner(Owner.Player1);
			this.g2.setWinner(Owner.Player2);
		});
	}

	@Test
	public void testGameNotEqualIfDifferentNumberOfMoves() {
		testNotEquals(() -> {
			RegularMove move = new RegularMove(this.g1.getBoardState(), new Coordinate(0, 2), new Coordinate(0, 3),
					Owner.Player1, this.g1.getNumMoves());
			this.g1.move(move);
			// remove last move
			this.g1.getMoves().remove(this.g1.getMoves().size() - 1);
			// make the board state the same as it was before
			this.g1.getBoardState().movePiece(new Coordinate(0, 3), new Coordinate(0, 1));
		});
	}

	@Test
	public void testGameNotEqualIfDifferentPlayer1Name() {
		testNotEquals(() -> {
			this.g1.setP1Name("something different");
		});
	}

	@Test
	public void testGameNotEqualIfDifferentPlayer2Name() {
		testNotEquals(() -> {
			this.g2.setP1Name("something different");
		});
	}

	@Test
	public void testGameNotEqualIfDifferentMoveTimer() {
		testNotEquals(() -> {
			this.g1.setMoveTimer(3456);
		});
	}
}