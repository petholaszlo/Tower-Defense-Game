package logic;

import static org.junit.Assert.assertTrue;
import org.junit.*;

public class WaveTest {

    Wave wave;
    
    @Before
    public void beforeEveryTest() {
        // Initialize wave before every test
        Point startPoint = new Point(20, 0);
        Point endPoint = new Point(20, 205);
        Point[] path = new Point[] { startPoint, endPoint };

        Enemy enemy1 = new Enemy(10, 1, path);
        Enemy enemy2 = new Enemy(10, 1, path);

        Enemy[] enemies = new Enemy[] { enemy1, enemy2 };
        wave = new Wave(enemies);
    }

    @Test
    public void waveInitializedProperly() {

        // Assert
        assertTrue("Check if enemies are stored in the class", wave.getEnemies().length > 0);
        assertTrue("Check if the first enemy is stored correctly", wave.getEnemies()[0].getActualHp() == 10);
    }

    @Test
    public void isWaveClearedWorksProperly() {

        // Assert
        assertTrue("Wave should NOT be clear", !wave.isWaveCleared());
        // Arrange
        // Kill all the enemies
        wave.getEnemies()[0].takeDamage(100);
        wave.getEnemies()[1].takeDamage(100);

        // Assert
        assertTrue("Wave should be clear", wave.isWaveCleared());
    }
}