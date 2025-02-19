package GameObjects.Mino;
import GameObjects.Block;
import java.awt.*;

public class OMinoFactory implements MinoFactory{
    @Override
    public Mino createMino(Block block) {
        block.setColor(Color.YELLOW);
        Mino tetrimino = new Mino(2);
        tetrimino.setBlock(0, 0, block);
        tetrimino.setBlock(0, 1, block);
        tetrimino.setBlock(1, 0, block);
        tetrimino.setBlock(1, 1, block);
        return tetrimino;
    }
}
