package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import board.BoardState;
import board.Coordinate;
import move_commands.MoveCommand;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class TestGame {
	private Game g;
	private Game g1;

	@Before
	public void setup() {
		g = new Game();

		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		BoardState b1 = new BoardState(p1);
		g1 = new Game(b1);
	}

	@Test
	public void testInitializes() {
		Game g = new Game();
		assertNotNull(g);
	}

	@Test
	public void testCopyConstructor() {
		Game game = new Game(this.g);
		assertFalse(game == this.g);
		assertEquals(this.g, game);
		assertFalse(game.getMoves() == this.g.getMoves());
		assertFalse(game.getBoardState() == this.g.getBoardState());

		this.g1.assignAi1();
		game = new Game(this.g1);
		assertFalse(game == this.g1);
		assertEquals(this.g1, game);
		assertFalse(game.getMoves() == this.g1.getMoves());
		assertFalse(game.getBoardState() == this.g1.getBoardState());
		assertEquals(this.g1.getP1Ai(), game.getP1Ai());

		game = new Game();
		game.assignAi1();
		game.assignAi2();

		for (int i = 0; i < 8; i++) {
			game.makeAiTurn();
		}

		Game gameCopy = new Game(game);
		assertFalse(gameCopy == game);
		assertEquals(game, gameCopy);
		assertFalse(game.getMoves() == gameCopy.getMoves());
		assertFalse(game.getBoardState() == gameCopy.getBoardState());
		assertEquals(game.getP1Ai(), gameCopy.getP1Ai());
		assertEquals(game.getP2Ai(), gameCopy.getP2Ai());

		game.makeAiTurn();
		assertNotEquals(game.getMoves(), gameCopy.getMoves());
		assertNotEquals(game.getBoardState(), gameCopy.getBoardState());
		assertNotEquals(game.getP1Ai(), gameCopy.getP1Ai());
		assertNotEquals(game.getP2Ai(), gameCopy.getP2Ai());
	}

	@Test
	public void testInitializesWithBoardState() {
		assertEquals(new Camel(Owner.Player1), g1.getPieceAt(new Coordinate(3, 4)));
		assertEquals(new Elephant(Owner.Player1), g1.getPieceAt(new Coordinate(4, 4)));
		assertNull(g1.getPieceAt(new Coordinate(3, 7)));
		assertEquals(new Rabbit(Owner.Player1), g1.getPieceAt(new Coordinate(7, 7)));
	}

	@Test
	public void testAssignAi() {
		Game game = new Game();
		game.assignAi1();
		game.assignAi2();

		assertNotNull(game.getP1Ai());
		assertNotNull(game.getP2Ai());

		game = new Game();
		game.assignAi2();

		assertNull(game.getP1Ai());
		assertNotNull(game.getP2Ai());

		game = new Game();

		assertNull(game.getP1Ai());
		assertNull(game.getP2Ai());

		game = new Game();
		game.assignAi1();

		assertNotNull(game.getP1Ai());
		assertNull(game.getP2Ai());
	}

	@Test
	public void testIsAiTurn() {
		Game game = new Game();
		game.assignAi1();
		game.assignAi2();
		assertTrue(game.isAiTurn());

		game.incrementTurn();
		assertTrue(game.isAiTurn());

		game = new Game();
		game.assignAi2();
		assertFalse(game.isAiTurn());

		game.incrementTurn();
		assertTrue(game.isAiTurn());

		game = new Game();
		game.assignAi1();
		assertTrue(game.isAiTurn());

		game.incrementTurn();
		assertFalse(game.isAiTurn());

		game = new Game();
		assertFalse(game.isAiTurn());

		game.incrementTurn();
		assertFalse(game.isAiTurn());
	}

	@Test
	public void isAiGame() {
		Game game = new Game();
		game.assignAi1();
		game.assignAi2();
		assertTrue(game.isAiGame());

		game = new Game();
		game.assignAi2();
		assertFalse(game.isAiGame());

		game = new Game();
		game.assignAi1();
		assertFalse(game.isAiGame());

		game = new Game();
		assertFalse(game.isAiGame());
	}

	@Test
	public void testMakeAiTurn() {
		Game game = new Game();
		game.assignAi1();
		game.assignAi2();
		assertTrue(game.makeAiTurn());
		assertFalse(game.getMoves().isEmpty());

		game = new Game();
		game.assignAi1();
		assertTrue(game.makeAiTurn());
		assertFalse(game.getMoves().isEmpty());

		game = new Game();
		game.assignAi2();
		assertFalse(game.makeAiTurn());
		assertTrue(game.getMoves().isEmpty());

		game = new Game();
		assertFalse(game.makeAiTurn());
		assertTrue(game.getMoves().isEmpty());

		game = new Game();
		game.assignAi1();
		game.assignAi2();
		game.incrementTurn();
		assertTrue(game.makeAiTurn());
		assertFalse(game.getMoves().isEmpty());

		game = new Game();
		game.assignAi1();
		game.incrementTurn();
		assertFalse(game.makeAiTurn());
		assertTrue(game.getMoves().isEmpty());

		game = new Game();
		game.assignAi2();
		game.incrementTurn();
		assertTrue(game.makeAiTurn());
		assertFalse(game.getMoves().isEmpty());

		game = new Game();
		game.incrementTurn();
		assertFalse(game.makeAiTurn());
		assertTrue(game.getMoves().isEmpty());
	}

	@Test
	public void testGetMoves() {
		Game game = new Game();
		assertTrue(game.getMoves().isEmpty());

		ArrayList<MoveCommand> list = new ArrayList<MoveCommand>();
		RegularMove move = new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2));
		list.add(move);

		game.move(move);
		assertEquals(list, game.getMoves());

		move = new RegularMove(game, new Coordinate(1, 2), new Coordinate(1, 3));
		list.add(move);

		game.move(move);
		assertEquals(list, game.getMoves());
	}

	@Test
	public void testGetLastMove() {
		Game game = new Game();

		assertNull(game.getLastMove());

		RegularMove move = new RegularMove(game, new Coordinate(1, 1), new Coordinate(1, 2));

		game.move(move);
		assertEquals(move, game.getLastMove());

		move = new RegularMove(game, new Coordinate(1, 2), new Coordinate(1, 3));

		game.move(move);
		assertEquals(move, game.getLastMove());
	}

	@Test
	public void testDeadCoorsAreRemoved() {
		Game game = new Game();
		HashSet<Coordinate> deadCoors = new HashSet<Coordinate>();

		RegularMove move = new RegularMove(game, new Coordinate(2, 1), new Coordinate(2, 2));
		game.move(move);
		deadCoors.add(new Coordinate(2, 2));

		assertEquals(deadCoors, game.getDeadCoors());

		move = new RegularMove(game, new Coordinate(5, 1), new Coordinate(5, 2));
		game.move(move);
		deadCoors.add(new Coordinate(5, 2));

		assertEquals(deadCoors, game.getDeadCoors());

		game.clearDeadCoors();
		assertTrue(game.getDeadCoors().isEmpty());
	}

	@Test
	public void testGetBoardState() {
		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		BoardState b = new BoardState(p1);
		Game game = new Game(b);
		assertEquals(b, game.getBoardState());
	}

	@Test
	public void testTurnNumberStartsAt0() {
		assertEquals(0, g.getTurnNumber());
	}

	@Test
	public void testTurnNumberIncrements() {
		assertEquals(Owner.Player1, g.getPlayerTurn());

		g.incrementTurn();
		assertEquals(Owner.Player2, g.getPlayerTurn());
		assertEquals(1, g.getTurnNumber());

		g.incrementTurn();
		assertEquals(Owner.Player1, g.getPlayerTurn());
		assertEquals(2, g.getTurnNumber());

		g.incrementTurn();
		assertEquals(Owner.Player2, g.getPlayerTurn());
		assertEquals(3, g.getTurnNumber());
	}

	@Test
	public void testSetTurnNumber() {
		g.setTurnNumber(5);
		assertEquals(5, g.getTurnNumber());
	}

	@Test
	public void testPlayer1GoesFirst() {
		assertEquals(Owner.Player1, g.getPlayerTurn());
	}

	@Test
	public void testGetOtherOwner() {
		assertEquals(Owner.Player1, g.getPlayerTurn());
		assertEquals(Owner.Player2, g.getOtherPlayerTurn());

		g.incrementTurn();
		assertEquals(Owner.Player2, g.getPlayerTurn());
		assertEquals(Owner.Player1, g.getOtherPlayerTurn());
	}

	@Test
	public void testGetWinner() {
		assertEquals(Owner.Nobody, g.getWinner());
	}

	@Test
	public void testSetWinner() {
		Owner winner = Owner.Player1;
		g.setWinner(winner);
		assertEquals(winner, g.getWinner());
	}

	@Test
	public void testStartWith4Moves() {
		assertEquals(4, g.getNumMoves());
	}

	@Test
	public void testDefaultPlayer1Name() {
		assertEquals("Player1", g.getP1Name());
	}

	@Test
	public void testChangePlayer1Name() {
		String newName = "new name";
		g.setP1Name(newName);
		assertEquals(newName, g.getP1Name());
	}

	@Test
	public void testDefaultPlayer2Name() {
		assertEquals("Player2", g.getP2Name());
	}

	@Test
	public void testChangePlayer2Name() {
		String newName = "new name";
		g.setP2Name(newName);
		assertEquals(newName, g.getP2Name());
	}

	@Test
	public void testGetMoveTimer() {
		assertEquals(0, g.getMoveTimer());
	}

	@Test
	public void testSetMoveTimer() {
		int newTime = 30;
		g.setMoveTimer(newTime);
		assertEquals(newTime, g.getMoveTimer());
	}

	@Test
	public void testGetPieceExists() {
		assertTrue(g1.isPieceAt(new Coordinate(3, 4)));
		assertEquals(new Camel(Owner.Player1), g1.getPieceAt(new Coordinate(3, 4)));
	}

	@Test
	public void testGetPieceNotExists() {
		assertFalse(g1.isPieceAt(new Coordinate(0, 0)));
		assertNull(g1.getPieceAt(new Coordinate(0, 0)));
	}

	// Testing remove piece checks
	@Test
	public void testRemovePieceValid() {
		HashMap<Coordinate, AbstractPiece> removeP = new HashMap<Coordinate, AbstractPiece>();
		removeP.put(new Coordinate(5, 7), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		removeP.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		removeP.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		removeP.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		removeP.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		removeP.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		removeP.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		removeP.put(new Coordinate(2, 2), new Camel(Owner.Player1));

		Game game = new Game(new BoardState(removeP));
		// g.currentBoard.printBoard();
		Coordinate start = new Coordinate(3, 6);
		Coordinate end = start.up();
		MoveCommand move = new RegularMove(game, start, end);
		assertTrue(game.move(move));
		// g.currentBoard.printBoard();
		assertFalse(game.isPieceAt(new Coordinate(2, 2)));
		HashSet<Coordinate> set = new HashSet<Coordinate>();
		set.add(new Coordinate(2, 2));
		assertEquals(set, game.getDeadCoors());
	}

	@Test
	public void testRemovePiece2() {
		HashMap<Coordinate, AbstractPiece> removeP = new HashMap<Coordinate, AbstractPiece>();
		removeP.put(new Coordinate(5, 7), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		removeP.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		removeP.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		removeP.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		removeP.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		removeP.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		removeP.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		removeP.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		removeP.put(new Coordinate(2, 2), new Camel(Owner.Player1));
		removeP.put(new Coordinate(5, 2), new Camel(Owner.Player1));
		removeP.put(new Coordinate(5, 1), new Dog(Owner.Player1));
		Game game = new Game(new BoardState(removeP));
		Coordinate start = new Coordinate(2, 2);
		Coordinate end = start.up();
		MoveCommand move = new RegularMove(game, start, end);
		game.move(move);
		assertTrue(game.isPieceAt(end));
		assertTrue(game.getDeadCoors().isEmpty());

		start = new Coordinate(5, 1);
		end = start.up();
		move = new RegularMove(game, start, end);
		game.move(move);
		assertFalse(game.isPieceAt(new Coordinate(5, 2)));

		HashSet<Coordinate> set = new HashSet<Coordinate>();
		set.add(new Coordinate(5, 2));
		assertEquals(set, game.getDeadCoors());
	}
}
