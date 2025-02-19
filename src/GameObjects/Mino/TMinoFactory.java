package GameObjects.Mino;
import GameObjects.Block;
import java.awt.*;

public class TMinoFactory implements MinoFactory{
    @Override
    public Mino createMino(Block block) {
        block.setColor(new Color(128, 0, 128));
        Mino tetrimino = new Mino(3);
        tetrimino.setBlock(0, 1, block);
        tetrimino.setBlock(1, 0, block);
        tetrimino.setBlock(1, 1, block);
        tetrimino.setBlock(1, 2, block);
        return tetrimino;
    }
}
