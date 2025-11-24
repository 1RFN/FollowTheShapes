import java.awt.*;
public class SquareShape extends GameShape {
    public SquareShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(isActive ? color.brighter().brighter() : color); // Efek highlight
        g2.fillRect(x, y, size, size);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(x, y, size, size);
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }

    @Override
    public void highlight(Graphics2D g2) {
        // Bisa tambah efek khusus jika diklik
    }
}