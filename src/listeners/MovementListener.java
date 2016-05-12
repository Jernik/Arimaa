package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import board.Coordinate;
import game.Game;
import gui.GUI;
import gui.ImagePanel;
import move_commands.MoveCommand;
import move_commands.PullMove;
import move_commands.PushMove;
import move_commands.RegularMove;

public class MovementListener implements MouseListener {
	private final Game game;
	ImagePanel selectedPiece;
	Coordinate selectedPieceCoord;
	ImagePanel secondSelectedPiece;
	Coordinate secondSelectedPieceCoord;
	private GUI gui;

	public MovementListener(GUI gui) {
		this.game = gui.getGame();
		this.gui = gui;
		this.selectedPiece = null;
		this.secondSelectedPiece = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int sourceX = (int) e.getPoint().getX();
		int sourceY = (int) e.getPoint().getY();

		if (game.isAiTurn()) {
			return;
		}

		Coordinate coor = new Coordinate((sourceX - 10) / 80, (sourceY - 10) / 80);
		// Beginning movement, nothing yet selected
		// Selecting piece to interact with
		if (clickOnBoard(coor)) {
			// No piece has been selected yet
			if (noPieceSelectedAndPieceClicked(coor)) {
				this.selectedPiece = gui.getBoardPieces().get(coor);
				this.selectedPieceCoord = new Coordinate(coor);
			}

			// If a piece is selected and an empty space is clicked
			// AKA move
			else if (isSelectedPieceAndEmptySpaceClicked(coor)) {
				// Using move to check for valid move
				RegularMove m = new RegularMove(game, this.selectedPieceCoord, coor, this.game.getPlayerTurn());
				if (game.move(m)) {
					gui.rerenderBoard();
				}
				this.selectedPiece = null;
				this.secondSelectedPieceCoord = null;
				this.secondSelectedPiece = null;
				this.secondSelectedPieceCoord = null;

			}

			// Piece already selected, clicked a second piece
			else if (pieceSelectedAndSecondPieceClicked(coor)) {
				this.secondSelectedPiece = gui.getBoardPieces().get(coor);
				this.secondSelectedPieceCoord = new Coordinate(coor);

				// Piece selected, Second piece selected, empty square
				// selected
			} else if (twoPieceSelectedAndEmptySpaceClicked(coor)) {
				MoveCommand move = null;
				if (this.secondSelectedPieceCoord.isOrthogonallyAdjacentTo(coor)) {
					move = new PushMove(game, this.selectedPieceCoord, this.secondSelectedPieceCoord, coor,
							game.getPlayerTurn());
				} else {
					move = new PullMove(game, this.selectedPieceCoord, coor, this.secondSelectedPieceCoord,
							game.getPlayerTurn());
				}
				if (game.move(move)) {
					gui.rerenderBoard();
				}

				this.selectedPiece = null;
				this.selectedPieceCoord = null;
				this.secondSelectedPiece = null;
				this.secondSelectedPieceCoord = null;
			}

			// Invalid selection, clear data
			else {
				this.selectedPiece = null;
				this.selectedPieceCoord = null;
				this.secondSelectedPiece = null;
				this.secondSelectedPieceCoord = null;
			}
		}
	}

	private boolean twoPieceSelectedAndEmptySpaceClicked(Coordinate coor) {
		return this.selectedPiece != null && this.secondSelectedPiece != null
				&& !gui.getBoardPieces().containsKey(coor);
	}

	private boolean pieceSelectedAndSecondPieceClicked(Coordinate coor) {
		return this.selectedPiece != null && this.secondSelectedPiece == null && gui.getBoardPieces().containsKey(coor)
				&& this.selectedPiece != gui.getBoardPieces().get(coor);
	}

	private boolean isSelectedPieceAndEmptySpaceClicked(Coordinate coor) {
		return this.selectedPiece != null && this.secondSelectedPiece == null
				&& !gui.getBoardPieces().containsKey(coor);
	}

	private boolean noPieceSelectedAndPieceClicked(Coordinate coor) {
		return gui.getBoardPieces().containsKey(coor) && this.selectedPiece == null && this.secondSelectedPiece == null;
	}

	private boolean clickOnBoard(Coordinate coor) {
		return coor.isValid();
	}
}