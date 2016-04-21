package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.Coordinate;
import game.GUI;
import game.Game;
import game.ImagePanel;
import move_commands.RegularMove;

public class MovementListener implements MouseListener {
	private final Game game;
	ImagePanel selectedPiece;
	Coordinate selectedPieceCoord;
	ImagePanel secondSelectedPiece;
	Coordinate secondSelectedPieceCoord;
	private GUI gui;

	public MovementListener(GUI gui) {
		this.game = gui.game;
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

		Coordinate coor = new Coordinate((sourceX - 10) / 80, (sourceY - 10) / 80);
		// Beginning movement, nothing yet selected
		// Selecting piece to interact with
		if (clickOnBoard(coor)) {
			// No piece has been selected yet
			if (noPieceSelectedAndPieceClicked(coor)) {
				this.selectedPiece = gui.boardPieces[coor.getX()][coor.getY()];
				this.selectedPieceCoord = new Coordinate(coor);
			}

			// If a piece is selected and an empty space is clicked
			// AKA move
			else if (isSelectedPieceAndEmptySpaceClicked(coor)) {
				// Using move to check for valid move
				if (game.move(this.game.getBoardState(), this.selectedPieceCoord, coor)) {
					gui.renderBoard();
				}
				this.selectedPiece = null;
				this.secondSelectedPieceCoord = null;
				this.secondSelectedPiece = null;
				this.secondSelectedPieceCoord = null;

			}

			// Piece already selected, clicked a second piece
			else if (pieceSelectedAndSecondPieceClicked(coor)) {
				this.secondSelectedPiece = gui.boardPieces[coor.getX()][coor.getY()];
				this.secondSelectedPieceCoord = new Coordinate(coor);

				// Piece selected, Second piece selected, empty square
				// selected
			} else if (twoPieceSelectedAndEmptySpaceClicked(coor)) {

				if (game.pushOrPull(this.selectedPieceCoord, this.secondSelectedPieceCoord, coor)) {
					gui.renderBoard();
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
				&& gui.boardPieces[coor.getX()][coor.getY()] == null;
	}

	private boolean pieceSelectedAndSecondPieceClicked(Coordinate coor) {
		return this.selectedPiece != null && this.secondSelectedPiece == null
				&& gui.boardPieces[coor.getX()][coor.getY()] != null
				&& this.selectedPiece != gui.boardPieces[coor.getX()][coor.getY()];
	}

	private boolean isSelectedPieceAndEmptySpaceClicked(Coordinate coor) {
		return this.selectedPiece != null && this.secondSelectedPiece == null
				&& gui.boardPieces[coor.getX()][coor.getY()] == null;
	}

	private boolean noPieceSelectedAndPieceClicked(Coordinate coor) {
		return gui.boardPieces[coor.getX()][coor.getY()] != null && this.selectedPiece == null
				&& this.secondSelectedPiece == null;
	}

	private boolean clickOnBoard(Coordinate coor) {
		return coor.getX() <= 7 && coor.getX() >= 0 && coor.getY() <= 7 && coor.getY() >= 0;
	}
}