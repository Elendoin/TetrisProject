package GameObjects.Mino;
import GameObjects.Block;

public interface MinoFactory {
    Mino createMino(Block block);
}
