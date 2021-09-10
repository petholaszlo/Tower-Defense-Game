package components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

import logic.Alcohol;
import logic.Leukocyte;
import logic.Level;
import logic.Medication;

public class ShopArea extends JPanel implements Renderable {

    Level level;
    TowerToBuild leukocyte;
    TowerToBuild alcohol;
    TowerToBuild medication;
    JLabel crowns;

    public ShopArea(Level level) {
        // Setting up the component visually
        this.setLayout(new GridLayout(4, 1, 10, 0));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setSize(200, 300);
        this.setLocation(610, 50);
        this.setBackground(Color.white);
        this.level = level;

        crowns = new JLabel(String.format("%d", level.getCrown()));
        this.add(crowns);

        leukocyte = new TowerToBuild(new Leukocyte(), level);
        this.add(leukocyte);

        alcohol = new TowerToBuild(new Alcohol(), level);
        this.add(alcohol);

        medication = new TowerToBuild(new Medication(), level);
        this.add(medication);

    }

    public void render() {
        crowns.setText(String.format("Cowns: %d   Hp: %d", level.getCrown(), level.getHp()));
        leukocyte.render();
        alcohol.render();
        medication.render();
    }
}