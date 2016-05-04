package ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import game.Coordinate;
import game.Game;
import move_commands.MoveCommand;
import move_commands.PullMove;
import move_commands.PushMove;
import move_commands.RegularMove;
import piece.Owner;

public class Ai {
	public static final long HARD_TIME_LIMIT = 100_000_000;// ns, 100 miliseconds
	public static final long AVERAGE_TIME_LIMIT = 500_000;// ns, 0.5 miliseconds
	private Owner owner;
	private Game game;

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

	/**
	 * this should uniformally generate a valid move command.
	 * 
	 * @return
	 */
	public MoveCommand generateMove() {
		Coordinate pieceCoor = this.generateRandomPieceCoor();
		Coordinate randomDirection = this.generateRandomDirection(pieceCoor);
		if (!randomDirection.isValid()) {
			// can't move here because its off the board, try again
			return this.generateMove();
		}
		if (this.game.checkCoor(randomDirection)) {
			if (this.game.getPieceAt(randomDirection).getOwner() == this.owner) {
				// can't dislodge a friendly piece, try again
				return this.generateMove();
			}
			if (!shouldGeneratePull()) {
				// tryed to do a regular move and it didn't work
				return this.generateMove();
			}
			// enemy piece is here, generate a push
			Coordinate pushPiecePlace = this.generateRandomDirection(randomDirection);
			while (pieceCoor.equals(pushPiecePlace)) {
				// you can't swap
				pushPiecePlace = this.generateRandomDirection(randomDirection);
			}
			MoveCommand pushMove = new PushMove(this.game.getBoardState(), pieceCoor, randomDirection, pushPiecePlace,
					this.owner);
			if (pushMove.isValidMove()) {
				return pushMove;
			}
			// wasn't a valid push, try again
			return this.generateMove();
		}
		// free space, generate a pull or regular move
		if (shouldGeneratePull()) {
			Coordinate pullPiecePlace = this.generateRandomDirection(randomDirection);
			while (pieceCoor.equals(pullPiecePlace)) {
				// you can't swap
				pullPiecePlace = this.generateRandomDirection(randomDirection);
			}
			MoveCommand pullMove = new PullMove(this.game.getBoardState(), pieceCoor, randomDirection, pullPiecePlace,
					this.owner);
			if (pullMove.isValidMove()) {
				return pullMove;
			}
			// wasn't a valid pull, try again
			return this.generateMove();
		}
		// use regular method
		MoveCommand regularMove = new RegularMove(this.game.getBoardState(), pieceCoor, randomDirection, this.owner);
		if (regularMove.isValidMove()) {
			return regularMove;
		}
		// invalid regular move, try again
		return this.generateMove();
	}

	// 1 / 4 chance of generating pull, 3 possible pull moves, and 1 regular move
	private boolean shouldGeneratePull() {
		return 0 != new Random().nextInt(4);
	}

	/**
	 * always returns the valid coordinate for one of the ai's pieces
	 * 
	 * @return
	 */
	// default scope
	Coordinate generateRandomPieceCoor() {
		Set<Coordinate> coors = new HashSet<Coordinate>(this.game.currentBoard.getAllCoordinates());
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
