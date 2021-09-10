package components;

import logic.Configuration;
import logic.Level;
import logic.Tower;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The JPanel component that represents a single enemy.
 */
public class TowerSprite extends JPanel implements Renderable {

    Tower tower;
    Level level;
    boolean isHovered;
    boolean isConstantRange;

    public TowerSprite(Tower tower, Level level) {
        this.setLayout(null);
        this.setBackground(Color.RED);
        //this.setOpaque(true);
        this.setSize(tower.getSize()[0] * Configuration.MAP_UNIT, tower.getSize()[0] * Configuration.MAP_UNIT);
        this.setLocation(tower.getBase()[0] * Configuration.MAP_UNIT, tower.getBase()[1] * Configuration.MAP_UNIT);

        this.tower = tower;
        this.level = level;

        // Add the hover adapter to the tower sprite
        addMouseListener(hoverAdapter);
        addMouseListener(clickAdapter);
    }

    public TowerSprite(Tower tower, Level level, boolean isConstantRange) {
        this(tower, level);
        this.isConstantRange = isConstantRange;
    }

    // Sets the tower of the sprite (used for updating while building)
    public void setTower(Tower tower) {
        this.tower = tower;
        this.setSize(tower.getSize()[0] * Configuration.MAP_UNIT, tower.getSize()[0] * Configuration.MAP_UNIT);
    }

    // Refresh the location of the tower
    public void refreshLocation() {
        this.setLocation(tower.getBase()[0] * Configuration.MAP_UNIT, tower.getBase()[1] * Configuration.MAP_UNIT);
    };

    /**
     * Mouse adapter to handle hovering
     */
    private MouseAdapter hoverAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            // On enter -> Set isHovered to true
            isHovered = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // On leave -> Set isHovered to false
            isHovered = false;
        }
    };

    /**
     * Mouse adapter to handle on click
     */
    private MouseAdapter clickAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // On enter -> Set isHovered to true
            level.towerToUpgrade = tower;
        }

    };

    /**
     * Draw the range of the tower on g Graphich
     * 
     * @param g Graphics to draw range on
     */
    public void drawRange(Graphics g) {
        // -> If the user hovers on tower
        // -> (or it has to show it's range constatly)
        // draw it's range
        if (isHovered || isConstantRange) {
            g.setColor(new Color(255, 255, 0, 50));
            g.fillOval(tower.getCenter().getX() - tower.getRange(), tower.getCenter().getY() - tower.getRange(),
                    tower.getRange() * 2, tower.getRange() * 2);
        }
    }

    /**
     * Draw reloading countdown of the tower
     * 
     * @param g The graphich to draw reloading on
     */
    private void drawReloading(Graphics g) {
        g.setColor(Color.YELLOW);
        int height = 2;
        int width = (this.getSize().width) * tower.getShootingPercentage() / 100;
        g.fillRect(0, this.getSize().height - height, width, height);
    }

    /**
     * Painting on component
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawReloading(g);
    }

    /**
     * Render function, to render locations
     */
    public void render() {
        if (tower.isShooting()) {
            this.setBackground(Color.pink);
            return;
        }
        this.setBackground(Color.RED);
    }
}