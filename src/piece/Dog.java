package piece;

import javax.swing.ImageIcon;

public class Dog extends AbstractPiece {
	private static final long serialVersionUID = -1787795402145758498L;

	public Dog(Owner owner) {
		super(null, owner, 2);
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon("resources/" + color + " dog.png"));
	}
}
