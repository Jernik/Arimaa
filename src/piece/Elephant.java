package piece;

import javax.swing.ImageIcon;

import gui.GUI;

public class Elephant extends AbstractPiece {
	private static final long serialVersionUID = 8132797322987449098L;

	public Elephant(Owner owner) {
		super(null, owner, 5);
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon(GUI.class.getClassLoader().getResource(color + " elephant.png")));
	}
}
