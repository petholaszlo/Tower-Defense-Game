package logic;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;

public class Wave {
    Enemy[] enemies;
    ArrayList<Enemy> notMovingEnemies;
    private Timer enemyStartTimer;

    /**
     * Initialize Wave with enemies
     * 
     * @param enemies The enemies within the wave
     */
    Wave(Enemy[] enemies) {
        // Set enemies from param
        this.enemies = enemies;

        // Add all enemies to the not moving enemies' list
        this.notMovingEnemies = new ArrayList<Enemy>();
        for (Enemy e : enemies) {
            notMovingEnemies.add(e);
        }
    }

    /**
     * Checks whether all the enemies of the wave are dead
     * 
     * @return true if all dead, false is not
     */
    public boolean isWaveCleared() {
        for (Enemy e : this.enemies) {
            if (e.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the enemies
     */
    public Enemy[] getEnemies() {
        return enemies;
    }

    // -----------------------
    // The timing of the waves
    // -----------------------

    /**
     * Start the wave of enemies
     */
    public void startEnemies() {
        enemyStartTimer = new Timer(2000, new EnemyStartListener());
        enemyStartTimer.start();
    }

    /**
     * ActionListener for the Timer
     */
    private class EnemyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            // Get a random enemy from the list of the not moving ones
            int startIndex = new Random().nextInt(notMovingEnemies.size());

            // Start that enemy and remove it from the not moving ones
            notMovingEnemies.get(startIndex).setIsMoving(true);
            notMovingEnemies.remove(startIndex);

            // If there are no other enemies to start, stop the timer
            if (notMovingEnemies.size() == 0) {
                enemyStartTimer.stop();
            }
        }
    };

    @Override
    public String toString() {
        int numOfEnemies = 0;
        for (Enemy e : this.enemies) {
            if (e.isAlive()) {
                numOfEnemies += 1;
            }
        }
        return String.format("Enemies: <%d>", numOfEnemies);
    }
}