package logic;

// dont forget to delete SOUT-s

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.Image;

import components.TowerSprite;

public class Level {
    int[][] map;
    Point[] path;
    Wave[] waves;
    ArrayList<Tower> towers;
    int hp;
    int crown;
    Image background;

    public boolean isBuilding;
    public TowerSprite towerSpriteToBuild;
    public Tower towerToBuild;
    public Tower towerToUpgrade;

    public Level(String folder) {
        this.loadInfo(folder);
        this.loadMap(folder);
        this.loadPath(folder);
        this.loadWaves(folder);
        this.loadBackground(folder);
        this.isBuilding = false;
        this.towerToBuild = null;
        this.towerToUpgrade = null;
        this.towerSpriteToBuild = null;
        this.towers = new ArrayList<>();
    }

    /**
     * Load the hp and crown data from file
     * 
     * @param folder The name of the folder where an info.txt file can be found
     */
    private void loadInfo(String folder) {
        try {
            File file = new File(String.format("data/%s/info.txt", folder));
            Scanner sc = new Scanner(file);
            int scannerPosition = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (scannerPosition == 0) {
                    this.hp = Integer.parseInt(line);
                }
                if (scannerPosition == 1) {
                    this.crown = Integer.parseInt(line);
                }
                scannerPosition++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // Handle if file is not found
        } catch (Exception e) {
            // Handle other exceptions (wrongly formatted file etc.)
        }
    }

    /**
     * Load the map design from file The map design defines where the user can build
     * towers by default
     * 
     * @param folder The name of the folder where a map.txt file can be found
     */
    private void loadMap(String folder) {
        try {
            File file = new File(String.format("data/%s/map.txt", folder));
            ArrayList<String> lines = new ArrayList<String>();

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lines.add(line);
            }

            this.map = new int[lines.size()][lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                String[] splittedLine = lines.get(i).split(",");
                for (int j = 0; j < splittedLine.length; j++) {
                    this.map[i][j] = Integer.parseInt(splittedLine[j]);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // Handle if file is not found
        } catch (Exception e) {
            // Handle other exceptions (wrongly formatted file etc.)
        }
    }

    /**
     * Load the path of the enemies from file
     * 
     * @param folder The name of the folder where a path.txt file can be found
     */
    private void loadPath(String folder) {
        try {
            File file = new File(String.format("data/%s/path.txt", folder));
            ArrayList<String> lines = new ArrayList<String>();

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lines.add(line);
            }

            this.path = new Point[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                String[] splittedLine = lines.get(i).split(",");
                int x = Integer.parseInt(splittedLine[0]);
                int y = Integer.parseInt(splittedLine[1]);
                this.path[i] = new Point(x * Configuration.MAP_UNIT, y * Configuration.MAP_UNIT);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // Handle if file is not found
        } catch (Exception e) {
            // Handle other exceptions (wrongly formatted file etc.)
        }
    }

    /**
     * Load the waves data from file and create them accordingly
     * 
     * @param folder The name of the folder where a waves.txt file can be found
     */
    private void loadWaves(String folder) {
        try {
            File file = new File(String.format("data/%s/waves.txt", folder));
            ArrayList<String> lines = new ArrayList<String>();

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lines.add(line);
            }

            this.waves = new Wave[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                String[] splittedLine = lines.get(i).split(",");
                int numberOfFeverGenom = Integer.parseInt(splittedLine[0]);
                int numberOfCoughGenom = Integer.parseInt(splittedLine[1]);
                int numberOfHeadacheGenom = Integer.parseInt(splittedLine[2]);

                Enemy[] enemiesArray = new Enemy[numberOfFeverGenom + numberOfCoughGenom + numberOfHeadacheGenom];
                int counter = 0;

                for (int e = 0; e < numberOfFeverGenom; e++) {
                    enemiesArray[counter] = new Enemy(80, 20, this.path);
                    counter++;
                }
                for (int e = 0; e < numberOfCoughGenom; e++) {
                    enemiesArray[counter] = new Enemy(40, 30, this.path);
                    counter++;
                }
                for (int e = 0; e < numberOfHeadacheGenom; e++) {
                    enemiesArray[counter] = new Enemy(40, 45, this.path);
                    counter++;
                }

                this.waves[i] = new Wave(enemiesArray);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // Handle if file is not found
        } catch (Exception e) {
            // Handle other exceptions (wrongly formatted file etc.)
        }
    }

    /**
     * Load the background image
     * 
     * @param folder The name of the folder where a waves.txt file can be found
     */
    private void loadBackground(String imageName) {
        try {
            this.background = (Image) ImageIO.read(new File(String.format("assets/%s.jpg", imageName)));
        } catch (FileNotFoundException e) {
            // Handle if file is not found
        } catch (Exception e) {
            // Handle other exceptions (wrongly formatted file etc.)
        }
    }

    /**
     * @return the map
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * @return the path of an enemy
     */
    public Point[] getPath() {
        return path;
    }

    /**
     * @return the waves
     */
    public Wave[] getWaves() {
        return waves;
    }

    /**
     * Return the list of towers
     */
    public ArrayList<Tower> getTowers() {
        return this.towers;
    }

    /**
     * @return the crown
     */
    public int getCrown() {
        return crown;
    }

    /**
     * @return the background image
     */
    public Image getBackgroundImage() {
        return this.background;
    }

    /**
     * Adds a tower to the towers list
     * 
     * @param tower
     */
    public void addTower(Tower tower) {
        if (canBuildTower()) {
            this.towers.add(tower);
            this.crown -= tower.getPrice();
            this.isBuilding = false;
            this.towerToBuild = null;
        }
    }

    public boolean canBuildTower() {
        int[] sizeOfTower = this.towerToBuild.getSize();
        int[] baseOfTower = this.towerToBuild.getBase();
        int[][] map = this.getMap();

        for (int k = baseOfTower[0]; k < baseOfTower[0] + sizeOfTower[0]; k++) {
            for (int l = baseOfTower[1]; l < baseOfTower[1] + sizeOfTower[1]; l++) {

                if (map.length <= l || map[l].length <= k || map[l][k] == 0) {
                    return false;
                }

                for (Tower t : this.towers) {
                    int[] actualTowerBase = t.getBase();
                    int[] actualTowerSize = t.getSize();
                    int ax1 = actualTowerBase[0];
                    int ax2 = actualTowerBase[0] + actualTowerSize[0];
                    int ay1 = actualTowerBase[1];
                    int ay2 = actualTowerBase[1] + actualTowerSize[1];

                    if ((l >= ax1 && l <= ax2) && (k >= ay1 && k <= ay2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    public void damagePlayer(int damage) {
        this.hp -= damage;
    }

    /**
     * @param crown the crown to set
     */
    public void addCrown(int crown) {
        this.crown += crown;
    }

    @Override
    public String toString() {
        return String.format("<%s with hp=%d, crown=%d, points_in_path=%d, waves=%d>", super.toString(), this.hp,
                this.crown, this.path.length, this.waves.length);
    }
}