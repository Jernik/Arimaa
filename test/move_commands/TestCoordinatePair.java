package move_commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import board.Coordinate;

public class TestCoordinatePair {
	@Test
	public void testEqualsReturnsFalseForNull() {
		CoordinatePair pair = new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 0));
		assertNotEquals(pair, null);
	}

	@Test
	public void testEqualsReturnsFalseForNonCoordinatePair() {
		CoordinatePair pair = new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 0));
		assertNotEquals(pair, new Coordinate(0, 0));
	}

	@Test
	public void testEqualsReturnsTrueForReflexive() {
		CoordinatePair pair = new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 0));

		assertEquals(pair, pair);
		assertEquals(pair.hashCode(), pair.hashCode());
	}

	@Test
	public void testEqualsReturnsTrueForSymmetric() {
		CoordinatePair pair1 = new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 0));
		CoordinatePair pair2 = new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 0));

		assertEquals(pair1, pair2);
		assertEquals(pair2, pair1);

		assertEquals(pair1.hashCode(), pair2.hashCode());
	}

	@Test
	public void testEqualsReturnsFalseIfFromIsDifferent() {
		CoordinatePair pair1 = new CoordinatePair(new Coordinate(1, 1), new Coordinate(1, 0));
		CoordinatePair pair2 = new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 0));

		assertNotEquals(pair1, pair2);

		assertNotEquals(pair1.hashCode(), pair2.hashCode());
	}

	@Test
	public void testEqualsReturnsFalseIfToIsDifferent() {
		CoordinatePair pair1 = new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 0));
		CoordinatePair pair2 = new CoordinatePair(new Coordinate(0, 0), new Coordinate(0, 1));

		assertNotEquals(pair1, pair2);

		assertNotEquals(pair1.hashCode(), pair2.hashCode());
	}
}
