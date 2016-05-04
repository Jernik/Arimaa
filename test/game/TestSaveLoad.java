package game;

import static org.easymock.EasyMock.*;
import org.easymock.EasyMockSupport;
import org.easymock.IAnswer;
import org.hamcrest.core.IsAnything;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import ai.Ai;
import move_commands.RegularMove;
import piece.AbstractPiece;
import piece.Camel;
import piece.Cat;
import piece.Dog;
import piece.Elephant;
import piece.Owner;
import piece.Rabbit;

public class TestSaveLoad extends EasyMockSupport{
	private Game g;

	public boolean deleteRecursivly(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				this.deleteRecursivly(c);
		}
		return f.delete();
	}

	@Before
	public void setup() {
		g = new Game();
	}

	@Test
	public void testSaveFileCreatesDirectoryAndFileIfNeeded() {
		// setup
		File d = new File(GUI.SAVE_FOLDER);
		this.deleteRecursivly(d);
		assertFalse(d.exists());

		GUI gui = new GUI();
		assertTrue(gui.saveFile());

		assertTrue(d.exists());
		assertTrue(new File(GUI.SAVE_PATH).exists());
	}

	@Test
	public void testSaveFileCreatesFileIfNeeded() {
		try {
			Files.delete(Paths.get(GUI.SAVE_PATH));
			GUI gui = new GUI();

			assertTrue(gui.saveFile());
			File f = new File(GUI.SAVE_PATH);
			assertTrue(f.exists());
		} catch (IOException e) {
			e.printStackTrace();
			fail("couldn't setup test");
		}
	}

	@Test
	public void testSaveFileChangesFile() {
		try {
			GUI gui = new GUI();
			gui.saveFile();
			byte[] before = Files.readAllBytes(Paths.get(GUI.SAVE_PATH));

			Ai ai = new Ai(Owner.Player1, gui.game);
			gui.game.move(ai.generateMove());
			assertTrue(gui.saveFile());
			byte[] after = Files.readAllBytes(Paths.get(GUI.SAVE_PATH));

			assertThat("Save file was not modified", after, not(equalTo(before)));
		} catch (IOException e) {
			e.printStackTrace();
			fail("couldn't setup test");
		}
	}

	@Test
	public void testSaveFileReturnsFalseIfErrors() {
		GUI gui = partialMockBuilder(GUI.class).addMockedMethod("createOutputStream").createMock();
		try {
			expect(gui.createOutputStream()).andThrow(new IOException());
		} catch (IOException e) {
			fail("could not return the mock");
		}
		replayAll();
		
		assertFalse(gui.saveFile());
		
		verifyAll();
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
	 moveGUI.game = g1;
	
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
	 // Done failing moves.
	 g1.move(new RegularMove(g1.currentBoard, new Coordinate(4, 4), new Coordinate(4, 3), g1.getOwner()));
	 g1.move(new RegularMove(g1.currentBoard, new Coordinate(7, 7), new Coordinate(7, 6), g1.getOwner()));
	 moveGUI.game = g1;
	
	 boolean saved = moveGUI.saveFile();
	 boolean loaded = myGUI.loadFile();
	 assertTrue(saved && loaded);
	 assertNotNull(myGUI.game);
	 assertNotNull(moveGUI.game);
	 assertTrue(myGUI.game.equals(moveGUI.game));
	 }
	
	 @Test(expected = IOException.class)
	 public void testSaveFileFailsOnIOException() throws IOException {
	 GUI myGUI = new GUI();
	 System.out.println("Failure caused by testSaveFileFailsOnIOException");
	 myGUI.failSaveFile();
	 }
	
	 // Not 100% how this test will work yet
	 @Test(expected = IOException.class)
	 public void testSaveFileFailsWhenNoSave() throws IOException {
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
	 boolean saved = moveGUI.deleteButNotSave();
	 boolean loaded = myGUI.loadFile();
	 System.out.println(saved + " " + loaded);
	 }
	
}
