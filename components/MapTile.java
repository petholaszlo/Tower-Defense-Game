package components;

import java.awt.Color;
import javax.swing.JPanel;

import logic.Configuration;
import logic.Level;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The JPanel component that represents a single part of the map.
 */
public class MapTile extends JPanel implements Renderable {

    Level level;
    GameArea gameArea;
    boolean isHovered;
    int buildable; // Defines if the actual tile is buildable

    public MapTile(Level level, int x, int y, GameArea gameArea) {
        this.setLayout(null);
        this.setLocation(x * Configuration.MAP_UNIT, y * Configuration.MAP_UNIT);
        this.setSize(Configuration.MAP_UNIT, Configuration.MAP_UNIT);

        this.level = level;
        this.buildable = level.getMap()[y][x];
        this.isHovered = false;

        this.gameArea = gameArea;

        addMouseListener(adapter);
    }

    /**
     * Mouse adapter to handle hovering and click
     */
    private MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isHovered = true;
        }

        public void mouseExited(MouseEvent e) {
            isHovered = false;
        }

        public void mousePressed(MouseEvent e) {
            gameArea.buildTower();
        }
    };

    public void render() {
        if (this.level.isBuilding) {
            if (this.buildable == 1) {
                this.setVisible(true);
                this.setBackground(new Color(255, 255, 0, 50));
                return;
            }
        }
        this.setVisible(false);
    }
}