package GameObjects;
import java.awt.*;

public class Block{
    private Color color;
    private int size;

    public Block(int size){
        this.size = size;
    }

    public Block(Color color, int size) {
        this.color = color;
        this.size = size;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public Block getCopy(){
        return new Block(color, size);
    }
}