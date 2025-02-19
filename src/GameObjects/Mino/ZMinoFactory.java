package GameObjects.Mino;
import GameObjects.Block;
import java.awt.*;

public class ZMinoFactory implements MinoFactory{
    @Override
    public Mino createMino(Block block) {
        block.setColor(Color.RED);
        Mino tetrimino = new Mino(3);
        tetrimino.setBlock(0, 0, block);
        tetrimino.setBlock(0, 1, block);
        tetrimino.setBlock(1, 1, block);
        tetrimino.setBlock(1, 2, block);
        return tetrimino;
    }
}
