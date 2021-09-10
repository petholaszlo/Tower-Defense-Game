package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import logic.*;

/**
 * The JPanel component that shows the map, enemies and towers.
 */
public class GameArea extends JPanel implements Renderable {

    Level level;
    MapTile[][] mapTiles;
    Wave actualWave;
    ArrayList<TowerSprite> towerSprites;
    ArrayList<EnemySprite> enemySprites;

    boolean canBuild = true;
    TowerSprite buildingTowerSprite;

    public GameArea(Level level) {
        // Setting up the component visually
        this.setLayout(null);
        this.setSize(600, 600);
        this.setLocation(0, 50);

        // Set transparent background
        this.setBackground(new Color(0f, 0f, 0f, 0f));

        this.buildingTowerSprite = null;

        // Setup level
        this.level = level;

        // Create enemies
        this.createEnemies();

        // Create test tower
        this.towerSprites = new ArrayList<>();

        // Create the map
        this.createMap();

        // Set the first wave
        this.setNextWave();

    }

    /**
     * Creates the map with MapTile components
     */
    private void createMap() {
        int mapSize = this.level.getMap().length;

        this.mapTiles = new MapTile[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                this.mapTiles[i][j] = new MapTile(level, j, i, this);
                this.add(this.mapTiles[i][j]);
            }
        }
    }

    /**
     * Create all the enemies in their start position
     */
    private void createEnemies() {
        enemySprites = new ArrayList<EnemySprite>();
        for (Wave w : this.level.getWaves()) {
            for (Enemy e : w.getEnemies()) {
                enemySprites.add(new EnemySprite(e));
                this.add(enemySprites.get(enemySprites.size() - 1));
            }
        }
    }

    /**
     * Set the next wave based on which one is cleared
     */
    private void setNextWave() {
        for (Wave w : this.level.getWaves()) {
            if (!w.isWaveCleared()) {
                this.actualWave = w;
                this.actualWave.startEnemies();
                return;
            }
        }
        this.actualWave = null;
    }

    /**
     * Move enemies on the map
     */
    private void moveEnemies() {
        if (actualWave != null) {
            for (Enemy e : this.actualWave.getEnemies()) {
                e.move();
            }
        }
    }

    /**
     * Shoot enemies of the actual wave
     */
    private void damageEnemies() {
        if (actualWave != null) {
            Enemy[] enemies = actualWave.getEnemies();
            for (Tower t : this.level.getTowers()) {
                t.shoot(enemies);
            }

            for (Enemy e : enemies) {
                e.omitDrawbacks();
            }
        }

    }

    /**
     * Remove dead enemy sprites
     */
    private void removeDeadEnemies() {
        ArrayList<EnemySprite> removableItems = new ArrayList<EnemySprite>();
        for (EnemySprite es : this.enemySprites) {
            if (!es.getEnemy().isAlive()) {
                removableItems.add(es);
                this.level.addCrown(es.getEnemy().getHp());
                this.remove(es);
            }

            if (es.getEnemy().getShouldDamagePlayer()) {
                this.level.damagePlayer(es.getEnemy().getHp());
                es.getEnemy().setShouldDamagePlayer(false);
                es.getEnemy().takeDamage(es.getEnemy().getHp());
                this.remove(es);
            }

        }
        if (removableItems.size() > 0) {
            this.enemySprites.removeAll(removableItems);
        }
    }

    public void buildTower() {
        switch (this.level.towerToBuild.getName()) {
            case "Leukocyte":
                this.level.addTower(new Leukocyte(this.level.towerToBuild.getBase()));
                break;
            case "Alcohol":
                this.level.addTower(new Alcohol(this.level.towerToBuild.getBase()));
                break;
            case "Medication":
                this.level.addTower(new Medication(this.level.towerToBuild.getBase()));
                break;
        }

        TowerSprite newTower = new TowerSprite(this.level.getTowers().get(this.level.getTowers().size() - 1), level);
        this.towerSprites.add(newTower);
        this.add(newTower);
    }

    /**
     * Main render function
     */
    public void render() {
        // Check if a wave is cleared
        if (this.actualWave != null) {
            if (this.actualWave.isWaveCleared()) {
                this.setNextWave();
            }
        }

        // Move enemies for every render
        moveEnemies();

        // Render enemies
        for (EnemySprite es : enemySprites) {
            es.render();
        }

        // Shoot the enemies
        damageEnemies();
        for (TowerSprite ts : this.towerSprites) {
            ts.render();
        }

        // Remove dead enemy sprites
        removeDeadEnemies();

        if (this.buildingTowerSprite != null) {
            this.buildingTowerSprite.render();
        }

        // Render the map
        boolean oneIsHovered = false;
        for (int i = 0; i < this.mapTiles.length; i++) {
            for (int j = 0; j < this.mapTiles.length; j++) {
                this.mapTiles[i][j].render();

                if (this.mapTiles[i][j].isHovered && level.towerToBuild != null && level.isBuilding) {

                    oneIsHovered = true;
                    canBuild = this.level.canBuildTower();

                    level.towerToBuild.setBase(new int[] { j, i });

                    if (this.buildingTowerSprite == null) {
                        this.buildingTowerSprite = new TowerSprite(level.towerToBuild, level, true);
                        this.add(this.buildingTowerSprite);
                    }

                    this.buildingTowerSprite.setTower(level.towerToBuild);
                    this.buildingTowerSprite.refreshLocation();

                }

            }
        }

        if (!oneIsHovered || !level.isBuilding) {
            if (this.buildingTowerSprite != null) {
                this.remove(buildingTowerSprite);
                this.buildingTowerSprite = null;
            }
        }

        this.repaint();
    }

    /**
     * Overriding the paint method to draw the lines of shooting
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(level.getBackgroundImage(), 0, 0, 600, 600, Color.white, null);
        super.paint(g);

        if (this.buildingTowerSprite != null && canBuild) {
            this.buildingTowerSprite.drawRange(g);
        }

        for (Tower t : this.level.getTowers()) {
            BulletSprite.draw(g, t);
        }

        for (TowerSprite ts : this.towerSprites) {
            ts.drawRange(g);
        }

    }
}