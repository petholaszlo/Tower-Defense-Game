package logic;

import static org.junit.Assert.assertTrue;
import org.junit.*;

public class TowerTest {
    
    Tower alcohol;
    Tower leukocyte;
    Tower medication;

    @Before
    public void beforeEveryTest() {
        // Initialize Enemy before every test

        int[] base1 = {10, 10};
        int[] base2 = {15, 10};
        int[] base3 = {0, 5};

        this.alcohol = new Alcohol(base1);
        this.leukocyte = new Leukocyte(base2);
        this.medication = new Medication(base3);
    }

    @Test
    public void towerInitializedProperly() {

        // Assert
        assertTrue("Checking if base is correct", alcohol.base[0] == 10 && alcohol.base[1] == 10);
        assertTrue("Checking if base is correct", leukocyte.base[0] == 15 && leukocyte.base[1] == 10);
        assertTrue("Checking if base is correct", medication.base[0] == 0 && medication.base[1] == 5);

        assertTrue("Checking if size is correct", alcohol.getSize()[0] == alcohol.getSize()[1] && alcohol.getSize()[0] == 1);
        assertTrue("Checking if size is correct", leukocyte.getSize()[0] == leukocyte.getSize()[1] && leukocyte.getSize()[0] == 1);
        assertTrue("Checking if size is correct", medication.getSize()[0] == medication.getSize()[1] && medication.getSize()[0] == 2);

        assertTrue("Checking if range is correct", alcohol.getRange() == 100);
        assertTrue("Checking if range is correct", leukocyte.getRange() == 100);
        assertTrue("Checking if range is correct", medication.getRange() == 200);

        assertTrue("Checking if reload time is correct", alcohol.getReloadTimeInSeconds() == 2);
        assertTrue("Checking if reload time is correct", leukocyte.getReloadTimeInSeconds() == 1);
        assertTrue("Checking if reload time is correct", medication.getReloadTimeInSeconds() == 4);

        assertTrue("Checking if base damage is correct", alcohol.getBaseDamage() == 20);
        assertTrue("Checking if base damage is correct", leukocyte.getBaseDamage() == 10);
        assertTrue("Checking if base damage is correct", medication.getBaseDamage() == 40);
    }

    @Test
    public void towerCenterCalculatedProperly() {

        // Assert
        assertTrue("Checking if the center is calculated properly", alcohol.getCenter().getX() == 315 && alcohol.getCenter().getY() == 315);
        assertTrue("Checking if the center is calculated properly", leukocyte.getCenter().getX() == 465 && leukocyte.getCenter().getY() == 315);
        assertTrue("Checking if the center is calculated properly", medication.getCenter().getX() == 30 && medication.getCenter().getY() == 180);
    }

    @Test
    public void towerInRangeCalculatedProperly() {

        // Arrange
        Point startPoint = new Point(310, 300);
        Point endPoint = new Point(210, 210);
        Point[] path = new Point[] { startPoint, endPoint };
        Enemy enemy = new Enemy(100, 1, path);

        // Assert
        assertTrue("Should be in range", alcohol.inRange(enemy));
        assertTrue("Should be out of range", !medication.inRange(enemy));
    }

    @Test
    public void towerShootsProperly() {
        // Arrange
        Point startPoint = new Point(310, 300);
        Point endPoint = new Point(210, 210);
        Point[] path = new Point[] { startPoint, endPoint };
        Enemy enemy = new Enemy(100, 1, path);

        Enemy[] enemies = new Enemy[1];
        enemies[0] = enemy;

        // Act
        alcohol.shoot(enemies);
        medication.shoot(enemies);

        // Assert
        assertTrue("Check if tower took a shot", alcohol.isShooting());
        assertTrue("Check if tower took a shot", !medication.isShooting());
    }
}