package GameObjects.Mino;
import GameObjects.Block;
import java.awt.*;

public class LMinoFactory implements MinoFactory{
    @Override
    public Mino createMino(Block block) {
        block.setColor(Color.ORANGE);
        Mino tetrimino = new Mino(3);
        tetrimino.setBlock(1, 0, block);
        tetrimino.setBlock(1, 1, block);
        tetrimino.setBlock(1, 2, block);
        tetrimino.setBlock(0, 2, block);
        return tetrimino;
    }
}
