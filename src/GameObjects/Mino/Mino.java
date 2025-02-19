package GameObjects.Mino;
import GameObjects.Block;

public class Mino {
    private int playerGridSize;
    private Block[][] minoGrid;

    public Mino(Block[][] minoGrid){
        this.playerGridSize = minoGrid.length;
        this.minoGrid = minoGrid;
    }

    public Mino(int playerGridSize) {
        this.playerGridSize = playerGridSize;
        this.minoGrid = new Block[playerGridSize][playerGridSize];
    }

    public void setBlock(int row, int col, Block block) {
        minoGrid[row][col] = new Block(block.getColor(), block.getSize());
    }

    public Block getBlock(int row, int col) {
        return minoGrid[row][col];
    }

    public int getPlayerGridSize() {
        return playerGridSize;
    }

    public Block[][] getMinoGrid() {
        return minoGrid;
    }

    public Block[][] rotatedClockwise(){
        Block[][] rotated = new Block[playerGridSize][playerGridSize];
        for(int i = 0; i < playerGridSize; i++){
            for(int j = 0; j < playerGridSize; j++){
                rotated[j][playerGridSize - 1 - i] = minoGrid[i][j];
            }
        }
        return rotated;
    }

    public Block[][] rotatedCounterClockwise(){
        Block[][] rotated = new Block[playerGridSize][playerGridSize];
        for(int i = 0; i < playerGridSize; i++){
            for(int j = 0; j < playerGridSize; j++){
                rotated[playerGridSize - 1 - j][j] = minoGrid[i][j];
            }
        }
        return rotated;
    }
}
