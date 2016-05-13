package piece;

import javax.swing.ImageIcon;

import gui.GUI;

public class Cat extends AbstractPiece {
	private static final long serialVersionUID = -5877331758642136790L;

	public Cat(Owner owner) {
		super(null, owner, 1);
	}

	@Override
	public void generateImage() {
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon(GUI.class.getClassLoader().getResource(color + " cat.png")));
	}
}
