package piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import org.junit.Test;

public class TestPieceEquality {
	@Test
	public void testEqualsReturnsFalseForNull() {
		AbstractPiece p = new Elephant(Owner.Player1);
		assertNotEquals(p, null);
	}

	@Test
	public void testEqualsReflexive() {
		AbstractPiece p = new Elephant(Owner.Player1);
		assertEquals(p, p);
		assertEquals(p.hashCode(), p.hashCode());
	}
	
	@Test
	public void testEqualsSymmetric() {
		AbstractPiece p1 = new Elephant(Owner.Player1);
		AbstractPiece p2 = new Elephant(Owner.Player1);
		assertEquals(p1, p2);
		assertEquals(p2, p1);
		assertEquals(p1.hashCode(), p2.hashCode());

	}
	
	@Test
	public void testEqualsReturnsFalseForOtherObject() {
		AbstractPiece p = new Elephant(Owner.Player1);
		ArrayList<Integer> notAPiece = new ArrayList<Integer>();
		assertFalse(p.equals(notAPiece));
	}

	@Test
	public void testComparatorChecksOwners() {
		assertNotEquals(new Rabbit(Owner.Player1), new Rabbit(Owner.Player2));
		assertEquals(new Rabbit(Owner.Player2), new Rabbit(Owner.Player2));

		assertNotEquals(new Rabbit(Owner.Player1).hashCode(), new Rabbit(Owner.Player2).hashCode());
		assertEquals(new Rabbit(Owner.Player2).hashCode(), new Rabbit(Owner.Player2).hashCode());
	}

	@Test
	public void testComparatorChecksType() {
		assertNotEquals(new Rabbit(Owner.Player1), new Camel(Owner.Player1));
		assertEquals(new Rabbit(Owner.Player1), new Rabbit(Owner.Player1));

		assertNotEquals(new Cat(Owner.Player1), new Camel(Owner.Player1));
		assertEquals(new Cat(Owner.Player1), new Cat(Owner.Player1));

		assertNotEquals(new Dog(Owner.Player1), new Camel(Owner.Player1));
		assertEquals(new Dog(Owner.Player1), new Dog(Owner.Player1));

		assertNotEquals(new Elephant(Owner.Player1), new Camel(Owner.Player1));
		assertEquals(new Elephant(Owner.Player1), new Elephant(Owner.Player1));

		assertNotEquals(new Horse(Owner.Player1), new Camel(Owner.Player1));
		assertEquals(new Horse(Owner.Player1), new Horse(Owner.Player1));

		assertNotEquals(new Camel(Owner.Player1), new Cat(Owner.Player1));
		assertEquals(new Camel(Owner.Player1), new Camel(Owner.Player1));

		// hashcode testing
		assertNotEquals(new Rabbit(Owner.Player1).hashCode(), new Camel(Owner.Player1).hashCode());
		assertEquals(new Rabbit(Owner.Player1).hashCode(), new Rabbit(Owner.Player1).hashCode());

		assertNotEquals(new Cat(Owner.Player1).hashCode(), new Camel(Owner.Player1).hashCode());
		assertEquals(new Cat(Owner.Player1).hashCode(), new Cat(Owner.Player1).hashCode());

		assertNotEquals(new Dog(Owner.Player1).hashCode(), new Camel(Owner.Player1).hashCode());
		assertEquals(new Dog(Owner.Player1).hashCode(), new Dog(Owner.Player1).hashCode());

		assertNotEquals(new Elephant(Owner.Player1).hashCode(), new Camel(Owner.Player1).hashCode());
		assertEquals(new Elephant(Owner.Player1).hashCode(), new Elephant(Owner.Player1).hashCode());

		assertNotEquals(new Horse(Owner.Player1).hashCode(), new Camel(Owner.Player1).hashCode());
		assertEquals(new Horse(Owner.Player1).hashCode(), new Horse(Owner.Player1).hashCode());

		assertNotEquals(new Camel(Owner.Player1).hashCode(), new Cat(Owner.Player1).hashCode());
		assertEquals(new Camel(Owner.Player1).hashCode(), new Camel(Owner.Player1).hashCode());
	}

}
