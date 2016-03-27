package game;

import listeners.LoadGameListener;
import listeners.MutiplayerGameListener;
import listeners.NewGameListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by millerlj on 3/26/2016.
 */
public class Runner {

    public static void main(String[] args) {
        GUI g = new GUI();

        // Add MAIN MENU panel with appropriate background image
        ImagePanel panel = new ImagePanel(new ImageIcon(
                "resources/BoardStoneBig.jpg").getImage());
        g.activeFrames.get(0).getContentPane().add(panel);
        g.activeFrames.get(0).pack();
        panel.setVisible(true);

        // Add the NEW GAME button to the Main Menu
        JButton newGameButton = new JButton();
        newGameButton.setSize(150, 75);
        newGameButton.setText("New Game");
        Font newGameFont = newGameButton.getFont();
        newGameButton.setFont(new Font(newGameFont.getName(), 4, 20));
        newGameButton.setLocation((panel.getWidth() / 4) - 35,
                (panel.getHeight() / 2) - 37);
        panel.add(newGameButton);
        newGameButton.setVisible(true);

        // Setup ActionListener for NEW GAME button
        newGameButton.addActionListener(new NewGameListener(g));

        // Add the LOAD GAME button to the Main Menu
        JButton loadGameButton = new JButton();
        loadGameButton.setSize(150, 75);
        loadGameButton.setText("Load Game");
        Font loadGameFont = loadGameButton.getFont();
        loadGameButton.setFont(new Font(loadGameFont.getName(), 4, 20));
        loadGameButton.setLocation((panel.getWidth() / 4) * 3 - 110,
                (panel.getHeight() / 2) - 37);
        panel.add(loadGameButton);
        loadGameButton.setVisible(true);

        // Setup ActionListener for the LOAD GAME button
        loadGameButton.addActionListener(new LoadGameListener(g));

        // Add the LOAD GAME button to the Main Menu
        JButton multiplayerGameButton = new JButton();
        multiplayerGameButton.setSize(250, 75);
        multiplayerGameButton.setText("New Multiplayer Game");
        Font multiplayerGameFont = multiplayerGameButton.getFont();
        multiplayerGameButton.setFont(new Font(multiplayerGameButton.getName(), 4, 20));
        multiplayerGameButton.setLocation((panel.getWidth() / 2) -125,
                (panel.getHeight() / 2) + 50);
        panel.add(multiplayerGameButton);
        multiplayerGameButton.setVisible(true);

        // Setup ActionListener for the LOAD GAME button
        multiplayerGameButton.addActionListener(new MutiplayerGameListener(g));

        g.activeFrames.get(0).setVisible(true);
    }
}
