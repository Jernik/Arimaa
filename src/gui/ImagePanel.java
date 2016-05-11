package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import board.Coordinate;

public class ImagePanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -7315460075240330922L;
	private Image img;
	private Coordinate coor;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void setCoordinate(Coordinate coor) {
		this.coor = coor;
		this.setLocation(this.getPixelX(), this.getPixelY());
	}

	private int getPixelX() {
		return this.coor.getX() * 80 + 10;
	}

	private int getPixelY() {
		return this.coor.getY() * 80 + 10;
	}

}