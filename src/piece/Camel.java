package piece;

import javax.swing.ImageIcon;

import gui.GUI;

public class Camel extends AbstractPiece {
	private static final long serialVersionUID = 1729226869493177295L;

	public Camel(Owner owner) {
		super(null, owner, 4);
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon(GUI.class.getClassLoader().getResource(color + " camel.png")));
	}
}
