package piece;

import javax.swing.ImageIcon;

import gui.GUI;

public class Horse extends AbstractPiece {
	private static final long serialVersionUID = 8143779066737395067L;

	public Horse(Owner owner) {
		super(null, owner, 3);
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon(GUI.class.getClassLoader().getResource(color + " horse.png")));
	}
}
