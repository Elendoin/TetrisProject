package Main;

import javax.swing.*;
import java.awt.*;
import Input.InputHandler;
import Scene.*;
import Scene.GameScene;

public class GamePanel extends JPanel implements Runnable{
    private static GamePanel gamePanel;
    private InputHandler inputHandler = new InputHandler();
    private Scene scene;
    private int blockSize;
    Thread gameThread;
    private final static double SCALE = 1.0;

    private GamePanel(){
        this.blockSize = 40;
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
    }

    public static GamePanel getInstance(){
        if(gamePanel == null){
            gamePanel = new GamePanel();
        }
        return gamePanel;
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public int getBlockSize(){
        return blockSize;
    }

    public double getScale(){
        return SCALE;
    }

    private void update(){
        scene.update();
    }

    public void startThread() {
        scene = new GameScene(inputHandler);
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        while(gameThread != null){
            update();
            repaint();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        scene.draw(g2D);
    }
}
