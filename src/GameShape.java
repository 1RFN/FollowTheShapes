import java.awt.*;
public abstract class GameShape implements Clickable {
    protected int x, y, size;
    protected Color color;
    protected boolean isActive = false; // Status saat menyala (sequence)

    public GameShape(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // Abstract method: setiap bentuk wajib punya cara menggambar sendiri
    public abstract void draw(Graphics g);
}