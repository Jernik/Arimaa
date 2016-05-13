package game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import board.Coordinate;
import move_commands.MoveCommand;
import move_commands.RegularMove;
import piece.Owner;
import piece.Rabbit;

public class TestUndo {
	private Game g;

	@Before
	public void setup() {
		g = new Game();
	}

	@Test
	public void testBaseUndoCase() {
		Game standardStart = new Game();
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = standardStart.getPlayerTurn();
		MoveCommand move = new RegularMove(standardStart, start, end, owner);
		standardStart.move(move);
		assertEquals(new Rabbit(Owner.Player1), standardStart.getPieceAt(end));
		standardStart.undoMove();
		assertEquals(standardStart.getPieceAt(new Coordinate(0, 1)), standardStart.getPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testUndoTwoMoves() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getPlayerTurn();
		MoveCommand move = new RegularMove(g, start, end, owner);
		g.move(move);
		start = new Coordinate(0, 2);
		end = start.down();
		move = new RegularMove(g, start, end, owner);
		g.move(move);
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(end));
		g.undoMove();
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testUndoThreeMoves() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getPlayerTurn();
		MoveCommand move = new RegularMove(g, start, end, owner);
		g.move(move);
		start = new Coordinate(0, 2);
		end = start.down();
		move = new RegularMove(g, start, end, owner);
		g.move(move);
		start = new Coordinate(0, 3);
		end = start.down();
		move = new RegularMove(g, start, end, owner);
		g.move(move);
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(end));
		g.undoMove();
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testThatUndoCantCrossTurns() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getPlayerTurn();
		MoveCommand move = new RegularMove(g, start, end, owner);
		g.move(move);
		start = new Coordinate(0, 2);
		end = start.down();
		move = new RegularMove(g, start, end, owner);
		g.move(move);
		start = new Coordinate(0, 3);
		end = start.down();
		move = new RegularMove(g, start, end, owner);
		g.move(move);
		start = new Coordinate(0, 4);
		end = start.down();
		move = new RegularMove(g, start, end, owner);
		g.move(move);
		g.undoMove();
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 5)));
	}

	@Test
	public void testThatUndoGrantsMoves() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getPlayerTurn();
		MoveCommand move = new RegularMove(g, start, end, owner);
		g.move(move);
		g.undoMove();
		assertEquals(4, g.getNumMoves());
	}
}
