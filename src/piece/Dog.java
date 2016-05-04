package piece;

import javax.swing.ImageIcon;

public class Dog extends AbstractPiece {
	public Dog(Owner owner) {
		super(null, owner, 2);
		loadImage(owner);
	}

	public void loadImage(Owner owner) {
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon("resources/" + color + " dog.png").getImage());
	}
}
