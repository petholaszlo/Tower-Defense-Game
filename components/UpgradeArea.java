package components;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.Level;

public class UpgradeArea extends JPanel implements Renderable {
    Level level;
    JLabel noTowerSelectedLabel;
    JLabel upgradeInfo;
    JButton upgradeButton1;
    JButton upgradeButton2;

    public UpgradeArea(Level level) {
        // Setting up the component visually
        this.setSize(200, 290);
        this.setLocation(610, 360);
        this.setBackground(Color.WHITE);
        this.level = level;

        this.add(new JLabel("Tower upgrade", SwingConstants.CENTER));

        noTowerSelectedLabel = new JLabel("Select a tower to upgrade", SwingConstants.CENTER);
        this.add(noTowerSelectedLabel);
        upgradeInfo = new JLabel("", SwingConstants.CENTER);
        this.add(upgradeInfo);

        upgradeButton1 = new JButton("Upgrade 1");
        this.add(upgradeButton1);
        upgradeButton1.addMouseListener(upgradeAdapter1);

        upgradeButton2 = new JButton("Upgrade 2");
        this.add(upgradeButton2);
        upgradeButton2.addMouseListener(upgradeAdapter2);
    }

    /**
     * Mouse adapter to handle on click
     */
    private MouseAdapter upgradeAdapter1 = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // On enter -> Set isHovered to true
            level.towerToUpgrade.upgradeTower(1);
        }
    };

    /**
     * Mouse adapter to handle on click
     */
    private MouseAdapter upgradeAdapter2 = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // On enter -> Set isHovered to true
            level.towerToUpgrade.upgradeTower(2);
        }
    };

    public void render() {
        if (this.level.towerToUpgrade == null) {
            noTowerSelectedLabel.setText("Select a tower to upgrade");
            upgradeButton1.setVisible(false);
            upgradeButton2.setVisible(false);
            return;
        }

        noTowerSelectedLabel.setText(String.format("Tower XP: %d", level.towerToUpgrade.getXp()));

        if (!level.towerToUpgrade.canUpgrade()) {
            upgradeInfo.setText("Can't upgrade tower");
            upgradeInfo.setVisible(true);
            upgradeButton1.setEnabled(false);
            upgradeButton2.setEnabled(false);
            return;
        }
        upgradeInfo.setVisible(false);

        upgradeButton1.setVisible(true);
        upgradeButton1.setEnabled(true);

        upgradeButton2.setVisible(true);
        upgradeButton2.setEnabled(true);

        this.repaint();
    }
}