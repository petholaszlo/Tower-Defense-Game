package views;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class MenuView extends JFrame {

    public Image image = new ImageIcon("assets/menu_bg.png").getImage();

    /**
     * Initialize the game view
     */
    public MenuView() {
        super();

        JButton level1 = new JButton();
        level1.setText("Első");
        level1.addActionListener(getActionListener("level_1"));

        JButton level2 = new JButton();
        level2.setText("Második");
        level2.addActionListener(getActionListener("level_2"));

        JButton level3 = new JButton();
        level3.setText("Harmadik");
        level3.addActionListener(getActionListener("level_3"));

        JButton level4 = new JButton();
        level4.setText("Negyedik");
        level4.addActionListener(getActionListener("level_4"));

        JButton level5 = new JButton();
        level5.setText("Ötödik");
        level5.addActionListener(getActionListener("level_5"));

        JFrame frame = new JFrame("Project Influenza");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new DrawImage();
        container.add(level1);
        container.add(level2);
        container.add(level3);
        container.add(level4);
        container.add(level5);

        frame.add(container);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(630, 630);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private ActionListener getActionListener(String level) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame mainWindow = new JFrame(level);
                mainWindow.setLayout(null);
                mainWindow.setSize(820, 680);
                mainWindow.setResizable(false);

                GameView gameView = new GameView(level);
                mainWindow.add(gameView);
                mainWindow.toFront();
                mainWindow.setVisible(true);

            }

        };
    }

    public class DrawImage extends JPanel {

        public DrawImage() {
            setBackground(new Color(0, true));
        }

        @Override
        public void paintComponent(Graphics g) {
            // Paint background first
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

            // Paint the rest of the component. Children and self etc.
            super.paintComponent(g);
        }
    }

}