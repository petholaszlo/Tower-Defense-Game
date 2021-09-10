package components;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;

import logic.Level;
import logic.Wave;

public class WavesArea extends JPanel implements Renderable {
    Level level;
    JLabel wavesLabel;

    public WavesArea(Level level) {
        // Setting up the component visually
        this.setSize(810, 40);
        this.setLocation(0, 0);
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.level = level;
        this.wavesLabel = new JLabel();
        this.add(wavesLabel);
    }

    public void render() {
        String wavesString = "";
        for (Wave w : level.getWaves()) {
            if (!w.isWaveCleared()) {
                wavesString += w.toString();
                wavesString += " >> ";
            }
        }
        wavesString += "WIN!";
        wavesLabel.setText(wavesString);
        this.repaint();
    }
}