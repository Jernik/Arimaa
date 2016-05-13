package piece;

import javax.swing.ImageIcon;

import gui.GUI;

public class Rabbit extends AbstractPiece {
	private static final long serialVersionUID = 2790417979920397492L;

	public Rabbit(Owner owner) {
		super(null, owner, 0);
		String color = owner.equals(Owner.Player1) ? "White" : "Black";
		this.setImage(new ImageIcon(GUI.class.getClassLoader().getResource(color + " rabbit.png")));
	}
}
