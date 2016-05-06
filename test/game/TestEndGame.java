package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

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

	// move(int, int, int) used to be row,col,dir. dir enum: 0 = up, 1 = right, 2 = down, 3 = left
	@Test
	public void testPlayer2Win() {
		game.setPlayerTurn(2);
		Coordinate start = new Coordinate(1, 1);
		Coordinate end = start.up();
		Owner owner = game.getOwner();
		MoveCommand move = new RegularMove(game.getBoardState(), start, end, owner, game.getNumMoves());
		assertTrue(game.move(move));
		assertEquals(2, game.getWinner());
	}

	@Test
	public void testPlayer1Win() {
		Coordinate start = new Coordinate(1, 6);
		Coordinate end = start.down();
		Owner owner = game.getOwner();
		MoveCommand move = new RegularMove(game.getBoardState(), start, end, owner, game.getNumMoves());
		assertTrue(game.move(move));
		assertEquals(1, game.getWinner());
	}

	@Test
	public void testWinWhenP1HasNoRabbits() {
		Coordinate start = new Coordinate(1, 1);
		Coordinate end = start.down();
		Owner owner = game2.getOwner();
		MoveCommand move = new RegularMove(game2.getBoardState(), start, end, owner, game2.getNumMoves());
		game2.move(move);
		assertEquals(2, game2.getWinner());
	}

	@Test
	public void testCheckFriendlyAdjacentDownCase() {
		Coordinate start = new Coordinate(5, 1);
		Coordinate end = start.right();
		Owner owner = game2.getOwner();
		MoveCommand move = new RegularMove(game2.getBoardState(), start, end, owner, game2.getNumMoves());
		assertTrue(game2.move(move));
	}

	@Test
	public void testEndMove() {
		Game g = new Game();
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
		start = new Coordinate(0, 2);
		end = start.down();
		owner = g.getOwner();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
		start = new Coordinate(0, 3);
		end = start.down();
		owner = g.getOwner();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
		start = new Coordinate(0, 4);
		end = start.down();
		owner = g.getOwner();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
		start = new Coordinate(1, 6);
		end = start.up();
		owner = g.getOwner();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
		start = new Coordinate(1, 5);
		end = start.up();
		owner = g.getOwner();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
		start = new Coordinate(1, 4);
		end = start.up();
		owner = g.getOwner();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
		start = new Coordinate(1, 3);
		end = start.up();
		owner = g.getOwner();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		assertTrue(g.move(move));
	}
}
