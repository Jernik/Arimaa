package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

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

public class TestEndGame {
	private Game game;
	private Game game2;

	@Before
	public void setup() {
		HashMap<Coordinate, AbstractPiece> winP = new HashMap<Coordinate, AbstractPiece>();
		winP.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		winP.put(new Coordinate(1, 6), new Rabbit(Owner.Player1));
		winP.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		winP.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		winP.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		winP.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		winP.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		winP.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		winP.put(new Coordinate(2, 2), new Camel(Owner.Player1));
		winP.put(new Coordinate(5, 2), new Camel(Owner.Player1));
		winP.put(new Coordinate(1, 1), new Rabbit(Owner.Player2));
		winP.put(new Coordinate(5, 1), new Dog(Owner.Player1));
		game = new Game(new BoardState(winP));

		HashMap<Coordinate, AbstractPiece> winP2 = new HashMap<Coordinate, AbstractPiece>();
		winP2.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		winP2.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		winP2.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		winP2.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		winP2.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		winP2.put(new Coordinate(5, 4), new Dog(Owner.Player2));
		winP2.put(new Coordinate(4, 3), new Cat(Owner.Player2));
		winP2.put(new Coordinate(2, 2), new Camel(Owner.Player1));
		winP2.put(new Coordinate(5, 2), new Camel(Owner.Player1));
		winP2.put(new Coordinate(1, 1), new Camel(Owner.Player1));
		winP2.put(new Coordinate(4, 1), new Elephant(Owner.Player2));
		winP2.put(new Coordinate(5, 1), new Dog(Owner.Player1));
		game2 = new Game(new BoardState(winP2));
	}

	@Test
	public void testLoseWithNoMoves1() {
		HashMap<Coordinate, AbstractPiece> noMovesP1 = new HashMap<Coordinate, AbstractPiece>();
		noMovesP1.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		noMovesP1.put(new Coordinate(5, 4), new Rabbit(Owner.Player1));

		noMovesP1.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));
		noMovesP1.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));
		noMovesP1.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		noMovesP1.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		Game game = new Game(new BoardState(noMovesP1));

		assertTrue(game.move(new RegularMove(game, new Coordinate(4, 3), new Coordinate(4, 4), game.getPlayerTurn())));

		assertTrue(game.hasNoMoves(game.getPlayerTurn()));
		assertEquals(Owner.Player2, game.getWinner());
	}

	@Test
	public void testLoseWithNoMovesIfOnlyPushAvaliableButWeaker() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 3), new Dog(Owner.Player1));

		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 3), new Camel(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Camel(Owner.Player2));

		Game game = new Game(new BoardState(p));

		assertTrue(game.move(new RegularMove(game, new Coordinate(4, 3), new Coordinate(4, 4), game.getPlayerTurn())));
		assertTrue(game.move(new RegularMove(game, new Coordinate(4, 2), new Coordinate(4, 3), game.getPlayerTurn())));

		assertTrue(game.hasNoMoves(game.getPlayerTurn()));
		assertEquals(Owner.Player2, game.getWinner());
	}

	@Test
	public void testLoseWithNoMovesIfOnlyPushAvaliableButEqualStrength() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 5), new Dog(Owner.Player2));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player1));
		p.put(new Coordinate(5, 3), new Camel(Owner.Player1));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player1));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player1));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player1));

		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player1));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player1));

		p.put(new Coordinate(3, 4), new Dog(Owner.Player1));

		Game game = new Game(new BoardState(p));
		game.incrementTurn();

		assertTrue(game.move(new RegularMove(game, new Coordinate(4, 5), new Coordinate(4, 4), game.getPlayerTurn())));
		assertTrue(game.move(new RegularMove(game, new Coordinate(4, 6), new Coordinate(4, 5), game.getPlayerTurn())));

		assertTrue(game.hasNoMoves(Owner.Player2));
		assertEquals(Owner.Player1, game.getWinner());
	}

	@Test
	public void testLoseWithNoMovesIfOnlyPushAvaliableAndNotEnoughMovesLeft() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(4, 4), new Dog(Owner.Player1));
		p.put(new Coordinate(4, 0), new Rabbit(Owner.Player1));

		p.put(new Coordinate(4, 3), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 2), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 3), new Camel(Owner.Player2));
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player2));

		p.put(new Coordinate(5, 4), new Rabbit(Owner.Player2));
		p.put(new Coordinate(6, 4), new Rabbit(Owner.Player2));

		p.put(new Coordinate(4, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(5, 5), new Rabbit(Owner.Player2));
		p.put(new Coordinate(4, 6), new Rabbit(Owner.Player2));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		p.put(new Coordinate(3, 4), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		assertTrue(game.move(new RegularMove(game, new Coordinate(4, 0), new Coordinate(5, 0), game.getPlayerTurn())));
		assertEquals(Owner.Nobody, game.getWinner());

		assertTrue(game.move(new RegularMove(game, new Coordinate(5, 0), new Coordinate(5, 1), game.getPlayerTurn())));
		assertEquals(Owner.Nobody, game.getWinner());

		assertTrue(game.move(new RegularMove(game, new Coordinate(5, 1), new Coordinate(5, 2), game.getPlayerTurn())));

		assertTrue(game.hasNoMoves(Owner.Player1));
		assertEquals(Owner.Player2, game.getWinner());
	}

	@Test
	public void testPlayer1WinThroughtRabbitElimination() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(3, 3), new Rabbit(Owner.Player1));
		p.put(new Coordinate(3, 5), new Elephant(Owner.Player2));

		Game game = new Game(new BoardState(p));
		assertEquals(Owner.Nobody, game.getWinner());

		assertTrue(game.move(new RegularMove(game, new Coordinate(3, 3), new Coordinate(3, 4), game.getPlayerTurn())));

		assertEquals(Owner.Player1, game.getWinner());
	}

	@Test
	public void testPlayer2WinThroughtRabbitElimination() {
		HashMap<Coordinate, AbstractPiece> p = new HashMap<Coordinate, AbstractPiece>();
		p.put(new Coordinate(3, 3), new Elephant(Owner.Player1));
		p.put(new Coordinate(3, 5), new Rabbit(Owner.Player2));

		Game game = new Game(new BoardState(p));
		assertEquals(Owner.Nobody, game.getWinner());

		assertTrue(game.move(new RegularMove(game, new Coordinate(3, 3), new Coordinate(3, 4), game.getPlayerTurn())));

		assertEquals(Owner.Player2, game.getWinner());
	}

	// move(int, int, int) used to be row,col,dir. dir enum: 0 = up, 1 = right, 2 = down, 3 = left
	@Test
	public void testPlayer2Win() {
		game.incrementTurn();
		Coordinate start = new Coordinate(1, 1);
		Coordinate end = start.up();
		Owner owner = game.getPlayerTurn();
		MoveCommand move = new RegularMove(game, start, end, owner);
		assertTrue(game.move(move));
		assertEquals(Owner.Player2, game.getWinner());
	}

	@Test
	public void testPlayer1Win() {
		Coordinate start = new Coordinate(1, 6);
		Coordinate end = start.down();
		Owner owner = game.getPlayerTurn();
		MoveCommand move = new RegularMove(game, start, end, owner);
		assertTrue(game.move(move));
		assertEquals(Owner.Player1, game.getWinner());
	}

	@Test
	public void testWinWhenP1HasNoRabbits() {
		Coordinate start = new Coordinate(1, 1);
		Coordinate end = start.down();
		Owner owner = game2.getPlayerTurn();
		MoveCommand move = new RegularMove(game2, start, end, owner);
		game2.move(move);
		assertEquals(Owner.Player2, game2.getWinner());
	}

	@Test
	public void testCheckFriendlyAdjacentDownCase() {
		Coordinate start = new Coordinate(5, 1);
		Coordinate end = start.right();
		Owner owner = game2.getPlayerTurn();
		MoveCommand move = new RegularMove(game2, start, end, owner);
		assertTrue(game2.move(move));
	}

	@Test
	public void testEndMove() {
		Game g = new Game();
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getPlayerTurn();
		MoveCommand move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
		start = new Coordinate(0, 2);
		end = start.down();
		owner = g.getPlayerTurn();
		move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
		start = new Coordinate(0, 3);
		end = start.down();
		owner = g.getPlayerTurn();
		move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
		start = new Coordinate(0, 4);
		end = start.down();
		owner = g.getPlayerTurn();
		move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
		start = new Coordinate(1, 6);
		end = start.up();
		owner = g.getPlayerTurn();
		move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
		start = new Coordinate(1, 5);
		end = start.up();
		owner = g.getPlayerTurn();
		move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
		start = new Coordinate(1, 4);
		end = start.up();
		owner = g.getPlayerTurn();
		move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
		start = new Coordinate(1, 3);
		end = start.up();
		owner = g.getPlayerTurn();
		move = new RegularMove(g, start, end, owner);
		assertTrue(g.move(move));
	}
}
