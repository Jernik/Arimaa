package move_commands;

import board.Coordinate;

public class CoordinatePair {
	private Coordinate from;
	private Coordinate to;

	public CoordinatePair(Coordinate from, Coordinate to) {
		this.from = from;
		this.to = to;
	}

	public Coordinate getFrom() {
		return from;
	}

	public Coordinate getTo() {
		return to;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CoordinatePair)) {
			return false;
		}
		CoordinatePair c = (CoordinatePair) o;
		return this.from.equals(c.getFrom()) && this.to.equals(c.getTo());
	}
	
	@Override
	public int hashCode() {
		return this.from.hashCode() + this.to.hashCode();
	}
}
