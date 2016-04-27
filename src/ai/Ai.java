package ai;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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

	public Ai(Owner owner, Game game) {
		this.owner = owner;
		this.game = game;
	}

	public MoveCommand generateMove() {
		return this.generateRandomMoveCommand(this.generateRandomPiece());
	}
	
	public AbstractPiece generateRandomPiece() {
		Set<Coordinate> coors = this.game.currentBoard.getAllCoordinates();
		int randomIndex = new Random().nextInt(coors.size());
		Iterator<Coordinate> iter = coors.iterator();
		for (int i = 0; i < (randomIndex); i++) {
			iter.next();
		}
		Coordinate randomCoor = iter.next();
		AbstractPiece randomPiece = this.game.getPieceAt(randomCoor);
		if (randomPiece.getOwner() == this.owner) {
			return randomPiece;
		}
		return this.generateRandomPiece();
	}

	public MoveCommand generateRandomMoveCommand(AbstractPiece piece) {
		MoveCommand move = null;
		switch (MoveType.getRandomMoveType()) {
		case Regular:
			// regular
			break;
		case Push:
			// push
			break;
		case Pull:
			// pull
			break;
		}
		return move;
	}
}
