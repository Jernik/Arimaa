package game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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
		Owner owner = standardStart.getOwner();
		MoveCommand move = new RegularMove(standardStart.getBoardState(), start, end, owner,
				standardStart.getNumMoves());
		standardStart.move(move);
		assertEquals(new Rabbit(Owner.Player1), standardStart.getPieceAt(end));
		standardStart.undoMove();
		assertEquals(standardStart.getPieceAt(new Coordinate(0, 1)), standardStart.getPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testUndoTwoMoves() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		start = new Coordinate(0, 2);
		end = start.down();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(end));
		g.undoMove();
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testUndoThreeMoves() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		start = new Coordinate(0, 2);
		end = start.down();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		start = new Coordinate(0, 3);
		end = start.down();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(end));
		g.undoMove();
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 1)));
	}

	@Test
	public void testThatUndoCantCrossTurns() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		start = new Coordinate(0, 2);
		end = start.down();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		start = new Coordinate(0, 3);
		end = start.down();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		start = new Coordinate(0, 4);
		end = start.down();
		move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		g.undoMove();
		assertEquals(new Rabbit(Owner.Player1), g.getPieceAt(new Coordinate(0, 5)));
	}

	@Test
	public void testThatUndoGrantsMoves() {
		Coordinate start = new Coordinate(0, 1);
		Coordinate end = start.down();
		Owner owner = g.getOwner();
		MoveCommand move = new RegularMove(g.getBoardState(), start, end, owner, g.getNumMoves());
		g.move(move);
		g.undoMove();
		assertEquals(4, g.getNumMoves());
	}

	@Test
	public void testSetWinner() {
		g.setWinner(1);
		assertEquals(1, g.getWinner());
	}
}
