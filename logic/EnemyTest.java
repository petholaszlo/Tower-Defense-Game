package logic;

import static org.junit.Assert.assertTrue;
import org.junit.*;

public class EnemyTest {
    Enemy enemy;

    @Before
    public void beforeEveryTest() {
        // Initialize Enemy before every test
        Point startPoint = new Point(0, 210);
        Point endPoint = new Point(210, 210);
        Point[] path = new Point[] { startPoint, endPoint };
        enemy = new Enemy(10, 1, path);
    }

    @Test
    public void enemyInitializedProperly() {
        // Arrange
        Point properStartPoint = new Point(-1 * Configuration.MAP_UNIT, 210);

        // Assert
        assertTrue("Enemy hp must be 10", enemy.getActualHp() == 10);
        assertTrue("Start point X set", enemy.getActualPosition().equals(properStartPoint));
        assertTrue("Horizontal speed calculated", enemy.horizontalSpeed == (float) (1.0 / Configuration.FPS));
        assertTrue("Vertical speed calculated", enemy.verticalSpeed == 0);
    }

    @Test
    public void enemyCenterCalculatedProperly() {
        // Arrange
        Point properCenterPoint = new Point(-15, 225);

        // Assert
        assertTrue("Enemy center calculated correctly", enemy.getActualPositionCenter().equals(properCenterPoint));
    }

    @Test
    public void enemyMovesProperly() {
        // Arrange
        enemy.isMoving = true;
        enemy.horizontalSpeed = 10;
        enemy.verticalSpeed = 5;

        // Act
        enemy.move();

        // Assert
        assertTrue("Enemy progresses", enemy.getProgress() == 15.0);
        assertTrue("Enemy position is changed", enemy.getActualPosition().equals(new Point(-20, 215)));
    }

}