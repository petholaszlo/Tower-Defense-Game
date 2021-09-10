package views;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import java.io.File;

import logic.Configuration;
import logic.Level;
import logic.Wave;
import components.GameArea;
import components.ShopArea;
import components.UpgradeArea;
import components.WavesArea;

/**
 * The JPanel view that shows the game itself.
 */
public class GameView extends JPanel {

    GameArea gameArea;
    ShopArea shopArea;
    UpgradeArea upgradeArea;
    WavesArea wavesArea;

    Timer frameTimer;
    Level level;
    boolean isPaused;
    final int FPS = 1000 / Configuration.FPS; // -> 60 frames per second

    /**
     * Initialize the game view
     * 
     * @param levelFolder The path to the folder where level data is found
     */
    public GameView(String levelFolder) {
        // Setup JPanel
        super();
        this.setLayout(null);
        this.setSize(820, 700);

        // Initialize Level
        this.level = new Level(levelFolder);

        // Initialize gameArea
        this.gameArea = new GameArea(level);
        this.shopArea = new ShopArea(level);
        this.upgradeArea = new UpgradeArea(level);
        this.wavesArea = new WavesArea(level);

        // Add initialized areas to this panel
        this.add(this.gameArea);
        this.add(this.shopArea);
        this.add(this.upgradeArea);
        this.add(this.wavesArea);

        // Start the game
        startGame();
    }

    /**
     * Start the game with a looping render
     */
    private void startGame() {
        isPaused = false;
        frameTimer = new Timer(FPS, new RenderListener());
        frameTimer.start();
    }

    private boolean isGameWon() {
        for (Wave w : this.level.getWaves()) {
            if (!w.isWaveCleared()) {
                return false;
            }
        }
        return true;
    }

    private boolean isGameLost() {
        if (this.level.getHp() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * ActionListener for the Timer
     */
    private class RenderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            gameArea.render();
            shopArea.render();
            upgradeArea.render();
            wavesArea.render();

            if (isGameWon()) {
                frameTimer.stop();

            }
            if (isGameLost()) {
                frameTimer.stop();
                // Show that the player is lost
            }
        }
    };
}