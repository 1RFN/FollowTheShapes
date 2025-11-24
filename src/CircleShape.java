import java.awt.*;
public class CircleShape extends GameShape {
    public CircleShape(int x, int y, int size, Color color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(isActive ? color.brighter().brighter() : color);
        g2.fillOval(x, y, size, size);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x, y, size, size);
    }

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        // Hitbox lingkaran sederhana (menggunakan jarak euclidian atau bounding box)
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        double distance = Math.sqrt(Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2));
        return distance <= size / 2;
    }
    
    @Override
    public void highlight(Graphics2D g2) {}
}