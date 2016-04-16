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
		// TODO: Refactor all uses of rowClicked/columnClicked to use coor
		// instead.
		int sourceX = (int) e.getPoint().getX();
		int sourceY = (int) e.getPoint().getY();

		Coordinate coor = new Coordinate((sourceX - 10) / 80, 7 - (sourceY - 10) / 80);
		// Beginning movement, nothing yet selected
		// Selecting piece to interact with
		if (clickOnBoard(coor.getX(), coor.getY())) {
			// No piece has been selected yet
			if (noPieceSelectedAndPieceClicked(coor.getX(), coor.getY())) {
				this.selectedPiece = gui.boardPieces[coor.getX()][coor.getY()];
				this.selectedPieceCoord = new Coordinate(coor.getX(), coor.getY());
			}

			// If a piece is selected and an empty space is clicked
			// AKA move
			else if (isSelectedPieceAndEmptySpaceClicked(coor.getX(), coor.getY())) {
				// Using move to check for valid move
				if (game.move(new RegularMove(this.game.getBoardState(), this.selectedPieceCoord, coor))) {
					gui.renderBoard();
				}
				this.selectedPiece = null;
				this.secondSelectedPiece = null;

			}

			// Piece already selected, clicked a second piece
			else if (pieceSelectedAndSecondPieceClicked(coor.getX(), coor.getY())) {
				this.secondSelectedPiece = gui.boardPieces[coor.getX()][coor.getY()];

				// Piece selected, Second piece selected, empty square
				// selected
			} else if (twoPieceSelectedAndEmptySpaceClicked(coor.getX(), coor.getY())) {

				if (checkForPull(coor.getX(), coor.getY())) {
					int calculatedDirection = moveDirection(selectedPiece, coor.getX(), coor.getY());

					if (game.pull(this.selectedPiece.getRow(), this.selectedPiece.getColumn(),
							this.secondSelectedPiece.getRow(), this.secondSelectedPiece.getColumn(),
							calculatedDirection)) {
						gui.renderBoard();

					}
					this.selectedPiece = null;
					this.secondSelectedPiece = null;

				} else if (checkForPush(coor.getX(), coor.getY())) {
					int calculatedDirection1 = moveDirectionOnePush(selectedPiece, secondSelectedPiece);

					int calculatedDirection2 = moveDirectionTwoPush(secondSelectedPiece, coor.getX(), coor.getY());

					if (game.push(this.selectedPiece.getRow(), this.selectedPiece.getColumn(), calculatedDirection1,
							calculatedDirection2)) {
						gui.renderBoard();

					}
					this.selectedPiece = null;
					this.secondSelectedPiece = null;
				}
			}

			// Invalid selection, clear data
			else {
				this.selectedPiece = null;
				this.secondSelectedPiece = null;
			}
		}

	}

	private int moveDirectionTwoPush(ImagePanel secondSelectedPiece2, int rowClicked, int columnClicked) {
		if (secondSelectedPiece.getRow() - 1 == rowClicked && secondSelectedPiece.getColumn() == columnClicked)
			return 0;
		else if (secondSelectedPiece.getColumn() + 1 == columnClicked && secondSelectedPiece.getRow() == rowClicked)
			return 1;
		else if (secondSelectedPiece.getRow() + 1 == rowClicked && secondSelectedPiece.getColumn() == columnClicked)
			return 2;
		else if (secondSelectedPiece.getColumn() - 1 == columnClicked && secondSelectedPiece.getRow() == rowClicked)
			return 3;
		return -1; // Shouldn't ever happen
	}

	private int moveDirectionOnePush(ImagePanel selectedPiece2, ImagePanel secondSelectedPiece2) {
		if (selectedPiece.getRow() - 1 == secondSelectedPiece.getRow()
				&& selectedPiece.getColumn() == secondSelectedPiece.getColumn())
			return 0;
		else if (selectedPiece.getColumn() + 1 == secondSelectedPiece.getColumn()
				&& selectedPiece.getRow() == secondSelectedPiece.getRow())
			return 1;
		else if (selectedPiece.getRow() + 1 == secondSelectedPiece.getRow()
				&& selectedPiece.getColumn() == secondSelectedPiece.getColumn())
			return 2;
		else if (selectedPiece.getColumn() - 1 == secondSelectedPiece.getColumn()
				&& selectedPiece.getRow() == secondSelectedPiece.getRow())
			return 3;
		return -1; // Shouldn't ever happen
	}

	private boolean twoPieceSelectedAndEmptySpaceClicked(int rowClicked, int columnClicked) {
		return this.selectedPiece != null && this.secondSelectedPiece != null
				&& gui.boardPieces[rowClicked][columnClicked] == null;
	}

	private boolean pieceSelectedAndSecondPieceClicked(int rowClicked, int columnClicked) {
		return this.selectedPiece != null && this.secondSelectedPiece == null
				&& gui.boardPieces[rowClicked][columnClicked] != null
				&& this.selectedPiece != gui.boardPieces[rowClicked][columnClicked];
	}

	private int moveDirection(ImagePanel selectedPiece2, int rowClicked, int columnClicked) {
		if (selectedPiece.getRow() - 1 == rowClicked && selectedPiece.getColumn() == columnClicked)
			return 0;
		else if (selectedPiece.getColumn() + 1 == columnClicked && selectedPiece.getRow() == rowClicked)
			return 1;
		else if (selectedPiece.getRow() + 1 == rowClicked && selectedPiece.getColumn() == columnClicked)
			return 2;
		else if (selectedPiece.getColumn() - 1 == columnClicked && selectedPiece.getRow() == rowClicked)
			return 3;
		return -1; // Please never happen...
	}

	private boolean isSelectedPieceAndEmptySpaceClicked(int rowClicked, int columnClicked) {
		return this.selectedPiece != null && this.secondSelectedPiece == null
				&& gui.boardPieces[rowClicked][columnClicked] == null;
	}

	private boolean noPieceSelectedAndPieceClicked(int rowClicked, int columnClicked) {
		return gui.boardPieces[rowClicked][columnClicked] != null && this.selectedPiece == null
				&& this.secondSelectedPiece == null;
	}

	private boolean clickOnBoard(int rowClicked, int columnClicked) {
		return rowClicked <= 7 && rowClicked >= 0 && columnClicked <= 7 && columnClicked >= 0;
	}

	private boolean checkForPush(int rowClicked, int columnClicked) {
		if (this.secondSelectedPiece.getRow() + 1 == rowClicked
				&& this.secondSelectedPiece.getColumn() == columnClicked) {
			return true;
		}
		if (this.secondSelectedPiece.getRow() - 1 == rowClicked
				&& this.secondSelectedPiece.getColumn() == columnClicked) {
			// numMoves-=2;
			return true;
		}
		if (this.secondSelectedPiece.getRow() == rowClicked
				&& this.secondSelectedPiece.getColumn() + 1 == columnClicked) {
			// numMoves-=2;
			return true;
		}
		if (this.secondSelectedPiece.getRow() == rowClicked
				&& this.secondSelectedPiece.getColumn() - 1 == columnClicked) {
			// numMoves-=2;
			return true;
		}
		return false;
	}

	private boolean checkForPull(int rowClicked, int columnClicked) {
		if (this.selectedPiece.getRow() + 1 == rowClicked && this.selectedPiece.getColumn() == columnClicked) {
			// numMoves-=2;
			return true;
		}
		if (this.selectedPiece.getRow() - 1 == rowClicked && this.selectedPiece.getColumn() == columnClicked) {
			// numMoves-=2;
			return true;
		}
		if (this.selectedPiece.getRow() == rowClicked && this.selectedPiece.getColumn() + 1 == columnClicked) {
			// numMoves--;
			return true;
		}
		if (this.selectedPiece.getRow() == rowClicked && this.selectedPiece.getColumn() - 1 == columnClicked) {
			// numMoves--;
			return true;
		}
		return false;
	}
}