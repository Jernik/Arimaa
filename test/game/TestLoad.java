package game;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.easymock.EasyMockSupport;
import org.junit.Test;

import ai.Ai;
import piece.Owner;

public class TestLoad extends EasyMockSupport {
	@Test
	public void testHappyLoad() {
		GUI gui = new GUI();
		gui.saveFile();

		Game before = new Game(gui.game);
		assertTrue(gui.loadFile(new File(GUI.SAVE_PATH)));
		assertEquals(before, gui.game);
	}

	@Test
	public void testLoadWithChanges() {
		GUI gui = new GUI();
		gui.saveFile();

		Game before = new Game(gui.game);
		Ai ai = new Ai(Owner.Player1, gui.game);
		gui.game.move(ai.generateMove());
		gui.game.move(ai.generateMove());
		gui.game.move(ai.generateMove());

		assertNotEquals(before, gui.game);
		assertTrue(gui.loadFile(new File(GUI.SAVE_PATH)));
		assertEquals(before, gui.game);
	}

	@Test
	public void testLoadFileReturnsFalseIfFileDoesntExist() {
		GUI gui = new GUI();
		gui.saveFile();

		Game before = new Game(gui.game);
		Ai ai = new Ai(Owner.Player1, gui.game);
		gui.game.move(ai.generateMove());
		Game afterMove = new Game(gui.game);

		assertNotEquals(before, gui.game);
		assertFalse(gui.loadFile(new File("sdfkjhas")));
		assertEquals(afterMove, gui.game);

	}

	@Test
	public void testLoadFileReturnsFalseIfErrors() {
		GUI gui = partialMockBuilder(GUI.class).addMockedMethod("createInputStream").createMock();
		gui.game = new Game();
		gui.saveFile();
		File f = new File(GUI.SAVE_PATH);
		try {
			expect(gui.createInputStream(f)).andThrow(new IOException());
		} catch (IOException e) {
			fail("could not return the mock");
		}
		replayAll();

		Ai ai = new Ai(Owner.Player1, gui.game);
		gui.game.move(ai.generateMove());
		Game afterMove = new Game(gui.game);

		assertFalse(gui.loadFile(f));
		assertEquals(afterMove, gui.game);
		verifyAll();
	}

	@Test
	public void testLoadFileReturnsFalseIfWrongObject() {
		File f = new File(GUI.SAVE_PATH);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(456);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Couldn't write a serializble file");
		}

		GUI gui = new GUI();
		Ai ai = new Ai(Owner.Player1, gui.game);
		gui.game.move(ai.generateMove());
		Game afterMove = new Game(gui.game);

		assertFalse(gui.loadFile(f));
		assertEquals(afterMove, gui.game);
	}
}
