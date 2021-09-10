package components;

import logic.Enemy;
import logic.Point;
import logic.Tower;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 */
class BulletSprite {

    /**
     * Draw the tower's shooting graphich to the g Graphics
     * 
     * @param g     Graphics to draw shooting on
     * @param tower The tower which shooting should be displayed
     */
    public static void draw(Graphics g, Tower tower) {
        // If the tower is shooting -> draw on graphics
        if (tower.isShooting()) {

            // Get the center of tower
            Point towerCenter = tower.getCenter();

            // Get the center of the tower's target
            Enemy actualTarget = tower.getActualTarget();
            if (actualTarget == null) {
                return;
            }
            Point targetCenter = actualTarget.getActualPositionCenter();

            // Draw a line between them
            g.setColor(Color.WHITE);
            g.drawLine(towerCenter.getX(), towerCenter.getY(), targetCenter.getX(), targetCenter.getY());
        }
    }

}