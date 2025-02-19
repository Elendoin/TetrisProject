package GameObjects.Mino;
import GameObjects.Block;

import java.awt.*;

public class IMinoFactory implements MinoFactory{
    @Override
    public Mino createMino(Block block) {
        block.setColor(Color.CYAN);
        Mino tetromino = new Mino(4);
        for (int i = 0; i < 4; i++) {
            tetromino.setBlock(i, 1, block);
        }
        return tetromino;
    }
}