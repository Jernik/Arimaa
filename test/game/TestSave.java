package game;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.easymock.EasyMockSupport;
import org.junit.Test;

import ai.Ai;
import piece.Owner;

public class TestSave extends EasyMockSupport {
	public boolean deleteRecursivly(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				this.deleteRecursivly(c);
		}
		return f.delete();
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

			Ai ai = new Ai(Owner.Player1, gui.getGame());
			gui.getGame().move(ai.generateMove());
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
}
