package ai;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import game.Coordinate;
import game.Game;
import move_commands.MoveCommand;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Owner;

public class Ai {
	private Owner owner;
	private Game game;

	enum MoveType {
		Regular, Push, Pull;
		static MoveType getRandomMoveType() {
			return MoveType.values()[MoveType.values().length];
		}
	}

	interface returnsCoordinate {
		public Coordinate execute();
	}

	interface returnsAbstractPiece {
		public AbstractPiece execute();
	}

	public Ai(Owner owner, Game game) {
		this.owner = owner;
		this.game = game;
	}

	public Owner getOwner() {
		return owner;
	}

	public Game getGame() {
		return game;
	}

	public MoveCommand generateMove() {
		Coordinate pieceCoor = this.generateRandomPieceCoor();
		AbstractPiece piece = this.game.getPieceAt(pieceCoor);
		Coordinate randomDirection = this.generateRandomDirection(pieceCoor);
		if (!randomDirection.isValid() || this.game.getPieceAt(randomDirection).getOwner() == this.owner) {
			// can't move here because its off the board, or there is one of the ai's pieces there, try again
			return this.generateMove();
		}
		if (this.game.checkCoor(randomDirection)) {
			// enemy piece is here
			// do push
			return null;
		}
		// free space
		// do move or pull
		RegularMove regularMove = new RegularMove(this.game.getBoardState(), pieceCoor, randomDirection, this.owner);
		return regularMove;
	}

	/**
	 * always returns the valid coordinate for one of the ai's pieces
	 * 
	 * @return
	 */
	// default scope
	Coordinate generateRandomPieceCoor() {
		Set<Coordinate> coors = this.game.currentBoard.getAllCoordinates();
		// removes all non ai controlled pieces
		coors.removeIf((Coordinate coor) -> this.game.getPieceAt(coor).getOwner() != this.owner);

		int randomIndex = new Random().nextInt(coors.size());
		Iterator<Coordinate> iter = coors.iterator();
		for (int i = 0; i < (randomIndex); i++) {
			iter.next();
		}
		Coordinate randomCoor = iter.next();
		return randomCoor;
	}

	/**
	 * uniformally returns a coordinate that is adjacent to the given coordinate. does not check to see if the
	 * coordinate is valid or if there is a piece there
	 * 
	 * @param coor
	 * @return
	 */
	// default scope
	Coordinate generateRandomDirection(Coordinate coor) {
		ArrayList<Coordinate> adjecantCoors = new ArrayList<Coordinate>(
				Arrays.asList(new Coordinate[] { coor.down(), coor.up(), coor.left(), coor.right() }));
		return adjecantCoors.get(new Random().nextInt(adjecantCoors.size()));
	}
}
