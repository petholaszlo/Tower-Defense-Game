package logic;

import static org.junit.Assert.assertTrue;
import org.junit.*;

public class LevelTest {

    Level level;

    @Before
    public void beforeEveryTest() {
        level = new Level("level_5");
    }

    @Test
    public void levelInitializedProperly() {

        // Arrange
        int[][] map = level.getMap();
        Point[] path = level.getPath();
        Wave[] waves = level.getWaves();

        // Assert
        assertTrue("Player's hp should be 3", level.getHp() == 3);
        assertTrue("Ammount of crowns should be 3000", level.getCrown() == 3000);
        assertTrue("Background image loaded properly", level.getBackgroundImage() != null);

        assertTrue("Checking if map.txt loaded correctly (1)", map[0][0] == 0);
        assertTrue("Checking if map.txt loaded correctly (2)", map[19][19] == 0);
        assertTrue("Checking if map.txt loaded correctly (3)", map[4][2] == 1);
        assertTrue("Checking if map.txt loaded correctly (4)", map[0][17] == 1);
        assertTrue("Checking if map.txt loaded correctly (5)", map[15][8] == 0);

        // First one's X should be -30, as the enemy is out of the map
        assertTrue("Checking if path.txt loaded correctly (1)", path[0].getX() == -30 && path[0].getY() == 180);
        assertTrue("Checking if path.txt loaded correctly (2)", path[6].getX() == 480 && path[6].getY() == 0);
        assertTrue("Checking if path.txt loaded correctly (3)", path[3].getX() == 360 && path[3].getY() == 450);

        assertTrue("Checking if wave.txt loaded correctly (1)", waves.length == 3);
    }

    @Test
    public void playerDamageWorksProperly() {

        // Arrange
        int hp = level.getHp();
        int damage = 2;

        // Act
        level.damagePlayer(damage);

        // Assert
        assertTrue("Player should have 2 less hp", level.getHp() == hp - damage);
    }

    @Test
    public void crownAddingWorksProperly() {

        // Arrange
        int ammount = 10;
        int crown = level.getCrown();

        // Act
        level.addCrown(ammount);

        // Assert
        assertTrue("Player should get 10 more crowns", level.getCrown() == crown + ammount);
    }

    @Test
    public void canBuildTowerWorksProperly() {

        // Arrange
        int[] base1 = { 2, 4 };
        Tower alcohol = new Alcohol(base1);
        level.towerToBuild = alcohol;

        // Assert
        assertTrue("Should be able to build an alcohol tower there", level.canBuildTower());

        int[] base2 = { 11, 5 };
        Tower medication = new Medication(base2);
        level.towerToBuild = medication;

        // Assert
        assertTrue("Should NOT be able to build an alcohol tower there (tower is too big)", !level.canBuildTower());
    }

    @Test
    public void buildingTowerWorksProperly() {

        // Arrange
        int[] base = { 2, 4 };
        Tower alcohol = new Alcohol(base);
        level.towerToBuild = alcohol;

        // Act
        level.addTower(alcohol);

        // Assert
        assertTrue("Check if the tower has been built", level.getTowers().size() > 0);
    }
}