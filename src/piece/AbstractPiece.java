package piece;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public abstract class AbstractPiece implements Serializable {
	private static final long serialVersionUID = 1115622952453934265L;
	private ImageIcon image;
	private Owner owner;
	private int rank;

	public AbstractPiece(ImageIcon image, Owner owner, int rank) {
		this.image = image;
		this.owner = owner;
		this.rank = rank;
	}

	public Image getImage() {
		return image.getImage();
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public Owner getOwner() {
		return owner;
	}

	public int getRank() {
		return rank;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		// this allows for subclassing of individual pieces
		// can i treat the given object as this type of object != this.class == obj.class
		if (obj.getClass().isAssignableFrom(this.getClass())) {
			AbstractPiece other = (AbstractPiece) obj;
			return this.getOwner().equals(other.getOwner());
		}
		return false;
	}

	public int hashCode() {
		return this.getClass().hashCode() + this.image.getImage().hashCode() + this.owner.hashCode()
				+ Integer.hashCode(this.rank);
	}

	public boolean isStrongerThan(AbstractPiece p2) {
		if (this.owner.equals(p2.owner))
			return true;
		return (this.getRank() > p2.getRank());
	}
}
