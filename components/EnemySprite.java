package components;

import logic.Enemy;
import logic.Point;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The JPanel component that represents a single enemy.
 */
class EnemySprite extends JPanel implements Renderable {

    Enemy enemy;
    JLabel healthLabel;

    public EnemySprite(Enemy enemy) {
        this.setSize(30, 30);

        // Temporary coloring of the enemies
        if (enemy.getHp() > 20) {
            this.setBackground(Color.BLUE);
        } else {
            this.setBackground(Color.YELLOW);
        }
        this.enemy = enemy;

        // Add the health label to the center of the enemy
        this.healthLabel = new JLabel("", SwingConstants.CENTER);
        this.healthLabel.setForeground(Color.black);
        this.add(healthLabel);
    }

    /**
     * @return the enemy
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Render function, to render locations
     */
    public void render() {
        // Set the health label to the enemy's actul HP
        String actualHp = String.format("%d", enemy.getActualHp());
        this.healthLabel.setText(actualHp);

        // "move" enemy on the GameArea
        Point position = enemy.getActualPosition();
        this.setLocation(position.getX(), position.getY());
    }
}