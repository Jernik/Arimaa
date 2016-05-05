package piece;

import javax.swing.ImageIcon;

public class Horse extends AbstractPiece {
	private static final long serialVersionUID = 8143779066737395067L;

	public Horse(Owner owner) {
		super(null, owner, 3);
		loadImage(owner);
	}

	public void loadImage(Owner owner) {
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon("resources/" + color + " horse.png").getImage());
	}
}
