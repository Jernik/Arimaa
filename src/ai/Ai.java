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
		return this.generateRandomMoveCommand(() -> this.generateRandomPieceCoor());
	}

	// default scope
	// always returns a valid coor of one of the ai's pieces
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

	// default scope
	MoveCommand generateRandomMoveCommand(returnsCoordinate pieceGenerator) {
		// given a coordinate, get the piece, pick a direction
		// if nothing, great -> regular move or pull
		// if something -> push
		Coordinate pieceCoor = pieceGenerator.execute();
		AbstractPiece piece = this.game.getPieceAt(pieceCoor);
		Coordinate randomDirection = this.generateRandomDirection(pieceCoor);
		return null;
	}
	
	// default scope
	Coordinate generateRandomDirection(Coordinate coor) {
		ArrayList<Coordinate> adjecantCoors = new ArrayList<Coordinate>(
				Arrays.asList(new Coordinate[] { coor.down(), coor.up(), coor.left(), coor.right() }));
		// adjecantCoors.removeIf((Coordinate coor) -> this.game.getPieceAt(coor).getOwner() == this.owner);
		return adjecantCoors.get(new Random().nextInt(adjecantCoors.size()));
	}
}
