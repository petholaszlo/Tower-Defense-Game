package components;

import logic.Level;
import logic.Tower;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The JPanel component that represents a buying block.
 */
class TowerToBuild extends JPanel implements Renderable {

    Tower tower;
    Level level;
    JLabel name;
    boolean isHovered;

    public TowerToBuild(Tower tower, Level level) {
        this.tower = tower;
        this.level = level;

        String info = tower.getName() + " " + tower.getPrice();
        this.name = new JLabel(info);
        this.add(name);

        // Add the hover adapter to the Buying sprite
        addMouseListener(clickAdapter);
    }

    /**
     * Mouse adapter to handle on click
     */
    private MouseAdapter clickAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // On enter -> Set isHovered to true
            if (level.towerToBuild != null && level.towerToBuild == tower) {
                level.isBuilding = false;
                level.towerToBuild = null;
            } else {
                if (isAvailable()) {
                    level.isBuilding = true;
                    level.towerToBuild = tower;
                }
            }
        }
    };

    /**
     * Returns wheter the tower can be built
     * 
     * @return true if player has enough crowns. false otherwise
     */
    private boolean isAvailable() {
        if (level.getCrown() >= tower.getPrice()) {
            return true;
        }
        return false;
    }

    /**
     * Render function, to render locations
     */
    public void render() {
        if (!isAvailable()) {
            this.setBackground(Color.gray);
            return;
        }

        if (level.towerToBuild == this.tower) {
            this.setBackground(Color.green);
            return;
        }
        this.setBackground(Color.white);
    }
}