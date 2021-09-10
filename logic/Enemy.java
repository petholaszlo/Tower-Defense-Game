package logic;

import java.util.ArrayList;

public class Enemy {
    // The base health point of the enemy
    // If killed -> the player gets this amount of crowns
    int hp;

    float speed;
    float horizontalSpeed;
    float verticalSpeed;

    int size; // Could be useful for later...
    int damageTaken;

    // for precise location (Point class takes two integer)
    float actualPositionX;
    float actualPositionY;

    // Positions rounded, to represent enemy on screen
    Point prevPosition;
    Point nextPosition;

    // Path that the enemy goes through
    Point[] path;

    // The last point in path that the enemy should touch in a movement section
    int lastPathPoint;

    // The progress of the enemy on the field
    float progress;
    boolean shouldDamagePlayer;

    // Defines wheter an enemy can move
    boolean isMoving;

    float speedModifier;
    float damageModifier;

    // List containing the drawbacks
    ArrayList<Drawback> drawbacks;

    public Enemy(int hp, float speed, Point[] path) {
        this.hp = hp;
        this.damageTaken = 0;
        this.drawbacks = new ArrayList<>();

        this.path = path;
        this.isMoving = false;
        this.speed = speed / Configuration.FPS;

        this.lastPathPoint = 1;
        this.setNextPositions();

        this.shouldDamagePlayer = false;

        this.speedModifier = 1;
        this.damageModifier = 1;
    }

    // -------------------------------
    // FUNCTIONS THAT DEFINES MOVEMENT
    // -------------------------------

    /**
     * Change the actual position of the enemy, based on horizontal and vertical
     * speed and turn if necessary
     */
    public void move() {
        if (this.isAlive() && this.isMoving) {
            actualPositionX = actualPositionX += (horizontalSpeed * speedModifier);
            actualPositionY = actualPositionY += (verticalSpeed * speedModifier);
            progress += Math.abs(horizontalSpeed * speedModifier);
            progress += Math.abs(verticalSpeed * speedModifier);

            // Set next positions if the actual next is reached
            if (Math.round(actualPositionX) == nextPosition.getX()
                    && Math.round(actualPositionY) == nextPosition.getY()) {
                this.lastPathPoint += 1;
                setNextPositions();

            }

        }

    }

    /**
     * @param speedModifier the speedModifier to set
     */
    public void setSpeedModifier(float speedModifier) {
        this.speedModifier = speedModifier;
    }

    /**
     * Set the start position of the enemy
     */
    private void setNextPositions() {
        // If last point is reached -> Enemy at the end -> Stop moving
        if (lastPathPoint == path.length) {
            this.isMoving = false;
            this.shouldDamagePlayer = true;
            return;
        }

        // Set start and end positions
        Point start = path[lastPathPoint - 1];
        Point end = path[lastPathPoint];

        // If Enemy is at the start position (lastPathPoint == 1)
        // -> Translate out of the map
        if (lastPathPoint == 1) {
            if (start.getX() == 0) {
                start.setX(-1 * Configuration.MAP_UNIT);
            } else if (start.getY() == 0) {
                start.setY(-1 * Configuration.MAP_UNIT);
            } else if (start.getY() == 19 * Configuration.MAP_UNIT) {
                start.setY(20 * Configuration.MAP_UNIT);
            } else if (start.getX() == 19 * Configuration.MAP_UNIT) {
                start.setX(20 * Configuration.MAP_UNIT);
            }
        }

        this.prevPosition = new Point(start.getX(), start.getY());
        this.nextPosition = new Point(end.getX(), end.getY());
        this.actualPositionX = this.prevPosition.getX();
        this.actualPositionY = this.prevPosition.getY();

        this.setDirectionalSpeed();
    }

    /**
     * Sets the horizontal and vertical directional speed of the enemy
     */
    private void setDirectionalSpeed() {
        this.horizontalSpeed = 0;
        this.verticalSpeed = 0;

        float xMovement = (prevPosition.getX() - nextPosition.getX()) * -1;
        float yMovement = (prevPosition.getY() - nextPosition.getY()) * -1;

        float normalizedXMovement = xMovement / (Math.abs(xMovement) + Math.abs(yMovement));
        float normalizedYMovement = yMovement / (Math.abs(xMovement) + Math.abs(yMovement));

        this.horizontalSpeed = normalizedXMovement * speed;
        this.verticalSpeed = normalizedYMovement * speed;
    }

    /**
     * @param isMoving the isMoving to set
     */
    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    // ----------------------------
    // FUNCTIONS THAT DEFINE HEALTH
    // ----------------------------

    /**
     * Applies damage to enemy
     * 
     * @param damage The ammount of damage
     */
    public void takeDamage(int damage) {
        damageTaken += (damage * damageModifier);
    }

    /**
     * Checks if enemy is still alive
     * 
     * @return True if enemy is alive, False if dead
     */
    public boolean isAlive() {
        return hp > damageTaken;
    }

    /**
     * Adds drawback to enemy
     * 
     * @param drawback The drawback, that is applied to enemy
     */
    public void addDrawback(Drawback drawback) {
        boolean alreadyHas = false;
        for (Drawback d : this.drawbacks) {
            if (d.getClass() == drawback.getClass()) {
                d.reset();
                alreadyHas = true;
            }
        }

        if (!alreadyHas) {
            this.drawbacks.add(drawback);
            drawback.beforeFirstOmit(this);
        }
    }

    /**
     * @param shouldDamagePlayer the shouldDamagePlayer to set
     */
    public void setShouldDamagePlayer(boolean shouldDamagePlayer) {
        this.shouldDamagePlayer = shouldDamagePlayer;
    }

    public void omitDrawbacks() {
        ArrayList<Drawback> shouldRemove = new ArrayList<Drawback>();
        for (Drawback d : this.drawbacks) {
            d.omit(this);
            if (d.isOver()) {
                d.beforeRemove(this);
                shouldRemove.add(d);
            }
        }

        if (shouldRemove.size() > 0) {
            this.drawbacks.removeAll(shouldRemove);
        }
    }

    // -------
    // GETTERS
    // -------

    public boolean getShouldDamagePlayer() {
        return this.shouldDamagePlayer;
    }

    /**
     * Return wheter an enemy is moving
     * 
     * @return the isMoving property
     */
    public boolean getIsMoving() {
        return this.isMoving;
    }

    /**
     * @return the progress
     */
    public float getProgress() {
        return progress;
    }

    /**
     * Get the current position of the enemy
     * 
     * @return Point class of the enemy's position
     */
    public Point getActualPosition() {
        return new Point(Math.round(actualPositionX), Math.round(actualPositionY));
    }

    /**
     * Function, to help to decide enemy type (Will be used for coloring)
     * 
     * @return Hp of the enemy
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * @param damageModifier the damageModifier to set
     */
    public void setDamageModifier(float damageModifier) {
        this.damageModifier = damageModifier;
    }

    /**
     * Return the actual HP calculated by damageTaken
     * 
     * @return The hp with damage taken
     */
    public int getActualHp() {
        return this.hp - this.damageTaken;
    }

    /**
     * Get the center point for displaying hit
     * 
     * @return The center point of the Enemy
     */
    public Point getActualPositionCenter() {
        int centerX = Math.round(actualPositionX + (Configuration.MAP_UNIT / 2));
        int centerY = Math.round(actualPositionY + (Configuration.MAP_UNIT / 2));
        return new Point(centerX, centerY);
    }

}