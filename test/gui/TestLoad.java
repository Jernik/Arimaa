package gui;

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
import game.Game;
import gui.GUI;
import piece.Owner;

public class TestLoad extends EasyMockSupport {
	@Test
	public void testHappyLoad() {
		GUI gui = new GUI();
		gui.saveFile();

		Game before = new Game(gui.getGame());
		assertTrue(gui.loadFile(new File(GUI.SAVE_PATH)));
		assertEquals(before, gui.getGame());
	}

	@Test
	public void testLoadWithChanges() {
		GUI gui = new GUI();
		gui.saveFile();

		Game before = new Game(gui.getGame());
		Ai ai = new Ai(Owner.Player1, gui.getGame());
		gui.getGame().move(ai.generateMove());
		gui.getGame().move(ai.generateMove());
		gui.getGame().move(ai.generateMove());

		assertNotEquals(before, gui.getGame());
		assertTrue(gui.loadFile(new File(GUI.SAVE_PATH)));
		assertEquals(before, gui.getGame());
	}

	@Test
	public void testLoadFileReturnsFalseIfFileDoesntExist() {
		GUI gui = new GUI();
		gui.saveFile();

		Game before = new Game(gui.getGame());
		Ai ai = new Ai(Owner.Player1, gui.getGame());
		gui.getGame().move(ai.generateMove());
		Game afterMove = new Game(gui.getGame());

		assertNotEquals(before, gui.getGame());
		assertFalse(gui.loadFile(new File("sdfkjhas")));
		assertEquals(afterMove, gui.getGame());

	}

	@Test
	public void testLoadFileReturnsFalseIfErrors() {
		GUI gui = partialMockBuilder(GUI.class).addMockedMethod("createInputStream").createMock();
		gui.setGame(new Game());
		gui.saveFile();
		File f = new File(GUI.SAVE_PATH);
		try {
			expect(gui.createInputStream(f)).andThrow(new IOException());
		} catch (IOException e) {
			fail("could not return the mock");
		}
		replayAll();

		Ai ai = new Ai(Owner.Player1, gui.getGame());
		gui.getGame().move(ai.generateMove());
		Game afterMove = new Game(gui.getGame());

		assertFalse(gui.loadFile(f));
		assertEquals(afterMove, gui.getGame());
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
		Ai ai = new Ai(Owner.Player1, gui.getGame());
		gui.getGame().move(ai.generateMove());
		Game afterMove = new Game(gui.getGame());

		assertFalse(gui.loadFile(f));
		assertEquals(afterMove, gui.getGame());
	}
}
