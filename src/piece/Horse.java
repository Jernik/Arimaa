package piece;

import javax.swing.ImageIcon;

public class Horse extends AbstractPiece {
	public Horse(Owner owner) {
		super(null, owner, 3);
		loadImage(owner);
	}

	public void loadImage(Owner owner) {
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon("resources/" + color + " horse.png").getImage());
	}
}
