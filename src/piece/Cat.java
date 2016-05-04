package piece;

import javax.swing.ImageIcon;

public class Cat extends AbstractPiece {
	private static final long serialVersionUID = -5877331758642136790L;

	public Cat(Owner owner) {
		super(null, owner, 1);
		loadImage(owner);
	}

	public void loadImage(Owner owner) {
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon("resources/" + color + " cat.png").getImage());
	}
}
