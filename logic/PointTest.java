package logic;

import static org.junit.Assert.assertTrue;
import org.junit.*;

public class PointTest {
    
    Point point;

    @Before
    public void beforeEveryTest() {
        // Initialize Points before every test
        point = new Point(5, 4);
    }

    @Test
    public void pointInitializedProperly() {

        // Assert
        assertTrue("Check if point is initialized properly", point.getX() == 5 && point.getY() == 4);
    }

    @Test
    public void setterWorksProperly() {

        // Arrange
        point.setX(10);
        point.setY(8);

        // Assert
        assertTrue("Should be: x = 10 and y = 8", point.getX() == 10 && point.getY() == 8);
    }

    @Test
    public void equalsWorksProperly() {

        // Arrange
        Point other1 = new Point(0, 0);
        Point other2 = new Point(5, 4);

        // Assert
        assertTrue("Should NOT be equal", !point.equals(other1));
        assertTrue("Should be equal", point.equals(other2));
    }
}