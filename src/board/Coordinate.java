package board;

import java.io.Serializable;

public class Coordinate implements Serializable {
	private static final long serialVersionUID = 1938906095938960188L;
	private int x;
	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		if (!this.isValid()) {
			this.x = -1;
			this.y = -1;
		}
	}

	public Coordinate(Coordinate coor) {
		this.x = coor.getX();
		this.y = coor.getY();
	}

	private boolean isValidValue(int value) {
		return value < BoardState.MAX_BOARD_SIZE && value > -1;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean isValid() {
		return isValidValue(this.x) && isValidValue(this.y);
	}

	public Coordinate down() {
		return new Coordinate(x, y + 1);
	}

	public Coordinate up() {
		return new Coordinate(x, y - 1);
	}

	public Coordinate left() {
		return new Coordinate(x - 1, y);
	}

	public Coordinate right() {
		return new Coordinate(x + 1, y);
	}

	/**
	 * 
	 * @param other
	 *            Coordinate to be compared to
	 * @return Returns true if both the caller Coordinate and parameter Coordinate are within one position of each other
	 *         relative to a single board tile in either the X or Y direction. Returns false if the two objects are
	 *         equivalent.
	 */
	public boolean isOrthogonallyAdjacentTo(Coordinate other) {
		return !this.equals(other) && (isHorizontal(other) || isVertical(other)) && this.isValid() && other.isValid();
	}

	private boolean isHorizontal(Coordinate other) {
		return (Math.abs(this.getX() - other.getX()) <= 1 && this.getY() == other.getY());
	}

	private boolean isVertical(Coordinate other) {
		return (this.getX() == other.getX() && Math.abs(this.getY() - other.getY()) <= 1);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Coordinate) {
			Coordinate other = (Coordinate) obj;
			return this.isValid() == other.isValid() && this.x == other.getX() && this.y == other.getY();
		}
		return false;
	}

	public int hashCode() {
		return Integer.hashCode(this.x) * 1000 + Integer.hashCode(this.y) * 10000;
	}

	public String toString() {
		return "(" + this.getX() + ", " + this.getY() + ")";
	}
}
