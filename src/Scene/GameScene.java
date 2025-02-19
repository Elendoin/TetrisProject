package Scene;
import GameObjects.Block;
import GameObjects.Mino.Mino;
import Input.InputHandler;
import Main.GamePanel;
import Player.Player;
import Scene.GameSceneStates.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;


public class GameScene implements Scene {
    private Block[][] grid;
    private InputHandler inputHandler;
    Player player;
    private boolean left;
    private long nextFallTime, interval;
    private static final long LONG_INTERVAL = 750;
    private static final long SHORT_INTERVAL = 100;
    private static final int X_RECTANGLE_OFFSET = 100;
    private static final int Y_RECTANGLE_OFFSET = 20;
    private double scale;
    private GameSceneState gameSceneState;
    private boolean paused;
    private boolean gameOver;
    private int score = 0;
    private double scoreMultiplier = 1.0;



    public GameScene(InputHandler inputHandler){
        scale = GamePanel.getInstance().getScale();
        grid = new Block[20][10];
        this.inputHandler = inputHandler;
        this.player = new Player(0, 4);
        interval = LONG_INTERVAL;
        nextFallTime = System.currentTimeMillis() + interval;
        this.gameSceneState = new Running(this);
    }


    @Override
    public void update() {

        long timeLeft = 0;
        //PAUZA
        if(inputHandler.isPressed(KeyEvent.VK_SPACE) && !paused){
            timeLeft = nextFallTime - System.currentTimeMillis();
            gameSceneState = new Paused(this);
            inputHandler.unpress(KeyEvent.VK_SPACE);
        }
        if(inputHandler.isPressed(KeyEvent.VK_SPACE) && gameSceneState.unpausable()){
            nextFallTime = System.currentTimeMillis() + timeLeft;
            gameSceneState = new Running(this);
            inputHandler.unpress(KeyEvent.VK_SPACE);
        }
        paused = gameSceneState.paused();
        if(paused){
            return;
        }

        //RIGHT MOVEMENT
        if(inputHandler.isPressed(KeyEvent.VK_RIGHT)){
            left = false;
            if(canMoveHorizontal()){
                player.setPosition(player.getPosition().getX(), player.getPosition().getY()+1);
            }
            inputHandler.unpress(KeyEvent.VK_RIGHT);
        }

        //LEFT MOVEMENT
        if(inputHandler.isPressed(KeyEvent.VK_LEFT)){
            left = true;
            if(canMoveHorizontal()){
                player.setPosition(player.getPosition().getX(), player.getPosition().getY()-1);
            }
            inputHandler.unpress(KeyEvent.VK_LEFT);
        }

        //ROTATE
        if(inputHandler.isPressed(KeyEvent.VK_UP)){
            Mino rotated = new Mino(player.getCurrentMino().rotatedClockwise());
            if(isValidPosition(rotated)){
                player.setCurrentMino(rotated);
            }
            inputHandler.unpress(KeyEvent.VK_UP);
        }

        //DOWN SPEED UP
        if(inputHandler.isPressed(KeyEvent.VK_DOWN) && interval != SHORT_INTERVAL){
            if(canMoveDown()){
                player.setPosition(player.getPosition().getX()+1, player.getPosition().getY());
            }
            interval = SHORT_INTERVAL;
            nextFallTime = System.currentTimeMillis() + interval;
        }
        else if (!inputHandler.isPressed(KeyEvent.VK_DOWN) && interval != LONG_INTERVAL){
            interval = LONG_INTERVAL;
            nextFallTime = System.currentTimeMillis() + interval;
        }

        //FALL PER INTERVAL
        if(System.currentTimeMillis() >= nextFallTime){
            if(canMoveDown()) {
                player.setPosition(player.getPosition().getX()+1, player.getPosition().getY());
                nextFallTime += interval;
            }
            else{
                addPlayerToGrid();
            }
        }

        //REMOVE AND DROP
        for(int i = 0; i < grid.length; i++){
            int blocksInLineCount = 0;
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j]!=null) {
                    blocksInLineCount++;
                }
            }
            if(blocksInLineCount == 10){
                grid = removeAnDrop(i, grid);
                score += (int)(100*scoreMultiplier);
                scoreMultiplier+=0.25;
            }
        }


    }

    @Override
    public void draw(Graphics2D g) {
        Mino playerMino = player.getCurrentMino();
        Block[][] playerMinoGrid = playerMino.getMinoGrid();
        int blockSize = GamePanel.getInstance().getBlockSize();

        //PLAYER BLOCKS
        for(int i = 0; i < playerMino.getPlayerGridSize(); i++){
            for(int j = 0; j < playerMino.getPlayerGridSize(); j++){
                if(playerMinoGrid[i][j] != null){
                    Color color = playerMinoGrid[i][j].getColor();
                    g.setColor(color);
                    int x = player.getPosition().getX() + i;
                    int y = player.getPosition().getY() + j;
                    g.fillRect((int)((y*blockSize + X_RECTANGLE_OFFSET) * scale), (int)((x*blockSize + Y_RECTANGLE_OFFSET) * scale),
                            (int)((blockSize-2)*scale), (int)((blockSize-2)*scale));
                }
            }
        }

        //ALL BLOCKS
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] != null)
                {
                    Color color = grid[i][j].getColor();
                    g.setColor(color);
                    g.fillRect((int)((j*blockSize + X_RECTANGLE_OFFSET)*scale), (int)((i*blockSize + Y_RECTANGLE_OFFSET)*scale),
                            (int)((blockSize-2) * scale), (int)((blockSize-2)* scale));
                }
            }
        }

        Queue<Mino> playerQueueCopy = new LinkedList<Mino>(player.getMinoQueue());
        //UPCOMING BLOCKS
        for(int i = 0; i < 3; i++){
            Mino upcomingMino = playerQueueCopy.poll();
            for(int x = 0; x < upcomingMino.getPlayerGridSize(); x++){
                for(int y = 0; y < upcomingMino.getPlayerGridSize(); y++){
                    if(upcomingMino.getMinoGrid()[x][y] != null) {
                        g.setColor(upcomingMino.getBlock(x, y).getColor());
                        g.fillRect((int)(((400+X_RECTANGLE_OFFSET*2 + 50 + (y*blockSize))*scale)),
                                (int)((Y_RECTANGLE_OFFSET*scale + (x*blockSize) + 75 + (i * 200))*scale),
                                (int)((blockSize-2) * scale), (int)((blockSize-2)* scale));
                    }
                }
            }
        }

        //RECTANGLES AND TEXT
        g.setColor(Color.WHITE);
        g.drawRect((int)((X_RECTANGLE_OFFSET) * scale),(int)((Y_RECTANGLE_OFFSET) * scale),(int)(400*scale), (int)(800 * scale));
        g.drawRect((int)((400+X_RECTANGLE_OFFSET*2) * scale), (int)(Y_RECTANGLE_OFFSET*scale), (int)(200*scale), (int)(700*scale));
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("NEXT", (int)((400+X_RECTANGLE_OFFSET*2 + 60) * scale), (int)(Y_RECTANGLE_OFFSET*3*scale));
        g.drawString(String.valueOf(score), (int)(700*scale), (int)(800*scale));

        if(paused){
            g.drawString(gameSceneState.pauseText(), 15, 30);
        }

    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }

    private boolean canMoveHorizontal(){
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        int n = player.getCurrentMino().getPlayerGridSize();
        Block[][] playerGrid = player.getCurrentMino().getMinoGrid();


        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(playerGrid[i][j] == null){
                    continue;
                }

                if(!left && ((y + j + 1 >= grid[i].length) || (grid[x + i][y + j + 1] != null))){
                    return false;
                }
                else if(left && ((y + j <= 0) || grid[x+i][y+j-1] != null)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canMoveDown(){
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        int n = player.getCurrentMino().getPlayerGridSize();
        Block[][] playerGrid = player.getCurrentMino().getMinoGrid();


        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(playerGrid[i][j] == null){
                    continue;
                }

                if((x + i + 1 >= grid.length) || (grid[x + i + 1][y + j] != null)){
                    return false;
                }
            }
        }
        return true;
    }

    private void addPlayerToGrid(){
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        int n = player.getCurrentMino().getPlayerGridSize();
        Block[][] playerGrid = player.getCurrentMino().getMinoGrid();


        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(playerGrid[i][j] == null){
                    continue;
                }

                grid[x + i][y + j] = playerGrid[i][j].getCopy();
            }
        }

        player.pickNextMino();
        player.resetPosition();

        if(!isValidPosition(player.getCurrentMino())){
            gameSceneState = new GameOver(this);
            paused = gameSceneState.paused();
        }
    }

    private Block[][] removeAnDrop(int row, Block[][] grid){
        Block[][] newGrid = new Block[20][10];

        for(int i = grid.length-1; i > row; i--){
            for(int j = 0 ; j < grid[0].length; j++){
                if(grid[i][j] != null){
                    newGrid[i][j] = grid[i][j].getCopy();
                }
            }
        }

        for(int j = 0; j < grid[0].length; j++){
            newGrid[row][j] = null;
        }

        for(int i = row-1; i > 0; i--){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] != null){
                    newGrid[i][j] = grid[i][j].getCopy();
                    int currentI = i;
                    while((currentI + 1 != grid.length) && (newGrid[i+1][j] == null) && (currentI+1 <= row)){
                        newGrid[currentI+1][j] = newGrid[currentI][j].getCopy();
                        newGrid[currentI][j] = null;
                        currentI++;
                    }
                }
            }
        }

        return newGrid;
    }

    private  boolean isValidPosition(Mino mino) {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        int n = mino.getPlayerGridSize();
        Block[][] playerGrid = mino.getMinoGrid();

        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (playerGrid[i][j] != null) {
                    int newX = x + i;
                    int newY = y + j;

                    if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                        return false;
                    }
                    if (grid[newX][newY] != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}