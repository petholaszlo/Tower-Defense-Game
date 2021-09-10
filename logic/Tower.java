package logic;

public abstract class Tower {
    protected String name;
    protected int[] size = new int[2];
    protected int range;
    protected int price;
    protected int upgradePrice;
    protected int upgradeXPThreshold;
    protected int baseDamage;
    protected int xp;
    protected int[] base = new int[2];;
    protected int reloadTime;
    protected Point center;
    protected int lastShot;
    protected int shootingTime;
    protected Enemy actualTarget;
    protected int reloadTimeInSeconds;
    protected int upgradeType = 0;

    public Tower(int xSize, int ySize, int reloadTimeInSeconds, int range, int baseDamage, int price, String name,
            int xBase, int yBase) {
        this(xSize, ySize, reloadTimeInSeconds, range, baseDamage, price, name);
        this.base = new int[] { xBase, yBase };
        setCenter();
    }

    public Tower(int xSize, int ySize, int reloadTimeInSeconds, int range, int baseDamage, int price, String name) {

        // For shooting at every second
        this.reloadTime = reloadTimeInSeconds * Configuration.FPS;
        this.reloadTimeInSeconds = reloadTimeInSeconds;

        // Shooting time is a quarter of a second
        this.shootingTime = Configuration.FPS / 4;

        this.upgradeXPThreshold = baseDamage * 3;

        this.price = price;
        this.actualTarget = null;
        this.lastShot = reloadTime + shootingTime;
        this.range = range;
        this.size[0] = xSize;
        this.size[1] = ySize;
        this.baseDamage = baseDamage;
        this.name = name;
        this.base = new int[] { 0, 0 };
        setCenter();
    }

    /**
     * @return the size
     */
    public int[] getSize() {
        return size;
    }

    /**
     * @return the base
     */
    public int[] getBase() {
        return base;
    }

    /**
     * Sets the tower center pixel perfectly
     */
    public void setCenter() {
        float centerX = base[0] * Configuration.MAP_UNIT + (size[0] * Configuration.MAP_UNIT) / 2;
        float centerY = base[1] * Configuration.MAP_UNIT + (size[1] * Configuration.MAP_UNIT) / 2;
        center = new Point(Math.round(centerX), Math.round(centerY));
    }

    /**
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @return how fast the tower reloads
     */
    public int getReloadTimeInSeconds() {
        return reloadTimeInSeconds;
    }

    /**
     * @return damage of each shot
     */
    public int getBaseDamage() {
        return baseDamage;
    }

    /**
     * Calculates wheter an enemy is in range using a basic pythagorian equation
     * 
     * @param enemy The enemy to check if in range
     * @return true if in range, false if not
     */
    public boolean inRange(Enemy enemy) {
        // Get the center point of the enemy
        Point enemyCenter = new Point(enemy.getActualPositionCenter().getX(), enemy.getActualPositionCenter().getY());

        // Calculate distance
        int a = Math.abs(center.getX() - enemyCenter.getX());
        int b = Math.abs(center.getY() - enemyCenter.getY());
        double distance = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

        // If distance is shorter than range -> Enemy is in range
        return this.range >= distance;
    }

    public void upgradeTower(int upgradeType) {
        this.upgradeType = upgradeType;
        this.xp -= this.upgradeXPThreshold;
        if (upgradeType == 1) {
            this.modifyBaseStat();
        }
    }

    public boolean canUpgrade() {
        return this.xp >= this.upgradeXPThreshold && this.upgradeType == 0;
    }

    /**
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * @return the actualTarget
     */
    public Enemy getActualTarget() {
        return actualTarget;
    }

    /**
     * Defines wheter a tower is still in shooting state
     * 
     * @return true if still shootin
     */
    public boolean isShooting() {
        return lastShot < shootingTime;
    }

    /**
     * Get the percentage of the shooting period
     * 
     * (100 means the tower can shoot, 0-99 means it's reloading)
     */
    public int getShootingPercentage() {
        float perc = ((float) ((float) lastShot / (float) (shootingTime + reloadTime))) * 100;
        return (int) perc;
    }

    /**
     * @return the xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param base the base to set
     */
    public void setBase(int[] base) {
        this.base = base;
        setCenter();
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Shoot an alive enemy in range with the highest progress if can
     */
    public void shoot(Enemy[] enemies) {
        // If the tower just fired -> wait but keep the target
        // waiting means increment the lastShot property of the object
        if (this.isShooting()) {
            lastShot++;
            return;
        }

        // waiting for reload
        if (lastShot < reloadTime + shootingTime) {
            // If the tower finished firing -> remove target
            if (actualTarget != null) {
                actualTarget = null;
            }
            lastShot++;
            return;
        }

        // Initilaize the nearest enemy as null
        Enemy nearestEnemy = null;

        // Loop through given enemies
        for (Enemy e : enemies) {

            // If the actual enemy is alive and in range:
            if (e.isAlive() && this.inRange(e)) {

                // Check if nearestEnemy is defined or not
                if (nearestEnemy == null) {
                    // If not -> set the actual one as the nearest
                    nearestEnemy = e;
                } else {
                    // If nearest enemy is defined
                    // Check whether the actually checked enemy is further in the map.
                    if (e.getProgress() > nearestEnemy.getProgress()) {
                        // if so -> That should be the target
                        nearestEnemy = e;
                    }
                }
            }
        }

        // If no possible target is found -> don't shoot
        if (nearestEnemy == null) {
            return;
        }

        // Else -> Set target, Shoot the enemy, Set the lastShot to 0
        this.actualTarget = nearestEnemy;
        nearestEnemy.takeDamage(baseDamage);
        if (this.upgradeType == 2) {
            specialAttack(nearestEnemy);
        }
        this.xp += baseDamage;
        lastShot = 0;

    }

    abstract void specialAttack(Enemy e);

    abstract void modifyBaseStat();

}
