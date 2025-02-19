package Player;
import GameObjects.*;
import GameObjects.Mino.Mino;
import GameObjects.Mino.*;
import Main.GamePanel;
import Vector.Vector2DInt;
import java.util.*;


public class Player {
    private Queue<Mino> minoQueue;
    private Vector2DInt position, startPosition;
    private Mino currentMino;

    public Player(int startPositionX, int startPositionY){
        minoQueue = new LinkedList<Mino>();
        position = new Vector2DInt(startPositionX, startPositionY);
        startPosition = new Vector2DInt(startPositionX, startPositionY);
        pickNextMino();
    }

    public void resetPosition(){
        position.setX(startPosition.getX());
        position.setY(startPosition.getY());
    }

    public void setPosition(int x, int y){
        position = new Vector2DInt(x, y);
    }

    public void setCurrentMino(Mino newMino){
        currentMino = newMino;
    }

    public Mino getCurrentMino() {
        return currentMino;
    }

    public Vector2DInt getPosition() {
        return position;
    }

    public Queue getMinoQueue(){
        return minoQueue;
    }

    public void pickNextMino(){
        if(minoQueue.size() <= 3) {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                indexes.add(i);
            }
            Collections.shuffle(indexes);

            for(int i = 0; i < 7; i++){
                switch (indexes.indexOf(i)) {
                    case 0:
                        minoQueue.add(new IMinoFactory().createMino(new Block(GamePanel.getInstance().getBlockSize() - 2)));
                        break;
                    case 1:
                        minoQueue.add(new OMinoFactory().createMino(new Block(GamePanel.getInstance().getBlockSize() - 2)));
                        break;
                    case 2:
                        minoQueue.add(new LMinoFactory().createMino(new Block(GamePanel.getInstance().getBlockSize() - 2)));
                        break;
                    case 3:
                        minoQueue.add(new TMinoFactory().createMino(new Block(GamePanel.getInstance().getBlockSize() - 2)));
                        break;
                    case 4:
                        minoQueue.add(new JMinoFactory().createMino(new Block(GamePanel.getInstance().getBlockSize() - 2)));
                        break;
                    case 5:
                        minoQueue.add(new SMinoFactory().createMino(new Block(GamePanel.getInstance().getBlockSize() - 2)));
                        break;
                    case 6:
                        minoQueue.add(new ZMinoFactory().createMino(new Block(GamePanel.getInstance().getBlockSize() - 2)));
                        break;
                }
            }
        }
        currentMino = minoQueue.poll();
    }
}
