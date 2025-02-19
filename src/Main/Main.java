package Main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        double scale = GamePanel.getInstance().getScale();

        JFrame frame = new JFrame("Tetris");
        GamePanel panel = GamePanel.getInstance();
        frame.setSize(new Dimension((int)(1000*scale),(int)(900*scale)));
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.startThread();
    }
}