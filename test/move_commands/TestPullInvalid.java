package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPullInvalid extends PullSetup {
	public void testInvalidPull(MoveCommand move) {
		BoardState board = new BoardState(pullingGame.getBoardState());
		assertFalse(move.isValidMove());
		assertFalse(pullingGame.move(move));

		assertEquals(board, pullingGame.getBoardState());
	}

	@Test
	public void testPullOffBoard1() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, -1), new Coordinate(5, 0),
				new Coordinate(5, -2), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testPullOffBoard2() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(3, 0), new Coordinate(4, 0),
				new Coordinate(3, -1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testPullUpOffBoard() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(7, 0), new Coordinate(7, -1),
				new Coordinate(7, 1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(0, 7, 1, 7, 0));
	}

	@Test
	public void testPullRightOffBoard() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(7, 0), new Coordinate(8, 0),
				new Coordinate(6, 0), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(0, 7, 0, 6, 1));
	}

	@Test
	public void testPullDownOffBoard() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(0, 7), new Coordinate(0, 8),
				new Coordinate(0, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(7, 0, 6, 0, 2));
	}

	@Test
	public void testPullLeftOffBoard() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(0, 7), new Coordinate(-1, 7),
				new Coordinate(1, 7), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(7, 0, 7, 1, 3));
	}

	@Test
	public void testPullCantSwap() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4),
				new Coordinate(5, 4), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testPullMustMove() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 5),
				new Coordinate(5, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testPullCantPullSelf() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 5), new Coordinate(5, 4),
				new Coordinate(5, 5), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testPullMustPullPiece() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 6), new Coordinate(1, 5),
				new Coordinate(2, 7), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(0, 4, 0, 5, 3));
	}

	@Test
	public void testPullNullPiece() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(7, 5), new Coordinate(7, 6),
				new Coordinate(6, 5), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(0, 4, 0, 5, 3));
	}

	@Test
	public void testPullCantPullNothing() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(5, 3), new Coordinate(5, 4),
				new Coordinate(4, 3), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
	}

	@Test
	public void testPullUpIntoOccupiedSpace() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(2, 2), new Coordinate(2, 1),
				new Coordinate(2, 3), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(2, 2, 3, 2, 0));
	}

	@Test
	public void testPullRightIntoOccupiedSpace() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 1), new Coordinate(2, 1),
				new Coordinate(0, 1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 1, 1, 0, 1));
	}

	@Test
	public void testPullDownIntoOccupiedSpace() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 1), new Coordinate(1, 2),
				new Coordinate(1, 0), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 1, 0, 1, 2));
	}

	@Test
	public void testPullLeftIntoOccupiedSpace() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(2, 2), new Coordinate(1, 2),
				new Coordinate(3, 2), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(2, 2, 2, 3, 3));
	}

	@Test
	public void testPullNothingUp() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 1), new Coordinate(4, 0),
				new Coordinate(4, 2), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 4, 2, 4, 0));
	}

	@Test
	public void testPullNothingRight() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 1), new Coordinate(5, 1),
				new Coordinate(3, 1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 4, 1, 3, 1));
	}

	@Test
	public void testPullNothingDown() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 1), new Coordinate(4, 2),
				new Coordinate(4, 0), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 4, 0, 4, 2));
	}

	@Test
	public void testPullNothingLeft() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 1), new Coordinate(3, 1),
				new Coordinate(5, 1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 4, 1, 5, 3));
	}

	@Test
	public void testPullOwnPieceUp() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 6), new Coordinate(1, 5),
				new Coordinate(1, 7), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(6, 1, 7, 1, 0));
		// assertFalse(pullingGame.checkCoor(5, 1));
	}

	@Test
	public void testPullOwnPieceRight() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(1, 6), new Coordinate(2, 6),
				new Coordinate(0, 6), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(6, 1, 6, 0, 1));
		// assertFalse(pullingGame.checkCoor(6, 2));
	}

	@Test
	public void testPullOwnPieceDown() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(6, 1), new Coordinate(6, 2),
				new Coordinate(6, 0), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 6, 0, 6, 2));
		// assertTrue(pullingGame.checkCoor(2, 6));
	}

	@Test
	public void testPullOwnPieceLeft() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(6, 1), new Coordinate(5, 1),
				new Coordinate(7, 1), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(1, 6, 1, 7, 3));
		// assertFalse(pullingGame.checkCoor(1, 5));
	}

	@Test
	public void testPullPieceOfGreaterStrength() {
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(3, 7), new Coordinate(6, 3),
				new Coordinate(4, 7), pullingGame.getOwner(), pullingGame.getNumMoves());
		testInvalidPull(move);
		// assertFalse(pullingGame.pull(7, 3, 7, 4, 3));
	}

	@Test
	public void testCantPushPullWith1Move() {
		HashMap<Coordinate, AbstractPiece> pushPullP = new HashMap<Coordinate, AbstractPiece>();
		pushPullP.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		pushPullP.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		pushPullP.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		pushPullP.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		pushPullP.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		pushPullP.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		pushPullP.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		pushPullP.put(new Coordinate(2, 2), new Camel(Owner.Player1));
		pushPullP.put(new Coordinate(5, 2), new Camel(Owner.Player1));
		pushPullP.put(new Coordinate(1, 1), new Camel(Owner.Player1));
		pushPullP.put(new Coordinate(3, 1), new Rabbit(Owner.Player2));
		pushPullP.put(new Coordinate(7, 1), new Dog(Owner.Player1));
		Game game = new Game(new BoardState(pushPullP));

		game.setPlayerTurn(1);
		assertTrue(game.move(new RegularMove(game.getBoardState(), new Coordinate(7, 1), new Coordinate(6, 1),
				game.getOwner(), game.getNumMoves())));
		assertTrue(game.move(new RegularMove(game.getBoardState(), new Coordinate(6, 1), new Coordinate(5, 1),
				game.getOwner(), game.getNumMoves())));
		assertTrue(game.move(new RegularMove(game.getBoardState(), new Coordinate(5, 1), new Coordinate(4, 1),
				game.getOwner(), game.getNumMoves())));
		BoardState board = new BoardState(game.getBoardState());
		// assertTrue(game.move(1, 7, 3));
		// assertTrue(game.move(1, 6, 3));
		// assertTrue(game.move(1, 5, 3));
		MoveCommand move = new PullMove(pullingGame.getBoardState(), new Coordinate(4, 1), new Coordinate(5, 1),
				new Coordinate(3, 1), pullingGame.getOwner(), pullingGame.getNumMoves());
		assertFalse(move.isValidMove());
		assertFalse(game.move(move));
		// assertFalse(game.pull(1, 4, 1, 3, 1));
		assertEquals(board, game.getBoardState());
	}
}
