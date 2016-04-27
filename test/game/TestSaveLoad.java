package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class TestSaveLoad {
	private Game g;

	@Before
	public void setup() {
		g = new Game();
	}

	@Test
	public void testSaveFile() throws IOException {
		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		BoardState b1 = new BoardState(p1);
		Game g1 = new Game(b1);
		GUI myGUI = new GUI();
		myGUI.game = null;
		
		GUI moveGUI = new GUI();
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(6, 7), new Coordinate(5, 7), g1.getOwner()));
		
		boolean saved = moveGUI.saveFile();
		boolean loaded = myGUI.loadFile();
		assertTrue(saved && loaded);
		assertTrue(myGUI.game.equals(moveGUI.game));
	}
	
	@Test
	public void testSaveFileWithSeveralMoves() throws IOException {
		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		BoardState b1 = new BoardState(p1);
		Game g1 = new Game(b1);
		GUI myGUI = new GUI();
		myGUI.game = null;
		
		GUI moveGUI = new GUI();
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(6, 7), new Coordinate(5, 7), g1.getOwner()));
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(5, 7), new Coordinate(6, 7), g1.getOwner()));
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(3, 6), new Coordinate(4, 6), g1.getOwner()));
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(4, 6), new Coordinate(3, 6), g1.getOwner()));
		
		boolean saved = moveGUI.saveFile();
		boolean loaded = myGUI.loadFile();
		assertTrue(saved && loaded);
		assertTrue(myGUI.game.equals(moveGUI.game));
	}
	
	@Test
	public void testSaveFileWithExtraMovesThatShouldNotHappen() throws IOException {
		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
		p1.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
		BoardState b1 = new BoardState(p1);
		Game g1 = new Game(b1);
		GUI myGUI = new GUI();
		myGUI.game = null;
		
		GUI moveGUI = new GUI();
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(6, 7), new Coordinate(5, 7), g1.getOwner()));
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(5, 7), new Coordinate(6, 7), g1.getOwner()));
		// Theses moves should fail, so we should have two moves left
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(6, 7), new Coordinate(7, 7), g1.getOwner()));
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(6, 7), new Coordinate(7, 7), g1.getOwner()));
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(6, 7), new Coordinate(7, 7), g1.getOwner()));
		//Done failing moves.
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(4, 4), new Coordinate(4, 3), g1.getOwner()));
		g1.move(new RegularMove(g1.currentBoard, new Coordinate(7, 7), new Coordinate(7, 6), g1.getOwner()));
		
		boolean saved = moveGUI.saveFile();
		boolean loaded = myGUI.loadFile();
		assertTrue(saved && loaded);
		assertTrue(myGUI.game.equals(moveGUI.game));
	}

	@Test(expected=IOException.class)
	public void testSaveFileFailsOnIOException() throws IOException {
		GUI myGUI = new GUI();
		System.out.println("Failure caused by testSaveFileFailsOnIOException");
		myGUI.failSaveFile();
	}
	
	//Not 100% how this test will work yet
//	@Test(expected=IOException.class)
//	public void testSaveFileFailsWhenNoSave() throws IOException {
//		HashMap<Coordinate, AbstractPiece> p1 = new HashMap<Coordinate, AbstractPiece>();
//		p1.put(new Coordinate(6, 7), new Cat(Owner.Player1));
//		p1.put(new Coordinate(7, 7), new Rabbit(Owner.Player1));
//		p1.put(new Coordinate(3, 6), new Dog(Owner.Player1));
//		p1.put(new Coordinate(3, 4), new Camel(Owner.Player1));
//		p1.put(new Coordinate(4, 4), new Elephant(Owner.Player1));
//		BoardState b1 = new BoardState(p1);
//		Game g1 = new Game(b1);
//		GUI myGUI = new GUI();
//		myGUI.game = null;
//		
//		GUI moveGUI = new GUI();
//		boolean saved = moveGUI.deleteButNotSave();
//		boolean loaded = myGUI.loadFile();
//		System.out.println(saved + " " + loaded);
//	}
	
}
